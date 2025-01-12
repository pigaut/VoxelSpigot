package io.github.pigaut.voxel.function.action.player;

import io.github.pigaut.voxel.player.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.loader.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class SendChatMessage implements PlayerAction {

    private final List<String> messages;

    public SendChatMessage(String message) {
        this.messages = List.of(message);
    }

    public SendChatMessage(List<String> messages) {
        this.messages = messages;
    }

    @Override
    public void execute(@NotNull PluginPlayer player) {
        for (String message : messages) {
            player.sendMessage(message);
        }
    }

    public static ConfigLoader<SendChatMessage> newConfigLoader() {
        return new ConfigLoader<SendChatMessage>() {
            @Override
            public @NotNull String getProblemDescription() {
                return "Could not load action 'CHAT_MESSAGE'";
            }

            @Override
            public @Nullable String getKey() {
                return "CHAT_MESSAGE";
            }

            @Override
            public @NotNull SendChatMessage loadFromScalar(ConfigScalar scalar) throws InvalidConfigurationException {
                return new SendChatMessage(scalar.toString());
            }

            @Override
            public @NotNull SendChatMessage loadFromSequence(@NotNull ConfigSequence sequence) throws InvalidConfigurationException {
                return new SendChatMessage(sequence.toStringList());
            }
        };
    }
}
