package io.github.pigaut.voxel.plugin;

import io.github.pigaut.voxel.bukkit.*;
import io.github.pigaut.voxel.placeholder.*;
import io.github.pigaut.voxel.plugin.manager.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.util.*;
import org.bukkit.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;
import org.jetbrains.annotations.*;

import java.io.*;
import java.util.*;

public class Settings implements ConfigBacked {

    protected final EnhancedPlugin plugin;

    // Generic Settings
    private boolean debug;
    private boolean showReloadErrors;
    private boolean coloredConsole;
    private boolean generateExamples;
    private boolean checkForUpdates;
    private boolean metrics;
    private boolean dumpLogo;
    private File languageFile;
    private Ticks autoSave;
    private Ticks worldLoadTimeout;
    private Ticks playerCacheDuration;

    public int guiReopenDelay = 40;

    // Drops settings
    private List<Material> applyFortuneWhitelist;

    // Structure settings
    private final NamespacedKey wandKey;
    private Set<Material> structureBlacklist;
    private ItemStack structureWand;

    public Settings(EnhancedPlugin plugin) {
        this.plugin = plugin;
        this.wandKey = plugin.getNamespacedKey("wand");
    }

    @Override
    public @NotNull List<ConfigurationException> loadConfigurationData() {
        List<ConfigurationException> errors = new ArrayList<>();

        ConfigSection config = plugin.getConfiguration();

        debug = config.getBoolean("debug")
                .withDefault(false, errors::add);

        showReloadErrors = config.getBoolean("show-reload-errors")
                .withDefault(true, errors::add);

        coloredConsole = config.getBoolean("colored-console")
                .withDefault(true, errors::add);

        generateExamples = config.getBoolean("generate-examples")
                .withDefault(true, errors::add);

        checkForUpdates = config.getBoolean("check-for-updates")
                .withDefault(true, errors::add);

        metrics = config.getBoolean("metrics")
                .withDefault(true, errors::add);

        dumpLogo = config.getBoolean("dump-logo")
                .withDefault(true, errors::add);

        languageFile = config.getString("language")
                .or("en")
                .map(fileName -> plugin.getFile("languages/" + fileName + ".yml"))
                .value();

        autoSave = config.get("auto-save", Ticks.class)
                .withDefault(Ticks.fromSeconds(0), errors::add);

        worldLoadTimeout = config.get("world-load-timeout", Ticks.class)
                .withDefault(Ticks.fromSeconds(3), errors::add);

        playerCacheDuration = config.get("player-cache-duration", Ticks.class)
                .withDefault(Ticks.fromSeconds(30), errors::add);

        applyFortuneWhitelist = config.getElements("apply-fortune|do-fortune-whitelist", Material.class)
                .withDefault(List.of(), errors::add);

        structureBlacklist = config.getElements("structure-blacklist", Material.class)
                .map(HashSet::new)
                .withDefault(new HashSet<>(), errors::add);

        {
            structureWand = config.get("structure-wand", ItemStack.class)
                    .withDefault(new ItemStack(Material.GOLDEN_PICKAXE), errors::add);

            final ItemMeta meta = structureWand.getItemMeta();
            if (meta != null) {
                PersistentData.setString(meta, wandKey, "true");
                structureWand.setItemMeta(meta);
            }
            else {
                errors.add(new InvalidConfigurationException(config, "structure-wand.type", "This item does not support item meta"));
            }
        }

        return errors;
    }

    public boolean isDebug() {
        return debug;
    }

    public boolean isShowReloadErrors() {
        return showReloadErrors;
    }

    public boolean isColoredConsole() {
        return coloredConsole;
    }

    public boolean isGenerateExamples() {
        return generateExamples;
    }

    public boolean isCheckForUpdates() {
        return checkForUpdates;
    }

    public boolean isMetrics() {
        return metrics;
    }

    public boolean isDumpLogo() {
        return dumpLogo;
    }

    public File getLanguageFile() {
        return languageFile;
    }

    public Ticks getAutoSave() {
        return autoSave;
    }

    public Ticks getWorldLoadTimeout() {
        return worldLoadTimeout;
    }

    public Ticks getPlayerCacheDuration() {
        return playerCacheDuration;
    }

    public List<Material> getApplyFortuneWhitelist() {
        return new ArrayList<>(applyFortuneWhitelist);
    }

    public boolean isApplyFortune(@NotNull Material material) {
        return applyFortuneWhitelist.contains(material);
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
