package io.github.pigaut.voxel.core.function.condition.server;

import io.github.pigaut.voxel.util.*;

public class IsProbability implements ServerCondition {

    private final double chance;

    public IsProbability(double chance) {
        this.chance = chance;
    }

    @Override
    public boolean isMet() {
        return Probability.test(chance);
    }

}
