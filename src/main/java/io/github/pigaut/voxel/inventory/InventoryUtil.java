package io.github.pigaut.voxel.inventory;

import org.bukkit.*;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.*;

public class InventoryUtil {

    private InventoryUtil() {}

    public static boolean isValidInventory(InventoryType storage, int size) {
        return storage == InventoryType.CHEST ? (size > 0 && size <= 54 && size % 9 == 0) : size == storage.getDefaultSize();
    }

    public static int getValidSizeOrDefault(InventoryType storage, int size) {
        if (isValidInventory(storage, size)) return size;
        return storage.getDefaultSize();
    }

    public static InventoryWindow getInventoryWindow(InventoryType storage) {
        return getInventoryWindow(storage, storage.getDefaultSize());
    }

    public static InventoryWindow getInventoryWindow(InventoryType storage, int size) {
        size = getValidSizeOrDefault(storage, size);

        switch (storage) {
            case CHEST:
                return new InventoryWindow(storage, size, 9, size/9);
            case DISPENSER, DROPPER, WORKBENCH, CRAFTING:
                return new InventoryWindow(storage, size, 3, 3);
            case FURNACE, MERCHANT, ANVIL:
                return new InventoryWindow(storage, size, 1, 3);
            case ENCHANTING:
                return new InventoryWindow(storage, size, 1, 2);
            case BREWING, HOPPER:
                return new InventoryWindow(storage, size, 1, 5);
            case PLAYER, CREATIVE:
                return new InventoryWindow(storage, size, 9, 4);
            case ENDER_CHEST, SHULKER_BOX:
                return new InventoryWindow(storage, size, 9, 3);
            case BEACON:
                return new InventoryWindow(storage, size, 1, 1);
        }

        return null;
    }

    public static Inventory createInventory(InventoryType storage) {
        return createInventory(storage.getDefaultTitle(), storage, storage.getDefaultSize());
    }

    public static Inventory createInventory(InventoryType storage, int size) {
        return createInventory(storage.getDefaultTitle(), storage, size);
    }

    public static Inventory createInventory(String title, InventoryType storage, int size) {
        if (storage == InventoryType.CHEST) {
            return Bukkit.createInventory(null, size, title);
        }
        return Bukkit.createInventory(null, storage, title);
    }

}
