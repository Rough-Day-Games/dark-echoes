package com.duncanois.darkechoes.client.screen;

import com.duncanois.darkechoes.DarkEchoes;
import com.duncanois.darkechoes.client.menus.BaseAugStationMenu;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;

public class AugStationScreen extends AbstractContainerScreen<BaseAugStationMenu> {
    private static final Identifier BACKGROUND_LOC = Identifier.fromNamespaceAndPath(DarkEchoes.MOD_ID, "textures/gui/container/augment_station_menu.png");
    public AugStationScreen(BaseAugStationMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title, 229, 218);
        this.titleLabelX = 5;
        this.titleLabelY = 5;
        this.inventoryLabelY = 125;
        this.inventoryLabelX = 35;
    }

    @Override
    protected void containerTick() {
        super.containerTick();
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractBackground(graphics, mouseX, mouseY, a);

        graphics.blit(
                RenderPipelines.GUI_TEXTURED,
                BACKGROUND_LOC,
                this.leftPos, this.topPos,
                0, 0,
                this.imageWidth, this.imageHeight,
                256, 256
        );
    }

    @Override
    protected void extractLabels(GuiGraphicsExtractor graphics, int xm, int ym) {
        super.extractLabels(graphics, xm, ym);

        graphics.text(this.font, this.title, this.titleLabelX, this.titleLabelY, 0xFF404040, false);
    }
}
