package io.github.pigaut.voxel.plugin;

import com.jeff_media.updatechecker.*;
import io.github.pigaut.sql.*;
import io.github.pigaut.voxel.*;
import io.github.pigaut.voxel.bukkit.*;
import io.github.pigaut.voxel.command.*;
import io.github.pigaut.voxel.config.*;
import io.github.pigaut.voxel.core.*;
import io.github.pigaut.voxel.core.function.Function;
import io.github.pigaut.voxel.core.function.*;
import io.github.pigaut.voxel.core.item.Item;
import io.github.pigaut.voxel.core.item.*;
import io.github.pigaut.voxel.core.language.*;
import io.github.pigaut.voxel.core.message.*;
import io.github.pigaut.voxel.core.particle.*;
import io.github.pigaut.voxel.core.sound.*;
import io.github.pigaut.voxel.core.structure.*;
import io.github.pigaut.voxel.hook.itemsadder.*;
import io.github.pigaut.voxel.menu.*;
import io.github.pigaut.voxel.placeholder.*;
import io.github.pigaut.voxel.player.*;
import io.github.pigaut.voxel.player.input.*;
import io.github.pigaut.voxel.plugin.manager.*;
import io.github.pigaut.voxel.plugin.runnable.*;
import io.github.pigaut.voxel.server.*;
import io.github.pigaut.voxel.util.*;
import io.github.pigaut.voxel.util.UpdateChecker;
import io.github.pigaut.voxel.util.reflection.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.*;
import io.github.pigaut.yaml.node.section.*;
import io.github.pigaut.yaml.util.*;
import org.bukkit.*;
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
import java.util.function.*;
import java.util.regex.*;

public abstract class EnhancedJavaPlugin extends JavaPlugin implements EnhancedPlugin, PluginProperties {

    private final static Pattern VERSION_PATTERN = Pattern.compile("(^[\\.\\d]*).+");

    protected final PluginScheduler scheduler = new PluginScheduler(this);
    protected final ColoredLogger coloredLogger = new ColoredLogger(this);
    private final OptionsManager optionsManager = new OptionsManager(this);
    private final LanguageManager languageManager = new SimpleLanguageManager(this);
    private final CommandManager commandManager = new CommandManager(this);
    private final PlayerStateManager<SimplePlayerState> playerManager = new PlayerStateManager<>(this, SimplePlayerState::new);
    private final ItemManager itemManager = new ItemManager(this);
    private final MessageManager messageManager = new MessageManager(this);
    private final ParticleManager particleManager = new ParticleManager(this);
    private final SoundManager soundManager = new SoundManager(this);
    private final FunctionManager functionManager = new FunctionManager(this);
    private final StructureManager structureManager = new StructureManager(this);

    private final ManagerInitializer managerInitializer = new ManagerInitializer(this);

    private final Settings settings = new Settings();
    private Configurator configurator;
    private RootSection config;
    private @Nullable UpdateChecker updateChecker = null;
    private @Nullable PluginMetrics metrics = null;
    private @Nullable Database database = null;

    private boolean debug = true;
    private boolean reloading = false;

    @Override
    public void onDisable() {
        managerInitializer.disable();
    }

    @Override
    public void onEnable() {
        configurator = createConfigurator();
        config = PluginSetup.createConfiguration(this);
        debug = config.getBoolean("debug").orElse(true);

        PluginSetup.dumpLogo(this);
        PluginSetup.checkServerVersion(this);
        PluginSetup.logFoundDependecies(this);
        PluginSetup.generateDirectoriesAndFiles(this);
        PluginSetup.generateExampleFiles(this);
        metrics = PluginSetup.createMetrics(this);
        updateChecker = PluginSetup.createUpdateChecker(this);
        database = PluginSetup.createDatabase(this);

        managerInitializer.initialize();

        registerListener(new PlayerInputListener(this));
        registerListener(new PlayerInventoryListener(this));
        registerListener(new StructureWandListener(this));

        if (SpigotServer.isPluginEnabled("ItemsAdder")) {
            registerListener(new ItemsAdderLoadListener(this));
        }

        for (Listener listener : getPluginListeners()) {
            registerListener(listener);
        }

        for (EnhancedCommand command : getPluginCommands()) {
            registerCommand(command);
        }
    }

    public boolean isReloading() {
        return reloading;
    }

