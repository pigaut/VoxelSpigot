package io.github.pigaut.voxel.plugin;

import io.github.pigaut.voxel.command.*;
import io.github.pigaut.voxel.config.*;
import io.github.pigaut.voxel.item.*;
import io.github.pigaut.voxel.language.*;
import io.github.pigaut.voxel.message.*;
import io.github.pigaut.voxel.meta.flag.*;
import io.github.pigaut.voxel.meta.placeholder.*;
import io.github.pigaut.voxel.particle.*;
import io.github.pigaut.voxel.player.*;
import io.github.pigaut.voxel.plugin.runnable.*;
import io.github.pigaut.voxel.sound.*;
import io.github.pigaut.voxel.version.*;
import io.github.pigaut.yaml.node.section.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.inventory.*;
import org.bukkit.plugin.*;
import org.jetbrains.annotations.*;

import java.io.*;
import java.util.*;

public interface EnhancedPlugin extends Plugin {

    PluginScheduler getScheduler();

    void reload();

    @NotNull
    List<SpigotVersion> getCompatibleVersions();

    @NotNull
    List<String> getPluginDirectories();

    @NotNull
    List<String> getPluginResources();

    @NotNull
    List<String> getExampleResources();

    @NotNull
    CommandManager getCommands();

    @NotNull
    PlayerManager<? extends PluginPlayer> getPlayers();

    @NotNull
    ItemManager getItems();

    @NotNull
    MessageManager getMessages();

    @NotNull
    LanguageManager getLanguages();

    @NotNull
    FlagManager getFlags();

    @NotNull
    ParticleManager getParticles();

    @NotNull
    SoundManager getSounds();

    @NotNull
    RootSection getConfiguration();

    @NotNull
    PluginConfigurator getConfigurator();

    @Nullable
    EnhancedCommand getCustomCommand(String name);

    void registerCommand(EnhancedCommand command);

    void unregisterCommand(String name);

    @Nullable
    PluginPlayer getPlayer(String playerName);

    @Nullable
    PluginPlayer getPlayer(UUID playerId);

    @Nullable
    ItemStack getItemStack(String name);

    @Nullable
    Message getMessage(String name);

    @NotNull
    String getLang(String name);

    @NotNull
    String getLang(String name, String def);

    @NotNull
    String getLang(Locale locale, String name);

    void sendMessage(Player player, String messageId);

    void sendMessage(Player player, String messageId, PlaceholderSupplier... placeholderSuppliers);

    void sendMessage(CommandSender sender, String messageId);

    void sendMessage(CommandSender sender, String messageId, PlaceholderSupplier... placeholderSuppliers);

    @Nullable
    Flag getFlag(String name);

    @Nullable
    ParticleEffect getParticle(String name);

    @Nullable
    SoundEffect getSound(String name);

    void createDirectory();

    void createDirectory(String path);

    void saveResource(String path);

    File getFile(String file);

    File getFile(String directory, String file);

    List<File> getFiles(String path);

    List<String> getFilePaths(String directory);

    void registerListener(Listener listener);

}
