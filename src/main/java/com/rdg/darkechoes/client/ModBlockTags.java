package com.rdg.darkechoes.client;

import com.rdg.darkechoes.DarkEchoes;
import com.rdg.darkechoes.registry.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;

import java.util.concurrent.CompletableFuture;

public class ModBlockTags extends BlockTagsProvider {
    public ModBlockTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, DarkEchoes.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.MINEABLE_WITH_AXE).add(
                ModBlocks.T_ONE_AUGSTATION.get(),
                ModBlocks.T_TWO_AUGSTATION.get()
        );

        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
                ModBlocks.T_THREE_AUGSTATION.get()
        );

        tag(BlockTags.INCORRECT_FOR_COPPER_TOOL).add(
                ModBlocks.T_TWO_AUGSTATION.get()
        );

        tag(BlockTags.INCORRECT_FOR_DIAMOND_TOOL).add(
                ModBlocks.T_THREE_AUGSTATION.get()
        );
    }
}
