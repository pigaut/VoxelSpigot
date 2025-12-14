package io.github.pigaut.voxel.core.structure;

import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.voxel.plugin.manager.*;
import io.github.pigaut.yaml.*;
import org.jetbrains.annotations.*;

public class StructureManager extends ConfigBackedManager.Sequence<BlockStructure> {

    public StructureManager(@NotNull EnhancedJavaPlugin plugin) {
        super(plugin, "structures");
    }

    @Override
    public void loadFromSequence(ConfigSequence sequence) throws InvalidConfigurationException {
        BlockStructure structure = sequence.loadRequired(BlockStructure.class);
        try {
            add(structure);
        }
        catch (DuplicateElementException e) {
            throw new InvalidConfigurationException(sequence, e.getMessage());
        }
    }

}
