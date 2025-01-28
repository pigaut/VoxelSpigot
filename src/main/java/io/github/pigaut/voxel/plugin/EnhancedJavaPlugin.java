package io.github.pigaut.voxel.plugin;

import com.jeff_media.updatechecker.*;
import io.github.pigaut.voxel.command.*;
import io.github.pigaut.voxel.config.*;
import io.github.pigaut.voxel.item.*;
import io.github.pigaut.voxel.language.*;
import io.github.pigaut.voxel.message.*;
import io.github.pigaut.voxel.meta.flag.*;
import io.github.pigaut.voxel.meta.placeholder.*;
import io.github.pigaut.voxel.particle.*;
import io.github.pigaut.voxel.player.*;
import io.github.pigaut.voxel.plugin.manager.*;
import io.github.pigaut.voxel.plugin.runnable.*;
import io.github.pigaut.voxel.server.*;
import io.github.pigaut.voxel.sound.*;
import io.github.pigaut.voxel.util.*;
import io.github.pigaut.voxel.version.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.node.scalar.*;
import io.github.pigaut.yaml.node.section.*;
import io.github.pigaut.yaml.node.sequence.*;
import org.bstats.bukkit.*;
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

    private final static Pattern VERSION_PATTERN = Pattern.compile("(^[\\.\\d]*).+");
    protected final Logger logger = getLogger();
    private final LanguageManager languageManager = new PluginLanguageManager(this);
    private final CommandManager commandManager = new CommandManager(this);
    private final PluginPlayerManager playerManager = new PluginPlayerManager(this);
    private final ItemManager itemManager = new PluginItemManager(this);
    private final MessageManager messageManager = new PluginMessageManager(this);
    private final FlagManager flagManager = new PluginFlagManager(this);
    private final ParticleManager particleManager = new PluginParticleManager(this);
    private final SoundManager soundManager = new PluginSoundManager(this);
    private final PluginScheduler scheduler = new PluginScheduler(this);
    private final List<Manager> loadedManagers = new ArrayList<>();
    private RootSection config;
    private UpdateChecker updateChecker = null;
    private Metrics metrics = null;
    private boolean debug = true;

    @Override
    public void onDisable() {
        for (Manager manager : loadedManagers) {
            manager.disable();
            manager.saveData();
        }
        loadedManagers.clear();
    }

    @Override
    public void onEnable() {
        checkSpigotVersion();
        createHooks();
        createFiles();
        loadManagers();
        createMetrics();
        createUpdateChecker();
    }

    public void checkSpigotVersion() throws UnsupportedVersionException {
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
    }

    public void createHooks() {
    }

    public void createFiles() {
        logger.info("Generating directories and files...");
        for (String directory : getPluginDirectories()) {
            createDirectory(directory);
        }
        for (String resource : getPluginResources()) {
            saveResource(resource);
        }
        config = new RootSection(getFile("config.yml"), getConfigurator());
        config.load();
        if (config.getOptionalBoolean("generate-examples").orElse(true)) {
            logger.info("Generating example files...");
            for (String exampleResource : getExampleResources()) {
                saveResource(exampleResource);
            }
        }
    }

    public void createMetrics() {
        final Integer metricsId = getMetricsId();
        if (metricsId != null && metrics != null) {
            metrics = new Metrics(this, metricsId);
            logger.info("Created bStats metrics with id: " + metricsId);
        }
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

    private void loadManagers() {
        debug = config.getOptionalBoolean("debug").orElse(true);

        loadedManagers.add(languageManager);
        loadedManagers.add(commandManager);
        loadedManagers.add(playerManager);
        loadedManagers.add(itemManager);
        loadedManagers.add(messageManager);
        loadedManagers.add(flagManager);
        loadedManagers.add(particleManager);
        loadedManagers.add(soundManager);

        for (Field field : getClass().getDeclaredFields()) {
            final Manager manager = ReflectionUtil.accessField(field, Manager.class, this);
            if (manager != null) {
                loadedManagers.add(manager);
            }
        }

        getCommand("");
        for (Manager manager : loadedManagers) {
            manager.enable();
            manager.loadData();
            manager.getListeners().forEach(this::registerListener);
        }

        final int autoSave = config.getOptionalInteger("auto-save").orElse(0);
        if (autoSave > 0) {
            scheduler.runTaskTimerAsync(autoSave, () -> {
                logger.info("Saving data to database...");
                for (Manager manager : loadedManagers) {
                    if (manager.isAutoSave()) {
                        manager.saveData();
                    }
                }
                logger.info("Data saved successfully to database.");
            });
        }

        getPluginCommands().forEach(this::registerCommand);
        getPluginListeners().forEach(this::registerListener);
    }

    @Override
    public boolean isDebug() {
        return false;
    }

    @Override
    public RootSection loadConfigSection(@NotNull File file) {
        final RootSection config = new RootSection(file, this.getConfigurator());
        config.setDebug(debug);
        config.setPrefix("Orestack");
        config.load();
        return config;
    }

    @Override
    public RootSequence loadConfigSequence(@NotNull File file) {
        final RootSequence config = new RootSequence(file, this.getConfigurator());
        config.setDebug(debug);
        config.setPrefix("Orestack");
        config.load();
        return config;
    }

    @Override
    public RootScalar loadConfigScalar(@NotNull File file) {
        final RootScalar config = new RootScalar(file, this.getConfigurator());
        config.setDebug(debug);
        config.setPrefix("Orestack");
        config.load();
        return config;
    }

    @Override
    public PluginScheduler getScheduler() {
        return scheduler;
    }

    @Override
    public List<SpigotVersion> getCompatibleVersions() {
        return List.of(SpigotVersion.values());
    }

    @Override
    public List<Manager> getLoadedManagers() {
        return new ArrayList<>(loadedManagers);
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
    public @NotNull ParticleManager getParticles() {
        return particleManager;
    }

    @Override
    public @NotNull SoundManager getSounds() {
        return soundManager;
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
    public @Nullable ParticleEffect getParticle(String name) {
        return particleManager.getParticle(name);
    }

    @Override
    public @Nullable SoundEffect getSound(String name) {
        return soundManager.getSound(name);
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
    public File getFile(String file) {
        return new File(getDataFolder(), file);
    }

    @Override
    public File getFile(String parent, String child) {
        return new File(getDataFolder(), parent + "/" + child);
    }

    @Override
    public List<File> getFiles(String directoryPath) {
        File directory = getFile(directoryPath);
        List<File> yamlFiles = new ArrayList<>();
        collectYamlFiles(directory, yamlFiles);
        return yamlFiles;
    }

    @Override
    public List<String> getFilePaths(String directory) {
        return getFiles(directory).stream()
                .map(file -> file.getPath().replaceAll("plugins\\\\" + getName() + "\\\\" + directory + "\\\\", ""))
                .toList();
    }

    @Override
    public void registerListener(Listener listener) {
        getServer().getPluginManager().registerEvents(listener, this);
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

    public List<EnhancedCommand> getPluginCommands() {
        return List.of();
    }

    public List<Listener> getPluginListeners() {
        return List.of();
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
