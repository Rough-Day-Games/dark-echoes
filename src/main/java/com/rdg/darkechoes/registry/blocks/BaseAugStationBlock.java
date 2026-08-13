package com.rdg.darkechoes.registry.blocks;

import com.rdg.darkechoes.client.menus.BaseAugStationMenu;
import com.rdg.darkechoes.registry.blocks.entities.BaseAugStationBE;
import com.mojang.math.OctahedralGroup;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
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
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import static com.rdg.darkechoes.registry.ModBlocks.AUGSTATION_BE;

public abstract class BaseAugStationBlock extends BaseEntityBlock {
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;

    public BaseAugStationBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH));
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

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> type) {
        return level.isClientSide() ? null : createTickerHelper(type, AUGSTATION_BE.get(), BaseAugStationBE::serverTick);
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
