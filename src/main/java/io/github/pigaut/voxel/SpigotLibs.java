package io.github.pigaut.voxel;

import io.github.pigaut.voxel.config.deserializer.*;
import io.github.pigaut.voxel.hologram.modern.options.*;
import io.github.pigaut.voxel.server.*;
import io.github.pigaut.yaml.parser.*;
import io.github.pigaut.yaml.parser.deserializer.*;
import org.bukkit.*;
import org.bukkit.block.data.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import org.bukkit.util.*;
import org.jetbrains.annotations.*;

public class SpigotLibs {

    private static final Deserializer<Material> materialDeserializer = new MaterialDeserializer();
    private static DisplayFactory DISPLAY_FACTORY = null;

    static {
        try {
            Class.forName("org.bukkit.entity.TextDisplay");
            Class.forName("org.bukkit.entity.ItemDisplay");
            Class.forName("org.bukkit.entity.BlockDisplay");
            DISPLAY_FACTORY = new DisplayFactory();
        } catch (ClassNotFoundException e) {}

    }

    public static @Nullable Material getMaterial(String value) {
        try {
            return materialDeserializer.deserialize(value);
        } catch (DeserializationException e) {
            return null;
        }
    }

    public static @NotNull Material deserializeMaterial(String value) throws DeserializationException {
        return materialDeserializer.deserialize(value);
    }

    public static Location getOffsetLocation(Location location, double right, double up, double front) {
        Vector direction = location.getDirection().normalize();
        Vector rightVector = direction.getCrossProduct(new Vector(0, 1, 0)).normalize();
        Vector upVector = new Vector(0, 1, 0);
        Vector offset = rightVector.multiply(right)
                .add(upVector.multiply(up))
                .add(direction.multiply(front));

        return location.add(offset);
    }

    public static boolean areChunksEqual(Chunk chunk, Chunk chunkToCompare) {
        return chunk.getX() == chunkToCompare.getX() && chunk.getZ() == chunkToCompare.getZ();
    }

    public static @NotNull World getWorldOrDefault(@NotNull Location location) {
        final World world = location.getWorld();
        return world != null ? world : SpigotServer.getDefaultWorld();
    }

    public static @NotNull ArmorStand createHologram(@NotNull String displayName, @NotNull Location location, boolean persistent) {
        ArmorStand hologram = (ArmorStand) location.getWorld().spawnEntity(
                new Location(null, location.getBlockX(), 3, location.getBlockZ()),
                EntityType.ARMOR_STAND
        );
        hologram.setVisible(false);
        hologram.setGravity(false);
        hologram.setMarker(true);
        hologram.setArms(false);
        hologram.setBasePlate(false);
        hologram.setCanPickupItems(false);
        for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
            hologram.addEquipmentLock(equipmentSlot, ArmorStand.LockType.REMOVING_OR_CHANGING);
        }
        hologram.teleport(location);
        hologram.setPersistent(persistent);
        hologram.setCustomNameVisible(true);
        hologram.setCustomName(displayName);
        return hologram;
    }

    public static @NotNull ArmorStand createEmptyHologram(@NotNull Location location, boolean persistent) {
        return createHologram("&7", location, persistent);
    }

    public static @NotNull Entity createTextDisplay(@NotNull Location location, String text, TextDisplayOptions options, boolean persistent) {
        if (DISPLAY_FACTORY == null) {
            throw new IllegalStateException("Text displays can only be created in spigot version 1.19.4+");
        }
        return DISPLAY_FACTORY.createTextDisplay(location, text, options, persistent);
    }

    public static @NotNull Entity createEmptyTextDisplay(@NotNull Location location, TextDisplayOptions options, boolean persistent) {
        return createTextDisplay(location, "", options, persistent);
    }

    public static @NotNull Entity createItemDisplay(@NotNull Location location, @NotNull ItemStack item, DisplayOptions options, boolean persistent) {
        if (DISPLAY_FACTORY == null) {
            throw new IllegalStateException("Item displays can only be created in spigot version 1.19.4+");
        }
        return DISPLAY_FACTORY.createItemDisplay(location, item, options, persistent);
    }

    public static @NotNull Entity createBlockDisplay(@NotNull Location location, @NotNull BlockData blockData, DisplayOptions options, boolean persistent) {
        if (DISPLAY_FACTORY == null) {
            throw new IllegalStateException("Block displays can only be created in spigot version 1.19.4+");
        }
        return DISPLAY_FACTORY.createBlockDisplay(location, blockData, options, persistent);
    }


    private static class DisplayFactory {

        public Entity createTextDisplay(Location location, String text, TextDisplayOptions options, boolean persistent) {
            final TextDisplay display = (TextDisplay) location.getWorld().spawnEntity(location, EntityType.TEXT_DISPLAY);
            options.apply(display);
            display.setPersistent(persistent);
            display.setText(text);
            return display;
        }

        public Entity createItemDisplay(Location location, ItemStack item, DisplayOptions options, boolean persistent) {
            final ItemDisplay display = (ItemDisplay) location.getWorld().spawnEntity(location, EntityType.ITEM_DISPLAY);
            options.apply(display);
            display.setPersistent(persistent);
            display.setItemStack(item);
            return display;
        }

        public Entity createBlockDisplay(Location location, BlockData blockData, DisplayOptions options, boolean persistent) {
            final BlockDisplay display = (BlockDisplay) location.getWorld().spawnEntity(location, EntityType.BLOCK_DISPLAY);
            options.apply(display);
            display.setPersistent(persistent);
            display.setBlock(blockData);
            return display;
        }

    }


}
