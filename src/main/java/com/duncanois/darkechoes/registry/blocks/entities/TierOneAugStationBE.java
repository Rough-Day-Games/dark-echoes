package com.duncanois.darkechoes.registry.blocks.entities;

import com.duncanois.darkechoes.client.menus.BaseAugStationMenu;
import com.duncanois.darkechoes.client.menus.TierOneAugStationMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;

public class TierOneAugStationBE extends BaseAugStationBE {
    public TierOneAugStationBE(BlockPos worldPosition, BlockState blockState) {
        super(worldPosition, blockState);
    }

    @Override
    protected AbstractContainerMenu createMenu(int i, Inventory inventory) {
        return new TierOneAugStationMenu(i, inventory, this);
    }


}
