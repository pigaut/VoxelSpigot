package io.github.pigaut.voxel.plugin.manager;

import io.github.pigaut.voxel.command.*;
import org.bukkit.event.*;

import java.util.*;

public abstract class Manager {

    public void onEnable() {
        load();
    }

    public void onDisable() {
        save();
    }

    public void onReload() {
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
