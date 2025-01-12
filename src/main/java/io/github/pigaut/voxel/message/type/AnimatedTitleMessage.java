package io.github.pigaut.voxel.message.type;

import io.github.pigaut.voxel.message.*;
import io.github.pigaut.voxel.meta.placeholder.*;
import io.github.pigaut.voxel.plugin.*;
import org.bukkit.entity.*;
import org.bukkit.scheduler.*;

import java.util.*;

public class AnimatedTitleMessage implements Message {

    private final EnhancedPlugin plugin;
    private final List<Title> frames;
    private final int frequency;

    public AnimatedTitleMessage(EnhancedPlugin plugin, List<Title> frames, int frequency) {
        this.plugin = plugin;
        this.frames = frames;
        this.frequency = frequency;
    }

    @Override
    public void send(Player player) {
        new BukkitRunnable() {
            int loop = 0;
            int pause = 0;

            @Override
            public void run() {
                if (pause > 0) {
                    pause -= frequency;
                    return;
                }

                if (loop >= frames.size()) {
                    cancel();
                    return;
                }

                Title title = frames.get(loop);

                if (title.stay() > frequency) {
                    pause = title.stay();
                }

                player.sendTitle(title.title(), title.subtitle(), title.fadeIn(), title.stay(), title.fadeOut());
                loop++;
            }

        }.runTaskTimer(plugin, frequency, frequency);
    }

    @Override
    public void send(Player player, PlaceholderSupplier... placeholderSuppliers) {

    }

}
