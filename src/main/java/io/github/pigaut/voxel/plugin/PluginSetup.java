package io.github.pigaut.voxel.plugin;

import com.jeff_media.updatechecker.*;
import io.github.pigaut.sql.*;
import io.github.pigaut.sql.database.*;
import io.github.pigaut.voxel.*;
import io.github.pigaut.voxel.config.*;
import io.github.pigaut.voxel.server.*;
import io.github.pigaut.voxel.util.*;
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
        File configFile = plugin.getFile("config.yml");
        Configurator configurator = plugin.getConfigurator();

        RootSection config;
        try {
            config = YamlConfig.loadSection(configFile, configurator);
        }
        catch (ConfigurationLoadException e) {
            config = YamlConfig.createEmptySection(configFile, configurator);
            ConfigErrorLogger.logAll(plugin, List.of(e));
        }

        return config;
    }

    public static void dumpLogo(EnhancedJavaPlugin plugin) {
        String logo = plugin.getLogo();
        if (logo != null) {
            if (plugin.forceLogoDump() || plugin.getConfiguration().getBoolean("dump-logo").orElse(true)) {
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
            currentMetrics.shutdown();
        }

        ConfigSection config = plugin.getConfiguration();
        if (!plugin.forceMetrics() && !config.getBoolean("metrics").orElse(true)) {
            return null;
        }

        plugin.getColoredLogger().info("Created bStats metrics with id: " + metricsId);
        currentMetrics = new PluginMetrics(plugin, metricsId);
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
        if (currentChecker != null) {
            currentChecker.stop();
        }

        ConfigSection config = plugin.getConfiguration();
        if (!plugin.forceUpdateChecker() && !config.getBoolean("check-for-updates").orElse(true)) {
            return null;
        }

        plugin.getColoredLogger().info("Created update checker with id: " + resourceId);
        currentChecker = new UpdateChecker(plugin, UpdateCheckSource.SPIGET, Integer.toString(resourceId))
                .setDownloadLink("https://www.spigotmc.org/resources/" + resourceId)
                .setChangelogLink("https://www.spigotmc.org/resources/" + resourceId + "/updates")
                .setDonationLink(plugin.getDonationLink())
                .setNotifyOpsOnJoin(true)
                .checkEveryXHours(24)
                .checkNow();

        return currentChecker;
    }

    public static void logFoundDependecies(EnhancedJavaPlugin plugin) {
        ColoredLogger logger = plugin.getColoredLogger();
        logger.info("Looking for compatible plugins...");

        List<String> plugins = plugin.getDescription().getSoftDepend();
        StringJoiner joiner = new StringJoiner(", ");
        for (int i = 0; i < plugins.size(); i++) {
            String pluginName = plugins.get(i);
            boolean enabled = SpigotServer.isPluginEnabled(pluginName);

            joiner.add((enabled ? "&a" : "&c") + pluginName);
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

        for (String directory : plugin.getPluginDirectories()) {
            plugin.createDirectory(directory);
        }

        for (String resource : plugin.getPluginResources()) {
            plugin.saveResource(resource);
        }

    }

    public static void generateExampleFiles(EnhancedJavaPlugin plugin) {
        ColoredLogger logger = plugin.getColoredLogger();

        if (!plugin.getConfiguration().getBoolean("generate-examples").orElse(true)) {
            logger.warning("Skipping example file generation (disabled in config)");
            return;
        }

        logger.info("Generating example files...");
        for (String resourcePath : plugin.getExampleResources()) {
            plugin.saveResource(resourcePath);
        }

        logger.info("Generating version specific example files...");
        final SpigotVersion currentVersion = SpigotServer.getVersion();
        final Map<SpigotVersion, List<String>> examplesByVersion = plugin.getExamplesByVersion();
        for (SpigotVersion requiredVersion : examplesByVersion.keySet()) {
            if (currentVersion.equalsOrIsNewerThan(requiredVersion)) {
                for (String resourcePath : examplesByVersion.get(requiredVersion)) {
                    plugin.saveResource(resourcePath);
                }
            }
        }

        logger.info("Generating hooked plugins example files...");
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
