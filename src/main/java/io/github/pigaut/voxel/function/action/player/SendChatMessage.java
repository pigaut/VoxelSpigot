package io.github.pigaut.voxel.function.action.player;

import io.github.pigaut.voxel.player.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.loader.*;
import io.github.pigaut.yaml.formatter.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class SendChatMessage implements PlayerAction {

    private final String message;

    public SendChatMessage(String message) {
        this.message = message;
    }

    @Override
    public void execute(@NotNull PluginPlayer player) {
        player.sendMessage(message);
    }

}
