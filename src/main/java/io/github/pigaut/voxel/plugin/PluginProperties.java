package io.github.pigaut.voxel.plugin;

import io.github.pigaut.voxel.command.*;
import io.github.pigaut.voxel.menu.*;
import io.github.pigaut.voxel.version.*;
import io.github.pigaut.yaml.configurator.*;
import org.bukkit.event.*;
import org.jetbrains.annotations.*;

import java.util.*;

public interface PluginProperties {

    @NotNull
    Configurator createConfigurator();

    default boolean isPremium() {
        return false;
    }

    default boolean forceMetrics() {
        return !isPremium();
    }

    default boolean forceUpdateChecker() {
        return !isPremium();
    }

    default boolean forceLogoDump() {
        return !isPremium();
    }

    @Nullable
    default String getLogo() {
        return null;
    }

    default @Nullable Integer getMetricsId() {
        return null;
    }

    default @Nullable Integer getResourceId() {
        return null;
    }

    default @Nullable String getDonationLink() {
        return null;
    }

    default @NotNull List<SpigotVersion> getCompatibleVersions() {
        return List.of(SpigotVersion.values());
    }

    default List<String> getPluginDirectories() {
        return List.of();
    }

    default List<String> getPluginResources() {
        return List.of();
    }

    default List<String> getExampleResources() {
        return List.of();
    }

    default Map<SpigotVersion, List<String>> getExamplesByVersion() {
        return Map.of();
    }

    default Map<String, List<String>> getExamplesByPlugin() {
        return Map.of();
    }

    default List<EnhancedCommand> getPluginCommands() {
        return new ArrayList<>();
    }

    default List<Listener> getPluginListeners() {
        return new ArrayList<>();
    }

    default @Nullable String getDatabaseName() {
        return null;
    }

}
