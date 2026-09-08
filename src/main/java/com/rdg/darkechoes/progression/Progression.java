package com.rdg.darkechoes.progression;

import com.rdg.darkechoes.client.ModItemTags;
import com.rdg.darkechoes.config.ServerConfig;
import com.rdg.darkechoes.registry.ModDataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;

import static com.rdg.darkechoes.helpers.GearHelper.assessMaxLevel;

public final class Progression {
    private Progression() {
    }

    public static boolean isAwakened(ItemStack stack) {
        return isAwakenedTool(stack) || isAwakenedArmor(stack) || isAwakenedCombatWeapon(stack);
    }

    public static boolean isAwakenedTool(ItemStack stack) {
        return stack != null && stack.has(ModDataComponents.AUGMENT_SLOTS) && stack.is(ModItemTags.AUGMENTABLE_TOOL);
    }

    public static boolean isAwakenedCombatWeapon(ItemStack stack) {
        return stack != null && stack.has(ModDataComponents.AUGMENT_SLOTS) && stack.is(ModItemTags.AUGMENTABLE_WEAPON);
    }

    public static boolean isCombatGear(ItemStack stack) {
        return stack != null && stack.is(ModItemTags.AUGMENTABLE_GEAR) && !ToolProgression.isProgressionTool(stack);
    }

    public static boolean isAwakenedArmor(ItemStack stack) {
        return stack != null && stack.has(ModDataComponents.AUGMENT_SLOTS) && stack.is(ModItemTags.AUGMENTABLE_ARMOR);
    }

    public static MobProgression data(ItemStack stack) {
        return stack == null || stack.isEmpty()
                ? MobProgression.EMPTY
                : stack.getOrDefault(ModDataComponents.MOB_PROGRESSION.get(), MobProgression.EMPTY);
    }

    public static int weaponLevel(ItemStack weapon, Entity target) {
        if (!isAwakenedCombatWeapon(weapon) || !(target instanceof Mob)) {
            return 0;
        }
        int maxLevelProgression = assessMaxLevel(weapon);
        MobProgression progression = data(weapon);
        return progression.target().equals(entityId(target))
                ? progression.level(ServerConfig.KILLS_PER_LEVEL.getAsInt(), maxLevelProgression)
                : 0;
    }

    public static double weaponDamageMultiplier(ItemStack weapon, Entity target) {
        return 1.0D + weaponLevel(weapon, target) * ServerConfig.WEAPON_DAMAGE_BONUS_PER_LEVEL.getAsDouble();
    }

    public static void recordWeaponKill(ItemStack weapon, LivingEntity target) {
        if (!isAwakenedCombatWeapon(weapon) || !(target instanceof Mob)) {
            return;
        }
        advance(weapon, entityId(target), ServerConfig.KILLS_PER_LEVEL.getAsInt());
    }

    public static int equippedArmorLevels(LivingEntity wearer, Entity attacker) {
        if (!(attacker instanceof Mob)) {
            return 0;
        }
        String targetId = entityId(attacker);
        int levels = 0;
        for (EquipmentSlot slot : armorSlots()) {
            ItemStack stack = wearer.getItemBySlot(slot);
            int maxLevelProgression = assessMaxLevel(stack);
            MobProgression progression = data(stack);
            if (isAwakenedArmor(stack) && progression.target().equals(targetId)) {
                levels += progression.level(
                        ServerConfig.ARMOR_HITS_PER_LEVEL.getAsInt(),
                        maxLevelProgression);
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
                advance(stack, targetId, ServerConfig.ARMOR_HITS_PER_LEVEL.getAsInt());
            }
        }
    }

    private static void advance(ItemStack stack, String targetId, int actionsPerLevel) {
        int maxLevelProgression = assessMaxLevel(stack);
        MobProgression current = data(stack);
        MobProgression updated = current.advance(
                targetId, actionsPerLevel, maxLevelProgression, current.slots());
        if (!updated.equals(current)) {
            stack.set(ModDataComponents.MOB_PROGRESSION.get(), updated);
        }
    }

    private static String entityId(Entity entity) {
        return BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()).toString();
    }

    private static EquipmentSlot[] armorSlots() {
        return new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
    }
}
