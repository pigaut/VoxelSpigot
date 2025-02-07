package io.github.pigaut.voxel.function;

import io.github.pigaut.voxel.config.*;
import io.github.pigaut.voxel.function.action.*;
import io.github.pigaut.voxel.function.condition.*;
import io.github.pigaut.voxel.function.options.*;
import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.loader.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class FunctionLoader implements ConfigLoader<Function> {

    private final EnhancedPlugin plugin;

    public FunctionLoader(EnhancedPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getProblemDescription() {
        return "invalid function";
    }

    @Override
    public @NotNull Function loadFromSection(@NotNull ConfigSection config) throws InvalidConfigurationException {

        final ConfigField conditionField = config.getOptionalField("conditions|if|condition").orElse(null);
        if (conditionField != null) {
            final Function function = new ConditionalFunction(
                    conditionField.load(Condition.class),
                    config.getOptional("do|action|actions|success", Action.class).orElse(Action.EMPTY),
                    config.getOptional("or|else|fail", Action.class).orElse(Action.EMPTY)
            );
            return addFunctionOptions(config, function);
        }

        final ConfigField negativeConditionField = config.getOptionalField("if-not|if not").orElse(null);
        if (negativeConditionField != null) {
            final Function function = new ConditionalFunction(
                    negativeConditionField.load(NegativeCondition.class),
                    config.getOptional("do|action|actions|success", Action.class).orElse(Action.EMPTY),
                    config.getOptional("or|else|fail", Action.class).orElse(Action.EMPTY)
            );
            return function;
        }

        final ConfigSequence doSequence = config.getOptionalSequence("do|actions|action").orElse(null);
        if (doSequence != null) {
            final Function function = new SimpleFunction(doSequence.load(Action.class));
            return addFunctionOptions(config, function);
        }

        throw new InvalidConfigurationException(config, "Function doesn't contain any valid statement");
    }

    @Override
    public @NotNull Function loadFromSequence(@NotNull ConfigSequence sequence) throws InvalidConfigurationException {
        return new MultiFunction(sequence.getAll(Function.class));
    }

    private Function addFunctionOptions(ConfigSection config, Function function) {
        final Integer repetitions = config.getOptionalInteger("repetitions|loops").orElse(null);
        if (repetitions != null) {
            function = new RepeatedFunction(function, repetitions);
        }

        final Integer delay = config.getOptionalInteger("delay").orElse(null);
        if (delay != null) {
            function = new DelayedFunction(plugin, function, delay);
        }

        final Double chance = config.getOptionalDouble("chance").orElse(null);
        if (chance != null) {
            if (chance < 0 || chance > 1) {
                throw new InvalidConfigurationException(config, "chance", "Value must be a percentage from 0 to 1");
            }
            function = new ChanceFunction(function, chance);
        }

        return function;
    }

}

