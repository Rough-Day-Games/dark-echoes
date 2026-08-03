package com.duncanois.darkechoes.registry.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.NonNull;

import static com.duncanois.darkechoes.registry.ModBlocks.AUGMENT_STATION_CODEC;

public class AugmentStations extends Block {
    public AugmentStations(Properties properties) {
        super(properties);
    }

    @Override
    protected @NonNull MapCodec<? extends Block> codec() {
        return AUGMENT_STATION_CODEC.get();
    }

    @Override
    protected @NonNull InteractionResult useWithoutItem(@NonNull BlockState state, @NonNull Level level, @NonNull BlockPos pos, Player player, @NonNull BlockHitResult hitResult) {
        if (player.getMainHandItem() == ItemStack.EMPTY) {
            level.playLocalSound(pos, SoundEvents.SCULK_SHRIEKER_SHRIEK, SoundSource.BLOCKS, 1.0f, 1.0f, true);
            return InteractionResult.PASS;
        }
        return InteractionResult.TRY_WITH_EMPTY_HAND;
//        return super.useWithoutItem(state, level, pos, player, hitResult);
    }

    @Override
    protected @NonNull VoxelShape getShape(@NonNull BlockState state, @NonNull BlockGetter level, @NonNull BlockPos pos, @NonNull CollisionContext context) {
        return Block.box(0f, 0f, 0f, 16f, 32f, 32f);
//        return super.getShape(state, level, pos, context);
    }
}
