package io.github.pigaut.voxel.menu;

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

    public static int getInventoryLength(InventoryType storage, int size) {
        switch (storage) {
            case CHEST, PLAYER, ENDER_CHEST, SHULKER_BOX, BARREL:
                return 9;
            case DISPENSER, DROPPER, WORKBENCH:
                return 3;
            default:
                return storage.getDefaultSize();
        }
    }

    public static int getInventoryHeight(InventoryType storage, int size) {
        switch (storage) {
            case ENDER_CHEST, SHULKER_BOX, BARREL, DISPENSER, DROPPER, WORKBENCH:
                return 3;
            case PLAYER:
                return 4;
            case CHEST:
                return size/9;
            default:
                return 1;
        }
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
