package com.rdg.darkechoes.client.screen;

import com.rdg.darkechoes.DarkEchoes;
import com.rdg.darkechoes.client.ModItemTags;
import com.rdg.darkechoes.client.menus.BaseAugStationMenu;
import com.rdg.darkechoes.progression.MobProgression;
import com.rdg.darkechoes.registry.ModDataComponents;
import com.rdg.darkechoes.registry.blocks.TierOneAugStationBlock;
import com.rdg.darkechoes.registry.blocks.TierTwoAugStationBlock;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import static com.rdg.darkechoes.registry.ModDataComponents.AUGMENT_SLOTS;

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

    Button awakenButton = Button.builder(Component.translatable("button.darkechoes.augstation.initawakening"), button -> {
                menu.awakenGear();
            })
            .size(38, 15)
            .build();

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractRenderState(graphics, mouseX, mouseY, a);

//        TODO add more item details of adaptation, and make sure to update GUI
        Slot gearSlot = menu.getSlot(0);
        ItemStack gear = gearSlot.getItem();
        Block augStationBlock = menu.augStationBlock;
//        TODO add proper block check
        DarkEchoes.LOGGER.info("augStationBlock: {}", augStationBlock);
        if (!gear.isEmpty()) {
            if (augStationBlock instanceof TierOneAugStationBlock) {
                if (gear.is(ModItemTags.TIER_ONE_ARMOR) || gear.is(ModItemTags.TIER_ONE_TOOL)) {
                    awakenButton.active = true;
                } else {
                    awakenButton.setTooltip(Tooltip.create(Component.translatable("container.augment_station.incompatible")));
                }
            } else if (augStationBlock instanceof TierTwoAugStationBlock) {
                if (gear.is(ModItemTags.TIER_THREE_ARMOR) || gear.is(ModItemTags.TIER_THREE_TOOL)) {
                    awakenButton.setTooltip(Tooltip.create(Component.translatable("container.augment_station.incompatible")));
                } else {
                    awakenButton.active = true;
                }
            } else {
                awakenButton.active = true;
            }

            Integer augment_slots = gear.get(AUGMENT_SLOTS);
            MobProgression mobProgression = gear.get(ModDataComponents.MOB_PROGRESSION);
//            TODO add better way to iterate if adaptation's target is pending or not
            Component mobName = mobProgression != null ? (mobProgression.target().isEmpty() ? Component.translatable(BuiltInRegistries.ENTITY_TYPE.getValue(Identifier.parse(mobProgression.pendingTarget())).getDescriptionId()) : Component.translatable(BuiltInRegistries.ENTITY_TYPE.getValue(Identifier.parse(mobProgression.target())).getDescriptionId())) : Component.translatable("container.augment_station.no_adaptation");
            graphics.text(this.font, gear.getItemName(), this.leftPos + 100, this.topPos + 7, 0xFF404040, false);
            graphics.fakeItem(gearSlot.getItem(), this.leftPos + 112, this.topPos + 25);
            graphics.text(this.font, (augment_slots != null ? "Augment Slots: " + augment_slots : "Not awakened yet!"), this.leftPos + 101, this.topPos + 45, 0xFF404040, false);
            graphics.text(this.font, "Adaptations", this.leftPos + 147, this.topPos + 20, 0xFF404040, false);
            graphics.text(this.font, mobName, this.leftPos + 147, this.topPos + 30, 0xFF404040, false);
        }
    }

    @Override
    protected void init() {
        super.init();
        awakenButton.setPosition(this.leftPos + 30, this.topPos + 87);
        this.addRenderableWidget(awakenButton);
        awakenButton.active = false;
    }
}
