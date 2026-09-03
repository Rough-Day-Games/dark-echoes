package com.rdg.darkechoes.client;

import com.rdg.darkechoes.DarkEchoes;
import com.rdg.darkechoes.registry.ModBlocks;
import com.rdg.darkechoes.registry.ModItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.blockstates.MultiPartGenerator;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.client.renderer.block.dispatch.Variant;
import net.minecraft.client.renderer.block.dispatch.multipart.CombinedCondition;
import net.minecraft.client.renderer.item.CuboidItemModelWrapper;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Models extends ModelProvider {
    public Models(PackOutput output) {
        super(output, DarkEchoes.MOD_ID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        itemModels.createFlatItemModel(ModItems.RESONANCE_CRYSTAL.get(), ModelTemplates.FLAT_ITEM);
        itemModels.createFlatItemModel(ModItems.ECHO_HELMET.get(), ModelTemplates.FLAT_ITEM);
        itemModels.createFlatItemModel(ModItems.ECHO_CHESTPLATE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.createFlatItemModel(ModItems.ECHO_LEGGINGS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.createFlatItemModel(ModItems.ECHO_BOOTS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.createFlatItemModel(ModItems.ECHO_SWORD.get(), ModelTemplates.FLAT_ITEM);
        itemModels.createFlatItemModel(ModItems.ECHO_AXE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.createFlatItemModel(ModItems.ECHO_PICKAXE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.createFlatItemModel(ModItems.ECHO_SHOVEL.get(), ModelTemplates.FLAT_ITEM);
        itemModels.createFlatItemModel(ModItems.ECHO_HOE.get(), ModelTemplates.FLAT_ITEM);

        itemModels.itemModelOutput.accept(ModItems.RESONANCE_CRYSTAL.get(),
                new CuboidItemModelWrapper.Unbaked(
                        ModelLocationUtils.getModelLocation(ModItems.RESONANCE_CRYSTAL.get()),
                        Optional.empty(),
                        Collections.emptyList()
                ));
        itemModels.itemModelOutput.accept(ModItems.ECHO_HELMET.get(),
                new CuboidItemModelWrapper.Unbaked(
                        ModelLocationUtils.getModelLocation(ModItems.ECHO_HELMET.get()),
                        Optional.empty(),
                        Collections.emptyList()
                ));
        itemModels.itemModelOutput.accept(ModItems.ECHO_CHESTPLATE.get(),
                new CuboidItemModelWrapper.Unbaked(
                        ModelLocationUtils.getModelLocation(ModItems.ECHO_CHESTPLATE.get()),
                        Optional.empty(),
                        Collections.emptyList()
                ));
        itemModels.itemModelOutput.accept(ModItems.ECHO_LEGGINGS.get(),
                new CuboidItemModelWrapper.Unbaked(
                        ModelLocationUtils.getModelLocation(ModItems.ECHO_LEGGINGS.get()),
                        Optional.empty(),
                        Collections.emptyList()
                ));
        itemModels.itemModelOutput.accept(ModItems.ECHO_BOOTS.get(),
                new CuboidItemModelWrapper.Unbaked(
                        ModelLocationUtils.getModelLocation(ModItems.ECHO_BOOTS.get()),
                        Optional.empty(),
                        Collections.emptyList()
                ));
        itemModels.itemModelOutput.accept(ModItems.ECHO_SWORD.get(),
                new CuboidItemModelWrapper.Unbaked(
                        ModelLocationUtils.getModelLocation(ModItems.ECHO_SWORD.get()),
                        Optional.empty(),
                        Collections.emptyList()
                ));
        itemModels.itemModelOutput.accept(ModItems.ECHO_AXE.get(),
                new CuboidItemModelWrapper.Unbaked(
                        ModelLocationUtils.getModelLocation(ModItems.ECHO_AXE.get()),
                        Optional.empty(),
                        Collections.emptyList()
                ));
        itemModels.itemModelOutput.accept(ModItems.ECHO_PICKAXE.get(),
                new CuboidItemModelWrapper.Unbaked(
                        ModelLocationUtils.getModelLocation(ModItems.ECHO_PICKAXE.get()),
                        Optional.empty(),
                        Collections.emptyList()
                ));
        itemModels.itemModelOutput.accept(ModItems.ECHO_SHOVEL.get(),
                new CuboidItemModelWrapper.Unbaked(
                        ModelLocationUtils.getModelLocation(ModItems.ECHO_SHOVEL.get()),
                        Optional.empty(),
                        Collections.emptyList()
                ));
        itemModels.itemModelOutput.accept(ModItems.ECHO_HOE.get(),
                new CuboidItemModelWrapper.Unbaked(
                        ModelLocationUtils.getModelLocation(ModItems.ECHO_HOE.get()),
                        Optional.empty(),
                        Collections.emptyList()
                ));

//        TODO how can i make such blockstate jsons that has so many states and different models as well?
        blockModels.createNonTemplateModelBlock(ModBlocks.T_ONE_AUGSTATION.get());
        blockModels.createNonTemplateModelBlock(ModBlocks.T_TWO_AUGSTATION.get());
        blockModels.createNonTemplateModelBlock(ModBlocks.T_THREE_AUGSTATION.get());
//
//        Identifier t1StationModel = TexturedModel.CUBE.create(ModBlocks.T_ONE_AUGSTATION.get(), blockModels.modelOutput);
//        Identifier t2StationModel = TexturedModel.CUBE.create(ModBlocks.T_TWO_AUGSTATION.get(), blockModels.modelOutput);
//        Identifier t3StationModel = TexturedModel.CUBE.create(ModBlocks.T_THREE_AUGSTATION.get(), blockModels.modelOutput);
//
//        // Create a common variant to transform
//        Variant t1Variant = new Variant(t1StationModel);
//        Variant t2Variant = new Variant(t2StationModel);
//        Variant t3Variant = new Variant(t3StationModel);
//
//        // Generate a multipart
//        blockModels.blockStateOutput.accept(
//                MultiPartGenerator.multiPart(ModBlocks.T_ONE_AUGSTATION.get())
//                        // Provide the base model
//                        .with(BlockModelGenerators.variant(t1Variant))
//                        // Add conditions for variant to appear
//                        .with(
//                                new CombinedCondition(
//                                        CombinedCondition.Operation.AND,
//                                        List.of(
//                                                BlockModelGenerators.condition().term(BlockStateProperties.FACING, Direction.NORTH).build()
//                                        )
//                                ),
//                                // Supply variant to mutate
//                                BlockModelGenerators.variant(t1Variant)
//                        ));
    }
}
