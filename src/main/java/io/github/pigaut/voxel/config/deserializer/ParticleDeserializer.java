package io.github.pigaut.voxel.config.deserializer;

import com.cryptomorin.xseries.particles.*;
import io.github.pigaut.yaml.configurator.convert.deserialize.*;
import io.github.pigaut.yaml.convert.parse.*;
import org.bukkit.*;
import org.jetbrains.annotations.*;

public class ParticleDeserializer implements Deserializer<Particle> {

    @Override
    public @Nullable String getProblemDescription() {
        return "invalid particle";
    }

    @Override
    public @NotNull Particle deserialize(@NotNull String particleName) throws StringParseException {
        final Particle particle = XParticle.of(particleName).map(XParticle::get).orElse(null);
        if (particle == null) {
            throw new StringParseException("Expected a particle but found: " + particleName);
        }
        return particle;
    }

}
