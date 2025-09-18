package io.github.pigaut.voxel.core.message.impl;

import io.github.pigaut.voxel.core.message.*;
import io.github.pigaut.voxel.menu.button.*;
import io.github.pigaut.voxel.placeholder.*;
import io.github.pigaut.yaml.*;
import net.md_5.bungee.api.*;
import net.md_5.bungee.api.chat.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class ActionBarMessage extends GenericMessage {

    private final String message;

    public ActionBarMessage(String message) {
        super(UUID.randomUUID().toString(), null);
        this.message = message;
    }

    public ActionBarMessage(String name, @Nullable String group, String message) {
        super(name, group);
        this.message = message;
    }

    @Override
    public @NotNull MessageType getType() {
        return MessageType.ACTIONBAR;
    }

    @Override
    public @NotNull ItemStack getIcon() {
        return IconBuilder.of(Material.NAME_TAG).buildIcon();
    }

    @Override
    public void send(@NotNull Player player, PlaceholderSupplier... placeholderSuppliers) {
        final String formattedMessage = StringPlaceholders.parseAll(player, message, placeholderSuppliers);
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(formattedMessage));
    }

}
