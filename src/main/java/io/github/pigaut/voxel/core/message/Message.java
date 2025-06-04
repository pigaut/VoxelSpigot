package io.github.pigaut.voxel.core.message;

import io.github.pigaut.voxel.placeholder.*;
import io.github.pigaut.voxel.plugin.manager.*;
import org.bukkit.entity.*;

public interface Message extends Identifiable {

    void send(Player player, PlaceholderSupplier... placeholderSuppliers);

}
