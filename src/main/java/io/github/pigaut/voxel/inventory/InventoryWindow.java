package io.github.pigaut.voxel.inventory;

import org.bukkit.event.inventory.*;

public class InventoryWindow {

    private final InventoryType storage;
    private final int size;
    private final int length;
    private final int height;

    public InventoryWindow(InventoryType storage, int size, int length, int height) {
        this.storage = storage;
        this.size = size;
        this.length = length;
        this.height = height;
    }

    public static InventoryWindow of(InventoryType storage) {
        return InventoryUtil.getInventoryWindow(storage);
    }

    public static InventoryWindow of(InventoryType storage, int size) {
        return InventoryUtil.getInventoryWindow(storage, size);
    }

    public InventoryType getStorage() {
        return storage;
    }

    public int getSize() {
        return size;
    }

    public int getLength() {
        return length;
    }

    public int getHeight() {
        return height;
    }

}
