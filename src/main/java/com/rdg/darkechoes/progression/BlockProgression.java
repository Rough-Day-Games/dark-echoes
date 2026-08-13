package com.rdg.darkechoes.progression;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record BlockProgression(String target, int actions, String pendingTarget, int pendingActions) {
    public static final BlockProgression EMPTY = new BlockProgression("", 0, "", 0);
    public static final Codec<BlockProgression> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("target").forGetter(BlockProgression::target),
            Codec.INT.fieldOf("actions").forGetter(BlockProgression::actions),
            Codec.STRING.fieldOf("pending_target").forGetter(BlockProgression::pendingTarget),
            Codec.INT.fieldOf("pending_actions").forGetter(BlockProgression::pendingActions)
    ).apply(instance, BlockProgression::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, BlockProgression> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, BlockProgression::target,
            ByteBufCodecs.VAR_INT, BlockProgression::actions,
            ByteBufCodecs.STRING_UTF8, BlockProgression::pendingTarget,
            ByteBufCodecs.VAR_INT, BlockProgression::pendingActions,
            BlockProgression::new);

    public boolean locked() {
        return !target.isEmpty();
    }

    public int level(int actionsPerLevel, int maxLevel) {
        return Math.min(maxLevel, actions / actionsPerLevel);
    }

    public BlockProgression advance(String block, int actionsPerLevel, int maxLevel) {
        if (locked()) {
            if (!target.equals(block) || level(actionsPerLevel, maxLevel) >= maxLevel) {
                return this;
            }
            return new BlockProgression(target, actions + 1, "", 0);
        }

        int nextPending = pendingTarget.equals(block) ? pendingActions + 1 : 1;
        if (nextPending >= actionsPerLevel) {
            return new BlockProgression(block, actionsPerLevel, "", 0);
        }
        return new BlockProgression("", 0, block, nextPending);
    }
}
