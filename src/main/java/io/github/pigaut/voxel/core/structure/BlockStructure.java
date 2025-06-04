package io.github.pigaut.voxel.core.structure;

import io.github.pigaut.voxel.menu.button.*;
import io.github.pigaut.voxel.plugin.manager.*;
import io.github.pigaut.voxel.util.Rotation;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.parser.*;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.*;

import java.io.*;
import java.util.*;

public class BlockStructure implements Identifiable {

    private final String name;
    private final String group;
    private final ConfigField field;
    private final List<BlockChange> blockChanges;
    private final Material mostCommonMaterial;

    public BlockStructure(String name, @Nullable String group, ConfigField field, List<BlockChange> blockChanges) {
        this.name = name;
        this.group = group;
        this.field = field;
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
    public @NotNull ConfigField getField() {
        return field;
    }

    @Override
    public @NotNull ItemStack getIcon() {
        return new IconBuilder()
                .withType(mostCommonMaterial)
                .withDisplay(StringFormatter.toTitleCase(this.getName()))
                .buildIcon();
    }

    public Material getMostCommonMaterial() {
        if (mostCommonMaterial != null) {
            return mostCommonMaterial;
        }

        final Map<Material, Integer> materialFrequency = new HashMap<>();
        for (BlockChange blockChange : blockChanges) {
            final Material material = blockChange.getType();
            materialFrequency.put(material, materialFrequency.getOrDefault(material, 0) + 1);
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
