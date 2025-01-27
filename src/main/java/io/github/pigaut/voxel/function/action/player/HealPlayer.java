package io.github.pigaut.voxel.function.action.player;

import io.github.pigaut.voxel.player.*;
import io.github.pigaut.voxel.util.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.loader.*;
import org.jetbrains.annotations.*;

public class HealPlayer implements PlayerAction {

    private final Amount amount;

    public HealPlayer(Amount amount) {
        this.amount = amount;
    }

    @Override
    public void execute(@NotNull PluginPlayer player) {
        player.heal(amount.getInt());
    }

}
