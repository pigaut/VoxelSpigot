package io.github.pigaut.voxel.core.function.interact.inventory;

import io.github.pigaut.voxel.core.function.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.load.*;
import org.bukkit.event.inventory.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class InventoryClickFunctionLoader implements ConfigLoader<InventoryClickFunction> {

    @Override
    public @NotNull String getProblemDescription() {
        return "invalid inventory click function";
    }

    @Override
    public @NotNull InventoryClickFunction loadFromSection(@NotNull ConfigSection config) throws InvalidConfigurationException {
        final Set<ClickType> clickTypes = new HashSet<>();

        if (config.isSet("click")) {
            clickTypes.add(config.getRequired("click", ClickType.class));
        }

        if (config.isSequence("click")) {
            clickTypes.addAll(config.getAll("click", ClickType.class));
        }

        final InventoryAction action = config.get("click-action", InventoryAction.class).throwOrElse(null);

        if (action != null && !clickTypes.isEmpty()) {
            throw new InvalidConfigurationException(config, "click-action", "Both a click type an inventory action are set. Please specify only one of these options");
        }

        if (action == null && clickTypes.isEmpty()) {
            clickTypes.add(ClickType.LEFT);
            clickTypes.add(ClickType.RIGHT);
        }

        final Function function = config.loadRequired(Function.class);
        if (action != null) {
            return new InventoryActionFunction(action, function);
        }

        return new SimpleInventoryClickFunction(clickTypes, function);
    }

    @Override
    public @NotNull InventoryClickFunction loadFromSequence(@NotNull ConfigSequence sequence) throws InvalidConfigurationException {
        return new MultiInventoryClickFunction(sequence.getAll(InventoryClickFunction.class));
    }

}
