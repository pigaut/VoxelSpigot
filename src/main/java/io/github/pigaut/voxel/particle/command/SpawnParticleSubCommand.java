package io.github.pigaut.voxel.particle.command;

import io.github.pigaut.voxel.command.node.*;
import io.github.pigaut.voxel.command.parameter.*;
import io.github.pigaut.voxel.particle.*;
import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.yaml.parser.deserializer.*;
import org.bukkit.*;
import org.jetbrains.annotations.*;

public class SpawnParticleSubCommand extends SubCommand {
    public SpawnParticleSubCommand(@NotNull EnhancedPlugin plugin) {
        super(plugin.getLang("SPAWN_PARTICLE_COMMAND", "spawn"), plugin);
        withDescription(plugin.getLang("SPAWN_PARTICLE_DESCRIPTION"));
        addParameter(new ParticleNameParameter(plugin));
        addParameter(new WorldNameParameter(plugin));
        addParameter(plugin.getLang("X_COORDINATE_PARAMETER", "x"));
        addParameter(plugin.getLang("Y_COORDINATE_PARAMETER", "y"));
        addParameter(plugin.getLang("Z_COORDINATE_PARAMETER", "z"));

        withCommandExecution((sender, args, placeholders) -> {
            final ParticleEffect particle = plugin.getParticle(args[0]);
            if (particle == null) {
                plugin.sendMessage(sender, "PARTICLE_NOT_FOUND", placeholders);
                return;
            }
            final World world = Bukkit.getWorld(args[1]);
            if (world == null) {
                plugin.sendMessage(sender, "WORLD_NOT_FOUND", placeholders);
                return;
            }

            final Double x = Deserializers.getDouble(args[2]);
            if (x == null) {
                plugin.sendMessage(sender, "EXPECTED_DOUBLE", placeholders);
                return;
            }

            final Double y = Deserializers.getDouble(args[3]);
            if (y == null) {
                plugin.sendMessage(sender, "EXPECTED_DOUBLE", placeholders);
                return;
            }

            final Double z = Deserializers.getDouble(args[4]);
            if (z == null) {
                plugin.sendMessage(sender, "EXPECTED_DOUBLE", placeholders);
                return;
            }

            particle.spawn(null, new Location(world, x, y, z));
            plugin.sendMessage(sender, "SPAWNED_PARTICLE", placeholders);
        });
    }
}
