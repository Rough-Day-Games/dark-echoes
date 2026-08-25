package com.rdg.darkechoes.registry.blocks.entities;

import com.rdg.darkechoes.client.ModItemTags;
import com.rdg.darkechoes.client.menus.BaseAugStationMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

import static com.rdg.darkechoes.registry.ModBlocks.AUGSTATION_BE;

public class BaseAugStationBE extends BaseContainerBlockEntity {
    private NonNullList<ItemStack> items = NonNullList.withSize(3, ItemStack.EMPTY);
    private int menuIndex = 0;
    protected final ContainerData containerData = new ContainerData() {
        {
            Objects.requireNonNull(BaseAugStationBE.this);
        }

        @Override
        public int get(int dataId) {
            return BaseAugStationBE.this.menuIndex;
        }

        @Override
        public void set(int dataId, int value) {
            BaseAugStationBE.this.menuIndex = value;
        }

        @Override
        public int getCount() {
            return 1;
        }
    };

    public BaseAugStationBE(BlockPos worldPosition, BlockState blockState) {
        super(AUGSTATION_BE.get(), worldPosition, blockState);
    }

    public static void serverTick(Level level, BlockPos blockPos, BlockState blockState, BaseAugStationBE blockEntity) {
        if (!level.isClientSide()) {
            setChanged(level, blockPos, blockState);
        }
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
        return new BaseAugStationMenu(i, inventory, this, containerData, worldPosition);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return this.saveWithFullMetadata(registries);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        output.putInt("menuIndex", menuIndex);
        ContainerHelper.saveAllItems(output, this.items);
    }

    @Override
    public void onDataPacket(Connection net, ValueInput valueInput) {
        super.onDataPacket(net, valueInput);
        this.menuIndex = valueInput.getIntOr("menuIndex", 0);
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        this.menuIndex = input.getIntOr("menuIndex", 0);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(input, this.items);
    }

    @Override
    public boolean canPlaceItem(int slot, ItemStack itemStack) {
        if (slot == 0) {
            return itemStack.is(ModItemTags.AUGMENTABLE_GEAR);
        } else {
            return itemStack.is(ModItemTags.AWAKENING_ITEMS);
        }
    }

    @Override
    public int getContainerSize() {
        return items.size();
    }
}
