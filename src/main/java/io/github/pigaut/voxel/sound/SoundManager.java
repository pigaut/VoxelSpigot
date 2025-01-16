package io.github.pigaut.voxel.sound;

import io.github.pigaut.voxel.plugin.manager.*;

import java.util.*;

public class SoundManager extends Manager {

    private final Map<String, SoundEffect> soundsByName = new HashMap<>();

    public List<String> getSoundNames() {
        return new ArrayList<>(soundsByName.keySet());
    }

    public SoundEffect getSound(String name) {
        return soundsByName.get(name);
    }

    public void addSound(String name, SoundEffect sound) {
        soundsByName.put(name, sound);
    }

    public void removeSound(String name) {
        soundsByName.remove(name);
    }

    public void clearSounds() {
        soundsByName.clear();
    }

}
