package io.github.pigaut.voxel.command;

import io.github.pigaut.voxel.command.node.*;
import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.yaml.parser.*;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.command.defaults.*;
import org.jetbrains.annotations.*;

import java.util.*;

public abstract class EnhancedCommand extends BukkitCommand {

    protected final EnhancedPlugin plugin;
    protected final RootCommand rootCommand;

    protected EnhancedCommand(@NotNull EnhancedPlugin plugin, @NotNull String name) {
        super(name);
        this.plugin = plugin;
        this.rootCommand = new RootCommand(name, plugin);
    }

    public void setAliases(String... aliases) {
        super.setAliases(Arrays.asList(aliases));
    }

    public RootCommand getRootCommand() {
        return rootCommand;
    }

    public SubCommand createSubCommand(String command) {
        return rootCommand.createSubCommand(command);
    }

    public void addSubCommand(SubCommand subCommand) {
        rootCommand.addSubCommand(subCommand);
    }

    @Override
    public boolean execute(CommandSender sender, String alias, String[] args) {
        CommandNode currentCommand = rootCommand;
        String[] subArgs = new String[0];
        for (int i = 0; i < args.length; i++) {
            final SubCommand foundCommand = currentCommand.getSubCommand(args[i]);
            if (foundCommand != null) {
                currentCommand = foundCommand;
                continue;
            }
            subArgs = Arrays.copyOfRange(args, i, args.length);
            break;
        }

        currentCommand.execute(sender, subArgs);
        return true;
    }

    @Override
    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) {
        CommandNode currentCommand = rootCommand;
        String[] subArgs = new String[0];
        for (int i = 0; i < args.length; i++) {
            final SubCommand foundCommand = currentCommand.getSubCommand(args[i]);
            if (foundCommand != null) {
                currentCommand = foundCommand;
                continue;
            }
            subArgs = Arrays.copyOfRange(args, i, args.length);
            break;
        }
        return currentCommand.tabComplete(sender, subArgs);
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args, @Nullable Location location) throws IllegalArgumentException {
        return tabComplete(sender, alias, args);
    }

}
