package io.github.pigaut.voxel.function.action.player;

import io.github.pigaut.voxel.player.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.loader.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class CachePlayerValue implements PlayerAction {

    private final Map<String, Object> cache;

    public CachePlayerValue(String id, Object value) {
        this.cache = Map.of(id, value);
    }

    public CachePlayerValue(Map<String, Object> cache) {
        this.cache = cache;
    }

    @Override
    public void execute(@NotNull PluginPlayer player) {

        for (Map.Entry<String, Object> entry : cache.entrySet()) {
            player.saveCache(entry.getKey(), entry.getValue());
        }
    }

    public static ConfigLoader<CachePlayerValue> newConfigLoader() {
        return new ConfigLoader<CachePlayerValue>() {
            @Override
            public @NotNull String getProblemDescription() {
                return "Could not load action 'PLAYER_CACHE'";
            }

            @Override
            public String getKey() {
                return "PLAYER_CACHE";
            }

            @Override
            public @NotNull CachePlayerValue loadFromSection(@NotNull ConfigSection section) throws InvalidConfigurationException {
                if (section.isSet("id")) {
                    final String id = section.getString("id");
                    final String value = section.getString("value");
                    return new CachePlayerValue(id, value);
                }

                final Map<String, Object> cache = new HashMap<>();
                for (String key : section.getKeys()) {
                    cache.put(key, section.getScalar(key).getValue());
                }
                return new CachePlayerValue(cache);
            }

        };
    }
}
