package io.github.pigaut.voxel.plugin.manager;

import io.github.pigaut.yaml.*;
import org.jetbrains.annotations.*;

import java.util.*;

@FunctionalInterface
public interface ConfigBacked {

    @NotNull
    List<ConfigurationException> loadConfigurationData();

}
