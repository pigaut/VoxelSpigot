package io.github.pigaut.voxel.command;

import io.github.pigaut.voxel.player.*;
import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.voxel.server.*;
import org.bukkit.entity.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class CommandParameters {

    public static final CommandParameter ONLINE_PLAYER = CommandParameter.create("online-players",
            (sender, args) -> SpigotServer.getOnlinePlayerNames());

    public static final CommandParameter WORLD_NAME = CommandParameter.create("world-name",
            (sender, args) -> SpigotServer.getWorldNames());

    public static final CommandParameter X_COORDINATE = CommandParameter.create("x",
            (sender, args) -> {
                if (sender instanceof Player player) {
                    return List.of(Integer.toString(PlayerUtil.getTargetBlockX(player)));
                }
                return List.of("0");
            });

    public static final CommandParameter Y_COORDINATE = CommandParameter.create("y",
            (sender, args) -> {
                if (sender instanceof Player player) {
                    return List.of(Integer.toString(PlayerUtil.getTargetBlockY(player)));
                }
                return List.of("0");
            });

    public static final CommandParameter Z_COORDINATE = CommandParameter.create("z",
            (sender, args) -> {
                if (sender instanceof Player player) {
                    return List.of(Integer.toString(PlayerUtil.getTargetBlockZ(player)));
                }
                return List.of("0");
            });

    public static CommandParameter filePath(@NotNull EnhancedPlugin plugin, @NotNull String path) {
        return CommandParameter.create("file-path", (sender, args) -> plugin.getFilePaths(path));
    }

    public static CommandParameter itemGroup(@NotNull EnhancedPlugin plugin) {
        return CommandParameter.create("item-group", (sender, args) -> plugin.getItems().getAllGroups());
    }

    public static CommandParameter itemName(@NotNull EnhancedPlugin plugin) {
        return CommandParameter.create("item-name", (sender, args) -> plugin.getItems().getAllNames());
    }

    public static CommandParameter messageGroup(@NotNull EnhancedPlugin plugin) {
        return CommandParameter.create("message-group", (sender, args) -> plugin.getItems().getAllGroups());
    }

    public static CommandParameter messageName(@NotNull EnhancedPlugin plugin) {
        return CommandParameter.create("message-name", (sender, args) -> plugin.getItems().getAllNames());
    }

    public static CommandParameter particleGroup(@NotNull EnhancedPlugin plugin) {
        return CommandParameter.create("particle-group", (sender, args) -> plugin.getParticles().getAllGroups());
    }

    public static CommandParameter particleName(@NotNull EnhancedPlugin plugin) {
        return CommandParameter.create("particle-name", (sender, args) -> plugin.getParticles().getAllNames());
    }

    public static CommandParameter soundGroup(@NotNull EnhancedPlugin plugin) {
        return CommandParameter.create("sound-group", (sender, args) -> plugin.getSounds().getAllGroups());
    }

    public static CommandParameter soundName(@NotNull EnhancedPlugin plugin) {
        return CommandParameter.create("sound-name", (sender, args) -> plugin.getSounds().getAllNames());
    }

    public static CommandParameter structureGroup(@NotNull EnhancedPlugin plugin) {
        return CommandParameter.create("structure-group", (sender, args) -> plugin.getStructures().getAllGroups());
    }

    public static CommandParameter structureName(@NotNull EnhancedPlugin plugin) {
        return CommandParameter.create("structure-name", (sender, args) -> plugin.getStructures().getAllNames());
    }

}
