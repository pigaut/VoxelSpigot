package io.github.pigaut.voxel.core.particle.impl;

import io.github.pigaut.voxel.util.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.amount.*;
import org.bukkit.*;
import org.jetbrains.annotations.*;

public class DirectionalParticle extends AbstractParticle {

    private final Amount speed;

    public DirectionalParticle(String name, @Nullable String group, Particle particle, Amount amount,
                               BlockRange direction, boolean playerOnly, Amount speed) {
        super(name, group, particle, amount, direction, playerOnly);
        this.speed = speed;
    }

    @Override
    public int getParticleCount() {
        return 1;
    }

    @Override
    public double getExtra() {
        return speed.getDouble();
    }

    @Override
    public @Nullable Object getData() {
        return null;
    }

}
