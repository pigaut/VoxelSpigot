package io.github.pigaut.voxel.core.particle.command;

import io.github.pigaut.voxel.command.node.*;
import io.github.pigaut.voxel.plugin.*;
import org.jetbrains.annotations.*;

public class ParticleSubCommand extends SubCommand {

    public ParticleSubCommand(@NotNull EnhancedPlugin plugin) {
        super("particle", plugin);
        withPermission(plugin.getPermission("particle"));
        withDescription(plugin.getLang("particle-command"));
        addSubCommand(new SpawnParticleSubCommand(plugin));
        addSubCommand(new SpawnParticleToSubCommand(plugin));
        addSubCommand(new ShowMeParticleSubCommand(plugin));
    }

}
