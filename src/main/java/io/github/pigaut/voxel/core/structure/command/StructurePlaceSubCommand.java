package io.github.pigaut.voxel.core.structure.command;

import io.github.pigaut.voxel.command.*;
import io.github.pigaut.voxel.command.node.*;
import io.github.pigaut.voxel.core.structure.*;
import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.voxel.bukkit.Rotation;
import org.bukkit.*;
import org.bukkit.block.*;
import org.jetbrains.annotations.*;

public class StructurePlaceSubCommand extends SubCommand {

    public StructurePlaceSubCommand(@NotNull EnhancedPlugin plugin) {
        super("place", plugin);
        withPermission(plugin.getPermission("structure.place"));
        withDescription(plugin.getTranslation("structure-place-command"));
        withParameter(CommandParameters.structureName(plugin));
        withPlayerExecution((player, args, placeholders) -> {
            final BlockStructure structure = plugin.getStructure(args[0]);
            if (structure == null) {
                plugin.sendMessage(player, "structure-not-found", placeholders);
                return;
            }

            final Block targetBlock = player.getTargetBlockExact(6);
            if (targetBlock == null) {
                plugin.sendMessage(player, "too-far-away", placeholders);
                return;
            }

            final Location location = targetBlock.getLocation();
            structure.updateBlocks(location, Rotation.NONE);
            plugin.sendMessage(player, "placed-structure", placeholders);
        });
    }

}
