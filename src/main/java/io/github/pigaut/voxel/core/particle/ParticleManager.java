package io.github.pigaut.voxel.core.particle;

import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.voxel.plugin.manager.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.node.section.*;
import org.jetbrains.annotations.*;

import java.io.*;

public class ParticleManager extends ConfigBackedManager.SectionKey<ParticleEffect> {

    public ParticleManager(@NotNull EnhancedJavaPlugin plugin) {
        super(plugin, "Particle", "effects/particles");
    }

    @Override
    public void loadFromSectionKey(ConfigSection section, String key) throws InvalidConfigurationException {
        final ParticleEffect particle = section.getRequired(key, ParticleEffect.class);
        try {
            add(particle);
        }
        catch (DuplicateElementException e) {
            throw new InvalidConfigurationException(section, key, e.getMessage());
        }
    }

}
