package com.rdg.darkechoes.helpers;

import com.rdg.darkechoes.progression.Augment;
import com.rdg.darkechoes.registry.ModDataComponents;
import com.rdg.darkechoes.registry.ModRegistries;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.CommonHooks;
import org.apache.commons.lang3.mutable.MutableBoolean;

import java.util.Map;
import java.util.Optional;

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

    public static HolderSet<Item> getAugmentSourceOfAugment(RegistryAccess access, ItemStack augmentSource) {
        for (Map.Entry<ResourceKey<Augment>, Augment> augment : access.lookupOrThrow(ModRegistries.AUGMENTS_REGISTRY_KEY).entrySet()) {
            if (augmentSource.is(augment.getValue().definition().augmentSource())) return augment.getValue().definition().augmentSource();
        }
        return HolderSet.empty();
    }

    public static Component getAugmentName(RegistryAccess access, ItemStack augmentSource) {
        for (Map.Entry<ResourceKey<Augment>, Augment> augment : access.lookupOrThrow(ModRegistries.AUGMENTS_REGISTRY_KEY).entrySet()) {
            if (augment.getValue().definition().augmentSource().contains(augmentSource.typeHolder())) {
                return augment.getValue().desc();
            }
        }
        return Component.empty();
    }

    public static Optional<Holder.Reference<Augment>> getAugmentToAdd(RegistryAccess access, ItemStack gear, ItemStack augmentSource) {
        HolderLookup.RegistryLookup<Augment> augmentLookup = access.lookupOrThrow(ModRegistries.AUGMENTS_REGISTRY_KEY);
        for (Map.Entry<ResourceKey<Augment>, Augment> augment : access.lookupOrThrow(ModRegistries.AUGMENTS_REGISTRY_KEY).entrySet()) {
            if (augment.getValue().canAugment(gear, augmentSource)) {
                return augmentLookup.get(augment.getKey());
            }
        }
        return Optional.empty();
    }

    @FunctionalInterface
    public interface AugmentVisitor {
        void accept(Holder<Augment> holder, boolean active);
    }
}
