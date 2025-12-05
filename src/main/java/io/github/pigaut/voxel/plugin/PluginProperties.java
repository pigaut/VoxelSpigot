package io.github.pigaut.voxel.plugin;

import io.github.pigaut.voxel.command.*;
import io.github.pigaut.voxel.plugin.boot.*;
import io.github.pigaut.voxel.plugin.boot.phase.*;
import io.github.pigaut.voxel.version.*;
import io.github.pigaut.yaml.configurator.*;
import org.bukkit.event.*;
import org.jetbrains.annotations.*;

import java.util.*;

public interface PluginProperties {

    default boolean isPremium() {
        return false;
    }

    @NotNull Configurator createConfigurator();
    default void registerHooks() {}
    default @Nullable String getDatabaseName() {
        return null;
    }
    @Nullable default String getLogo() {
        return null;
    }

    default @Nullable Integer getMetricsId() {
        return null;
    }
    default @Nullable Integer getResourceId() {
        return null;
    }

    default List<BootPhase> getStartupRequirements() {
        return List.of();
    }
    default List<StartupTask> getStartupTasks() {
        return List.of();
    }

    default List<EnhancedCommand> getDefaultCommands() {
        return List.of();
    }
    default List<Listener> getDefaultListeners() {
        return List.of();
    }

    default @NotNull List<SpigotVersion> getCompatibleVersions() {
        return List.of(SpigotVersion.values());
    }
    default @NotNull List<String> getCompatiblePlugins() {
        return List.of();
    }

    default List<String> getDefaultDirectories() {
        return List.of();
    }
    default List<String> getDefaultResources() {
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

    default boolean forceLogoDump() {
        return !isPremium();
    }
    default boolean forceUpdateChecker() {
        return !isPremium();
    }

}
