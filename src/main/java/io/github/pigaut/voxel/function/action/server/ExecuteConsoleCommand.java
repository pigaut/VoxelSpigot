package io.github.pigaut.voxel.function.action.server;

import org.bukkit.*;
import org.bukkit.command.*;

public class ExecuteConsoleCommand implements ServerAction {

    private final String command;

    public ExecuteConsoleCommand(String command) {
        this.command = command;
    }

    @Override
    public void execute() {
        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
        Bukkit.dispatchCommand(console, command);
    }

}
