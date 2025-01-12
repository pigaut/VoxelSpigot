package io.github.pigaut.voxel.command.execution;

import org.bukkit.entity.*;

@FunctionalInterface
public interface PlayerExecution {

    void execute(Player player, String[] args);

}
