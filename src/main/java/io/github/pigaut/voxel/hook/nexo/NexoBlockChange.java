package io.github.pigaut.voxel.hook.nexo;

import com.nexomc.nexo.api.*;
import io.github.pigaut.voxel.bukkit.Rotation;
import io.github.pigaut.voxel.core.structure.*;
import org.bukkit.*;
import org.bukkit.block.*;
import org.jetbrains.annotations.*;

public class NexoBlockChange extends OffsetBlockChange {

    private final String blockId;

    public NexoBlockChange(String blockId, int offsetX, int offsetY, int offsetZ) {
        super(offsetX, offsetY, offsetZ);
        this.blockId = blockId;
    }

    @Override
    public boolean matchBlock(Location origin, Rotation rotation) {
        return NexoBlocks.isCustomBlock(blockId);
    }

    @Override
    public @NotNull Block getBlock(Location origin, Rotation rotation) {
        return getLocation(origin, rotation).getBlock();
    }

    @Override
    public void removeBlock(Location origin, Rotation rotation) {
        NexoBlocks.remove(getLocation(origin, rotation));
    }

    @Override
    public void updateBlock(Location origin, Rotation rotation) {
        NexoBlocks.place(blockId, getLocation(origin, rotation));
    }

}
