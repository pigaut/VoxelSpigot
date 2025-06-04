package io.github.pigaut.voxel.menu.paged;

import io.github.pigaut.voxel.menu.*;
import io.github.pigaut.voxel.menu.button.*;
import io.github.pigaut.voxel.menu.fixed.*;
import io.github.pigaut.voxel.player.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class PagedMenuView extends FixedMenuView {

    private final PagedMenu pagedMenu;
    private List<Button> entries;
    private int currentPage = 0;

    public PagedMenuView(@NotNull PagedMenu pagedMenu, @NotNull PlayerState viewer, MenuView previousView) {
        super(pagedMenu, viewer, previousView);
        this.pagedMenu = pagedMenu;
    }

    public boolean isFirstPage() {
        return currentPage == 0;
    }

    public boolean isLastPage() {
        return currentPage == this.getTotalPages() - 1;
    }

    public int getTotalPages() {
        final int entriesPerPage = pagedMenu.getEntriesPerPage();
        return (entries.size() + entriesPerPage - 1) / entriesPerPage;
    }

    public void nextPage() {
        this.setCurrentPage(currentPage + 1);
    }

    public void previousPage() {
        this.setCurrentPage(currentPage - 1);
    }

    public void setCurrentPage(int page) {
        if (page < 0 || page >= this.getTotalPages()) {
            return;
        }
        this.currentPage = page;
        this.update();
        pagedMenu.onPageTurn(this);
    }

    @Override
    public void update() {
        buttons = menu.createButtons();
        entries = pagedMenu.createEntries();
        final int entriesPerPage = pagedMenu.getEntriesPerPage();
        final int startIndex = currentPage * entriesPerPage;
        final int endIndex = Math.min(startIndex + entriesPerPage, entries.size());
        for (int i = startIndex, j = 0; i < endIndex; i++, j++) {
            buttons[pagedMenu.getEntrySlot(j)] = entries.get(i);
        }
        updateInventory();
    }

}
