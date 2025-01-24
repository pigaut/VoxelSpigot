package io.github.pigaut.voxel.sound;

import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.node.section.*;

import java.io.*;

public class PluginSoundManager extends SoundManager {

    public PluginSoundManager(EnhancedPlugin plugin) {
        super(plugin);
    }

    @Override
    public void loadData() {
        clearSounds();
        for (File itemFile : plugin.getFiles("effects/sounds")) {
            final ConfigSection config = ConfigSection.loadConfiguration(itemFile, plugin.getConfigurator());
            for (String key : config.getKeys()) {
                final SoundEffect sound = config.get(key, SoundEffect.class);
                addSound(key, sound);
            }
        }
    }

}
