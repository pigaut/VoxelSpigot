package io.github.pigaut.voxel.player;

import io.github.pigaut.voxel.plugin.boot.*;
import io.github.pigaut.voxel.plugin.manager.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class PlayerStateManager<P extends PlayerState> extends Manager {

    private final PlayerStateFactory<P> playerStateFactory;
    private final Map<UUID, P> playersByUUID = new HashMap<>();
    private final Map<UUID, P> playersCache = new HashMap<>();

    public PlayerStateManager(@NotNull EnhancedJavaPlugin plugin, @NotNull PlayerStateFactory<P> playerStateFactory) {
        super(plugin);
        this.playerStateFactory = playerStateFactory;
    }

    public @Nullable P getPlayerState(@NotNull String name) {
        Player player = Bukkit.getPlayer(name);
        return player != null ? getPlayerState(player) : null;
    }

    public @Nullable P getPlayerState(@NotNull UUID playerId) {
        return playersByUUID.get(playerId);
    }

    public @NotNull P getPlayerState(@NotNull Player player) {
        UUID playerId = player.getUniqueId();
        if (playersByUUID.containsKey(playerId)) {
            return playersByUUID.get(playerId);
        }

        P playerState = playerStateFactory.create(plugin, player);
        playersByUUID.put(playerState.getUniqueId(), playerState);
        return playerState;
    }

    public List<P> getAllPlayerStates() {
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
        P playerState = playersCache.get(playerId);
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
        P playerState = playersByUUID.remove(playerId);
        if (playerState != null) {
            playersCache.put(playerId, playerState);

            int playerCacheDuration = plugin.getSettings().getPlayerCacheDuration().getCount();
            plugin.getScheduler().runTaskLater(playerCacheDuration, () -> {
                playersCache.remove(playerId);
            });
        }
    }

}
