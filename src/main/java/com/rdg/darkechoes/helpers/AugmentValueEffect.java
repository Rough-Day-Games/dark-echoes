package com.rdg.darkechoes.helpers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.rdg.darkechoes.registry.ModRegistries;

import java.util.function.Function;

public interface AugmentValueEffect {
    Codec<AugmentValueEffect> CODEC = ModRegistries.AUGMENT_VALUE_EFFECT_TYPE.byNameCodec().dispatch(AugmentValueEffect::codec, Function.identity());

    int process(int augmentLevel, int originalValue);

    MapCodec<? extends AugmentValueEffect> codec();
}
