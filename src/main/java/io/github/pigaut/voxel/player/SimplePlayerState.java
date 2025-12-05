package io.github.pigaut.voxel.player;

import io.github.pigaut.voxel.bukkit.*;
import io.github.pigaut.voxel.core.message.*;
import io.github.pigaut.voxel.menu.*;
import io.github.pigaut.voxel.placeholder.*;
import io.github.pigaut.voxel.player.input.*;
import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.voxel.util.collection.*;
import io.github.pigaut.yaml.convert.parse.*;
import io.github.pigaut.yaml.util.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;
import org.jetbrains.annotations.*;

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

    private @Nullable InputCollector inputCollector = null;

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
    public void sendTitle(String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        Preconditions.checkArgument(fadeIn <= Short.MAX_VALUE, "Title fade in amount is too large.");
        Preconditions.checkArgument(stay <= Short.MAX_VALUE, "Title stay amount is too large.");
        Preconditions.checkArgument(fadeOut <= Short.MAX_VALUE, "Title fade out amount is too large.");
        asPlayer().sendTitle(title, subtitle, fadeIn, stay, fadeOut);
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
        final MenuView view = menu.createView(this, previousView);
        view.open();
        return view;
    }

    @Override
    public @Nullable MenuView getOpenMenu() {
        return openMenu;
    }

    @Override
    public void setOpenMenu(@Nullable MenuView view) {
        this.openMenu = view;
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
    public @NotNull ItemStack getTool() {
        return getInventory().getItemInMainHand();
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
    public void addTemporaryFlag(String flag, int ticks) {
        flags.add(flag);
        plugin.getScheduler().runTaskLater(ticks, () -> flags.remove(flag));
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
        return inputCollector != null && inputCollector.isCollecting();
    }

    @Override
    public boolean isAwaitingInput(@NotNull InputSource inputSource) {
        return isAwaitingInput() && inputCollector.getInputSource() == inputSource;
    }

    @Override
    public void submitInput(@NotNull String input) {
        Preconditions.checkState(isAwaitingInput(), "Player is currently not being prompted for input.");
        inputCollector.accept(input);
        if (!inputCollector.isCollecting()) {
            inputCollector = null;
        }
    }

    @Override
    public void cancelInputCollection() {
        if (inputCollector != null) {
            if (inputCollector.isCollecting()) {
                inputCollector.cancel();
            }
            this.inputCollector = null;
        }
    }

    private void registerInputCollector(@NotNull InputCollector inputCollector) {
        if (isAwaitingInput()) {
            this.inputCollector.cancel();
        }
        this.inputCollector = inputCollector;
    }

    @Override
    public ChatInput<String> collectChatInput() {
        ChatInput<String> chatInput = new ChatInput<>(plugin, this, Parsers.STRING);
        registerInputCollector(chatInput);
        return chatInput;
    }

    @Override
    public <T> ChatInput<T> collectChatInput(@NotNull Class<T> classType) {
        ChatInput<T> chatInput = new ChatInput<>(plugin,this, Parsers.getByType(classType));
        registerInputCollector(chatInput);
        return chatInput;
    }

    @Override
    public <T> ChatInput<T> collectChatInput(@NotNull Parser<T> parser) {
        ChatInput<T> chatInput = new ChatInput<>(plugin,this, parser);
        registerInputCollector(chatInput);
        return chatInput;
    }

    @Override
    public MenuSelection<String> collectMenuSelection() {
        MenuSelection<String> menuSelection = new MenuSelection<>(this, Parsers.STRING);
        registerInputCollector(menuSelection);
        return menuSelection;
    }

    @Override
    public <T> MenuSelection<T> collectMenuSelection(@NotNull Class<T> classType) {
        MenuSelection<T> menuSelection = new MenuSelection<>(this, Parsers.getByType(classType));
        registerInputCollector(menuSelection);
        return menuSelection;
    }

    @Override
    public <T> MenuSelection<T> collectMenuSelection(@NotNull Parser<T> parser) {
        MenuSelection<T> menuSelection = new MenuSelection<>(this, parser);
        registerInputCollector(menuSelection);
        return menuSelection;
    }

}
