package io.github.pigaut.voxel.function.action.block;

import io.github.pigaut.voxel.function.action.*;
import io.github.pigaut.voxel.particle.*;
import io.github.pigaut.voxel.player.*;
import org.bukkit.block.*;
import org.jetbrains.annotations.*;

public class SpawnParticleOnBlock implements Action {

    private final ParticleEffect particle;

    public SpawnParticleOnBlock(ParticleEffect particle) {
        this.particle = particle;
    }

    @Override
    public void execute(@Nullable PluginPlayer player, @Nullable Block block) {
        if (block != null) {
            particle.spawn(player != null ? player.asPlayer() : null, block.getLocation());
        }
    }

}
