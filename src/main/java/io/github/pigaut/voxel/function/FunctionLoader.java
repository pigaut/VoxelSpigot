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

        final ConfigSequence conditionSequence = config.getOptionalSequence("if").orElse(null);
        if (conditionSequence != null) {
            final Function function = new ConditionalFunction(
                    conditionSequence.getAll(Condition.class),
                    config.getAll("do", Action.class),
                    config.getAll("or", Action.class)
            );
            return addFunctionOptions(config, function);
        }

        final ConfigSequence doSequence = config.getOptionalSequence("actions").orElse(null);
        if (doSequence != null) {
            final Function function = new SimpleFunction(doSequence.getAll(Action.class));
            return addFunctionOptions(config, function);
        }

        final ConfigScalar switchType = config.getOptionalScalar("switch").orElse(null);
        if (switchType != null) {
            final PluginConfigurator configurator = plugin.getConfigurator();
            final ConfigLoader<? extends Condition> conditionLoader = configurator.getConditionLoader().getLoader(switchType, switchType.toString());

            final SwitchFunction function = new SwitchFunction();

            final SwitchCase defaultCase = config.getOptionalSection("default")
                    .map(defaultSection -> {
                        final List<Action> actions = defaultSection.getAll(Action.class);
                        return SwitchCase.createDefaultCase(actions);
                    })
                    .orElse(null);

            for (ConfigSection caseSection : config.getNestedSections("cases")) {
                final Condition condition = conditionLoader.loadFromScalar(caseSection.getScalar("case"));
                final List<Action> actions = caseSection.getAll("do", Action.class);
                final boolean breakCycle = caseSection.getOptionalBoolean("break").orElse(false);

                final SwitchCase switchCase = new SwitchCase(condition, actions, breakCycle);
                function.addCase(switchCase);
            }

            if (defaultCase != null) {
                function.addCase(defaultCase);
            }

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

