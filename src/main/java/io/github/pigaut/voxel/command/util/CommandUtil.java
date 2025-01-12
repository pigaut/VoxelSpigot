package io.github.pigaut.voxel.command.util;

import com.google.common.base.*;
import io.github.pigaut.voxel.command.*;
import io.github.pigaut.voxel.util.*;
import org.bukkit.*;
import org.bukkit.command.*;

import java.lang.reflect.*;
import java.util.*;

public class CommandUtil {

    private static final Field COMMAND_MAP_FIELD = ReflectionUtil.getDeclaredField(Bukkit.getServer().getClass(), "commandMap");
    private static final Field KNOWN_COMMANDS_FIELD = ReflectionUtil.getDeclaredField(SimpleCommandMap.class, "knownCommands");

    private CommandUtil() {}

    public static void registerCommand(Command command) {
        Preconditions.checkNotNull(command, "Command cannot be null");

        CommandMap serverCommands = getServerCommands();
        serverCommands.register(command.getLabel(), command);
    }

    public static void registerCommands(Command... commands) {
        if (commands.length == 0) return;

        CommandMap serverCommands = getServerCommands();

        for (Command command : commands) {
            if (command != null)
                serverCommands.register(command.getLabel(), command);
        }
    }

    public static void unregisterCommand(Command command) {
        Preconditions.checkNotNull(command, "Command cannot be null");

        CommandMap serverCommands = getServerCommands();
        Map<String, Command> knownCommands = getKnownCommands(serverCommands);

        knownCommands.remove(command.getName());
        command.unregister(serverCommands);
    }

    public static void unregisterCommand(String name) {
        Preconditions.checkNotNull(name, "Command name cannot be null");

        CommandMap serverCommands = getServerCommands();

        Command command = serverCommands.getCommand(name);
        Preconditions.checkNotNull(command, "No command found for the given name");

        Map<String, Command> knownCommands = getKnownCommands(serverCommands);

        knownCommands.remove(command.getName());
        command.unregister(serverCommands);
    }

    public static CommandMap getServerCommands() {
        final Field commandMapField = ReflectionUtil.getDeclaredField(Bukkit.getServer().getClass(), "commandMap");
        final CommandMap commandMap = ReflectionUtil.accessField(commandMapField, CommandMap.class, Bukkit.getServer());
        Preconditions.checkState(commandMap != null, "Unable to retrieve the command map.");
        return commandMap;
    }

    public static Map<String, Command> getKnownCommands() {
        CommandMap serverCommands = getServerCommands();
        return getKnownCommands(serverCommands);
    }

    public static Map<String, Command> getKnownCommands(CommandMap commandMap) {
        final Field knownCommandsField = ReflectionUtil.getDeclaredField(SimpleCommandMap.class, "knownCommands");
        final Map<String, Command> knownCommands = (Map<String, Command>) ReflectionUtil.accessField(knownCommandsField, commandMap);
        Preconditions.checkState(knownCommands != null, "Unable to retrieve known commands map.");
        return knownCommands;
    }

}
