package io.github.pigaut.voxel.version;

import java.util.*;
import java.util.regex.*;

public enum SpigotVersion {

    V1_8(NMSVersion.V1_8_R1),
    V1_8_3(NMSVersion.V1_8_R2),
    V1_8_4(NMSVersion.V1_8_R3),
    V1_8_5(NMSVersion.V1_8_R3),
    V1_8_6(NMSVersion.V1_8_R3),
    V1_8_7(NMSVersion.V1_8_R3),
    V1_8_8(NMSVersion.V1_8_R3),
    V1_9(NMSVersion.V1_9_R1),
    V1_9_2(NMSVersion.V1_9_R1),
    V1_9_4(NMSVersion.V1_9_R2),
    V1_10(NMSVersion.V1_10_R1),
    V1_10_2(NMSVersion.V1_10_R1),
    V1_11(NMSVersion.V1_11_R1),
    V1_11_1(NMSVersion.V1_11_R1),
    V1_11_2(NMSVersion.V1_11_R1),
    V1_12(NMSVersion.V1_12_R1),
    V1_12_1(NMSVersion.V1_12_R1),
    V1_12_2(NMSVersion.V1_12_R1),
    V1_13(NMSVersion.V1_13_R1),
    V1_13_1(NMSVersion.V1_13_R2),
    V1_13_2(NMSVersion.V1_13_R2),
    V1_14(NMSVersion.V1_14_R1),
    V1_14_1(NMSVersion.V1_14_R1),
    V1_14_2(NMSVersion.V1_14_R1),
    V1_14_3(NMSVersion.V1_14_R1),
    V1_14_4(NMSVersion.V1_14_R1),
    V1_15(NMSVersion.V1_15_R1),
    V1_15_1(NMSVersion.V1_15_R1),
    V1_15_2(NMSVersion.V1_15_R1),
    V1_16_1(NMSVersion.V1_16_R1),
    V1_16_2(NMSVersion.V1_16_R2),
    V1_16_3(NMSVersion.V1_16_R2),
    V1_16_4(NMSVersion.V1_16_R3),
    V1_16_5(NMSVersion.V1_16_R3),
    V1_17(NMSVersion.V1_17_R1),
    V1_17_1(NMSVersion.V1_17_R1),
    V1_18(NMSVersion.V1_18_R1),
    V1_18_1(NMSVersion.V1_18_R1),
    V1_18_2(NMSVersion.V1_18_R2),
    V1_19(NMSVersion.V1_19_R1),
    V1_19_1(NMSVersion.V1_19_R1),
    V1_19_2(NMSVersion.V1_19_R1),
    V1_19_3(NMSVersion.V1_19_R2),
    V1_19_4(NMSVersion.V1_19_R3),
    V1_20(NMSVersion.V1_20_R1),
    V1_20_1(NMSVersion.V1_20_R1),
    V1_20_2(NMSVersion.V1_20_R2),
    V1_20_3(NMSVersion.V1_20_R3),
    V1_20_4(NMSVersion.V1_20_R3),
    V1_20_5(NMSVersion.V1_20_R3),
    V1_20_6(NMSVersion.V1_20_R3),
    V1_21(NMSVersion.V1_21_R1),
    V1_21_1(NMSVersion.V1_21_R1),
    V1_21_2(NMSVersion.V1_21_R2),
    V1_21_3(NMSVersion.V1_21_R2),
    V1_21_4(NMSVersion.V1_21_R3),
    V1_21_5(NMSVersion.V1_21_R3),

    UNKNOWN(NMSVersion.UNKNOWN);

    private final NMSVersion nmsVersion;

    SpigotVersion(NMSVersion nmsVersion) {
        this.nmsVersion = nmsVersion;
    }

    public NMSVersion getNMSVersion() {
        return nmsVersion;
    }

    public boolean isOlderThan(SpigotVersion other) {
        return this.ordinal() <= other.ordinal();
    }

    public boolean isNewerThan(SpigotVersion other) {
        return this.ordinal() >= other.ordinal();
    }

    @Override
    public String toString() {
        return name().replace("V", "").replace("_", ".");
    }

    public static List<SpigotVersion> getVersionsNewerThan(SpigotVersion thresholdVersion) {
        final List<SpigotVersion> versions = new ArrayList<>();
        for (SpigotVersion version : values()) {
            if (version.isNewerThan(thresholdVersion)) {
                versions.add(version);
            }
        }
        return versions;
    }

    public static List<SpigotVersion> getVersionsOlderThan(SpigotVersion thresholdVersion) {
        final List<SpigotVersion> versions = new ArrayList<>();
        for (SpigotVersion version : values()) {
            if (version.isOlderThan(thresholdVersion)) {
                versions.add(version);
            }
        }
        return versions;
    }

}
