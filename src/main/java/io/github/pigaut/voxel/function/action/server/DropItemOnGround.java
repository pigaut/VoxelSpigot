package io.github.pigaut.voxel.function.action.server;

import com.google.common.collect.*;
import io.github.pigaut.voxel.function.action.player.*;
import io.github.pigaut.voxel.player.*;
import io.github.pigaut.voxel.server.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.loader.*;
import org.bukkit.*;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.*;

public class DropItemOnGround implements PlayerAction {

    private final Multimap<ItemStack, Location> items;

    public DropItemOnGround(Multimap<ItemStack, Location> items) {
        this.items = items;
    }

    @Override
    public void execute(@NotNull PluginPlayer player) {
        items.forEach((item, location) -> {
            World world = location.getWorld();
            if (world == null) {
                world = SpigotServer.getDefaultWorld();
            }
            world.dropItemNaturally(location, item);
        });
    }

    public static ConfigLoader<DropItemOnGround> newConfigLoader() {
        return new ConfigLoader<DropItemOnGround>() {
            @Override
            public @NotNull String getProblemDescription() {
                return "Could not load action 'DROP_ITEM'";
            }

            @Override
            public String getKey() {
                return "DROP_ITEM";
            }

            @Override
            public @NotNull DropItemOnGround loadFromSection(@NotNull ConfigSection section) throws InvalidConfigurationException {
                final Multimap<ItemStack, Location> items = ArrayListMultimap.create();

                for (ConfigSection nestedSection : section.getNestedSections()) {
                    final ItemStack item = nestedSection.get("item", ItemStack.class);
                    if (nestedSection.isSequence("location|locations")) {
                        items.putAll(item, nestedSection.getList("location|locations", Location.class));
                    }
                    else {
                        items.put(item, nestedSection.get("location", Location.class));
                    }
                }

                return new DropItemOnGround(items);
            }
        };
    }

}
