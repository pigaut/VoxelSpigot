package io.github.pigaut.voxel.function.condition.server;

import org.bukkit.*;

public class OnlinePlayersCondition implements ServerCondition {

    private final int onlinePlayers;

    public OnlinePlayersCondition(int onlinePlayers) {
        this.onlinePlayers = onlinePlayers;
    }

    @Override
    public boolean isMet() {
        return Bukkit.getOnlinePlayers().size() == onlinePlayers;
    }

}
