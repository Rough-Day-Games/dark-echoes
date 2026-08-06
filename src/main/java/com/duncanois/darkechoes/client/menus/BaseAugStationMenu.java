package com.duncanois.darkechoes.client.menus;

import com.duncanois.darkechoes.client.ModItemTags;
import com.duncanois.darkechoes.registry.ModDataComponents;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.transfer.item.ItemStackResourceHandler;

import static com.duncanois.darkechoes.client.ModMenus.AUGMENT_STATION_MENU;

public class BaseAugStationMenu extends AbstractContainerMenu {
    public static final int GEAR_SLOT_INDEX = 0;
    public static final int AWAKEN_SLOT_INDEX = 1;
    private final Container augmentStation;
    public final Slot awaken_slot;
    public final Slot gear_slot;

    public BaseAugStationMenu(int containerId, Inventory playerInv) {
        this(containerId, playerInv, new SimpleContainer(2));
    }

    public BaseAugStationMenu(int containerId, Inventory playerInv, Container augmentStation) {
        super(AUGMENT_STATION_MENU.get(), containerId);
        checkContainerSize(augmentStation, 2);
        this.augmentStation = augmentStation;
        this.addSlot(new GearSlot(augmentStation, GEAR_SLOT_INDEX, 68, 60));
        this.addSlot(new AwakenSlot(augmentStation, AWAKEN_SLOT_INDEX, 14, 60));
        this.addStandardInventorySlots(playerInv, 36, 137);
        this.awaken_slot = this.slots.get(AWAKEN_SLOT_INDEX);
        this.gear_slot = this.slots.get(GEAR_SLOT_INDEX);
//        ItemStackResourceHandler
    }

    @Override
    public ItemStack quickMoveStack(Player player, int slotIndex) {
        ItemStack selected = ItemStack.EMPTY;
        Slot slot = this.slots.get(slotIndex);
        if (slot != null && slot.hasItem()) {
            ItemStack stack = slot.getItem();
            selected = stack.copy();
            if (slotIndex == GEAR_SLOT_INDEX || slotIndex == AWAKEN_SLOT_INDEX) {
                if (!this.moveItemStackTo(stack, 3, 37, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (slotIndex >= 2 && slotIndex < 38) {
                if (GearSlot.mayPlaceItem(selected)) {
                    if (!moveItemStackTo(stack, GEAR_SLOT_INDEX, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (AwakenSlot.mayPlaceItem(selected)) {
                    if (!moveItemStackTo(stack, AWAKEN_SLOT_INDEX, 2, false)) {
                        return ItemStack.EMPTY;
                    }
                } else {
                    return ItemStack.EMPTY;
                }
            }
            if (stack.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            slot.onTake(player, stack);
        }

        return selected;
    }

    @Override
    public boolean stillValid(Player player) {
        return augmentStation.stillValid(player);
    }

//    TODO plz fix client desync
    public void awakenGear() {
        if (!awaken_slot.hasItem()) {
            ItemStack awakened_gear = gear_slot.getItem();
                    awakened_gear.set(ModDataComponents.FRAGILE.get(), true);
                    awakened_gear.set(ModDataComponents.AUGMENT_SLOTS.get(), -1);
            augmentStation.setItem(GEAR_SLOT_INDEX, awakened_gear);
            super.slotsChanged(this.augmentStation);
            augmentStation.setChanged();
            gear_slot.setChanged();
        }

    }

    public static class GearSlot extends Slot {
        public GearSlot(Container container, int index, int x, int y) {
            super(container, index, x, y);
        }

        public static boolean mayPlaceItem(ItemStack stack) {
            return true;
        }

        public boolean mayPlace(ItemStack stack) {
            return mayPlaceItem(stack);
        }
    }

    static class AwakenSlot extends Slot {
        public AwakenSlot(Container container, int index, int x, int y) {
            super(container, index, x, y);
        }

        public static boolean mayPlaceItem(ItemStack stack) {
            return stack.is(ModItemTags.AWAKENING_ITEMS);
        }

        public boolean mayPlace(ItemStack stack) {
            return mayPlaceItem(stack);
        }
    }
}
