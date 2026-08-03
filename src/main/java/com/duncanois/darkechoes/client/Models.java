package com.duncanois.darkechoes.client;

import com.duncanois.darkechoes.DarkEchoes;
import com.duncanois.darkechoes.registry.ModBlocks;
import com.duncanois.darkechoes.registry.ModItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.renderer.item.CuboidItemModelWrapper;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;

import java.util.Collections;
import java.util.Optional;

public class Models extends ModelProvider {
    public Models(PackOutput output) {
        super(output, DarkEchoes.MOD_ID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        itemModels.createFlatItemModel(ModItems.RESONANCE_CRYSTAL.get(), ModelTemplates.FLAT_ITEM);
        itemModels.createFlatItemModel(ModItems.ECHO_DIAMOND_HELMET.get(), ModelTemplates.FLAT_ITEM);
        itemModels.createFlatItemModel(ModItems.ECHO_DIAMOND_CHESTPLATE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.createFlatItemModel(ModItems.ECHO_DIAMOND_LEGGINGS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.createFlatItemModel(ModItems.ECHO_DIAMOND_BOOTS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.createFlatItemModel(ModItems.ECHO_DIAMOND_SWORD.get(), ModelTemplates.FLAT_ITEM);
        itemModels.createFlatItemModel(ModItems.ECHO_DIAMOND_AXE.get(), ModelTemplates.FLAT_ITEM);

//        TODO replace the getModelLocation params once we have proper textures
        itemModels.itemModelOutput.accept(ModItems.RESONANCE_CRYSTAL.get(),
                new CuboidItemModelWrapper.Unbaked(
                        ModelLocationUtils.getModelLocation(Items.DIAMOND),
                        Optional.empty(),
                        Collections.emptyList()
                ));
        itemModels.itemModelOutput.accept(ModItems.ECHO_DIAMOND_HELMET.get(),
                new CuboidItemModelWrapper.Unbaked(
                        ModelLocationUtils.getModelLocation(Items.DIAMOND_HELMET),
                        Optional.empty(),
                        Collections.emptyList()
                ));
        itemModels.itemModelOutput.accept(ModItems.ECHO_DIAMOND_CHESTPLATE.get(),
                new CuboidItemModelWrapper.Unbaked(
                        ModelLocationUtils.getModelLocation(Items.DIAMOND_CHESTPLATE),
                        Optional.empty(),
                        Collections.emptyList()
                ));
        itemModels.itemModelOutput.accept(ModItems.ECHO_DIAMOND_LEGGINGS.get(),
                new CuboidItemModelWrapper.Unbaked(
                        ModelLocationUtils.getModelLocation(Items.DIAMOND_LEGGINGS),
                        Optional.empty(),
                        Collections.emptyList()
                ));
        itemModels.itemModelOutput.accept(ModItems.ECHO_DIAMOND_BOOTS.get(),
                new CuboidItemModelWrapper.Unbaked(
                        ModelLocationUtils.getModelLocation(Items.DIAMOND_BOOTS),
                        Optional.empty(),
                        Collections.emptyList()
                ));
        itemModels.itemModelOutput.accept(ModItems.ECHO_DIAMOND_SWORD.get(),
                new CuboidItemModelWrapper.Unbaked(
                        ModelLocationUtils.getModelLocation(Items.DIAMOND_SWORD),
                        Optional.empty(),
                        Collections.emptyList()
                ));
        itemModels.itemModelOutput.accept(ModItems.ECHO_DIAMOND_AXE.get(),
                new CuboidItemModelWrapper.Unbaked(
                        ModelLocationUtils.getModelLocation(Items.DIAMOND_AXE),
                        Optional.empty(),
                        Collections.emptyList()
                ));

        blockModels.createNonTemplateModelBlock(ModBlocks.T_ONE_AUGSTATION.get());
    }
}
