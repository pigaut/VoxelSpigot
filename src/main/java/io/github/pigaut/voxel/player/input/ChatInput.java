package io.github.pigaut.voxel.player.input;

import io.github.pigaut.voxel.menu.*;
import io.github.pigaut.voxel.player.*;
import io.github.pigaut.voxel.plugin.runnable.*;
import org.bukkit.entity.*;

public class ChatInput extends PlayerInput {

    public ChatInput(SimplePlayerState player) {
        super(player, "Enter a value in chat");
    }

    public void collect() {
        if (playerState.isAwaitingInput()) {
            throw new IllegalStateException("Player is already being asked for input.");
        }

        playerState.setAwaitingInput(InputType.CHAT);
        playerState.setLastInput(null);

        final MenuView previousView = playerState.getOpenMenu();

        final Player player = playerState.asPlayer();
        if (previousView != null) {
            previousView.close();
        }
        player.sendTitle(inputDescription, "Type ESC to cancel", 10, 3600, 10);

        new PluginRunnable(playerState.getPlugin()) {
            @Override
            public void run() {
                if (!playerState.isAwaitingInput(InputType.CHAT)) {
                    player.resetTitle();
                    playerState.setOpenMenu(previousView);
                    onCancel.run();
                    cancel();
                    return;
                }

                final String input = playerState.getLastInput();
                if (input != null) {
                    player.resetTitle();
                    playerState.setAwaitingInput(null);
                    inputCollector.accept(input);
                    cancel();
                }
            }
        }.runTaskTimer(0, 5);
    }

}
