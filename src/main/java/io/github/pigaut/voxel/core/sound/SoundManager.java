package io.github.pigaut.voxel.core.sound;

import io.github.pigaut.voxel.plugin.boot.*;
import io.github.pigaut.voxel.plugin.manager.*;
import io.github.pigaut.yaml.*;
import org.jetbrains.annotations.*;

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
