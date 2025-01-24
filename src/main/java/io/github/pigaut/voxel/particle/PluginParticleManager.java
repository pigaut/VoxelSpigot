package io.github.pigaut.voxel.particle;

import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.node.section.*;

import java.io.*;

public class PluginParticleManager extends ParticleManager {

    public PluginParticleManager(EnhancedPlugin plugin) {
        super(plugin);
    }

    @Override
    public void loadData() {
        clearParticles();
        for (File itemFile : plugin.getFiles("effects/particles")) {
            final ConfigSection config = ConfigSection.loadConfiguration(itemFile, plugin.getConfigurator());
            for (String key : config.getKeys()) {
                final ParticleEffect particle = config.get(key, ParticleEffect.class);
                addParticle(key, particle);
            }
        }
    }

}
