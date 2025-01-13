package io.github.pigaut.voxel.player;

public class PluginPlayerManager extends PlayerManager<PluginPlayer> {

    public PluginPlayerManager() {
        super(AbstractPluginPlayer::new);
    }

}
