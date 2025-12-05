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

    private Locale defaultLanguage = Locale.ENGLISH;
    private final Map<String, String> dictionary = new HashMap<>();

    public LanguageDictionary(EnhancedPlugin plugin) {
        this.plugin = plugin;
    }

    @NotNull
    public Locale getDefaultLanguage() {
        return defaultLanguage;
    }

    public void setDefaultLanguage(@NotNull Locale defaultLanguage) {
        this.defaultLanguage = defaultLanguage;
    }

    @NotNull
    public String getTranslation(@NotNull String name) throws TranslationNotFoundException {
        final String lang = dictionary.get(name);
        if (lang == null) {
            throw new TranslationNotFoundException(defaultLanguage, name);
        }
        return lang;
    }

    @NotNull
    public String getTranslationOrDefault(@NotNull String name, @NotNull String def) {
        return dictionary.getOrDefault(name, def);
    }

    public void addMessage(@NotNull String name, @NotNull String message) {
        dictionary.put(name, message);
    }

    public void removeMessage(@NotNull String name) {
        dictionary.remove(name);
    }

    public void clearDictionary() {
        dictionary.clear();
    }

    @Override
    public @NotNull List<ConfigurationException> loadConfigurationData() {
        List<ConfigurationException> errors = new ArrayList<>();

        Locale languageLocale = plugin.getSettings().getLanguage();
        setDefaultLanguage(languageLocale);

        String languageFilePath = "languages/" + languageLocale.getLanguage() + ".yml";
        File file = plugin.getFile(languageFilePath);

        RootSection languageConfig;
        try {
            languageConfig = YamlConfig.loadSection(file, "Language");
        }
        catch (ConfigurationLoadException e) {
            languageConfig = YamlConfig.createEmptySection(file);
            errors.add(e);
        }

        RootSection languageDefaults = YamlConfig.createEmptySection(null);
        try (InputStream inputStream = plugin.getClass().getClassLoader().getResourceAsStream("languages/" + languageConfig.getFile().getName())) {
            if (inputStream != null) {
                languageDefaults.load(inputStream);
            }
        }
        catch (IOException ignored) {}

        Set<String> languageIds = languageConfig.getKeys();
        for (String langId : languageDefaults.getKeys()) {
            if (!languageIds.contains(langId)) {
                String value = languageDefaults.getRequiredString(langId, StringColor.FORMATTER);
                addMessage(CaseFormatter.toKebabCase(langId), value);
                errors.add(new InvalidConfigurationException(languageConfig, langId, "Message not found (Fix or regenerate the language file)"));
            }
        }

        for (String key : languageConfig.getKeys()) {
            String value = languageConfig.getRequiredString(key, StringColor.FORMATTER);
            addMessage(CaseFormatter.toKebabCase(key), value);
        }

        return errors;
    }

}
