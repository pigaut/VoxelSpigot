package io.github.pigaut.voxel.menu.template.button;

import io.github.pigaut.voxel.menu.*;
import io.github.pigaut.voxel.menu.button.*;
import io.github.pigaut.voxel.player.*;
import io.github.pigaut.yaml.convert.format.*;
import org.bukkit.*;
import org.bukkit.event.inventory.*;
import org.jetbrains.annotations.*;

public class ValueInputButton extends Button {

    private final String value;

    public ValueInputButton(@NotNull Material icon, @NotNull String value) {
        super(IconBuilder.of(icon)
                .withDisplay(CaseFormatter.toTitleCase(value))
                .addLore("")
                .addLore("&eLeft-Click: &fto select value")
                .buildIcon());
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public void onLeftClick(MenuView view, PlayerState player, InventoryClickEvent event) {
        player.setLastInput(value);
    }

}
