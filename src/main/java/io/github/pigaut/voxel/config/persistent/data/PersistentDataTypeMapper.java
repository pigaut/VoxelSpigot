package io.github.pigaut.voxel.config.persistent.data;

import io.github.pigaut.yaml.configurator.*;
import io.github.pigaut.yaml.configurator.mapper.*;
import org.bukkit.persistence.*;
import org.jetbrains.annotations.*;

import static org.bukkit.persistence.PersistentDataType.*;

public class PersistentDataTypeMapper implements ConfigMapper<PersistentDataType> {

    @Override
    public @NotNull FieldType getDefaultMappingType() {
        return FieldType.SCALAR;
    }

    @Override
    public @NotNull Object createScalar(@NotNull PersistentDataType dataType) {
        if (dataType == BYTE) {
            return "byte";
        }

        if (dataType == SHORT) {
            return "short";
        }

        if (dataType == INTEGER) {
            return "integer";
        }

        if (dataType == LONG) {
            return "long";
        }

        if (dataType == FLOAT) {
            return "float";
        }

        if (dataType == DOUBLE) {
            return "double";
        }

        if (dataType == BYTE_ARRAY) {
            return "byte_array";
        }

        if (dataType == INTEGER_ARRAY) {
            return "integer_array";
        }

        if (dataType == LONG_ARRAY) {
            return "long_array";
        }

        if (dataType == TAG_CONTAINER_ARRAY) {
            return "tag_container_array";
        }

        if (dataType == TAG_CONTAINER) {
            return "tag_container";
        }

        return "string";
    }

}
