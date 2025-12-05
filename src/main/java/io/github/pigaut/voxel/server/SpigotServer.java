package io.github.pigaut.voxel.server;

import io.github.pigaut.voxel.hook.*;
import io.github.pigaut.voxel.version.*;
import io.github.pigaut.yaml.convert.parse.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.plugin.*;
import org.jetbrains.annotations.*;

import java.io.*;
import java.util.*;

public class SpigotServer {

    private static SpigotServer server;

    private final SpigotVersion version;

    private SpigotServer() {
        {
            final String version = "V" + Bukkit.getBukkitVersion().split("-")[0].replaceAll("\\.", "_");
            SpigotVersion foundVersion = ParseUtil.parseEnumOrNull(SpigotVersion.class, version);
            this.version = foundVersion != null ? foundVersion : SpigotVersion.UNKNOWN;
        }
    }

    private static SpigotServer getServer() {
        if (server == null)
            server = new SpigotServer();
        return server;
    }

    @NotNull
    public static SpigotVersion getVersion() {
        return getServer().version;
    }

    @NotNull
    public static NMSVersion getNMSVersion() {
        return getVersion().getNMSVersion();
    }

    public static World getDefaultWorld() {
        return Bukkit.getWorlds().get(0);
    }

    public static List<String> getOnlinePlayerNames() {
        return Bukkit.getOnlinePlayers().stream()
                .map(Player::getName).toList();
    }

    public static List<String> getWorldNames() {
        return Bukkit.getWorlds().stream()
                .map(World::getName)
                .toList();
    }

    public static @Nullable EconomyHook getEconomyHook() {
        return isPluginLoaded("Vault") ? EconomyHook.newInstance() : null;
    }

    public static @Nullable PlaceholdersHook getPlaceholderAPIHook() {
        return isPluginLoaded("PlaceholderAPI") ? new PlaceholdersHook() : null;
    }

    public static @Nullable Plugin getPlugin(String name) {
        return Bukkit.getPluginManager().getPlugin(name);
    }

    public static @Nullable String getPluginVersion(String name) {
        final Plugin plugin = getPlugin(name);
        if (plugin == null) {
            return null;
        }
        return plugin.getDescription().getVersion();
    }

    public static boolean isPluginLoaded(String name) {
        return getPlugin(name) != null;
    }

    public static boolean isPluginEnabled(String name) {
        return Bukkit.getPluginManager().isPluginEnabled(name);
    }

    @Nullable
    public static <T> T getRegisteredService(Class<T> service) {
        RegisteredServiceProvider<T> registration = Bukkit.getServer().getServicesManager().getRegistration(service);
        return registration != null ? registration.getProvider() : null;
    }

    public static void callEvent(Event event) {
        Bukkit.getPluginManager().callEvent(event);
    }

    public static List<String> getWorldFolderNames() {
        File worldFolder = Bukkit.getServer().getWorldContainer();

        List<String> worlds = new ArrayList<>();

        if (!worldFolder.isDirectory()) {
            return worlds;
        }

        File[] children = worldFolder.listFiles();
        if (children == null) {
            return worlds;
        }

        for (File child : children) {
            if (!child.isDirectory()) continue;

            // Check if this folder contains a level.dat file
            File levelDat = new File(child, "level.dat");
            if (levelDat.exists() && levelDat.isFile()) {
                worlds.add(child.getName());
            }
        }

        return worlds;
    }

}
