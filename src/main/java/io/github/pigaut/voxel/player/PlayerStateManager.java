package io.github.pigaut.voxel.player;

import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.voxel.plugin.manager.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class PlayerStateManager<TPlugin extends EnhancedJavaPlugin, TPlayer extends GenericPlayerState> extends Manager {

    private final TPlugin plugin;
    private final PlayerStateFactory<TPlugin, TPlayer> playerStateFactory;
    private final Map<UUID, TPlayer> playersByUUID = new HashMap<>();
    private final Map<UUID, TPlayer> playersCache = new HashMap<>();

    public PlayerStateManager(@NotNull TPlugin plugin, @NotNull PlayerStateFactory<TPlugin, TPlayer> playerStateFactory) {
        super(plugin);
        this.plugin = plugin;
        this.playerStateFactory = playerStateFactory;
    }

    public @Nullable TPlayer getPlayerState(@NotNull String name) {
        Player player = Bukkit.getPlayer(name);
        return player != null ? getPlayerState(player) : null;
    }

    public @Nullable TPlayer getPlayerState(@NotNull UUID playerId) {
        return playersByUUID.get(playerId);
    }

    public @NotNull TPlayer getPlayerState(@NotNull Player player) {
        UUID playerId = player.getUniqueId();
        if (playersByUUID.containsKey(playerId)) {
            return playersByUUID.get(playerId);
        }

        TPlayer playerState = playerStateFactory.create(plugin, player);
        playersByUUID.put(playerState.getUniqueId(), playerState);
        return playerState;
    }

    public List<TPlayer> getAllPlayerStates() {
        return new ArrayList<>(playersByUUID.values());
    }

    @Override
    public void enable() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            registerPlayer(player);
        }
        playersCache.clear();
    }

    @Override
    public void disable() {
        playersCache.putAll(playersByUUID);
        playersByUUID.clear();
    }

    public void registerPlayer(@NotNull Player player) {
        UUID playerId = player.getUniqueId();
        TPlayer playerState = playersCache.get(playerId);
        if (playerState != null) {
            playersCache.remove(playerId);
        }
        else {
            playerState = playerStateFactory.create(plugin, player);
        }

        playersByUUID.put(playerState.getUniqueId(), playerState);
    }

    public void unregisterPlayer(@NotNull Player player) {
        UUID playerId = player.getUniqueId();
        TPlayer playerState = playersByUUID.remove(playerId);
        if (playerState != null) {
            playersCache.put(playerId, playerState);

            int playerCacheDuration = plugin.getSettings().getPlayerCacheDuration().getCount();
            plugin.getScheduler().runTaskLater(playerCacheDuration, () -> {
                playersCache.remove(playerId);
            });
        }
    }

}
