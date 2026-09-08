package com.rdg.darkechoes.helpers;

import com.rdg.darkechoes.DarkEchoes;
import com.rdg.darkechoes.client.menus.BaseAugStationMenu;
import com.rdg.darkechoes.progression.Augment;
import com.rdg.darkechoes.registry.Augments;
import com.rdg.darkechoes.registry.ModDataComponents;
import com.rdg.darkechoes.registry.ModRegistries;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Unit;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantable;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static com.rdg.darkechoes.helpers.AugmentHelper.getAugmentToAdd;
import static com.rdg.darkechoes.registry.Augments.MALLEABLE;

public record AugStationAugment(ItemStack augmentSource) implements CustomPacketPayload {
    public static final Type<AugStationAugment> TYPE = new Type<>(Identifier.fromNamespaceAndPath(DarkEchoes.MOD_ID, "aug_station_augment"));

    public static final StreamCodec<RegistryFriendlyByteBuf, AugStationAugment> STREAM_CODEC = StreamCodec.composite(
            ItemStack.STREAM_CODEC,
            AugStationAugment::augmentSource,
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
        ItemStack augmentItem = augment_slot.getItem();

        Optional<Holder.Reference<Augment>> augment = getAugmentToAdd(context.player().level().registryAccess(), gear, packet.augmentSource());

        GearAugments.Mutable augments = new GearAugments.Mutable(AugmentHelper.getAugments(gear));
        augments.set(augment.orElseThrow());
        gear.set(ModDataComponents.AUGMENTS, augments.toImmutable());
        gear.set(ModDataComponents.AUGMENT_SLOTS, gear.get(ModDataComponents.AUGMENT_SLOTS) - 1);
        if (augment.get().is(Augments.MAGIC_REBORN)) gear.set(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY);
        if (augment.get().is(Augments.HEAVENS)) gear.set(DataComponents.GLIDER, Unit.INSTANCE);

        augmentItem.shrink(1);
    }
}
