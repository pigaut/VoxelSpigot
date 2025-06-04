package io.github.pigaut.voxel.core.function.action;


import io.github.pigaut.yaml.configurator.loader.*;

import java.util.*;
import java.util.function.*;

public class LoaderBuilder<T> {

    private final List<Argument<?>> arguments = new ArrayList<>();
    private final Function<List<Object>, T> constructor;

    public LoaderBuilder(Function<List<Object>, T> constructor) {
        this.constructor = constructor;
    }

    public <A> LoaderBuilder<T> arg(String key, int index, Class<A> type) {
        arguments.add(new Argument<>(key, index, type));
        return this;
    }

    public BranchLoader<T> build() {
        return branch -> {
            List<Object> args = new ArrayList<>();
            for (Argument<?> arg : arguments) {
                args.add(branch.get(arg.key, arg.index, arg.type));
            }
            return constructor.apply(args);
        };
    }

    private static class Argument<A> {
        final String key;
        final int index;
        final Class<A> type;

        Argument(String key, int index, Class<A> type) {
            this.key = key;
            this.index = index;
            this.type = type;
        }
    }
}
