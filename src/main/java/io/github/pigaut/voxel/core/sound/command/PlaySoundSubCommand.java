package io.github.pigaut.voxel.core.sound.command;

import io.github.pigaut.voxel.command.*;
import io.github.pigaut.voxel.command.node.*;
import io.github.pigaut.voxel.core.sound.*;
import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.yaml.convert.parse.*;
import org.bukkit.*;
import org.jetbrains.annotations.*;

public class PlaySoundSubCommand extends SubCommand {
    public PlaySoundSubCommand(@NotNull EnhancedPlugin plugin) {
        super("play", plugin);
        withPermission(plugin.getPermission("sound.play"));
        withDescription(plugin.getLang("sound-play-command"));
        withParameter(CommandParameters.soundName(plugin));
        withParameter(CommandParameters.WORLD_NAME);
        withParameter(CommandParameters.X_COORDINATE);
        withParameter(CommandParameters.Y_COORDINATE);
        withParameter(CommandParameters.Z_COORDINATE);
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

            final Double x = ParseUtil.parseDoubleOrNull(args[2]);
            final Double y = ParseUtil.parseDoubleOrNull(args[3]);
            final Double z = ParseUtil.parseDoubleOrNull(args[4]);

            if (x == null || y == null || z == null) {
                plugin.sendMessage(sender, "expected-coordinates", placeholders);
                return;
            }

            sound.play(null, new Location(world, x, y, z));
            plugin.sendMessage(sender, "played-sound", placeholders);
        });
    }
}
