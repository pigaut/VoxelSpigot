package io.github.pigaut.voxel.particle;

import io.github.pigaut.voxel.plugin.manager.*;

import java.util.*;

public class ParticleManager extends Manager {

    private final Map<String, ParticleEffect> particlesByName = new HashMap<>();

    public List<String> getParticleNames() {
        return new ArrayList<>(particlesByName.keySet());
    }

    public ParticleEffect getParticle(String name) {
        return particlesByName.get(name);
    }

    public void addParticle(String name, ParticleEffect particle) {
        particlesByName.put(name, particle);
    }

    public void removeParticle(String name) {
        particlesByName.remove(name);
    }

    public void clearParticles() {
        particlesByName.clear();
    }

}
