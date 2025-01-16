package io.github.pigaut.voxel.sound;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.jetbrains.annotations.*;

@FunctionalInterface
public interface SoundEffect {

    void play(@Nullable Player player, @NotNull Location location);

}
