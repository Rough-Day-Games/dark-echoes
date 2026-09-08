package com.rdg.darkechoes.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class CommonConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.IntValue INITIAL_ADAPTATION_SLOT_TIER_ONE;
    public static final ModConfigSpec.IntValue INITIAL_ADAPTATION_SLOT_TIER_TWO;
    public static final ModConfigSpec.IntValue INITIAL_ADAPTATION_SLOT_TIER_THREE;
    public static final ModConfigSpec.IntValue MAX_AUGMENT_SLOTS_TIER_ONE;
    public static final ModConfigSpec.IntValue MAX_AUGMENT_SLOTS_TIER_TWO;
    public static final ModConfigSpec.IntValue MAX_AUGMENT_SLOTS_TIER_THREE;
    public static final ModConfigSpec.DoubleValue AMENDMENT_PERCENTAGE_TIER_ONE;
    public static final ModConfigSpec.DoubleValue AMENDMENT_PERCENTAGE_TIER_TWO;
    public static final ModConfigSpec.DoubleValue AMENDMENT_PERCENTAGE_TIER_THREE;
    public static final ModConfigSpec SPEC;

    static {
        BUILDER.push("common");

        INITIAL_ADAPTATION_SLOT_TIER_ONE = BUILDER
                .comment("Initial adaptation slots gained after awakening tier one gear.")
                .defineInRange("initialAdaptationSlotsTier1", 3, 1, Integer.MAX_VALUE);

        INITIAL_ADAPTATION_SLOT_TIER_TWO = BUILDER
                .comment("Initial adaptation slots gained after awakening tier two gear.")
                .defineInRange("initialAdaptationSlotsTier2", 5, 1, Integer.MAX_VALUE);

        INITIAL_ADAPTATION_SLOT_TIER_THREE = BUILDER
                .comment("Initial adaptation slots gained after awakening tier three gear.")
                .defineInRange("initialAdaptationSlotsTier3", 7, 1, Integer.MAX_VALUE);

        MAX_AUGMENT_SLOTS_TIER_ONE = BUILDER
                .comment("The max count of augment slots for tier one gear.")
                .defineInRange("maxAugmentSlotsTier1", 3, -1, 100);

        MAX_AUGMENT_SLOTS_TIER_TWO = BUILDER
                .comment("The max count of augment slots for tier two gear.")
                .defineInRange("maxAugmentSlotsTier2", 6, -1, 100);

        MAX_AUGMENT_SLOTS_TIER_THREE = BUILDER
                .comment("The max count of augment slots for tier three gear.")
                .defineInRange("maxAugmentSlotsTier3", 10, -1, 100);

        AMENDMENT_PERCENTAGE_TIER_ONE = BUILDER
                .comment("Percentage of gear durability increased after amending with tier one amendment items, based on the gear's max damage value.")
                .defineInRange("amendmentPercentageTier1", 0.25D, 0.01D, 1D);

        AMENDMENT_PERCENTAGE_TIER_TWO = BUILDER
                .comment("Percentage of gear durability increased after amending with tier two amendment items, based on the gear's max damage value.")
                .defineInRange("amendmentPercentageTier2", 0.50D, 0.01D, 1D);

        AMENDMENT_PERCENTAGE_TIER_THREE = BUILDER
                .comment("Percentage of gear durability increased after amending with tier three amendment items, based on the gear's max damage value.")
                .defineInRange("amendmentPercentageTier3", 0.75D, 0.01D, 1D);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
