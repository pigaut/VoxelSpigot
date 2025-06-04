package io.github.pigaut.voxel.core.sound;

import io.github.pigaut.voxel.plugin.manager.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.jetbrains.annotations.*;

public interface SoundEffect extends Identifiable {

    void play(@Nullable Player player, @NotNull Location location);

}
