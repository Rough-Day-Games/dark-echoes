package com.rdg.darkechoes.helpers.augment_value_effect;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.rdg.darkechoes.helpers.AugmentValueEffect;
import com.rdg.darkechoes.registry.ModRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.neoforge.registries.DeferredHolder;

public record AddValue(LevelBasedValue value) implements AugmentValueEffect {
    public static final MapCodec<AddValue> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(LevelBasedValue.CODEC.fieldOf("value").forGetter(AddValue::value)).apply(i, AddValue::new));

    @Override
    public int process(int augmentLevel, int originalValue) {
        return originalValue + this.value.calculate(augmentLevel);
    }

    @Override
    public MapCodec<? extends AugmentValueEffect> codec() {
        return CODEC;
    }
}
