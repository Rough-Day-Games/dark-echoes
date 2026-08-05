package com.duncanois.darkechoes.client.screen;

import com.duncanois.darkechoes.DarkEchoes;
import com.duncanois.darkechoes.client.menus.BaseAugStationMenu;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;

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

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractRenderState(graphics, mouseX, mouseY, a);

//        TODO add more item details of adaptation, and make sure to update GUI
        Slot tool = menu.getSlot(0);
        if (tool.hasItem()) {
            graphics.text(this.font, tool.getItem().getItemName(), this.leftPos + 99, this.topPos + 6, 0xFF404040, false);
        }
    }

    @Override
    protected void init() {
        super.init();

        this.addRenderableWidget(
                Button.builder(Component.translatable("button.darkechoes.augstation.initawakening"), button -> {
                            this.minecraft.player.sendOverlayMessage(menu.getSlot(0).getItem().getItemName());
                        }).pos(this.leftPos + 30, this.topPos + 87)
                        .size(38, 15)
                        .build()
        );
    }
}
