package io.github.pigaut.voxel.command.execution;

import io.github.pigaut.voxel.placeholder.*;
import org.bukkit.command.*;

@FunctionalInterface
public interface CommandExecution {

    void execute(CommandSender sender, String[] args, PlaceholderSupplier placeholders);

}
