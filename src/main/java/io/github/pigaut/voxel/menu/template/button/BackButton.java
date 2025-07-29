package io.github.pigaut.voxel.menu.template.button;

import io.github.pigaut.voxel.menu.*;
import io.github.pigaut.voxel.menu.button.*;
import io.github.pigaut.voxel.player.*;
import org.bukkit.*;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.*;

public class BackButton extends Button {

    public BackButton() {
        super(IconBuilder.of(Material.SPRUCE_DOOR)
                .withDisplay("&cBack")
                .enchanted(true)
                .buildIcon());
    }

    @Override
    public void onLeftClick(MenuView view, PlayerState player, InventoryClickEvent event) {
        final MenuView previousView = view.getPreviousView();
        if (previousView != null) {
            previousView.open();
        }
    }

}
