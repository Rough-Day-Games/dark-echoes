package com.rdg.darkechoes.registry.blocks;

import com.rdg.darkechoes.registry.blocks.entities.BaseAugStationBE;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

public class TierThreeAugStationBlock extends BaseAugStationBlock implements EntityBlock {
    public TierThreeAugStationBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(TierThreeAugStationBlock::new);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new BaseAugStationBE(blockPos, blockState);
    }
}
