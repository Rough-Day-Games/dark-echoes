package com.rdg.darkechoes.helpers;

import com.mojang.serialization.Codec;
import com.rdg.darkechoes.progression.Augment;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.Set;

public class GearAugments {
    public static final GearAugments EMPTY = new GearAugments(new Object2BooleanOpenHashMap<>());
    public static final Codec<GearAugments> CODEC;
    public static final StreamCodec<RegistryFriendlyByteBuf, GearAugments> STREAM_CODEC;
    private final Object2BooleanOpenHashMap<Holder<Augment>> augments;

    static {
        CODEC = Codec.unboundedMap(Augment.CODEC, Codec.BOOL).xmap((map) -> new GearAugments(new Object2BooleanOpenHashMap<>(map)), (augments) -> augments.augments);
        STREAM_CODEC = StreamCodec.composite(ByteBufCodecs.map(Object2BooleanOpenHashMap::new, Augment.STREAM_CODEC, ByteBufCodecs.BOOL), (a) -> a.augments, GearAugments::new);
    }

    @Override
    public int hashCode() {
        return this.augments.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else {
            boolean value;
            if (obj instanceof GearAugments) {
                GearAugments that = (GearAugments) obj;
                value = this.augments.equals(that.augments);
            } else {
                value = false;
            }

            return value;
        }
    }

    @Override
    public String toString() {
        return "GearAugments{augments=" + String.valueOf(this.augments) + "}";
    }

    private GearAugments(Object2BooleanOpenHashMap<Holder<Augment>> augments) {
        this.augments = augments;

        augments.object2BooleanEntrySet();
    }

    public static class Mutable {
        private final Object2BooleanOpenHashMap<Holder<Augment>> augments = new Object2BooleanOpenHashMap<>();

        public Mutable(GearAugments augments) {this.augments.putAll(augments.augments);}

        public void set(Holder<Augment> augment) {
            if (this.augments.containsKey(augment)) {
                this.augments.removeBoolean(augment);
            } else {
                this.augments.put(augment, true);
            }
        }

        public Set<Holder<Augment>> augments() {return  this.augments.keySet();}

        public GearAugments toImmutable() {return new GearAugments(this.augments);}
    }
}
