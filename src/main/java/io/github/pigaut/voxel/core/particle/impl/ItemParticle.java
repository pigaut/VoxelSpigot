package io.github.pigaut.voxel.core.particle.impl;

import com.cryptomorin.xseries.particles.*;
import io.github.pigaut.voxel.util.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.amount.*;
import org.bukkit.*;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.*;

public class ItemParticle extends AbstractParticle {

    private final ItemStack item;

    public ItemParticle(String name, String group, Amount amount,
                        BlockRange range, boolean playerOnly, Material material) {
        super(name, group, XParticle.ITEM.get(), amount, range, playerOnly);
        item = new ItemStack(material);
    }

    @Override
    public int getParticleCount() {
        return 1;
    }

    @Override
    public double getExtra() {
        return 0;
    }

    @Override
    public @Nullable Object getData() {
        return item;
    }

}
