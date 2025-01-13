package io.github.pigaut.voxel.plugin;

import io.github.pigaut.voxel.command.*;
import io.github.pigaut.voxel.config.*;
import io.github.pigaut.voxel.item.*;
import io.github.pigaut.voxel.language.*;
import io.github.pigaut.voxel.message.*;
import io.github.pigaut.voxel.meta.flag.*;
import io.github.pigaut.voxel.meta.placeholder.*;
import io.github.pigaut.voxel.player.*;
import io.github.pigaut.voxel.plugin.manager.*;
import io.github.pigaut.voxel.runnable.*;
import io.github.pigaut.voxel.server.*;
import io.github.pigaut.voxel.util.*;
import io.github.pigaut.voxel.version.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.*;
import io.github.pigaut.yaml.node.section.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.inventory.*;
import org.bukkit.plugin.*;
import org.bukkit.plugin.java.*;
import org.jetbrains.annotations.*;

import java.io.*;
import java.lang.reflect.*;
import java.util.*;
import java.util.logging.*;
import java.util.regex.*;

public abstract class EnhancedJavaPlugin extends JavaPlugin implements EnhancedPlugin {

    private final CommandManager commandManager = new CommandManager();
    private final PlayerManager<PluginPlayer> playerManager = new PlayerManager<>(AbstractPluginPlayer::new);
    private final ItemManager itemManager = new PluginItemManager(this);
    private final MessageManager messageManager = new PluginMessageManager(this);
    private final LanguageManager languageManager = new PluginLanguageManager(this);
    private final FlagManager flagManager = new PluginFlagManager(this);
    private final PluginScheduler pluginScheduler = new PluginScheduler(this);
    private RootSection config;

    protected final Logger logger = getLogger();
    private final List<Manager> loadedManagers = new ArrayList<>();

    @Override
    public void onEnable() {
        SpigotVersion serverVersion = SpigotServer.getVersion();
        if (serverVersion == SpigotVersion.UNKNOWN) {
            logger.severe("Could not identify server version. Attempting to load.");
        }
        else {
            if (!getCompatibleVersions().contains(serverVersion)) {
                throw new UnsupportedVersionException(this);
            }
            else {
                logger.info("Found compatible server version: " + serverVersion);
            }
        }

        createHooks();

        for (String directory : getPluginDirectories()) {
            createDirectory(directory);
        }

        for (String resource : getPluginResources()) {
            saveResource(resource);
        }

        config = new RootSection(getFile("config.yml"), getConfigurator());
        config.load();
        if (config.getOptionalBoolean("generate-examples").orElse(true)) {
            for (String exampleResource : getExampleResources()) {
                saveResource(exampleResource);
            }
        }

        loadedManagers.clear();
        loadedManagers.add(commandManager);
        loadedManagers.add(playerManager);
        loadedManagers.add(itemManager);
        loadedManagers.add(messageManager);
        loadedManagers.add(languageManager);
        loadedManagers.add(flagManager);
        for (Field field : getClass().getDeclaredFields()) {
            final Manager manager = ReflectionUtil.accessField(field, Manager.class, this);
            if (manager != null) {
                loadedManagers.add(manager);
            }
        }

        for (Manager manager : loadedManagers) {
            manager.onEnable();
            for (Listener listener : manager.getListeners()) {
                registerListener(listener);
            }

            getCommand(""); //initialize command manager I think, can't remember
            for (EnhancedCommand command : manager.getCommands()) {
                registerCommand(command);
            }

            final int autoSave = manager.getAutoSave();
            if (autoSave > 0) {
                pluginScheduler.runTaskTimerAsync(autoSave, manager::save);
            }
        }

    }

    @Override
    public void onDisable() {
        loadedManagers.forEach(Manager::onDisable);
    }

    @Override
    public void reload() {
        pluginScheduler.runTaskAsync(() -> {
            onEnable();
        });
    }

    @Override
    public PluginScheduler getScheduler() {
        return pluginScheduler;
    }

    @Override
    public List<SpigotVersion> getCompatibleVersions() {
        return List.of(SpigotVersion.values());
    }

    protected void createHooks() {}

    @Override
    public List<String> getPluginDirectories() {
        return Collections.emptyList();
    }

    @Override
    public List<String> getPluginResources() {
        return Collections.emptyList();
    }

    @Override
    public List<String> getExampleResources() {
        return Collections.emptyList();
    }

    @Override
    public @NotNull CommandManager getCommands() {
        return commandManager;
    }

    @Override
    public @NotNull PlayerManager<? extends PluginPlayer> getPlayers() {
        return playerManager;
    }

    @Override
    public @NotNull ItemManager getItems() {
        return itemManager;
    }

    @Override
    public @NotNull MessageManager getMessages() {
        return messageManager;
    }

    @Override
    public @NotNull LanguageManager getLanguages() {
        return languageManager;
    }

    @Override
    public @NotNull FlagManager getFlags() {
        return flagManager;
    }

    @Override
    public @NotNull RootSection getConfiguration() {
        return config;
    }

    @Override
    public @NotNull PluginConfigurator getConfigurator() {
        return new PluginConfigurator(this);
    }

    @Override
    public @Nullable EnhancedCommand getCustomCommand(String name) {
        return commandManager.getCustomCommand(name);
    }

