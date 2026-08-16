package com.rdg.darkechoes.registry.blocks;

import com.mojang.math.OctahedralGroup;
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
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
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

    @Override
    protected @NonNull VoxelShape getShape(@NonNull BlockState state, @NonNull BlockGetter level, @NonNull BlockPos pos, @NonNull CollisionContext context) {
        if (state.getValue(FACING) == Direction.EAST) {
            if (state.getValue(CORNER) == AugStationPiece.TOP_LEFT) {
                return Shapes.rotate(Block.box(2f, -16f, 0f, 16f, 16f, 32f), OctahedralGroup.BLOCK_ROT_Y_180);
            } else if (state.getValue(CORNER) == AugStationPiece.BOTTOM_RIGHT) {
                return Shapes.rotate(Block.box(2f, 0f, -16f, 16f, 32f, 16f), OctahedralGroup.BLOCK_ROT_Y_180);
            } else if (state.getValue(CORNER) == AugStationPiece.TOP_RIGHT) {
                return Shapes.rotate(Block.box(2f, -16f, -16f, 16f, 16f, 16f), OctahedralGroup.BLOCK_ROT_Y_180);
            } else return Shapes.rotate(Block.box(2f, 0f, 0f, 16f, 32f, 32f), OctahedralGroup.BLOCK_ROT_Y_180);
        } else if (state.getValue(FACING) == Direction.SOUTH) {
            if (state.getValue(CORNER) == AugStationPiece.TOP_LEFT) {
                return Shapes.rotate(Block.box(2f, -16f, 0f, 16f, 16f, 32f), OctahedralGroup.BLOCK_ROT_Y_270);
            } else if (state.getValue(CORNER) == AugStationPiece.BOTTOM_RIGHT) {
                return Shapes.rotate(Block.box(2f, 0f, -16f, 16f, 32f, 16f), OctahedralGroup.BLOCK_ROT_Y_270);
            } else if (state.getValue(CORNER) == AugStationPiece.TOP_RIGHT) {
                return Shapes.rotate(Block.box(2f, -16f, -16f, 16f, 16f, 16f), OctahedralGroup.BLOCK_ROT_Y_270);
            } else return Shapes.rotate(Block.box(2f, 0f, 0f, 16f, 32f, 32f), OctahedralGroup.BLOCK_ROT_Y_270);
        } else if (state.getValue(FACING) == Direction.NORTH) {
            if (state.getValue(CORNER) == AugStationPiece.TOP_LEFT) {
                return Shapes.rotate(Block.box(2f, -16f, 0f, 16f, 16f, 32f), OctahedralGroup.BLOCK_ROT_Y_90);
            } else if (state.getValue(CORNER) == AugStationPiece.BOTTOM_RIGHT) {
                return Shapes.rotate(Block.box(2f, 0f, -16f, 16f, 32f, 16f), OctahedralGroup.BLOCK_ROT_Y_90);
            } else if (state.getValue(CORNER) == AugStationPiece.TOP_RIGHT) {
                return Shapes.rotate(Block.box(2f, -16f, -16f, 16f, 16f, 16f), OctahedralGroup.BLOCK_ROT_Y_90);
            } else return Shapes.rotate(Block.box(2f, 0f, 0f, 16f, 32f, 32f), OctahedralGroup.BLOCK_ROT_Y_90);
        } else {
            if (state.getValue(CORNER) == AugStationPiece.TOP_LEFT) {
                return Block.box(2f, -16f, 0f, 16f, 16f, 32f);
            } else if (state.getValue(CORNER) == AugStationPiece.BOTTOM_RIGHT) {
                return Block.box(2f, 0f, -16f, 16f, 32f, 16f);
            } else if (state.getValue(CORNER) == AugStationPiece.TOP_RIGHT) {
                return Block.box(2f, -16f, -16f, 16f, 16f, 16f);
            } else return Block.box(2f, 0f, 0f, 16f, 32f, 32f);
        }
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
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!level.isClientSide() && player instanceof ServerPlayer serverPlayer) {
            if (state.getValue(AUG_STATION_PIECE) == AugStationPiece.BOTTOM_LEFT) {
                BlockEntity blockEntity = level.getBlockEntity(pos);
                if (blockEntity instanceof BaseAugStationBE be) {
                    serverPlayer.openMenu(new SimpleMenuProvider(
                            (containerId, playerInv, _) -> be.createMenu(containerId, playerInv, player), Component.translatable("container.augment_station")
                    ), pos);
                }
            } else {
                BlockPos below = pos.below();
                BlockPos left = getBlockFromPos(state.getValue(FACING), pos, AugStationPiece.BOTTOM_RIGHT, AugStationPiece.BOTTOM_LEFT);
                BlockPos bottomLeft = getBlockFromPos(state.getValue(FACING), pos, AugStationPiece.TOP_RIGHT, AugStationPiece.BOTTOM_LEFT);
                if (state.getValue(CORNER) == AugStationPiece.BOTTOM_RIGHT) {
                    BlockEntity blockEntity = level.getBlockEntity(left);
                    if (blockEntity instanceof BaseAugStationBE be) {
                        serverPlayer.openMenu(new SimpleMenuProvider(
                                (containerId, playerInv, _) -> be.createMenu(containerId, playerInv, player), Component.translatable("container.augment_station")
                        ), left);
                    }
                } else if (state.getValue(CORNER) == AugStationPiece.TOP_LEFT) {
                    BlockEntity blockEntity = level.getBlockEntity(below);
                    if (blockEntity instanceof BaseAugStationBE be) {
                        serverPlayer.openMenu(new SimpleMenuProvider(
                                (containerId, playerInv, _) -> be.createMenu(containerId, playerInv, player), Component.translatable("container.augment_station")
                        ), below);
                    }
                } else {
                    BlockEntity blockEntity = level.getBlockEntity(bottomLeft);
                    if (blockEntity instanceof BaseAugStationBE be) {
                        serverPlayer.openMenu(new SimpleMenuProvider(
                                (containerId, playerInv, _) -> be.createMenu(containerId, playerInv, player), Component.translatable("container.augment_station")
                        ), bottomLeft);
                    }
                }
            }
        }
        return InteractionResult.SUCCESS;
    }

    public BlockPos getBlockFromPos(Direction direction, BlockPos current, AugStationPiece currentPiece, AugStationPiece desiredPiece) {
        if (currentPiece == AugStationPiece.BOTTOM_LEFT) {
            if (desiredPiece == AugStationPiece.TOP_RIGHT) {
                if (direction == Direction.EAST) {
                    return current.offset(0, 1, -1);
                } else if (direction == Direction.WEST) {
                    return current.offset(0, 1, 1);
                } else if (direction == Direction.SOUTH) {
                    return current.offset(1, 1, 0);
                } else {
                    return current.offset(-1, 1, 0);
                }
            } else if (desiredPiece == AugStationPiece.BOTTOM_RIGHT) {
                if (direction == Direction.EAST) {
                    return current.offset(0, 0, -1);
                } else if (direction == Direction.WEST) {
                    return current.offset(0, 0, 1);
                } else if (direction == Direction.SOUTH) {
                    return current.offset(1, 0, 0);
                } else {
                    return current.offset(-1, 0, 0);
                }
            } else return current.above();
        } else if (currentPiece == AugStationPiece.BOTTOM_RIGHT) {
            if (desiredPiece == AugStationPiece.BOTTOM_LEFT) {
                if (direction == Direction.EAST) {
                    return current.offset(0, 0, 1);
                } else if (direction == Direction.WEST) {
                    return current.offset(0, 0, -1);
                } else if (direction == Direction.SOUTH) {
                    return current.offset(-1, 0, 0);
                } else {
                    return current.offset(1, 0, 0);
                }
            } else if (desiredPiece == AugStationPiece.TOP_LEFT) {
                if (direction == Direction.EAST) {
                    return current.offset(0, 1, 1);
                } else if (direction == Direction.WEST) {
                    return current.offset(0, 1, -1);
                } else if (direction == Direction.SOUTH) {
                    return current.offset(-1, 1, 0);
                } else {
                    return current.offset(1, 1, 0);
                }
            } else return current.above();
        } else if (currentPiece == AugStationPiece.TOP_RIGHT) {
            if (desiredPiece == AugStationPiece.BOTTOM_LEFT) {
                if (direction == Direction.EAST) {
                    return current.offset(0, -1, 1);
                } else if (direction == Direction.WEST) {
                    return current.offset(0, -1, -1);
                } else if (direction == Direction.SOUTH) {
                    return current.offset(-1, -1, 0);
                } else {
                    return current.offset(1, -1, 0);
                }
            } else if (desiredPiece == AugStationPiece.TOP_LEFT) {
                if (direction == Direction.EAST) {
                    return current.offset(0, 0, 1);
                } else if (direction == Direction.WEST) {
                    return current.offset(0, 0, -1);
                } else if (direction == Direction.SOUTH) {
                    return current.offset(-1, 0, 0);
                } else {
                    return current.offset(1, 0, 0);
                }
            } else return current.below();
        } else {
            if (desiredPiece == AugStationPiece.TOP_RIGHT) {
                if (direction == Direction.EAST) {
                    return current.offset(0, 0, -1);
                } else if (direction == Direction.WEST) {
                    return current.offset(0, 0, 1);
                } else if (direction == Direction.SOUTH) {
                    return current.offset(1, 0, 0);
                } else {
                    return current.offset(-1, 0, 0);
                }
            } else if (desiredPiece == AugStationPiece.BOTTOM_RIGHT) {
                if (direction == Direction.EAST) {
                    return current.offset(0, -1, -1);
                } else if (direction == Direction.WEST) {
                    return current.offset(0, -1, 1);
                } else if (direction == Direction.SOUTH) {
                    return current.offset(1, -1, 0);
                } else {
                    return current.offset(-1, -1, 0);
                }
            } else return current.below();
        }
    }

    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide()) {
            BlockPos above;
            BlockPos below;
            BlockPos left;
            BlockPos right;
            BlockPos bottomLeft;
            BlockPos bottomRight;
            BlockPos topLeft;
            BlockPos topRight;

            BlockState aboveState;
            BlockState belowState;
            BlockState leftState;
            BlockState rightState;
            BlockState bottomLeftState;
            BlockState bottomRightState;
            BlockState topLeftState;
            BlockState topRightState;

            if (state.getValue(CORNER) == AugStationPiece.TOP_LEFT) {
                below = pos.below();
                belowState = level.getBlockState(below);
                bottomRight = getBlockFromPos(state.getValue(FACING), pos, AugStationPiece.TOP_LEFT, AugStationPiece.BOTTOM_RIGHT);
                bottomRightState = level.getBlockState(bottomRight);
                right = getBlockFromPos(state.getValue(FACING), pos, AugStationPiece.TOP_LEFT, AugStationPiece.TOP_RIGHT);
                rightState = level.getBlockState(right);

                if (belowState.is(this) && belowState.getValue(AUG_STATION_PIECE) == AugStationPiece.BOTTOM_LEFT) {
                    level.setBlock(below, Blocks.AIR.defaultBlockState(), 35);
                    level.levelEvent(player, 2001, below, Block.getId(belowState));
                }

                if (bottomRightState.is(this) && bottomRightState.getValue(AUG_STATION_PIECE) == AugStationPiece.BOTTOM_RIGHT) {
                    level.setBlock(bottomRight, Blocks.AIR.defaultBlockState(), 35);
                    level.levelEvent(player, 2001, bottomRight, Block.getId(bottomRightState));
                }

                if (rightState.is(this) && rightState.getValue(AUG_STATION_PIECE) == AugStationPiece.TOP_RIGHT) {
                    level.setBlock(right, Blocks.AIR.defaultBlockState(), 35);
                    level.levelEvent(player, 2001, right, Block.getId(rightState));
                }
            } else if (state.getValue(CORNER) == AugStationPiece.BOTTOM_RIGHT) {
                left = getBlockFromPos(state.getValue(FACING), pos, AugStationPiece.BOTTOM_RIGHT, AugStationPiece.BOTTOM_LEFT);
                leftState = level.getBlockState(left);
                above = pos.above();
                aboveState = level.getBlockState(above);
                topLeft = getBlockFromPos(state.getValue(FACING), pos, AugStationPiece.BOTTOM_RIGHT, AugStationPiece.TOP_LEFT);
                topLeftState = level.getBlockState(topLeft);

                if (leftState.is(this) && leftState.getValue(AUG_STATION_PIECE) == AugStationPiece.BOTTOM_LEFT) {
                    level.setBlock(left, Blocks.AIR.defaultBlockState(), 35);
                    level.levelEvent(player, 2001, left, Block.getId(leftState));
                }

                if (aboveState.is(this) && aboveState.getValue(AUG_STATION_PIECE) == AugStationPiece.TOP_RIGHT) {
                    level.setBlock(above, Blocks.AIR.defaultBlockState(), 35);
                    level.levelEvent(player, 2001, above, Block.getId(aboveState));
                }

                if (topLeftState.is(this) && topLeftState.getValue(AUG_STATION_PIECE) == AugStationPiece.TOP_LEFT) {
                    level.setBlock(topLeft, Blocks.AIR.defaultBlockState(), 35);
                    level.levelEvent(player, 2001, topLeft, Block.getId(topLeftState));
                }
            } else if (state.getValue(CORNER) == AugStationPiece.TOP_RIGHT) {
                below = pos.below();
                belowState = level.getBlockState(below);
                left = getBlockFromPos(state.getValue(FACING), pos, AugStationPiece.TOP_RIGHT, AugStationPiece.TOP_LEFT);
                leftState = level.getBlockState(left);
                bottomLeft = getBlockFromPos(state.getValue(FACING), pos, AugStationPiece.TOP_RIGHT, AugStationPiece.BOTTOM_LEFT);
                bottomLeftState = level.getBlockState(bottomLeft);

                if (belowState.is(this) && belowState.getValue(AUG_STATION_PIECE) == AugStationPiece.BOTTOM_RIGHT) {
                    level.setBlock(below, Blocks.AIR.defaultBlockState(), 35);
                    level.levelEvent(player, 2001, below, Block.getId(belowState));
                }
                if (bottomLeftState.is(this) && bottomLeftState.getValue(AUG_STATION_PIECE) == AugStationPiece.BOTTOM_LEFT) {
                    level.setBlock(bottomLeft, Blocks.AIR.defaultBlockState(), 35);
                    level.levelEvent(player, 2001, bottomLeft, Block.getId(bottomLeftState));
                }
                if (leftState.is(this) && leftState.getValue(AUG_STATION_PIECE) == AugStationPiece.TOP_LEFT) {
                    level.setBlock(left, Blocks.AIR.defaultBlockState(), 35);
                    level.levelEvent(player, 2001, left, Block.getId(leftState));
                }
            } else if (state.getValue(CORNER) == AugStationPiece.BOTTOM_LEFT) {
                above = pos.above();
                aboveState = level.getBlockState(above);
                right = getBlockFromPos(state.getValue(FACING), pos, AugStationPiece.BOTTOM_LEFT, AugStationPiece.BOTTOM_RIGHT);
                rightState = level.getBlockState(right);
                topRight = getBlockFromPos(state.getValue(FACING), pos, AugStationPiece.BOTTOM_LEFT, AugStationPiece.TOP_RIGHT);
                topRightState = level.getBlockState(topRight);

                if (aboveState.is(this) && aboveState.getValue(AUG_STATION_PIECE) == AugStationPiece.TOP_LEFT) {
                    level.setBlock(above, Blocks.AIR.defaultBlockState(), 35);
                    level.levelEvent(player, 2001, above, Block.getId(aboveState));
                }
                if (topRightState.is(this) && topRightState.getValue(AUG_STATION_PIECE) == AugStationPiece.TOP_RIGHT) {
                    level.setBlock(topRight, Blocks.AIR.defaultBlockState(), 35);
                    level.levelEvent(player, 2001, topRight, Block.getId(topRightState));
                }
                if (rightState.is(this) && rightState.getValue(AUG_STATION_PIECE) == AugStationPiece.BOTTOM_RIGHT) {
                    level.setBlock(right, Blocks.AIR.defaultBlockState(), 35);
                    level.levelEvent(player, 2001, right, Block.getId(rightState));
                }
            }
        }
        return super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Block.box(2f, 0f, 0f, 16f, 16f, 16f);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> type) {
        return level.isClientSide() ? null : createTickerHelper(type, AUGSTATION_BE.get(), BaseAugStationBE::serverTick);
    }
}