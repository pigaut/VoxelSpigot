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
        setDefaultLanguage(plugin.getConfiguration().getOptional("language", Locale.class).orElse(Locale.ENGLISH));
        clearDictionary();
        for (File langFile : plugin.getFiles("languages")) {
            final RootSection config = ConfigSection.loadConfiguration(langFile);
            final String name = config.getName();
            final Locale locale = Locale.forLanguageTag(name);
            if (locale == null) {
                throw new ConfigurationLoadException(config, "'" + name + "' is not a valid language tag.");
            }
            for (String key : config.getKeys()) {
                final String value = config.getString(key, StringColor.FORMATTER);
                addLang(locale, StringFormatter.toKebabCase(key), value);
            }
        }
    }

}
