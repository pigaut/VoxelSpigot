package io.github.pigaut.voxel.core.function.condition.player;

import io.github.pigaut.voxel.player.*;
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
    public boolean isMet(@NotNull PlayerState player) {
        for (String permission : permissions) {
            if (!player.hasPermission(permission)) {
                return false;
            }
        }
        return true;
    }

}
