package com.rdg.darkechoes.helpers;

import com.rdg.darkechoes.DarkEchoes;
import com.rdg.darkechoes.client.menus.BaseAugStationMenu;
import com.rdg.darkechoes.registry.ModDataComponents;
//import com.rdg.darkechoes.registry.augments.Malleable;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record AugStationAwaken(boolean isFragile, boolean isWeakened,
                               int augmentSlotCount) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<AugStationAwaken> TYPE = new CustomPacketPayload.Type<AugStationAwaken>(Identifier.fromNamespaceAndPath(DarkEchoes.MOD_ID, "aug_station_awaken"));

    public static final StreamCodec<ByteBuf, AugStationAwaken> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL,
            AugStationAwaken::isFragile,
            ByteBufCodecs.BOOL,
            AugStationAwaken::isWeakened,
            ByteBufCodecs.VAR_INT,
            AugStationAwaken::augmentSlotCount,
            AugStationAwaken::new
    );

    public static void handle(final AugStationAwaken packet, IPayloadContext context) {
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

//        TODO after augment that allows gear to be enchanted is implemented, check here
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
