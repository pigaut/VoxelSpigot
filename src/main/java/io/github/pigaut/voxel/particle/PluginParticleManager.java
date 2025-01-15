package io.github.pigaut.voxel.particle;

import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.yaml.node.section.*;

import java.io.*;

public class PluginParticleManager extends ParticleManager {

    private final EnhancedPlugin plugin;

    public PluginParticleManager(EnhancedPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void disable() {
        clearParticles();
    }

    @Override
    public void load() {
        for (File itemFile : plugin.getFiles("particles")) {
            final RootSection config = new RootSection(itemFile, plugin.getConfigurator());
            config.load();
            for (String key : config.getKeys()) {
                final ParticleEffect particle = config.get(key, ParticleEffect.class);
                addParticleEffect(key, particle);
            }
        }
    }

}
