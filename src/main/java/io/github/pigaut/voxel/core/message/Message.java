package io.github.pigaut.voxel.core.message;

import io.github.pigaut.voxel.placeholder.*;
import io.github.pigaut.voxel.plugin.manager.*;
import org.bukkit.entity.*;
import org.jetbrains.annotations.*;

public interface Message extends Identifiable {

    @NotNull MessageType getType();

    void send(@NotNull Player player, PlaceholderSupplier... placeholderSuppliers);

}
