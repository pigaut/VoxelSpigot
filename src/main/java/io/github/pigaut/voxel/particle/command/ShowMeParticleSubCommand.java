package io.github.pigaut.voxel.particle.command;

import io.github.pigaut.voxel.command.node.*;
import io.github.pigaut.voxel.particle.*;
import io.github.pigaut.voxel.plugin.*;
import org.bukkit.*;
import org.jetbrains.annotations.*;

public class ShowMeParticleSubCommand extends LangSubCommand {

    public ShowMeParticleSubCommand(@NotNull EnhancedPlugin plugin) {
        super("particle", plugin);
        withParameters(new ParticleNameParameter(plugin));
        withPlayerExecution((player, args, placeholders) -> {
            final ParticleEffect particle = plugin.getParticle(args[0]);
            if (particle == null) {
                plugin.sendMessage(player, "PARTICLE_NOT_FOUND", placeholders);
                return;
            }
            final Location playerLocation = player.getLocation();
            particle.spawn(player, playerLocation.add(player.getFacing().getDirection().multiply(2)).add(0, 1, 0));
            plugin.sendMessage(player, "SHOW_ME_PARTICLE", placeholders);
        });
    }

}
