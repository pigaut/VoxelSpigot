package io.github.pigaut.voxel.config.deserializer;

import com.cryptomorin.xseries.*;
import io.github.pigaut.yaml.configurator.convert.deserialize.*;
import io.github.pigaut.yaml.convert.parse.*;
import org.bukkit.*;
import org.jetbrains.annotations.*;

public class SoundDeserializer implements Deserializer<Sound> {

    @Override
    public @Nullable String getProblemDescription() {
        return "invalid sound";
    }

    @Override
    public @NotNull Sound deserialize(@NotNull String soundName) throws StringParseException {
        final Sound sound = XSound.of(soundName).map(XSound::get).orElse(null);
        if (sound == null) {
            throw new StringParseException("Expected a sound but found: " + soundName);
        }
        return sound;
    }

}
