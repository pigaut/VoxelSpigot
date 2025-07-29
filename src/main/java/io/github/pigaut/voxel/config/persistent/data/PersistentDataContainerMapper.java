package io.github.pigaut.voxel.config.persistent.data;

import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.*;
import io.github.pigaut.yaml.configurator.mapper.*;
import org.bukkit.*;
import org.bukkit.persistence.*;
import org.jetbrains.annotations.*;

public class PersistentDataContainerMapper implements ConfigMapper<PersistentDataContainer> {

    @Override
    public @NotNull FieldType getDefaultMappingType() {
        return FieldType.SEQUENCE;
    }

    @Override
    public void mapSequence(@NotNull ConfigSequence sequence, @NotNull PersistentDataContainer container) {
        for (NamespacedKey persistentKey : container.getKeys()) {
            final PersistentDataType<?, ?> dataType = PersistentData.getDataType(container, persistentKey);
            if (dataType == null)
                continue;

            final Object value = container.get(persistentKey, dataType);
            if (value == null)
                continue;

            final ConfigSection dataSection = sequence.addSection();
            dataSection.set("key", persistentKey.toString());
            dataSection.set("type", dataType);
            dataSection.set("value", value);
        }
    }

}
