package com.duncanois.darkechoes.registry.blocks.entities;

import com.duncanois.darkechoes.client.menus.TierThreeAugStationMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;

public class TierThreeAugStationBE extends BaseAugStationBE {
    public TierThreeAugStationBE(BlockPos worldPosition, BlockState blockState) {
        super(worldPosition, blockState);
    }

    @Override
    protected AbstractContainerMenu createMenu(int i, Inventory inventory) {
        return new TierThreeAugStationMenu(i, inventory, this);
    }
}
