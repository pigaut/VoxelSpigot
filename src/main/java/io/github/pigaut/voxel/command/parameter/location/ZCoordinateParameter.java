package io.github.pigaut.voxel.command.parameter.location;

import io.github.pigaut.voxel.command.completion.*;
import io.github.pigaut.voxel.command.parameter.*;
import io.github.pigaut.voxel.plugin.*;
import org.bukkit.block.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class ZCoordinateParameter extends CommandParameter {

    public ZCoordinateParameter(@NotNull EnhancedPlugin plugin) {
        super("z", new ZCoordinateCompletion());
    }

    private static class ZCoordinateCompletion implements PlayerCompletion {
        @Override
        public List<String> tabComplete(Player player, String[] args) {
            final Block targetBlock = player.getTargetBlockExact(6);
            if (targetBlock != null) {
                return List.of(Double.toString(targetBlock.getZ()));
            }
            return List.of("0");
        }

        @Override
        public @NotNull List<String> tabComplete(CommandSender sender, String[] args) {
            if (sender instanceof Player player) {
                return tabComplete(player, args);
            }
            return List.of("0");
        }
    }

}
