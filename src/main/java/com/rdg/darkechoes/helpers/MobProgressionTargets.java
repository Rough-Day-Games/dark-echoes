package com.rdg.darkechoes.helpers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.rdg.darkechoes.progression.MobProgression;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record MobProgressionTargets(String target, MobProgression progression) {
//    public static final Codec<MobProgressionTargets> CODEC;
//    public static final StreamCodec<RegistryFriendlyByteBuf, MobProgressionTargets> STREAM_CODEC;
//
//    static {
//        CODEC = RecordCodecBuilder.create(instance -> instance.group(
//                Codec.STRING.optionalFieldOf("target", "").forGetter(MobProgressionTargets::target),
//                MobProgression.CODEC.optionalFieldOf("progression", MobProgression.EMPTY).forGetter(MobProgressionTargets::progression)
//        ).apply(instance, MobProgressionTargets::new));
//        STREAM_CODEC = StreamCodec.composite(
//                ByteBufCodecs.STRING_UTF8, MobProgressionTargets::target,
//                MobProgression.STREAM_CODEC, MobProgressionTargets::progression,
//                MobProgressionTargets::new
//        );
//    }

//    public static final MobProgressions EMPTY = new MobProgressions();
//    public static final Codec<MobProgressions> CODEC;
//    public static final StreamCodec<String, MobProgression> STREAM_CODEC;
//    private final Object2ObjectOpenHashMap<String, MobProgression> progressions = new Object2ObjectOpenHashMap<>();
//
//    static {
//        CODEC = Codec.unboundedMap(Codec.STRING, MobProgression.CODEC).xmap((map) -> new MobProgressions(new Object2ObjectOpenHashMap<>(map)), (progressions) -> progressions.progressions);
//        STREAM_CODEC = StreamCodec.composite(ByteBufCodecs.map(new Object2ObjectOpenHashMap<String, MobProgression>(), ByteBufCodecs.STRING_UTF8, MobProgression.STREAM_CODEC))
//    }
//
//    private MobProgressions(Object2ObjectOpenHashMap<String, MobProgression> progressions) {
//        this.progressions.putAll(progressions);
//
//        progressions.object2ObjectEntrySet();
//    }
}
