package io.github.pigaut.voxel.function.interact.inventory;

import io.github.pigaut.voxel.function.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.loader.*;
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
            clickTypes.add(config.get("click", ClickType.class));
        }

        if (config.isSequence("click")) {
            clickTypes.addAll(config.getList("click", ClickType.class));
        }

        final InventoryAction action = config.getOptional("action", InventoryAction.class).orElse(null);

        if (action != null && !clickTypes.isEmpty()) {
            throw new InvalidConfigurationException(config, "action", "Both a click type an inventory action are set. Please specify only one of these options");
        }

        if (action == null && clickTypes.isEmpty()) {
            throw new InvalidConfigurationException(config, "click", "You need to specify a click type or inventory action");
        }

        final Boolean shouldCancel = config.getOptionalBoolean("cancel-event").orElse(null);
        final Function function = config.load(Function.class);

        if (action != null) {
            return new InventoryActionFunction(action, shouldCancel, function);
        }

        final boolean shouldShift = config.getOptionalBoolean("shifting|should-shift").orElse(false);
        return new SimpleInventoryClickFunction(clickTypes, shouldShift, shouldCancel, function);
    }

    @Override
    public @NotNull InventoryClickFunction loadFromSequence(@NotNull ConfigSequence sequence) throws InvalidConfigurationException {
        return new MultiInventoryClickFunction(sequence.getAll(InventoryClickFunction.class));
    }

}
