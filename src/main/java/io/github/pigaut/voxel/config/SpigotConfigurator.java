package io.github.pigaut.voxel.config;

import io.github.pigaut.voxel.bukkit.*;
import io.github.pigaut.voxel.config.attribute.*;
import io.github.pigaut.voxel.config.block.*;
import io.github.pigaut.voxel.config.color.*;
import io.github.pigaut.voxel.config.deserializer.*;
import io.github.pigaut.voxel.config.location.*;
import io.github.pigaut.voxel.config.persistent.data.*;
import io.github.pigaut.voxel.core.item.config.*;
import io.github.pigaut.yaml.configurator.*;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.enchantments.*;
import org.bukkit.inventory.*;
import org.bukkit.persistence.*;

public class SpigotConfigurator extends StandardConfigurator {

    public SpigotConfigurator() {
        this(true);
    }

    public SpigotConfigurator(boolean compact) {
        addMapper(PersistentDataContainer.class, new PersistentDataContainerMapper());
        addMapper(PersistentDataType.class, new PersistentDataTypeMapper());
        addMapper(ItemStack.class, new ItemStackMapper());
        addMapper(Attribute.class, new AttributeMapper(compact));
        addMapper(Location.class, new LocationMapper(compact));
        addMapper(Block.class, new BlockDataMapper());

        addLoader(ItemStack.class, new ItemStackLoader());
        addLoader(Attribute.class, new AttributeLoader());
        addLoader(Location.class, new LocationLoader());
        addLoader(Color.class, new ColorLoader());

        addDeserializer(World.class, new WorldDeserializer());
        addDeserializer(Enchantment.class, new EnchantmentDeserializer());
        addDeserializer(Material.class, new MaterialDeserializer());
        addDeserializer(Particle.class, new ParticleDeserializer());
        addDeserializer(Sound.class, new SoundDeserializer());

        addSerializer(World.class, World::getName);
        addSerializer(Enchantment.class, enchant -> enchant.getKey().getKey());
    }

}
