package com.rdg.darkechoes.registry.blocks;

import com.rdg.darkechoes.registry.blocks.entities.BaseAugStationBE;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import static com.rdg.darkechoes.registry.blocks.ModBlockStateProperties.AUG_STATION_PIECE;

public class TierOneAugStationBlock extends BaseAugStationBlock {
    public TierOneAugStationBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseAugStationBlock> codec() {
        return simpleCodec(TierOneAugStationBlock::new);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NonNull BlockPos blockPos, @NonNull BlockState blockState) {
        BaseAugStationBE newBlockEntity = new BaseAugStationBE(blockPos, blockState);
        if (blockState.getValue(AUG_STATION_PIECE) == AugStationPiece.BOTTOM_LEFT) {
            return newBlockEntity;
        } else  {
            return null;
        }
    }
}
