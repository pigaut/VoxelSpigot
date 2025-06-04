package io.github.pigaut.voxel.menu.template.menu;

import io.github.pigaut.voxel.menu.*;
import io.github.pigaut.voxel.menu.button.*;
import io.github.pigaut.voxel.menu.paged.*;
import io.github.pigaut.voxel.menu.template.button.*;
import io.github.pigaut.voxel.plugin.*;
import org.jetbrains.annotations.*;

public class FramedSelectionMenu extends PagedMenu {

    private final Button[] buttons;

    public FramedSelectionMenu(String title, int size) {
        this(null, title, size);
    }

    public FramedSelectionMenu(@Nullable EnhancedPlugin plugin, String title, int size) {
        super(plugin, title, size);
        this.buttons = new Button[size];
        switch (size) {
            case MenuSize.SMALL -> {
                addEntrySlots(ButtonLayout.SMALL_BOX);
                ButtonLayout.apply(buttons, Buttons.GRAY_PANEL, ButtonLayout.SMALL_FRAME);
                buttons[22] = Buttons.BACK;
            }

            case MenuSize.MEDIUM -> {
                addEntrySlots(ButtonLayout.MEDIUM_BOX);
                ButtonLayout.apply(buttons, Buttons.GRAY_PANEL, ButtonLayout.MEDIUM_FRAME);
                buttons[31] = Buttons.BACK;
            }

            case MenuSize.BIG -> {
                addEntrySlots(ButtonLayout.BIG_BOX);
                ButtonLayout.apply(buttons, Buttons.GRAY_PANEL, ButtonLayout.BIG_FRAME);
                buttons[40] = Buttons.BACK;
            }

            case MenuSize.LARGE -> {
                addEntrySlots(ButtonLayout.LARGE_BOX);
                ButtonLayout.apply(buttons, Buttons.GRAY_PANEL, ButtonLayout.LARGE_FRAME);
                buttons[49] = Buttons.BACK;
            }
        }
    }

    @Override
    public @Nullable Button[] createButtons() {
        return buttons.clone();
    }

}
