package io.github.pigaut.voxel.core.particle.command;

import io.github.pigaut.voxel.command.parameter.*;
import io.github.pigaut.voxel.plugin.*;
import org.jetbrains.annotations.*;

public class ParticleNameParameter extends CommandParameter {

    public ParticleNameParameter(@NotNull EnhancedPlugin plugin) {
        super("particle-name", (sender, args) -> plugin.getParticles().getAllNames());
    }

}
