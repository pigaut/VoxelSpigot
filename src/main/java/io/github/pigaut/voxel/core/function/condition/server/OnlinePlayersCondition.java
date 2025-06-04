package io.github.pigaut.voxel.core.function.condition.server;

import io.github.pigaut.voxel.util.*;
import org.bukkit.*;

public class OnlinePlayersCondition implements ServerCondition {

    private final Amount amount;

    public OnlinePlayersCondition(Amount amount) {
        this.amount = amount;
    }

    @Override
    public boolean isMet() {
        return amount.match(Bukkit.getOnlinePlayers().size());
    }

}
