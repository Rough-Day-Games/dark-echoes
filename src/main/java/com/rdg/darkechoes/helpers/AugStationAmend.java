package com.rdg.darkechoes.helpers;

import com.rdg.darkechoes.DarkEchoes;
import com.rdg.darkechoes.client.menus.BaseAugStationMenu;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import static com.rdg.darkechoes.helpers.GearHelper.assessAmendmentPercentage;

public record AugStationAmend(ItemStack stack) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<AugStationAmend> TYPE = new Type<>(Identifier.fromNamespaceAndPath(DarkEchoes.MOD_ID, "aug_station_amend"));

    public static final StreamCodec<RegistryFriendlyByteBuf, AugStationAmend> STREAM_CODEC = StreamCodec.composite(
            ItemStack.STREAM_CODEC,
            AugStationAmend::stack,
            AugStationAmend::new
    );

    public static void handle(final AugStationAmend packet, IPayloadContext context) {
        Slot gear_slot = context.player().containerMenu.getSlot(BaseAugStationMenu.GEAR_SLOT_INDEX);
        Slot amend_slot = context.player().containerMenu.getSlot(BaseAugStationMenu.AMEND_SLOT_INDEX);

        ItemStack gear = gear_slot.getItem();
        ItemStack amend = amend_slot.getItem();

        double amendPercentage = assessAmendmentPercentage(packet.stack);
        int finalCalc = (int) Math.round(gear.getMaxDamage() * amendPercentage);
        if (gear.getDamageValue() >= gear.getMaxDamage()) {
            gear.setDamageValue(gear.getMaxDamage() - finalCalc);
        } else {
            gear.setDamageValue((gear.getDamageValue()) - finalCalc);
        }

        amend.shrink(1);

        gear_slot.setChanged();
        amend_slot.setChanged();
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
