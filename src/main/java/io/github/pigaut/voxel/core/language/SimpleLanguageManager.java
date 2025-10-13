package io.github.pigaut.voxel.core.language;

import io.github.pigaut.voxel.bukkit.*;
import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.convert.format.*;
import io.github.pigaut.yaml.node.section.*;

import java.io.*;
import java.util.*;

public class SimpleLanguageManager extends LanguageManager {

    public SimpleLanguageManager(EnhancedJavaPlugin plugin) {
        super(plugin);
    }

    @Override
    public void loadData() {
        final ConfigSection config = plugin.getConfiguration();
        final Locale defaultLanguage = config.get("language", Locale.class).withDefault(Locale.ENGLISH);
        setDefaultLanguage(defaultLanguage);

        final String languageFilePath = "languages/" + defaultLanguage.getLanguage() + ".yml";
        final File file = plugin.getFile(languageFilePath);

        RootSection languageConfig;
        try {
            languageConfig = YamlConfig.loadSection(file, "Language");
        }
        catch (ConfigurationLoadException e) {
            logger.severe(e.getLogMessage(plugin.getDataFolder().getPath()));
            languageConfig = YamlConfig.createEmptySection(file);
        }

        final RootSection languageDefaults = YamlConfig.createEmptySection(null);
        try (InputStream inputStream = plugin.getClass().getClassLoader().getResourceAsStream("languages/" + languageConfig.getFile().getName())) {
            if (inputStream != null) {
                languageDefaults.load(inputStream);
            }
        }
        catch (IOException ignored) {}

        final Set<String> languageIds = languageConfig.getKeys();
        for (String langId : languageDefaults.getKeys()) {
            if (!languageIds.contains(langId)) {
                final String value = languageDefaults.getRequiredString(langId, StringColor.FORMATTER);
                addLang(CaseFormatter.toKebabCase(langId), value);
                plugin.getLogger().warning("Message with id " + langId + " not found in " + languageFilePath + ". Please fix your language file.");
            }
        }

        for (String key : languageConfig.getKeys()) {
            final String value = languageConfig.getRequiredString(key, StringColor.FORMATTER);
            addLang(CaseFormatter.toKebabCase(key), value);
        }


    }

}
