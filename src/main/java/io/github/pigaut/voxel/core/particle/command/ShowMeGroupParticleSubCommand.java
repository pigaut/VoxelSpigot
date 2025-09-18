package io.github.pigaut.voxel.core.particle.command;

import io.github.pigaut.voxel.command.*;
import io.github.pigaut.voxel.command.node.*;
import io.github.pigaut.voxel.core.particle.*;
import io.github.pigaut.voxel.plugin.*;
import org.bukkit.*;
import org.jetbrains.annotations.*;

public class ShowMeGroupParticleSubCommand extends SubCommand {

    public ShowMeGroupParticleSubCommand(@NotNull EnhancedPlugin plugin) {
        super("show-me-group", plugin);
        withPermission(plugin.getPermission("particle.show-me-group"));
        withDescription(plugin.getLang("particle-show-me-group-command"));
        withParameter(CommandParameters.particleGroup(plugin));
        withParameter(CommandParameter.create("group-particle",
                (sender, args) -> plugin.getParticles().getAllNames(args[0])));
        withPlayerExecution((player, args, placeholders) -> {
            final String particleGroup = args[0];
            if (!plugin.getParticles().containsGroup(particleGroup)) {
                plugin.sendMessage(player, "particle-group-not-found", placeholders);
                return;
            }

            final ParticleEffect particle = plugin.getParticle(args[1]);
            if (particle == null || !particleGroup.equals(particle.getGroup())) {
                plugin.sendMessage(player, "group-particle-not-found", placeholders);
                return;
            }

            final Location playerLocation = player.getLocation();
            particle.spawn(player, playerLocation.add(player.getFacing().getDirection().multiply(2)).add(0, 1, 0));
            plugin.sendMessage(player, "showed-me-particle", placeholders);
        });
    }

}
