package io.github.pigaut.voxel.plugin.boot;

import io.github.pigaut.sql.*;
import io.github.pigaut.sql.database.*;
import io.github.pigaut.voxel.*;
import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.voxel.server.*;
import io.github.pigaut.voxel.util.*;
import io.github.pigaut.voxel.util.UpdateChecker;
import io.github.pigaut.voxel.version.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.*;
import io.github.pigaut.yaml.node.section.*;
import org.bstats.charts.*;
import org.jetbrains.annotations.*;

import java.io.*;
import java.util.*;
import java.util.logging.*;

public class PluginSetup {

    private PluginSetup() {}

    public static @NotNull RootSection createConfiguration(EnhancedJavaPlugin plugin) {
        File file = plugin.getFile("config.yml");
        if (!file.exists()) {
            plugin.saveDefaultConfig();
        }
        Configurator configurator = plugin.getConfigurator();
        return YamlConfig.createEmptySection(file, configurator);
    }

    public static Optional<ConfigurationLoadException> loadConfiguration(EnhancedJavaPlugin plugin) {
        RootSection configuration = plugin.getConfiguration();
        try {
            configuration.load();
        }
        catch (ConfigurationLoadException e) {
            return Optional.of(e);
        }
        return Optional.empty();
    }

    public static void dumpLogo(EnhancedJavaPlugin plugin) {
        String logo = plugin.getLogo();
        if (logo != null) {
            if (plugin.forceLogoDump() || plugin.getSettings().isDumpLogo()) {
                plugin.getColoredLogger().log(Level.INFO, logo);
            }
        }
    }

    public static void checkServerVersion(EnhancedJavaPlugin plugin) {
        ColoredLogger logger = plugin.getColoredLogger();
        SpigotVersion serverVersion = SpigotServer.getVersion();

        if (serverVersion == SpigotVersion.UNKNOWN) {
            logger.warning("Found unknown server version");
        }
        else {
            if (!plugin.getCompatibleVersions().contains(serverVersion)) {
                throw new UnsupportedVersionException(plugin);
            }
            logger.info("Found compatible server version: " + serverVersion);
        }
    }

    public static @Nullable PluginMetrics createMetrics(EnhancedJavaPlugin plugin) {
        Integer metricsId = plugin.getMetricsId();
        if (metricsId == null) {
            return null;
        }

        PluginMetrics currentMetrics = plugin.getMetrics();
        if (currentMetrics != null) {
            return currentMetrics;
        }

        boolean enabled = plugin.getSettings().isMetrics();
        if (!enabled) {
            return null;
        }

        plugin.getColoredLogger().info("Created bStats metrics with id: " + metricsId);
        currentMetrics = new PluginMetrics(plugin, metricsId, enabled);
        currentMetrics.addCustomChart(new SimplePie("premium", () ->
                Boolean.toString(plugin.isPremium())));

        return currentMetrics;
    }

    public static @Nullable UpdateChecker createUpdateChecker(EnhancedJavaPlugin plugin) {
        Integer resourceId = plugin.getResourceId();
        if (resourceId == null) {
            return null;
        }

        UpdateChecker currentChecker = plugin.getUpdateChecker();
        if (currentChecker == null) {
            currentChecker = new UpdateChecker(plugin, plugin.getResourceId());
            plugin.registerListener(currentChecker);
        }

        if (!plugin.forceUpdateChecker() && !plugin.getSettings().isCheckForUpdates()) {
            return null;
        }

        plugin.getColoredLogger().info("Created update checker with id: " + resourceId);
        currentChecker.checkForUpdates();
        return currentChecker;
    }

    public static void checkCompatiblePlugins(EnhancedJavaPlugin plugin) {
        ColoredLogger logger = plugin.getColoredLogger();
        logger.info("Looking for compatible plugins...");

        List<String> plugins = plugin.getCompatiblePlugins();
        StringJoiner joiner = new StringJoiner(", ");
        for (int i = 0; i < plugins.size(); i++) {
            String pluginName = plugins.get(i);
            boolean enabled = SpigotServer.isPluginEnabled(pluginName);

            joiner.add((enabled ? "&e" : "&7") + pluginName);
            if ((i + 1) % 4 == 0) {
                logger.log(Level.INFO, joiner.toString());
                joiner = new StringJoiner(", ");
            }
        }

        if (joiner.length() > 0) {
            logger.log(Level.INFO, joiner.toString());
        }
    }

    public static void generateDirectoriesAndFiles(EnhancedJavaPlugin plugin) {
        plugin.getColoredLogger().info("Generating directories and files...");

        for (String directory : plugin.getDefaultDirectories()) {
            plugin.createDirectory(directory);
        }

        for (String resource : plugin.getDefaultResources()) {
            plugin.saveResource(resource);
        }
    }

    public static void generateExampleFiles(EnhancedJavaPlugin plugin) {
        ColoredLogger logger = plugin.getColoredLogger();

        if (!plugin.getSettings().isGenerateExamples()) {
            logger.warning("Skipping example file generation (disabled in config)");
            return;
        }

        logger.info("Generating example files...");
        for (String resourcePath : plugin.getExampleResources()) {
            plugin.saveResource(resourcePath);
        }

        logger.info("Generating version specific example files...");
        SpigotVersion currentVersion = SpigotServer.getVersion();
        Map<SpigotVersion, List<String>> examplesByVersion = plugin.getExamplesByVersion();
        for (SpigotVersion requiredVersion : examplesByVersion.keySet()) {
            if (currentVersion.equalsOrIsNewerThan(requiredVersion)) {
                for (String resourcePath : examplesByVersion.get(requiredVersion)) {
                    plugin.saveResource(resourcePath);
                }
            }
        }

        logger.info("Generating plugin specific example files...");
        Map<String, List<String>> examplesByPlugin = plugin.getExamplesByPlugin();
        for (String pluginName : examplesByPlugin.keySet()) {
            if (SpigotServer.isPluginEnabled(pluginName)) {
                for (String resourcePath : examplesByPlugin.get(pluginName)) {
                    plugin.saveResource(resourcePath);
                }
            }
        }

    }

    public static @Nullable Database createDatabase(EnhancedJavaPlugin plugin) {
        String databaseName = plugin.getDatabaseName();
        if (databaseName == null) {
            return null;
        }

        plugin.getColoredLogger().info("Establishing database connection...");
        Database current = plugin.getDatabase();

        if (current != null) {
            current.openConnection();
            return current;
        }

        File file = plugin.getFile(databaseName);
        current = new FileDatabase(file);
        current.openConnection();
        return current;
    }


}
