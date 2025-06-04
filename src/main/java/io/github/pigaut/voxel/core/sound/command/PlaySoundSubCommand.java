package io.github.pigaut.voxel.core.sound.command;

import io.github.pigaut.voxel.command.node.*;
import io.github.pigaut.voxel.command.parameter.location.*;
import io.github.pigaut.voxel.core.sound.*;
import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.yaml.parser.deserializer.*;
import org.bukkit.*;
import org.jetbrains.annotations.*;

public class PlaySoundSubCommand extends SubCommand {
    public PlaySoundSubCommand(@NotNull EnhancedPlugin plugin) {
        super("play", plugin);
        withPermission(plugin.getPermission("sound.play"));
        withDescription(plugin.getLang("sound-play-command"));
        addParameter(new SoundNameParameter(plugin));
        addParameter(new WorldNameParameter());
        addParameter(new XCoordinateParameter(plugin));
        addParameter(new YCoordinateParameter(plugin));
        addParameter(new ZCoordinateParameter(plugin));

        withCommandExecution((sender, args, placeholders) -> {
            final SoundEffect sound = plugin.getSound(args[0]);
            if (sound == null) {
                plugin.sendMessage(sender, "sound-not-found", placeholders);
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

            sound.play(null, new Location(world, x, y, z));
            plugin.sendMessage(sender, "played-sound", placeholders);
        });
    }
}
