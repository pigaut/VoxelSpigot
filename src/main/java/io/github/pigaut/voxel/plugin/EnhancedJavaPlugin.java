package io.github.pigaut.voxel.plugin;

import com.jeff_media.updatechecker.*;
import io.github.pigaut.sql.*;
import io.github.pigaut.sql.database.*;
import io.github.pigaut.voxel.bukkit.*;
import io.github.pigaut.voxel.command.*;
import io.github.pigaut.voxel.config.*;
import io.github.pigaut.voxel.core.function.*;
import io.github.pigaut.voxel.core.function.Function;
import io.github.pigaut.voxel.core.item.*;
import io.github.pigaut.voxel.core.item.Item;
import io.github.pigaut.voxel.core.message.*;
import io.github.pigaut.voxel.core.particle.*;
import io.github.pigaut.voxel.core.sound.*;
import io.github.pigaut.voxel.core.structure.*;
import io.github.pigaut.voxel.hologram.*;
import io.github.pigaut.voxel.hook.*;
import io.github.pigaut.voxel.language.*;
import io.github.pigaut.voxel.menu.*;
import io.github.pigaut.voxel.placeholder.*;
import io.github.pigaut.voxel.player.*;
import io.github.pigaut.voxel.player.input.*;
import io.github.pigaut.voxel.plugin.manager.*;
import io.github.pigaut.voxel.plugin.runnable.*;
import io.github.pigaut.voxel.server.*;
import io.github.pigaut.voxel.util.*;
import io.github.pigaut.voxel.version.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.node.section.*;
import io.github.pigaut.yaml.util.*;
import org.bstats.charts.*;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.inventory.*;
import org.bukkit.plugin.*;
import org.bukkit.plugin.java.*;
import org.jetbrains.annotations.*;

import java.io.*;
import java.lang.reflect.Field;
import java.util.*;
import java.util.function.*;
import java.util.logging.*;
import java.util.regex.*;

public abstract class EnhancedJavaPlugin extends JavaPlugin implements EnhancedPlugin {

    private final static Pattern VERSION_PATTERN = Pattern.compile("(^[\\.\\d]*).+");

    protected final Logger logger = getLogger();
    protected final PluginScheduler scheduler = new PluginScheduler(this);

    private final LanguageManager languageManager = new SimpleLanguageManager(this);
    private final CommandManager commandManager = new CommandManager(this);
    private final PlayerStateManager<SimplePlayerState> playerManager = new PlayerStateManager<>(this, SimplePlayerState::new);
    private final ItemManager itemManager = new ItemManager(this);
    private final MessageManager messageManager = new MessageManager(this);
    private final ParticleManager particleManager = new ParticleManager(this);
    private final SoundManager soundManager = new SoundManager(this);
    private final FunctionManager functionManager = new FunctionManager(this);
    private final StructureManager structureManager = new StructureManager(this);
    private final MenuManager menuManager = new MenuManager(this);
    private final HologramManager hologramManager = new HologramManager(this);

    private final List<Manager> loadedManagers = new ArrayList<>();

    private RootSection config;
    private UpdateChecker updateChecker = null;
    private PluginMetrics metrics = null;
    private Database database = null;
    private boolean debug = true;
    private boolean reloading = false;

    private EconomyHook economy = null;
    private boolean papi = false;

