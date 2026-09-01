package com.rdg.darkechoes.client.screen;

import com.mojang.blaze3d.platform.cursor.CursorTypes;
import com.rdg.darkechoes.DarkEchoes;
import com.rdg.darkechoes.client.ModItemTags;
import com.rdg.darkechoes.client.menus.BaseAugStationMenu;
import com.rdg.darkechoes.config.CombatConfig;
import com.rdg.darkechoes.progression.*;
import com.rdg.darkechoes.registry.ModDataComponents;
import com.rdg.darkechoes.registry.ModItems;
import com.rdg.darkechoes.registry.blocks.TierOneAugStationBlock;
import com.rdg.darkechoes.registry.blocks.TierTwoAugStationBlock;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;

import java.util.Collections;
import java.util.Set;

import static com.rdg.darkechoes.combat.CombatEvents.combatGearActionName;
import static com.rdg.darkechoes.progression.Progression.isCombatGear;
import static com.rdg.darkechoes.progression.ToolProgression.blockName;
import static com.rdg.darkechoes.progression.ToolProgression.isTool;
import static com.rdg.darkechoes.registry.ModDataComponents.AUGMENT_SLOTS;

public class BaseAugStationScreen extends AbstractContainerScreen<BaseAugStationMenu> {
    private static final Identifier BACKGROUND_LOC_MAIN = Identifier.fromNamespaceAndPath(DarkEchoes.MOD_ID, "textures/gui/container/augment_station_menu.png");
    private static final Identifier BACKGROUND_LOC_ADAPTATION = Identifier.fromNamespaceAndPath(DarkEchoes.MOD_ID, "textures/gui/container/augment_station_menu_adaptation.png");
    private static final Identifier SIDE_BUTTON_INACTIVE = Identifier.fromNamespaceAndPath(DarkEchoes.MOD_ID, "textures/gui/container/augmenstation_side_tab_inactive.png");
    private static final Identifier SIDE_BUTTON_ACTIVE = Identifier.fromNamespaceAndPath(DarkEchoes.MOD_ID, "textures/gui/container/augmenstation_side_tab_active.png");
    final Button awakenButton = Button.builder(Component.translatable("button.darkechoes.augstation.initawakening"), _ -> menu.awakenOrResonateGear())
            .size(108, 18)
            .build();
    final Button augmentButton = Button.builder(Component.translatable("button.darkechoes.augstation.initaugment"), _ -> menu.augmentGear())
            .size(108, 18)
            .tooltip(Tooltip.create(Component.literal("Augmentation coming soon~")))
            .build();

