package io.github.pigaut.voxel.menu.button;

import io.github.pigaut.voxel.menu.*;
import io.github.pigaut.yaml.parser.*;
import org.bukkit.*;
import org.bukkit.event.inventory.*;

import java.util.function.*;

public class ButtonBuilder extends IconBuilder {

    private BiConsumer<MenuView, InventoryClickEvent> leftClick = (view, event) -> {};
    private BiConsumer<MenuView, InventoryClickEvent> shiftLeftClick = (view, event) -> {};
    private BiConsumer<MenuView, InventoryClickEvent> rightClick = (view, event) -> {};
    private BiConsumer<MenuView, InventoryClickEvent> shiftRightClick = (view, event) -> {};

    ButtonBuilder() {
        super(Material.TERRACOTTA);
    }

    ButtonBuilder(Material type) {
        super(type);
    }

    public Button buildButton() {
        return new Button(this.buildIcon()) {
            @Override
            public void onLeftClick(MenuView view, InventoryClickEvent event) {
                leftClick.accept(view, event);
            }

            @Override
            public void onRightClick(MenuView view, InventoryClickEvent event) {
                rightClick.accept(view, event);
            }

            @Override
            public void onShiftLeftClick(MenuView view, InventoryClickEvent event) {
                shiftLeftClick.accept(view, event);
            }

            @Override
            public void onShiftRightClick(MenuView view, InventoryClickEvent event) {
                shiftRightClick.accept(view, event);
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
    public ButtonBuilder addLore(String line) {
        return (ButtonBuilder) super.addLore(line);
    }

    @Override
    public ButtonBuilder enchanted(boolean enchanted) {
        return (ButtonBuilder) super.enchanted(enchanted);
    }

    public ButtonBuilder onLeftClick(BiConsumer<MenuView, InventoryClickEvent> action) {
        this.leftClick = action;
        return this;
    }

    public ButtonBuilder onShiftLeftClick(BiConsumer<MenuView, InventoryClickEvent> action) {
        this.shiftLeftClick = action;
        return this;
    }

    public ButtonBuilder onRightClick(BiConsumer<MenuView, InventoryClickEvent> action) {
        this.rightClick = action;
        return this;
    }

    public ButtonBuilder onShiftRightClick(BiConsumer<MenuView, InventoryClickEvent> action) {
        this.shiftRightClick = action;
        return this;
    }

}
