package io.github.pigaut.voxel.menu.template.button;

import io.github.pigaut.voxel.menu.*;
import io.github.pigaut.voxel.menu.button.*;
import org.bukkit.*;
import org.bukkit.event.inventory.*;

public class CloseMenuButton extends Button {

    public CloseMenuButton() {
        super(IconBuilder.of(Material.BARRIER)
                .withDisplay("&c&lClose")
                .addLore("")
                .addLore("&eLeft-Click: &fClose this menu")
                .enchanted(true).buildIcon());
    }

    @Override
    public void onLeftClick(MenuView view, InventoryClickEvent event) {
        view.close();
    }

}
