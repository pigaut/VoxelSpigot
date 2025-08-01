package io.github.pigaut.voxel.util;

import io.github.pigaut.yaml.parser.deserializer.*;
import org.jetbrains.annotations.*;

import java.util.concurrent.*;

public interface Amount {

    int getInt();

    double getDouble();

    boolean match(double amount);

    static @Nullable Amount fromString(String string) {
        final Double amount = Deserializers.getDouble(string);
        if (amount != null) {
            return new Amount.Single(amount);
        }
        final String[] range = string.split("-");
        if (range.length != 2) {
            return null;
        }
        final Double min = Deserializers.getDouble(range[0]);
        final Double max = Deserializers.getDouble(range[1]);
        if (min == null || max == null || min >= max) {
            return null;
        }
        return new Amount.Range(min, max);
    }

    static Amount of(double amount) {
        return new Single(amount);
    }

    static Amount ofRange(double min, double max) {
        if (min >= max) {
            throw new IllegalArgumentException("Min cannot be greater than max");
        }
        return new Range(min, max);
    }

    Amount ANY = new Amount() {
        @Override
        public int getInt() {
            return 0;
        }

        @Override
        public double getDouble() {
            return 0;
        }

        @Override
        public boolean match(double amount) {
            return true;
        }
    };

    class Single implements Amount {

        private final double amount;

        public Single(double amount) {
            this.amount = amount;
        }

        @Override
        public int getInt() {
            return (int) amount;
        }

        @Override
        public double getDouble() {
            return amount;
        }

        @Override
        public boolean match(double amount) {
            return this.amount == amount;
        }

        @Override
        public String toString() {
            return Double.toString(amount);
        }
    }

    class Range implements Amount {

        private final double min, max;

        public Range(double min, double max) {
            this.min = min;
            this.max = max;
        }

        @Override
        public int getInt() {
            return (int) getDouble();
        }

        @Override
        public double getDouble() {
            return ThreadLocalRandom.current().nextDouble(min, max + 1);
        }

        @Override
        public boolean match(double amount) {
            return amount >= min && amount <= max;
        }

        @Override
        public String toString() {
            return min + "-" + max;
        }
    }

}
