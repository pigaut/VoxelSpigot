package io.github.pigaut.voxel.function.action.player;

import io.github.pigaut.voxel.player.*;
import org.bukkit.*;
import org.jetbrains.annotations.*;

public class StrikePlayerWithLightning implements PlayerAction {

    private final boolean doDamage;

    public StrikePlayerWithLightning(boolean doDamage) {
        this.doDamage = doDamage;
    }

    @Override
    public void execute(@NotNull PluginPlayer player) {
        final World world = player.getWorld();
        final Location location = player.getLocation();

        if (doDamage) {
            world.strikeLightning(location);
        }
        else {
            world.strikeLightningEffect(location);
        }
    }

}
