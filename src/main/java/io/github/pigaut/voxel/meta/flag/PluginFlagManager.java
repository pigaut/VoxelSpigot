package io.github.pigaut.voxel.meta.flag;

import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.node.section.*;
import org.bukkit.inventory.*;

import java.io.*;

public class PluginFlagManager extends FlagManager {

    private final EnhancedPlugin plugin;

    public PluginFlagManager(EnhancedPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void load() {
        final RootSection config = new RootSection(plugin.getFile("flags.yml"));
        config.load();

        for (ConfigSection section : config.getNestedSections()) {
            addFlag(new Flag(section.getKey(),
                    section.getOptionalString("group").orElse("default"),
                    section.getOptionalInteger("weight").orElse(0))
            );
        }
    }

}
