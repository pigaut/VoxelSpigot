package io.github.pigaut.voxel.particle.command;

import io.github.pigaut.voxel.command.node.*;
import io.github.pigaut.voxel.particle.*;
import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.voxel.server.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class SpawnParticleToSubCommand extends SubCommand {

    public SpawnParticleToSubCommand(@NotNull EnhancedPlugin plugin) {
        super(plugin.getLang("SPAWN_PARTICLE_TO_COMMAND", "spawn-to"), plugin);
        withDescription(plugin.getLang("SPAWN_PARTICLE_TO_DESCRIPTION"));
        addParameter(plugin.getLang("PARTICLE_NAME_PARAMETER", "particle-name"));
        addParameter(plugin.getLang("PLAYER_NAME_PARAMETER", "player-name"));
        withTabCompletion((player, args) -> {
            if (args.length == 1) {
               return plugin.getParticles().getParticleNames();
            }
            if (args.length == 2) {
                return SpigotServer.getOnlinePlayerNames();
            }
            return List.of();
        });
        withCommandExecution((sender, args, placeholders) -> {
           final ParticleEffect particle = plugin.getParticle(args[0]);
           if (particle == null) {
               plugin.sendMessage(sender, "PARTICLE_NOT_FOUND", placeholders);
               return;
           }

           final Player player = Bukkit.getPlayer(args[1]);

           if (player == null) {
               plugin.sendMessage(sender, "PLAYER_NOT_FOUND", placeholders);
               return;
           }

           particle.spawn(player, player.getLocation());
        });
    }

}
