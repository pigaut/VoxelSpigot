package io.github.pigaut.voxel.core;

import io.github.pigaut.voxel.bukkit.*;
import io.github.pigaut.voxel.placeholder.*;
import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.voxel.plugin.manager.*;
import io.github.pigaut.yaml.*;
import org.bukkit.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class OptionsManager extends Manager implements ConfigBacked {

    private List<Material> doFortuneWhitelist;

    private final NamespacedKey wandKey;
    private Set<Material> structureBlacklist;
    private ItemStack structureWand;

    public OptionsManager(EnhancedJavaPlugin plugin) {
        super(plugin);
        this.wandKey = plugin.getNamespacedKey("wand");
    }

    @Override
    public @NotNull List<ConfigurationException> loadConfigurationData() {
        List<ConfigurationException> errorsFound = new ArrayList<>();

        ConfigSection config = plugin.getConfiguration();
        doFortuneWhitelist = config.getElements("do-fortune-whitelist", Material.class)
                .withDefault(List.of(), errorsFound::add);

        structureBlacklist = config.getElements("structure-blacklist", Material.class)
                .map(HashSet::new)
                .withDefault(new HashSet<>(), errorsFound::add);

        {
            structureWand = config.get("structure-wand", ItemStack.class)
                    .withDefault(new ItemStack(Material.GOLDEN_PICKAXE), errorsFound::add);

            final ItemMeta meta = structureWand.getItemMeta();
            if (meta != null) {
                PersistentData.setString(meta, wandKey, "true");
                structureWand.setItemMeta(meta);
            }
            else {
                errorsFound.add(new InvalidConfigurationException(config, "structure-wand.type", "This item does not support item meta"));
            }
        }

        return errorsFound;
    }

    public List<Material> getDoFortuneWhitelist() {
        return new ArrayList<>(doFortuneWhitelist);
    }

    public boolean shouldDoFortune(Material material) {
        return doFortuneWhitelist.contains(material);
    }

    public Set<Material> getStructureBlacklist() {
        return new HashSet<>(structureBlacklist);
    }

    public boolean isStructureWand(@NotNull ItemStack item) {
        final ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return false;
        }
        return PersistentData.hasString(meta, wandKey);
    }

    public ItemStack getStructureWand() {
        return ItemPlaceholders.parseAll(structureWand.clone());
    }

}
