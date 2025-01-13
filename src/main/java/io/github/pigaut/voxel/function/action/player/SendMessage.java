package io.github.pigaut.voxel.function.action.player;

import io.github.pigaut.voxel.message.*;
import io.github.pigaut.voxel.player.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.loader.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class SendMessage implements PlayerAction {

    private final Message message;

    public SendMessage(Message message) {
        this.message = message;
    }

    @Override
    public void execute(@NotNull PluginPlayer player) {
        player.sendMessage(message);
    }

}
