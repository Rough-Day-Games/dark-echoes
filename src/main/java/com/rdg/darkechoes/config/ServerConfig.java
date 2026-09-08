package com.rdg.darkechoes.config;

import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;

public final class ServerConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    // Combat
    public static final ModConfigSpec.ConfigValue<List<? extends String>> INCOMING_DAMAGE_MULTIPLIERS;
    public static final ModConfigSpec.ConfigValue<List<? extends String>> OUTGOING_DAMAGE_MULTIPLIERS;
    public static final ModConfigSpec.ConfigValue<List<? extends String>> ITEM_DAMAGE_MULTIPLIERS;
    public static final ModConfigSpec.IntValue MAX_MOB_PROGRESSION_LEVEL_TIER_ONE;
    public static final ModConfigSpec.IntValue MAX_MOB_PROGRESSION_LEVEL_TIER_TWO;
    public static final ModConfigSpec.IntValue MAX_MOB_PROGRESSION_LEVEL_TIER_THREE;
    public static final ModConfigSpec.IntValue KILLS_PER_LEVEL;
    public static final ModConfigSpec.IntValue ARMOR_HITS_PER_LEVEL;
    public static final ModConfigSpec.DoubleValue WEAPON_DAMAGE_BONUS_PER_LEVEL;
    public static final ModConfigSpec.DoubleValue ARMOR_REDUCTION_BONUS_PER_LEVEL;

    // Tools
    public static final ModConfigSpec.IntValue MAX_TOOL_PROGRESSION_LEVEL_TIER_ONE;
    public static final ModConfigSpec.IntValue MAX_TOOL_PROGRESSION_LEVEL_TIER_TWO;
    public static final ModConfigSpec.IntValue MAX_TOOL_PROGRESSION_LEVEL_TIER_THREE;
    public static final ModConfigSpec.IntValue TOOL_BLOCKS_PER_LEVEL;
    public static final ModConfigSpec.DoubleValue TOOL_MINING_SPEED_BONUS_PER_LEVEL;
    public static final ModConfigSpec.BooleanValue DEBUG_LOGGING;
    public static final ModConfigSpec SPEC;

    static {
        BUILDER.push("combat");

        MAX_MOB_PROGRESSION_LEVEL_TIER_ONE = BUILDER
                .comment("Maximum mob-specific progression level for tier three combat gear.")
                .defineInRange("maxMobProgressionLevelTier1", 3, 1, 100);

        MAX_MOB_PROGRESSION_LEVEL_TIER_TWO = BUILDER
                .comment("Maximum mob-specific progression level for tier two combat gear.")
                .defineInRange("maxMobProgressionLevelTier2", 6, 1, 100);

        MAX_MOB_PROGRESSION_LEVEL_TIER_THREE = BUILDER
                .comment("Maximum mob-specific progression level for tier three combat gear.")
                .defineInRange("maxMobProgressionLevelTier3", 10, 1, 100);

        INCOMING_DAMAGE_MULTIPLIERS = BUILDER
                .comment("Damage received: target selector|damage source selector=multiplier",
                        "Selectors accept an id, #tag, or *. Example: minecraft:skeleton|#minecraft:is_projectile=0.75")
                .defineListAllowEmpty("incomingDamageMultipliers", List.of(), () -> "", ServerConfig::isDamageRule);

        OUTGOING_DAMAGE_MULTIPLIERS = BUILDER
                .comment("Damage dealt: attacker selector|damage source selector=multiplier",
                        "Example: minecraft:skeleton|minecraft:arrow=1.20")
                .defineListAllowEmpty("outgoingDamageMultipliers", List.of(), () -> "", ServerConfig::isDamageRule);

        ITEM_DAMAGE_MULTIPLIERS = BUILDER
                .comment("Damage dealt with an item: item id or #item tag=multiplier",
                        "Example: minecraft:diamond_sword=1.10")
                .defineListAllowEmpty("itemDamageMultipliers", List.of(), () -> "", ServerConfig::isItemRule);

        KILLS_PER_LEVEL = BUILDER
                .comment("Matching mob kills required for each awakened weapon level.")
                .defineInRange("killsPerLevel", 100, 1, 10000);

        ARMOR_HITS_PER_LEVEL = BUILDER
                .comment("Matching mob hits received for each awakened armor level.")
                .defineInRange("armorHitsPerLevel", 100, 1, 10000);

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

        BUILDER.push("toolProgression");

        MAX_TOOL_PROGRESSION_LEVEL_TIER_ONE = BUILDER
                .comment("Maximum block-specific progression level for tier one awakened tools.")
                .defineInRange("maxToolProgressionLevelTier1", 3, 1, 100);

        MAX_TOOL_PROGRESSION_LEVEL_TIER_TWO = BUILDER
                .comment("Maximum block-specific progression level for tier two awakened tools.")
                .defineInRange("maxToolProgressionLevelTier2", 6, 1, 100);

        MAX_TOOL_PROGRESSION_LEVEL_TIER_THREE = BUILDER
                .comment("Maximum block-specific progression level for tier three awakened tools.")
                .defineInRange("maxToolProgressionLevelTier3", 10, 1, 100);

        TOOL_BLOCKS_PER_LEVEL = BUILDER
                .comment("Matching block breaks required for each awakened tool level.")
                .defineInRange("toolBlocksPerLevel", 100, 1, 100000);

        TOOL_MINING_SPEED_BONUS_PER_LEVEL = BUILDER
                .comment("Fractional mining speed bonus per matching awakened tool level. 0.05 is 5%.")
                .defineInRange("toolMiningSpeedBonusPerLevel", 0.05D, 0.0D, 10.0D);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }

    private ServerConfig() {
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
