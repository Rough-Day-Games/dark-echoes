package com.duncanois.darkechoes.progression;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record MobProgression(String target, int actions, String pendingTarget, int pendingActions) {
    public static final MobProgression EMPTY = new MobProgression("", 0, "", 0);
    public static final Codec<MobProgression> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.optionalFieldOf("target", "").forGetter(MobProgression::target),
            Codec.intRange(0, Integer.MAX_VALUE).optionalFieldOf("actions", 0).forGetter(MobProgression::actions),
            Codec.STRING.optionalFieldOf("pending_target", "").forGetter(MobProgression::pendingTarget),
            Codec.intRange(0, Integer.MAX_VALUE).optionalFieldOf("pending_actions", 0).forGetter(MobProgression::pendingActions)
    ).apply(instance, MobProgression::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, MobProgression> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, MobProgression::target,
            ByteBufCodecs.VAR_INT, MobProgression::actions,
            ByteBufCodecs.STRING_UTF8, MobProgression::pendingTarget,
            ByteBufCodecs.VAR_INT, MobProgression::pendingActions,
            MobProgression::new);

    public boolean locked() {
        return !target.isEmpty();
    }

    public int level(int actionsPerLevel, int maxLevel) {
        return Math.min(actions / actionsPerLevel, maxLevel);
    }

    public MobProgression advance(String mob, int actionsPerLevel, int maxLevel) {
        int maximumActions = actionsPerLevel * maxLevel;
        if (locked()) {
            if (!target.equals(mob) || actions >= maximumActions) {
                return this;
            }
            return new MobProgression(target, actions + 1, "", 0);
        }

        int nextPending = pendingTarget.equals(mob) ? pendingActions + 1 : 1;
        if (nextPending >= actionsPerLevel) {
            return new MobProgression(mob, actionsPerLevel, "", 0);
        }
        return new MobProgression("", 0, mob, nextPending);
    }
}

