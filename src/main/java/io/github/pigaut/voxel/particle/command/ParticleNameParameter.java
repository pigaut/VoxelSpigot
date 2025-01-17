package io.github.pigaut.voxel.particle.command;

import io.github.pigaut.voxel.command.parameter.*;
import io.github.pigaut.voxel.plugin.*;
import org.jetbrains.annotations.*;

public class ParticleNameParameter extends CommandParameter {

    public ParticleNameParameter(@NotNull EnhancedPlugin plugin) {
        super(plugin.getLang("particle-name-parameter"), (sender, args) -> plugin.getParticles().getParticleNames());
    }

}
