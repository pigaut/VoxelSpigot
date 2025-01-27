package io.github.pigaut.voxel.sound.command;

import io.github.pigaut.voxel.command.node.*;
import io.github.pigaut.voxel.command.parameter.*;
import io.github.pigaut.voxel.particle.*;
import io.github.pigaut.voxel.particle.command.*;
import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.voxel.sound.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.jetbrains.annotations.*;

public class PlaySoundToSubCommand extends LangSubCommand {

    public PlaySoundToSubCommand(@NotNull EnhancedPlugin plugin) {
        super("play-sound-to", plugin);
        addParameter(new OnlinePlayerParameter(plugin));
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
