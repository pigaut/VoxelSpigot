package io.github.pigaut.voxel.player;

import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.voxel.plugin.manager.Manager;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class PlayerStateManager<P extends PlayerState> extends Manager implements Listener {

    private final PlayerStateFactory<P> playerStateFactory;
    private final Map<UUID, P> playersByUUID = new HashMap<>();
    private final Map<String, P> playersByName = new HashMap<>();

    public PlayerStateManager(@NotNull EnhancedJavaPlugin plugin, @NotNull PlayerStateFactory<P> playerStateFactory) {
        super(plugin);
        this.playerStateFactory = playerStateFactory;
    }

    public @Nullable P getPlayerState(@NotNull String name) {
        return playersByName.get(name);
    }

    public @Nullable P getPlayerState(@NotNull UUID playerId) {
        return playersByUUID.get(playerId);
    }

    public @NotNull P getPlayerState(@NotNull Player player) {
        return playersByUUID.getOrDefault(player.getUniqueId(),
                    playersByName.getOrDefault(player.getName(),
                        playerStateFactory.create(plugin, player)));
    }

    public List<P> getAllPlayerStates() {
        return new ArrayList<>(playersByUUID.values());
    }

    @Override
    public void enable() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            this.registerPlayer(playerStateFactory.create(plugin, player));
        }
    }

    @Override
    public void disable() {
        this.clearPlayers();
    }

    @Override
    public void reload() {}

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        this.registerPlayer(playerStateFactory.create(plugin, event.getPlayer()));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        this.unregisterPlayer(event.getPlayer());
    }

    private void registerPlayer(P player) {
        playersByUUID.put(player.getUniqueId(), player);
        playersByName.put(player.getName(), player);
    }

    private void unregisterPlayer(Player player) {
        playersByUUID.remove(player.getUniqueId());
        playersByName.remove(player.getName());
    }

    private void clearPlayers() {
        playersByUUID.clear();
        playersByName.clear();
    }

}
