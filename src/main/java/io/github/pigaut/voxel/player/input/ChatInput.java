package io.github.pigaut.voxel.player.input;

import io.github.pigaut.voxel.menu.*;
import io.github.pigaut.voxel.player.*;
import io.github.pigaut.voxel.plugin.runnable.*;
import io.github.pigaut.yaml.configurator.deserialize.*;
import io.github.pigaut.yaml.convert.parse.*;
import org.bukkit.*;
import org.bukkit.entity.*;

public class ChatInput<T> extends PlayerInput<T> {

    public ChatInput(SimplePlayerState player, Deserializer<T> deserializer) {
        super(player, deserializer, "Enter a value in chat");
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
        player.sendTitle(inputDescription, "Type ESC to cancel", 10, Integer.MAX_VALUE, 10);

        new PluginRunnable(playerState.getPlugin()) {
            int resetErrorTitle = 0;
            @Override
            public void run() {
                if (!playerState.isAwaitingInput(InputType.CHAT)) {
                    player.resetTitle();
                    playerState.setOpenMenu(previousView);

                    onCancel.run();
                    cancel();
                    return;
                }

                if (resetErrorTitle > 0) {
                    resetErrorTitle--;
                    if (resetErrorTitle == 0) {
                        player.sendTitle(inputDescription, "Type ESC to cancel", 10, Integer.MAX_VALUE, 10);
                    }
                }

                final String input = playerState.getLastInput();
                if (input == null) {
                    return;
                }

                final T parsedInput;
                try {
                    parsedInput = deserializer.deserialize(input);
                } catch (StringParseException e) {
                    player.sendTitle(ChatColor.RED + e.getMessage(), "Type ESC to cancel", 0, 40, 0);
                    playerState.setLastInput(null);
                    resetErrorTitle = 8;
                    return;
                }

                player.resetTitle();
                playerState.setAwaitingInput(null);
                inputCollector.accept(parsedInput);
                cancel();
            }
        }.runTaskTimer(0, 5);
    }

}
