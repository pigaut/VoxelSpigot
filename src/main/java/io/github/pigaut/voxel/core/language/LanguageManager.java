package io.github.pigaut.voxel.core.language;

import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.voxel.plugin.manager.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class LanguageManager extends Manager {

    private Locale defaultLanguage = Locale.ENGLISH;
    private final Map<String, String> dictionary = new HashMap<>();

    public LanguageManager(EnhancedJavaPlugin plugin) {
        super(plugin);
    }

    @NotNull
    public Locale getDefaultLanguage() {
        return defaultLanguage;
    }

    public void setDefaultLanguage(@NotNull Locale defaultLanguage) {
        this.defaultLanguage = defaultLanguage;
    }

    @NotNull
    public String getLang(String name) throws LangNotFoundException {
        final String lang = dictionary.get(name);
        if (lang == null) {
            throw new LangNotFoundException(defaultLanguage, name);
        }
        return lang;
    }

    @NotNull
    public String getLang(String name, String def) {
        return dictionary.getOrDefault(name, def);
    }

    public void addLang(String name, String message) {
        dictionary.put(name, message);
    }

    public void clearDictionary() {
        dictionary.clear();
    }

}
