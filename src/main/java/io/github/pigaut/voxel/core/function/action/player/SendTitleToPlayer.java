package io.github.pigaut.voxel.core.function.action.player;

import io.github.pigaut.voxel.core.message.*;
import io.github.pigaut.voxel.core.message.impl.*;
import io.github.pigaut.voxel.player.*;
import org.jetbrains.annotations.*;

public class SendTitleToPlayer implements PlayerAction {

    private final Message title;

    public SendTitleToPlayer(String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        this.title = new TitleMessage(title, subtitle, fadeIn, stay, fadeOut);
    }

    @Override
    public void execute(@NotNull PlayerState player) {
        player.sendMessage(title);
    }

}
