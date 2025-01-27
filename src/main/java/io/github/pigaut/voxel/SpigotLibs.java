package io.github.pigaut.voxel;

import io.github.pigaut.voxel.server.*;
import io.github.pigaut.yaml.parser.*;
import io.github.pigaut.yaml.parser.deserializer.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import org.bukkit.util.*;
import org.jetbrains.annotations.*;

public class SpigotLibs {

    private static final Deserializer<Material> materialDeserializer = Deserializers.enumDeserializer(Material.class);

    public static @Nullable Material getMaterial(String value) {
        try {
            return materialDeserializer.deserialize(value);
        } catch (DeserializationException e) {
            return null;
        }
    }

    public static Material deserializeMaterial(String value) throws DeserializationException {
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
                new Location(null, location.getBlockX(), 3, location.getBlockY()),
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

    public static @NotNull ArmorStand createHologram(@NotNull Location location, boolean persistent) {
        return createHologram("&7", location, persistent);
    }

}
