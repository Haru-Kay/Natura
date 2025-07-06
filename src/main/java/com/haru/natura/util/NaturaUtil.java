package com.haru.natura.util;

import com.haru.natura.block.custom.NaturaLeavesBlock;
import com.haru.natura.block.custom.RedwoodLeavesBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.LeavesBlock;

public class NaturaUtil {
    public static int posDoubleToInt(double n) {
        return Math.floorDiv((int)Math.floor(n), 1);
    }
    public static BlockPos newBlockPos(double x, double y, double z) {
        int xi = posDoubleToInt(x);
        int yi = posDoubleToInt(y);
        int zi = posDoubleToInt(z);

        return new BlockPos(xi, yi, zi);
    }

    public static BlockPos addPos(BlockPos pos, double x, double y, double z) {
        pos.offset(posDoubleToInt(x), posDoubleToInt(y), posDoubleToInt(z));

        return pos;
    }

    public static boolean isLeaves(LevelSimulatedReader level, BlockPos pos) {
        return level.isStateAtPosition(pos, state -> (
                state.getBlock() instanceof LeavesBlock || state.getBlock() instanceof NaturaLeavesBlock ||
                        state.getBlock() instanceof RedwoodLeavesBlock));
    }

    public static boolean isWood(LevelSimulatedReader level, BlockPos pos) {
        return level.isStateAtPosition(pos, state -> state.is(BlockTags.LOGS));
    }
}
