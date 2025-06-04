package io.github.pigaut.voxel.menu.template.button;

import io.github.pigaut.voxel.menu.*;
import io.github.pigaut.voxel.menu.button.*;
import io.github.pigaut.yaml.*;
import org.bukkit.*;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.*;

public class ConfigSaveButton extends Button {

    public static final ItemStack icon = IconBuilder.of(Material.WRITABLE_BOOK)
            .withDisplay("&f&lSave To File")
            .addLore("")
            .addLore("&eLeft-Click: &fSave data to YAML file")
            .enchanted(true)
            .buildIcon();

    private final ConfigRoot root;

    public ConfigSaveButton(ConfigRoot root) {
        super(icon);
        this.root = root;
    }

    @Override
    public void onLeftClick(MenuView view, InventoryClickEvent event) {
        root.save();
    }

}
