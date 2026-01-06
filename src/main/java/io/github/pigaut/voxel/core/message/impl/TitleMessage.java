package io.github.pigaut.voxel.core.message.impl;

import io.github.pigaut.voxel.core.message.*;
import io.github.pigaut.voxel.menu.button.*;
import io.github.pigaut.voxel.placeholder.*;
import io.github.pigaut.voxel.player.*;
import io.github.pigaut.yaml.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class TitleMessage extends GenericMessage {

    private final Title title;

    public TitleMessage(String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        this(UUID.randomUUID().toString(), null, title, subtitle, fadeIn, stay, fadeOut);
    }

    public TitleMessage(String name, @Nullable String group, String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        super(name, group);
        this.title = new Title(title, subtitle, fadeIn, stay, fadeOut);
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
    public void send(@NotNull PlayerState playerState) {
        Player player = playerState.asPlayer();
        title.send(player, playerState.getPlaceholderSuppliers());
    }

}
