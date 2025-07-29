package io.github.pigaut.voxel.player;

import io.github.pigaut.voxel.core.message.*;
import io.github.pigaut.voxel.menu.*;
import io.github.pigaut.voxel.placeholder.*;
import io.github.pigaut.voxel.player.input.*;
import io.github.pigaut.voxel.plugin.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.*;

import java.util.*;

public interface PlayerState {

    @NotNull
    EnhancedPlugin getPlugin();

    /**
     * Gets the unique identifier of the player.
     *
     * @return The unique identifier of the player.
     */
    UUID getUniqueId();

    String getName();

    /**
     * Converts the player to a Bukkit Player object.
     *
     * @return The Bukkit Player object.
     */
    Player asPlayer();

    /**
     * Converts the player to an offline player.
     *
     * @return The offline player representation.
     */
    OfflinePlayer asOfflinePlayer();

    /**
     * Checks if the player has a specific permission.
     *
     * @param permission The permission to check.
     * @return True, if the player has the permission, otherwise false.
     */
    boolean hasPermission(String permission);

    /**
     * Send a raw chat message to the player. Use Chat#send to parse colors and placeholders
     *
     * @param message The message to send
     */
    void sendRawMessage(String message);

    void sendMessage(String message);

    void sendMessage(String message, PlaceholderSupplier... placeholderSuppliers);

    void sendMessage(Message message);

    /**
     * Sends a title to the player.
     *
     * @param title The title to send.
     */
    void sendTitle(Title title);

    /**
     * Opens an inventory for the player.
     *
     * @param inventory The inventory to open.
     * @return The inventory view.
     */
    InventoryView openInventory(Inventory inventory);

    /**
     * Closes the currently open inventory for the player.
     */
    void closeInventory();

    @NotNull MenuView openMenu(Menu menu);

    @NotNull MenuView openMenu(Menu menu, MenuView previousView);

    @Nullable MenuView getOpenMenu();

    void setOpenMenu(@Nullable MenuView view);

    /**
     * Opens the player's ender chest inventory.
     */
    void openEnderChest();

    /**
     * Sets the experience level of the player.
     *
     * @param amount The amount of experience levels to set.
     */
    void setExp(int amount);

    /**
     * Adds experience levels to the player's current experience.
     *
     * @param amount The amount of experience levels to add.
     */
    void addExp(int amount);

    /**
     * Removes experience levels from the player's current experience.
     *
     * @param amount The amount of experience levels to remove.
     */
    void removeExp(int amount);

    /**
     * Teleports the player to a specific location.
     *
     * @param location The location to teleport the player to.
     */
    void teleport(Location location);

    /**
     * Heals the player by a specified amount.
     *
     * @param amount The amount to heal the player.
     */
    void heal(int amount);

    /**
     * Damages the player by a specified amount.
     *
     * @param amount The amount to damage the player.
     */
    void damage(int amount);

    /**
     * Gets the world the player is currently in.
     *
     * @return The world the player is in.
     */
    World getWorld();

    /**
     * Gets the location of the player.
     *
     * @return The location of the player.
     */
    Location getLocation();

    /**
     * Gets the player's inventory.
     *
     * @return The player's inventory.
     */
    PlayerInventory getInventory();

    /**
     * Executes a command as the player.
     *
     * @param command The command to execute.
     */
    void performCommand(String command);

    /**
     * Gets a player head item representing the player.
     *
     * @return The player head item.
     */
    ItemStack getPlayerHead();

    /**
     * Gives the player one or more items.
     *
     * @param items The items to give to the player.
     */
    void giveItem(ItemStack... items);

    void giveItemOrDrop(ItemStack... items);

    void updateInventory();

    /**
     * Gets the list of flags associated with the player.
     *
     * @return The list of flags.
     */
    Collection<String> getFlags();

    /**
     * Adds a flag to the player's set of flags.
     *
     * @param flag The flag to add.
     */
    void addFlag(String flag);

    /**
     * Removes a flag from the player's set of flags.
     *
     * @param flag The flag to remove.
     */
    void removeFlag(String flag);

    /**
     * Checks if the player has a specific flag.
     *
     * @param flag The name of the flag to check.
     * @return True if the player has the flag, otherwise false.
     */
    boolean hasFlag(String flag);

    <T> T getCache(String id, Class<T> type);

    void saveCache(String id, Object value);

    void flushCache(String id, Class<?> type);

    @NotNull PlaceholderSupplier[] getPlaceholders();

    void updatePlaceholders(@NotNull PlaceholderSupplier... placeholderSuppliers);

    void clearPlaceholders();

    @Nullable Location getFirstSelection();

    void setFirstSelection(@Nullable Location location);

    @Nullable Location getSecondSelection();

    void setSecondSelection(@Nullable Location location);

    boolean isAwaitingInput();

    boolean isAwaitingInput(InputType inputType);

    void setAwaitingInput(@Nullable InputType inputType);

    @Nullable String getLastInput();

    void setLastInput(@Nullable String input);

    ChatInput createChatInput();

    MenuInput createMenuSelector();

}
