package io.github.pigaut.voxel.config.deserializer;

import com.cryptomorin.xseries.particles.*;
import io.github.pigaut.yaml.configurator.parser.*;
import io.github.pigaut.yaml.parser.*;
import org.bukkit.*;
import org.jetbrains.annotations.*;

public class ParticleDeserializer implements ConfigDeserializer<Particle> {

    @Override
    public @Nullable String getProblemDescription() {
        return "invalid particle";
    }

    @Override
    public @NotNull Particle deserialize(@NotNull String particleName) throws DeserializationException {
        final Particle particle = XParticle.of(particleName).map(XParticle::get).orElse(null);
        if (particle == null) {
            throw new DeserializationException("Expected a particle but found: " + particleName);
        }
        return particle;
    }

}
