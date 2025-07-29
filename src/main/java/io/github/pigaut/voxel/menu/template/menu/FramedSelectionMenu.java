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
        final Button frameButton = getFrameButton();

        int toolbarStart;
        switch (size) {
            case MenuSize.SMALL -> {
                addEntrySlots(ButtonLayout.SMALL_BOX);
                ButtonLayout.apply(buttons, frameButton, ButtonLayout.SMALL_FRAME);
                toolbarStart = 18;
            }

            case MenuSize.MEDIUM -> {
                addEntrySlots(ButtonLayout.MEDIUM_BOX);
                ButtonLayout.apply(buttons, frameButton, ButtonLayout.MEDIUM_FRAME);
                toolbarStart = 27;
            }

            case MenuSize.BIG -> {
                addEntrySlots(ButtonLayout.BIG_BOX);
                ButtonLayout.apply(buttons, frameButton, ButtonLayout.BIG_FRAME);
                toolbarStart = 36;
            }

            case MenuSize.LARGE -> {
                addEntrySlots(ButtonLayout.LARGE_BOX);
                ButtonLayout.apply(buttons, frameButton, ButtonLayout.LARGE_FRAME);
                toolbarStart = 45;
            }

            default -> throw new IllegalArgumentException("Invalid size for framed selection menu.");
        }

        buttons[toolbarStart++] = getToolbarButton1();
        buttons[toolbarStart++] = getToolbarButton2();
        buttons[toolbarStart++] = getToolbarButton3();
        buttons[toolbarStart++] = getToolbarButton4();
        buttons[toolbarStart++] = getToolbarButton5();
        buttons[toolbarStart++] = getToolbarButton6();
        buttons[toolbarStart++] = getToolbarButton7();
        buttons[toolbarStart++] = getToolbarButton8();
        buttons[toolbarStart] = getToolbarButton9();

    }

    public Button getFrameButton() {
        return Buttons.GRAY_PANEL;
    }

    public Button getToolbarButton1() {
        return Buttons.GRAY_PANEL;
    }

    public Button getToolbarButton2() {
        return Buttons.GRAY_PANEL;
    }

    public Button getToolbarButton3() {
        return Buttons.GRAY_PANEL;
    }

    public Button getToolbarButton4() {
        return Buttons.MAIN_MENU;
    }

    public Button getToolbarButton5() {
        return Buttons.BACK;
    }

    public Button getToolbarButton6() {
        return Buttons.GRAY_PANEL;
    }

    public Button getToolbarButton7() {
        return Buttons.GRAY_PANEL;
    }

    public Button getToolbarButton8() {
        return Buttons.GRAY_PANEL;
    }

    public Button getToolbarButton9() {
        return Buttons.GRAY_PANEL;
    }

    @Override
    public Button[] createButtons() {
        return buttons.clone();
    }

}
