package io.github.pigaut.voxel.core.function.action.player;

import io.github.pigaut.voxel.core.message.*;
import io.github.pigaut.voxel.core.message.impl.*;
import io.github.pigaut.voxel.player.*;
import org.jetbrains.annotations.*;

public class SendActionbarToPlayer implements PlayerAction {

    private final Message actionbar;

    public SendActionbarToPlayer(String message) {
        this.actionbar = new ActionBarMessage(message);
    }

    @Override
    public void execute(@NotNull PlayerState player) {
        player.sendMessage(actionbar);
    }

}
