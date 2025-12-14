package io.github.pigaut.voxel.hook.craftengine;

import io.github.pigaut.voxel.bukkit.Rotation;
import io.github.pigaut.voxel.core.structure.*;
import net.momirealms.craftengine.bukkit.api.*;
import net.momirealms.craftengine.core.block.*;
import org.bukkit.*;
import org.bukkit.block.*;
import org.jetbrains.annotations.*;

public class CraftEngineBlockChange extends OffsetBlockChange {

    private final CustomBlock customBlock;

    public CraftEngineBlockChange(CustomBlock customBlock, int offsetX, int offsetY, int offsetZ) {
        super(offsetX, offsetY, offsetZ);
        this.customBlock = customBlock;
    }

    @Override
    public boolean isPlaced(@NotNull Location origin, @NotNull Rotation rotation) {
        Block block = getBlock(origin, rotation);
        return CraftEngineBlocks.isCustomBlock(block);
    }

    @Override
    public void remove(@NotNull Location origin, @NotNull Rotation rotation) {
        Block block = getBlock(origin, rotation);
        CraftEngineBlocks.remove(block);
    }

    @Override
    public void place(@NotNull Location origin, @NotNull Rotation rotation) {
        Location location = getLocation(origin, rotation);
        CraftEngineBlocks.place(location, customBlock.defaultState(), false);
    }

}
