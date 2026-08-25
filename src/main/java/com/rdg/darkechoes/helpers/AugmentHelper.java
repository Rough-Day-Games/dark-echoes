package com.rdg.darkechoes.helpers;

import com.rdg.darkechoes.registry.ModDataComponents;
import net.minecraft.world.item.ItemStack;

public class AugmentHelper {
    public static void setAugments(ItemStack itemStack, GearAugments augments) {
        itemStack.set(ModDataComponents.AUGMENTS, augments);
    }

    public static GearAugments getAugments(ItemStack gear) {
        return gear.getOrDefault(ModDataComponents.AUGMENTS, GearAugments.EMPTY);
    }
}