    @Override
    public void onEnable() {
        final SpigotVersion serverVersion = SpigotServer.getVersion();
        if (serverVersion == SpigotVersion.UNKNOWN) {
            logger.severe("Found unknown server version");
        } else {
            if (!getCompatibleVersions().contains(serverVersion)) {
                throw new UnsupportedVersionException(this);
            } else {
                logger.info("Found compatible server version: " + serverVersion);
            }
        }

        this.createHooks();

        logger.info("Generating directories and files...");
        for (String directory : getPluginDirectories()) {
            createDirectory(directory);
        }
        for (String resource : getPluginResources()) {
            saveResource(resource);
        }

        config = new RootSection(this.getFile("config.yml"), this.getConfigurator());
        config.load();
        debug = config.getOptionalBoolean("debug").orElse(true);

        if (config.getOptionalBoolean("generate-examples").orElse(true)) {
            logger.info("Generating example files...");
            for (String resourcePath : getExampleResources()) {
                saveResource(resourcePath);
            }

            final SpigotVersion currentVersion = SpigotServer.getVersion();
            final Map<SpigotVersion, List<String>> examplesByVersion = getExamplesByVersion();
            for (SpigotVersion requiredVersion : examplesByVersion.keySet()) {
                if (currentVersion.equalsOrIsNewerThan(requiredVersion)) {
                    for (String resourcePath : examplesByVersion.get(requiredVersion)) {
                        saveResource(resourcePath);
                    }
                }
            }
        }

        logger.info("Establishing database connection...");
        final String databaseName = getDatabaseName();
        if (database != null) {
            database.openConnection();
        }
        else if (databaseName != null) {
            final File file = getFile(databaseName);
            database = new FileDatabase(file);
            database.openConnection();
        }

        logger.info("Loading configuration and data...");
        loadedManagers.add(languageManager);
        loadedManagers.add(commandManager);
        loadedManagers.add(playerManager);
        loadedManagers.add(itemManager);
        loadedManagers.add(messageManager);
        loadedManagers.add(particleManager);
        loadedManagers.add(soundManager);
        loadedManagers.add(functionManager);
        loadedManagers.add(structureManager);
        loadedManagers.add(hologramManager);

        for (Field field : getClass().getDeclaredFields()) {
            final Manager manager = ReflectionUtil.accessField(field, Manager.class, this);
            if (manager != null) {
                loadedManagers.add(manager);
            }
        }

        loadedManagers.add(menuManager);

        getCommand("");
        final List<ConfigurationException> errorsFound = new ArrayList<>();
        for (Manager manager : loadedManagers) {
            manager.enable();

            try {
                manager.loadData();
            } catch (ConfigurationException e) {
                errorsFound.add(e);
            }

            final String filesDirectory = manager.getFilesDirectory();
            if (filesDirectory != null) {
                for (File file : getFiles(filesDirectory)) {
                    try {
                        manager.loadFile(file);
                    } catch (ConfigurationException e) {
                        errorsFound.add(e);
                    }
                }
            }

            if (manager instanceof Listener listener) {
                registerListener(listener);
            }
        }
        logConfigurationErrors(null, errorsFound);

        final int autoSave = config.getOptionalInteger("auto-save").orElse(0);
        if (autoSave > 0) {
            scheduler.runTaskTimerAsync(autoSave, () -> {
                logger.info("Saving plugin data to database...");
                for (Manager manager : loadedManagers) {
                    if (manager.isAutoSave()) {
                        manager.saveData();
                    }
                }
                logger.info("All plugin data saved successfully.");
            });
        }

        registerListener(new PlayerInputListener(this));

        this.getPluginListeners().forEach(this::registerListener);
        this.getPluginCommands().forEach(this::registerCommand);

        if (metrics != null) {
            metrics.shutdown();
        }
        final Integer metricsId = getMetricsId();
        if (metricsId != null) {
            if (forceMetrics() || config.getOptionalBoolean("metrics").orElse(true)) {
                logger.info("Created bStats metrics with id: " + metricsId);
                metrics = new PluginMetrics(this, metricsId);
                metrics.addCustomChart(new SimplePie("premium", () -> Boolean.toString(isPremium())));
            }
        }

        if (updateChecker != null) {
            updateChecker.stop();
        }
        final boolean checkForUpdates = config.getOptionalBoolean("check-for-updates").orElse(true);
        final Integer spigotId = getResourceId();
        if (checkForUpdates && spigotId != null) {
            updateChecker = new UpdateChecker(this, UpdateCheckSource.SPIGET, Integer.toString(spigotId))
                    .setDownloadLink("https://www.spigotmc.org/resources/" + spigotId)
                    .setChangelogLink("https://www.spigotmc.org/resources/" + spigotId + "/updates")
                    .setDonationLink(getDonationLink())
                    .setNotifyOpsOnJoin(true)
                    .checkEveryXHours(24)
                    .checkNow();
        }

    }

    @Override
    public void onDisable() {
        logger.info("Saving data to database...");
        for (Manager manager : loadedManagers) {
            manager.disable();
            manager.saveData();
        }
        loadedManagers.clear();
        if (database != null) {
            database.closeConnection();
        }
    }

