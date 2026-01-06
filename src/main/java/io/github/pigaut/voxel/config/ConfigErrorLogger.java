package io.github.pigaut.voxel.config;

import io.github.pigaut.voxel.placeholder.*;
import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.yaml.*;
import org.bukkit.*;
import org.bukkit.entity.*;

import java.util.*;
import java.util.logging.*;

public class ConfigErrorLogger {

    public static void logAll(EnhancedPlugin plugin, List<ConfigurationException> errors) {
        PluginLogger logger = plugin.getColoredLogger();
        for (ConfigurationException exception : errors) {
            logger.log(Level.SEVERE, createErrorMessage(plugin, exception));
        }
    }

    public static void logAll(EnhancedPlugin plugin, Player player, List<ConfigurationException> errors) {
        if (errors.isEmpty()) {
            return;
        }

        final PlaceholderSupplier errorCount = PlaceholderSupplier.of("{error_count}", errors.size());
        if (!plugin.getSettings().isShowReloadErrors()) {
            plugin.sendMessage(player, "configuration-errors", errorCount);
            logAll(plugin, errors);
            return;
        }

        plugin.sendMessage(player, "debug-configuration-errors", errorCount);
        player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "---------------------------------------------");
        for (ConfigurationException exception : errors) {
            final String errorMessage = createErrorMessage(plugin, exception);
            plugin.getColoredLogger().log(Level.SEVERE, errorMessage);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', errorMessage));
        }
    }

    private static String createErrorMessage(EnhancedPlugin plugin, ConfigurationException exception) {
        if (exception instanceof ConfigurationLoadException loadException) {
            return createErrorMessage(plugin, loadException);
        }
        else if (exception instanceof InvalidConfigurationException invalidException) {
            return createErrorMessage(plugin, invalidException);
        }
        return exception.getLogMessage();
    }

    private static String createErrorMessage(EnhancedPlugin plugin, ConfigurationLoadException exception) {
        return createErrorMessage(exception.getPrefix(), exception.getFilePath(plugin.getDataFolder().getPath()), exception.getDetails());
    }

    private static String createErrorMessage(String prefix, String file, String cause) {
        final String optionalPrefix = prefix != null ? (prefix + " ") : "";
        final String optionalFile = file != null ? ("  &c&lFile &c>> " + file + "\n") : "";
        final String details = "  &e&lDetail &e>> " + cause + "\n";

        return "&c&l" + optionalPrefix + "Configuration: &fINVALID YAML FORMAT\n" +
                optionalFile +
                details +
                "&c&l---------------------------------------------";
    }

    private static String createErrorMessage(EnhancedPlugin plugin, InvalidConfigurationException exception) {
        return createErrorMessage(exception.getPrefix(), exception.getProblem(), exception.getFilePath(plugin.getDataFolder().getPath()),
                exception.getPath(), exception.getLine(), exception.getDetails());
    }

    private static String createErrorMessage(String prefix, String problem, String file, String path, String line, String cause) {
        final String optionalPrefix = prefix != null ? (prefix + " ") : "";
        final String optionalProblem = problem != null ? (": &f" + problem.toUpperCase()) : "";

        final String optionalFile = file != null ? ("  &c&lFile &c>> " + file + "\n") : "";
        final String optionalPath = path != null && !path.isEmpty() ? ("  &c&lPath &c>> " + path + "\n") : "";
        final String optionalLine = line != null ? ("  &f&lLine &f>> " + line + "\n") : "";
        final String details = "  &e&lDetails &e>> " + cause + "\n";

        return "&c&l" + optionalPrefix + "Configuration" + optionalProblem + "\n" +
                optionalFile +
                optionalPath +
                optionalLine +
                details +
                "&c&l---------------------------------------------";
    }

}
