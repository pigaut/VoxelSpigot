package io.github.pigaut.voxel.particle.command;

import io.github.pigaut.voxel.command.node.*;
import io.github.pigaut.voxel.particle.*;
import io.github.pigaut.voxel.player.*;
import io.github.pigaut.voxel.plugin.*;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.*;

public class SpawnParticleSubCommand extends SubCommand {

    public SpawnParticleSubCommand(@NotNull EnhancedPlugin plugin) {
        super(plugin.getLang("SPAWN_PARTICLE_COMMAND", "spawn"), plugin);
        withDescription(plugin.getLang("SPAWN_PARTICLE_DESCRIPTION"));
        addParameter(plugin.getLang("PARTICLE_NAME_PARAMETER", "particle-name"));
        withPlayerCompletion((player, args) -> plugin.getParticles().getParticleNames());
        withPlayerExecution((player, args) -> {
            final ParticleEffect particle = plugin.getParticle(args[0]);

            if (particle == null) {
                plugin.sendMessage(player, "PARTICLE_NOT_FOUND");
                return;
            }

            final Location playerLocation = player.getLocation();
            particle.spawn(player, playerLocation.add(player.getFacing().getDirection().multiply(2)).add(0, 1, 0));
            plugin.sendMessage(player, "SPAWNED_PARTICLE");
        });
    }

}
