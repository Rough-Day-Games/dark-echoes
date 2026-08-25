package com.rdg.darkechoes.helpers;

import com.mojang.serialization.Codec;
import com.rdg.darkechoes.registry.ModRegistries;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.util.Unit;

import java.util.function.UnaryOperator;

public interface AugmentEffectComponents {
    Codec<DataComponentType<?>> COMPONENT_CODEC = Codec.lazyInitialized(ModRegistries.AUGMENT_EFFECT_COMPONENT_TYPE::byNameCodec);
    Codec<DataComponentMap> CODEC = DataComponentMap.makeCodec(COMPONENT_CODEC);
    DataComponentType<Unit> PREVENT_GEAR_BREAK = register("prevent_gear_break", (b) -> b.persistent(Unit.CODEC));

    private static <T> DataComponentType<T> register(String id, UnaryOperator<DataComponentType.Builder<T>> builder) {
        return Registry.register(ModRegistries.AUGMENT_EFFECT_COMPONENT_TYPE, id, builder.apply(DataComponentType.builder()).build());
    }

    static DataComponentType<?> bootstrap(Registry<DataComponentType<?>> registry) {
        return PREVENT_GEAR_BREAK;
    }
}
