package io.github.pigaut.voxel.player;

import io.github.pigaut.voxel.*;
import io.github.pigaut.voxel.message.*;
import io.github.pigaut.voxel.meta.flag.*;
import io.github.pigaut.voxel.meta.placeholder.*;
import io.github.pigaut.voxel.util.*;
import io.github.pigaut.voxel.util.collection.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class AbstractPluginPlayer implements PluginPlayer {

    protected final UUID playerId;
    private final FlagMap flags = new FlagMap();
    private final TypeMap<String, Object> playerCache = new TypeMap<>();
    private @Nullable PlaceholderSupplier[] placeholders = null;

    public AbstractPluginPlayer(UUID playerId) {
        this.playerId = playerId;
    }

    public AbstractPluginPlayer(Player player) {
        this.playerId = player.getUniqueId();
    }

    @Override
    public UUID getUniqueId() {
        return playerId;
    }

    @Override
    public String getName() {
        return asPlayer().getName();
    }

    @Override
    public Player asPlayer() {
        return Bukkit.getPlayer(playerId);
    }

    @Override
    public OfflinePlayer asOfflinePlayer() {
        return Bukkit.getOfflinePlayer(playerId);
    }

    @Override
    public boolean hasPermission(String permission) {
        return asPlayer().hasPermission(permission);
    }

    @Override
    public void sendRawMessage(String message) {
        asPlayer().sendMessage(message);
    }

    @Override
    public void sendMessage(String message) {
        Chat.send(asPlayer(), message);
    }

    @Override
    public void sendMessage(String message, PlaceholderSupplier... placeholderSuppliers) {
        Chat.send(asPlayer(), message, placeholderSuppliers);
    }

    @Override
    public void sendMessage(Message message) {
        if (placeholders != null) {
            message.send(asPlayer(), placeholders);
        }
        else {
            message.send(asPlayer());
        }
    }

    @Override
    public void sendTitle(Title title) {
        title.send(asPlayer());
    }

    @Override
    public InventoryView openInventory(Inventory inventory) {
        return asPlayer().openInventory(inventory);
    }

    @Override
    public void closeInventory() {
        asPlayer().closeInventory();
    }

    @Override
    public void openEnderChest() {
        Player player = asPlayer();
        player.openInventory(player.getEnderChest());
    }

    @Override
    public void setExp(int amount) {
        asPlayer().setExp(amount);
    }

    @Override
    public void addExp(int amount) {
        Player player = asPlayer();
        player.setExp(player.getExp() + amount);
    }

    @Override
    public void removeExp(int amount) {
        addExp(-amount);
    }

    @Override
    public void teleport(Location location) {
        asPlayer().teleport(location, PlayerTeleportEvent.TeleportCause.PLUGIN);
    }

    @Override
    public void heal(int amount) {
        Player player = asPlayer();
        player.setHealth(player.getHealth() + amount);
    }

    @Override
    public void damage(int amount) {
        heal(-amount);
    }

    @Override
    public World getWorld() {
        return asPlayer().getWorld();
    }

    @Override
    public Location getLocation() {
        return asPlayer().getLocation();
    }

    @Override
    public PlayerInventory getInventory() {
        return asPlayer().getInventory();
    }

    @Override
    public void performCommand(String command) {
        asPlayer().performCommand(command);
    }

    @Override
    public void performCommandAsOp(String command) {
        Player player = asPlayer();

        player.setOp(true);
        player.performCommand(command);
        player.setOp(false);
    }

    @Override
    public ItemStack getPlayerHead() {
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1);
        final SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        skullMeta.setOwningPlayer(asPlayer());
        skull.setItemMeta(skullMeta);
        return skull;
    }

    @Override
    public void giveItem(ItemStack... items) {
        asPlayer().getInventory().addItem(items);
    }

    @Override
    public void giveItemOrDrop(ItemStack... items) {
        PlayerUtil.giveItemsOrDrop(asPlayer(), items);
    }

    @Override
    public void updateInventory() {
        asPlayer().updateInventory();
    }

    @Override
    public Collection<String> getFlags() {
        return flags.names();
    }

    @Override
    public void addFlag(Flag flag) {
        flags.add(flag);
    }

    @Override
    public void removeFlag(Flag flag) {
        flags.removeFlag(flag);
    }

    @Override
    public void removeFlag(String name) {
        flags.removeFlag(name);
    }

    @Override
    public boolean hasFlag(String name) {
        return flags.contains(name);
    }

    @Override
    public boolean hasFlag(Flag flag) {
        return flags.contains(flag);
    }

    public <T> T getCache(String id, Class<T> type) {
        return playerCache.get(id, type);
    }

    @Override
    public void saveCache(String id, Object value) {
        playerCache.put(id, value.getClass(), value);
    }

    @Override
    public void flushCache(String id, Class<?> type) {
        playerCache.remove(id, type);
    }

    @Override
    public @Nullable PlaceholderSupplier[] getPlaceholders() {
        return placeholders;
    }

    @Override
    public void updatePlaceholders(@Nullable PlaceholderSupplier... placeholderSuppliers) {
        this.placeholders = placeholderSuppliers;
    }

}
