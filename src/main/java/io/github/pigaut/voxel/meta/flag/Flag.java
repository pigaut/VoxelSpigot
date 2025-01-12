package io.github.pigaut.voxel.meta.flag;

import io.github.pigaut.voxel.plugin.*;

public record Flag(String group, String name, int weight) {

    private static EnhancedPlugin plugin;

    public Flag(String name) {
        this("default", name, 0);
    }

}
