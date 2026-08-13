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
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import static com.rdg.darkechoes.registry.ModDataComponents.AUGMENT_SLOTS;

public class AugStationScreen extends AbstractContainerScreen<BaseAugStationMenu> {
    private static final Identifier BACKGROUND_LOC = Identifier.fromNamespaceAndPath(DarkEchoes.MOD_ID, "textures/gui/container/augment_station_menu.png");

    public AugStationScreen(BaseAugStationMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title, 512, 382);
        this.titleLabelX = 5;
        this.titleLabelY = 5;
        this.inventoryLabelY = 277;
        this.inventoryLabelX = 180;
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
                512, 512
        );
    }

    @Override
    protected void extractLabels(GuiGraphicsExtractor graphics, int xm, int ym) {
        super.extractLabels(graphics, xm, ym);

        graphics.text(this.font, this.title, this.titleLabelX, this.titleLabelY, 0xFF404040, false);
    }

    final Button awakenButton = Button.builder(Component.translatable("button.darkechoes.augstation.initawakening"), _ -> menu.awakenOrAugmentGear())
            .size(108, 18)
            .build();

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractRenderState(graphics, mouseX, mouseY, a);

//        TODO add more item details of adaptation, and make sure to update GUI
        Slot gearSlot = menu.getSlot(0);
        ItemStack gear = gearSlot.getItem();
        Block be = menu.augStationBlock;
        if (!gear.isEmpty()) {
            if (be instanceof TierOneAugStationBlock) {
                if (gear.is(ModItemTags.TIER_ONE_ARMOR) || gear.is(ModItemTags.TIER_ONE_TOOL)) {
                    resetButton();
                } else {
                    incompatibleItem();
                }
            } else if (be instanceof TierTwoAugStationBlock) {
                if (gear.is(ModItemTags.TIER_THREE_ARMOR) || gear.is(ModItemTags.TIER_THREE_TOOL)) {
                    incompatibleItem();
                } else {
                    resetButton();
                }
            } else {
                resetButton();
            }

            Integer augment_slots = gear.get(AUGMENT_SLOTS);
            MobProgression mobProgression = gear.get(ModDataComponents.MOB_PROGRESSION);

            augmentSlotsLimit(gear);

            if (augment_slots != null) {
                awakenButton.setMessage(Component.translatable("button.darkechoes.augstation.initaugment"));
            }
//            TODO add better way to iterate if adaptation's target is pending or not
            Component mobName = mobProgression != null ? (mobProgression.target().isEmpty() ? Component.translatable(BuiltInRegistries.ENTITY_TYPE.getValue(Identifier.parse(mobProgression.pendingTarget())).getDescriptionId()) : Component.translatable(BuiltInRegistries.ENTITY_TYPE.getValue(Identifier.parse(mobProgression.target())).getDescriptionId())) : Component.translatable("container.augment_station.no_adaptation");
            graphics.text(this.font, gear.getItemName(), this.leftPos + 257, this.topPos + 21, 0xFF404040, false);
            graphics.fakeItem(gearSlot.getItem(), this.leftPos + 370, this.topPos + 34);
            graphics.text(this.font, (augment_slots != null ? "Augment Slots: " + augment_slots : "Not awakened yet!"), this.leftPos + 270, this.topPos + 60, 0xFF404040, false);
            graphics.text(this.font, "Adaptation Slots: (get data from item)", this.leftPos + 270, this.topPos + 75, 0xFF404040, false);
            if (gear.has(ModDataComponents.WEAKENED)) {
                graphics.text(this.font, "This item is weakened!", this.leftPos + 270, this.topPos + 90, 0xFFAAAAAA, false);
            } else if (gear.has(ModDataComponents.FRAGILE)) {
                graphics.text(this.font, "This item is fragile!", this.leftPos + 270, this.topPos + 90, 0xFFFF5555, false);;
            }
            graphics.text(this.font, "Adaptations", this.leftPos + 23, this.topPos + 129, 0xFF404040, false);
            graphics.text(this.font, mobName, this.leftPos + 36, this.topPos + 140, 0xFF404040, false);

        } else {
            resetButton();
        }
    }

    private void incompatibleItem() {
        awakenButton.active = false;
        awakenButton.setTooltip(Tooltip.create(Component.translatable("container.augment_station.incompatible")));
    }

//    TODO might want to convert the max augment slots into a config
    private void augmentSlotsLimit(ItemStack gear) {
        Integer augment_slots = gear.get(AUGMENT_SLOTS);
        if ((gear.is(ModItemTags.TIER_ONE_ARMOR) || gear.is(ModItemTags.TIER_ONE_TOOL)) && augment_slots != null && augment_slots >= 3) {
            awakenButton.active = false;
            awakenButton.setTooltip(Tooltip.create(Component.translatable("container.augment_station.limit_augment_slots")));
        } else if ((gear.is(ModItemTags.TIER_TWO_ARMOR) || gear.is(ModItemTags.TIER_TWO_TOOL)) && augment_slots != null && augment_slots >= 6) {
            awakenButton.active = false;
            awakenButton.setTooltip(Tooltip.create(Component.translatable("container.augment_station.limit_augment_slots")));
        } else if ((gear.is(ModItemTags.TIER_THREE_ARMOR) || gear.is(ModItemTags.TIER_THREE_TOOL)) && augment_slots != null &&  augment_slots >= 10) {
            awakenButton.active = false;
            awakenButton.setTooltip(Tooltip.create(Component.translatable("container.augment_station.limit_augment_slots")));
        } else if (augment_slots != null && augment_slots <= -1) {
            awakenButton.active = false;
            awakenButton.setTooltip(Tooltip.create(Component.translatable("container.augment_station.limit_augment_slots")));
        }
    }

    private void resetButton() {
        awakenButton.active = true;
        awakenButton.setTooltip(null);
        awakenButton.setMessage(Component.translatable("button.darkechoes.augstation.initawakening"));
    }

    @Override
    protected void init() {
        super.init();
        awakenButton.setPosition(this.leftPos + 72, this.topPos + 88);
        this.addRenderableWidget(awakenButton);
        awakenButton.active = false;
    }
}
