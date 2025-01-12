package io.github.pigaut.voxel.command.completion;

import org.bukkit.command.*;
import org.jetbrains.annotations.*;

import java.util.*;

@FunctionalInterface
public interface CommandCompletion {

    CommandCompletion EMPTY = (sender, args) -> List.of();

    @NotNull List<@NotNull String> tabComplete(CommandSender sender, String[] args);

}
