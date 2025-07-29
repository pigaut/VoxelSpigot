package io.github.pigaut.voxel.config.deserializer;

import com.cryptomorin.xseries.*;
import com.cryptomorin.xseries.particles.*;
import io.github.pigaut.yaml.configurator.parser.*;
import io.github.pigaut.yaml.parser.*;
import org.bukkit.*;
import org.jetbrains.annotations.*;

public class SoundDeserializer implements ConfigDeserializer<Sound> {

    @Override
    public @Nullable String getProblemDescription() {
        return "invalid sound";
    }

    @Override
    public @NotNull Sound deserialize(@NotNull String soundName) throws DeserializationException {
        final Sound sound = XSound.of(soundName).map(XSound::get).orElse(null);
        if (sound == null) {
            throw new DeserializationException("Expected a sound but found: " + soundName);
        }
        return sound;
    }

}
