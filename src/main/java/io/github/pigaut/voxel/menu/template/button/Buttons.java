package io.github.pigaut.voxel.menu.template.button;

import io.github.pigaut.voxel.menu.*;
import io.github.pigaut.voxel.menu.button.*;
import org.bukkit.*;

public class Buttons {

    public static final Button WHITE_PANEL = Button.builder()
            .withType(Material.WHITE_STAINED_GLASS_PANE)
            .withDisplay(" ")
            .buildButton();

    public static final Button ORANGE_PANEL = Button.builder()
            .withType(Material.ORANGE_STAINED_GLASS_PANE)
            .withDisplay(" ")
            .buildButton();

    public static final Button MAGENTA_PANEL = Button.builder()
            .withType(Material.MAGENTA_STAINED_GLASS_PANE)
            .withDisplay(" ")
            .buildButton();

    public static final Button LIGHT_BLUE_PANEL = Button.builder()
            .withType(Material.LIGHT_BLUE_STAINED_GLASS_PANE)
            .withDisplay(" ")
            .buildButton();

    public static final Button YELLOW_PANEL = Button.builder()
            .withType(Material.YELLOW_STAINED_GLASS_PANE)
            .withDisplay(" ")
            .buildButton();

    public static final Button LIME_PANEL = Button.builder()
            .withType(Material.LIME_STAINED_GLASS_PANE)
            .withDisplay(" ")
            .buildButton();

    public static final Button PINK_PANEL = Button.builder()
            .withType(Material.PINK_STAINED_GLASS_PANE)
            .withDisplay(" ")
            .buildButton();

    public static final Button GRAY_PANEL = Button.builder()
            .withType(Material.GRAY_STAINED_GLASS_PANE)
            .withDisplay(" ")
            .buildButton();

    public static final Button LIGHT_GRAY_PANEL = Button.builder()
            .withType(Material.LIGHT_GRAY_STAINED_GLASS_PANE)
            .withDisplay(" ")
            .buildButton();

    public static final Button CYAN_PANEL = Button.builder()
            .withType(Material.CYAN_STAINED_GLASS_PANE)
            .withDisplay(" ")
            .buildButton();

    public static final Button PURPLE_PANEL = Button.builder()
            .withType(Material.PURPLE_STAINED_GLASS_PANE)
            .withDisplay(" ")
            .buildButton();

    public static final Button BLUE_PANEL = Button.builder()
            .withType(Material.BLUE_STAINED_GLASS_PANE)
            .withDisplay(" ")
            .buildButton();

    public static final Button BROWN_PANEL = Button.builder()
            .withType(Material.BROWN_STAINED_GLASS_PANE)
            .withDisplay(" ")
            .buildButton();

    public static final Button GREEN_PANEL = Button.builder()
            .withType(Material.GREEN_STAINED_GLASS_PANE)
            .withDisplay(" ")
            .buildButton();

    public static final Button RED_PANEL = Button.builder()
            .withType(Material.RED_STAINED_GLASS_PANE)
            .withDisplay(" ")
            .buildButton();

    public static final Button BLACK_PANEL = Button.builder()
            .withType(Material.BLACK_STAINED_GLASS_PANE)
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
