package io.github.pigaut.voxel.config;

import io.github.pigaut.voxel.bukkit.*;
import io.github.pigaut.voxel.config.misc.*;
import io.github.pigaut.voxel.core.function.*;
import io.github.pigaut.voxel.core.function.action.*;
import io.github.pigaut.voxel.core.function.condition.*;
import io.github.pigaut.voxel.core.function.condition.config.*;
import io.github.pigaut.voxel.core.function.config.*;
import io.github.pigaut.voxel.core.function.interact.block.*;
import io.github.pigaut.voxel.core.function.interact.inventory.*;
import io.github.pigaut.voxel.core.item.config.*;
import io.github.pigaut.voxel.core.message.*;
import io.github.pigaut.voxel.core.message.config.*;
import io.github.pigaut.voxel.core.particle.*;
import io.github.pigaut.voxel.core.particle.config.*;
import io.github.pigaut.voxel.core.sound.*;
import io.github.pigaut.voxel.core.sound.config.*;
import io.github.pigaut.voxel.core.structure.*;
import io.github.pigaut.voxel.core.structure.config.*;
import io.github.pigaut.voxel.hologram.*;
import io.github.pigaut.voxel.placeholder.*;
import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.voxel.util.*;
import io.github.pigaut.yaml.configurator.deserialize.*;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.*;

public class PluginConfigurator extends SpigotConfigurator {

    private final ConditionLoader conditionLoader;
    private final ActionLoader actionLoader;

    public PluginConfigurator(@NotNull EnhancedPlugin plugin) {
        this.conditionLoader = new ConditionLoader();
        this.actionLoader = new ActionLoader(plugin);

        addDeserializer(Action.class, string ->
            Deserializers.enumDeserializer(BlockInteraction.class).deserialize(string).toAction());

        addLoader(Placeholder.class, new PlaceholderLoader());
        addLoader(Placeholder[].class, new PlaceholdersLoader());

        addLoader(BlockRange.class, new BlockRangeLoader());
        addMapper(BlockRange.class, new BlockRangeMapper());

        addLoader(ItemStack.class, new PluginItemStackLoader(plugin));
        addLoader(BlockChange.class, new BlockChangeLoader());
        addLoader(BlockStructure.class, new BlockStructureLoader(plugin));

        addLoader(Message.class, new MessageLoader(plugin));
        addLoader(ParticleEffect.class, new ParticleEffectLoader(plugin));
        addLoader(SoundEffect.class, new SoundEffectLoader(plugin));
        addLoader(Hologram.class, new HologramLoader(plugin));

        addLoader(Condition.class, conditionLoader);
        addLoader(NegativeCondition.class, new NegativeConditionLoader());
        addLoader(DisjunctiveCondition.class, new DisjunctiveConditionLoader());
        addLoader(SystemAction.class, actionLoader);
        addLoader(Function.class, new FunctionLoader(plugin));
        addLoader(BlockClickFunction.class, new BlockClickFunctionLoader());
        addLoader(InventoryClickFunction.class, new InventoryClickFunctionLoader());

    }

    public ConditionLoader getConditionLoader() {
        return conditionLoader;
    }

    public ActionLoader getActionLoader() {
        return actionLoader;
    }

}
