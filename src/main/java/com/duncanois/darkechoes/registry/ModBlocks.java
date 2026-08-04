package com.duncanois.darkechoes.registry;

import com.duncanois.darkechoes.DarkEchoes;
import com.duncanois.darkechoes.registry.blocks.BaseAugStationBlock;
import com.duncanois.darkechoes.registry.blocks.TierThreeAugStationBlock;
import com.duncanois.darkechoes.registry.blocks.TierTwoAugStationBlock;
import com.duncanois.darkechoes.registry.blocks.entities.BaseAugStationBE;
import com.duncanois.darkechoes.registry.blocks.TierOneAugStationBlock;
import com.duncanois.darkechoes.registry.blocks.entities.TierThreeAugStationBE;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<MapCodec<? extends Block>> BLOCK_TYPE = DeferredRegister.create(BuiltInRegistries.BLOCK_TYPE, DarkEchoes.MOD_ID);
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(DarkEchoes.MOD_ID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, DarkEchoes.MOD_ID);

    public static final Supplier<MapCodec<BaseAugStationBlock>> AUGMENT_STATION_CODEC = BLOCK_TYPE.register(
            "augment_station", () -> BlockBehaviour.simpleCodec(BaseAugStationBlock::new)
    );

//    public static final DeferredBlock<AugmentStations> T_ONE_AUGSTATION = BLOCKS.register("tier_one_augmentstation",
//            registryName -> new AugmentStations(BlockBehaviour.Properties.of()
//                    .setId(ResourceKey.create(Registries.BLOCK, registryName))
//                    .destroyTime(2.0f)
//                    .explosionResistance(10.0f)
//                    .sound(SoundType.WOOD)
//            ));

    public static final DeferredBlock<TierOneAugStationBlock> T_ONE_AUGSTATION = BLOCKS.register("augment_station/t_one",
            (registryName) -> new TierOneAugStationBlock(BlockBehaviour.Properties.of()
                    .setId(ResourceKey.create(Registries.BLOCK, registryName))
                    .destroyTime(2.0f)
                    .explosionResistance(10.0f)
                    .sound(SoundType.WOOD))
    );
    public static final DeferredBlock<TierTwoAugStationBlock> T_TWO_AUGSTATION = BLOCKS.register("augment_station/t_two",
            (registryName) -> new TierTwoAugStationBlock(BlockBehaviour.Properties.of()
                    .setId(ResourceKey.create(Registries.BLOCK, registryName))
                    .destroyTime(4.0f)
                    .explosionResistance(10.0f)
                    .sound(SoundType.METAL))
    );
    public static final DeferredBlock<TierThreeAugStationBlock> T_THREE_AUGSTATION = BLOCKS.register("augment_station/t_three",
            (registryName) -> new TierThreeAugStationBlock(BlockBehaviour.Properties.of()
                    .setId(ResourceKey.create(Registries.BLOCK, registryName))
                    .destroyTime(6.0f)
                    .explosionResistance(50.0f)
                    .sound(SoundType.METAL)
            )
    );
    public static final Supplier<BlockEntityType<BaseAugStationBE>> AUGMENTSTATION_BE = BLOCK_ENTITY_TYPES.register(
            "augmentstation_block_entity",
            () -> new BlockEntityType<>(
                    BaseAugStationBE::new,
                    false,
                    T_ONE_AUGSTATION.get(), T_TWO_AUGSTATION.get(), T_THREE_AUGSTATION.get()
            )
    );

}
