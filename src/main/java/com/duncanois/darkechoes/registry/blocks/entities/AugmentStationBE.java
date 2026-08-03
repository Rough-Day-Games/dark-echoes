package com.duncanois.darkechoes.registry.blocks.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import static com.duncanois.darkechoes.registry.ModBlocks.AUGMENTSTATION_BE;

public class AugmentStationBE extends BlockEntity {
    public AugmentStationBE(BlockPos worldPosition, BlockState blockState) {
        super(AUGMENTSTATION_BE.get(), worldPosition, blockState);
    }
}
