package io.github.pigaut.voxel.command.execution;

import io.github.pigaut.voxel.placeholder.*;
import io.github.pigaut.voxel.player.*;

public interface PlayerStateExecution {

    void execute(PlayerState player, String[] args, PlaceholderSupplier placeholders);

}
