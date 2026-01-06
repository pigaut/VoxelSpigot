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

public class MultiMessage implements Message {

    private final String name;
    private final String group;
    private final ConfigSequence sequence;
    private final List<Message> messages;

    public MultiMessage(String name, String group, ConfigSequence sequence, @NotNull List<@NotNull Message> messages) {
        this.name = name;
        this.group = group;
        this.sequence = sequence;
        this.messages = messages;
    }

    @Override
    public @NotNull MessageType getType() {
        return MessageType.MULTI;
    }

    @Override
    public @NotNull String getName() {
        return name;
    }

    @Override
    public @Nullable String getGroup() {
        return group;
    }

    @Override
    public @NotNull ItemStack getIcon() {
        return IconBuilder.of(Material.BOOKSHELF).buildIcon();
    }

    @Override
    public void send(@NotNull PlayerState playerState) {
        for (Message message : messages) {
            message.send(playerState);
        }
    }

}
