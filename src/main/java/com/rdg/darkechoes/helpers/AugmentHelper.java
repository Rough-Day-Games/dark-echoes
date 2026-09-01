package com.rdg.darkechoes.helpers;

import com.rdg.darkechoes.progression.Augment;
import com.rdg.darkechoes.registry.ModDataComponents;
import com.rdg.darkechoes.registry.ModRegistries;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.CommonHooks;
import org.apache.commons.lang3.mutable.MutableBoolean;

public class AugmentHelper {
    public static void setAugments(ItemStack itemStack, GearAugments augments) {
        itemStack.set(ModDataComponents.AUGMENTS, augments);
    }

    public static GearAugments getAugments(ItemStack gear) {
        return gear.getOrDefault(ModDataComponents.AUGMENTS, GearAugments.EMPTY);
    }

    public static boolean has(ItemStack itemStack, DataComponentType<?> effect) {
        MutableBoolean found = new MutableBoolean(false);
        runIterationOnItem(itemStack, (augment, active) -> {
            if (augment.value().effects().has(effect)) {
                found.setTrue();
            }
        });

        return found.booleanValue();
    }

    public static void runIterationOnItem(ItemStack gear, AugmentVisitor method) {
        GearAugments augments = getAugments(gear);
        HolderLookup.RegistryLookup<Augment> lookup = CommonHooks.resolveLookup(ModRegistries.AUGMENTS_REGISTRY_KEY);

        for (Object2BooleanOpenHashMap.Entry<Holder<Augment>> entry : augments.entrySet()) {
            method.accept(entry.getKey(), entry.getBooleanValue());
        }
    }

    public static void getAllAugments(HolderLookup.RegistryLookup<Augment> augments) {

    }

    @FunctionalInterface
    public interface AugmentVisitor {
        void accept(Holder<Augment> holder, boolean active);
    }
}
