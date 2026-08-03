package com.duncanois.darkechoes.registry.blocks.entities;

import com.duncanois.darkechoes.registry.blocks.AugmentStations;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class TierOneAugStation extends AugmentStations implements EntityBlock {
    public TierOneAugStation(Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NonNull BlockPos blockPos, @NonNull BlockState blockState) {
        return new AugmentStationBE(blockPos, blockState);
    }
}
