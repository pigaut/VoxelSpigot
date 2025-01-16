package io.github.pigaut.voxel.particle.command;

import io.github.pigaut.voxel.command.node.*;
import io.github.pigaut.voxel.plugin.*;
import org.jetbrains.annotations.*;

public class ParticleSubCommand extends LangSubCommand {

    public ParticleSubCommand(@NotNull EnhancedPlugin plugin) {
        super("particle", plugin);
        addSubCommand(new SpawnParticleSubCommand(plugin));
        addSubCommand(new SpawnParticleToSubCommand(plugin));
        addSubCommand(new ShowMeParticleSubCommand(plugin));
    }

}
