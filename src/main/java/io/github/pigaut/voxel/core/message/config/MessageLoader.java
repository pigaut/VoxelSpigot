package io.github.pigaut.voxel.core.message.config;

import io.github.pigaut.voxel.core.message.*;
import io.github.pigaut.voxel.core.message.impl.*;
import io.github.pigaut.voxel.hologram.*;
import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.voxel.plugin.manager.*;
import io.github.pigaut.voxel.server.*;
import io.github.pigaut.voxel.util.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.load.*;
import io.github.pigaut.yaml.convert.format.*;
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
        final String messageName = scalar.toString(CaseStyle.SNAKE);
        final Message message = plugin.getMessage(messageName);
        if (message == null) {
            throw new InvalidConfigurationException(scalar, "Could not find any message with name: '" + messageName + "'");
        }
        return message;
    }

    @Override
    public @NotNull Message loadFromSection(@NotNull ConfigSection section) throws InvalidConfigurationException {
        final String messageName = section.getKey();
        final String messageGroup = Group.byMessageFile(section.getRoot().getFile());
        final String type = section.getRequiredString("type", CaseStyle.CONSTANT);
        Message message;
        switch (type) {
            case "CHAT" -> {
                final String chat = section.getString("message", StringColor.FORMATTER).throwOrElse("none");
                message = new ChatMessage(messageName, messageGroup, chat);
            }

            case "ACTIONBAR" -> {
                final String actionbar = section.getString("message", StringColor.FORMATTER).throwOrElse("none");
                message = new ActionBarMessage(messageName, messageGroup, actionbar);
            }

            case "BOSSBAR" -> {
                message = new BossBarMessage(plugin, messageName, messageGroup,
                        section.getString("title", StringColor.FORMATTER).throwOrElse("none"),
                        section.get("style", BarStyle.class).throwOrElse(BarStyle.SOLID),
                        section.get("color", BarColor.class).throwOrElse(BarColor.RED),
                        section.getInteger("duration").throwOrElse(100),
                        section.getDoubleList("progress")
                );
            }

            case "TITLE" -> {
                message = new TitleMessage(messageName, messageGroup,
                        section.getString("title", StringColor.FORMATTER).throwOrElse("none"),
                        section.getString("subtitle", StringColor.FORMATTER).throwOrElse(""),
                        section.getInteger("fade-in").throwOrElse(10),
                        section.getInteger("stay").throwOrElse(70),
                        section.getInteger("fade-out").throwOrElse(20)
                );
            }

            case "HOLOGRAM" -> {
                message = new HologramMessage(plugin, messageName, messageGroup,
                        SpigotServer.isPluginLoaded("DecentHolograms") ? section.getRequired("hologram", Hologram.class) : null,
                        section.getInteger("duration").throwOrElse(40),
                        section.getDouble("radius.x").throwOrElse(null),
                        section.getDouble("radius.y").throwOrElse(null),
                        section.getDouble("radius.z").throwOrElse(null)
                );
            }

            default -> {
                throw new InvalidConfigurationException(section, "type", "Found unknown message type: '" + type + "'");
            }
        }

        final Integer repetitions = section.getInteger("repetitions|loops").throwOrElse(null);
        if (repetitions != null && repetitions < 1) {
            throw new InvalidConfigurationException(section, "repetitions", "The message repetitions must be greater than 0");
        }

        final Integer interval = section.getInteger("interval|period").throwOrElse(null);

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

        final Integer delay = section.getInteger("delay").throwOrElse(null);
        if (delay != null) {
            message = new DelayedMessage(plugin, message, delay);
        }

        return message;
    }

    @Override
    public @NotNull Message loadFromSequence(@NotNull ConfigSequence sequence) throws InvalidConfigurationException {
        final String messageName = sequence.getKey();
        final String messageGroup = Group.byMessageFile(sequence.getRoot().getFile());
        return new MultiMessage(messageName, messageGroup, sequence, sequence.getAll(Message.class));
    }

}
