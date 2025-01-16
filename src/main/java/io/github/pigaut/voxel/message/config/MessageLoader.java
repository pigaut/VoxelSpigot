package io.github.pigaut.voxel.message.config;

import io.github.pigaut.voxel.message.*;
import io.github.pigaut.voxel.message.type.*;
import io.github.pigaut.voxel.plugin.*;
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
        return "Could not load player message";
    }

    @Override
    public @NotNull Message loadFromScalar(ConfigScalar scalar) throws InvalidConfigurationException {
        final String messageName = scalar.toString();
        final Message message = plugin.getMessage(messageName);
        if (message == null) {
            throw new InvalidConfigurationException(scalar, "Could not find any message with name: '" + messageName + "'");
        }
        return message;
    }

    @Override
    public @NotNull Message loadFromSection(@NotNull ConfigSection section) throws InvalidConfigurationException {
        final String type = section.getString("type", StringStyle.CONSTANT);
        Message message;
        switch (type) {
            case "CHAT" -> {
                message = new ChatMessage(section.getString("message", StringColor.FORMATTER));
            }

            case "ACTIONBAR" -> {
                message = new ActionBarMessage(section.getString("message", StringColor.FORMATTER));
            }

            case "BOSSBAR" -> {
                message = new BossBarMessage(plugin,
                        section.getString("title"),
                        section.getOptional("style", BarStyle.class).orElse(BarStyle.SEGMENTED_6),
                        section.getOptional("color", BarColor.class).orElse(BarColor.RED),
                        section.getOptionalInteger("duration").orElse(100),
                        section.getDoubleList("progress")
                );
            }

            case "TITLE" -> {
                message = new TitleMessage(
                        section.getString("title"),
                        section.getOptionalString("subtitle").orElse(""),
                        section.getOptionalInteger("fade-in").orElse(20),
                        section.getOptionalInteger("stay").orElse(60),
                        section.getOptionalInteger("fade-out").orElse(20)
                );
            }

            default -> {
                throw new InvalidConfigurationException(section, "type", "'" + type + "' is not valid message type");
            }
        }

        return addMessageOptions(section, message);
    }

    @Override
    public @NotNull Message loadFromSequence(@NotNull ConfigSequence sequence) throws InvalidConfigurationException {
        return new MultiMessage(sequence.getAll(Message.class));
    }

    private Message addMessageOptions(ConfigSection section, Message message) {
        final Integer repetitions = section.getOptionalInteger("repetitions|loops").orElse(null);
        if (repetitions != null) {
            message = new RepeatedMessage(message, repetitions);
        }

        final Integer delay = section.getOptionalInteger("delay").orElse(null);
        if (delay != null) {
            message = new DelayedMessage(plugin, message, delay);
        }

        return message;
    }

}
