package io.github.pigaut.voxel.menu.template.button;

import io.github.pigaut.voxel.menu.*;
import io.github.pigaut.voxel.menu.button.*;
import io.github.pigaut.voxel.player.*;
import org.bukkit.*;
import org.bukkit.event.inventory.*;

public class MainMenuButton extends Button {

    public MainMenuButton() {
        super(IconBuilder.of(Material.MINECART)
                .withDisplay("&5Main Menu")
                .enchanted(true)
                .buildIcon());
    }

    @Override
    public void onLeftClick(MenuView view, PlayerState player, InventoryClickEvent event) {
        final MenuView firstView = view.getFirstView();
        if (view == firstView) {
            return;
        }
        firstView.open();
    }

}