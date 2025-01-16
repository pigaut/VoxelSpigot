package io.github.pigaut.voxel;

import io.github.pigaut.yaml.parser.*;
import io.github.pigaut.yaml.parser.deserializer.*;
import org.bukkit.*;
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

}
