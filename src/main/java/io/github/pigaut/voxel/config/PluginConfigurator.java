package io.github.pigaut.voxel.config;

import io.github.pigaut.voxel.config.misc.*;
import io.github.pigaut.voxel.core.function.*;
import io.github.pigaut.voxel.core.function.action.*;
import io.github.pigaut.voxel.core.function.condition.*;
import io.github.pigaut.voxel.core.function.condition.config.*;
import io.github.pigaut.voxel.core.function.config.*;
import io.github.pigaut.voxel.core.hologram.*;
import io.github.pigaut.voxel.core.item.config.*;
import io.github.pigaut.voxel.core.message.*;
import io.github.pigaut.voxel.core.message.config.*;
import io.github.pigaut.voxel.core.particle.*;
import io.github.pigaut.voxel.core.particle.config.*;
import io.github.pigaut.voxel.core.sound.*;
import io.github.pigaut.voxel.core.sound.config.*;
import io.github.pigaut.voxel.core.structure.*;
import io.github.pigaut.voxel.core.structure.config.*;
import io.github.pigaut.voxel.hook.craftengine.*;
import io.github.pigaut.voxel.hook.itemsadder.*;
import io.github.pigaut.voxel.placeholder.*;
import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.voxel.server.*;
import io.github.pigaut.voxel.util.*;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.*;

public class PluginConfigurator extends SpigotConfigurator {

    private final ConditionLoader conditionLoader;
    private final ActionLoader actionLoader;

    public PluginConfigurator(@NotNull EnhancedPlugin plugin) {
        this.conditionLoader = new ConditionLoader();
        this.actionLoader = new ActionLoader(plugin);

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
        addLoader(FunctionAction.class, actionLoader);
        addLoader(Function.class, new FunctionLoader(plugin));

        if (SpigotServer.isPluginEnabled("ItemsAdder")) {
            addLoader(dev.lone.itemsadder.api.CustomBlock.class, new ItemsAdderBlockLoader());
        }

        if (SpigotServer.isPluginEnabled("CraftEngine")) {
            addLoader(net.momirealms.craftengine.core.block.CustomBlock.class, new CraftEngineBlockLoader());
        }

    }

    public ConditionLoader getConditionLoader() {
        return conditionLoader;
    }

    public ActionLoader getActionLoader() {
        return actionLoader;
    }

}
