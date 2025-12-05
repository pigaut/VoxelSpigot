package io.github.pigaut.voxel.player.input;

import io.github.pigaut.voxel.menu.*;
import io.github.pigaut.voxel.menu.template.button.*;
import io.github.pigaut.voxel.menu.template.menu.*;
import io.github.pigaut.voxel.player.*;
import io.github.pigaut.yaml.convert.parse.*;
import io.github.pigaut.yaml.util.*;
import org.jetbrains.annotations.*;

import java.util.*;
import java.util.function.*;

public class MenuSelection<T> extends GenericInputCollector<T> {

    private String description = "Select a value";
    private int size = 45;
    private List<ValueInputButton> valueEntries = new ArrayList<>();

    public MenuSelection(PlayerState player, Parser<T> parser) {
        super(player, parser);
    }

    @Override
    public @NotNull InputSource getInputSource() {
        return InputSource.MENU;
    }

    @Override
    public void onStart() {
        PlayerState playerState = getPlayerState();
        playerState.openMenu(new ValueSelectionMenu(description, size, valueEntries));
    }

    @Override
    public MenuSelection<T> onInput(@NotNull Consumer<T> inputCollector) {
        return (MenuSelection<T>) super.onInput(inputCollector);
    }

    @Override
    public MenuSelection<T> onCancel(@NotNull Runnable cancelAction) {
        return (MenuSelection<T>) super.onCancel(cancelAction);
    }

    public MenuSelection<T> description(@NotNull String description) {
        this.description = description;
        return this;
    }

    public MenuSelection<T> size(int size) {
        Preconditions.checkArgument(MenuSize.isValid(size), "Invalid menu size.");
        this.size = size;
        return this;
    }

    public MenuSelection<T> withValues(@NotNull List<ValueInputButton> entries) {
        Parser<T> parser = getInputParser();
        for (ValueInputButton entry : entries) {
            try {
                parser.parse(entry.getValue());
            } catch (StringParseException e) {
                throw new IllegalStateException("Invalid menu input selection value. " + e.getMessage());
            }
        }
        this.valueEntries = entries;
        return this;
    }

    public MenuSelection<T> addValue(@NotNull ValueInputButton entry) {
        Parser<T> parser = getInputParser();
        try {
            parser.parse(entry.getValue());
        } catch (StringParseException e) {
            throw new IllegalStateException("Invalid menu input selection value. " + e.getMessage());
        }
        this.valueEntries.add(entry);
        return this;
    }

}
