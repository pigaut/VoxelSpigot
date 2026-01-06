package io.github.pigaut.voxel.bukkit;

import io.github.pigaut.voxel.placeholder.*;
import io.github.pigaut.voxel.util.*;
import net.md_5.bungee.chat.*;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;

import java.util.*;

public class Chat {

    public static void send(Player player, String message) {
        if (message.startsWith("[json]")) {
            sendJson(player, StringUtil.removeTag(message, "[json]"));
            return;
        }
        player.sendMessage(StringPlaceholders.parseAll(player, message));
    }

    public static void send(Player player, String message, Collection<PlaceholderSupplier> placeholderSuppliers) {
        if (message.startsWith("[json]")) {
            sendJson(player, StringUtil.removeTag(message, "[json]"), placeholderSuppliers);
            return;
        }
        player.sendMessage(StringPlaceholders.parseAll(player, message, placeholderSuppliers));
    }

    public static void send(CommandSender sender, String message, Collection<PlaceholderSupplier> placeholderSuppliers) {
        sender.sendMessage(StringPlaceholders.parseAll(message, placeholderSuppliers));
    }

    public static void sendJson(Player player, String message) {
        String parsedMessage = StringPlaceholders.parseAll(player, message);
        player.spigot().sendMessage(ComponentSerializer.parse(parsedMessage));
    }

    public static void sendJson(Player player, String message, Collection<PlaceholderSupplier> placeholderSuppliers) {
        String parsedMessage = StringPlaceholders.parseAll(player, message, placeholderSuppliers);
        player.spigot().sendMessage(ComponentSerializer.parse(parsedMessage));
    }

}
