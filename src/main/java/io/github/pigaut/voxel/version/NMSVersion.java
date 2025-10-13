package io.github.pigaut.voxel.version;

public enum NMSVersion {

    V1_8_R1,
    V1_8_R2,
    V1_8_R3,
    V1_9_R1,
    V1_9_R2,
    V1_10_R1,
    V1_11_R1,
    V1_12_R1,
    V1_13_R1,
    V1_13_R2,
    V1_14_R1,
    V1_15_R1,
    V1_16_R1,
    V1_16_R2,
    V1_16_R3,
    V1_17_R1,
    V1_18_R1,
    V1_18_R2,
    V1_19_R1,
    V1_19_R2,
    V1_19_R3,
    V1_20_R1,
    V1_20_R2,
    V1_20_R3,
    V1_21_R1,
    V1_21_R2,
    V1_21_R3,
    V1_21_R4,
    V1_21_R5,

    UNKNOWN();

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }

    public boolean isOlderThan(NMSVersion other) {
        return this.ordinal() < other.ordinal();
    }

    public boolean isNewerThan(NMSVersion other) {
        return this.ordinal() > other.ordinal();
    }

}
