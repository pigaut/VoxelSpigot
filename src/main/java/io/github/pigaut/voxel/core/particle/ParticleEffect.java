package io.github.pigaut.voxel.core.particle;

import io.github.pigaut.voxel.plugin.manager.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.jetbrains.annotations.*;

public interface ParticleEffect extends Identifiable {

    void spawn(@Nullable Player player, @NotNull Location location);

}
