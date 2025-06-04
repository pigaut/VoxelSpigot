package io.github.pigaut.voxel.core.structure.config;

import io.github.pigaut.voxel.core.structure.*;
import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.voxel.plugin.manager.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.loader.*;
import io.github.pigaut.yaml.parser.*;
import org.jetbrains.annotations.*;

import java.io.*;
import java.util.*;

public class BlockStructureLoader implements ConfigLoader<BlockStructure> {

    private final EnhancedPlugin plugin;

    public BlockStructureLoader(EnhancedPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getProblemDescription() {
        return "invalid structure";
    }

    @Override
    public @NotNull BlockStructure loadFromScalar(ConfigScalar scalar) throws InvalidConfigurationException {
        final String structureName = scalar.toString(StringStyle.SNAKE);
        final BlockStructure blockStructure = plugin.getStructure(structureName);
        if (blockStructure == null) {
            throw new InvalidConfigurationException(scalar, "Could not find any block structure with name: " + structureName);
        }
        return blockStructure;
    }

    @Override
    public @NotNull BlockStructure loadFromSection(@NotNull ConfigSection section) throws InvalidConfigurationException {
        final String name;
        final String group;
        if (section instanceof ConfigRoot root) {
            name = root.getName();
            group = PathGroup.byStructureFile(root.getFile());
        }
        else {
            name = UUID.randomUUID().toString();
            group = null;
        }
        final List<BlockChange> blocks = List.of(section.load(BlockChange.class));
        return new BlockStructure(name, group, section, blocks);
    }

    @Override
    public @NotNull BlockStructure loadFromSequence(@NotNull ConfigSequence sequence) throws InvalidConfigurationException {
        final String name;
        final String group;
        if (sequence instanceof ConfigRoot root) {
            name = root.getName();
            group = PathGroup.byStructureFile(root.getFile());
        }
        else {
            name = UUID.randomUUID().toString();
            group = null;
        }
        final List<BlockChange> blocks = sequence.getAll(BlockChange.class);
        if (blocks.size() < 2) {
            throw new InvalidConfigurationException(sequence, "Structure must have at least two blocks in it");
        }
        return new BlockStructure(name, group, sequence, blocks);
    }

}
