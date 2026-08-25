package com.rdg.darkechoes.helpers;

import com.rdg.darkechoes.DarkEchoes;
import com.rdg.darkechoes.client.menus.BaseAugStationMenu;
import com.rdg.darkechoes.progression.Augment;
import com.rdg.darkechoes.registry.ModDataComponents;
import com.rdg.darkechoes.registry.ModRegistries;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.Optional;

import static com.rdg.darkechoes.registry.Augments.MALLEABLE;

public record AugStationAugment(boolean active) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<AugStationAugment> TYPE = new Type<>(Identifier.fromNamespaceAndPath(DarkEchoes.MOD_ID, "aug_station_augment"));

    public static final StreamCodec<ByteBuf, AugStationAugment> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL,
            AugStationAugment::active,
            AugStationAugment::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(final AugStationAugment packet, IPayloadContext context) {
        Slot gear_slot = context.player().containerMenu.getSlot(BaseAugStationMenu.GEAR_SLOT_INDEX);
        Slot augment_slot = context.player().containerMenu.getSlot(BaseAugStationMenu.AUGMENT_SLOT_INDEX);

        ItemStack gear = gear_slot.getItem();
        ItemStack augment = augment_slot.getItem();
        GearAugments.Mutable augments = new GearAugments.Mutable(AugmentHelper.getAugments(gear));
        Optional<Holder.Reference<Augment>> malleable = context.player().level().registryAccess().lookupOrThrow(ModRegistries.AUGMENTS_REGISTRY_KEY).get(MALLEABLE);
//        Holder<DataComponentType<?>> malleable = registry.wrapAsHolder(ModDataComponents.MALLEABLE.get());
//        Holder.Reference<DataComponentType<?>> malleable = ModRegistries.AUGMENT_EFFECT_COMPONENT_TYPE.getOrThrow(ModDataComponents.MALLEABLE.getKey());
        augments.set(malleable.orElseThrow());
        gear.set(ModDataComponents.AUGMENTS, augments.toImmutable());

        augment.shrink(1);
    }
}
