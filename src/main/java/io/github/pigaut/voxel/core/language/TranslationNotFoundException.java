package io.github.pigaut.voxel.core.language;

import java.util.*;

public class TranslationNotFoundException extends RuntimeException {

    public TranslationNotFoundException(Locale locale, String id) {
        super("Message with id: " + id + " not found for language: " + locale + ". Please fix your language file.");
    }

}
