package io.github.pigaut.voxel.menu.button;

import com.cryptomorin.xseries.*;
import io.github.pigaut.voxel.bukkit.*;
import org.bukkit.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class IconBuilder {

    private Material type;
    private int amount = 1;
    private String display = "";
    private boolean enchanted = false;
    private final List<String> lore = new ArrayList<>();
    private @Nullable String headTexture = null;

    public IconBuilder() {
        this(Material.TERRACOTTA);
    }

    public IconBuilder(Material type) {
        this.type = type;
    }

    public static IconBuilder of(Material type) {
        return new IconBuilder(type);
    }

    public ItemStack buildIcon() {
        final ItemStack icon = new ItemStack(type, amount);
        final ItemMeta meta = icon.getItemMeta();
        if (meta != null) {
            if (display != null) {
                meta.setDisplayName(display);
            }
            if (!lore.isEmpty()) {
                meta.setLore(lore);
            }
            if (enchanted) {
                meta.addEnchant(XEnchantment.RESPIRATION.get(), 1, false);
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }

            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DYE);
            icon.setItemMeta(meta);
        }
        return icon;
    }

    public IconBuilder withType(Material type) {
        this.type = type;
        return this;
    }

    public IconBuilder withAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public IconBuilder withDisplay(String display) {
        this.display = StringColor.translateColors("&f" + display);
        return this;
    }

    public IconBuilder addLeftClickLore(String action) {
        final String line = "&eLeft-Click: &f" + action;
        lore.add(StringColor.translateColors(line));
        return this;
    }

    public IconBuilder addRightClickLore(String action) {
        final String line = "&6Right-Click: &f" + action;
        lore.add(StringColor.translateColors(line));
        return this;
    }

    public IconBuilder addShiftLeftClickLore(String action) {
        final String line = "&cShift + Left-Click: &f" + action;
        lore.add(StringColor.translateColors(line));
        return this;
    }

    public IconBuilder addShiftRightClickLore(String action) {
        final String line = "&4Shift + Right-Click: &f" + action;
        lore.add(StringColor.translateColors(line));
        return this;
    }

    public IconBuilder addLore(String... loreLines) {
        for (String loreLine : loreLines) {
            lore.add(StringColor.translateColors("&f" + loreLine));
        }
        return this;
    }

    public IconBuilder addLore(List<String> loreLines) {
        for (String loreLine : loreLines) {
            lore.add(StringColor.translateColors("&f" + loreLine));
        }
        return this;
    }

    public IconBuilder enchanted(boolean enchanted) {
        this.enchanted = enchanted;
        return this;
    }

}
