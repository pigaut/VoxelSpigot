package io.github.pigaut.voxel.function.condition.player;

import io.github.pigaut.voxel.player.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.loader.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class PlayerHasPermission implements PlayerCondition {

    private final List<String> permissions;

    public PlayerHasPermission(String permission) {
        this.permissions = List.of(permission);
    }

    public PlayerHasPermission(List<String> permissions) {
        this.permissions = permissions;
    }

    @Override
    public boolean isMet(@NotNull PluginPlayer player) {
        for (String permission : permissions) {
            if (!player.hasPermission(permission)) {
                return false;
            }
        }
        return true;
    }

}
