package io.github.pigaut.voxel.menu.button;

import io.github.pigaut.voxel.menu.*;
import io.github.pigaut.voxel.player.*;
import io.github.pigaut.voxel.util.*;
import org.bukkit.*;
import org.bukkit.event.inventory.*;

import java.util.*;
import java.util.function.*;

public class ButtonBuilder extends IconBuilder {

    private BiConsumer<MenuView, PlayerState> leftClick = (view, player) -> {};
    private BiConsumer<MenuView, PlayerState> shiftLeftClick = (view, player) -> {};
    private BiConsumer<MenuView, PlayerState> rightClick = (view, player) -> {};
    private BiConsumer<MenuView, PlayerState> shiftRightClick = (view, player) -> {};

    ButtonBuilder() {
        super(Material.TERRACOTTA);
    }

    ButtonBuilder(Material type) {
        super(type);
    }

    public Button buildButton() {
        return new Button(this.buildIcon()) {
            @Override
            public void onLeftClick(MenuView view, PlayerState player) {
                leftClick.accept(view, player);
            }

            @Override
            public void onRightClick(MenuView view, PlayerState player) {
                rightClick.accept(view, player);
            }

            @Override
            public void onShiftLeftClick(MenuView view, PlayerState player) {
                shiftLeftClick.accept(view, player);
            }

            @Override
            public void onShiftRightClick(MenuView view, PlayerState player) {
                shiftRightClick.accept(view, player);
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
    public ButtonBuilder addLeftClickLore(String action) {
        return (ButtonBuilder) super.addLeftClickLore(action);
    }

    @Override
    public ButtonBuilder addRightClickLore(String action) {
        return (ButtonBuilder) super.addRightClickLore(action);
    }

    @Override
    public ButtonBuilder addShiftLeftClickLore(String action) {
        return (ButtonBuilder) super.addShiftLeftClickLore(action);
    }

    @Override
    public ButtonBuilder addShiftRightClickLore(String action) {
        return (ButtonBuilder) super.addShiftRightClickLore(action);
    }

    @Override
    public ButtonBuilder enchanted(boolean enchanted) {
        return (ButtonBuilder) super.enchanted(enchanted);
    }

    public ButtonBuilder onLeftClick(BiConsumer<MenuView, PlayerState> action) {
        this.leftClick = action;
        return this;
    }

    public ButtonBuilder onShiftLeftClick(BiConsumer<MenuView, PlayerState> action) {
        this.shiftLeftClick = action;
        return this;
    }

    public ButtonBuilder onRightClick(BiConsumer<MenuView, PlayerState> action) {
        this.rightClick = action;
        return this;
    }

    public ButtonBuilder onShiftRightClick(BiConsumer<MenuView, PlayerState> action) {
        this.shiftRightClick = action;
        return this;
    }

}
