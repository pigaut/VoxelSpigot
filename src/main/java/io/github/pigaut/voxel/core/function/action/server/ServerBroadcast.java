package io.github.pigaut.voxel.core.function.action.server;

import io.github.pigaut.voxel.bukkit.*;

public class ServerBroadcast implements ServerAction {

    private final String message;

    public ServerBroadcast(String message) {
        this.message = message;
    }

    @Override
    public void execute() {
        Chat.sendAll(message);
    }

}