    public void reload(Consumer<List<ConfigurationException>> errorCollector) throws PluginReloadInProgressException {
        if (reloading) {
            throw new PluginReloadInProgressException();
        }

        reloading = true;
        createHooks();

        logger.info("Generating directories and files...");
        for (String directory : getPluginDirectories()) {
            createDirectory(directory);
        }
        for (String resource : getPluginResources()) {
            saveResource(resource);
        }

        config.load();
        debug = config.getOptionalBoolean("debug").orElse(true);

        if (config.getOptionalBoolean("generate-examples").orElse(true)) {
            logger.info("Generating example files...");
            for (String resourcePath : getExampleResources()) {
                saveResource(resourcePath);
            }

            final SpigotVersion currentVersion = SpigotServer.getVersion();
            final Map<SpigotVersion, List<String>> examplesByVersion = getExamplesByVersion();
            for (SpigotVersion requiredVersion : examplesByVersion.keySet()) {
                if (currentVersion.equalsOrIsNewerThan(requiredVersion)) {
                    for (String resourcePath : examplesByVersion.get(requiredVersion)) {
                        saveResource(resourcePath);
                    }
                }
            }
        }

        for (Manager manager : loadedManagers) {
            manager.disable();
            manager.enable();
        }

        scheduler.runTaskLaterAsync(1, () -> {
            logger.info("Saving data to database...");
            loadedManagers.forEach(Manager::saveData);

            logger.info("Loading configuration and data...");
            final List<ConfigurationException> errorsFound = new ArrayList<>();
            for (Manager manager : loadedManagers) {
                manager.loadData();

                final String filesDirectory = manager.getFilesDirectory();
                if (filesDirectory != null) {
                    for (File file : getFiles(filesDirectory)) {
                        try {
                            manager.loadFile(file);
                        } catch (ConfigurationException e) {
                            errorsFound.add(e);
                        }
                    }
                }
            }
            errorCollector.accept(errorsFound);
            reloading = false;
        });
    }

    public void createHooks() {
        logger.info("Looking for compatible plugins...");

        if (SpigotServer.isPluginLoaded("Vault")) {
            economy = EconomyHook.newInstance();
            if (economy != null) {
                logger.info("Vault Hook: created successfully");
            } else {
                logger.warning("Vault Hook: missing economy plugin");
            }
        } else {
            logger.info("Vault Hook: plugin not loaded");
        }

        if (SpigotServer.isPluginLoaded("PlaceholderAPI")) {
            papi = true;
            logger.info("PlaceholderAPI Hook: created successfully");
        } else {
            logger.info("PlaceholderAPI Hook: plugin not loaded");
        }
    }

    public @Nullable EconomyHook getEconomy() {
        return economy;
    }

    public boolean isPlaceholderAPI() {
        return papi;
    }

    public void generateFiles() {
        logger.info("Generating directories and files...");
        for (String directory : getPluginDirectories()) {
            createDirectory(directory);
        }
        for (String resource : getPluginResources()) {
            saveResource(resource);
        }
    }

    @Override
    public @NotNull String getPermission(@NotNull String permission) {
        return this.getName().toLowerCase() + "." + permission;
    }

    @Override
    public @NotNull NamespacedKey getNamespacedKey(@NotNull String key) {
        return new NamespacedKey(this, key);
    }

    public void generateExamples() {
        if (!config.getOptionalBoolean("generate-examples").orElse(true)) {
            return;
        }

        logger.info("Generating example files...");
        for (String resourcePath : getExampleResources()) {
            saveResource(resourcePath);
        }

        final SpigotVersion currentVersion = SpigotServer.getVersion();
        final Map<SpigotVersion, List<String>> examplesByVersion = getExamplesByVersion();
        for (SpigotVersion requiredVersion : examplesByVersion.keySet()) {
            if (currentVersion.equalsOrIsNewerThan(requiredVersion)) {
                for (String resourcePath : examplesByVersion.get(requiredVersion)) {
                    saveResource(resourcePath);
                }
            }
        }

    }

    public boolean isReloading() {
        return reloading;
    }

