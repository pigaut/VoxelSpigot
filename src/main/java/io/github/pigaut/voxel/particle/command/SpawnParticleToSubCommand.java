package io.github.pigaut.voxel.particle.command;

import io.github.pigaut.voxel.command.node.*;
import io.github.pigaut.voxel.command.parameter.*;
import io.github.pigaut.voxel.particle.*;
import io.github.pigaut.voxel.plugin.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.jetbrains.annotations.*;

public class SpawnParticleToSubCommand extends LangSubCommand {

    public SpawnParticleToSubCommand(@NotNull EnhancedPlugin plugin) {
        super("spawn-particle-to", plugin);
        addParameter(new ParticleNameParameter(plugin));
        addParameter(new OnlinePlayerParameter(plugin));
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
