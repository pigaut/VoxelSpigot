package io.github.pigaut.voxel.function.action.server;

import io.github.pigaut.voxel.function.action.*;
import io.github.pigaut.voxel.player.*;
import io.github.pigaut.voxel.sound.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.loader.*;
import org.bukkit.*;
import org.bukkit.block.*;
import org.jetbrains.annotations.*;

public class PlaySound implements Action {

    private final SoundEffect sound;
    private final Location location;

    public PlaySound(SoundEffect sound, Location location) {
        this.sound = sound;
        this.location = location;
    }

    @Override
    public void execute(@Nullable PluginPlayer player, @Nullable Block block) {
        sound.play(player != null ? player.asPlayer() : null, location);
    }

    public static ConfigLoader<PlaySound> newConfigLoader() {
        return new BranchLoader<>() {
            @Override
            public @NotNull PlaySound loadFromBranch(ConfigBranch branch) throws InvalidConfigurationException {
                final SoundEffect sound = branch.getField("sound|value", 1).load(SoundEffect.class);
                final Location location = branch.getField("location", 2).load(Location.class);
                return new PlaySound(sound, location);
            }
        };
    }

}
