package io.github.pigaut.voxel.util;

import java.util.*;

public class Probability {

    private static final Random random = new Random();

    public static boolean test(double chance) {
        if (chance < 0 || chance > 1) {
            throw new IllegalArgumentException("Probability must be between 0 and 1 (exclusive)");
        }

        final double randomValue = random.nextDouble(0.0, 1.0);
        return randomValue <= chance;
    }

}
