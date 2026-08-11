package com.rdg.darkechoes.progression;

import com.rdg.darkechoes.client.ModItemTags;
import com.rdg.darkechoes.config.CombatConfig;
import com.rdg.darkechoes.registry.ModDataComponents;
import com.rdg.darkechoes.registry.ModItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.TransmuteRecipe;

public final class Progression {
    private Progression() {
    }

//    public static boolean canFuse(ItemStack stack) {
//        return stack != null && (isDiamondWeapon(stack) || isDiamondArmor(stack));
//    }

    public static boolean isAwakened(ItemStack stack) {
        return isAwakenedTool(stack) || isAwakenedArmor(stack);
    }

    public static boolean isAwakenedTool(ItemStack stack) {
        return stack != null && stack.has(ModDataComponents.AUGMENT_SLOTS) && stack.is(ModItemTags.AUGMENTABLE_TOOL);
    }

    public static boolean isAwakenedArmor(ItemStack stack) {
        return stack != null && stack.has(ModDataComponents.AUGMENT_SLOTS) && stack.is(ModItemTags.AUGMENTABLE_ARMOR);
    }

//    public static ItemStack fuse(ItemStack original) {
//        Item result = fusedItem(original);
//        return result == null
//                ? ItemStack.EMPTY
//                : TransmuteRecipe.createWithOriginalComponents(new ItemStackTemplate(result), original);
//    }

    public static MobProgression data(ItemStack stack) {
        return stack == null || stack.isEmpty()
                ? MobProgression.EMPTY
                : stack.getOrDefault(ModDataComponents.MOB_PROGRESSION.get(), MobProgression.EMPTY);
    }

    public static int weaponLevel(ItemStack weapon, Entity target) {
        if (!isAwakenedTool(weapon) || !(target instanceof Mob)) {
            return 0;
        }
        MobProgression progression = data(weapon);
        return progression.target().equals(entityId(target))
                ? progression.level(CombatConfig.KILLS_PER_LEVEL.getAsInt(), CombatConfig.MAX_MOB_PROGRESSION_LEVEL.getAsInt())
                : 0;
    }

    public static double weaponDamageMultiplier(ItemStack weapon, Entity target) {
        return 1.0D + weaponLevel(weapon, target) * CombatConfig.WEAPON_DAMAGE_BONUS_PER_LEVEL.getAsDouble();
    }

    public static void recordWeaponKill(ItemStack weapon, LivingEntity target) {
        if (!isAwakenedTool(weapon) || !(target instanceof Mob)) {
            return;
        }
        advance(weapon, entityId(target), CombatConfig.KILLS_PER_LEVEL.getAsInt());
    }

    public static int equippedArmorLevels(LivingEntity wearer, Entity attacker) {
        if (!(attacker instanceof Mob)) {
            return 0;
        }
        String targetId = entityId(attacker);
        int levels = 0;
        for (EquipmentSlot slot : armorSlots()) {
            ItemStack stack = wearer.getItemBySlot(slot);
            MobProgression progression = data(stack);
            if (isAwakenedArmor(stack) && progression.target().equals(targetId)) {
                levels += progression.level(
                        CombatConfig.ARMOR_HITS_PER_LEVEL.getAsInt(),
                        CombatConfig.MAX_MOB_PROGRESSION_LEVEL.getAsInt());
            }
        }
        return levels;
    }

    public static void recordArmorHit(LivingEntity wearer, Entity attacker) {
        if (!(attacker instanceof Mob)) {
            return;
        }
        String targetId = entityId(attacker);
        for (EquipmentSlot slot : armorSlots()) {
            ItemStack stack = wearer.getItemBySlot(slot);
            if (isAwakenedArmor(stack)) {
                advance(stack, targetId, CombatConfig.ARMOR_HITS_PER_LEVEL.getAsInt());
            }
        }
    }

    private static void advance(ItemStack stack, String targetId, int actionsPerLevel) {
        MobProgression current = data(stack);
        MobProgression updated = current.advance(
                targetId, actionsPerLevel, CombatConfig.MAX_MOB_PROGRESSION_LEVEL.getAsInt());
        if (!updated.equals(current)) {
            stack.set(ModDataComponents.MOB_PROGRESSION.get(), updated);
        }
    }

    private static boolean isDiamondWeapon(ItemStack stack) {
        return stack.is(Items.DIAMOND_SWORD) || stack.is(Items.DIAMOND_AXE);
    }

    private static boolean isDiamondArmor(ItemStack stack) {
        return stack.is(Items.DIAMOND_HELMET)
                || stack.is(Items.DIAMOND_CHESTPLATE)
                || stack.is(Items.DIAMOND_LEGGINGS)
                || stack.is(Items.DIAMOND_BOOTS);
    }

    private static Item fusedItem(ItemStack stack) {
        if (stack.is(Items.DIAMOND_SWORD)) return ModItems.ECHO_SWORD.get();
        if (stack.is(Items.DIAMOND_AXE)) return ModItems.ECHO_AXE.get();
        if (stack.is(Items.DIAMOND_HELMET)) return ModItems.ECHO_HELMET.get();
        if (stack.is(Items.DIAMOND_CHESTPLATE)) return ModItems.ECHO_CHESTPLATE.get();
        if (stack.is(Items.DIAMOND_LEGGINGS)) return ModItems.ECHO_LEGGINGS.get();
        if (stack.is(Items.DIAMOND_BOOTS)) return ModItems.ECHO_BOOTS.get();
        return null;
    }

    private static String entityId(Entity entity) {
        return BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()).toString();
    }

    private static EquipmentSlot[] armorSlots() {
        return new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
    }
}
