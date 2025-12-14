package io.github.pigaut.voxel.plugin.task;

import org.jetbrains.annotations.*;

public interface RegionScheduler {

    void runTask(@NotNull Runnable runnable);

    @Nullable
    Task runTaskLater(long delay, @NotNull Runnable runnable);

    @NotNull
    Task runTaskTimer(long period, @NotNull Runnable runnable);

    @NotNull
    Task runTaskTimer(long delay, long period, @NotNull Runnable runnable);

}
