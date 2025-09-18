package io.github.pigaut.voxel.core.function.config;

import io.github.pigaut.voxel.core.function.*;
import io.github.pigaut.voxel.core.function.action.*;
import io.github.pigaut.voxel.core.function.condition.*;
import io.github.pigaut.voxel.core.function.impl.*;
import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.voxel.plugin.manager.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.load.*;
import io.github.pigaut.yaml.convert.format.*;
import io.github.pigaut.yaml.util.*;
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
        final String functionName = scalar.toString(CaseStyle.SNAKE);

        final Function function = plugin.getFunction(functionName);
        if (function == null) {
            throw new InvalidConfigurationException(scalar, "Could not find function with name: '" + functionName + "'");
        }

        return function;
    }

    @Override
    public @NotNull Function loadFromSection(@NotNull ConfigSection section) throws InvalidConfigurationException {
        final String functionName = section.getKey();
        final String functionGroup = Group.byFunctionFile(section.getRoot().getFile());

        Function function = null;
        if (section.contains("if|condition|conditions")) {
            function = new ConditionalFunction(functionName, functionGroup,
                    section.getRequired("if|condition|conditions", Condition.class),
                    section.get("do|action|actions", SystemAction.class).throwOrElse(SystemAction.EMPTY),
                    section.get("else|or|or-else|or else", SystemAction.class).throwOrElse(SystemAction.EMPTY)
            );
        }
        else if (section.contains("if-not|if not")) {
            function = new ConditionalFunction(functionName, functionGroup,
                    section.getRequired("if-not|if not", NegativeCondition.class),
                    section.get("do|action|actions", SystemAction.class).throwOrElse(SystemAction.EMPTY),
                    section.get("else|or|or-else|or else", SystemAction.class).throwOrElse(SystemAction.EMPTY)
            );
        }
        else if (section.contains("if-any|if any")) {
            function = new ConditionalFunction(functionName, functionGroup,
                    section.getRequired("if-any|if any", DisjunctiveCondition.class),
                    section.get("do|action|actions", SystemAction.class).throwOrElse(SystemAction.EMPTY),
                    section.get("else|or|or-else", SystemAction.class).throwOrElse(SystemAction.EMPTY)
            );
        }
        else if (section.contains("do|action|actions")) {
            function = new SimpleFunction(functionName, functionGroup,
                    section.getRequired("do|action|actions", SystemAction.class));
        }

        if (function == null) {
            throw new InvalidConfigurationException(section, "Function doesn't contain any valid statement");
        }

        final Integer repetitions = section.getInteger("repetitions|repeat|loops")
                .filter(Predicates.isPositive(), "Repetitions must be greater than 0")
                .throwOrElse(null);

        final Integer interval = section.getInteger("interval|period")
                .filter(Predicates.notNull(repetitions), "Repetitions must be set to use interval delay")
                .filter(Predicates.isPositive(), "Interval must be greater than 0")
                .throwOrElse(null);

        if (interval != null) {
            function = new PeriodicFunction(plugin, function, interval, repetitions);
        }
        else if (repetitions != null) {
            function = new RepeatedFunction(function, repetitions);
        }

        final Integer delay = section.getInteger("delay")
                .filter(Predicates.isPositive(), "Delay must be greater than 0")
                .throwOrElse(null);

        if (delay != null) {
            function = new DelayedFunction(plugin, function, delay);
        }

        final Double chance = section.getDouble("chance")
                .filter(Predicates.range(0, 1), "Chance must be a value between 0.0 to 1.0")
                .throwOrElse(null);

        if (chance != null) {
            function = new ChanceFunction(function, chance);
        }

        return function;
    }

    @Override
    public @NotNull Function loadFromSequence(@NotNull ConfigSequence sequence) throws InvalidConfigurationException {
        final String functionName = sequence.getKey();
        final String functionGroup = Group.byFunctionFile(sequence.getRoot().getFile());
        return new MultiFunction(functionName, functionGroup, sequence, sequence.getAll(Function.class));
    }

}

