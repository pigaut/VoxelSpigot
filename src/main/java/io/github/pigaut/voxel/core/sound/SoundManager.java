package io.github.pigaut.voxel.core.sound;

import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.voxel.plugin.manager.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.node.section.*;
import org.jetbrains.annotations.*;

import java.io.*;

public class SoundManager extends ConfigBackedManager.SectionKey<SoundEffect> {

    public SoundManager(@NotNull EnhancedJavaPlugin plugin) {
        super(plugin, "effects/sounds");
    }

    @Override
    public void loadFromSectionKey(ConfigSection section, String key) throws InvalidConfigurationException {
        final SoundEffect sound = section.getRequired(key, SoundEffect.class);
        try {
            add(sound);
        }
        catch (DuplicateElementException e) {
            throw new InvalidConfigurationException(section, key, e.getMessage());
        }
    }

}
