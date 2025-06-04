package io.github.pigaut.voxel.player;

import io.github.pigaut.voxel.bukkit.*;
import io.github.pigaut.voxel.core.message.*;
import io.github.pigaut.voxel.menu.*;
import io.github.pigaut.voxel.placeholder.*;
import io.github.pigaut.voxel.player.input.*;
import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.voxel.util.*;
import io.github.pigaut.voxel.util.collection.*;
import io.github.pigaut.yaml.util.ConfigurationException;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;
import org.jetbrains.annotations.*;

import javax.naming.*;
import java.util.*;

public class SimplePlayerState implements PlayerState {

    private final EnhancedPlugin plugin;
    private final UUID playerId;
    private final String playerName;
    private final Set<String> flags = new HashSet<>();
    private final TypeMap<String, Object> playerCache = new TypeMap<>();
    private @NotNull PlaceholderSupplier[] placeholders = PlaceholderSupplier.EMPTY;
    private @Nullable MenuView openMenu = null;

    private @Nullable Location firstSelection = null;
    private @Nullable Location secondSelection = null;

    private @Nullable InputType awaitingInput = null;
    private @Nullable String lastInput = null;

    public SimplePlayerState(@NotNull EnhancedPlugin plugin, @NotNull Player player) {
        this.plugin = plugin;
        this.playerId = player.getUniqueId();
        this.playerName = player.getName();
    }

    @Override
    public @NotNull EnhancedPlugin getPlugin() {
        return plugin;
    }

    @Override
    public UUID getUniqueId() {
        return playerId;
    }

    @Override
    public String getName() {
        return playerName;
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
    public @NotNull MenuView openMenu(@NotNull Menu menu) {
        return openMenu(menu, openMenu);
    }

    @Override
    public @NotNull MenuView openMenu(Menu menu, MenuView previousView) {
        final MenuView menuView = menu.createView(this, previousView);
        this.setOpenView(menuView);
        return menuView;
    }

    @Override
    public @Nullable MenuView getOpenMenu() {
        return openMenu;
    }

    @Override
    public void setOpenView(@Nullable MenuView menuView) {
        if (openMenu != null) {
            if (menuView != null) {
                openMenu.getMenu().onClose(menuView);
            }
            openMenu = null;
        }

        if (menuView == null) {
            return;
        }

        menuView.update();
        menuView.getMenu().onOpen(menuView);
        this.openInventory(menuView.getInventory());
        this.openMenu = menuView;
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
        final Player player = asPlayer();
        player.performCommand(StringPlaceholders.parseAll(player, command, placeholders));
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
        return new ArrayList<>(flags);
    }

    @Override
    public void addFlag(String flag) {
        flags.add(flag);
    }

    @Override
    public void removeFlag(String flag) {
        flags.remove(flag);
    }

    @Override
    public boolean hasFlag(String flag) {
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
    public @NotNull PlaceholderSupplier[] getPlaceholders() {
        return placeholders;
    }

    @Override
    public void updatePlaceholders(@NotNull PlaceholderSupplier... placeholderSuppliers) {
        this.placeholders = placeholderSuppliers;
    }

    @Override
    public void clearPlaceholders() {
        placeholders = PlaceholderSupplier.EMPTY;
    }

    @Override
    public @Nullable Location getFirstSelection() {
        return firstSelection;
    }

    @Override
    public void setFirstSelection(@Nullable Location location) {
        this.firstSelection = location;
    }

    @Override
    public @Nullable Location getSecondSelection() {
        return secondSelection;
    }

    @Override
    public void setSecondSelection(@Nullable Location location) {
        this.secondSelection = location;
    }

    @Override
    public boolean isAwaitingInput() {
        return awaitingInput != null;
    }

    @Override
    public boolean isAwaitingInput(@NotNull InputType inputType) {
        return awaitingInput == inputType;
    }

    @Override
    public void setAwaitingInput(@Nullable InputType inputType) {
        this.awaitingInput = inputType;
    }

    @Override
    public @Nullable String getLastInput() {
        return lastInput;
    }

    @Override
    public void setLastInput(@Nullable String input) {
        this.lastInput = input;
    }

    @Override
    public ChatInput createChatInput() {
        return new ChatInput(this);
    }

    @Override
    public MenuInput createMenuSelector() {
        return new MenuInput(this);
    }

}
