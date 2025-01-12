package io.github.pigaut.voxel.function.action.player;

import io.github.pigaut.voxel.player.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.loader.*;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.*;

public class ClearPlayerInventory implements PlayerAction {

    private final boolean removeArmor;

    public ClearPlayerInventory(boolean removeArmor) {
        this.removeArmor = removeArmor;
    }

    @Override
    public void execute(@NotNull PluginPlayer player) {
        final PlayerInventory inventory = player.getInventory();
        if (!removeArmor) {
            for (int i = 0; i < 36; i++) {
                inventory.setItem(i, null);
            }
        }
        else {
            inventory.clear();
        }
        player.updateInventory();
    }

    public static ConfigLoader<ClearPlayerInventory> newConfigLoader() {
        return new ConfigLoader<ClearPlayerInventory>() {
            @Override
            public @NotNull String getProblemDescription() {
                return "Could not load action 'CLEAR_INVENTORY'";
            }

            @Override
            public String getKey() {
                return "CLEAR_INVENTORY";
            }

            @Override
            public @NotNull ClearPlayerInventory loadFromScalar(ConfigScalar scalar) throws InvalidConfigurationException {
                return new ClearPlayerInventory(scalar.toBoolean());
            }

        };
    }

}


