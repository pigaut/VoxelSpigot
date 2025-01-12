package io.github.pigaut.voxel.function.action.server;

import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.loader.*;
import io.github.pigaut.yaml.formatter.*;
import org.bukkit.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class Broadcast implements ServerAction {

    private final List<String> messages;

    public Broadcast(String message) {
        this.messages = List.of(message);
    }

    public Broadcast(List<String> messages) {
        this.messages = messages;
    }

    @Override
    public void execute() {
        for (String message : messages) {
            Bukkit.broadcastMessage(message);
        }
    }

    public static ConfigLoader<Broadcast> newConfigLoader() {
        return new ConfigLoader<Broadcast>() {
            @Override
            public @NotNull String getProblemDescription() {
                return "Could not load action 'BROADCAST'";
            }

            @Override
            public String getKey() {
                return "BROADCAST";
            }

            @Override
            public @NotNull Broadcast loadFromScalar(ConfigScalar scalar) throws InvalidConfigurationException {
                return new Broadcast(scalar.toString(StringColor.FORMATTER));
            }

            @Override
            public @NotNull Broadcast loadFromSequence(@NotNull ConfigSequence sequence) throws InvalidConfigurationException {
                return new Broadcast(sequence.toStringList(StringColor.FORMATTER));
            }
        };
    }
}
