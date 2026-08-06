package com.duncanois.darkechoes.registry.blocks.entities;

import com.duncanois.darkechoes.client.ModItemTags;
import com.duncanois.darkechoes.client.menus.BaseAugStationMenu;
import com.duncanois.darkechoes.registry.blocks.TierOneAugStationBlock;
import com.duncanois.darkechoes.registry.blocks.TierThreeAugStationBlock;
import com.duncanois.darkechoes.registry.blocks.TierTwoAugStationBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.Nullable;

import static com.duncanois.darkechoes.registry.ModBlocks.*;

public class BaseAugStationBE extends BaseContainerBlockEntity implements Container {
    private NonNullList<ItemStack> items = NonNullList.withSize(2, ItemStack.EMPTY);

    public BaseAugStationBE(BlockPos worldPosition, BlockState blockState) {
        super(AUGSTATION_BE.get(), worldPosition, blockState);
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("container.augment_station");
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> nonNullList) {
        this.items = nonNullList;
    }

    @Override
    protected AbstractContainerMenu createMenu(int i, Inventory inventory) {
        return new BaseAugStationMenu(i, inventory, this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return this.saveWithFullMetadata(registries);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        ContainerHelper.saveAllItems(output, this.items);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(input, this.items);
    }

    @Override
    public boolean canPlaceItem(int slot, ItemStack itemStack) {
        if (slot == 0) {
//            Block baseBlock = getBlockState().getBlock();
//            return switch (baseBlock) {
//                case TierOneAugStationBlock _ -> itemStack.is(ModItemTags.TIER_ONE_GEAR);
//                case TierTwoAugStationBlock _ -> itemStack.is(ModItemTags.TIER_TWO_GEAR);
//                case TierThreeAugStationBlock _ -> itemStack.is(ModItemTags.TIER_THREE_GEAR);
//                default -> false;
//            };
            return itemStack.is(ModItemTags.AUGMENTABLE_GEAR);
        } else {
            return itemStack.is(ModItemTags.AWAKENING_ITEMS);
        }
    }

//    TODO overkill? maybe not necessary? an attempt to fix client desync
    public static void serverTick(Level level, BlockPos blockPos, BlockState blockState, BaseAugStationBE blockEntity) {
        if (!level.isClientSide()) {
            blockEntity.setChanged();
            level.sendBlockUpdated(blockPos, blockState, blockState, Block.UPDATE_ALL);
            setChanged(level, blockPos, blockState);
        }
    }

    @Override
    public int getContainerSize() {
        return items.size();
    }
}
