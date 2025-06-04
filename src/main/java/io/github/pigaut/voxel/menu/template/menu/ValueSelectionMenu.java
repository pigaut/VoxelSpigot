package io.github.pigaut.voxel.menu.template.menu;

import io.github.pigaut.voxel.menu.button.*;
import io.github.pigaut.voxel.menu.template.button.*;

import java.util.*;

public class ValueSelectionMenu extends FramedSelectionMenu {

    private final List<ValueInputButton> values;

    public ValueSelectionMenu(String title, int size, List<ValueInputButton> values) {
        super(title, size);
        this.values = values;
    }

    @Override
    public List<Button> createEntries() {
        return new ArrayList<>(values);
    }

}
