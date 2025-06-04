package io.github.pigaut.voxel.core.message.config;

import io.github.pigaut.voxel.core.message.*;
import io.github.pigaut.voxel.core.message.impl.*;
import io.github.pigaut.voxel.hologram.*;
import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.voxel.plugin.manager.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.loader.*;
import io.github.pigaut.yaml.formatter.*;
import io.github.pigaut.yaml.parser.*;
import org.bukkit.boss.*;
import org.jetbrains.annotations.*;

public class MessageLoader implements ConfigLoader<Message> {

    private final EnhancedPlugin plugin;

    public MessageLoader(EnhancedPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getProblemDescription() {
        return "invalid message";
    }

    @Override
    public @NotNull Message loadFromScalar(ConfigScalar scalar) throws InvalidConfigurationException {
        final String messageName = scalar.toString(StringStyle.SNAKE);
        final Message message = plugin.getMessage(messageName);
        if (message == null) {
            throw new InvalidConfigurationException(scalar, "Could not find any message with name: '" + messageName + "'");
        }
        return message;
    }

    @Override
    public @NotNull Message loadFromSection(@NotNull ConfigSection section) throws InvalidConfigurationException {
        final String messageName = section.getKey();
        final String messageGroup = PathGroup.byMessageFile(section.getRoot().getFile());
        final String type = section.getString("type", StringStyle.CONSTANT);
        Message message;
        switch (type) {
            case "CHAT" -> {
                final String chat = section.getString("message", StringColor.FORMATTER);
                message = new ChatMessage(messageName, messageGroup, section, chat);
            }

            case "ACTIONBAR" -> {
                final String actionbar = section.getString("message", StringColor.FORMATTER);
                message = new ActionBarMessage(messageName, messageGroup, section, actionbar);
            }

            case "BOSSBAR" -> {
                message = new BossBarMessage(plugin, messageName, messageGroup, section,
                        section.getString("title", StringColor.FORMATTER),
                        section.getOptional("style", BarStyle.class).orElse(BarStyle.SEGMENTED_6),
                        section.getOptional("color", BarColor.class).orElse(BarColor.RED),
                        section.getOptionalInteger("duration").orElse(100),
                        section.getDoubleList("progress")
                );
            }

            case "TITLE" -> {
                message = new TitleMessage(messageName, messageGroup, section,
                        section.getString("title", StringColor.FORMATTER),
                        section.getOptionalString("subtitle", StringColor.FORMATTER).orElse(""),
                        section.getOptionalInteger("fade-in").orElse(20),
                        section.getOptionalInteger("stay").orElse(60),
                        section.getOptionalInteger("fade-out").orElse(20)
                );
            }

            case "HOLOGRAM" -> {
                message = new HologramMessage(plugin, messageName, messageGroup, section,
                        section.get("hologram", Hologram.class),
                        section.getOptionalInteger("duration").orElse(40),
                        section.getOptionalDouble("radius.x").orElse(null),
                        section.getOptionalDouble("radius.y").orElse(null),
                        section.getOptionalDouble("radius.z").orElse(null)
                );
            }

            default -> {
                throw new InvalidConfigurationException(section, "type", "Could not find message type with name: '" + type + "'");
            }
        }

        final Integer repetitions = section.getOptionalInteger("repetitions|loops").orElse(null);
        if (repetitions != null && repetitions < 1) {
            throw new InvalidConfigurationException(section, "repetitions", "The message repetitions must be greater than 0");
        }

        final Integer interval = section.getOptionalInteger("interval|period").orElse(null);

        if (interval != null) {
            if (repetitions == null) {
                throw new InvalidConfigurationException(section, "interval", "The 'repetitions' option must be set to use interval delay");
            }
            if (interval < 1) {
                throw new InvalidConfigurationException(section, "interval", "The function interval must be greater than 0");
            }
            message = new PeriodicMessage(plugin, message, interval, repetitions);
        }
        else if (repetitions != null) {
            message = new RepeatedMessage(message, repetitions);
        }

        final Integer delay = section.getOptionalInteger("delay").orElse(null);
        if (delay != null) {
            message = new DelayedMessage(plugin, message, delay);
        }

        return message;
    }

    @Override
    public @NotNull Message loadFromSequence(@NotNull ConfigSequence sequence) throws InvalidConfigurationException {
        final String messageName = sequence.getKey();
        final String messageGroup = PathGroup.byMessageFile(sequence.getRoot().getFile());
        return new MultiMessage(messageName, messageGroup, sequence, sequence.getAll(Message.class));
    }

}
