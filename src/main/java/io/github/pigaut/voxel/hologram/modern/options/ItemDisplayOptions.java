package io.github.pigaut.voxel.hologram.modern.options;

import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.loader.*;
import org.bukkit.entity.*;
import org.jetbrains.annotations.*;

public class ItemDisplayOptions extends DisplayOptions {

    public final ItemDisplay.ItemDisplayTransform transform;

    public ItemDisplayOptions(Display.Billboard billboard, ItemDisplay.ItemDisplayTransform transform) {
        super(billboard);
        this.transform = transform;
    }

    public ItemDisplayOptions() {
        this.transform = ItemDisplay.ItemDisplayTransform.GROUND;
    }

    public void apply(ItemDisplay display) {
        display.setBillboard(billboard);
        display.setItemDisplayTransform(transform);
    }

    public static class Loader implements ConfigLoader<ItemDisplayOptions> {

        @Override
        public @Nullable String getProblemDescription() {
            return "invalid item display options";
        }

        @Override
        public @NotNull ItemDisplayOptions loadFromSection(@NotNull ConfigSection section) throws InvalidConfigurationException {
            final Display.Billboard billboard = section.getOptional("billboard", Display.Billboard.class)
                    .orElse(Display.Billboard.CENTER);
            final ItemDisplay.ItemDisplayTransform transform = section.getOptional("item-style", ItemDisplay.ItemDisplayTransform.class)
                    .orElse(ItemDisplay.ItemDisplayTransform.GROUND);

            return new ItemDisplayOptions(billboard, transform);
        }
    }

}
