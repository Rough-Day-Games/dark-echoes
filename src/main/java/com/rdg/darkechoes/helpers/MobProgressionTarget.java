package com.rdg.darkechoes.helpers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record MobProgressionTarget(boolean isPending, int actions) {
    public static final Codec<MobProgressionTarget> CODEC;
    public static final StreamCodec<RegistryFriendlyByteBuf, MobProgressionTarget> STREAM_CODEC;

    static {
        CODEC = RecordCodecBuilder.create(i -> i.group(
                Codec.BOOL.fieldOf("isPending").forGetter(MobProgressionTarget::isPending),
                Codec.INT.fieldOf("actions").forGetter(MobProgressionTarget::actions)
        ).apply(i, MobProgressionTarget::new));
        STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.BOOL, MobProgressionTarget::isPending,
                ByteBufCodecs.VAR_INT, MobProgressionTarget::actions,
                MobProgressionTarget::new
        );
    }
}
