package io.github.pigaut.voxel;

import io.github.pigaut.voxel.plugin.boot.*;
import io.github.pigaut.voxel.server.*;
import io.github.pigaut.voxel.version.*;

public class UnsupportedVersionException extends RuntimeException {

    public UnsupportedVersionException(EnhancedJavaPlugin plugin) {
        super(new StringBuilder()
                        .append("This plugin is not compatible with server version ")
                        .append(SpigotServer.getVersion())
                        .append(". Compatible versions: ")
                        .append(String.join(", ",
                                plugin.getCompatibleVersions()
                                        .stream()
                                        .map(SpigotVersion::toString)
                                        .toList()
                        )).toString());

    }

}
