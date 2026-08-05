package com.duncanois.darkechoes.registry.blocks;

import com.duncanois.darkechoes.registry.blocks.entities.BaseAugStationBE;
import com.duncanois.darkechoes.registry.blocks.entities.TierOneAugStationBE;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import static com.duncanois.darkechoes.registry.ModBlocks.AUGSTATION_BE;

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
        return new BaseAugStationBE(blockPos, blockState);
    }
}
