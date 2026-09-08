package com.rdg.darkechoes.helpers;

import com.rdg.darkechoes.config.ServerConfig;
import com.rdg.darkechoes.config.CommonConfig;
import net.minecraft.world.item.ItemStack;

import static com.rdg.darkechoes.client.ModItemTags.*;

public class GearHelper {
    public static int assessMaxLevel(ItemStack gear) {
        if (gear.is(AUGMENTABLE_COMBAT_GEAR)) {
            if (gear.is(TIER_ONE_COMBAT_GEAR)) {
                return ServerConfig.MAX_MOB_PROGRESSION_LEVEL_TIER_ONE.getAsInt();
            } else if (gear.is(TIER_TWO_COMBAT_GEAR)) {
                return ServerConfig.MAX_MOB_PROGRESSION_LEVEL_TIER_TWO.getAsInt();
            } else {
                return ServerConfig.MAX_MOB_PROGRESSION_LEVEL_TIER_THREE.getAsInt();
            }
        } else {
            if (gear.is(TIER_ONE_TOOL)) {
                return ServerConfig.MAX_TOOL_PROGRESSION_LEVEL_TIER_ONE.getAsInt();
            } else if (gear.is(TIER_TWO_TOOL)) {
                return ServerConfig.MAX_TOOL_PROGRESSION_LEVEL_TIER_TWO.getAsInt();
            } else {
                return ServerConfig.MAX_TOOL_PROGRESSION_LEVEL_TIER_THREE.getAsInt();
            }
        }
    }

    public static double assessAmendmentPercentage(ItemStack amendmentItem) {
        if (amendmentItem.is(TIER_ONE_MENDER)) {
            return CommonConfig.AMENDMENT_PERCENTAGE_TIER_ONE.getAsDouble();
        } else if (amendmentItem.is(TIER_TWO_MENDER)) {
            return CommonConfig.AMENDMENT_PERCENTAGE_TIER_TWO.getAsDouble();
        } else {
            return CommonConfig.AMENDMENT_PERCENTAGE_TIER_THREE.getAsDouble();
        }
    }
}
