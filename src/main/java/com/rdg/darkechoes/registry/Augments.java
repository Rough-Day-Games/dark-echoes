package com.rdg.darkechoes.registry;

import com.rdg.darkechoes.DarkEchoes;
import com.rdg.darkechoes.client.ModItemTags;
import com.rdg.darkechoes.helpers.AugmentEffectComponents;
import com.rdg.darkechoes.progression.Augment;
//import com.rdg.darkechoes.registry.augments.Malleable;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Optional;

public class Augments {
    public static final ResourceKey<Augment> MALLEABLE = key("malleable");

    private static ResourceKey<Augment> key(String name) {
        return ResourceKey.create(ModRegistries.AUGMENTS_REGISTRY_KEY, Identifier.fromNamespaceAndPath(DarkEchoes.MOD_ID, name));
    }

    public static void bootstrap(BootstrapContext<Augment> context) {
        HolderGetter<Item> items = context.lookup(Registries.ITEM);
        HolderGetter<Block> blocks = context.lookup(Registries.BLOCK);
        register(
                context,
                MALLEABLE,
                Augment.augment(
                        Augment.definition(
                                items.getOrThrow(ModItemTags.AUGMENTABLE_GEAR),
                                Optional.empty(),
                                ModBlocks.T_ONE_AUGSTATION.get().defaultBlockState()
                        )).withEffect(
                        AugmentEffectComponents.PREVENT_GEAR_BREAK
                )
        );
    }

    private static void register(BootstrapContext<Augment> context, ResourceKey<Augment> key, Augment.Builder builder) {
        context.register(key, builder.build(key.identifier()));
    }
}
