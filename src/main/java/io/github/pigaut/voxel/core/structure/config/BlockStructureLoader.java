package io.github.pigaut.voxel.core.structure.config;

import io.github.pigaut.voxel.core.structure.*;
import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.voxel.plugin.manager.*;
import io.github.pigaut.voxel.server.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.load.*;
import io.github.pigaut.yaml.convert.format.*;
import org.jetbrains.annotations.*;

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
        final String structureName = scalar.toString(CaseStyle.SNAKE);
        final BlockStructure blockStructure = plugin.getStructure(structureName);
        if (blockStructure != null) {
            return blockStructure;
        }
        List<BlockChange> blocks = List.of(scalar.loadRequired(BlockChange.class));

        if (SpigotServer.isFolia()) {
            return new FoliaBlockStructure(plugin, blocks);
        }
        return new BlockStructure(blocks);
    }

    @Override
    public @NotNull BlockStructure loadFromSection(@NotNull ConfigSection section) throws InvalidConfigurationException {
        List<BlockChange> blocks = List.of(section.loadRequired(BlockChange.class));

        BlockStructure structure;

        if (section instanceof ConfigRoot root) {
            String name = root.getName();
            String group = Group.byStructureFile(root.getFile());
            if (SpigotServer.isFolia()) {
                return new FoliaBlockStructure(plugin, name, group, blocks);
            }
            return new BlockStructure(name, group, blocks);
        }

        if (SpigotServer.isFolia()) {
            return new FoliaBlockStructure(plugin, blocks);
        }
        return new BlockStructure(blocks);
    }

    @Override
    public @NotNull BlockStructure loadFromSequence(@NotNull ConfigSequence sequence) throws InvalidConfigurationException {
        List<BlockChange> blocks = sequence.getAll(BlockChange.class);

        if (sequence instanceof ConfigRoot root) {
            String name = root.getName();
            String group = Group.byStructureFile(root.getFile());
            if (SpigotServer.isFolia()) {
                return new FoliaBlockStructure(plugin, name, group, blocks);
            }
            return new BlockStructure(name, group, blocks);
        }

        if (SpigotServer.isFolia()) {
            return new FoliaBlockStructure(plugin, blocks);
        }
        return new BlockStructure(blocks);
    }

}
