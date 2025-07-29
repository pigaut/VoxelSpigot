package io.github.pigaut.voxel.language;

import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.voxel.util.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.node.*;
import io.github.pigaut.yaml.node.section.*;
import io.github.pigaut.yaml.parser.*;

import java.io.*;
import java.util.*;

public class SimpleLanguageManager extends LanguageManager {

    public SimpleLanguageManager(EnhancedJavaPlugin plugin) {
        super(plugin);
    }

    @Override
    public void loadData() {
        final ConfigSection config = plugin.getConfiguration();
        final Locale defaultLanguage = config.getOptional("language", Locale.class).orElse(Locale.ENGLISH);
        setDefaultLanguage(defaultLanguage);

        final String languageFilePath = "languages/" + defaultLanguage.getLanguage() + ".yml";

        final RootSection languageConfig = new RootSection(plugin.getFile(languageFilePath));
        languageConfig.setPrefix("Language");
        try {
            languageConfig.load();
        } catch (ConfigurationLoadException e) {
            logger.severe(e.getLogMessage());
        }

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
