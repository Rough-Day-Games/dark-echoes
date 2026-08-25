package com.rdg.darkechoes.progression;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.rdg.darkechoes.helpers.AugmentEffectComponents;
import com.rdg.darkechoes.registry.ModDataComponents;
import com.rdg.darkechoes.registry.ModRegistries;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Unit;
import net.minecraft.util.Util;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.ConditionalEffect;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.jspecify.annotations.NonNull;

import java.util.*;
import java.util.function.UnaryOperator;

public record Augment(Component desc, Augment.AugmentDefinition definition, HolderSet<Augment> exclusive, DataComponentMap effects) {
public static final Codec<Augment> DIRECT_CODEC = RecordCodecBuilder.create(
            inst -> inst.group(
                    ComponentSerialization.CODEC.fieldOf("description").forGetter(Augment::desc),
                    AugmentDefinition.CODEC.forGetter(Augment::definition),
                    RegistryCodecs.homogeneousList(ModRegistries.AUGMENTS_REGISTRY_KEY).optionalFieldOf("exclusive", HolderSet.empty()).forGetter(Augment::exclusive),
                    AugmentEffectComponents.CODEC.optionalFieldOf("effects", DataComponentMap.EMPTY).forGetter(Augment::effects)
            ).apply(inst, Augment::new)
);

    public static final Codec<Holder<Augment>> CODEC = RegistryFixedCodec.create(ModRegistries.AUGMENTS_REGISTRY_KEY);
    public static StreamCodec<RegistryFriendlyByteBuf, Holder<Augment>> STREAM_CODEC = ByteBufCodecs.holderRegistry(ModRegistries.AUGMENTS_REGISTRY_KEY);

    public record AugmentDefinition(
            HolderSet<Item> supportedItems,
            Optional<HolderSet<Item>> primaryItems,
            BlockState requiredAugStationTier
    ) {
        public static final MapCodec<Augment.AugmentDefinition> CODEC = RecordCodecBuilder.mapCodec(
                inst -> inst.group(
                        RegistryCodecs.homogeneousList(Registries.ITEM).fieldOf("supported_items").forGetter(Augment.AugmentDefinition::supportedItems),
                        RegistryCodecs.homogeneousList(Registries.ITEM).optionalFieldOf("primary_items").forGetter(Augment.AugmentDefinition::primaryItems),
                        BlockState.CODEC.fieldOf("required_aug_station_tier").forGetter(Augment.AugmentDefinition::requiredAugStationTier)
                ).apply(inst, Augment.AugmentDefinition::new)
        );
    }

    @Override
    public @NonNull String toString() {
        return "Augment " + this.desc.getString();
    }

    public boolean isSupportedGear(ItemStack gear) {return gear.is(this.definition.supportedItems);}

    public boolean canAugment(ItemStack gear) {return this.definition.supportedItems().contains(gear.typeHolder()) && gear.has(ModDataComponents.AUGMENT_SLOTS);}

//    public void modifyDurability(ServerLevel serverLevel, ItemStack gear) {
//        applyEffects(
//                getEffects(AugmentEffectComponents.PREVENT_GEAR_BREAK),
//                itemContext(serverLevel, gear)
//        );
//    }

    public static <T> void applyEffects(List<ConditionalEffect<T>> effects, LootContext filterData, Augment.GenericAction<T> action) {
        for (ConditionalEffect<T> conditionalEffect : effects) {
            if (conditionalEffect.matches(filterData)) {
                action.apply(conditionalEffect.effect());
            }
        }
    }

    @FunctionalInterface
    private interface GenericAction<T> {
        void apply(T effect);
    }

    public <T> List<T> getEffects(DataComponentType<List<T>> type) {
        return this.effects.getOrDefault(type, List.of());
    }

    public static LootContext itemContext(ServerLevel serverLevel, ItemInstance itemStack) {
        LootParams params = new LootParams.Builder(serverLevel)
                .withParameter(LootContextParams.TOOL, itemStack)
                .create(LootContextParamSets.ALL_PARAMS);
        return new LootContext.Builder(params).create(Optional.empty());
    }

    public static Builder augment(AugmentDefinition definition) {return new Builder(definition);}

    public static AugmentDefinition definition(HolderSet<Item> supportedItems,
                                               Optional<HolderSet<Item>> primaryItems,
                                               BlockState requiredAugStationTier) {
        return new AugmentDefinition(supportedItems, primaryItems, requiredAugStationTier);
    }

    public static class Builder {
        private final AugmentDefinition definition;
        private HolderSet<Augment> exclusive = HolderSet.empty();
        private final Map<DataComponentType<?>, List<?>> effectLists = new HashMap<>();
        private final DataComponentMap.Builder effectMapBuilder = DataComponentMap.builder();
        protected UnaryOperator<MutableComponent> nameFactory = UnaryOperator.identity();

        public Builder(AugmentDefinition definition) {this.definition = definition;}

        public Builder exclusiveWith(HolderSet<Augment> set) {
            this.exclusive = set;
            return this;
        }

        public <E> Builder withEffect(DataComponentType<List<ConditionalEffect<E>>> type, E effect) {
            this.getEffectsList(type).add(new ConditionalEffect<>(effect, Optional.empty()));
            return this;
        }

        public Builder withEffect(DataComponentType<Unit> type) {
            this.effectMapBuilder.set(type, Unit.INSTANCE);
            return this;
        }

        private <E> List getEffectsList(DataComponentType<List<E>> type) {
            return this.effectLists.computeIfAbsent(type, (k) -> {
               ArrayList<E> list = new ArrayList<>();
               this.effectMapBuilder.set(type, list);
               return list;
            });
        }

        public Augment build(Identifier identifierKey) {
            return new Augment(this.nameFactory.apply(Component.translatable(Util.makeDescriptionId("augment", identifierKey))), this.definition, this.exclusive, this.effectMapBuilder.build());
        }
    }
}
