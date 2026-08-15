package com.rdg.darkechoes.registry.blocks;

import com.rdg.darkechoes.registry.blocks.entities.BaseAugStationBE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import static com.rdg.darkechoes.registry.ModBlocks.AUGSTATION_BE;
import static com.rdg.darkechoes.registry.blocks.ModBlockStateProperties.AUG_STATION_PIECE;

public abstract class BaseAugStationBlock extends BaseEntityBlock {
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final EnumProperty<AugStationPiece> CORNER = AUG_STATION_PIECE;

    public BaseAugStationBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(CORNER, AugStationPiece.BOTTOM_LEFT));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, CORNER);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        registerDefaultState(stateDefinition.any().setValue(FACING, context.getHorizontalDirection().getOpposite()));
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(CORNER, AugStationPiece.BOTTOM_LEFT);
    }

//    TODO rework shape into 2x2 shape
    @Override
    protected @NonNull VoxelShape getShape(@NonNull BlockState state, @NonNull BlockGetter level, @NonNull BlockPos pos, @NonNull CollisionContext context) {
//        if (state.getValue(FACING) == Direction.EAST) {
//            return Shapes.rotate(Block.box(0f, 0f, 0f, 16f, 32f, 32f), OctahedralGroup.BLOCK_ROT_Y_180);
//        } else if (state.getValue(FACING) == Direction.SOUTH) {
//            return Shapes.rotate(Block.box(0f, 0f, 0f, 16f, 32f, 32f), OctahedralGroup.BLOCK_ROT_Y_270);
//        } else if (state.getValue(FACING) == Direction.NORTH) {
//            return Shapes.rotate(Block.box(0f, 0f, 0f, 16f, 32f, 32f), OctahedralGroup.BLOCK_ROT_Y_90);
//        }
        return Block.box(0f, 0f, 0f, 16f, 16f, 16f);
    }

    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity entity, ItemStack stack) {
        level.setBlock(pos.above(), state.setValue(CORNER, AugStationPiece.TOP_LEFT), 3);
        if (state.getValue(FACING) == Direction.EAST) {
            level.setBlock(pos.offset(0, 0, -1), state.setValue(CORNER, AugStationPiece.BOTTOM_RIGHT), 3);
            level.setBlock(pos.offset(0, 1, -1), state.setValue(CORNER, AugStationPiece.TOP_RIGHT), 3);
        } else if (state.getValue(FACING) == Direction.SOUTH) {
            level.setBlock(pos.offset(1, 0, 0), state.setValue(CORNER, AugStationPiece.BOTTOM_RIGHT), 3);
            level.setBlock(pos.offset(1, 1, 0), state.setValue(CORNER, AugStationPiece.TOP_RIGHT), 3);
        } else if (state.getValue(FACING) == Direction.NORTH) {
            level.setBlock(pos.offset(-1, 0, 0), state.setValue(CORNER, AugStationPiece.BOTTOM_RIGHT), 3);
            level.setBlock(pos.offset(-1, 1, 0), state.setValue(CORNER, AugStationPiece.TOP_RIGHT), 3);
        } else if (state.getValue(FACING) == Direction.WEST) {
            level.setBlock(pos.offset(0, 0, 1), state.setValue(CORNER, AugStationPiece.BOTTOM_RIGHT), 3);
            level.setBlock(pos.offset(0, 1, 1), state.setValue(CORNER, AugStationPiece.TOP_RIGHT), 3);
        }
//        level.setBlock(pos.offset(0, 0, 1), state.setValue(CORNER, AugStationPiece.BOTTOM_RIGHT), 3);
    }

//    TODO if the blockstate is not bottom left, let it open the block entity it creates
    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!level.isClientSide() && player instanceof ServerPlayer serverPlayer) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof BaseAugStationBE be) {
                serverPlayer.openMenu(new SimpleMenuProvider(
                        (containerId, playerInv, _) -> be.createMenu(containerId, playerInv, player), Component.translatable("container.augment_station")
                ), pos);
            }
        }
        return InteractionResult.SUCCESS;
    }

//    TODO WIP, for whatever reason this doesnt work yet
    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos below = pos.below();
        BlockPos left;
        if (state.getValue(FACING) == Direction.EAST) {
            left = pos.offset(0, 0, 1);
        } else if (state.getValue(FACING) == Direction.WEST) {
            left = pos.offset(0, 0, -1);
        } else if (state.getValue(FACING) == Direction.SOUTH) {
            left = pos.offset(-1, 0, 0);
        } else {
            left = pos.offset(1, 0, 0);
        }
        if (state.getValue(CORNER) == AugStationPiece.TOP_LEFT) {
            return level.getBlockState(below).isFaceSturdy(level, below, Direction.UP) || level.getBlockState(below).is(this);
        } else if (state.getValue(CORNER) == AugStationPiece.BOTTOM_RIGHT) {
            return level.getBlockState(left).isFaceSturdy(level, left, Direction.DOWN) || level.getBlockState(left).is(this);
        } else return state.getValue(CORNER) == AugStationPiece.BOTTOM_LEFT;
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> type) {
        return level.isClientSide() ? null : createTickerHelper(type, AUGSTATION_BE.get(), BaseAugStationBE::serverTick);
    }
}
