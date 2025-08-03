package io.github.pigaut.voxel.plugin.manager;

import com.google.common.collect.*;
import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.yaml.parser.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class ManagerContainer<T extends Identifiable> extends Manager implements Container<T> {

    private final Map<String, T> valuesByName = new HashMap<>();
    private final Multimap<String, T> valuesByGroup = Multimaps.newListMultimap(new HashMap<>(), ArrayList::new);

    public ManagerContainer(@NotNull EnhancedJavaPlugin plugin) {
        super(plugin);
    }

    @Override
    public boolean contains(@NotNull String name) {
        return valuesByName.containsKey(name);
    }

    @Override
    public @Nullable T get(@NotNull String name) {
        return valuesByName.get(name);
    }

    @Override
    public @NotNull List<T> getAll() {
        return new ArrayList<>(valuesByName.values());
    }

    @Override
    public @NotNull List<T> getAll(@NotNull String group) {
        return new ArrayList<>(valuesByGroup.get(group));
    }

    @Override
    public void add(@NotNull T value) throws DuplicateElementException {
        final String name = StringFormatter.toSnakeCase(value.getName());
        if (valuesByName.containsKey(name)) {
            throw new DuplicateElementException(name);
        }
        valuesByName.put(name, value);
        final String group = value.getGroup();
        if (group != null) {
            valuesByGroup.put(StringFormatter.toSnakeCase(group), value);
        }
    }

    @Override
    public void remove(@NotNull String name) {
        final T value = valuesByName.remove(name);
        if (value != null) {
            final String group = value.getGroup();
            if (group != null) {
                valuesByGroup.remove(group, value);
            }
        }
    }

    @Override
    public void removeAll(@NotNull String group) {
        for (T value : valuesByGroup.removeAll(group)) {
            valuesByName.remove(value.getName());
        }
    }

    @Override
    public @NotNull List<String> getAllNames() {
        return new ArrayList<>(valuesByName.keySet());
    }

    @Override
    public @NotNull List<String> getAllGroups() {
        return new ArrayList<>(valuesByGroup.keySet());
    }

    @Override
    public void clear() {
        valuesByName.clear();
        valuesByGroup.clear();
    }

    @Override
    public void disable() {
        this.clear();
    }

}
