package io.github.pigaut.voxel.core.structure;

import io.github.pigaut.voxel.menu.button.*;
import io.github.pigaut.voxel.plugin.manager.*;
import io.github.pigaut.voxel.bukkit.Rotation;
import io.github.pigaut.yaml.convert.format.*;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class BlockStructure implements Identifiable {

    private final String name;
    private final String group;
    private final List<BlockChange> blockChanges;
    private final Material mostCommonMaterial;

    public BlockStructure(List<BlockChange> blockChanges) {
        this(UUID.randomUUID().toString(), null, blockChanges);
    }

    public BlockStructure(String name, @Nullable String group, List<BlockChange> blockChanges) {
        this.name = name;
        this.group = group;
        this.blockChanges = blockChanges;
        this.mostCommonMaterial = this.getMostCommonMaterial();
    }

    @Override
    public @NotNull String getName() {
        return name;
    }

    @Override
    public @Nullable String getGroup() {
        return group;
    }

    @Override
    public @NotNull ItemStack getIcon() {
        return new IconBuilder()
                .withType(mostCommonMaterial)
                .withDisplay(CaseFormatter.toTitleCase(this.getName()))
                .buildIcon();
    }

    public synchronized Material getMostCommonMaterial() {
        if (mostCommonMaterial != null) {
            return mostCommonMaterial;
        }

        final Map<Material, Integer> materialFrequency = new HashMap<>();
        for (BlockChange blockChange : blockChanges) {
            if (blockChange instanceof SimpleBlockChange simpleBlock) {
                Material material = simpleBlock.getType();
                materialFrequency.put(material, materialFrequency.getOrDefault(material, 0) + 1);
            }
        }

        Material mostCommonMaterial = null;
        int maxCount = 0;
        for (Map.Entry<Material, Integer> entry : materialFrequency.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mostCommonMaterial = entry.getKey();
            }
        }

        return mostCommonMaterial != null ? mostCommonMaterial : Material.TERRACOTTA;
    }

    public boolean hasMultipleBlocks() {
        return blockChanges.size() > 1;
    }

    public List<BlockChange> getBlockChanges() {
        return new ArrayList<>(blockChanges);
    }

    public boolean matchBlocks(Location origin, Rotation rotation) {
        for (BlockChange blockChange : blockChanges) {
            if (!blockChange.matchBlock(origin, rotation)) {
                return false;
            }
        }
        return true;
    }

    public @Nullable Block getBlockAt(Location origin, Rotation rotation, Location location) {
        for (BlockChange blockChange : blockChanges) {
            final Block block = blockChange.getBlock(origin, rotation);
            if (block.getLocation().equals(location)) {
                return block;
            }
        }
        return null;
    }

    public List<Block> getBlocks(Location origin, Rotation rotation) {
        return blockChanges.stream()
                .map(component -> component.getBlock(origin, rotation))
                .toList();
    }

    public void updateBlocks(Location origin, Rotation rotation) {
        for (BlockChange blockChange : blockChanges) {
            blockChange.updateBlock(origin, rotation);
        }
    }

    public void removeBlocks(Location origin, Rotation rotation) {
        for (BlockChange blockChange : blockChanges) {
            blockChange.removeBlock(origin, rotation);
        }
    }

}
