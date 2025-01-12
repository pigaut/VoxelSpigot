package io.github.pigaut.voxel.message.type;

import io.github.pigaut.voxel.message.*;
import io.github.pigaut.voxel.meta.placeholder.*;
import org.bukkit.entity.*;

public class TitleMessage implements Message {

    private final Title title;

    public TitleMessage(Title title) {
        this.title = title;
    }

    public TitleMessage(String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        this(new Title(title, subtitle, fadeIn, stay, fadeOut));
    }

    @Override
    public void send(Player player) {
        title.send(player);
    }

    @Override
    public void send(Player player, PlaceholderSupplier... placeholderSuppliers) {
        title.send(player, placeholderSuppliers);
    }

}
