package io.github.pigaut.voxel.util;

import org.bukkit.*;
import org.bukkit.block.*;
import org.jetbrains.annotations.*;

import java.util.*;

import static org.bukkit.Material.*;
import static org.bukkit.Material.MELON_SEEDS;

public class Crops {

    private static final List<Material> CROPS = List.of(WHEAT, CARROTS, POTATOES, BEETROOTS, NETHER_WART, COCOA, SWEET_BERRY_BUSH, PUMPKIN_STEM, MELON_STEM);
    private static final Map<Material, Material> cropByItem = Map.of(
            WHEAT, WHEAT,
            CARROTS, CARROT,
            POTATOES, POTATO,
            BEETROOTS, BEETROOT,
            NETHER_WART, NETHER_WART,
            COCOA, COCOA_BEANS,
            SWEET_BERRY_BUSH, SWEET_BERRIES,
            PUMPKIN_STEM, PUMPKIN_SEEDS,
            MELON_STEM, MELON_SEEDS
    );

    public static boolean isCrop(Material material) {
        return CROPS.contains(material);
    }

    public static @NotNull Material getCropItem(Material crop) {
        if (!isCrop(crop)) {
            throw new IllegalArgumentException("Material is not a crop");
        }
        return cropByItem.get(crop);
    }

    private static final BlockFace[] SURROUNDING_FACES = {BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST};

    public static @Nullable Block getAttachedCocoa(Block block) {
        for (BlockFace face : SURROUNDING_FACES) {
            final Block surroundingBlock = block.getRelative(face);
            if (surroundingBlock.getType() == Material.COCOA) {
                return surroundingBlock;
            }
        }
        return null;
    }

    public static boolean hasCocoaAttached(Block block) {
        return block.getRelative(BlockFace.NORTH, 1).getType() == COCOA ||
                block.getRelative(BlockFace.EAST, 1).getType() == COCOA ||
                block.getRelative(BlockFace.SOUTH, 1).getType() == COCOA ||
                block.getRelative(BlockFace.WEST, 1).getType() == COCOA;
    }

}
