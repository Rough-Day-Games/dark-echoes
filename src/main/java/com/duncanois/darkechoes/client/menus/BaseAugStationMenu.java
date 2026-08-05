package com.duncanois.darkechoes.client.menus;

import com.duncanois.darkechoes.client.ModItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import static com.duncanois.darkechoes.client.ModMenus.AUGMENT_STATION_MENU;

public class BaseAugStationMenu extends AbstractContainerMenu {
    public static final int TOOL_SLOT = 0;
    public static final int AWAKEN_SLOT = 1;
    private final Container augmentStation;

    public BaseAugStationMenu(int containerId, Inventory playerInv) {
        this(containerId, playerInv, new SimpleContainer(2));
    }

    public BaseAugStationMenu(int containerId, Inventory playerInv, Container augmentStation) {
        super(AUGMENT_STATION_MENU.get(), containerId);
        checkContainerSize(augmentStation, 2);
        this.augmentStation = augmentStation;
        this.addSlot(new ToolSlot(augmentStation, TOOL_SLOT, 68, 60));
        this.addSlot(new AwakenSlot(augmentStation, AWAKEN_SLOT, 14, 60));
        this.addStandardInventorySlots(playerInv, 36, 137);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int slotIndex) {
        ItemStack selected = ItemStack.EMPTY;
        Slot slot = this.slots.get(slotIndex);
        if (slot != null && slot.hasItem()) {
            ItemStack stack = slot.getItem();
            selected = stack.copy();
            if (slotIndex == TOOL_SLOT || slotIndex == AWAKEN_SLOT) {
                if (!this.moveItemStackTo(stack, 3, 37, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (slotIndex >= 2 && slotIndex < 38) {
                if (augmentStation.canPlaceItem(TOOL_SLOT, stack)) {
//                    TODO add better check on this, echo sword can be quick moved to below tier three but flashes for a milisecond
                    if (!moveItemStackTo(stack, TOOL_SLOT, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (AwakenSlot.mayPlaceItem(selected)) {
                    if (!moveItemStackTo(stack, AWAKEN_SLOT, 2, false)) {
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

    public static class ToolSlot extends Slot {
        public ToolSlot(Container container, int index, int x, int y) {
            super(container, index, x, y);
        }

        public static boolean mayPlaceItem(ItemStack stack) {
            return stack.is(ModItemTags.AUGMENTABLE_GEAR);
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
