package io.github.pigaut.voxel.function.action.player;

import io.github.pigaut.voxel.player.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.loader.*;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class GiveItemToPlayer implements PlayerAction {

    private final ItemStack item;

    public GiveItemToPlayer(ItemStack item) {
        this.item = item;
    }

    @Override
    public void execute(@NotNull PluginPlayer player) {
        player.giveItemOrDrop(item);
    }

}
