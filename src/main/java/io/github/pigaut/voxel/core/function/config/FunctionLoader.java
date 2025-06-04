package io.github.pigaut.voxel.core.function.config;

import io.github.pigaut.voxel.core.function.*;
import io.github.pigaut.voxel.core.function.action.*;
import io.github.pigaut.voxel.core.function.condition.*;
import io.github.pigaut.voxel.core.function.impl.*;
import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.voxel.plugin.manager.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.loader.*;
import io.github.pigaut.yaml.parser.*;
import org.jetbrains.annotations.*;

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
    public @NotNull Function loadFromScalar(ConfigScalar scalar) throws InvalidConfigurationException {
        final String functionName = scalar.toString(StringStyle.SNAKE);
        final Function function = plugin.getFunction(functionName);
        if (function == null) {
            throw new InvalidConfigurationException(scalar, "Could not find function with name: '" + functionName + "'");
        }
        return function;
    }

    @Override
    public @NotNull Function loadFromSection(@NotNull ConfigSection section) throws InvalidConfigurationException {
        final String functionName = section.getKey();
        final String functionGroup = PathGroup.byFunctionFile(section.getRoot().getFile());

        final ConfigField conditionField = section.getOptionalField("if|condition|conditions").orElse(null);
        if (conditionField != null) {
            final Function function = new ConditionalFunction(functionName, functionGroup, section,
                    conditionField.load(Condition.class),
                    section.getOptional("do|action|actions", SystemAction.class).orElse(SystemAction.EMPTY),
                    section.getOptional("else|or|or-else", SystemAction.class).orElse(SystemAction.EMPTY)
            );
            return addFunctionOptions(section, function);
        }

        final ConfigField negativeConditionField = section.getOptionalField("if-not|if not").orElse(null);
        if (negativeConditionField != null) {
            final Function function = new ConditionalFunction(functionName, functionGroup, section,
                    negativeConditionField.load(NegativeCondition.class),
                    section.getOptional("do|action|actions", SystemAction.class).orElse(SystemAction.EMPTY),
                    section.getOptional("else|or|or-else", SystemAction.class).orElse(SystemAction.EMPTY)
            );
            return addFunctionOptions(section, function);
        }

        final ConfigField disjunctiveConditionField = section.getOptionalField("if-any|if any").orElse(null);
        if (disjunctiveConditionField != null) {
            final Function function = new ConditionalFunction(functionName, functionGroup, section,
                    disjunctiveConditionField.load(DisjunctiveCondition.class),
                    section.getOptional("do|action|actions", SystemAction.class).orElse(SystemAction.EMPTY),
                    section.getOptional("else|or|or-else", SystemAction.class).orElse(SystemAction.EMPTY)
            );
            return addFunctionOptions(section, function);
        }

        final ConfigField actionField = section.getOptionalField("do|action|actions").orElse(null);
        if (actionField != null) {
            final Function function = new SimpleFunction(functionName, functionGroup, section, actionField.load(SystemAction.class));
            return addFunctionOptions(section, function);
        }

        throw new InvalidConfigurationException(section, "Function doesn't contain any valid statement");
    }

    @Override
    public @NotNull Function loadFromSequence(@NotNull ConfigSequence sequence) throws InvalidConfigurationException {
        final String functionName = sequence.getKey();
        final String functionGroup = PathGroup.byFunctionFile(sequence.getRoot().getFile());
        return new MultiFunction(functionName, functionGroup, sequence, sequence.getAll(Function.class));
    }

    private Function addFunctionOptions(ConfigSection section, Function function) {
        final Integer repetitions = section.getOptionalInteger("repetitions|loops").orElse(null);
        if (repetitions != null && repetitions < 1) {
            throw new InvalidConfigurationException(section, "repetitions", "The function repetitions must be greater than 0");
        }

        final Integer interval = section.getOptionalInteger("interval|period").orElse(null);

        if (interval != null) {
            if (repetitions == null) {
                throw new InvalidConfigurationException(section, "interval", "The 'repetitions' option must be set to use interval delay");
            }
            if (interval < 1) {
                throw new InvalidConfigurationException(section, "interval", "The function interval must be greater than 0");
            }
            function = new PeriodicFunction(plugin, function, interval, repetitions);
        }

        else if (repetitions != null) {
            function = new RepeatedFunction(function, repetitions);
        }

        final Integer delay = section.getOptionalInteger("delay").orElse(null);
        if (delay != null) {
            function = new DelayedFunction(plugin, function, delay);
        }

        final Double chance = section.getOptionalDouble("chance").orElse(null);
        if (chance != null) {
            if (chance < 0 || chance > 1) {
                throw new InvalidConfigurationException(section, "chance", "The function chance must be a percentage from 0.0 to 1.0");
            }
            function = new ChanceFunction(function, chance);
        }

        return function;
    }

}

