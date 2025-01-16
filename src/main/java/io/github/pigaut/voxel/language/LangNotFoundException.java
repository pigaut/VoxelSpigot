package io.github.pigaut.voxel.language;

import java.util.*;

public class LangNotFoundException extends RuntimeException {

    public LangNotFoundException(Locale locale, String id) {
        super("No message found for language '" + locale + "' with id '" + id + "'. Please fix your language file.");
    }

}
