package io.github.pigaut.voxel.menu.template.menu;

import io.github.pigaut.voxel.menu.*;
import io.github.pigaut.voxel.menu.button.*;
import io.github.pigaut.voxel.menu.fixed.*;
import io.github.pigaut.voxel.menu.template.button.*;
import io.github.pigaut.voxel.plugin.*;
import org.jetbrains.annotations.*;

public class FramedMenu extends FixedMenu {

    private final Button[] buttons;

    public FramedMenu(String title, int size) {
        this(null, title, size);
    }

    public FramedMenu(@Nullable EnhancedPlugin plugin, String title, int size) {
        super(plugin, title, size);
        this.buttons = new Button[size];
        switch (size) {
            case MenuSize.SMALL -> {
                ButtonLayout.apply(buttons, Buttons.GRAY_PANEL, ButtonLayout.SMALL_FRAME);
                buttons[22] = Buttons.BACK;
            }

            case MenuSize.MEDIUM -> {
                ButtonLayout.apply(buttons, Buttons.GRAY_PANEL, ButtonLayout.MEDIUM_FRAME);
                buttons[31] = Buttons.BACK;
            }

            case MenuSize.BIG -> {
                ButtonLayout.apply(buttons, Buttons.GRAY_PANEL, ButtonLayout.BIG_FRAME);
                buttons[40] = Buttons.BACK;
            }

            case MenuSize.LARGE -> {
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
