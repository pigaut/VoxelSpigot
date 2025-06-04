package io.github.pigaut.voxel.bukkit;

import org.bukkit.*;
import org.bukkit.block.*;
import org.jetbrains.annotations.*;

import java.util.*;

import static org.bukkit.Material.*;
import static org.bukkit.Material.MELON_SEEDS;

public class Crops {

    private static final List<Material> CROPS = List.of(WHEAT, CARROTS, POTATOES, BEETROOTS, NETHER_WART, COCOA,
            SWEET_BERRY_BUSH, PUMPKIN_STEM, MELON_STEM, SUGAR_CANE, CACTUS, BAMBOO);
    private static final Map<Material, Material> cropBySeeds = new HashMap<>();

    static {
        cropBySeeds.put(WHEAT, WHEAT_SEEDS);
        cropBySeeds.put(CARROTS, CARROT);
        cropBySeeds.put(POTATOES, POTATO);
        cropBySeeds.put(BEETROOTS, BEETROOT_SEEDS);
        cropBySeeds.put(NETHER_WART, NETHER_WART);
        cropBySeeds.put(COCOA, COCOA_BEANS);
        cropBySeeds.put(SWEET_BERRY_BUSH, SWEET_BERRIES);
        cropBySeeds.put(PUMPKIN_STEM, PUMPKIN_SEEDS);
        cropBySeeds.put(MELON_STEM, MELON_SEEDS);
        cropBySeeds.put(SUGAR_CANE, SUGAR_CANE);
        cropBySeeds.put(CACTUS, CACTUS);
        cropBySeeds.put(BAMBOO, BAMBOO);
    }

    public static boolean isCrop(Material material) {
        return CROPS.contains(material);
    }

    public static @NotNull Material getCropSeeds(Material crop) {
        if (!isCrop(crop)) {
            throw new IllegalArgumentException("Material is not a crop");
        }
        return cropBySeeds.get(crop);
    }

    private static final List<BlockFace> SURROUNDING_FACES = List.of(BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST);

    public static List<Block> getCropsAttached(Block block) {
        return SURROUNDING_FACES.stream()
                .map(block::getRelative)
                .filter(attachedBlock -> {
                    final Material type = attachedBlock.getType();
                    return type == COCOA || type == SUGAR_CANE || type == CACTUS || type == BAMBOO;
                })
                .toList();
    }

    public static boolean hasCropAttached(Block block) {
        for (BlockFace face : SURROUNDING_FACES) {
            final Material type = block.getRelative(face).getType();
            if (type == COCOA || type == SUGAR_CANE || type == CACTUS || type == BAMBOO) {
                return true;
            }
        }
        return false;
    }

    public static @Nullable Block getCropAttached(Block block) {
        for (BlockFace face : SURROUNDING_FACES) {
            final Block attachedCrop = block.getRelative(face);
            final Material type = attachedCrop.getType();
            if (type == COCOA || type == SUGAR_CANE || type == CACTUS || type == BAMBOO) {
                return attachedCrop;
            }
        }
        return null;
    }

}
