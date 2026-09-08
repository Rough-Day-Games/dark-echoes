package com.rdg.darkechoes.combat;

import com.mojang.blaze3d.vertex.PoseStack;
import com.rdg.darkechoes.DarkEchoes;
import com.rdg.darkechoes.config.ServerConfig;
import com.rdg.darkechoes.helpers.AugmentEffectComponents;
import com.rdg.darkechoes.helpers.AugmentHelper;
import com.rdg.darkechoes.progression.MobProgression;
import com.rdg.darkechoes.progression.Progression;
import com.rdg.darkechoes.progression.ToolProgression;
import com.rdg.darkechoes.registry.ModDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.state.level.BlockOutlineRenderState;
import net.minecraft.client.renderer.state.level.LevelRenderState;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.CustomBlockOutlineRenderer;
import net.neoforged.neoforge.client.event.ExtractBlockOutlineRenderStateEvent;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.block.BreakBlockEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

import java.util.ArrayList;
import java.util.List;

import static com.rdg.darkechoes.helpers.GearHelper.assessMaxLevel;

public final class CombatEvents {
    private CombatEvents() {
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onIncomingDamage(LivingIncomingDamageEvent event) {
        LivingEntity target = event.getEntity();
        if (target.level().isClientSide()) {
            return;
        }

        DamageSource source = event.getSource();
        Entity attacker = source.getEntity();
        ItemStack weapon = weapon(source);
        if (weapon != null && AugmentHelper.has(weapon, AugmentEffectComponents.PREVENT_GEAR_BREAK) && weapon.getDamageValue() >= weapon.getMaxDamage()) {
            event.setAmount(0);
        }
        double outgoing = CombatRules.outgoingMultiplier(attacker, source);
        double incoming = CombatRules.incomingMultiplier(target, source);
        double item = CombatRules.itemMultiplier(weapon);
        double progression = Progression.weaponDamageMultiplier(weapon, target);
        float original = event.getAmount();
        float modified = (float) Math.max(0.0D, original * outgoing * incoming * item * progression);
        event.setAmount(modified);

        int armorLevels = Progression.equippedArmorLevels(target, attacker);
        if (armorLevels > 0) {
            double armorMultiplier = 1.0D
                    + armorLevels * ServerConfig.ARMOR_REDUCTION_BONUS_PER_LEVEL.getAsDouble();
            event.addReductionModifier(DamageContainer.Reduction.ARMOR,
                    (container, vanillaReduction) -> (float) Math.min(
                            container.getNewDamage(), vanillaReduction * armorMultiplier));
        }

        Progression.recordArmorHit(target, attacker);

        if (ServerConfig.DEBUG_LOGGING.getAsBoolean()) {
            DarkEchoes.LOGGER.info(
                    "Damage: attacker={}, target={}, original={}, outgoing={}, incoming={}, item={}, progression={}, armorLevels={}, preReduction={}",
                    entityId(attacker), entityId(target), original, outgoing, incoming, item,
                    progression, armorLevels, modified);
        }
    }

    @SubscribeEvent
    public static void updateBlockBounds(ExtractBlockOutlineRenderStateEvent event) {
        Entity entity = event.getCamera().entity();
        if (entity instanceof Player) {
            BlockPos mainBlock = event.getBlockPos();
            BlockPos above = mainBlock.above();
            BlockPos below = mainBlock.below();
            BlockPos north = mainBlock.north();
            BlockPos south = mainBlock.south();
            BlockPos west = mainBlock.west();
            BlockPos east = mainBlock.east();
            Direction side = entity.getNearestViewDirection();

            BlockPos tl = (side == Direction.NORTH || side == Direction.SOUTH) ? mainBlock.offset(1, 1, 0) : ((side == Direction.WEST || side == Direction.EAST) ? mainBlock.offset(0, 1, 1) : mainBlock.offset(1, 0, 1));
            BlockPos tr = (side == Direction.NORTH || side == Direction.SOUTH) ? mainBlock.offset(-1, 1, 0) : ((side == Direction.WEST || side == Direction.EAST) ? mainBlock.offset(0, 1, -1) : mainBlock.offset(1, 0, -1));
            BlockPos bl = (side == Direction.NORTH || side == Direction.SOUTH) ? mainBlock.offset(1, -1, 0) : ((side == Direction.WEST || side == Direction.EAST) ? mainBlock.offset(0, -1, 1) : mainBlock.offset(-1, 0, 1));
            BlockPos br = (side == Direction.NORTH || side == Direction.SOUTH) ? mainBlock.offset(-1, -1, 0) : ((side == Direction.WEST || side == Direction.EAST) ? mainBlock.offset(0, -1, -1) : mainBlock.offset(-1, 0, -1));

            List<BlockPos> blockPosListVert = List.of(tr, north, tl, west, east, br, south, bl);
            List<BlockPos> blockPosListNS = List.of(tr, above, tl, west, east, br, below, bl);
            List<BlockPos> blockPosListWE = List.of(tr, above, tl, north, south, br, below, bl);

            ItemStack tool = ((Player) entity).getMainHandItem();
            Level level = event.getLevel();

            CustomBlockOutlineRenderer renderer = (renderState, buffer, poseStack, translucentPass, levelRenderState) -> {
                poseStack.pushPose();
                VoxelShape shape = renderState.collisionShape();
                poseStack.translate(-32f, -16f, 32f);
                poseStack.scale(48f, 48f, 0);
                poseStack.setIdentity();
                poseStack.popPose();
                return false;
            };

            if (AugmentHelper.has(tool, AugmentEffectComponents.LARGER_BLOCK_BREAK_RADIUS)) {
                event.addCustomRenderer(renderer);
            }
        }
    }

    @SubscribeEvent
    public static void onActualBreakBlock(BreakBlockEvent event) {
        BlockPos mainBlock = event.getPos();
        BlockPos above =  mainBlock.above();
        BlockPos below =  mainBlock.below();
        BlockPos north =  mainBlock.north();
        BlockPos south =  mainBlock.south();
        BlockPos west =  mainBlock.west();
        BlockPos east =  mainBlock.east();
        Direction side = event.getPlayer().getNearestViewDirection();

        BlockPos tl = (side == Direction.NORTH || side == Direction.SOUTH) ? mainBlock.offset(1, 1, 0) : ((side == Direction.WEST || side == Direction.EAST) ? mainBlock.offset(0, 1, 1) : mainBlock.offset(1, 0, 1));
        BlockPos tr = (side == Direction.NORTH || side == Direction.SOUTH) ? mainBlock.offset(-1, 1, 0) : ((side == Direction.WEST || side == Direction.EAST) ? mainBlock.offset(0, 1, -1) : mainBlock.offset(1, 0, -1));
        BlockPos bl = (side == Direction.NORTH || side == Direction.SOUTH) ? mainBlock.offset(1, -1, 0) : ((side == Direction.WEST || side == Direction.EAST) ? mainBlock.offset(0, -1, 1) : mainBlock.offset(-1, 0, 1));
        BlockPos br = (side == Direction.NORTH || side == Direction.SOUTH) ? mainBlock.offset(-1, -1, 0) : ((side == Direction.WEST || side == Direction.EAST) ? mainBlock.offset(0, -1, -1) : mainBlock.offset(-1, 0, -1));

        List<BlockPos> blockPosListVert = List.of(tr, north, tl, west, east, br, south, bl);
        List<BlockPos> blockPosListNS = List.of(tr, above, tl, west, east, br, below, bl);
        List<BlockPos> blockPosListWE = List.of(tr, above, tl, north, south, br, below, bl);

        Player player = event.getPlayer();
        ItemStack tool = player.getMainHandItem();
        Level level = (Level) event.getLevel();
        if (!level.isClientSide()) {
            boolean dropBlock = !player.isCreative();
            if (AugmentHelper.has(tool, AugmentEffectComponents.LARGER_BLOCK_BREAK_RADIUS) && tool.canDestroyBlock(event.getLevel().getBlockState(mainBlock), level, mainBlock, player) && tool.isCorrectToolForDrops(level.getBlockState(mainBlock))) {
                if (side == Direction.UP || side == Direction.DOWN) {
                    blockPosListVert.forEach(blockPos -> {
                        if (tool.isCorrectToolForDrops(level.getBlockState(blockPos))) {
                            tool.hurtAndBreak(1, player, InteractionHand.MAIN_HAND);
                            if (tool.getDamageValue() >= tool.getMaxDamage()) return;
                            ToolProgression.recordBlockBreak(tool, level.getBlockState(blockPos));
                            event.getLevel().destroyBlock(blockPos, dropBlock);
                        }
                    });
                } else if (side == Direction.NORTH || side == Direction.SOUTH) {
                    blockPosListNS.forEach(blockPos -> {
                        if (tool.isCorrectToolForDrops(level.getBlockState(blockPos))) {
                            tool.hurtAndBreak(1, player, InteractionHand.MAIN_HAND);
                            if (tool.getDamageValue() >= tool.getMaxDamage()) return;
                            ToolProgression.recordBlockBreak(tool, level.getBlockState(blockPos));
                            event.getLevel().destroyBlock(blockPos, dropBlock);
                        }
                    });
                } else if (side == Direction.WEST || side == Direction.EAST) {
                    blockPosListWE.forEach(blockPos -> {
                        if (tool.isCorrectToolForDrops(level.getBlockState(blockPos))) {
                            tool.hurtAndBreak(1, player, InteractionHand.MAIN_HAND);
                            if (tool.getDamageValue() >= tool.getMaxDamage()) return;
                            ToolProgression.recordBlockBreak(tool, level.getBlockState(blockPos));
                            event.getLevel().destroyBlock(blockPos, dropBlock);
                        }
                    });
                }
            }
        }

    }

    @SubscribeEvent
    public static void onEntityPreTick(EntityTickEvent.Pre event) {
        Entity entity = event.getEntity();
        if (entity instanceof LivingEntity && !entity.level().isClientSide()) {
            LivingEntity living = (LivingEntity) entity;
            ItemStack boots = living.getItemBySlot(EquipmentSlot.FEET);
            if (AugmentHelper.has(boots, AugmentEffectComponents.PREVENT_FALL_DAMAGE) && !entity.isShiftKeyDown()) {
                living.addEffect(new MobEffectInstance(MobEffects.JUMP_BOOST, Integer.MAX_VALUE, 2, false, false, false));
            } else {
                living.removeEffect(MobEffects.JUMP_BOOST);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingJump(LivingEvent.LivingJumpEvent event) {
        LivingEntity entity = event.getEntity();
        ItemStack boots = entity.getItemBySlot(EquipmentSlot.FEET);
        if (AugmentHelper.has(boots, AugmentEffectComponents.PREVENT_FALL_DAMAGE) && !entity.level().isClientSide()) {
            boots.hurtAndBreak(1, entity, EquipmentSlot.FEET);
        }
    }

    @SubscribeEvent
    public static void onFallDamage(LivingFallEvent event) {
        LivingEntity entity = event.getEntity();
        ItemStack boots = event.getEntity().getItemBySlot(EquipmentSlot.FEET);
        if (AugmentHelper.has(boots, AugmentEffectComponents.PREVENT_FALL_DAMAGE) && !entity.level().isClientSide()) {
            int finalDmg = (int) (1 * (event.getDistance() / 4));
            if (finalDmg > 0) {
                entity.playSound(SoundEvents.SLIME_BLOCK_FALL);
                entity.spawnItemParticles(Items.SLIME_BALL.getDefaultInstance(), 10);
            }
            boots.hurtAndBreak(finalDmg, entity, EquipmentSlot.FEET);
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        if (!event.getEntity().level().isClientSide()) {
            Progression.recordWeaponKill(weapon(event.getSource()), event.getEntity());
        }
    }

    @SubscribeEvent
    public static void onTooltip(ItemTooltipEvent event) {
        if (Boolean.TRUE.equals(event.getItemStack().get(ModDataComponents.FRAGILE))) {
            event.getToolTip().add(Component.translatable("tooltip.darkechoes.fragile").withStyle(ChatFormatting.RED));
        } else if (Boolean.TRUE.equals(event.getItemStack().get(ModDataComponents.WEAKENED))) {
            event.getToolTip().add(Component.translatable("tooltip.darkechoes.weakened").withStyle(ChatFormatting.GRAY));
        }

        ItemStack stack = event.getItemStack();
        if (AugmentHelper.has(stack, AugmentEffectComponents.PREVENT_GEAR_BREAK) && stack.getDamageValue() >= stack.getMaxDamage()) {
            event.getToolTip().add(
                    Component.translatable("tooltip.darkechoes.malleable_broken").withStyle(ChatFormatting.RED)
            );
        }

        if (ToolProgression.isProgressionTool(stack)) {
            event.getToolTip().add(Component.translatable("tooltip.darkechoes.awakened")
                    .withStyle(ChatFormatting.AQUA));
            ToolProgression.appendTooltip(stack, event.getToolTip());
            return;
        }
        if (!Progression.isAwakenedCombatWeapon(stack) && !Progression.isAwakenedArmor(stack)) {
            return;
        }

        event.getToolTip().add(Component.translatable("tooltip.darkechoes.awakened")
                .withStyle(ChatFormatting.AQUA));
        MobProgression progression = Progression.data(stack);
        int actionsPerLevel = Progression.isAwakenedCombatWeapon(stack)
                ? ServerConfig.KILLS_PER_LEVEL.getAsInt()
                : ServerConfig.ARMOR_HITS_PER_LEVEL.getAsInt();
        int maxLevel = assessMaxLevel(stack);
        boolean weapon = Progression.isAwakenedCombatWeapon(stack);
        if (progression.locked()) {
            int level = progression.level(actionsPerLevel, maxLevel);
            long bonus = Math.round(level * (weapon
                    ? ServerConfig.WEAPON_DAMAGE_BONUS_PER_LEVEL.getAsDouble()
                    : ServerConfig.ARMOR_REDUCTION_BONUS_PER_LEVEL.getAsDouble()) * 100.0D);
            Component targetName = targetName(progression.target());
            if (level >= maxLevel) {
                event.getToolTip().add(Component.translatable(
                                weapon ? "tooltip.darkechoes.weapon_progression_max"
                                        : "tooltip.darkechoes.armor_progression_max",
                                targetName, level, bonus)
                        .withStyle(ChatFormatting.GRAY));
            } else {
                int remaining = actionsPerLevel - progression.actions() % actionsPerLevel;
                event.getToolTip().add(Component.translatable(
                                weapon ? "tooltip.darkechoes.weapon_progression"
                                        : "tooltip.darkechoes.armor_progression",
                                targetName, level, bonus, level + 1, remaining, combatGearActionName(weapon, remaining))
                        .withStyle(ChatFormatting.GRAY));
            }
        } else if (!progression.pendingTarget().isEmpty()) {
            int remaining = actionsPerLevel - progression.pendingActions();
            event.getToolTip().add(Component.translatable(
                            "tooltip.darkechoes.progression_pending",
                            targetName(progression.pendingTarget()), remaining, combatGearActionName(weapon, remaining))
                    .withStyle(ChatFormatting.GRAY));
        }
    }

    public static Component targetName(String id) {
        Identifier identifier = Identifier.tryParse(id);
        EntityType<?> type = identifier == null ? null : BuiltInRegistries.ENTITY_TYPE.getValue(identifier);
        return type == null ? Component.literal(id) : type.getDescription();
    }

    public static Component combatGearActionName(boolean weapon, int count) {
        String type = weapon ? "kill" : "hit";
        return Component.translatable("tooltip.darkechoes." + type + (count == 1 ? ".one" : ".many"));
    }

    private static ItemStack weapon(DamageSource source) {
        ItemStack sourceWeapon = source.getWeaponItem();
        Entity attacker = source.getEntity();
        if (attacker instanceof LivingEntity livingAttacker) {
            ItemStack mainHand = livingAttacker.getMainHandItem();
            if (Progression.isAwakenedCombatWeapon(mainHand) || sourceWeapon == null || sourceWeapon.isEmpty()) {
                return mainHand;
            }
        }
        return sourceWeapon;
    }

    private static String entityId(Entity entity) {
        if (entity == null) {
            return "none";
        }
        Identifier id = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType());
        return id == null ? entity.getType().toString() : id.toString();
    }
}
