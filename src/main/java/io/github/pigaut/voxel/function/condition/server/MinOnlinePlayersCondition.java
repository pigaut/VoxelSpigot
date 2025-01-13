package io.github.pigaut.voxel.function.condition.server;

import org.bukkit.*;

public class MinOnlinePlayersCondition implements ServerCondition {

    private final int minimumPlayers;

    public MinOnlinePlayersCondition(int minimumPlayers) {
        this.minimumPlayers = minimumPlayers;
    }

    @Override
    public boolean isMet() {
        return Bukkit.getOnlinePlayers().size() >= minimumPlayers;
    }

}
