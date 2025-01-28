package io.github.pigaut.voxel.language;

import io.github.pigaut.voxel.plugin.EnhancedPlugin;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.formatter.*;
import io.github.pigaut.yaml.node.*;
import io.github.pigaut.yaml.node.section.*;
import io.github.pigaut.yaml.parser.*;

import java.io.*;
import java.util.*;

public class PluginLanguageManager extends LanguageManager {

    public PluginLanguageManager(EnhancedPlugin plugin) {
        super(plugin);
    }

    @Override
    public void loadData() {
        final ConfigSection config = plugin.getConfiguration();
        final Locale defaultLanguage = config.get("language", Locale.class);
        setDefaultLanguage(defaultLanguage);

        final String languageFilePath = "languages/" + defaultLanguage.getLanguage() + ".yml";
        final File languageFile = plugin.getFile(languageFilePath);
        if (!languageFile.exists()) {
            throw new InvalidConfigurationException(config, "language", "Could not find language file at path: " + languageFilePath);
        }

        final RootSection languageConfig = plugin.loadConfigSection(languageFile);
        final RootSection languageDefaults = new RootSection();
        try (InputStream inputStream = plugin.getClass().getClassLoader().getResourceAsStream("languages/" + languageConfig.getFile().getName())) {
            if (inputStream != null) {
                languageDefaults.load(inputStream);
            }
        } catch (IOException ignored) {}

        final Set<String> languageIds = languageConfig.getKeys();
        for (String langId : languageDefaults.getKeys()) {
            if (!languageIds.contains(langId)) {
                final String value = languageDefaults.getString(langId, StringColor.FORMATTER);
                addLang(StringFormatter.toKebabCase(langId), value);
                plugin.getLogger().warning("Message with id " + langId + " not found in " + languageFilePath + ". Please fix your language file.");
            }
        }

        for (String key : languageConfig.getKeys()) {
            final String value = languageConfig.getString(key, StringColor.FORMATTER);
            addLang(StringFormatter.toKebabCase(key), value);
        }
    }

}
