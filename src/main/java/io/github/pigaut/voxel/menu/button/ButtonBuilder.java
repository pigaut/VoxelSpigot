package io.github.pigaut.voxel.menu.button;

import io.github.pigaut.voxel.menu.*;
import io.github.pigaut.voxel.player.*;
import io.github.pigaut.voxel.util.*;
import org.bukkit.*;
import org.bukkit.event.inventory.*;

import java.util.*;
import java.util.function.*;

public class ButtonBuilder extends IconBuilder {

    private TriConsumer<MenuView, PlayerState, InventoryClickEvent> leftClick = (view, player, event) -> {};
    private TriConsumer<MenuView, PlayerState, InventoryClickEvent> shiftLeftClick = (view, player, event) -> {};
    private TriConsumer<MenuView, PlayerState, InventoryClickEvent> rightClick = (view, player, event) -> {};
    private TriConsumer<MenuView, PlayerState, InventoryClickEvent> shiftRightClick = (view, player, event) -> {};

    ButtonBuilder() {
        super(Material.TERRACOTTA);
    }

    ButtonBuilder(Material type) {
        super(type);
    }

    public Button buildButton() {
        return new Button(this.buildIcon()) {
            @Override
            public void onLeftClick(MenuView view, PlayerState player, InventoryClickEvent event) {
                leftClick.accept(view, player, event);
            }

            @Override
            public void onRightClick(MenuView view, PlayerState player, InventoryClickEvent event) {
                rightClick.accept(view, player, event);
            }

            @Override
            public void onShiftLeftClick(MenuView view, PlayerState player, InventoryClickEvent event) {
                shiftLeftClick.accept(view, player, event);
            }

            @Override
            public void onShiftRightClick(MenuView view, PlayerState player, InventoryClickEvent event) {
                shiftRightClick.accept(view, player, event);
            }
        };
    }

    @Override
    public ButtonBuilder withType(Material type) {
        return (ButtonBuilder) super.withType(type);
    }

    @Override
    public ButtonBuilder withAmount(int amount) {
        return (ButtonBuilder) super.withAmount(amount);
    }

    @Override
    public ButtonBuilder withDisplay(String display) {
        return (ButtonBuilder) super.withDisplay(display);
    }

    @Override
    public ButtonBuilder addLore(String... loreLines) {
        return (ButtonBuilder) super.addLore(loreLines);
    }

    @Override
    public ButtonBuilder addLore(List<String> loreLines) {
        return (ButtonBuilder) super.addLore(loreLines);
    }

    @Override
    public ButtonBuilder enchanted(boolean enchanted) {
        return (ButtonBuilder) super.enchanted(enchanted);
    }

    public ButtonBuilder onLeftClick(TriConsumer<MenuView, PlayerState, InventoryClickEvent> action) {
        this.leftClick = action;
        return this;
    }

    public ButtonBuilder onShiftLeftClick(TriConsumer<MenuView, PlayerState, InventoryClickEvent> action) {
        this.shiftLeftClick = action;
        return this;
    }

    public ButtonBuilder onRightClick(TriConsumer<MenuView, PlayerState, InventoryClickEvent> action) {
        this.rightClick = action;
        return this;
    }

    public ButtonBuilder onShiftRightClick(TriConsumer<MenuView, PlayerState, InventoryClickEvent> action) {
        this.shiftRightClick = action;
        return this;
    }

}
