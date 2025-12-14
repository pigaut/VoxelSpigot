package io.github.pigaut.voxel.plugin.task;

import org.bukkit.plugin.*;

public interface Task {

    void cancel();

    boolean isCancelled();

    Plugin getOwningPlugin();

}
