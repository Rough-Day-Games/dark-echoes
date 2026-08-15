package com.rdg.darkechoes.client.screen;

import com.rdg.darkechoes.DarkEchoes;
import com.rdg.darkechoes.client.ModItemTags;
import com.rdg.darkechoes.client.menus.BaseAugStationMenu;
import com.rdg.darkechoes.config.CombatConfig;
import com.rdg.darkechoes.progression.MobProgression;
import com.rdg.darkechoes.progression.Progression;
import com.rdg.darkechoes.registry.ModDataComponents;
import com.rdg.darkechoes.registry.ModItems;
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
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import static com.rdg.darkechoes.combat.CombatEvents.actionName;
import static com.rdg.darkechoes.registry.ModDataComponents.AUGMENT_SLOTS;

public class BaseAugStationScreen extends AbstractContainerScreen<BaseAugStationMenu> {
    private static final Identifier BACKGROUND_LOC_MAIN = Identifier.fromNamespaceAndPath(DarkEchoes.MOD_ID, "textures/gui/container/augment_station_menu.png");
    private static final Identifier BACKGROUND_LOC_ADAPTATION = Identifier.fromNamespaceAndPath(DarkEchoes.MOD_ID, "textures/gui/container/augment_station_menu_adaptation.png");
    final Button awakenButton = Button.builder(Component.translatable("button.darkechoes.augstation.initawakening"), button -> {
                menu.awakenOrResonateGear();
                button.active = false;
            })
            .size(108, 18)
            .build();
    final Button augmentButton = Button.builder(Component.translatable("button.darkechoes.augstation.initaugment"), button -> {
//                menu.awakenOrResonateGear()
            })
            .size(108, 18)
            .tooltip(Tooltip.create(Component.literal("Augmentation coming soon~")))
            .build();
    final Button nextButton = Button.builder(Component.translatable("menu.darkechoes.augment_station.next"), _ -> menu.openMenuIndex(true))
            .size(18, 18)
            .build();
    final Button prevButton = Button.builder(Component.translatable("menu.darkechoes.augment_station.prev"), _ -> menu.openMenuIndex(false))
            .size(18, 18)
            .build();

