package io.github.pigaut.voxel.plugin.manager;

import io.github.pigaut.voxel.config.*;
import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.convert.format.*;
import org.jetbrains.annotations.*;

import java.io.*;
import java.util.*;
import java.util.function.*;

public abstract class ConfigBackedManager<T extends Identifiable> extends ContainerBackedManager<T> implements ConfigBacked {

    protected final String filesDirectory;
    private final String prefix;

    public ConfigBackedManager(@NotNull EnhancedJavaPlugin plugin, String filesDirectory) {
        super(plugin);
        this.filesDirectory = filesDirectory;
        this.prefix = CaseFormatter.toTitleCase(getClass()).replace(" Manager", "");
    }

    public String getPrefix() {
        return prefix;
    }

    @Override
    public void reload() {
        reload(errorsFound -> ConfigErrorLogger.logAll(plugin, errorsFound));
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
    public void disable() {
        this.clear();
    }

    public abstract static class Section<T extends Identifiable> extends ConfigBackedManager<T> {

        public Section(@NotNull EnhancedJavaPlugin plugin, String filesDirectory) {
            super(plugin, filesDirectory);
        }

        @Override
        public void loadFromFile(File file) throws InvalidConfigurationException {
            loadFromSection(YamlConfig.loadSection(file, plugin.getConfigurator(), getPrefix()));
        }

        public abstract void loadFromSection(ConfigSection section) throws InvalidConfigurationException;

    }

    public abstract static class Sequence<T extends Identifiable> extends ConfigBackedManager<T> {

        public Sequence(@NotNull EnhancedJavaPlugin plugin, String filesDirectory) {
            super(plugin, filesDirectory);
        }

        @Override
        public void loadFromFile(File file) throws InvalidConfigurationException {
            loadFromSequence(YamlConfig.loadSequence(file, plugin.getConfigurator(), getPrefix()));
        }

        public abstract void loadFromSequence(ConfigSequence sequence) throws InvalidConfigurationException;

    }

    public abstract static class Scalar<T extends Identifiable> extends ConfigBackedManager<T> {

        public Scalar(@NotNull EnhancedJavaPlugin plugin, String filesDirectory) {
            super(plugin, filesDirectory);
        }

        @Override
        public void loadFromFile(File file) throws InvalidConfigurationException {
            loadFromScalar(YamlConfig.loadScalar(file, plugin.getConfigurator(), getPrefix()));
        }

        public abstract void loadFromScalar(ConfigScalar scalar) throws InvalidConfigurationException;

    }

    public abstract static class SectionKey<T extends Identifiable> extends ConfigBackedManager<T> {

        public SectionKey(@NotNull EnhancedJavaPlugin plugin, String filesDirectory) {
            super(plugin, filesDirectory);
        }

        @Override
        public @NotNull List<ConfigurationException> loadConfigurationData() {
            List<ConfigurationException> errorsFound = new ArrayList<>();
            for (File file : plugin.getFiles(filesDirectory)) {
                final ConfigSection section;
                try {
                    section = YamlConfig.loadSection(file, plugin.getConfigurator(), getPrefix());
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
