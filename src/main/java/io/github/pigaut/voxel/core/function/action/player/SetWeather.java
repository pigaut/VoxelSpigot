package io.github.pigaut.voxel.core.function.action.player;

import io.github.pigaut.voxel.bukkit.*;
import io.github.pigaut.voxel.player.*;
import org.bukkit.*;
import org.jetbrains.annotations.*;

public class SetWeather implements PlayerAction {

    private final Weather weather;
    private final int duration;

    public SetWeather(@NotNull Weather weather, int duration) {
        this.weather = weather;
        this.duration = duration;
    }

    @Override
    public void execute(@NotNull PlayerState player) {
        World world = player.getWorld();

        world.setWeatherDuration(duration);
        world.setThunderDuration(duration);

        switch (weather) {
            case CLEAR:
                world.setStorm(false);
                world.setThundering(false);
                break;
            case RAIN:
                world.setStorm(true);
                world.setThundering(false);
                break;
            case THUNDER:
                world.setStorm(false);
                world.setThundering(true);
                break;
            case STORM:
                world.setStorm(true);
                world.setThundering(true);
                break;
        }
    }

}