    public void reload(Consumer<List<ConfigurationException>> errorCollector) throws PluginReloadInProgressException {
        if (reloading) {
            throw new PluginReloadInProgressException();
        }
        reloading = true;

        configurator = createConfigurator();
        config = PluginSetup.createConfiguration(this);
        debug = config.getBoolean("debug").orElse(true);

        PluginSetup.dumpLogo(this);
        PluginSetup.logFoundDependecies(this);
        PluginSetup.generateDirectoriesAndFiles(this);
        PluginSetup.generateExampleFiles(this);
        metrics = PluginSetup.createMetrics(this);
        updateChecker = PluginSetup.createUpdateChecker(this);

        List<Manager> loadedManagers = managerInitializer.getLoadedManagers();
        for (Manager manager : loadedManagers) {
            manager.disable();
            manager.enable();
        }

        scheduler.runTaskLaterAsync(1, () -> {
            coloredLogger.info("Saving data to database...");
            loadedManagers.forEach(Manager::saveData);

            coloredLogger.info("Loading configuration and data...");
            final List<ConfigurationException> errorsFound = new ArrayList<>();
            for (Manager manager : loadedManagers) {
                manager.loadData();
                if (manager instanceof ConfigBacked configBackedManager) {
                    errorsFound.addAll(configBackedManager.loadConfigurationData());
                }
            }
            errorCollector.accept(errorsFound);
            reloading = false;
        });
    }

    public List<Manager> getAllManagers() {
        List<Manager> managers = new ArrayList<>();

        managers.add(optionsManager);
        managers.add(languageManager);
        managers.add(commandManager);
        managers.add(playerManager);
        managers.add(itemManager);
        managers.add(messageManager);
        managers.add(particleManager);
        managers.add(soundManager);
        managers.add(functionManager);
        managers.add(structureManager);

        for (Field field : getClass().getDeclaredFields()) {
            Manager manager = ReflectionUtil.accessField(field, Manager.class, this);
            if (manager != null) {
                managers.add(manager);
            }
        }

        return managers;
    }

    public @NotNull Configurator createConfigurator() {
        return new PluginConfigurator(this);
    }

    @Override
    public boolean isDebug() {
        return debug;
    }

    @Override
    public String getVersion() {
        return this.getDescription().getVersion();
    }

    public @Nullable UpdateChecker getUpdateChecker() {
        return updateChecker;
    }

    public @Nullable PluginMetrics getMetrics() {
        return metrics;
    }

    @Override
    public ColoredLogger getColoredLogger() {
        return coloredLogger;
    }

    @Override
    public ManagerInitializer getInitializer() {
        return managerInitializer;
    }

    @Override
    public @Nullable Database getDatabase() {
        return database;
    }

    public @NotNull Settings getSettings() {
        return settings;
    }

    @Override
    public @NotNull PluginScheduler getScheduler() {
        return scheduler;
    }

    @Override
    public @NotNull String getPermission(@NotNull String permission) {
        return getName().toLowerCase() + "." + permission;
    }

    @Override
    public @NotNull NamespacedKey getNamespacedKey(@NotNull String key) {
        return new NamespacedKey(this, key);
    }

    @Override
    public @NotNull OptionsManager getOptions() {
        return optionsManager;
    }

    @Override
    public @NotNull LanguageManager getLanguages() {
        return languageManager;
    }

    @Override
    public @NotNull String getLang(@NotNull String name) {
        return languageManager.getLang(name);
    }

    @Override
    public @NotNull String getLang(@NotNull String name, @NotNull String def) {
        return languageManager.getLang(name, def);
    }

    @Override
    public @NotNull CommandManager getCommands() {
        return commandManager;
    }

    @Override
    public @NotNull PlayerStateManager<? extends PlayerState> getPlayersState() {
        return playerManager;
    }

    @Override
    public @NotNull PlayerState getPlayerState(@NotNull Player player) {
        return playerManager.getPlayerState(player);
    }

    @Override
    public @Nullable PlayerState getPlayerState(@NotNull String playerName) {
        return playerManager.getPlayerState(playerName);
    }

    @Override
    public @Nullable PlayerState getPlayerState(@NotNull UUID playerId) {
        return playerManager.getPlayerState(playerId);
    }

    @Override
    public @NotNull ItemManager getItems() {
        return itemManager;
    }

    @Override
    public @Nullable ItemStack getItemStack(@NotNull String name) {
        return itemManager.getItemStack(name);
    }

    @Override
    public @Nullable Item getItem(@NotNull String name) {
        return itemManager.get(name);
    }

    @Override
    public @NotNull List<Item> getItems(@NotNull String group) {
        return itemManager.getAll(group);
    }

    @Override
    public @NotNull MessageManager getMessages() {
        return messageManager;
    }

    @Override
    public @Nullable Message getMessage(@NotNull String name) {
        return messageManager.get(name);
    }

    @Override
    public @NotNull ParticleManager getParticles() {
        return particleManager;
    }

    @Override
    public @Nullable ParticleEffect getParticle(@NotNull String name) {
        return particleManager.get(name);
    }

    @Override
    public @NotNull SoundManager getSounds() {
        return soundManager;
    }

