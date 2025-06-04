package io.github.pigaut.voxel.core.sound.impl;

import io.github.pigaut.voxel.*;
import io.github.pigaut.voxel.core.sound.*;
import io.github.pigaut.voxel.server.*;
import io.github.pigaut.yaml.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.jetbrains.annotations.*;

public class SimpleSoundEffect implements SoundEffect {

    private final String name;
    private final String group;
    private final ConfigSection section;
    private final Sound sound;
    private final float volume;
    private final float pitch;
    private final double offsetX, offsetY, offsetZ;
    private final boolean playerOnly;

    public SimpleSoundEffect(String name, @Nullable String group, ConfigSection section, Sound sound, float volume, float pitch, double offsetX, double offsetY, double offsetZ, boolean playerOnly) {
        this.name = name;
        this.group = group;
        this.section = section;
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

    @Override
    public @NotNull ConfigSection getField() {
        return section;
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
