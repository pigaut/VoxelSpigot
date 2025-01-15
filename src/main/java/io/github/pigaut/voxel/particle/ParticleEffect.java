package io.github.pigaut.voxel.particle;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.jetbrains.annotations.*;

public interface ParticleEffect {

    void spawn(@Nullable Player player, @NotNull Location location);

}
