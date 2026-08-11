package com.rdg.darkechoes.helpers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.neoforge.common.MutableDataComponentHolder;
import org.jspecify.annotations.Nullable;

//public class FragileDataHolder implements MutableDataComponentHolder {
//    public static final Codec<FragileDataHolder> CODEC = RecordCodecBuilder.create(instance -> {
//        instance.group(
//                Codec.BOOL.fieldOf("fragile").forGetter(FragileDataHolder::getData)
//        ).apply(instance, FragileDataHolder::new);
//    });
//
//    public static final StreamCodec<RegistryFriendlyByteBuf, FragileDataHolder> STREAM_CODEC = StreamCodec.composite(
//            ByteBufCodecs.BOOL, FragileDataHolder::getData,
//            FragileDataHolder::new
//    )
//
//    @Override
//    public @Nullable <T> T set(DataComponentType<T> componentType, @Nullable T value) {
//        return null;
//    }
//
//    @Override
//    public @Nullable <T> T remove(DataComponentType<? extends T> componentType) {
//        return null;
//    }
//
//    @Override
//    public void applyComponents(DataComponentPatch patch) {
//
//    }
//
//    @Override
//    public void applyComponents(DataComponentMap components) {
//
//    }
//
//    @Override
//    public DataComponentMap getComponents() {
//        return null;
//    }
//}
