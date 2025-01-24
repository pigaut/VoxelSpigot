package io.github.pigaut.voxel.message.type;

import io.github.pigaut.voxel.message.*;
import io.github.pigaut.voxel.meta.placeholder.*;
import io.github.pigaut.voxel.plugin.*;
import org.bukkit.*;
import org.bukkit.boss.*;
import org.bukkit.entity.*;
import org.bukkit.plugin.*;
import org.bukkit.scheduler.*;

import java.util.*;

public class BossBarMessage implements Message {

	private final EnhancedPlugin plugin;

	private final String title;
	private final BarColor color;
	private final BarStyle style;
	private final int duration;
	private final int intervals;
	private final int frequency;
	private final List<Double> progress;

	public BossBarMessage(EnhancedPlugin plugin, String title, BarStyle style, BarColor color, int duration, List<Double> progress) {
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
	public void send(Player player, PlaceholderSupplier... placeholderSuppliers) {
		final String parsedTitle = StringPlaceholders.parseAll(player, title, placeholderSuppliers);
		BossBar bossBar = Bukkit.getServer().createBossBar(parsedTitle, color, style);

		if (intervals != -1) {
			new BukkitRunnable() {
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
			}.runTaskTimer(plugin, 0, frequency);
		}
		else {
			plugin.getScheduler().runTaskLater(duration, () -> bossBar.removePlayer(player));
		}

		bossBar.addPlayer(player);
	}

}
