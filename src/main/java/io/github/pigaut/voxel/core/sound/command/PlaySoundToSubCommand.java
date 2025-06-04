package io.github.pigaut.voxel.core.sound.command;

import io.github.pigaut.voxel.command.node.*;
import io.github.pigaut.voxel.command.parameter.*;
import io.github.pigaut.voxel.core.sound.*;
import io.github.pigaut.voxel.plugin.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.jetbrains.annotations.*;

public class PlaySoundToSubCommand extends SubCommand {

    public PlaySoundToSubCommand(@NotNull EnhancedPlugin plugin) {
        super("play-to", plugin);
        withPermission(plugin.getPermission("sound.play-to"));
        withDescription(plugin.getLang("sound-play-to-command"));
        addParameter(new OnlinePlayerParameter());
        addParameter(new SoundNameParameter(plugin));
        withCommandExecution((sender, args, placeholders) -> {
            final Player player = Bukkit.getPlayer(args[0]);
            if (player == null) {
                plugin.sendMessage(sender, "player-not-online", placeholders);
                return;
            }
           final SoundEffect sound = plugin.getSound(args[1]);
           if (sound == null) {
               plugin.sendMessage(sender, "sound-not-found", placeholders);
               return;
           }
           sound.play(player, player.getLocation());
           plugin.sendMessage(sender, "played-sound-at-player", placeholders);
        });
    }

}
