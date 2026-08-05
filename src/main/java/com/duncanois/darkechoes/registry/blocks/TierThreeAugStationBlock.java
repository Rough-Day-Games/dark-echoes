package com.duncanois.darkechoes.registry.blocks;

import com.duncanois.darkechoes.registry.blocks.entities.BaseAugStationBE;
import com.duncanois.darkechoes.registry.blocks.entities.TierOneAugStationBE;
import com.duncanois.darkechoes.registry.blocks.entities.TierThreeAugStationBE;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jspecify.annotations.Nullable;

import static com.duncanois.darkechoes.registry.ModBlocks.AUGSTATION_BE;

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
