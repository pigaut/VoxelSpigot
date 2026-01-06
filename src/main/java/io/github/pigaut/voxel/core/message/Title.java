package io.github.pigaut.voxel.core.message;

import io.github.pigaut.voxel.placeholder.*;
import org.bukkit.entity.*;

import java.util.*;

public record Title(String title, String subtitle, int fadeIn, int stay, int fadeOut) {

    public void send(Player player, Collection<PlaceholderSupplier> placeholderSuppliers) {
        String parsedTitle = StringPlaceholders.parseAll(player, title, placeholderSuppliers);
        String parsedSubTitle = StringPlaceholders.parseAll(player, subtitle, placeholderSuppliers);
        player.sendTitle(parsedTitle, parsedSubTitle, fadeIn, stay, fadeOut);
    }

}