    public BaseAugStationScreen(BaseAugStationMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title, 256, 256);
        this.titleLabelX = 5;
        this.titleLabelY = 5;
        this.inventoryLabelY = 165;
        this.inventoryLabelX = 45;
    }

    @Override
    protected void containerTick() {
        super.containerTick();
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractBackground(graphics, mouseX, mouseY, a);
        int menuIndex = menu.augmentStationData.get(0);

        if (menuIndex == 1) {
            graphics.blit(
                    RenderPipelines.GUI_TEXTURED,
                    BACKGROUND_LOC_ADAPTATION,
                    this.leftPos, this.topPos,
                    0, 0,
                    this.imageWidth, this.imageHeight,
                    256, 256
            );
        } else {
            graphics.blit(
                    RenderPipelines.GUI_TEXTURED,
                    BACKGROUND_LOC_MAIN,
                    this.leftPos, this.topPos,
                    0, 0,
                    this.imageWidth, this.imageHeight,
                    256, 256
            );
        }

    }

    private void resetButton() {
        Slot gearSlot = menu.getSlot(0);
        ItemStack gear = gearSlot.getItem();
        awakenButton.active = !gear.isEmpty();
        awakenButton.setTooltip(null);
        awakenButton.setMessage(Component.translatable("button.darkechoes.augstation.initawakening"));
    }

    @Override
    protected void extractLabels(GuiGraphicsExtractor graphics, int xm, int ym) {
        super.extractLabels(graphics, xm, ym);

        graphics.text(this.font, this.title, this.titleLabelX, this.titleLabelY, 0xFF404040, false);
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractRenderState(graphics, mouseX, mouseY, a);
        int pageTitleX = 125;
        int pageTitleY = 24;
        int menuIndex = menu.augmentStationData.get(0);
        Slot gearSlot = menu.getSlot(0);
        BaseAugStationMenu.AwakenSlot awakenSlot = (BaseAugStationMenu.AwakenSlot) menu.getSlot(1);
        ItemStack gear = gearSlot.getItem();
        Integer augment_slots = gear.get(AUGMENT_SLOTS);
        Component targetDetails = null;
        Component mobName = null;

        if (menuIndex == 0) {
            awakenButton.setPosition(this.leftPos + 26, this.topPos + 133);
            augmentButton.setPosition(999, 999);
            awakenSlot.active = true;
            graphics.centeredText(this.font, Component.translatable("menu.darkechoes.augment_station.page.awakening"), this.leftPos + pageTitleX, this.topPos + pageTitleY, 0xFF404040);
        } else if (menuIndex == 1) {
            awakenButton.setPosition(999, 999);
            augmentButton.setPosition(999, 999);
            awakenSlot.active = false;
            graphics.centeredText(this.font, Component.translatable("menu.darkechoes.augment_station.page.adaptation"), this.leftPos + pageTitleX, this.topPos + pageTitleY, 0xFF404040);
        } else if (menuIndex == 2) {
            awakenButton.setPosition(999, 999);
            augmentButton.setPosition(this.leftPos + 26, this.topPos + 133);
            awakenSlot.active = false;
            graphics.centeredText(this.font, Component.translatable("menu.darkechoes.augment_station.page.augmentation"), this.leftPos + pageTitleX, this.topPos + pageTitleY, 0xFF404040);
        }

        MobProgression progression = Progression.data(gear);
        int actionsPerLevel = Progression.isAwakenedCombatWeapon(gear)
                ? CombatConfig.KILLS_PER_LEVEL.getAsInt()
                : CombatConfig.ARMOR_HITS_PER_LEVEL.getAsInt();
        int maxLevel = CombatConfig.MAX_MOB_PROGRESSION_LEVEL.getAsInt();
        boolean weapon = Progression.isAwakenedCombatWeapon(gear);
        if (progression.locked()) {
            int level = progression.level(actionsPerLevel, maxLevel);
            long bonus = Math.round(level * (weapon
                    ? CombatConfig.WEAPON_DAMAGE_BONUS_PER_LEVEL.getAsDouble()
                    : CombatConfig.ARMOR_REDUCTION_BONUS_PER_LEVEL.getAsDouble()) * 100.0D);
            mobName = targetDetails(progression.target(), level);
            if (level >= maxLevel) {
                targetDetails = Component.translatable(
                        weapon ? "container.augment_station.weapon_progression_max"
                                : "container.augment_station.tool_progression_max",
                        bonus);
            } else {
                int remaining = actionsPerLevel - progression.actions() % actionsPerLevel;
                targetDetails = Component.translatable(
                        weapon ? "container.augment_station.weapon_progression"
                                : "container.augment_station.tool_progression",
                        bonus, level + 1, remaining, actionName(weapon, remaining));
            }
        } else if (!progression.pendingTarget().isEmpty()) {
            int remaining = actionsPerLevel - progression.pendingActions();
            targetDetails = Component.translatable("container.augment_station.progression_pending", remaining, actionName(weapon, remaining));
        }

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
            graphics.text(this.font, gear.getItemName(), this.leftPos + 62, this.topPos + 44, 0xFF404040, false);

            if (menuIndex == 0) {
                graphics.text(this.font, (augment_slots != null ? "Augment Slots: " + augment_slots : "Not awakened yet!"), this.leftPos + 62, this.topPos + 60, 0xFF404040, false);
//                TODO once adaptation slots exist, get its data and display here
                graphics.text(this.font, "Adaptation Slots: 1", this.leftPos + 62, this.topPos + 76, 0xFF404040, false);
                if (gear.has(ModDataComponents.WEAKENED)) {
                    graphics.text(this.font, "This item is weakened!", this.leftPos + 62, this.topPos + 92, 0xFFAAAAAA, false);
                } else if (gear.has(ModDataComponents.FRAGILE)) {
                    graphics.text(this.font, "This item is fragile!", this.leftPos + 62, this.topPos + 92, 0xFFFF5555, false);
                }
                augmentSlotsLimit(gear);

                if (augment_slots != null) {
                    awakenButton.setMessage(Component.translatable("button.darkechoes.augstation.initresonance"));
                }
            } else if (menuIndex == 1) {
//                graphics.drawScrollingString();
                graphics.text(this.font, mobName, this.leftPos + 62, this.topPos + 57, 0xFF404040, false);
                graphics.text(this.font, targetDetails, this.leftPos + 62, this.topPos + 73, 0xFF404040, false);
            } else if (menuIndex == 2) {
// TODO augmentation menu WIP
            }
        } else {
            resetButton();
        }
    }

    public static Component targetDetails(String id, int level) {
        Identifier identifier = Identifier.tryParse(id);
        EntityType<?> type = identifier == null ? null : BuiltInRegistries.ENTITY_TYPE.getValue(identifier);
        return type == null ? Component.literal(id + ": Level " + level) : Component.translatable("container.augment_station.target_details", type.getDescription(), level);
    }

    private void incompatibleItem() {
        awakenButton.active = false;
        awakenButton.setTooltip(Tooltip.create(Component.translatable("container.augment_station.incompatible")));
    }

    private void augmentSlotsLimit(ItemStack gear) {
        Integer augment_slots = gear.get(AUGMENT_SLOTS);
        Slot awakenSlot = menu.getSlot(1);
        if ((gear.is(ModItemTags.TIER_ONE_ARMOR) || gear.is(ModItemTags.TIER_ONE_TOOL)) && augment_slots != null && augment_slots >= CombatConfig.MAX_AUGMENT_SLOTS_TIER_ONE.getAsInt() && awakenSlot.getItem().is(ModItems.RESONANCE_CRYSTAL)) {
            awakenButton.active = false;
            awakenButton.setTooltip(Tooltip.create(Component.translatable("container.augment_station.limit_augment_slots")));
        } else if ((gear.is(ModItemTags.TIER_TWO_ARMOR) || gear.is(ModItemTags.TIER_TWO_TOOL)) && augment_slots != null && augment_slots >= CombatConfig.MAX_AUGMENT_SLOTS_TIER_TWO.getAsInt() && awakenSlot.getItem().is(ModItems.RESONANCE_CRYSTAL)) {
            awakenButton.active = false;
            awakenButton.setTooltip(Tooltip.create(Component.translatable("container.augment_station.limit_augment_slots")));
        } else if ((gear.is(ModItemTags.TIER_THREE_ARMOR) || gear.is(ModItemTags.TIER_THREE_TOOL)) && augment_slots != null && augment_slots >= CombatConfig.MAX_AUGMENT_SLOTS_TIER_THREE.getAsInt() && awakenSlot.getItem().is(ModItems.RESONANCE_CRYSTAL)) {
            awakenButton.active = false;
            awakenButton.setTooltip(Tooltip.create(Component.translatable("container.augment_station.limit_augment_slots")));
        } else if (augment_slots != null && augment_slots <= -1 && !awakenSlot.getItem().is(ModItems.RESONANCE_CRYSTAL)) {
            awakenButton.active = false;
            awakenButton.setTooltip(Tooltip.create(Component.translatable("container.augment_station.limit_augment_slots")));
        }
    }

    @Override
    protected void init() {
        super.init();
        awakenButton.setPosition(this.leftPos + 26, this.topPos + 133);
        augmentButton.active = false;
        augmentButton.setPosition(this.leftPos + 26, this.topPos + 133);
        nextButton.setPosition(this.leftPos + 222, this.topPos + 193);
        prevButton.setPosition(this.leftPos + 16, this.topPos + 193);
        this.addRenderableWidget(awakenButton);
        this.addRenderableWidget(augmentButton);
        this.addRenderableWidget(nextButton);
        this.addRenderableWidget(prevButton);
    }
}
