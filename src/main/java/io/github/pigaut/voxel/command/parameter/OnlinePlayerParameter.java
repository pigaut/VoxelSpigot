package io.github.pigaut.voxel.command.parameter;

import io.github.pigaut.voxel.server.*;

public class OnlinePlayerParameter extends CommandParameter {

    public OnlinePlayerParameter() {
        super("online-player", (sender, args) -> SpigotServer.getOnlinePlayerNames());
    }

}
