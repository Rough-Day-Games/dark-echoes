package com.rdg.darkechoes.registry;

import com.mojang.serialization.MapCodec;
import com.rdg.darkechoes.DarkEchoes;
import com.rdg.darkechoes.helpers.AugmentProvider;
import com.rdg.darkechoes.helpers.AugmentValueEffect;
import com.rdg.darkechoes.helpers.augment_value_effect.AddValue;
import com.rdg.darkechoes.helpers.augment_value_effect.LevelBasedValue;
import com.rdg.darkechoes.progression.Augment;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.RegistryBuilder;

public class ModRegistries {
    public static final ResourceKey<Registry<Augment>> AUGMENTS_REGISTRY_KEY = ResourceKey.createRegistryKey(Identifier.fromNamespaceAndPath(DarkEchoes.MOD_ID, "augment"));
    public static final ResourceKey<Registry<DataComponentType<?>>> AUGMENT_EFFECT_COMPONENT_TYPE_KEY = ResourceKey.createRegistryKey(Identifier.fromNamespaceAndPath(DarkEchoes.MOD_ID, "augment_effect_component_type"));
    public static final ResourceKey<Registry<MapCodec<? extends AugmentProvider>>> AUGMENT_PROVIDER_TYPE_KEY = ResourceKey.createRegistryKey(Identifier.fromNamespaceAndPath(DarkEchoes.MOD_ID, "augment_provider"));
    public static final ResourceKey<Registry<MapCodec<? extends LevelBasedValue>>> AUGMENT_LEVEL_BASED_VALUE_TYPE_KEY = ResourceKey.createRegistryKey(Identifier.fromNamespaceAndPath(DarkEchoes.MOD_ID, "level_based_value"));
    public static final ResourceKey<Registry<MapCodec<? extends AugmentValueEffect>>> AUGMENT_VALUE_EFFECT_TYPE_KEY = ResourceKey.createRegistryKey(Identifier.fromNamespaceAndPath(DarkEchoes.MOD_ID, "augment_value_effect_type"));
    public static final Registry<DataComponentType<?>> AUGMENT_EFFECT_COMPONENT_TYPE = new RegistryBuilder<>(AUGMENT_EFFECT_COMPONENT_TYPE_KEY)
            .sync(true)
            .defaultKey(Identifier.fromNamespaceAndPath(DarkEchoes.MOD_ID, "none"))
            .create();
    public static final Registry<MapCodec<? extends AugmentProvider>> AUGMENT_PROVIDER_TYPE = new RegistryBuilder<>(AUGMENT_PROVIDER_TYPE_KEY)
            .sync(true)
            .defaultKey(Identifier.fromNamespaceAndPath(DarkEchoes.MOD_ID, "none"))
            .create();
    public static final Registry<MapCodec<? extends LevelBasedValue>> AUGMENT_LEVEL_BASED_VALUE_TYPE = new RegistryBuilder<>(AUGMENT_LEVEL_BASED_VALUE_TYPE_KEY)
            .sync(true)
            .create();
    public static final Registry<MapCodec<? extends AugmentValueEffect>> AUGMENT_VALUE_EFFECT_TYPE = new RegistryBuilder<>(AUGMENT_VALUE_EFFECT_TYPE_KEY)
            .sync(true)
            .defaultKey(Identifier.fromNamespaceAndPath(DarkEchoes.MOD_ID, "none"))
            .create();


    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(AUGMENTS_REGISTRY_KEY, Augments::bootstrap);
//            .add(AUGMENT_LEVEL_BASED_VALUE_TYPE_KEY, bootstrap -> {
//                bootstrap.register(ResourceKey.create(AUGMENT_LEVEL_BASED_VALUE_TYPE_KEY, Identifier.fromNamespaceAndPath(DarkEchoes.MOD_ID, "augment_level_based_value/constant")), LevelBasedValue.Constant.TYPED_CODEC);
//            });
//            .add(AUGMENT_EFFECT_COMPONENT_TYPE_KEY, AugmentEffectComponents::bootstrap);

}
