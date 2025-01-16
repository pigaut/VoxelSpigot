package io.github.pigaut.voxel.sound;

import io.github.pigaut.voxel.*;
import io.github.pigaut.voxel.server.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.jetbrains.annotations.*;

public class SimpleSoundEffect implements SoundEffect {

    private final Sound sound;
    private final float volume;
    private final float pitch;
    private final double offsetX, offsetY, offsetZ;
    private final boolean playerOnly;

    public SimpleSoundEffect(Sound sound, float volume, float pitch, double offsetX, double offsetY, double offsetZ, boolean playerOnly) {
        this.sound = sound;
        this.volume = volume;
        this.pitch = pitch;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.offsetZ = offsetZ;
        this.playerOnly = playerOnly;
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
