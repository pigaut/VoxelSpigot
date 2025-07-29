package io.github.pigaut.voxel.config.deserializer;

import com.cryptomorin.xseries.*;
import io.github.pigaut.yaml.configurator.parser.*;
import io.github.pigaut.yaml.parser.*;
import org.bukkit.*;
import org.jetbrains.annotations.*;

public class MaterialDeserializer implements ConfigDeserializer<Material> {

    @Override
    public @Nullable String getProblemDescription() {
        return "invalid material";
    }

    @Override
    public @NotNull Material deserialize(@NotNull String materialName) throws DeserializationException {
        final Material material = XMaterial.matchXMaterial(materialName).map(XMaterial::get).orElse(null);
        if (material == null) {
            throw new DeserializationException("Expected a material but found: " + materialName);
        }
        return material;
    }

}
