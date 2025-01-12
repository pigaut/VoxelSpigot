package io.github.pigaut.voxel.message.type;

import io.github.pigaut.voxel.message.*;
import io.github.pigaut.voxel.meta.placeholder.*;
import net.md_5.bungee.api.*;
import net.md_5.bungee.api.chat.*;
import org.bukkit.entity.*;

public class ActionBarMessage implements Message {

    private final String message;

    public ActionBarMessage(String message) {
        this.message = message;
    }

    @Override
    public void send(Player player) {
        final String formattedMessage = StringPlaceholders.parseAll(player, message);
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(formattedMessage));
    }

    @Override
    public void send(Player player, PlaceholderSupplier... placeholderSuppliers) {
        final String formattedMessage = StringPlaceholders.parseAll(player, message, placeholderSuppliers);
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(formattedMessage));
    }

}
