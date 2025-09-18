package io.github.pigaut.voxel.plugin.manager;

import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.convert.format.*;
import org.jetbrains.annotations.*;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Group {

    public static String byItemFile(File file) {
        return byFile(file, "items", false);
    }

    public static String byMessageFile(File file) {
        return byFile(file, "messages", false);
    }

    public static String byParticleFile(File file) {
        return byFile(file, "particles", false);
    }

    public static String bySoundFile(File file) {
        return byFile(file, "sounds", false);
    }

    public static String byStructureFile(File file) {
        return byFile(file, "structures", true);
    }

    public static String byFunctionFile(File file) {
        return byFile(file, "functions", false);
    }

    @Nullable
    public static String byFile(File file, String parentFolder, boolean ignoreFileName) {
        return byPath(file.toPath(), parentFolder, ignoreFileName);
    }

    @Nullable
    private static String byPath(Path path, String parentFolder, boolean ignoreFileName) {
        path = path.normalize();
        int parentFolderIndex = -1;

        for (int i = 0; i < path.getNameCount(); i++) {
            if (parentFolder.equals(path.getName(i).toString())) {
                parentFolderIndex = i;
                break;
            }
        }

        if (parentFolderIndex == -1) {
            return null;
        }

        // Check if there is no folders between parent folder and file name
        if (parentFolderIndex >= path.getNameCount() - 2) {
            return ignoreFileName ? "default" : YamlConfig.getFileName(path.toFile()).toLowerCase();
        }

        List<String> groupSegments = new ArrayList<>();
        for (int i = parentFolderIndex + 1; i < path.getNameCount() - 1; i++) {
            groupSegments.add(path.getName(i).toString().toLowerCase());
        }

        if (!ignoreFileName) {
             groupSegments.add(YamlConfig.getFileName(path.toFile()).toLowerCase());
        }

        return String.join("_", groupSegments);
    }

    public static File getFile(EnhancedPlugin plugin, String parent, String group, String file) {
        final String groupPath = String.join(File.separator, CaseFormatter.splitWords(group));
        final String finalPath = String.join(File.separator, parent, groupPath, file);
        return plugin.getFile(finalPath);
    }

    public static File getFile(EnhancedPlugin plugin, String parent, String group) {
        final String groupPath = String.join(File.separator, CaseFormatter.splitWords(group)) + ".yml";
        final String finalPath = String.join(File.separator, parent, groupPath);
        return plugin.getFile(finalPath);
    }

}
