package io.github.pigaut.voxel.command;

import com.google.common.base.*;
import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.voxel.plugin.boot.*;
import io.github.pigaut.voxel.plugin.manager.*;
import org.bukkit.command.defaults.*;

import java.util.*;

public class CommandRegistry {

    private final EnhancedJavaPlugin plugin;
    private final Map<String, EnhancedCommand> customCommands = new HashMap<>();
    private boolean initialized = false;

    public CommandRegistry(EnhancedJavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void init() {
        //Initialize Spigot Command Manager
        plugin.getCommand("");
        initialized = true;
    }

    public EnhancedCommand getCustomCommand(String name) {
        return customCommands.get(name);
    }

    public void registerCommand(EnhancedCommand command) {
        Preconditions.checkArgument(initialized, "Command registry has not been initialized.");
        CommandUtil.registerCommand(command);
        customCommands.put(command.getName(), command);
    }

    public void unregisterCommand(String name) {
        Preconditions.checkArgument(initialized, "Command registry has not been initialized.");
        BukkitCommand command = customCommands.get(name);
        if (command != null) {
            CommandUtil.unregisterCommand(command);
            customCommands.remove(name);
        }
    }

}
