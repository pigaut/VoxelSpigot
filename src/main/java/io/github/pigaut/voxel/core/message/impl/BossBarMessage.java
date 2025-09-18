package io.github.pigaut.voxel.core.message.impl;

import io.github.pigaut.voxel.core.message.*;
import io.github.pigaut.voxel.menu.button.*;
import io.github.pigaut.voxel.placeholder.*;
import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.voxel.plugin.runnable.*;
import io.github.pigaut.yaml.*;
import org.bukkit.*;
import org.bukkit.boss.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class BossBarMessage extends GenericMessage {

	private final EnhancedPlugin plugin;

	private final String title;
	private final BarColor color;
	private final BarStyle style;
	private final int duration;
	private final int intervals;
	private final int frequency;
	private final List<Double> progress;

	public BossBarMessage(EnhancedPlugin plugin, String name, @Nullable String group,
						  String title, BarStyle style, BarColor color, int duration, List<Double> progress) {
        super(name, group);
		this.plugin = plugin;
        this.title = title;
		this.color = color;
		this.style = style;
		this.duration = duration;
		this.intervals = progress.size();
		this.frequency = intervals == 0 ? -1 : duration/progress.size();
		this.progress = progress;
	}

	@Override
	public @NotNull MessageType getType() {
		return MessageType.BOSSBAR;
	}

	@Override
	public @NotNull ItemStack getIcon() {
		return IconBuilder.of(Material.DRAGON_HEAD).buildIcon();
	}

	@Override
	public void send(@NotNull Player player, PlaceholderSupplier... placeholderSuppliers) {
		final String parsedTitle = StringPlaceholders.parseAll(player, title, placeholderSuppliers);
		BossBar bossBar = Bukkit.getServer().createBossBar(parsedTitle, color, style);

		if (frequency != -1) {
			new PluginRunnable(plugin) {
				int interval = 0;

				@Override
				public void run() {
					if (interval >= intervals) {
						bossBar.removePlayer(player);
						cancel();
						return;
					}

					bossBar.setProgress(progress.get(interval));
					interval++;
				}
			}.runTaskTimer(0, frequency);
		}
		else {
			plugin.getScheduler().runTaskLater(duration, () -> {
				bossBar.removePlayer(player);
			});
		}

		bossBar.addPlayer(player);
	}

}
