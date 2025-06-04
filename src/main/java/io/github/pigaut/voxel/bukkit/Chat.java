package io.github.pigaut.voxel.bukkit;

import io.github.pigaut.voxel.placeholder.*;
import io.github.pigaut.voxel.util.*;
import net.md_5.bungee.chat.*;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;

public class Chat {

    public static void send(Player player, String message) {
        if (message.startsWith("[json]")) {
            sendJson(player, StringUtil.removeTag(message, "[json]"));
        }
        else {
            player.sendMessage(StringPlaceholders.parseAll(message));
        }
    }

    public static void send(Player player, String message, PlaceholderSupplier... placeholderSuppliers) {
        if (message.startsWith("[json]")) {
            sendJson(player, StringUtil.removeTag(message, "[json]"), placeholderSuppliers);
        }
        else {
            player.sendMessage(StringPlaceholders.parseAll(player, message, placeholderSuppliers));
        }
    }

    public static void send(CommandSender sender, String message, PlaceholderSupplier... placeholderSuppliers) {
        sender.sendMessage(StringPlaceholders.parseAll(message, placeholderSuppliers));
    }

    public static void sendAll(String message) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            send(player, message);
        }
    }

    public static void sendAll(String message, PlaceholderSupplier... placeholders) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            send(player, message, placeholders);
        }
    }

    public static void sendJson(Player player, String message) {
        player.spigot().sendMessage(ComponentSerializer.parse(StringPlaceholders.parseAll(player, message)));
    }

    public static void sendJson(Player player, String message, PlaceholderSupplier... placeholderSuppliers) {
        player.spigot().sendMessage(ComponentSerializer.parse(StringPlaceholders.parseAll(message, placeholderSuppliers)));
    }

}
