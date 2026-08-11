package com.rdg.darkechoes.helpers;

import com.rdg.darkechoes.DarkEchoes;
import com.rdg.darkechoes.client.menus.BaseAugStationMenu;
import com.rdg.darkechoes.registry.ModDataComponents;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record AugStationData(boolean isFragile, boolean isWeakened,
                             int augmentSlotCount) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<AugStationData> TYPE = new CustomPacketPayload.Type<AugStationData>(Identifier.fromNamespaceAndPath(DarkEchoes.MOD_ID, "aug_station_data"));

    public static final StreamCodec<ByteBuf, AugStationData> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL,
            AugStationData::isFragile,
            ByteBufCodecs.BOOL,
            AugStationData::isWeakened,
            ByteBufCodecs.VAR_INT,
            AugStationData::augmentSlotCount,
            AugStationData::new
    );

    public static void handle(final AugStationData packet, IPayloadContext context) {
        Slot gear_slot = context.player().containerMenu.getSlot(BaseAugStationMenu.GEAR_SLOT_INDEX);
        Slot awaken_item_slot = context.player().containerMenu.getSlot(BaseAugStationMenu.AWAKEN_SLOT_INDEX);
        ItemStack gear = gear_slot.getItem();
        ItemStack awaken_item = awaken_item_slot.getItem();

        gear.set(ModDataComponents.AUGMENT_SLOTS, packet.augmentSlotCount());
        if (packet.isWeakened) {
            gear.set(ModDataComponents.WEAKENED, true);
        } else if (packet.isFragile) {
            gear.set(ModDataComponents.FRAGILE, true);
        }

        if (gear.has(DataComponents.ENCHANTMENTS)) gear.set(DataComponents.ENCHANTMENTS, null);
        if (!awaken_item.isEmpty()) awaken_item.shrink(1);

        gear_slot.setChanged();
        awaken_item_slot.setChanged();
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
