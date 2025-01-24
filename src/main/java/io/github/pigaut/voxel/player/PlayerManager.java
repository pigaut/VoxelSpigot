package io.github.pigaut.voxel.player;

import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.voxel.plugin.manager.Manager;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;
import org.jetbrains.annotations.*;

import java.util.*;

public abstract class PlayerManager<P extends PluginPlayer> extends Manager implements Listener {

    private final PlayerFactory<P> playerFactory;
    private final Map<UUID, P> playersByUUID = new HashMap<>();
    private final Map<String, P> playersByName = new HashMap<>();

    public PlayerManager(@NotNull EnhancedPlugin plugin, @NotNull PlayerFactory<P> playerFactory) {
        super(plugin);
        this.playerFactory = playerFactory;
    }

    public P getPlayer(String name) {
        return playersByName.get(name);
    }

    public P getPlayer(UUID playerId) {
        return playersByUUID.get(playerId);
    }

    @Override
    public void enable() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            addPlayer(playerFactory.create(player));
        }
    }

    @Override
    public void disable() {
        clearPlayers();
    }

    @Override
    public List<Listener> getListeners() {
        return List.of(this);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        addPlayer(playerFactory.create(event.getPlayer()));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        removePlayer(event.getPlayer());
    }

    private void addPlayer(P player) {
        playersByUUID.put(player.getUniqueId(), player);
        playersByName.put(player.getName(), player);
    }

    private void removePlayer(Player player) {
        playersByUUID.remove(player.getUniqueId());
        playersByName.remove(player.getName());
    }

    private void clearPlayers() {
        playersByUUID.clear();
        playersByName.clear();
    }

}
