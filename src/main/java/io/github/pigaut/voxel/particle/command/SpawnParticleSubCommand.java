package io.github.pigaut.voxel.particle.command;

import io.github.pigaut.voxel.command.node.*;
import io.github.pigaut.voxel.command.parameter.location.*;
import io.github.pigaut.voxel.particle.*;
import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.yaml.parser.deserializer.*;
import org.bukkit.*;
import org.jetbrains.annotations.*;

public class SpawnParticleSubCommand extends LangSubCommand {
    public SpawnParticleSubCommand(@NotNull EnhancedPlugin plugin) {
        super("spawn-particle", plugin);
        addParameter(new ParticleNameParameter(plugin));
        addParameter(new WorldNameParameter(plugin));
        addParameter(new XCoordinateParameter(plugin));
        addParameter(new YCoordinateParameter(plugin));
        addParameter(new ZCoordinateParameter(plugin));

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

            final Double x = Deserializers.getDouble(args[2]);
            final Double y = Deserializers.getDouble(args[3]);
            final Double z = Deserializers.getDouble(args[4]);

            if (x == null || y == null || z == null) {
                plugin.sendMessage(sender, "expected-coordinates", placeholders);
                return;
            }

            particle.spawn(null, new Location(world, x, y, z));
            plugin.sendMessage(sender, "spawned-particle", placeholders);
        });
    }
}
