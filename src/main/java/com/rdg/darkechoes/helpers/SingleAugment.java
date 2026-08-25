package com.rdg.darkechoes.helpers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.rdg.darkechoes.progression.Augment;
import net.minecraft.core.Holder;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.item.ItemStack;

public record SingleAugment(Holder<Augment> augment, boolean active) implements AugmentProvider {
    public static final MapCodec<SingleAugment> CODEC = RecordCodecBuilder.mapCodec((i) -> i.group(Augment.CODEC.fieldOf("augment").forGetter(SingleAugment::augment), Codec.BOOL.fieldOf("active").forGetter(SingleAugment::active)).apply(i, SingleAugment::new));
    @Override
    public void augment(ItemStack stack, GearAugments.Mutable gearAugments) {
        gearAugments.set(this.augment);
    }

    @Override
    public MapCodec<SingleAugment> codec() {
        return CODEC;
    }
}
