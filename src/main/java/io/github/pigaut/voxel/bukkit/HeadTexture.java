package io.github.pigaut.voxel.bukkit;

import com.cryptomorin.xseries.profiles.builder.*;
import com.cryptomorin.xseries.profiles.objects.*;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.*;

public class HeadTexture {

    public static void apply(@NotNull ItemStack item, @NotNull String texture) {
        XSkull.of(item)
                .profile(Profileable.of(ProfileInputType.BASE64, texture))
                .apply();
    }

}
