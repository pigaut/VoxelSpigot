package io.github.pigaut.voxel.plugin.manager;

public class DuplicateElementException extends Exception {

    public DuplicateElementException(String elementName) {
        super("An element with the name '" + elementName + "' already exists.");
    }

}
