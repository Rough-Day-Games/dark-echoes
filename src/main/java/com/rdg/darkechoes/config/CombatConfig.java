package com.rdg.darkechoes.config;

import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;

public final class CombatConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.ConfigValue<List<? extends String>> INCOMING_DAMAGE_MULTIPLIERS;
    public static final ModConfigSpec.ConfigValue<List<? extends String>> OUTGOING_DAMAGE_MULTIPLIERS;
    public static final ModConfigSpec.ConfigValue<List<? extends String>> ITEM_DAMAGE_MULTIPLIERS;
    public static final ModConfigSpec.IntValue MAX_MOB_PROGRESSION_LEVEL;
    public static final ModConfigSpec.IntValue KILLS_PER_LEVEL;
    public static final ModConfigSpec.IntValue ARMOR_HITS_PER_LEVEL;
    public static final ModConfigSpec.DoubleValue WEAPON_DAMAGE_BONUS_PER_LEVEL;
    public static final ModConfigSpec.DoubleValue ARMOR_REDUCTION_BONUS_PER_LEVEL;
    public static final ModConfigSpec.BooleanValue DEBUG_LOGGING;
    public static final ModConfigSpec SPEC;

    static {
        BUILDER.push("combat");

        INCOMING_DAMAGE_MULTIPLIERS = BUILDER
                .comment("Damage received: target selector|damage source selector=multiplier",
                        "Selectors accept an id, #tag, or *. Example: minecraft:skeleton|#minecraft:is_projectile=0.75")
                .defineListAllowEmpty("incomingDamageMultipliers", List.of(), () -> "", CombatConfig::isDamageRule);

        OUTGOING_DAMAGE_MULTIPLIERS = BUILDER
                .comment("Damage dealt: attacker selector|damage source selector=multiplier",
                        "Example: minecraft:skeleton|minecraft:arrow=1.20")
                .defineListAllowEmpty("outgoingDamageMultipliers", List.of(), () -> "", CombatConfig::isDamageRule);

        ITEM_DAMAGE_MULTIPLIERS = BUILDER
                .comment("Damage dealt with an item: item id or #item tag=multiplier",
                        "Example: minecraft:diamond_sword=1.10")
                .defineListAllowEmpty("itemDamageMultipliers", List.of(), () -> "", CombatConfig::isItemRule);

        MAX_MOB_PROGRESSION_LEVEL = BUILDER
                .comment("Maximum mob-specific progression level.")
                .defineInRange("maxMobProgressionLevel", 20, 1, 100);

        KILLS_PER_LEVEL = BUILDER
                .comment("Matching mob kills required for each fused weapon level.")
                .defineInRange("killsPerLevel", 2, 1, 10000);

        ARMOR_HITS_PER_LEVEL = BUILDER
                .comment("Matching mob hits received for each fused armor level.")
                .defineInRange("armorHitsPerLevel", 2, 1, 10000);

        WEAPON_DAMAGE_BONUS_PER_LEVEL = BUILDER
                .comment("Fractional outgoing damage bonus per mob-specific weapon level. 0.05 is 5%.")
                .defineInRange("weaponDamageBonusPerLevel", 0.05D, 0.0D, 10.0D);

        ARMOR_REDUCTION_BONUS_PER_LEVEL = BUILDER
                .comment("Fractional bonus to vanilla armor reduction per matching equipped armor level. 0.05 is 5%.")
                .defineInRange("armorReductionBonusPerLevel", 0.05D, 0.0D, 10.0D);

        DEBUG_LOGGING = BUILDER
                .comment("Log damage multiplier and progression decisions.")
                .define("debugLogging", false);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }

    private CombatConfig() {
    }

    private static boolean isDamageRule(Object value) {
        if (!(value instanceof String rule)) {
            return false;
        }
        int equals = rule.lastIndexOf('=');
        int separator = rule.indexOf('|');
        return separator > 0 && separator < equals - 1 && validMultiplier(rule, equals);
    }

    private static boolean isItemRule(Object value) {
        if (!(value instanceof String rule)) {
            return false;
        }
        int equals = rule.lastIndexOf('=');
        return equals > 0 && validMultiplier(rule, equals);
    }

    private static boolean validMultiplier(String rule, int equals) {
        try {
            double multiplier = Double.parseDouble(rule.substring(equals + 1).trim());
            return Double.isFinite(multiplier) && multiplier >= 0.0D;
        } catch (NumberFormatException ignored) {
            return false;
        }
    }
}
