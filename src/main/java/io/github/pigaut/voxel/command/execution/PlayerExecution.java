package io.github.pigaut.voxel.command.execution;

import io.github.pigaut.voxel.placeholder.*;
import org.bukkit.entity.*;

@FunctionalInterface
public interface PlayerExecution {

    void execute(Player player, String[] args, PlaceholderSupplier placeholders);

}
