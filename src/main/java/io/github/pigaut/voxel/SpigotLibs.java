package io.github.pigaut.voxel;

import io.github.pigaut.yaml.parser.*;
import io.github.pigaut.yaml.parser.deserializer.*;
import org.bukkit.*;
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

}
