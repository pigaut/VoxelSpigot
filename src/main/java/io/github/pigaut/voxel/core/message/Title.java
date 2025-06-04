package io.github.pigaut.voxel.core.message;

import io.github.pigaut.voxel.placeholder.*;
import org.bukkit.entity.*;

public record Title(String title, String subtitle, int fadeIn, int stay, int fadeOut) {

    public void send(Player player, PlaceholderSupplier... placeholderSuppliers) {
        final String parsedTitle = StringPlaceholders.parseAll(player, title, placeholderSuppliers);
        final String parsedSubTitle = StringPlaceholders.parseAll(player, subtitle, placeholderSuppliers);
        player.sendTitle(parsedTitle, parsedSubTitle, fadeIn, stay, fadeOut);
    }

}
