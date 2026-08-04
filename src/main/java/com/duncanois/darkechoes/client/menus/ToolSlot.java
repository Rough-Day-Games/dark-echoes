package com.duncanois.darkechoes.client.menus;

import com.duncanois.darkechoes.client.ModItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

public class ToolSlot extends Slot {
    private static TagKey<Item> tier;
    public ToolSlot(Container container, int index, int x, int y, TagKey<Item> tier) {
        super(container, index, x, y);
        ToolSlot.tier = tier;
    }

    public static boolean mayPlaceItem(ItemStack stack) {
        return stack.is(tier);
    }

    public boolean mayPlace(ItemStack stack) {
        return mayPlaceItem(stack);
    }
}
