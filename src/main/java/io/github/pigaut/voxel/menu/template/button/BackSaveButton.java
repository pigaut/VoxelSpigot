package io.github.pigaut.voxel.menu.template.button;

import io.github.pigaut.voxel.menu.*;
import io.github.pigaut.voxel.menu.button.*;
import io.github.pigaut.voxel.player.*;
import io.github.pigaut.yaml.*;
import org.bukkit.*;
import org.bukkit.event.inventory.*;

public class BackSaveButton extends Button {

    private final ConfigRoot config;

    public BackSaveButton(ConfigRoot config) {
        super(IconBuilder.of(Material.SPRUCE_DOOR)
                .withDisplay("&cSave & Back")
                .enchanted(true)
                .buildIcon());
        this.config = config;
    }

    @Override
    public void onLeftClick(MenuView view, PlayerState player) {
        final MenuView previousView = view.getPreviousView();
        if (previousView != null) {
            previousView.open();
        }
    }

}
