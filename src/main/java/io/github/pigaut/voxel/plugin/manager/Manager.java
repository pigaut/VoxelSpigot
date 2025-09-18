package io.github.pigaut.voxel.plugin.manager;

import io.github.pigaut.voxel.plugin.*;
import java.util.logging.*;

public abstract class Manager {

    protected final EnhancedJavaPlugin plugin;
    protected final Logger logger;

    protected Manager(EnhancedJavaPlugin plugin) {
        this.plugin = plugin;
        this.logger = plugin.getLogger();
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
