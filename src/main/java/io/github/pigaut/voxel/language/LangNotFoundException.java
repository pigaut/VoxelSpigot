package io.github.pigaut.voxel.language;

import java.util.*;

public class LangNotFoundException extends RuntimeException {

    public LangNotFoundException(Locale locale, String id) {
        super("Message with id: " + id + " not found for language: " + locale + ". Please fix your language file.");
    }

}
