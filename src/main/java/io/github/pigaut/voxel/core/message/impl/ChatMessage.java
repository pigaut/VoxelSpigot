package io.github.pigaut.voxel.core.message.impl;

import io.github.pigaut.voxel.bukkit.*;
import io.github.pigaut.voxel.core.message.*;
import io.github.pigaut.voxel.menu.button.*;
import io.github.pigaut.voxel.placeholder.*;
import io.github.pigaut.yaml.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.*;

public class ChatMessage extends GenericMessage {

    private final String message;

    public ChatMessage(String name, @Nullable String group, ConfigSection section, String message) {
        super(name, group, section);
        this.message = message;
    }

    @Override
    public @NotNull MessageType getType() {
        return MessageType.CHAT;
    }

    @Override
    public @NotNull ItemStack getIcon() {
        return IconBuilder.of(Material.BOOK).buildIcon();
    }

    @Override
    public void send(@NotNull Player player, PlaceholderSupplier... placeholderSuppliers) {
        Chat.send(player, message, placeholderSuppliers);
    }

}
