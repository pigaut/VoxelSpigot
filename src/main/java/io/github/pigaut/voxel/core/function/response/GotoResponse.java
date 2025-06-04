package io.github.pigaut.voxel.core.function.response;

public class GotoResponse implements FunctionResponse {

    private final int gotoLine;

    public GotoResponse(int gotoLine) {
        this.gotoLine = gotoLine;
    }

    @Override
    public ResponseType getType() {
        return ResponseType.GOTO;
    }

    public int getLine() {
        return gotoLine;
    }

}
