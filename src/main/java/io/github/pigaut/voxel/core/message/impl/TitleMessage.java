package io.github.pigaut.voxel.core.message.impl;

import io.github.pigaut.voxel.core.message.*;
import io.github.pigaut.voxel.menu.button.*;
import io.github.pigaut.voxel.placeholder.*;
import io.github.pigaut.yaml.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.*;

public class TitleMessage extends GenericMessage {

    private final Title title;

    protected TitleMessage(String name, @Nullable String group, ConfigSection section, Title title) {
        super(name, group, section);
        this.title = title;
    }

    public TitleMessage(String name, @Nullable String group, ConfigSection section, String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        this(name, group, section, new Title(title, subtitle, fadeIn, stay, fadeOut));
    }

    @Override
    public @NotNull MessageType getType() {
        return MessageType.TITLE;
    }

    @Override
    public @NotNull ItemStack getIcon() {
        return IconBuilder.of(Material.MAP).buildIcon();
    }

    @Override
    public void send(@NotNull Player player, PlaceholderSupplier... placeholderSuppliers) {
        title.send(player, placeholderSuppliers);
    }

}
