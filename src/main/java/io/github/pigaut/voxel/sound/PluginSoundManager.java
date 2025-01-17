package io.github.pigaut.voxel.sound;

import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.yaml.node.section.*;

import java.io.*;

public class PluginSoundManager extends SoundManager {

    private final EnhancedPlugin plugin;

    public PluginSoundManager(EnhancedPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void disable() {
        clearSounds();
    }

    @Override
    public void load() {
        for (File itemFile : plugin.getFiles("effects/sounds")) {
            final RootSection config = new RootSection(itemFile, plugin.getConfigurator());
            config.load();
            for (String key : config.getKeys()) {
                final SoundEffect sound = config.get(key, SoundEffect.class);
                addSound(key, sound);
            }
        }
    }

}
