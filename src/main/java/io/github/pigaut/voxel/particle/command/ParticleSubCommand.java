package io.github.pigaut.voxel.particle.command;

import io.github.pigaut.voxel.command.node.*;
import io.github.pigaut.voxel.plugin.*;
import org.jetbrains.annotations.*;

public class ParticleSubCommand extends SubCommand {

    public ParticleSubCommand(@NotNull EnhancedPlugin plugin) {
        super(plugin.getLang("PARTICLE_COMMAND", "particle"), plugin);
    }

}