    @Override
    public void registerCommand(EnhancedCommand command) {
        commandManager.registerCommand(command);
    }

    @Override
    public void unregisterCommand(String name) {
        commandManager.unregisterCommand(name);
    }

    @Override
    public @Nullable PluginPlayer getPlayer(String playerName) {
        return playerManager.getPlayer(playerName);
    }

    @Override
    public @Nullable PluginPlayer getPlayer(UUID playerId) {
        return playerManager.getPlayer(playerId);
    }

    @Override
    public @Nullable ItemStack getItemStack(String name) {
        return itemManager.getItemStack(name);
    }

    @Override
    public @Nullable Message getMessage(String name) {
        return messageManager.getMessage(name);
    }

    @Override
    public @NotNull String getLang(String name) {
        return languageManager.getLang(name);
    }

    @Override
    public @NotNull String getLang(String name, String def) {
        return languageManager.getLang(name, def);
    }

    @Override
    public @NotNull String getLang(Locale locale, String name) {
        return languageManager.getLang(locale, name);
    }

    @Override
    public void sendMessage(Player player, String messageId) {
        Chat.send(player, getLang(messageId));
    }

    @Override
    public void sendMessage(Player player, String messageId, PlaceholderSupplier... placeholderSuppliers) {
        Chat.send(player, getLang(messageId), placeholderSuppliers);
    }

    @Override
    public void sendMessage(CommandSender sender, String messageId) {
        Chat.send(sender, getLang(messageId));
    }

    @Override
    public void sendMessage(CommandSender sender, String messageId, PlaceholderSupplier... placeholderSuppliers) {
        Chat.send(sender, getLang(messageId), placeholderSuppliers);
    }

    @Override
    public @Nullable Flag getFlag(String name) {
        return flagManager.getFlag(name);
    }

    @Override
    public void createDirectory() {
        getDataFolder().mkdirs();
    }

    @Override
    public void createDirectory(String path) {
        getFile(path).mkdirs();
    }

    @Override
    public void saveResource(String path) {
        if (!getFile(path).exists()) {
            saveResource(path, false);
        }
    }

    @Override
    public File getFile(String path) {
        return new File(getDataFolder(), path);
    }

    @Override
    public List<File> getFiles(String path) {
        File directory = getFile(path);
        List<File> yamlFiles = new ArrayList<>();
        collectYamlFiles(directory, yamlFiles);
        return yamlFiles;
    }

    private void collectYamlFiles(File directory, List<File> yamlFiles) {
        if (directory == null || !directory.isDirectory()) {
            return;
        }

        File[] files = directory.listFiles();
        if (files == null) {
            return;
        }

        for (File file : files) {
            if (file.isDirectory()) {
                collectYamlFiles(file, yamlFiles);
            } else if (YamlConfig.isYamlFile(file)) {
                yamlFiles.add(file);
            }
        }
    }

    @Override
    public void registerListener(Listener listener) {
        getServer().getPluginManager().registerEvents(listener, this);
    }

    private final static Pattern VERSION_PATTERN = Pattern.compile("(^[\\.\\d]*).+");

    protected boolean shouldCreateHook(String pluginName, String minVersion, String maxVersion) {
        final Plugin plugin = SpigotServer.getPlugin(pluginName);
        if (plugin == null) {
            logger.info(pluginName + " Hook: plugin not loaded");
            return false;
        }

        final String version = plugin.getDescription().getVersion();
        final Matcher matcher = VERSION_PATTERN.matcher(version);
        if (!matcher.find()) {
            logger.info(pluginName + "Hook: found unknown version (" + minVersion + " - " + maxVersion + ")");
            return false;
        }

        final String[] versionParts = matcher.group(1).split("\\.");
        final String[] minVersionParts = minVersion.split("\\.");
        final String[] maxVersionParts = maxVersion.split("\\.");

        if (isVersionLessThan(versionParts, minVersionParts)) {
            logger.info(pluginName + " Hook: found incompatible version (" + minVersion + " - " + maxVersion + ")");
            return false;
        }

        if (isVersionGreaterThan(versionParts, maxVersionParts)) {
            logger.info(pluginName + " Hook: found incompatible version (" + minVersion + " - " + maxVersion + ")");
            return false;
        }

        logger.info(pluginName + " Hook: found compatible version (" + minVersion + " - " + maxVersion + ")");
        return true;
    }

    private boolean isVersionLessThan(String[] version, String[] targetVersion) {
        for (int i = 0; i < Math.max(version.length, targetVersion.length); i++) {
            int vPart = i < version.length ? Integer.parseInt(version[i]) : 0;
            int tPart = i < targetVersion.length ? Integer.parseInt(targetVersion[i]) : 0;
            if (vPart < tPart) {
                return true;
            } else if (vPart > tPart) {
                return false;
            }
        }
        return false;
    }

    private boolean isVersionGreaterThan(String[] version, String[] targetVersion) {
        for (int i = 0; i < Math.max(version.length, targetVersion.length); i++) {
            int vPart = i < version.length ? Integer.parseInt(version[i]) : 0;
            int tPart = i < targetVersion.length ? Integer.parseInt(targetVersion[i]) : 0;
            if (vPart > tPart) {
                return true;
            } else if (vPart < tPart) {
                return false;
            }
        }
        return false;
    }

}
