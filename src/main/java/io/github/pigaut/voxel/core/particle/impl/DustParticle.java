package io.github.pigaut.voxel.core.particle.impl;

import com.cryptomorin.xseries.particles.*;
import io.github.pigaut.voxel.util.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.amount.*;
import org.bukkit.*;
import org.jetbrains.annotations.*;

public class DustParticle extends AbstractParticle {

    private final Amount red;
    private final Amount green;
    private final Amount blue;
    private final Amount size;
    private final boolean uniform;
    private Particle.DustOptions uniformDust = null;

    public DustParticle(String name, @Nullable String group,
                        Amount amount, BlockRange range, boolean playerOnly,
                        Amount red, Amount green, Amount blue, Amount size, boolean uniform) {
        super(name, group, XParticle.DUST.get(), amount, range, playerOnly);
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.size = size;
        this.uniform = uniform;
        if (uniform) {
            uniformDust = new Particle.DustOptions(Color.fromRGB(red.getInteger(), green.getInteger(), blue.getInteger()), (float) size.getDouble());
        }
    }

    @Override
    public int getParticleCount() {
        return 1;
    }

    @Override
    public double getExtra() {
        return 0;
    }

    @Override
    public @Nullable Object getData() {
        if (uniform) {
            return uniformDust;
        }
        return new Particle.DustOptions(Color.fromRGB(red.getInteger(), green.getInteger(), blue.getInteger()), (float) size.getDouble());
    }

}
