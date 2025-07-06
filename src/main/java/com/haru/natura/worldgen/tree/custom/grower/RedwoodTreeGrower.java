package com.haru.natura.worldgen.tree.custom.grower;

import com.haru.natura.Natura;
import com.haru.natura.util.NaturaWoodTypes;
import com.haru.natura.worldgen.NaturaConfiguredFeatures;
import com.haru.natura.worldgen.tree.custom.trunk.RedwoodTrunkPlacer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.grower.AbstractMegaTreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class RedwoodTreeGrower extends AbstractMegaTreeGrower {
    @Nullable
    @Override
    protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource pRandom, boolean pHasFlowers) {
        return null;
    }

    @Nullable
    @Override
    protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredMegaFeature(RandomSource pRandom) {
        return NaturaConfiguredFeatures.REDWOOD_TREE_KEY;
    }

    List<BlockPos> saplings = new ArrayList<>(49);

    @Override
    public boolean growTree(ServerLevel pLevel, ChunkGenerator pGenerator, BlockPos pPos, BlockState pState, RandomSource pRandom) {
        for(int x = 0; x >= -6; --x) {
            for(int z = 0; z >= -6; --z) {
                if (isValidSaplingPlacement(pState, pLevel, pPos, x, z)) {
                    return this.placeMega(pLevel, pGenerator, pPos, pState, pRandom, x + 3, z + 3);
                }
            }
        }
        return false;
    }

    private BlockPos getCenterSapling(BlockState pState, ServerLevel pLevel, BlockPos pPos, int x, int z) {
        return pPos;
    }

    @Override
    public boolean placeMega(ServerLevel pLevel, ChunkGenerator pGenerator, BlockPos pPos, BlockState pState, RandomSource pRandom, int pBranchX, int pBranchY) {
        ResourceKey<ConfiguredFeature<?, ?>> resourcekey = this.getConfiguredMegaFeature(pRandom);
        if (resourcekey == null) {
            return false;
        } else {
            Holder<ConfiguredFeature<?, ?>> holder = pLevel.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE).getHolder(resourcekey).orElse((Holder.Reference<ConfiguredFeature<?, ?>>)null);

            var event = net.minecraftforge.event.ForgeEventFactory.blockGrowFeature(pLevel, pRandom, pPos, holder);
            holder = event.getFeature();
            if (event.getResult() == net.minecraftforge.eventbus.api.Event.Result.DENY) return false;
            if (holder == null) {
                return false;
            } else {
                ConfiguredFeature<?, ?> configuredfeature = holder.value();

                BlockState blockstate = Blocks.AIR.defaultBlockState();
                for (int x = 0; x < saplings.size(); ++x) {
                    pLevel.setBlock(saplings.get(x), blockstate, 4);
                }
                if (configuredfeature.place(pLevel, pGenerator, pRandom, pPos.offset(pBranchX, 0, pBranchY))) {
                    return true;
                } else {
                    for (int x = 0; x < saplings.size(); ++x) {
                        pLevel.setBlock(saplings.get(x), pState, 4);
                    }
                    return false;
                }
            }
        }
    }

    public boolean isValidSaplingPlacement(BlockState pBlockUnder, BlockGetter pLevel, BlockPos pPos, int xStart, int zStart) {
        Block block = pBlockUnder.getBlock();
        saplings.clear();
        for (int x = 0; x <= 6; ++x) {
            for (int z = 0; z <= 6; ++z) {
                if (pLevel.getBlockState(pPos.offset(xStart + x, 0, zStart + z)).is(block)) {
                    //saplings.add(List.of(xStart + x, 0, zStart + z));
                    saplings.add(pPos.offset(xStart + x, 0, zStart + z));
                } else {
                    return true;
                }
            }
        }
        return true;
    }
}
