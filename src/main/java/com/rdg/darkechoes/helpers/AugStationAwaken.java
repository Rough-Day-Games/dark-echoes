package com.rdg.darkechoes.helpers;

import com.rdg.darkechoes.DarkEchoes;
import com.rdg.darkechoes.client.ModItemTags;
import com.rdg.darkechoes.client.menus.BaseAugStationMenu;
import com.rdg.darkechoes.config.CommonConfig;
import com.rdg.darkechoes.progression.BlockProgression;
import com.rdg.darkechoes.progression.MobProgression;
import com.rdg.darkechoes.registry.ModDataComponents;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import static com.rdg.darkechoes.progression.Progression.isCombatGear;
import static com.rdg.darkechoes.progression.ToolProgression.isTool;

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

        if (isTool(gear) && !gear.has(ModDataComponents.BLOCK_PROGRESSION)) {
            gear.set(ModDataComponents.BLOCK_PROGRESSION, new BlockProgression("", 0, "", 0, gear.is(ModItemTags.TIER_ONE_GEAR) ? CommonConfig.INITIAL_ADAPTATION_SLOT_TIER_ONE.getAsInt() : (gear.is(ModItemTags.TIER_TWO_GEAR) ? CommonConfig.INITIAL_ADAPTATION_SLOT_TIER_TWO.getAsInt() : (gear.is(ModItemTags.TIER_THREE_GEAR) ? CommonConfig.INITIAL_ADAPTATION_SLOT_TIER_THREE.getAsInt() : 0))));
        } else if (isCombatGear(gear) && !gear.has(ModDataComponents.MOB_PROGRESSION)) {
            gear.set(ModDataComponents.MOB_PROGRESSION, new MobProgression("", 0, "", 0, gear.is(ModItemTags.TIER_ONE_GEAR) ? CommonConfig.INITIAL_ADAPTATION_SLOT_TIER_ONE.getAsInt() : (gear.is(ModItemTags.TIER_TWO_GEAR) ? CommonConfig.INITIAL_ADAPTATION_SLOT_TIER_TWO.getAsInt() : (gear.is(ModItemTags.TIER_THREE_GEAR) ? CommonConfig.INITIAL_ADAPTATION_SLOT_TIER_THREE.getAsInt() : 0))));
        }
        gear.set(ModDataComponents.AUGMENT_SLOTS, packet.augmentSlotCount());
        if (packet.isWeakened) {
            gear.set(ModDataComponents.WEAKENED, true);
        } else if (packet.isFragile) {
            gear.set(ModDataComponents.FRAGILE, true);
        }

        if (gear.has(DataComponents.ENCHANTMENTS) && AugmentHelper.has(gear, AugmentEffectComponents.ALLOW_ENCHANTS)) {
            ItemEnchantments enchants = EnchantmentHelper.getEnchantmentsForCrafting(gear);
            DarkEchoes.LOGGER.info("gear got enchantments and has allow enchants");
            if (enchants != ItemEnchantments.EMPTY) {
                gear.set(DataComponents.ENCHANTMENTS, new ItemEnchantments.Mutable(enchants).toImmutable());
            } else {
                gear.set(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY);
            }
        } else if (gear.has(DataComponents.ENCHANTMENTS)) {
            DarkEchoes.LOGGER.info("gear got enchantments, will delete");
            gear.set(DataComponents.ENCHANTMENTS, null);
        }

        if (!awaken_item.isEmpty()) awaken_item.shrink(1);

        gear_slot.setChanged();
        awaken_item_slot.setChanged();
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
