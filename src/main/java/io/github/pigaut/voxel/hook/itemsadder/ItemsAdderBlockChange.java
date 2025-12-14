package io.github.pigaut.voxel.hook.itemsadder;

import dev.lone.itemsadder.api.*;
import io.github.pigaut.voxel.bukkit.Rotation;
import io.github.pigaut.voxel.core.structure.*;
import org.bukkit.*;
import org.jetbrains.annotations.*;

public class ItemsAdderBlockChange extends OffsetBlockChange {

    private final CustomBlock customBlock;

    public ItemsAdderBlockChange(CustomBlock customBlock, int offsetX, int offsetY, int offsetZ) {
        super(offsetX, offsetY, offsetZ);
        this.customBlock = customBlock;
    }

    @Override
    public boolean isPlaced(@NotNull Location origin, @NotNull Rotation rotation) {
        CustomBlock placedBlock = CustomBlock.byAlreadyPlaced(getBlock(origin, rotation));
        if (placedBlock == null) {
            return false;
        }
        return customBlock.getNamespacedID().equals(placedBlock.getNamespacedID());
    }

    @Override
    public void remove(@NotNull Location origin, @NotNull Rotation rotation) {
        CustomBlock.remove(getLocation(origin, rotation));
    }

    @Override
    public void place(@NotNull Location origin, @NotNull Rotation rotation) {
        customBlock.place(getLocation(origin, rotation));
    }

}