    public BaseAugStationScreen(BaseAugStationMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title, 256, 246);
        this.titleLabelX = 5;
        this.titleLabelY = 5;
        this.inventoryLabelY = 155;
        this.inventoryLabelX = 45;
    }

    public static Component targetDetails(String id, int level, boolean entity) {
        Identifier identifier = Identifier.tryParse(id);
        if (entity) {
            EntityType<?> type = identifier == null ? null : BuiltInRegistries.ENTITY_TYPE.getValue(identifier);
            if (level < 1) {
                return type == null ? Component.literal(id + ": Pending") : Component.translatable("container.augment_station.pending_target_details", type.getDescription());
            }
            return type == null ? Component.literal(id + ": LV " + level) : Component.translatable("container.augment_station.target_details", type.getDescription(), level);
        } else {
            Block block = identifier == null ? null : BuiltInRegistries.BLOCK.getValue(identifier);
            if (level < 1) {
                return block == null ? Component.literal(id + ": Pending") : Component.translatable("container.augment_station.pending_target_details", block.getName());
            }
            return block == null ? Component.literal(id + ": LV " + level) : Component.translatable("container.augment_station.target_details", block.getName(), level);
        }
    }

    @Override
    protected void containerTick() {
        super.containerTick();
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractBackground(graphics, mouseX, mouseY, a);
        int menuIndex = menu.augmentStationData.get(0);

        if (menuIndex == 1 || menuIndex == 3) {
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

        for (int i = 0; i <= 3; i++) {
            extractTabButton(graphics, mouseX, mouseY, i);
        }
    }

    private void extractTabButton(GuiGraphicsExtractor graphics, int mouseX, int mouseY, int menu) {
        int y = this.topPos + 4 + 26 * menu;
        int menuIndex = this.menu.augmentStationData.get(0);
        boolean isMenuIndexOpen = menuIndex == menu;

        if (!isMenuIndexOpen && mouseX > this.leftPos + 256 && mouseY > y && mouseX < this.leftPos + 288 && mouseY < y + 26) {
            graphics.requestCursor(CursorTypes.POINTING_HAND);
        }

        graphics.blit(
                RenderPipelines.GUI_TEXTURED,
                isMenuIndexOpen ? SIDE_BUTTON_ACTIVE : SIDE_BUTTON_INACTIVE,
                this.leftPos + (isMenuIndexOpen ? 253 : 256), y,
                0, 0,
                isMenuIndexOpen ? 32 : 28, 26,
                32, 26);
    }

    private void resetButton() {
        Slot gearSlot = menu.getSlot(0);
        ItemStack gear = gearSlot.getItem();
        awakenButton.active = !gear.isEmpty();
        augmentButton.active = false;
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
        int pageTitleY = 14;
        int menuIndex = menu.augmentStationData.get(0);
        Slot gearSlot = menu.getSlot(0);
        BaseAugStationMenu.AwakenSlot awakenSlot = (BaseAugStationMenu.AwakenSlot) menu.getSlot(1);
        BaseAugStationMenu.AugmentSlot augmentSlot = (BaseAugStationMenu.AugmentSlot) menu.getSlot(2);
        ItemStack gear = gearSlot.getItem();
        Integer augment_slots = gear.get(AUGMENT_SLOTS);
        Component targetDetails = null;
        Component targetName = null;

        if (menuIndex == 0) {
            awakenButton.setPosition(this.leftPos + 26, this.topPos + 123);
            augmentButton.setPosition(999, 999);
            awakenSlot.active = true;
            augmentSlot.active = false;
            graphics.centeredText(this.font, Component.translatable("menu.darkechoes.augment_station.page.awakening"), this.leftPos + pageTitleX, this.topPos + pageTitleY, 0xFF8B8B8B);
        } else if (menuIndex == 1) {
            awakenButton.setPosition(999, 999);
            augmentButton.setPosition(999, 999);
            awakenSlot.active = false;
            augmentSlot.active = false;
            graphics.centeredText(this.font, Component.translatable("menu.darkechoes.augment_station.page.adaptation"), this.leftPos + pageTitleX, this.topPos + pageTitleY, 0xFF8B8B8B);
        } else if (menuIndex == 2) {
            awakenButton.setPosition(999, 999);
            augmentButton.setPosition(this.leftPos + 26, this.topPos + 123);
            awakenSlot.active = false;
            augmentSlot.active = true;
            graphics.centeredText(this.font, Component.translatable("menu.darkechoes.augment_station.page.augmentation"), this.leftPos + pageTitleX, this.topPos + pageTitleY, 0xFF8B8B8B);
        } else if (menuIndex == 3) {
            awakenButton.setPosition(999, 999);
            augmentButton.setPosition(999, 999);
            awakenSlot.active = false;
            augmentSlot.active = false;
            graphics.centeredText(this.font, Component.translatable("menu.darkechoes.augment_station.page.augments"), this.leftPos + pageTitleX, this.topPos + pageTitleY, 0xFF8B8B8B);
        }
        MobProgression mobProgression = null;
        BlockProgression blockProgression = null;

        if (isCombatGear(gear)) {
            mobProgression = Progression.data(gear);
            int actionsPerLevel = Progression.isAwakenedCombatWeapon(gear)
                    ? CombatConfig.KILLS_PER_LEVEL.getAsInt()
                    : CombatConfig.ARMOR_HITS_PER_LEVEL.getAsInt();
            int maxLevel = CombatConfig.MAX_MOB_PROGRESSION_LEVEL.getAsInt();
            boolean weapon = Progression.isAwakenedCombatWeapon(gear);
            if (mobProgression.locked()) {
                int level = mobProgression.level(actionsPerLevel, maxLevel);
                long bonus = Math.round(level * (weapon
                        ? CombatConfig.WEAPON_DAMAGE_BONUS_PER_LEVEL.getAsDouble()
                        : CombatConfig.ARMOR_REDUCTION_BONUS_PER_LEVEL.getAsDouble()) * 100.0D);
                targetName = targetDetails(mobProgression.target(), level, true);
                if (level >= maxLevel) {
                    targetDetails = Component.translatable(
                            "container.augment_station.weapon_progression_max",
                            bonus);
                } else {
                    int remaining = actionsPerLevel - mobProgression.actions() % actionsPerLevel;
                    targetDetails = Component.translatable(
                            "container.augment_station.weapon_progression",
                            bonus, level + 1, remaining, combatGearActionName(weapon, remaining));
                }
            } else if (!mobProgression.pendingTarget().isEmpty()) {
                int remaining = actionsPerLevel - mobProgression.pendingActions();
                targetName = targetDetails(mobProgression.pendingTarget(), 0, true);
                targetDetails = Component.translatable("container.augment_station.progression_pending", remaining, combatGearActionName(weapon, remaining));
            }
        } else if (isTool(gear)) {
            blockProgression = ToolProgression.data(gear);
            int actionsPerLevel = CombatConfig.TOOL_BLOCKS_PER_LEVEL.getAsInt();
            int maxLevel = CombatConfig.MAX_TOOL_PROGRESSION_LEVEL.getAsInt();
            if (blockProgression.locked()) {
                int level = blockProgression.level(actionsPerLevel, maxLevel);
                long bonus = Math.round(level * CombatConfig.TOOL_MINING_SPEED_BONUS_PER_LEVEL.getAsDouble() * 100.0D);
                targetName = targetDetails(blockProgression.target(), level, false);
                if (level >= maxLevel) {
                    targetDetails = Component.translatable(
                            "container.augment_station.tool_progression_max",
                            bonus);
                } else {
                    int remaining = actionsPerLevel - blockProgression.actions() % actionsPerLevel;
                    targetDetails = Component.translatable(
                            "container.augment_station.tool_progression",
                            bonus, level + 1, remaining, blockName(remaining));
                }
            } else if (!blockProgression.pendingTarget().isEmpty()) {
                int remaining = actionsPerLevel - blockProgression.pendingActions();
                targetName = targetDetails(blockProgression.pendingTarget(), 0, false);
                targetDetails = Component.translatable("container.augment_station.progression_pending", remaining, blockName(remaining));
            }
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
            graphics.text(this.font, gear.getItemName(), this.leftPos + 62, this.topPos + 34, 0xFF404040, false);

            if (menuIndex == 0) {
                graphics.text(this.font, (augment_slots != null ? Component.translatable("container.augment_station.augment_slots", augment_slots) : Component.translatable("container.augment_station.gear_not_awakened")), this.leftPos + 62, this.topPos + 50, 0xFF404040, false);
                graphics.text(this.font, (isCombatGear(gear) ? Component.translatable("container.augment_station.adaptation_slots", mobProgression.slots()) : (isTool(gear) ? Component.translatable("container.augment_station.adaptation_slots", blockProgression.slots()) : Component.translatable("container.augment_station.no_adaptation"))), this.leftPos + 62, this.topPos + 66, 0xFF404040, false);
                if (gear.has(ModDataComponents.WEAKENED)) {
                    graphics.text(this.font, Component.translatable("container.augment_station.gear_weakened"), this.leftPos + 62, this.topPos + 82, 0xFFAAAAAA, false);
                } else if (gear.has(ModDataComponents.FRAGILE)) {
                    graphics.text(this.font, Component.translatable("container.augment_station.gear_fragile"), this.leftPos + 62, this.topPos + 82, 0xFFFF5555, false);
                }
                augmentSlotsLimit(gear);

                if (augment_slots != null) {
                    awakenButton.setMessage(Component.translatable("button.darkechoes.augstation.initresonance"));
                }

                if (!awakenSlot.hasItem()) {
                    awakenButton.setTooltip(Tooltip.create(Component.translatable("container.augment_station.fragile_warning")));
                } else if (awakenSlot.getItem().is(Items.ECHO_SHARD)) {
                    awakenButton.setTooltip(Tooltip.create(Component.translatable("container.augment_station.weakened_warning")));
                }
            } else if (menuIndex == 1) {
//                graphics.drawScrollingString();
//                TODO once multi-target adaptation is implemented, iterate through adaptations
                if (targetName != null) {
                    graphics.text(this.font, targetName, this.leftPos + 62, this.topPos + 47, 0xFF404040, false);
                    graphics.text(this.font, targetDetails, this.leftPos + 62, this.topPos + 63, 0xFF404040, false);
                }
            } else if (menuIndex == 2) {
                if (!augmentSlot.getItem().is(Items.WRITABLE_BOOK) || augmentSlot.getItem().isEmpty() || !gear.has(AUGMENT_SLOTS)) {
                    augmentButton.active = false;
                    augmentButton.setTooltip(Tooltip.create(Component.literal("WIP, only accepts a book & quill! For now adds the malleable augment.")));
                } else {
                    augmentButton.setTooltip(null);
                    augmentButton.active = true;
                }
            } else if (menuIndex == 3) {
                Set<Holder<Augment>> augments = gear.get(ModDataComponents.AUGMENTS) != null ? gear.get(ModDataComponents.AUGMENTS).keySet() : Collections.emptySet();
//                DarkEchoes.LOGGER.info("augments, or something: {}", augments);
                int index = 0;
                for (Holder<Augment> aug : augments) {
                    index++;
                    graphics.text(this.font, aug.value().desc(), this.leftPos + 62, this.topPos + 47 * index, 0xFF404040, false);
                }
            }
        } else {
            resetButton();
        }
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
        double mouseX = event.x() - this.leftPos;
        double mouseY = event.y() - this.topPos;
        int menuIndex = this.menu.augmentStationData.get(0);
        if (event.button() == 0) {
            for (int i = 0; i < 4; i++) {
                int baseY = 4 + (26 * i);
                if (mouseX > 256 && mouseY > baseY && mouseX < 288 && mouseY < baseY + 26) {
                    if (i != menuIndex) {
                        menu.openMenuIndex(i);
                        return true;
                    }
                }
            }
        }

        return super.mouseClicked(event, doubleClick);
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
        awakenButton.active = false;
        awakenButton.setPosition(this.leftPos + 26, this.topPos + 123);
        augmentButton.active = false;
        augmentButton.setPosition(this.leftPos + 26, this.topPos + 123);
        this.addRenderableWidget(awakenButton);
        this.addRenderableWidget(augmentButton);
    }
}
