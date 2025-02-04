package io.github.pigaut.voxel.util;

import org.bukkit.*;
import org.bukkit.block.*;
import org.jetbrains.annotations.*;

import java.util.*;

import static org.bukkit.Material.*;
import static org.bukkit.Material.MELON_SEEDS;

public class Crops {

    private static final List<Material> CROPS = List.of(WHEAT, CARROTS, POTATOES, BEETROOTS, NETHER_WART, COCOA, SWEET_BERRY_BUSH, PUMPKIN_STEM, MELON_STEM, SUGAR_CANE, CACTUS);
    private static final Map<Material, Material> cropByItem = new HashMap<>();

    static {
        cropByItem.put(WHEAT, WHEAT);
        cropByItem.put(CARROTS, CARROT);
        cropByItem.put(POTATOES, POTATO);
        cropByItem.put(BEETROOTS, BEETROOT);
        cropByItem.put(NETHER_WART, NETHER_WART);
        cropByItem.put(COCOA, COCOA_BEANS);
        cropByItem.put(SWEET_BERRY_BUSH, SWEET_BERRIES);
        cropByItem.put(PUMPKIN_STEM, PUMPKIN_SEEDS);
        cropByItem.put(MELON_STEM, MELON_SEEDS);
        cropByItem.put(SUGAR_CANE, SUGAR_CANE);
        cropByItem.put(CACTUS, CACTUS);
    }

    public static boolean isCrop(Material material) {
        return CROPS.contains(material);
    }

    public static @NotNull Material getCropItem(Material crop) {
        if (!isCrop(crop)) {
            throw new IllegalArgumentException("Material is not a crop");
        }
        return cropByItem.get(crop);
    }

    private static final List<BlockFace> SURROUNDING_FACES = List.of(BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST);

    public static List<Block> getCropsAttached(Block block) {
        return SURROUNDING_FACES.stream()
                .map(block::getRelative)
                .filter(attachedBlock -> {
                    final Material type = attachedBlock.getType();
                    return type == COCOA || type == SUGAR_CANE || type == CACTUS;
                })
                .toList();
    }

    public static boolean hasCropAttached(Block block) {
        for (BlockFace face : SURROUNDING_FACES) {
            final Material type = block.getRelative(face).getType();
            if (type == COCOA || type == SUGAR_CANE || type == CACTUS) {
                return true;
            }
        }
        return false;
    }

    public static @Nullable Block getCropAttached(Block block) {
        for (BlockFace face : SURROUNDING_FACES) {
            final Block attachedCrop = block.getRelative(face);
            final Material type = attachedCrop.getType();
            if (type == COCOA || type == SUGAR_CANE || type == CACTUS) {
                return attachedCrop;
            }
        }
        return null;
    }

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
