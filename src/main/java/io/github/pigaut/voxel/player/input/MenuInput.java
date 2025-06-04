package io.github.pigaut.voxel.player.input;

import io.github.pigaut.voxel.menu.*;
import io.github.pigaut.voxel.menu.template.button.*;
import io.github.pigaut.voxel.menu.template.menu.*;
import io.github.pigaut.voxel.player.*;
import io.github.pigaut.voxel.plugin.runnable.*;
import org.jetbrains.annotations.*;

import java.util.*;
import java.util.function.*;

public class MenuInput extends PlayerInput {

    private List<ValueInputButton> valueEntries = new ArrayList<>();

    public MenuInput(SimplePlayerState player) {
        super(player, "Select a value");
    }

    @Override
    public MenuInput withDescription(@NotNull String inputDescription) {
        return (MenuInput) super.withDescription(inputDescription);
    }

    @Override
    public MenuInput onInput(@NotNull Consumer<String> inputCollector) {
        return (MenuInput) super.onInput(inputCollector);
    }

    @Override
    public MenuInput onCancel(@NotNull Runnable onCancel) {
        return (MenuInput) super.onCancel(onCancel);
    }

    public MenuInput withValues(@NotNull List<ValueInputButton> entries) {
        this.valueEntries = entries;
        return this;
    }

    public MenuInput addValue(@NotNull ValueInputButton entry) {
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
                    playerState.setOpenView(selectionView.getPreviousView());
                    onCancel.run();
                    cancel();
                    return;
                }

                final String input = playerState.getLastInput();
                if (input != null) {
                    playerState.setAwaitingInput(null);
                    inputCollector.accept(input);
                    cancel();
                }
            }
        }.runTaskTimer(0, 5);

    }

}
