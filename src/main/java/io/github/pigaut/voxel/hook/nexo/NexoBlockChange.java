package io.github.pigaut.voxel.hook.nexo;

import com.nexomc.nexo.api.*;
import io.github.pigaut.voxel.bukkit.Rotation;
import io.github.pigaut.voxel.core.structure.*;
import org.bukkit.*;
import org.jetbrains.annotations.*;

public class NexoBlockChange extends OffsetBlockChange {

    private final String blockId;

    public NexoBlockChange(String blockId, int offsetX, int offsetY, int offsetZ) {
        super(offsetX, offsetY, offsetZ);
        this.blockId = blockId;
    }

    @Override
    public boolean isPlaced(@NotNull Location origin, @NotNull Rotation rotation) {
        return NexoBlocks.isCustomBlock(blockId);
    }

    @Override
    public void remove(@NotNull Location origin, @NotNull Rotation rotation) {
        NexoBlocks.remove(getLocation(origin, rotation));
    }

    @Override
    public void place(@NotNull Location origin, @NotNull Rotation rotation) {
        NexoBlocks.place(blockId, getLocation(origin, rotation));
    }

}
