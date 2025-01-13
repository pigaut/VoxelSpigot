package io.github.pigaut.voxel.function.condition.server;

import org.bukkit.*;

public class MaxOnlinePlayersCondition implements ServerCondition {

    private final int maximumPlayers;

    public MaxOnlinePlayersCondition(int maximumPlayers) {
        this.maximumPlayers = maximumPlayers;
    }

    @Override
    public boolean isMet() {
        return Bukkit.getOnlinePlayers().size() <= maximumPlayers;
    }

}
