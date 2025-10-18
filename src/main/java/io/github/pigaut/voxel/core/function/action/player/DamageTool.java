package io.github.pigaut.voxel.core.function.action.player;

import io.github.pigaut.voxel.bukkit.*;
import io.github.pigaut.voxel.player.*;
import io.github.pigaut.yaml.amount.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.*;

public class DamageTool implements PlayerAction {

    private final Amount amount;

    public DamageTool(Amount amount) {
        this.amount = amount;
    }

    @Override
    public void execute(@NotNull PlayerState playerState) {
        Player player = playerState.asPlayer();
        ItemStack tool = player.getInventory().getItemInMainHand();
        ItemUtil.damageItem(tool, player, amount.getInteger());
    }

}
