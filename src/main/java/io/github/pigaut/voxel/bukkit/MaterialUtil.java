package io.github.pigaut.voxel.bukkit;

import io.github.pigaut.yaml.util.*;
import org.bukkit.*;
import org.jetbrains.annotations.*;

import java.util.*;

import static org.bukkit.Material.BAMBOO;
import static org.bukkit.Material.BEETROOTS;
import static org.bukkit.Material.BEETROOT_SEEDS;
import static org.bukkit.Material.CACTUS;
import static org.bukkit.Material.CARROT;
import static org.bukkit.Material.CARROTS;
import static org.bukkit.Material.COCOA;
import static org.bukkit.Material.COCOA_BEANS;
import static org.bukkit.Material.MELON_SEEDS;
import static org.bukkit.Material.MELON_STEM;
import static org.bukkit.Material.NETHER_WART;
import static org.bukkit.Material.POTATO;
import static org.bukkit.Material.POTATOES;
import static org.bukkit.Material.PUMPKIN_SEEDS;
import static org.bukkit.Material.PUMPKIN_STEM;
import static org.bukkit.Material.SUGAR_CANE;
import static org.bukkit.Material.SWEET_BERRIES;
import static org.bukkit.Material.SWEET_BERRY_BUSH;
import static org.bukkit.Material.WHEAT;
import static org.bukkit.Material.WHEAT_SEEDS;

public class MaterialUtil {

    public static boolean isAir(@NotNull Material material) {
        return material == Material.AIR ||
                material == Material.CAVE_AIR ||
                material == Material.VOID_AIR;
    }

    public static boolean isNotAir(@NotNull Material material) {
        return !isAir(material);
    }

    private static final List<Material> CROPS = List.of(WHEAT, CARROTS, POTATOES, BEETROOTS, NETHER_WART, COCOA,
            SWEET_BERRY_BUSH, PUMPKIN_STEM, MELON_STEM, SUGAR_CANE, CACTUS, BAMBOO);
    private static final Map<Material, Material> CROP_BY_SEEDS = new HashMap<>();

    static {
        CROP_BY_SEEDS.put(WHEAT, WHEAT_SEEDS);
        CROP_BY_SEEDS.put(CARROTS, CARROT);
        CROP_BY_SEEDS.put(POTATOES, POTATO);
        CROP_BY_SEEDS.put(BEETROOTS, BEETROOT_SEEDS);
        CROP_BY_SEEDS.put(NETHER_WART, NETHER_WART);
        CROP_BY_SEEDS.put(COCOA, COCOA_BEANS);
        CROP_BY_SEEDS.put(SWEET_BERRY_BUSH, SWEET_BERRIES);
        CROP_BY_SEEDS.put(PUMPKIN_STEM, PUMPKIN_SEEDS);
        CROP_BY_SEEDS.put(MELON_STEM, MELON_SEEDS);
        CROP_BY_SEEDS.put(SUGAR_CANE, SUGAR_CANE);
        CROP_BY_SEEDS.put(CACTUS, CACTUS);
        CROP_BY_SEEDS.put(BAMBOO, BAMBOO);
    }

    public static boolean isCrop(@NotNull Material material) {
        return CROPS.contains(material);
    }

    public static @NotNull Material getCropSeeds(@NotNull Material crop) {
        Preconditions.checkArgument(isCrop(crop), "Material is not a crop");
        return CROP_BY_SEEDS.get(crop);
    }

}
