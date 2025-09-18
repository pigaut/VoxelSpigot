package io.github.pigaut.voxel.player.input;

import io.github.pigaut.voxel.menu.*;
import io.github.pigaut.voxel.menu.template.button.*;
import io.github.pigaut.voxel.menu.template.menu.*;
import io.github.pigaut.voxel.player.*;
import io.github.pigaut.voxel.plugin.runnable.*;
import io.github.pigaut.yaml.configurator.deserialize.*;
import io.github.pigaut.yaml.convert.parse.*;
import org.jetbrains.annotations.*;

import java.util.*;
import java.util.function.*;

public class MenuInput<T> extends PlayerInput<T> {

    private List<ValueInputButton> valueEntries = new ArrayList<>();

    public MenuInput(SimplePlayerState player, Deserializer<T> deserializer) {
        super(player, deserializer, "Select a value");
    }

    @Override
    public MenuInput<T> withDescription(@NotNull String inputDescription) {
        return (MenuInput<T>) super.withDescription(inputDescription);
    }

    @Override
    public MenuInput<T> withInputCollector(@NotNull Consumer<T> inputCollector) {
        return (MenuInput<T>) super.withInputCollector(inputCollector);
    }

    @Override
    public MenuInput<T> withCancelTask(@NotNull Runnable cancelTask) {
        return (MenuInput<T>) super.withCancelTask(cancelTask);
    }

    public MenuInput<T> withValues(@NotNull List<ValueInputButton> entries) {
        for (ValueInputButton entry : entries) {
            try {
                deserializer.deserialize(entry.getValue());
            } catch (StringParseException e) {
                throw new IllegalStateException("Invalid menu input selection value. " + e.getMessage());
            }
        }
        this.valueEntries = entries;
        return this;
    }

    public MenuInput<T> addValue(@NotNull ValueInputButton entry) {
        try {
            deserializer.deserialize(entry.getValue());
        } catch (StringParseException e) {
            throw new IllegalStateException("Invalid menu input selection value. " + e.getMessage());
        }
        this.valueEntries.add(entry);
        return this;
    }

    @Override
    public void collect() {
        if (playerState.isAwaitingInput()) {
            throw new IllegalStateException("Player is already being asked for input.");
        }

        final MenuView selectionView = playerState.openMenu(new ValueSelectionMenu(inputDescription, 45, valueEntries));
        playerState.setAwaitingInput(InputType.MENU);
        playerState.setLastInput(null);

        new PluginRunnable(playerState.getPlugin()) {
            @Override
            public void run() {
                if (!playerState.isAwaitingInput(InputType.MENU)) {
                    final MenuView previousView = selectionView.getPreviousView();
                    if (previousView != null) {
                        previousView.open();
                    }
                    onCancel.run();
                    cancel();
                    return;
                }

                final String input = playerState.getLastInput();
                if (input == null) {
                    return;
                }

                final T parsedInput;
                try {
                    parsedInput = deserializer.deserialize(input);
                } catch (StringParseException e) {
                    playerState.setLastInput(null);
                    return;
                }

                playerState.setAwaitingInput(null);
                inputCollector.accept(parsedInput);
                cancel();
            }
        }.runTaskTimer(0, 5);

    }

}
