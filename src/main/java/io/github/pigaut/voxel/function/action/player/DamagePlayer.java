package io.github.pigaut.voxel.function.action.player;

import io.github.pigaut.voxel.player.*;
import org.jetbrains.annotations.*;

public class DamagePlayer implements PlayerAction {

    private final int health;

    public DamagePlayer(int health) {
        this.health = -health;
    }

    @Override
    public void execute(@NotNull PluginPlayer player) {
        player.heal(health);
    }

}
