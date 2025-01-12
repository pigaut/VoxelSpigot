package io.github.pigaut.voxel.function.action.player;

import io.github.pigaut.voxel.message.*;
import io.github.pigaut.voxel.player.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.loader.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class SendMessage implements PlayerAction {

    private final List<Message> messages;

    public SendMessage(Message message) {
        this.messages = List.of(message);
    }

    public SendMessage(List<Message> messages) {
        this.messages = messages;
    }

    @Override
    public void execute(@NotNull PluginPlayer player) {
        for (Message message : messages) {
            player.sendMessage(message);
        }
    }

    public static ConfigLoader<SendMessage> newConfigLoader() {
        return new ConfigLoader<SendMessage>() {
            @Override
            public @NotNull String getProblemDescription() {
                return "Could not load action 'SEND_MESSAGE'";
            }

            @Override
            public String getKey() {
                return "SEND_MESSAGE";
            }

            @Override
            public @NotNull SendMessage loadFromScalar(ConfigScalar scalar) throws InvalidConfigurationException {
                return new SendMessage(scalar.load(Message.class));
            }

            @Override
            public @NotNull SendMessage loadFromSection(@NotNull ConfigSection section) throws InvalidConfigurationException {
                return new SendMessage(section.load(Message.class));
            }

            @Override
            public @NotNull SendMessage loadFromSequence(@NotNull ConfigSequence sequence) throws InvalidConfigurationException {
                return new SendMessage(sequence.toList(Message.class));
            }
        };
    }
}
