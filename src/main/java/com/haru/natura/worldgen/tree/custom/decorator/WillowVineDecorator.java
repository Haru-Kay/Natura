package com.haru.natura.worldgen.tree.custom.decorator;

import com.haru.natura.Natura;
import com.haru.natura.util.NaturaWoodTypes;
import com.haru.natura.worldgen.tree.NaturaTreeDecoratorTypes;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.levelgen.feature.treedecorators.LeaveVineDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

public class WillowVineDecorator extends TreeDecorator {
    public static final Codec<WillowVineDecorator> CODEC = Codec.floatRange(0.0F, 1.0F).fieldOf("probability").xmap(WillowVineDecorator::new,
            (willowVineDecoratorInstance) -> {
                return willowVineDecoratorInstance.probability;
            }).codec();
    private final float probability;

    protected TreeDecoratorType<?> type() {
        return NaturaTreeDecoratorTypes.WILLOW_VINE_DECORATOR.get();
    }

    public WillowVineDecorator(float probability) {
        this.probability = probability;
    }

    public void place(TreeDecorator.Context pContext) {
        RandomSource randomsource = pContext.random();
        for (BlockPos leafPos : pContext.leaves()) {
            for (Direction dir : Direction.Plane.HORIZONTAL) {
                if (randomsource.nextFloat() < this.probability) {
                    BlockPos offset = leafPos.relative(dir);
                    if (pContext.isAir(offset)) {
                        addHangingVine(offset, pContext);
                    }
                }
            }
        }
    }

    private static void addHangingVine(BlockPos pPos, TreeDecorator.Context pContext) {
        BlockPos current = pPos;
        int vineLimit = 5;

        while(pContext.isAir(current) && current.getY() > pPos.getY() - vineLimit) {
            pContext.setBlock(current, NaturaWoodTypes.WILLOW.leaves.get().defaultBlockState());
            current = current.below();
        }

    }
}
