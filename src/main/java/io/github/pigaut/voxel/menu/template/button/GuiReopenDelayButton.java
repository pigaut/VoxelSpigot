package io.github.pigaut.voxel.menu.template.button;

import io.github.pigaut.voxel.menu.*;
import io.github.pigaut.voxel.menu.button.*;
import io.github.pigaut.voxel.player.*;
import io.github.pigaut.voxel.plugin.*;
import org.bukkit.*;
import org.bukkit.event.inventory.*;

public class GuiReopenDelayButton extends Button {

    private final Settings settings;

    public GuiReopenDelayButton(EnhancedPlugin plugin) {
        super(IconBuilder.of(Material.LEVER)
                .enchanted(true)
                .withDisplay("&aView Time: &f" + plugin.getSettings().guiReopenDelay + " ticks" +
                        (plugin.getSettings().guiReopenDelay == 200 ? " &c(max)" : plugin.getSettings().guiReopenDelay == 5 ? " &c(min)" : ""))
                .addLore("The time delay after which the menu reopens")
                .addLore("when viewing messages, particles, etc..")
                .addLore("")
                .addLeftClickLore("To increment by 5 ticks")
                .addRightClickLore("To decrease by 5 ticks")
                .addShiftLeftClickLore("To increment by 20 ticks")
                .addShiftRightClickLore("To decrease by 20 ticks")
                .buildIcon());
        this.settings = plugin.getSettings();
    }

    @Override
    public boolean updateOnClick() {
        return true;
    }

    @Override
    public void onLeftClick(MenuView view, PlayerState player, InventoryClickEvent event) {
        settings.guiReopenDelay = Math.min(settings.guiReopenDelay + 5, 200);
    }

    @Override
    public void onRightClick(MenuView view, PlayerState player, InventoryClickEvent event) {
        settings.guiReopenDelay = Math.max(settings.guiReopenDelay - 5, 5);
    }

    @Override
    public void onShiftLeftClick(MenuView view, PlayerState player, InventoryClickEvent event) {
        settings.guiReopenDelay = Math.min(settings.guiReopenDelay + 20, 200);
    }

    @Override
    public void onShiftRightClick(MenuView view, PlayerState player, InventoryClickEvent event) {
        settings.guiReopenDelay = Math.max(settings.guiReopenDelay - 20, 5);
    }

}
