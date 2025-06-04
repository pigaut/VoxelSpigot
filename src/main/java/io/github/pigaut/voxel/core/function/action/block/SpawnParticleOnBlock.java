package io.github.pigaut.voxel.core.function.action.block;

import io.github.pigaut.voxel.core.function.action.*;
import io.github.pigaut.voxel.core.particle.*;
import io.github.pigaut.voxel.player.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.jetbrains.annotations.*;

public class SpawnParticleOnBlock implements Action {

    private final ParticleEffect particle;

    public SpawnParticleOnBlock(ParticleEffect particle) {
        this.particle = particle;
    }

    @Override
    public void execute(@Nullable PlayerState player, @Nullable Event event, @Nullable Block block, @Nullable Entity target) {
        if (block != null) {
            particle.spawn(player != null ? player.asPlayer() : null, block.getLocation().add(0.5, 0.5, 0.5));
        }
    }

}
