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

    private final EnhancedPlugin plugin;

    public PluginLanguageManager(EnhancedPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void disable() {
        clearDictionary();
    }

    @Override
    public void load() {
        for (File langFile : plugin.getFiles("languages")) {
            final RootSection config = new RootSection(langFile);
            config.load();

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
