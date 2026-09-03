package com.rdg.darkechoes.registry;

import com.rdg.darkechoes.DarkEchoes;
import com.rdg.darkechoes.client.ModItemTags;
import com.rdg.darkechoes.helpers.AugmentEffectComponents;
import com.rdg.darkechoes.helpers.augment_value_effect.AddValue;
import com.rdg.darkechoes.helpers.augment_value_effect.LevelBasedValue;
import com.rdg.darkechoes.progression.Augment;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;

import java.util.Optional;

public class Augments {
    public static final ResourceKey<Augment> MALLEABLE = key("malleable");
    public static final ResourceKey<Augment> MAGIC_REBORN = key("magic_reborn");
    public static final ResourceKey<Augment> FLEXIBLE_ADAPTATION = key("flexible_adaptation");

    public static final ResourceKey<Augment> EARTH_SHATTERER = key("earth_shatterer");

    public static final ResourceKey<Augment> HEAVENS =  key("heavens");
    public static final ResourceKey<Augment> ECHO_SENSE =  key("echo_sense");
    public static final ResourceKey<Augment> LOW_GRAVITY = key("low_gravity");

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
                                items.getOrThrow(ModItemTags.MALLEABLE_SOURCES)
                        )
                ).withEffect(
                        AugmentEffectComponents.PREVENT_GEAR_BREAK
                )
        );
        register(
                context,
                MAGIC_REBORN,
                Augment.augment(
                        Augment.definition(
                                items.getOrThrow(ModItemTags.AUGMENTABLE_GEAR),
                                Optional.empty(),
                                items.getOrThrow(ModItemTags.MAGIC_REBORN_SOURCES)
                        )
                ).withEffect(
                        AugmentEffectComponents.ALLOW_ENCHANTS
                )
        );
        register(
                context,
                FLEXIBLE_ADAPTATION,
                Augment.augment(
                        Augment.definition(
                                items.getOrThrow(ModItemTags.AUGMENTABLE_GEAR),
                                Optional.empty(),
                                items.getOrThrow(ModItemTags.FLEXIBLE_SOURCES)
                        )
                ).withEffect(AugmentEffectComponents.EXTRA_ADAPTATION, new AddValue(LevelBasedValue.constant(1)))
        );

        register(
                context,
                EARTH_SHATTERER,
                Augment.augment(
                        Augment.definition(
                                items.getOrThrow(ModItemTags.AUGMENTABLE_TOOL),
                                Optional.empty(),
                                items.getOrThrow(ModItemTags.EARTH_SHATTERER_SOURCES)
                        )
                )
        );

        register(
                context,
                HEAVENS,
                Augment.augment(
                        Augment.definition(
                                items.getOrThrow(ModItemTags.AUGMENTABLE_CHESTPLATES),
                                Optional.empty(),
                                items.getOrThrow(ModItemTags.HEAVENS_AUGMENT_SOURCES)
                        )
                )
        );

        register(
                context,
                ECHO_SENSE,
                Augment.augment(
                        Augment.definition(
                                items.getOrThrow(ModItemTags.ECHO_SENSE_COMPATIBLE),
                                Optional.empty(),
                                items.getOrThrow(ModItemTags.ECHO_SENSE_SOURCES)
                        )
                )
        );

        register(
                context,
                LOW_GRAVITY,
                Augment.augment(
                        Augment.definition(
                                items.getOrThrow(ModItemTags.AUGMENTABLE_BOOTS),
                                Optional.empty(),
                                items.getOrThrow(ModItemTags.LOW_GRAVITY_SOURCES)
                        )
                )
        );
    }

    private static void register(BootstrapContext<Augment> context, ResourceKey<Augment> key, Augment.Builder builder) {
        context.register(key, builder.build(key.identifier()));
    }
}
