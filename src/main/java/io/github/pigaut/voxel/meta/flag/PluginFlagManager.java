package io.github.pigaut.voxel.meta.flag;

import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.yaml.*;

public class PluginFlagManager extends FlagManager {

    public PluginFlagManager(EnhancedPlugin plugin) {
        super(plugin);
    }

    @Override
    public void loadData() {
        clearFlags();
        final ConfigSection config = ConfigSection.loadConfiguration(plugin.getFile("flags.yml"));
        for (ConfigSection section : config.getNestedSections()) {
            addFlag(new Flag(section.getKey(),
                    section.getOptionalString("group").orElse("default"),
                    section.getOptionalInteger("weight").orElse(0))
            );
        }
    }

}
