package com.rdg.darkechoes.helpers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.rdg.darkechoes.registry.ModRegistries;
import net.minecraft.world.item.ItemStack;

import java.util.function.Function;

public interface AugmentProvider {
    Codec<AugmentProvider> DIRECT_CODEC = ModRegistries.AUGMENT_PROVIDER_TYPE.byNameCodec().dispatch(AugmentProvider::codec, Function.identity());

    void augment(ItemStack stack, GearAugments.Mutable gearAugments);

    MapCodec<? extends AugmentProvider> codec();
}
