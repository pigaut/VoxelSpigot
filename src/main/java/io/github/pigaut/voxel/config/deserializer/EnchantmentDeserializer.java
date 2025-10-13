package io.github.pigaut.voxel.config.deserializer;

import com.cryptomorin.xseries.*;
import io.github.pigaut.yaml.configurator.convert.deserialize.*;
import io.github.pigaut.yaml.convert.parse.*;
import org.bukkit.enchantments.*;
import org.jetbrains.annotations.*;

public class EnchantmentDeserializer implements Deserializer<Enchantment> {

    @Override
    public @Nullable String getProblemDescription() {
        return "invalid enchantment";
    }

    @Override
    public @NotNull Enchantment deserialize(@NotNull String enchantName) throws StringParseException {
        final Enchantment enchantment = XEnchantment.of(enchantName).map(XEnchantment::get).orElse(null);
        if (enchantment == null) {
            throw new StringParseException("Could not find enchantment with name: " + enchantName);
        }
        return enchantment;
    }

}