    public boolean forceMetrics() {
        return false;
    }

    public void createMetrics() {
        final Integer metricsId = getMetricsId();
        if (metricsId == null || metrics != null) {
            return;
        }

        if (!forceMetrics() && !config.getOptionalBoolean("metrics").orElse(true)) {
            return;
        }

        metrics = new PluginMetrics(this, metricsId);
        logger.info("Created bStats metrics with id: " + metricsId);
    }

    public void createUpdateChecker() {
        if (!config.getOptionalBoolean("check-for-updates").orElse(true)) {
            return;
        }
        if (updateChecker != null) {
            updateChecker.checkNow();
            return;
        }
        final Integer spigotId = getResourceId();
        if (spigotId == null) {
            return;
        }
        updateChecker = new UpdateChecker(this, UpdateCheckSource.SPIGET, Integer.toString(spigotId))
                .setDownloadLink("https://www.spigotmc.org/resources/" + spigotId)
                .setChangelogLink("https://www.spigotmc.org/resources/" + spigotId + "/updates")
                .setDonationLink(getDonationLink())
                .setNotifyOpsOnJoin(true)
                .checkEveryXHours(24)
                .checkNow();
    }

    public void createDatabase() {
        if (database != null) {
            database.openConnection();
            return;
        }
        final String databaseName = getDatabaseName();
        if (databaseName == null) {
            return;
        }
        final File file = getFile(databaseName);
        database = new FileDatabase(file);
        database.openConnection();
    }

    @Override
    public boolean isDebug() {
        return debug;
    }

    @Override
    public String getVersion() {
        return this.getDescription().getVersion();
    }

    @Override
    public @Nullable Database getDatabase() {
        return database;
    }

    @Override
    public @NotNull PluginScheduler getScheduler() {
        return scheduler;
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
    public @NotNull MenuManager getMenus() {
        return menuManager;
    }

    @Override
    public @Nullable Menu getMenu(@NotNull String name) {
        return menuManager.getMenu(name);
    }

    @Override
    public @NotNull HologramManager getHolograms() {
        return hologramManager;
    }

    @Override
    public @NotNull Collection<HologramDisplay> getHolograms(@NotNull Chunk chunk) {
        return hologramManager.getAllHolograms(chunk);
    }

    @Override
    public @NotNull RootSection getConfiguration() {
        if (config == null) {
            config = new RootSection(getFile("config.yml"), getConfigurator());
            config.load();
            return config;
        }
        config.load();
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

    public @NotNull List<SpigotVersion> getCompatibleVersions() {
        return List.of(SpigotVersion.values());
    }

    public @NotNull List<Manager> getLoadedManagers() {
        return new ArrayList<>(loadedManagers);
    }

    public boolean isPremium() {
        return false;
    }

    public @Nullable Integer getMetricsId() {
        return null;
    }

    public @Nullable Integer getResourceId() {
        return null;
    }

    public @Nullable String getDonationLink() {
        return null;
    }

    public List<String> getPluginDirectories() {
        return List.of();
    }

    public List<String> getPluginResources() {
        return List.of();
    }

    public List<String> getExampleResources() {
        return List.of();
    }

    public Map<SpigotVersion, List<String>> getExamplesByVersion() {
        return Map.of();
    }

    public List<EnhancedCommand> getPluginCommands() {
        return List.of();
    }

    public List<Listener> getPluginListeners() {
        return List.of();
    }

    public Map<String, Menu> getPluginMenus() {
        return Map.of();
    }

    public @Nullable String getDatabaseName() {
        return null;
    }

    @Override
    public void logConfigurationErrors(@Nullable Player player, @NotNull List<ConfigurationException> errors) {
        if (errors.isEmpty()) {
            return;
        }

        final PlaceholderSupplier errorCount = PlaceholderSupplier.of("{error_count}", errors.size());
        errors.forEach(error -> logger.severe(error.getLogMessage(getDataFolder().getPath())));

        if (player == null) {
            return;
        }

        if (debug) {
            sendMessage(player, "debug-configuration-errors", errorCount);
            errors.forEach(error -> player.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + error.getLogMessage()));
            return;
        }

        sendMessage(player, "configuration-errors", errorCount);
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
