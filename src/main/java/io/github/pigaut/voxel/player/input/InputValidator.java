package io.github.pigaut.voxel.player.input;

import org.jetbrains.annotations.*;

public interface InputValidator {
    /**
     * Validates the given input string.
     *
     * @param input the string to validate
     * @return {@code null} if the input is valid, or a non-null string describing
     *         the validation error otherwise
     */
    @Nullable String validate(@NotNull String input);

}