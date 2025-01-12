package io.github.pigaut.voxel.message;

import io.github.pigaut.voxel.meta.placeholder.*;
import org.bukkit.entity.*;

public interface Message {

    void send(Player player);

    void send(Player player, PlaceholderSupplier... placeholderSuppliers);

}
