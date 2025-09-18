package io.github.pigaut.voxel.core.particle.impl;

import com.cryptomorin.xseries.particles.*;
import io.github.pigaut.voxel.util.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.amount.*;
import org.jetbrains.annotations.*;

public class NoteParticle extends AbstractParticle {

    public NoteParticle(String name, @Nullable String group,
                        Amount amount, boolean playerOnly, Amount note) {
        super(name, group, XParticle.NOTE.get(), amount, new BlockRange(note, Amount.ZERO, Amount.ZERO), playerOnly);
    }

    @Override
    public int getParticleCount() {
        return 0;
    }

    @Override
    public double getExtra() {
        return 1.0;
    }

    @Override
    public @Nullable Object getData() {
        return null;
    }

}
