package io.github.pigaut.voxel.config.deserializer;

import com.cryptomorin.xseries.*;
import io.github.pigaut.yaml.configurator.parser.*;
import io.github.pigaut.yaml.parser.*;
import org.bukkit.enchantments.*;
import org.jetbrains.annotations.*;

public class EnchantmentDeserializer implements ConfigDeserializer<Enchantment> {

    @Override
    public @Nullable String getProblemDescription() {
        return "invalid enchantment";
    }

    @Override
    public @NotNull Enchantment deserialize(@NotNull String enchantName) throws DeserializationException {
        final Enchantment enchantment = XEnchantment.of(enchantName).map(XEnchantment::get).orElse(null);
        if (enchantment == null) {
            throw new DeserializationException("Could not find enchantment with name: " + enchantName);
        }
        return enchantment;
    }

}
