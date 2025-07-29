package io.github.pigaut.voxel.menu.template.button;

import io.github.pigaut.voxel.menu.button.*;
import org.bukkit.*;

public class Buttons {

    public static final Button BACK = new BackButton();

    public static final Button MAIN_MENU = new MainMenuButton();

    public static final Button GRAY_PANEL = Button.builder()
            .withType(Material.GRAY_STAINED_GLASS_PANE)
            .withDisplay(" ")
            .buildButton();


}
