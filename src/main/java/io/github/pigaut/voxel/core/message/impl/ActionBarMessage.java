package io.github.pigaut.voxel.core.message.impl;

import io.github.pigaut.voxel.menu.button.*;
import io.github.pigaut.voxel.placeholder.*;
import io.github.pigaut.yaml.*;
import net.md_5.bungee.api.*;
import net.md_5.bungee.api.chat.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.*;

public class ActionBarMessage extends GenericMessage {

    private final String message;

    public ActionBarMessage(String name, @Nullable String group, ConfigSection section, String message) {
        super(name, group, section);
        this.message = message;
    }

    @Override
    public @NotNull ItemStack getIcon() {
        return IconBuilder.of(Material.NAME_TAG).buildIcon();
    }

    @Override
    public void send(Player player, PlaceholderSupplier... placeholderSuppliers) {
        final String formattedMessage = StringPlaceholders.parseAll(player, message, placeholderSuppliers);
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(formattedMessage));
    }

}
