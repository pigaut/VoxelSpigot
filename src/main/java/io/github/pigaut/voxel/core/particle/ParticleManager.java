package io.github.pigaut.voxel.core.particle;

import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.voxel.plugin.manager.*;
import io.github.pigaut.yaml.node.section.*;
import org.jetbrains.annotations.*;

import java.io.*;

public class ParticleManager extends ManagerContainer<ParticleEffect> {

    public ParticleManager(@NotNull EnhancedJavaPlugin plugin) {
        super(plugin);
    }

    @Override
    public @Nullable String getFilesDirectory() {
        return "effects/particles";
    }

    @Override
    public void loadFile(@NotNull File file) {
        final RootSection config = new RootSection(file, plugin.getConfigurator());
        config.setPrefix("Particle");
        config.load();
        for (String particleName : config.getKeys()) {
            final ParticleEffect particle = config.get(particleName, ParticleEffect.class);
            this.add(particle);
        }
    }

}
