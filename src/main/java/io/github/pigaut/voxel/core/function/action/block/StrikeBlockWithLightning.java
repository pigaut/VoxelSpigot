package io.github.pigaut.voxel.core.function.action.block;

import org.bukkit.*;
import org.bukkit.block.*;
import org.jetbrains.annotations.*;

public class StrikeBlockWithLightning implements BlockAction {

    private final boolean doDamage;

    public StrikeBlockWithLightning(boolean doDamage) {
        this.doDamage = doDamage;
    }

    @Override
    public void execute(@NotNull Block block) {
        final World world = block.getWorld();
        final Location location = block.getLocation();

        if (doDamage) {
            world.strikeLightning(location);
        }
        else {
            world.strikeLightningEffect(location);
        }
    }

}
