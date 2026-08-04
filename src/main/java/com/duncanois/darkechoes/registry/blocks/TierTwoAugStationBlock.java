package com.duncanois.darkechoes.registry.blocks;

import com.duncanois.darkechoes.registry.blocks.entities.TierOneAugStationBE;
import com.duncanois.darkechoes.registry.blocks.entities.TierTwoAugStationBE;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jspecify.annotations.Nullable;

public class TierTwoAugStationBlock extends BaseAugStationBlock implements EntityBlock {
    public TierTwoAugStationBlock(Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new TierTwoAugStationBE(blockPos, blockState);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!level.isClientSide() && player instanceof ServerPlayer serverPlayer) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof TierTwoAugStationBE be) {
                serverPlayer.openMenu(be);
            }
        }
        return InteractionResult.SUCCESS;
    }
}
