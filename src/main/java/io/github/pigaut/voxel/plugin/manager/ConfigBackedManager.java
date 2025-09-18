package io.github.pigaut.voxel.plugin.manager;

import com.google.common.collect.*;
import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.*;
import io.github.pigaut.yaml.convert.format.*;
import io.github.pigaut.yaml.util.*;
import org.checkerframework.checker.units.qual.*;
import org.jetbrains.annotations.*;

import java.io.*;
import java.util.*;
import java.util.function.*;

public abstract class ConfigBackedManager<T extends Identifiable> extends Manager implements ConfigBacked, Container<T> {

    protected final String filesDirectory;

    private final Map<String, T> valuesByName = new HashMap<>();
    private final Multimap<String, T> valuesByGroup = Multimaps.newListMultimap(new HashMap<>(), ArrayList::new);

    public ConfigBackedManager(@NotNull EnhancedJavaPlugin plugin, String filesDirectory) {
        super(plugin);
        this.filesDirectory = filesDirectory;
    }

    @Override
    public void reload() {
        reload(errorsFound -> plugin.logConfigurationErrors(null, errorsFound));
    }

    public void reload(Consumer<List<ConfigurationException>> errorCollector) {
        disable();
        enable();
        plugin.getScheduler().runTaskAsync(() -> {
            saveData();
            loadData();
            errorCollector.accept(loadConfigurationData());
        });
    }

    public @NotNull String getFilesDirectory() {
        return filesDirectory;
    }

    @Override
    public @NotNull List<ConfigurationException> loadConfigurationData() {
        if (filesDirectory == null) {
            return List.of();
        }

        final List<ConfigurationException> errorsFound = new ArrayList<>();
        for (File file : plugin.getFiles(filesDirectory)) {
            try {
                loadFromFile(file);
            }
            catch (ConfigurationException e) {
                errorsFound.add(e);
            }
        }

        return errorsFound;
    }

    public abstract void loadFromFile(File file) throws InvalidConfigurationException;

    @Override
    public boolean contains(@NotNull String name) {
        return valuesByName.containsKey(name);
    }

    @Override
    public boolean containsGroup(@NotNull String group) {
        return valuesByGroup.containsKey(group);
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
        final String name = CaseFormatter.toSnakeCase(value.getName());
        if (valuesByName.containsKey(name)) {
            throw new DuplicateElementException(name);
        }
        valuesByName.put(name, value);
        final String group = value.getGroup();
        if (group != null) {
            valuesByGroup.put(CaseFormatter.toSnakeCase(group), value);
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
        return valuesByName.keySet().stream().toList();
    }

    @Override
    public @NotNull List<String> getAllNames(String group) {
        return valuesByGroup.get(group).stream()
                .map(Identifiable::getName)
                .toList();
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

    public abstract static class Section<T extends Identifiable> extends ConfigBackedManager<T> {

        private final String prefix;
        private final Configurator configurator;

        public Section(@NotNull EnhancedJavaPlugin plugin, String prefix, String filesDirectory) {
            super(plugin, filesDirectory);
            this.prefix = prefix;
            this.configurator = plugin.getConfigurator();
        }

        @Override
        public void loadFromFile(File file) throws InvalidConfigurationException {
            loadFromSection(YamlConfig.loadSection(file, configurator, prefix));
        }

        public abstract void loadFromSection(ConfigSection section) throws InvalidConfigurationException;

    }

    public abstract static class Sequence<T extends Identifiable> extends ConfigBackedManager<T> {

        private final String prefix;
        private final Configurator configurator;

        public Sequence(@NotNull EnhancedJavaPlugin plugin, String prefix, String filesDirectory) {
            super(plugin, filesDirectory);
            this.prefix = prefix;
            this.configurator = plugin.getConfigurator();
        }

        @Override
        public void loadFromFile(File file) throws InvalidConfigurationException {
            loadFromSequence(YamlConfig.loadSequence(file, configurator, prefix));
        }

        public abstract void loadFromSequence(ConfigSequence sequence) throws InvalidConfigurationException;

    }

    public abstract static class Scalar<T extends Identifiable> extends ConfigBackedManager<T> {

        private final String prefix;
        private final Configurator configurator;

        public Scalar(@NotNull EnhancedJavaPlugin plugin, String prefix, String filesDirectory) {
            super(plugin, filesDirectory);
            this.prefix = prefix;
            this.configurator = plugin.getConfigurator();
        }

        @Override
        public void loadFromFile(File file) throws InvalidConfigurationException {
            loadFromScalar(YamlConfig.loadScalar(file, configurator, prefix));
        }

        public abstract void loadFromScalar(ConfigScalar scalar) throws InvalidConfigurationException;

    }

    public abstract static class SectionKey<T extends Identifiable> extends ConfigBackedManager<T> {

        private final String prefix;
        private final Configurator configurator;

        public SectionKey(@NotNull EnhancedJavaPlugin plugin, String prefix, String filesDirectory) {
            super(plugin, filesDirectory);
            this.prefix = prefix;
            this.configurator = plugin.getConfigurator();
        }

        @Override
        public @NotNull List<ConfigurationException> loadConfigurationData() {
            List<ConfigurationException> errorsFound = new ArrayList<>();
            for (File file : plugin.getFiles(filesDirectory)) {
                final ConfigSection section;
                try {
                    section = YamlConfig.loadSection(file, configurator, prefix);
                }
                catch (ConfigurationException e) {
                    errorsFound.add(e);
                    continue;
                }

                for (String key : section.getKeys()) {
                    try {
                        loadFromSectionKey(section, key);
                    }
                    catch (ConfigurationException e) {
                        errorsFound.add(e);
                    }
                }
            }

            return errorsFound;
        }

        @Override
        public void loadFromFile(File file) throws InvalidConfigurationException {
            throw new UnsupportedOperationException("loadFromFile is not supported in ConfigBackedManager.SectionKey");
        }

        public abstract void loadFromSectionKey(ConfigSection section, String key) throws InvalidConfigurationException;

    }

}
