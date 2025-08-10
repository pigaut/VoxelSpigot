package io.github.pigaut.voxel.hologram.modern.options;

import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.loader.*;
import org.bukkit.entity.*;
import org.jetbrains.annotations.*;

public class DisplayOptions {

    public final Display.Billboard billboard;

    public DisplayOptions(Display.Billboard billboard) {
        this.billboard = billboard;
    }

    public DisplayOptions() {
        this.billboard = Display.Billboard.CENTER;
    }

    public void apply(Display display) {
        display.setBillboard(billboard);
    }

    public static class Loader implements ConfigLoader<DisplayOptions> {

        @Override
        public @Nullable String getProblemDescription() {
            return "invalid hologram options";
        }

        @Override
        public @NotNull DisplayOptions loadFromSection(@NotNull ConfigSection section) throws InvalidConfigurationException {
            final Display.Billboard billboard = section.getOptional("billboard", Display.Billboard.class)
                    .orElse(Display.Billboard.CENTER);

            return new DisplayOptions(billboard);
        }

    }

}
