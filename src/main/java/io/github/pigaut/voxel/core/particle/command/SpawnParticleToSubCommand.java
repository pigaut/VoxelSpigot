package io.github.pigaut.voxel.core.particle.command;

import io.github.pigaut.voxel.command.*;
import io.github.pigaut.voxel.command.node.*;
import io.github.pigaut.voxel.core.particle.*;
import io.github.pigaut.voxel.plugin.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.jetbrains.annotations.*;

public class SpawnParticleToSubCommand extends SubCommand {

    public SpawnParticleToSubCommand(@NotNull EnhancedPlugin plugin) {
        super("spawn-to", plugin);
        withPermission(plugin.getPermission("particle.spawn-to"));
        withDescription(plugin.getLang("particle-spawn-to-command"));
        withParameter(CommandParameters.ONLINE_PLAYER);
        withParameter(CommandParameters.particleName(plugin));
        withCommandExecution((sender, args, placeholders) -> {
            final Player player = Bukkit.getPlayer(args[0]);
            if (player == null) {
                plugin.sendMessage(sender, "player-not-online", placeholders);
                return;
            }
           final ParticleEffect particle = plugin.getParticle(args[1]);
           if (particle == null) {
               plugin.sendMessage(sender, "particle-not-found", placeholders);
               return;
           }
           particle.spawn(player, player.getLocation());
           plugin.sendMessage(sender, "spawned-particle-at-player", placeholders);
        });
    }

}