    @Override
    public @Nullable SoundEffect getSound(@NotNull String name) {
        return soundManager.get(name);
    }

    @Override
    public @NotNull FunctionManager getFunctions() {
        return functionManager;
    }

    @Override
    public @Nullable Function getFunction(@NotNull String name) {
        return functionManager.get(name);
    }

    @Override
    public @NotNull StructureManager getStructures() {
        return structureManager;
    }

    @Override
    public @Nullable BlockStructure getStructure(@NotNull String name) {
        return structureManager.get(name);
    }

    @Override
    public @NotNull Configurator getConfigurator() {
        Preconditions.checkNotNull(configurator, "Configurator has not been instantiated.");
        return configurator;
    }

    @Override
    public @NotNull RootSection getConfiguration() {
        Preconditions.checkNotNull(configurator, "Configuration has not been instantiated.");
        return config;
    }

    @Override
    public @Nullable EnhancedCommand getCustomCommand(String name) {
        return commandManager.getCustomCommand(name);
    }

    @Override
    public void sendMessage(@NotNull Player player, @NotNull String messageId) {
        Chat.send(player, getLang(messageId));
    }

    @Override
    public void sendMessage(@NotNull Player player, @NotNull String messageId, PlaceholderSupplier... placeholderSuppliers) {
        Chat.send(player, getLang(messageId), placeholderSuppliers);
    }

    @Override
    public void sendMessage(@NotNull CommandSender sender, @NotNull String messageId) {
        Chat.send(sender, getLang(messageId));
    }

    @Override
    public void sendMessage(@NotNull CommandSender sender, @NotNull String messageId, PlaceholderSupplier... placeholderSuppliers) {
        Chat.send(sender, getLang(messageId), placeholderSuppliers);
    }

    @Override
    public void registerCommand(@NotNull EnhancedCommand command) {
        commandManager.registerCommand(command);
    }

    @Override
    public void unregisterCommand(@NotNull String name) {
        commandManager.unregisterCommand(name);
    }

    @Override
    public void registerListener(@NotNull Listener listener) {
        getServer().getPluginManager().registerEvents(listener, this);
    }

    @Override
    public void createDirectory() {
        getDataFolder().mkdirs();
    }

    @Override
    public void createDirectory(@NotNull String path) {
        getFile(path).mkdirs();
    }

    @Override
    public void saveResource(@NotNull String path) {
        if (!getFile(path).exists()) {
            saveResource(path, false);
        }
    }

    @Override
    public File getFile(@NotNull String file) {
        return new File(getDataFolder(), file);
    }

    @Override
    public File getFile(@NotNull String parent, @NotNull String child) {
        return new File(getDataFolder(), parent + "/" + child);
    }

    @Override
    public List<File> getFiles(@NotNull String directoryPath) {
        File directory = getFile(directoryPath);
        List<File> yamlFiles = new ArrayList<>();
        collectYamlFiles(directory, yamlFiles);
        return yamlFiles;
    }

    @Override
    public String getFilePath(@NotNull String path) {
        return path.replaceAll("plugins\\\\" + this.getName() + "\\\\", "");
    }

    @Override
    public List<String> getFilePaths(@NotNull String directory) {
        return getFiles(directory).stream()
                .map(file -> file.getPath().replaceAll("plugins\\\\" + this.getName() + "\\\\" + directory + "\\\\", ""))
                .toList();
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

    protected boolean shouldCreateHook(String pluginName, String minVersion, String maxVersion) {
        final Plugin plugin = SpigotServer.getPlugin(pluginName);
        if (plugin == null) {
            coloredLogger.warning(pluginName + " Hook: plugin not loaded");
            return false;
        }

        final String version = plugin.getDescription().getVersion();
        final Matcher matcher = VERSION_PATTERN.matcher(version);
        if (!matcher.find()) {
            coloredLogger.warning(pluginName + "Hook: found unknown version (" + minVersion + " - " + maxVersion + ")");
            return false;
        }

        final String[] versionParts = matcher.group(1).split("\\.");
        final String[] minVersionParts = minVersion.split("\\.");
        final String[] maxVersionParts = maxVersion.split("\\.");

        if (isVersionLessThan(versionParts, minVersionParts)) {
            coloredLogger.warning(pluginName + " Hook: found incompatible version (" + minVersion + " - " + maxVersion + ")");
            return false;
        }

        if (isVersionGreaterThan(versionParts, maxVersionParts)) {
            coloredLogger.warning(pluginName + " Hook: found incompatible version (" + minVersion + " - " + maxVersion + ")");
            return false;
        }

        coloredLogger.info(pluginName + " Hook: found compatible version (" + minVersion + " - " + maxVersion + ")");
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
