package io.github.pigaut.voxel.menu.template.button;

import io.github.pigaut.voxel.menu.*;
import io.github.pigaut.voxel.menu.button.*;
import org.bukkit.*;

public class Buttons {

    public static final Button GRAY_PANEL = Button.builder()
            .withType(Material.GRAY_STAINED_GLASS_PANE)
            .withDisplay(" ")
            .buildButton();

    public static final Button CLOSE = Button.builder()
            .withType(Material.RED_WOOL)
            .withDisplay("&4Close")
            .enchanted(true)
            .onLeftClick((view, playerState, event) -> view.close())
            .buildButton();

    public static final Button BACK = Button.builder()
            .withType(Material.SPRUCE_DOOR)
            .withDisplay("&cBack")
            .enchanted(true)
            .onLeftClick((view, playerState, event) -> {
                final MenuView previousView = view.getPreviousView();
                if (previousView != null) {
                    previousView.open();
                }
            })
            .buildButton();

    public static final Button MAIN_MENU = Button.builder()
            .withType(Material.MINECART)
            .withDisplay("&5Main Menu")
            .enchanted(true)
            .onLeftClick((view, playerState, event) -> {
                final MenuView firstView = view.getFirstView();
                if (view == firstView) {
                    return;
                }
                firstView.open();
            })
            .buildButton();

}
