package io.github.pigaut.voxel.core.message.config;

import io.github.pigaut.voxel.bukkit.*;
import io.github.pigaut.voxel.core.hologram.*;
import io.github.pigaut.voxel.core.message.*;
import io.github.pigaut.voxel.core.message.impl.*;
import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.voxel.plugin.manager.*;
import io.github.pigaut.voxel.server.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.load.*;
import io.github.pigaut.yaml.convert.format.*;
import io.github.pigaut.yaml.util.*;
import org.bukkit.boss.*;
import org.jetbrains.annotations.*;

import java.util.*;

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
                final String chat = section.getString("message", StringColor.FORMATTER).withDefault("none");
                message = new ChatMessage(messageName, messageGroup, chat);
            }

            case "ACTIONBAR" -> {
                final String actionbar = section.getString("message", StringColor.FORMATTER).withDefault("none");
                message = new ActionBarMessage(messageName, messageGroup, actionbar);
            }

            case "BOSSBAR" -> {
                message = new BossBarMessage(plugin, messageName, messageGroup,
                        section.getString("title", StringColor.FORMATTER).withDefault("none"),
                        section.get("style", BarStyle.class).withDefault(BarStyle.SOLID),
                        section.get("color", BarColor.class).withDefault(BarColor.RED),
                        section.getInteger("duration").withDefault(100),
                        section.getDoubleList("progress")
                );
            }

            case "TITLE" -> {
                message = new TitleMessage(messageName, messageGroup,
                        section.getString("title", StringColor.FORMATTER).withDefault("none"),
                        section.getString("subtitle", StringColor.FORMATTER).withDefault(""),
                        section.getInteger("fade-in").withDefault(10),
                        section.getInteger("stay").withDefault(70),
                        section.getInteger("fade-out").withDefault(20)
                );
            }

            case "HOLOGRAM" -> {
                message = new HologramMessage(plugin, messageName, messageGroup,
                        SpigotServer.isPluginLoaded("DecentHolograms") ? section.getRequired("hologram", Hologram.class) : null,
                        section.getInteger("duration").withDefault(40),
                        section.getDouble("radius.x").withDefault(null),
                        section.getDouble("radius.y").withDefault(null),
                        section.getDouble("radius.z").withDefault(null)
                );
            }

            default -> {
                throw new InvalidConfigurationException(section, "type", "Found unknown message type: '" + type + "'");
            }
        }

        Integer repetitions = section.getInteger("repeat|repetitions")
                .filter(Predicates.isPositive(), "Repetitions must be greater than 0")
                .withDefault(null);

        Integer interval = section.get("interval|period", Ticks.class)
                .filter(repetitions != null, "Repetitions must be set to use interval delay")
                .map(Ticks::getCount)
                .withDefault(null);

        if (interval != null) {
            message = new PeriodicMessage(plugin, message, interval, repetitions);
        }
        else if (repetitions != null) {
            message = new RepeatedMessage(message, repetitions);
        }

        Integer delay = section.get("delay", Ticks.class)
                .map(Ticks::getCount)
                .withDefault(null);

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
