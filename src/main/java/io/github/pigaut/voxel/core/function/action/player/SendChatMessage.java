package io.github.pigaut.voxel.core.function.action.player;

import io.github.pigaut.voxel.placeholder.*;
import io.github.pigaut.voxel.player.*;
import io.github.pigaut.voxel.util.*;
import org.jetbrains.annotations.*;

public class SendChatMessage implements PlayerAction {

    private final String message;

    public SendChatMessage(String message) {
        this.message = StringColor.translateColors(message);
    }

    @Override
    public void execute(@NotNull PlayerState player) {
        player.sendMessage(StringPlaceholders.parseAll(message, player.getPlaceholders()));
    }

}
