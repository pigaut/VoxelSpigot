package io.github.pigaut.voxel.meta.flag;

import io.github.pigaut.voxel.plugin.manager.*;

import java.util.*;

public class FlagManager extends Manager {

    private final Map<String, Flag> flagsByName = new HashMap<>();

    public Flag getFlag(String name) {
        return flagsByName.get(name);
    }

    public void addFlag(Flag flag) {
        flagsByName.put(flag.name(), flag);
    }

    public void clearFlags() {
        flagsByName.clear();
    }

}
