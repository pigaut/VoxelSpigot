package io.github.pigaut.voxel.core.structure;

import io.github.pigaut.voxel.bukkit.*;
import io.github.pigaut.voxel.menu.button.*;
import io.github.pigaut.voxel.placeholder.*;
import io.github.pigaut.voxel.player.*;
import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.voxel.plugin.manager.*;
import io.github.pigaut.yaml.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.block.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class StructureManager extends ConfigBackedManager.Sequence<BlockStructure> implements Listener {

    public final NamespacedKey wandKey;

    private Set<Material> materialBlacklist;
    private ItemStack structureWand;

    public StructureManager(@NotNull EnhancedJavaPlugin plugin) {
        super(plugin, "Structure", "structures");
        wandKey = plugin.getNamespacedKey("wand");
    }

    public boolean isStructureWand(@NotNull ItemStack item) {
        final ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return false;
        }
        return PersistentData.hasString(meta, wandKey);
    }

    public ItemStack getWand() {
        return ItemPlaceholders.parseAll(structureWand.clone());
    }

    public Set<Material> getMaterialBlacklist() {
        return new HashSet<>(materialBlacklist);
    }

    @Override
    public void loadFromSequence(ConfigSequence sequence) throws InvalidConfigurationException {
        final BlockStructure structure = sequence.loadRequired(BlockStructure.class);
        try {
            add(structure);
        }
        catch (DuplicateElementException e) {
            throw new InvalidConfigurationException(sequence, e.getMessage());
        }
    }

    @Override
    public void loadData() {
        final ConfigSection config = plugin.getConfiguration();
        materialBlacklist = new HashSet<>(config.getAll("structure-blacklist", Material.class));

        {
            structureWand = config.get("structure-wand", ItemStack.class).throwOrElse(null);

            if (structureWand == null) {
                structureWand = new IconBuilder()
                        .withType(Material.GOLDEN_PICKAXE)
                        .withDisplay("&6&lWand")
                        .buildIcon();
                plugin.getLogger().warning("Could not find 'structure-wand' in config.yml using default item");
            }

            final ItemMeta meta = structureWand.getItemMeta();
            if (meta != null) {
                PersistentData.setString(meta, wandKey, "true");
                structureWand.setItemMeta(meta);
            }
        }
    }

    @EventHandler
    public void onWandClick(PlayerInteractEvent event) {
        if (!event.hasBlock() || !event.hasItem() || event.getHand() != EquipmentSlot.HAND
                || !isStructureWand(event.getItem())) {
            return;
        }
        event.setCancelled(true);

        final Player player = event.getPlayer();
        if (!player.hasPermission(plugin.getPermission("structure.wand"))) {
            plugin.sendMessage(player, "missing-wand-permission");
            return;
        }

        final PlayerState playerState = plugin.getPlayerState(player.getUniqueId());
        final Location targetLocation = event.getClickedBlock().getLocation();
        if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
            playerState.setFirstSelection(targetLocation);
            plugin.sendMessage(player, "selected-first-position");
            return;
        }

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            playerState.setSecondSelection(targetLocation);
            plugin.sendMessage(player, "selected-second-position");
        }
    }

}
