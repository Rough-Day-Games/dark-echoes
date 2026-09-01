package com.rdg.darkechoes.registry;

import com.mojang.serialization.Codec;
import com.rdg.darkechoes.DarkEchoes;
import com.rdg.darkechoes.helpers.GearAugments;
import com.rdg.darkechoes.progression.BlockProgression;
import com.rdg.darkechoes.progression.MobProgression;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModDataComponents {
    public static final DeferredRegister.DataComponents COMPONENTS =
            DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, DarkEchoes.MOD_ID);
    public static final DeferredRegister.DataComponents AUGMENT_COMPONENT_TYPES =
            DeferredRegister.createDataComponents(ModRegistries.AUGMENT_EFFECT_COMPONENT_TYPE_KEY, DarkEchoes.MOD_ID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> PROGRESSION_LEVEL =
            COMPONENTS.registerComponentType("progression_level", builder -> builder
                    .persistent(Codec.intRange(0, 100))
                    .networkSynchronized(ByteBufCodecs.VAR_INT));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<MobProgression>> MOB_PROGRESSION =
            COMPONENTS.registerComponentType("mob_progression", builder -> builder
                    .persistent(MobProgression.CODEC)
                    .networkSynchronized(MobProgression.STREAM_CODEC));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<BlockProgression>> BLOCK_PROGRESSION =
            COMPONENTS.registerComponentType("block_progression", builder -> builder
                    .persistent(BlockProgression.CODEC)
                    .networkSynchronized(BlockProgression.STREAM_CODEC));
//    TODO validate augment slots when adding augments
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> AUGMENT_SLOTS =
            COMPONENTS.registerComponentType("augment_slots", builder -> builder
                    .persistent(Codec.intRange(-1, 999))
                    .networkSynchronized(ByteBufCodecs.VAR_INT));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> FRAGILE =
            COMPONENTS.registerComponentType("fragile", builder -> builder
                    .persistent(Codec.BOOL));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> WEAKENED =
            COMPONENTS.registerComponentType("weakened", builder -> builder
                    .persistent(Codec.BOOL));


    public static final DeferredHolder<DataComponentType<?>, DataComponentType<GearAugments>> AUGMENTS =
            COMPONENTS.registerComponentType("augments", b -> b.persistent(GearAugments.CODEC).networkSynchronized(GearAugments.STREAM_CODEC).cacheEncoding());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> MALLEABLE =
            AUGMENT_COMPONENT_TYPES.registerComponentType("malleable",
                    builder -> builder.persistent(Codec.BOOL));

    private ModDataComponents() {
    }
}
