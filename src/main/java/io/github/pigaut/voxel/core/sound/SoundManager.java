package io.github.pigaut.voxel.core.sound;

import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.voxel.plugin.manager.*;
import io.github.pigaut.yaml.node.section.*;
import org.jetbrains.annotations.*;

import java.io.*;

public class SoundManager extends ManagerContainer<SoundEffect> {

    public SoundManager(@NotNull EnhancedJavaPlugin plugin) {
        super(plugin);
    }

    @Override
    public @Nullable String getFilesDirectory() {
        return "effects/sounds";
    }

    @Override
    public void loadFile(@NotNull File file) {
        final RootSection config = new RootSection(file, plugin.getConfigurator());
        config.setPrefix("Sound");
        config.load();

        for (String soundName : config.getKeys()) {
            final SoundEffect sound = config.get(soundName, SoundEffect.class);
            this.add(sound);
        }
    }

}
