package io.github.pigaut.voxel.core.function.action.player;

import io.github.pigaut.voxel.bukkit.*;
import io.github.pigaut.voxel.placeholder.*;
import io.github.pigaut.voxel.player.*;
import org.jetbrains.annotations.*;

public class SendChatToPlayer implements PlayerAction {

    private final String message;

    public SendChatToPlayer(String message) {
        this.message = StringColor.translateColors(message);
    }

    @Override
    public void execute(@NotNull PlayerState player) {
        String parsedMessage = StringPlaceholders.parseAll(player.asPlayer(), message, player.getPlaceholders());
        player.sendMessage(parsedMessage);
    }

}
