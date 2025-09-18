package io.github.pigaut.voxel.core.particle.command;

import io.github.pigaut.voxel.command.*;
import io.github.pigaut.voxel.command.node.*;
import io.github.pigaut.voxel.core.particle.*;
import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.yaml.convert.parse.*;
import org.bukkit.*;
import org.jetbrains.annotations.*;

public class SpawnParticleSubCommand extends SubCommand {

    public SpawnParticleSubCommand(@NotNull EnhancedPlugin plugin) {
        super("spawn", plugin);
        withPermission(plugin.getPermission("particle.spawn"));
        withDescription(plugin.getLang("particle-spawn-command"));
        withParameter(CommandParameters.particleName(plugin));
        withParameter(CommandParameters.WORLD_NAME);
        withParameter(CommandParameters.X_COORDINATE);
        withParameter(CommandParameters.Y_COORDINATE);
        withParameter(CommandParameters.Z_COORDINATE);
        withCommandExecution((sender, args, placeholders) -> {
            final ParticleEffect particle = plugin.getParticle(args[0]);
            if (particle == null) {
                plugin.sendMessage(sender, "particle-not-found", placeholders);
                return;
            }
            final World world = Bukkit.getWorld(args[1]);
            if (world == null) {
                plugin.sendMessage(sender, "world-not-found", placeholders);
                return;
            }

            final Double x = ParseUtil.parseDoubleOrNull(args[2]);
            final Double y = ParseUtil.parseDoubleOrNull(args[3]);
            final Double z = ParseUtil.parseDoubleOrNull(args[4]);

            if (x == null || y == null || z == null) {
                plugin.sendMessage(sender, "expected-coordinates", placeholders);
                return;
            }

            particle.spawn(null, new Location(world, x, y, z));
            plugin.sendMessage(sender, "spawned-particle", placeholders);
        });
    }
}
