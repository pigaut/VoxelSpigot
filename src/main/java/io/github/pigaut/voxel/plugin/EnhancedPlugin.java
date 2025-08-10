package io.github.pigaut.voxel.plugin;

import io.github.pigaut.sql.*;
import io.github.pigaut.voxel.command.*;
import io.github.pigaut.voxel.config.*;
import io.github.pigaut.voxel.core.function.*;
import io.github.pigaut.voxel.core.item.*;
import io.github.pigaut.voxel.core.item.Item;
import io.github.pigaut.voxel.core.message.*;
import io.github.pigaut.voxel.core.particle.*;
import io.github.pigaut.voxel.core.sound.*;
import io.github.pigaut.voxel.core.structure.*;
import io.github.pigaut.voxel.hologram.*;
import io.github.pigaut.voxel.language.*;
import io.github.pigaut.voxel.menu.*;
import io.github.pigaut.voxel.placeholder.*;
import io.github.pigaut.voxel.player.*;
import io.github.pigaut.voxel.plugin.runnable.*;
import io.github.pigaut.yaml.node.*;
import io.github.pigaut.yaml.node.scalar.*;
import io.github.pigaut.yaml.node.section.*;
import io.github.pigaut.yaml.node.sequence.*;
import io.github.pigaut.yaml.util.*;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.inventory.*;
import org.bukkit.plugin.*;
import org.jetbrains.annotations.*;

import javax.xml.crypto.*;
import java.io.*;
import java.util.*;

public interface EnhancedPlugin extends Plugin {

    boolean isDebug();

    String getVersion();

    @Nullable
    Database getDatabase();

    @NotNull
    PluginScheduler getScheduler();

    @NotNull
    String getPermission(@NotNull String permission);

    @NotNull
    NamespacedKey getNamespacedKey(@NotNull String key);

    @NotNull
    LanguageManager getLanguages();

    @NotNull
    String getLang(@NotNull String name) throws LangNotFoundException;

    @NotNull
    String getLang(@NotNull String name, @NotNull String def);

    @NotNull
    CommandManager getCommands();

    @NotNull
    PlayerStateManager<? extends @NotNull PlayerState> getPlayersState();

    @NotNull
    PlayerState getPlayerState(@NotNull Player player);

    @Nullable
    PlayerState getPlayerState(@NotNull String playerName);

    @Nullable
    PlayerState getPlayerState(@NotNull UUID playerId);

    @NotNull
    ItemManager getItems();

    @Nullable
    ItemStack getItemStack(@NotNull String name);

    @Nullable
    Item getItem(@NotNull String name);

    @NotNull
    List<Item> getItems(@NotNull String group);

    @NotNull
    MessageManager getMessages();

    @Nullable
    Message getMessage(@NotNull String name);

    @NotNull
    ParticleManager getParticles();

    @Nullable
    ParticleEffect getParticle(@NotNull String name);

    @NotNull
    SoundManager getSounds();

    @Nullable
    SoundEffect getSound(@NotNull String name);

    @NotNull
    FunctionManager getFunctions();

    @Nullable
    Function getFunction(@NotNull String name);

    @NotNull
    StructureManager getStructures();

    @Nullable
    BlockStructure getStructure(@NotNull String name);

    @NotNull
    MenuManager getMenus();

    @Nullable
    Menu getMenu(@NotNull String name);

    @NotNull
    HologramManager getHolograms();

    @NotNull
    Collection<HologramDisplay> getHolograms(@NotNull Chunk chunk);

    @NotNull
    RootSection getConfiguration();

    @NotNull
    PluginConfigurator getConfigurator();

    @Nullable
    EnhancedCommand getCustomCommand(String name);

    void sendMessage(@NotNull Player player, @NotNull String messageId) throws LangNotFoundException;

    void sendMessage(@NotNull Player player, @NotNull String messageId, @NotNull PlaceholderSupplier... placeholderSuppliers) throws LangNotFoundException;

    void sendMessage(@NotNull CommandSender sender, @NotNull String messageId) throws LangNotFoundException;

    void sendMessage(@NotNull CommandSender sender, @NotNull String messageId, @NotNull PlaceholderSupplier... placeholderSuppliers) throws LangNotFoundException;

    void registerCommand(@NotNull EnhancedCommand command);

    void unregisterCommand(@NotNull String name);

    void registerListener(@NotNull Listener listener);

    void createDirectory();

    void createDirectory(@NotNull String path);

    void saveResource(@NotNull String path);

    File getFile(@NotNull String file);

    File getFile(@NotNull String parent, @NotNull String child);

    List<File> getFiles(@NotNull String path);

    String getFilePath(@NotNull String path);

    List<String> getFilePaths(@NotNull String directory);

    void logConfigurationErrors(@Nullable Player player, @NotNull List<ConfigurationException> errors);

}
