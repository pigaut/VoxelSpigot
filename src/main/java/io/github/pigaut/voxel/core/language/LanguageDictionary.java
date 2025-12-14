package io.github.pigaut.voxel.core.language;

import io.github.pigaut.voxel.bukkit.*;
import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.voxel.plugin.manager.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.convert.format.*;
import io.github.pigaut.yaml.node.section.*;
import org.jetbrains.annotations.*;

import java.io.*;
import java.util.*;

public class LanguageDictionary implements ConfigBacked {

    private final EnhancedPlugin plugin;
    private final Map<String, String> dictionary = new HashMap<>();

    public LanguageDictionary(EnhancedPlugin plugin) {
        this.plugin = plugin;
    }

    @NotNull
    public String getTranslation(@NotNull String name) throws TranslationNotFoundException {
        final String lang = dictionary.get(name);
        if (lang == null) {
            throw new TranslationNotFoundException(name);
        }
        return lang;
    }

    @NotNull
    public String getTranslationOrDefault(@NotNull String name, @NotNull String def) {
        return dictionary.getOrDefault(name, def);
    }

    public void addTranslation(@NotNull String name, @NotNull String message) {
        dictionary.put(name, message);
    }

    public void removeTranslation(@NotNull String name) {
        dictionary.remove(name);
    }

    public void clearDictionary() {
        dictionary.clear();
    }

    @Override
    public @NotNull List<ConfigurationException> loadConfigurationData() {
        List<ConfigurationException> errors = new ArrayList<>();

        File file = plugin.getSettings().getLanguageFile();
        RootSection languageConfig = YamlConfig.createEmptySection(file);
        languageConfig.setPrefix("Language");
        languageConfig.load(errors::add);

        RootSection languageDefaults = YamlConfig.createEmptySection(null);
        try (InputStream inputStream = plugin.getClass().getClassLoader().getResourceAsStream("languages/" + languageConfig.getFile().getName())) {
            if (inputStream != null) {
                languageDefaults.load(inputStream);
            }
        }
        catch (IOException ignored) {
        }

        Set<String> translationKeys = languageConfig.getKeys();
        for (String key : languageDefaults.getKeys()) {
            if (!translationKeys.contains(key)) {
                String translation = languageDefaults.getRequiredString(key, StringColor.FORMATTER);
                addTranslation(CaseFormatter.toKebabCase(key), translation);
                errors.add(new InvalidConfigurationException(languageConfig, key, "Message not found (Fix or regenerate the language file)"));
            }
        }

        for (String key : languageConfig.getKeys()) {
            String translation = languageConfig.getRequiredString(key, StringColor.FORMATTER);
            addTranslation(CaseFormatter.toKebabCase(key), translation);
        }

        return errors;
    }

}
