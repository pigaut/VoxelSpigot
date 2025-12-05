package io.github.pigaut.voxel.player.input;

import io.github.pigaut.voxel.player.*;
import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.yaml.convert.parse.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.jetbrains.annotations.*;

import java.util.function.*;

public class ChatInput<T> extends GenericInputCollector<T> {

    private final EnhancedPlugin plugin;
    private final Player player;
    private String description = "Enter a value in chat";

    public ChatInput(@NotNull EnhancedPlugin plugin, @NotNull PlayerState player, @NotNull Parser<T> parser) {
        super(player, parser);
        this.plugin = plugin;
        this.player = player.asPlayer();
    }

    @Override
    public @NotNull InputSource getInputSource() {
        return InputSource.CHAT;
    }

    @Override
    public void onStart() {
        player.sendTitle(description, "Type ESC to cancel", 10, Short.MAX_VALUE, 0);
    }

    @Override
    public void onSuccess(@NotNull T input) {
        player.resetTitle();
    }

    @Override
    public void onCancel() {
        player.resetTitle();
    }

    @Override
    public void onInputRejected(@NotNull String errorMessage) {
        player.sendTitle(ChatColor.RED + errorMessage, "Type ESC to cancel", 10, 70, 0);
        plugin.getScheduler().runTaskLater(60, () -> {
            if (isCollecting()) {
                player.sendTitle(description, "Type ESC to cancel", 10, Short.MAX_VALUE, 0);
            }
        });
    }

    public ChatInput<T> description(@NotNull String description) {
        this.description = description;
        return this;
    }

    public ChatInput<T> onInput(@NotNull Consumer<T> inputCollector) {
        return (ChatInput<T>) super.onInput(inputCollector);
    }

    public ChatInput<T> onCancel(@NotNull Runnable cancelAction) {
        return (ChatInput<T>) super.onCancel(cancelAction);
    }

}
