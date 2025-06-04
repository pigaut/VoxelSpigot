package io.github.pigaut.voxel.core.function.action.player;

import io.github.pigaut.voxel.core.message.*;
import io.github.pigaut.voxel.player.*;
import org.jetbrains.annotations.*;

public class SendMessage implements PlayerAction {

    private final Message message;

    public SendMessage(Message message) {
        this.message = message;
    }

    @Override
    public void execute(@NotNull PlayerState player) {
        player.sendMessage(message);
    }

}
