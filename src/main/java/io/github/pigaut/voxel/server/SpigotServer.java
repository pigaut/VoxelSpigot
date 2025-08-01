package io.github.pigaut.voxel.server;

import io.github.pigaut.voxel.hook.*;
import io.github.pigaut.voxel.version.*;
import io.github.pigaut.yaml.parser.deserializer.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.plugin.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class SpigotServer {

    private static SpigotServer server;

    private final SpigotVersion version;
    private final World defaultWorld;

    private SpigotServer() {
        {
            final String version = "V" + Bukkit.getBukkitVersion().split("-")[0].replaceAll("\\.", "_");
            SpigotVersion foundVersion = EnumDeserializer.deserializeOrNull(SpigotVersion.class, version);
            this.version = foundVersion != null ? foundVersion : SpigotVersion.UNKNOWN;
        }
        defaultWorld = Bukkit.getWorlds().get(0);
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
        return getServer().defaultWorld;
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

}
