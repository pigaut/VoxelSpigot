package io.github.pigaut.voxel.plugin.manager;

import io.github.pigaut.voxel.plugin.boot.*;
import io.github.pigaut.voxel.plugin.boot.phase.*;
import io.github.pigaut.voxel.util.*;

import java.util.*;

public abstract class Manager {

    protected final EnhancedJavaPlugin plugin;
    protected final ColoredLogger logger;

    protected Manager(EnhancedJavaPlugin plugin) {
        this.plugin = plugin;
        this.logger = plugin.getColoredLogger();
    }

    public boolean isAutoSave() {
        return false;
    }

    public void enable() {

    }

    public void disable() {

    }

    public void loadData() {

    }

    public void saveData() {

    }

    public void reload() {
        disable();
        enable();
        plugin.getScheduler().runTaskAsync(() -> {
            saveData();
            loadData();
        });
    }

}
