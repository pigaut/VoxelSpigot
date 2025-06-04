package io.github.pigaut.voxel.core.structure;

import io.github.pigaut.voxel.bukkit.*;
import io.github.pigaut.voxel.menu.button.*;
import io.github.pigaut.voxel.placeholder.*;
import io.github.pigaut.voxel.player.*;
import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.voxel.plugin.manager.*;
import io.github.pigaut.voxel.util.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.node.*;
import io.github.pigaut.yaml.node.section.*;
import io.github.pigaut.yaml.node.sequence.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.block.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;
import org.jetbrains.annotations.*;

import java.io.*;
import java.util.*;

public class StructureManager extends ManagerContainer<BlockStructure> implements Listener {

    public final NamespacedKey wandKey;

    private Set<Material> materialBlacklist;
    private ItemStack structureWand;

    public StructureManager(@NotNull EnhancedJavaPlugin plugin) {
        super(plugin);
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
    public @Nullable String getFilesDirectory() {
        return "structures";
    }

    @Override
    public void loadFile(@NotNull File file) {
        final RootSequence config = new RootSequence(file, plugin.getConfigurator());
        config.setPrefix("Structure");
        config.load();
        final BlockStructure structure = config.load(BlockStructure.class);
        this.add(structure);
    }

    @Override
    public void loadData() {
        final ConfigSection config = plugin.getConfiguration();
        materialBlacklist = new HashSet<>(config.getList("structure-blacklist", Material.class));

        {
            structureWand = config.getOptional("structure-wand", ItemStack.class).orElse(null);

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
