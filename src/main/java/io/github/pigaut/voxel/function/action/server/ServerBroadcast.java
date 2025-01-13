package io.github.pigaut.voxel.function.action.server;

import io.github.pigaut.voxel.util.*;

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
