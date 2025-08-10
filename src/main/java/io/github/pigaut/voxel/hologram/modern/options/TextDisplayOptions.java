package io.github.pigaut.voxel.hologram.modern.options;

import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.loader.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.jetbrains.annotations.*;

import java.util.*;

public final class TextDisplayOptions extends DisplayOptions {

    private final int lineWidth;
    private final Color backgroundColor;
    private final byte opacity;
    private final boolean shadowed;
    private final boolean seeThrough;
    private final boolean defaultBackground;
    private final TextDisplay.TextAlignment alignment;

    public TextDisplayOptions(
            Display.Billboard billboard,
            int lineWidth,
            Color backgroundColor,
            byte opacity,
            boolean shadowed,
            boolean seeThrough,
            boolean defaultBackground,
            TextDisplay.TextAlignment alignment) {
        super(billboard);
        this.lineWidth = lineWidth;
        this.backgroundColor = backgroundColor;
        this.opacity = opacity;
        this.shadowed = shadowed;
        this.seeThrough = seeThrough;
        this.defaultBackground = defaultBackground;
        this.alignment = alignment;
    }

    public TextDisplayOptions() {
        this.lineWidth = 200;
        this.backgroundColor = Color.fromRGB(0, 0, 0);
        this.opacity = (byte) -1;
        this.shadowed = false;
        this.seeThrough = false;
        this.defaultBackground = true;
        this.alignment = TextDisplay.TextAlignment.CENTER;
    }

    public void apply(TextDisplay display) {
        display.setBillboard(billboard);
        display.setLineWidth(lineWidth);
        display.setBackgroundColor(backgroundColor);
        display.setTextOpacity(opacity);
        display.setShadowed(shadowed);
        display.setSeeThrough(seeThrough);
        display.setDefaultBackground(defaultBackground);
        display.setAlignment(alignment);
    }

    public static class Loader implements ConfigLoader<TextDisplayOptions> {

        @Override
        public @Nullable String getProblemDescription() {
            return "invalid text display options";
        }

        @Override
        public @NotNull TextDisplayOptions loadFromSection(@NotNull ConfigSection section) throws InvalidConfigurationException {

            final Display.Billboard billboard = section.getOptional("billboard", Display.Billboard.class).orElse(Display.Billboard.CENTER);
            final int lineWidth = section.getOptionalInteger("line-width|width").orElse(200);
            final Color backgroundColor = section.getOptional("color|background-color", Color.class).orElse(Color.fromRGB(0x40000000));
            final byte opacity = section.getOptionalInteger("opacity").map(Integer::byteValue).orElse((byte) 255);
            final boolean shadowed = section.getOptionalBoolean("shadowed").orElse(false);
            final boolean seeThrough = section.getOptionalBoolean("see-through").orElse(false);
            final boolean defaultBackground = section.getOptionalBoolean("default-background").orElse(false);
            final TextDisplay.TextAlignment alignment = section.getOptional("alignment", TextDisplay.TextAlignment.class)
                    .orElse(TextDisplay.TextAlignment.CENTER);

            return new TextDisplayOptions(billboard, lineWidth, backgroundColor, opacity, shadowed, seeThrough, defaultBackground, alignment);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (TextDisplayOptions) obj;
        return Objects.equals(this.billboard, that.billboard) &&
                this.lineWidth == that.lineWidth &&
                Objects.equals(this.backgroundColor, that.backgroundColor) &&
                this.opacity == that.opacity &&
                this.shadowed == that.shadowed &&
                this.seeThrough == that.seeThrough &&
                this.defaultBackground == that.defaultBackground &&
                Objects.equals(this.alignment, that.alignment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(billboard, lineWidth, backgroundColor, opacity, shadowed, seeThrough, defaultBackground, alignment);
    }

    @Override
    public String toString() {
        return "TextDisplayOptions[" +
                "billboard=" + billboard + ", " +
                "lineWidth=" + lineWidth + ", " +
                "backgroundColor=" + backgroundColor + ", " +
                "opacity=" + opacity + ", " +
                "shadowed=" + shadowed + ", " +
                "seeThrough=" + seeThrough + ", " +
                "defaultBackground=" + defaultBackground + ", " +
                "alignment=" + alignment + ']';
    }

}
