package io.github.pigaut.voxel.core.sound.impl;

import io.github.pigaut.voxel.*;
import io.github.pigaut.voxel.core.sound.*;
import io.github.pigaut.voxel.server.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.jetbrains.annotations.*;

public class SimpleSound implements SoundEffect {

    private final String name;
    private final String group;
    private final Sound sound;
    private final float volume;
    private final float pitch;
    private final double offsetX, offsetY, offsetZ;
    private final boolean playerOnly;

    public SimpleSound(String name, @Nullable String group, Sound sound, float volume, float pitch, double offsetX, double offsetY, double offsetZ, boolean playerOnly) {
        this.name = name;
        this.group = group;
        this.sound = sound;
        this.volume = volume;
        this.pitch = pitch;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.offsetZ = offsetZ;
        this.playerOnly = playerOnly;
    }

    @Override
    public @NotNull String getName() {
        return name;
    }

    @Override
    public @Nullable String getGroup() {
        return group;
    }

    public void play(@Nullable Player player, @NotNull Location location) {
        final Location offsetLocation = SpigotLibs.getOffsetLocation(location, offsetX, offsetY, offsetZ);

        if (playerOnly) {
            if (player != null) {
                player.playSound(offsetLocation, sound, volume, pitch);
            }
            return;
        }

        World world = location.getWorld();
        if (world == null) {
            world = SpigotServer.getDefaultWorld();
        }
        world.playSound(offsetLocation, sound, volume, pitch);
    }

}
