package io.github.pigaut.voxel.command;

import com.google.common.base.*;
import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.voxel.plugin.manager.*;
import org.bukkit.command.defaults.*;

import java.util.*;

public class CommandManager extends Manager {

    private final Map<String, EnhancedCommand> customCommands = new HashMap<>();

    public CommandManager(EnhancedJavaPlugin plugin) {
        super(plugin);
    }

    @Override
    public void enable() {
        //Initialize Spigot Command Manager
        plugin.getCommand("");
    }

    public EnhancedCommand getCustomCommand(String name) {
        return customCommands.get(name);
    }

    public void registerCommand(EnhancedCommand command) {
        CommandUtil.registerCommand(command);
        customCommands.put(command.getName(), command);
    }

    public void unregisterCommand(String name) {
        BukkitCommand command = customCommands.get(name);
        Preconditions.checkNotNull(command, "Command not found for given name");
        CommandUtil.unregisterCommand(command);
        customCommands.remove(name);
    }

}
