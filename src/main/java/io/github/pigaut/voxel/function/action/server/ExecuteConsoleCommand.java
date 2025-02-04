package io.github.pigaut.voxel.function.action.server;

import io.github.pigaut.voxel.function.action.*;
import io.github.pigaut.voxel.meta.placeholder.*;
import io.github.pigaut.voxel.player.*;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.jetbrains.annotations.*;

public class ExecuteConsoleCommand implements Action {

    private final String command;

    public ExecuteConsoleCommand(String command) {
        this.command = command;
    }

    @Override
    public void execute(@Nullable PluginPlayer player, @Nullable Event event, @Nullable Block block, @Nullable Entity target) {
        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
        if (player != null) {
            Bukkit.dispatchCommand(console, StringPlaceholders.parseAll(player.asPlayer(), command, player.getPlaceholders()));
            return;
        }
        Bukkit.dispatchCommand(console, StringPlaceholders.parseAll(command));
    }
}
