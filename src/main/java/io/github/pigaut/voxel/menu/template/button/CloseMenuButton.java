package io.github.pigaut.voxel.menu.template.button;

import io.github.pigaut.voxel.menu.*;
import io.github.pigaut.voxel.menu.button.*;
import io.github.pigaut.voxel.player.*;
import org.bukkit.*;
import org.bukkit.event.inventory.*;

public class CloseMenuButton extends Button {

    public CloseMenuButton() {
        super(IconBuilder.of(Material.RED_WOOL)
                .withDisplay("&4Close")
                .enchanted(true).buildIcon());
    }

    @Override
    public void onLeftClick(MenuView view, PlayerState player, InventoryClickEvent event) {
        view.close();
    }

}
