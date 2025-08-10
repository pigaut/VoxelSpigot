package io.github.pigaut.voxel.hologram;

import com.google.common.collect.*;
import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.voxel.plugin.manager.*;
import org.bukkit.*;
import org.bukkit.event.*;
import org.bukkit.event.world.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class HologramManager extends Manager implements Listener {

    private final Multimap<ChunkCoords, HologramDisplay> hologramsByChunk = Multimaps.newListMultimap(new HashMap<>(), ArrayList::new);

    public HologramManager(EnhancedJavaPlugin plugin) {
        super(plugin);
    }

    private record ChunkCoords(int x, int z) {
        @Override
        public boolean equals(Object object) {
            if (this == object) return true;
            if (!(object instanceof ChunkCoords that)) return false;
            return x == that.x && z == that.z;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, z);
        }
    }

    public boolean isRegistered(@NotNull HologramDisplay display) {
        return hologramsByChunk.containsValue(display);
    }

    public Collection<HologramDisplay> getAllHolograms(@NotNull Chunk chunk) {
        return hologramsByChunk.get(new ChunkCoords(chunk.getX(), chunk.getZ()));
    }

    public void registerHologram(@NotNull Chunk chunk, @NotNull HologramDisplay display) {
        hologramsByChunk.put(new ChunkCoords(chunk.getX(), chunk.getZ()), display);
    }

    public void unregisterHologram(@NotNull Chunk chunk, @NotNull HologramDisplay display) {
        hologramsByChunk.remove(new ChunkCoords(chunk.getX(), chunk.getZ()), display);
    }

    @EventHandler
    public void onChunkLoad(ChunkLoadEvent event) {
        for (HologramDisplay display : getAllHolograms(event.getChunk())) {
            System.out.println("Spawning hologram");
            display.spawn();
        }
    }

//    @EventHandler
//    public void onChunkUnload(ChunkUnloadEvent event) {
//        for (HologramDisplay display : getAllHolograms(event.getChunk())) {
//            System.out.println("Despawning hologram");
//            display.despawn();
//        }
//    }

}
