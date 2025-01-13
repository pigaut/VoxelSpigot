package io.github.pigaut.voxel.function.action.player;

import io.github.pigaut.voxel.player.*;
import org.jetbrains.annotations.*;

public class SetPlayerFlight implements PlayerAction {

    private final boolean flight;

    public SetPlayerFlight(boolean flight) {
        this.flight = flight;
    }

    @Override
    public void execute(@NotNull PluginPlayer player) {
        player.asPlayer().setAllowFlight(flight);
    }

}
