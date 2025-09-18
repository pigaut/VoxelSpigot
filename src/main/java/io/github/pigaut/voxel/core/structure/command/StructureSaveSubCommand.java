package io.github.pigaut.voxel.core.structure.command;

import io.github.pigaut.voxel.command.*;
import io.github.pigaut.voxel.command.node.*;
import io.github.pigaut.voxel.core.structure.*;
import io.github.pigaut.voxel.player.*;
import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.node.sequence.*;
import org.bukkit.*;
import org.bukkit.block.*;
import org.jetbrains.annotations.*;
import org.snakeyaml.engine.v2.common.*;

import java.io.*;
import java.util.*;

public class StructureSaveSubCommand extends SubCommand {

    public StructureSaveSubCommand(@NotNull EnhancedPlugin plugin) {
        super("save", plugin);
        withPermission(plugin.getPermission("structure.save"));
        withDescription(plugin.getLang("structure-save-command"));
        withParameter(CommandParameters.filePath(plugin, "structures"));
        withPlayerExecution((player, args, placeholders) -> {
            final PlayerState playerState = plugin.getPlayerState(player.getUniqueId());
            if (playerState == null) {
                plugin.sendMessage(player, "loading-player-data", placeholders);
                return;
            }

            final File file = plugin.getFile("structures", args[0]);
            YamlConfig.createFileIfNotExists(file);

            if (!YamlConfig.isYamlFile(file)) {
                plugin.sendMessage(player, "not-yaml-file", placeholders);
                return;
            }

            final Location firstSelection = playerState.getFirstSelection();
            final Location secondSelection = playerState.getSecondSelection();
            if (firstSelection == null || secondSelection == null) {
                plugin.sendMessage(player, "incomplete-region", placeholders);
                return;
            }

            final int centerX = (int) ((firstSelection.getBlockX() + secondSelection.getBlockX()) / 2.0);
            final int lowestY = Math.min(firstSelection.getBlockY(), secondSelection.getBlockY());
            final int centerZ = (int) ((firstSelection.getBlockZ() + secondSelection.getBlockZ()) / 2.0);

            final RootSequence sequence = YamlConfig.createEmptySequence(file, plugin.getConfigurator());
            sequence.setFlowStyle(FlowStyle.AUTO);

            final Set<Material> structureBlacklist = plugin.getStructures().getMaterialBlacklist();
            for (Location location : CuboidRegion.getAllLocations(player.getWorld(), firstSelection, secondSelection)) {
                final Block block = location.getBlock();
                final Material blockType = block.getType();

                if (structureBlacklist.contains(blockType)) {
                    continue;
                }

                final ConfigSection blockConfig = sequence.addEmptySection();
                blockConfig.map(block);
                blockConfig.set("offset.x", location.getBlockX() - centerX);
                blockConfig.set("offset.y", location.getBlockY() - lowestY);
                blockConfig.set("offset.z", location.getBlockZ() - centerZ);
            }

            if (sequence.size() < 2) {
                plugin.sendMessage(player, "structure-minimum-blocks", placeholders);
                return;
            }

            sequence.save();
            plugin.getStructures().reload();
            plugin.sendMessage(player, "saved-structure", placeholders);
        });
    }

}
