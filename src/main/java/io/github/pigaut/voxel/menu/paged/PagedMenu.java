package io.github.pigaut.voxel.menu.paged;

import io.github.pigaut.voxel.menu.*;
import io.github.pigaut.voxel.menu.button.*;
import io.github.pigaut.voxel.menu.fixed.*;
import io.github.pigaut.voxel.player.*;
import io.github.pigaut.voxel.plugin.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class PagedMenu extends FixedMenu {

    private final List<Integer> entrySlots = new ArrayList<>();

    public PagedMenu(String title, int size) {
        this(null, title, size);
    }

    public PagedMenu(@Nullable EnhancedPlugin plugin, String title, int size) {
        super(plugin, title, size);
    }

    public List<Button> createEntries() {
        return List.of();
    }

    public List<Integer> getEntrySlots() {
        return new ArrayList<>(entrySlots);
    }

    public Integer getEntrySlot(int index) {
        return entrySlots.get(index);
    }

    public void addEntrySlots(int... slots) {
        for (int slot : slots) {
            if (!entrySlots.contains(slot)) {
                entrySlots.add(slot);
            }
        }
    }

    public int getEntriesPerPage() {
        return entrySlots.size();
    }

    public void onPageTurn(PagedMenuView menuView) {}

    @Override
    public @NotNull MenuView createView(PlayerState player, MenuView previousView) {
        return new PagedMenuView(this, player, previousView);
    }

}
