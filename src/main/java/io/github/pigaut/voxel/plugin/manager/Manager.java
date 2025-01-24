package io.github.pigaut.voxel.plugin.manager;

import io.github.pigaut.voxel.command.*;
import io.github.pigaut.voxel.plugin.*;
import org.bukkit.event.*;

import java.util.*;

public abstract class Manager {

    protected final EnhancedPlugin plugin;

    protected Manager(EnhancedPlugin plugin) {
        this.plugin = plugin;
    }

    public void enable() {}

    public void disable() {}

    public void reload() {
        disable();
        enable();
        plugin.getScheduler().runTaskAsync(() -> {
            saveData();
            loadData();
        });
    }

    public void loadData() { }

    public void saveData() { }

    public int getAutoSave() { return -1; }

    public List<Listener> getListeners() {
        return List.of();
    }

    public List<EnhancedCommand> getCommands() {
        return List.of();
    }

}
