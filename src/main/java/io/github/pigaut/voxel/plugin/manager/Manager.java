package io.github.pigaut.voxel.plugin.manager;

import io.github.pigaut.voxel.command.*;
import org.bukkit.event.*;

import java.util.*;

public abstract class Manager {

    public void enable() {
        load();
    }

    public void disable() {
        save();
    }

    public void reload() {
        disable();
        load();
    }

    public void load() { }

    public void save() { }

    public int getAutoSave() { return -1; }

    public List<Listener> getListeners() {
        return List.of();
    }

    public List<EnhancedCommand> getCommands() {
        return List.of();
    }

}
