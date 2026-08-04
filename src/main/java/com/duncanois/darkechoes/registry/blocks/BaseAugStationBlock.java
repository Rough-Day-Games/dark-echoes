package com.duncanois.darkechoes.registry.blocks;

import com.mojang.math.OctahedralGroup;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import static com.duncanois.darkechoes.registry.ModBlocks.AUGMENT_STATION_CODEC;

public class BaseAugStationBlock extends Block {
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;

    public BaseAugStationBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected @NonNull MapCodec<? extends Block> codec() {
        return AUGMENT_STATION_CODEC.get();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        registerDefaultState(stateDefinition.any().setValue(FACING, context.getHorizontalDirection().getOpposite()));
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected @NonNull VoxelShape getShape(@NonNull BlockState state, @NonNull BlockGetter level, @NonNull BlockPos pos, @NonNull CollisionContext context) {
        if (state.getValue(FACING) == Direction.EAST) {
            return Shapes.rotate(Block.box(0f, 0f, 0f, 16f, 32f, 32f), OctahedralGroup.BLOCK_ROT_Y_180);
        } else if (state.getValue(FACING) == Direction.SOUTH) {
            return Shapes.rotate(Block.box(0f, 0f, 0f, 16f, 32f, 32f), OctahedralGroup.BLOCK_ROT_Y_270);
        } else if (state.getValue(FACING) == Direction.NORTH) {
            return Shapes.rotate(Block.box(0f, 0f, 0f, 16f, 32f, 32f), OctahedralGroup.BLOCK_ROT_Y_90);
        }
        return Block.box(0f, 0f, 0f, 16f, 32f, 32f);
    }

//    TODO rework into multiblock
//    @Override
//    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
//        BlockPos above = pos.above();
//        BlockPos right = pos.relative(Direction.EAST);
//        BlockPos top_right = right.above();
//        return Block.box(0f, 0f, 0f, 8f, 16f, 16f);
//    }
}
