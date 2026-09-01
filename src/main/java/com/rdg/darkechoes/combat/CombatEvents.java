package com.rdg.darkechoes.combat;

import com.rdg.darkechoes.DarkEchoes;
import com.rdg.darkechoes.config.CombatConfig;
import com.rdg.darkechoes.helpers.AugmentEffectComponents;
import com.rdg.darkechoes.helpers.AugmentHelper;
import com.rdg.darkechoes.progression.MobProgression;
import com.rdg.darkechoes.progression.Progression;
import com.rdg.darkechoes.progression.ToolProgression;
import com.rdg.darkechoes.registry.ModDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

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
        if (AugmentHelper.has(weapon, AugmentEffectComponents.PREVENT_GEAR_BREAK) && weapon.getDamageValue() >= weapon.getMaxDamage()) {
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
                    + armorLevels * CombatConfig.ARMOR_REDUCTION_BONUS_PER_LEVEL.getAsDouble();
            event.addReductionModifier(DamageContainer.Reduction.ARMOR,
                    (container, vanillaReduction) -> (float) Math.min(
                            container.getNewDamage(), vanillaReduction * armorMultiplier));
        }

        Progression.recordArmorHit(target, attacker);

        if (CombatConfig.DEBUG_LOGGING.getAsBoolean()) {
            DarkEchoes.LOGGER.info(
                    "Damage: attacker={}, target={}, original={}, outgoing={}, incoming={}, item={}, progression={}, armorLevels={}, preReduction={}",
                    entityId(attacker), entityId(target), original, outgoing, incoming, item,
                    progression, armorLevels, modified);
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
                ? CombatConfig.KILLS_PER_LEVEL.getAsInt()
                : CombatConfig.ARMOR_HITS_PER_LEVEL.getAsInt();
        int maxLevel = CombatConfig.MAX_MOB_PROGRESSION_LEVEL.getAsInt();
        boolean weapon = Progression.isAwakenedCombatWeapon(stack);
        if (progression.locked()) {
            int level = progression.level(actionsPerLevel, maxLevel);
            long bonus = Math.round(level * (weapon
                    ? CombatConfig.WEAPON_DAMAGE_BONUS_PER_LEVEL.getAsDouble()
                    : CombatConfig.ARMOR_REDUCTION_BONUS_PER_LEVEL.getAsDouble()) * 100.0D);
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
