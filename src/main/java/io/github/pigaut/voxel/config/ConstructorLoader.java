package io.github.pigaut.voxel.config;

import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.loader.*;
import io.github.pigaut.yaml.formatter.*;
import org.jetbrains.annotations.*;

import java.util.function.*;

@FunctionalInterface
public interface ConstructorLoader<T> extends FieldLoader<T> {

    @Override
    @NotNull T loadFromField(ConfigField configField) throws InvalidConfigurationException;

    @Override
    default @NotNull T loadFromSection(@NotNull ConfigSection section) throws InvalidConfigurationException {
        return loadFromField(section.getField("value"));
    }

    @Override
    default @NotNull T loadFromSequence(@NotNull ConfigSequence sequence) throws InvalidConfigurationException {
        return loadFromField(sequence.getField(1));
    }

    static <K, T> ConstructorLoader<T> from(Class<K> type, Function<K, T> function) {
        return field -> function.apply(field.load(type));
    }

    static <T> ConstructorLoader<T> from(Supplier<T> supplier) {
        return field -> supplier.get();
    }

    static <T> ConstructorLoader<T> fromSection(Function<ConfigSection, T> function) {
        return field -> function.apply(field.toSection());
    }

    static <T> ConstructorLoader<T> fromString(Function<String, T> function) {
        return field -> function.apply(field.toScalar().toString(StringColor.FORMATTER));
    }

    static <T> ConstructorLoader<T> fromInteger(Function<Integer, T> function) {
        return field -> function.apply(field.toScalar().toInteger());
    }

    static <T> ConstructorLoader<T> fromBoolean(Function<Boolean, T> function) {
        return field -> function.apply(field.toScalar().toBoolean());
    }

}
