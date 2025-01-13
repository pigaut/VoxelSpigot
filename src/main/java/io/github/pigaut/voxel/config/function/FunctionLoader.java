package io.github.pigaut.voxel.config.function;

import io.github.pigaut.voxel.config.*;
import io.github.pigaut.voxel.function.*;
import io.github.pigaut.voxel.function.action.*;
import io.github.pigaut.voxel.function.condition.*;
import io.github.pigaut.voxel.function.condition.player.*;
import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.*;
import io.github.pigaut.yaml.configurator.loader.*;
import org.bukkit.plugin.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class FunctionLoader implements ConfigLoader<Function> {

    private final EnhancedPlugin plugin;

    public FunctionLoader(EnhancedPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getProblemDescription() {
        return "Could not load function";
    }

    @Override
    public @NotNull Function loadFromSection(@NotNull ConfigSection config) throws InvalidConfigurationException {

        final ConfigSection conditionSection = config.getOptionalSection("if").orElse(null);
        if (conditionSection != null) {
            final Function function = new ConditionalFunction(
                    conditionSection.getAll(Condition.class),
                    config.getAll("do", Action.class),
                    config.getAll("or", Action.class)
            );
            return addFunctionOptions(config, function);
        }

        final ConfigSection doSection = config.getOptionalSection("actions").orElse(null);
        if (doSection != null) {
            final Function function = new SimpleFunction(doSection.getAll(Action.class));
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

        throw new InvalidConfigurationException(config, "Function doesn't contain a valid statement");
    }

    private Function addFunctionOptions(ConfigSection config, Function function) {
        // add other delay between repetitions

        int repetitions = config.getOptionalInteger("repetitions|loops").orElse(1);
        if (repetitions > 1) {
            function = new LoopedFunction(function, repetitions);
        }

        int delay = config.getOptionalInteger("delay").orElse(0);
        if (delay > 0) {
            function = new DelayedFunction(plugin, function, delay);
        }

        double chance = config.getOptionalDouble("chance").orElse(-1.0);
        if (chance > -1) {
            if (chance < 0 || chance > 1) {
                throw new InvalidConfigurationException(config, "chance", "Value must be a percentage from 0 to 1");
            }
            function = new ChanceFunction(function, chance);
        }

        return function;
    }

}

