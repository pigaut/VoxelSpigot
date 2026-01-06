package io.github.pigaut.voxel.config;

import io.github.pigaut.voxel.bukkit.*;
import io.github.pigaut.voxel.config.attribute.*;
import io.github.pigaut.voxel.config.attribute.legacy.*;
import io.github.pigaut.voxel.config.block.*;
import io.github.pigaut.voxel.config.color.*;
import io.github.pigaut.voxel.config.deserializer.*;
import io.github.pigaut.voxel.config.location.*;
import io.github.pigaut.voxel.config.persistence.*;
import io.github.pigaut.voxel.core.item.config.*;
import io.github.pigaut.voxel.server.*;
import io.github.pigaut.voxel.util.reflection.*;
import io.github.pigaut.voxel.version.*;
import io.github.pigaut.yaml.configurator.*;
import io.github.pigaut.yaml.configurator.convert.serialize.*;
import org.bukkit.*;
import org.bukkit.attribute.*;
import org.bukkit.block.*;
import org.bukkit.enchantments.*;
import org.bukkit.inventory.*;
import org.bukkit.persistence.*;

public class SpigotConfigurator extends StandardConfigurator {

    public SpigotConfigurator() {
        this(true);
    }

    @SuppressWarnings("UnstableApiUsage")
    public SpigotConfigurator(boolean compact) {
        final SpigotVersion serverVersion = SpigotServer.getVersion();

        addLoader(ItemStack.class, new ItemStackLoader());
        addMapper(ItemStack.class, new ItemStackMapper());

        addLoader(Location.class, new LocationLoader());
        addMapper(Location.class, new LocationMapper(compact));

        addLoader(ItemAttribute.class, new AttributeLoader());
        addMapper(ItemAttribute.class, new AttributeMapper());

        addLoader(Color.class, new ColorLoader());

        addMapper(Block.class, new BlockDataMapper());

        addMapper(PersistentDataContainer.class, new PersistentDataContainerMapper());
        addMapper(PersistentDataType.class, new PersistentDataTypeMapper());

        addDeserializer(World.class, new WorldDeserializer());
        if (serverVersion.isNewerThan(SpigotVersion.V1_17_1)) {
            addSerializer(World.class, World::getName);
        }
        else {
            addSerializer(World.class, world -> Reflection.on(world).call("getName").get());
        }

        addDeserializer(Enchantment.class, new EnchantmentDeserializer());
        addSerializer(Enchantment.class, enchant -> enchant.getKey().getKey());

        addDeserializer(Material.class, new MaterialDeserializer());
        addDeserializer(Particle.class, new ParticleDeserializer());
        addDeserializer(Sound.class, new SoundDeserializer());
        addDeserializer(Attribute.class, new AttributeDeserializer());

        addConverter(EquipmentSlot.class, new EquipmentSlotConverter());

        if (serverVersion.equalsOrIsNewerThan(SpigotVersion.V1_21_3)) {
            addDeserializer(EquipmentSlotGroup.class, new SlotGroupDeserializer());
            addSerializer(EquipmentSlotGroup.class, Serializers.defaultSerializer());
            addLoader(ItemAttribute.class, new AttributeLoader());
            addMapper(ItemAttribute.class, new AttributeMapper());
        }
        else {
            addLoader(ItemAttribute.class, new AttributeLoaderLegacy());
            addMapper(ItemAttribute.class, new AttributeMapperLegacy());
        }

    }

}
