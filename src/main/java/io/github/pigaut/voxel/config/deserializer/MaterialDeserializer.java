package io.github.pigaut.voxel.config.deserializer;

import com.cryptomorin.xseries.*;
import io.github.pigaut.yaml.configurator.deserialize.*;
import io.github.pigaut.yaml.convert.parse.*;
import org.bukkit.*;
import org.jetbrains.annotations.*;

public class MaterialDeserializer implements Deserializer<Material> {

    @Override
    public @Nullable String getProblemDescription() {
        return "invalid material";
    }

    @Override
    public @NotNull Material deserialize(@NotNull String materialName) throws StringParseException {
        final Material material = XMaterial.matchXMaterial(materialName)
                .map(XMaterial::get)
                .orElse(null);

        if (material == null) {
            throw new StringParseException("Expected a material but found: " + materialName);
        }

        return material;
    }

}
