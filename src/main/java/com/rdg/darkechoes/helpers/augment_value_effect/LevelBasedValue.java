package com.rdg.darkechoes.helpers.augment_value_effect;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.rdg.darkechoes.registry.ModRegistries;

public interface LevelBasedValue {
    Codec<LevelBasedValue> DISPATCH_CODEC = ModRegistries.AUGMENT_LEVEL_BASED_VALUE_TYPE.byNameCodec().dispatch(LevelBasedValue::codec, (c) -> c);
    Codec<LevelBasedValue> CODEC = Codec.either(LevelBasedValue.Constant.CODEC, DISPATCH_CODEC).xmap((either) -> either.map((l) -> l, (r) -> r), (levelBasedValue) -> {
        Either<Constant, LevelBasedValue> value;
        if (levelBasedValue instanceof Constant constant) {
            value = Either.left(constant);
        } else {
            value = Either.right(levelBasedValue);
        }
        return value;
    });

    static Constant constant(int value) {return new Constant(value);}

    static DefinedPerTier definedPerTier(int tierOneValue, int tierTwoValue, int tierThreeValue) {return new DefinedPerTier(tierOneValue, tierTwoValue, tierThreeValue);}

    int calculate(int originalValue);

    MapCodec<? extends LevelBasedValue> codec();

    record Constant(int value) implements LevelBasedValue {
        public static final Codec<Constant> CODEC;
        public static final MapCodec<Constant> TYPED_CODEC;

        public int calculate(int level) {return this.value + level;}

        public MapCodec<Constant> codec() {return TYPED_CODEC;}

        static {
            CODEC = Codec.INT.xmap(Constant::new, Constant::value);
            TYPED_CODEC = RecordCodecBuilder.mapCodec((i) -> i.group(Codec.INT.fieldOf("value").forGetter(Constant::value)).apply(i, Constant::new));
        }
    }

    record DefinedPerTier(int tierOneValue, int tierTwoValue, int tierThreeValue) implements LevelBasedValue {
        public static final MapCodec<DefinedPerTier> CODEC = RecordCodecBuilder.mapCodec((i) -> i.group(Codec.INT.fieldOf("tier_one_value").forGetter(DefinedPerTier::tierOneValue), Codec.INT.fieldOf("tier_two_value").forGetter(DefinedPerTier::tierTwoValue), Codec.INT.fieldOf("tier_three_value").forGetter(DefinedPerTier::tierThreeValue)).apply(i, DefinedPerTier::new));

        @Override
        public int calculate(int originalValue) {
            return this.tierOneValue + this.tierTwoValue * (originalValue - 1);
        }

        @Override
        public MapCodec<DefinedPerTier> codec() {
            return CODEC;
        }
    }
}
