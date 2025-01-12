package io.github.pigaut.voxel.hook;

import me.clip.placeholderapi.*;
import org.bukkit.*;
import org.jetbrains.annotations.*;

public class PlaceholderAPIHook {

    public String setPlaceholders(@Nullable OfflinePlayer offlinePlayer, @NotNull String value) {
        return PlaceholderAPI.setPlaceholders(offlinePlayer, value);
    }

}
