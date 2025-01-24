package io.github.pigaut.voxel.language;

import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.voxel.plugin.manager.*;
import io.github.pigaut.voxel.util.collection.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class LanguageManager extends Manager {

    private Locale defaultLanguage = Locale.ENGLISH;
    protected final Table<Locale, String, String> dictionary = new Table();

    public LanguageManager(EnhancedPlugin plugin) {
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
        final String lang = dictionary.get(defaultLanguage, name);
        if (lang == null) {
            throw new LangNotFoundException(defaultLanguage, name);
        }
        return lang;
    }

    @NotNull
    public String getLang(String name, String def) {
        final String lang = dictionary.get(defaultLanguage, name);
        return lang != null ? lang : def;
    }

    @NotNull
    public String getLang(Locale language, String name) {
        final String lang = dictionary.get(defaultLanguage, name);
        return lang != null ? lang : "No message found for language: " + language + " (" + name + ")";
    }

    @NotNull
    public String getLang(Locale language, String name, String def) {
        final String lang = dictionary.get(language, name);
        return lang != null ? lang : def;
    }

    public void addLang(Locale language, String name, String message) {
        dictionary.put(language, name, message);
    }

    public void clearDictionary() {
        dictionary.clear();
    }

}
