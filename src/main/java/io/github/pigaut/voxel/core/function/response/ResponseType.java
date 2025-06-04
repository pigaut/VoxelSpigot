package io.github.pigaut.voxel.core.function.response;

public enum ResponseType implements FunctionResponse {

    RETURN,
    STOP,
    GOTO;

    @Override
    public ResponseType getType() {
        return this;
    }

}
