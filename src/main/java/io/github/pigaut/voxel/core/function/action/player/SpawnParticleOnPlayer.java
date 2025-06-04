package io.github.pigaut.voxel.core.function.action.player;

import io.github.pigaut.voxel.core.particle.*;
import io.github.pigaut.voxel.player.*;
import org.jetbrains.annotations.*;

public class SpawnParticleOnPlayer implements PlayerAction {

    private final ParticleEffect particle;

    public SpawnParticleOnPlayer(ParticleEffect particle) {
        this.particle = particle;
    }

    @Override
    public void execute(@NotNull PlayerState player) {
        particle.spawn(player.asPlayer(), player.getLocation());
    }

}
