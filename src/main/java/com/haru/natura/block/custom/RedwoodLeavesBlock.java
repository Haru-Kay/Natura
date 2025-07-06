package com.haru.natura.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.OptionalInt;

public class RedwoodLeavesBlock extends Block implements SimpleWaterloggedBlock {
    private final int maxDistance;
    public static final IntegerProperty DISTANCE = IntegerProperty.create("natura_distance_redwood_tree", 1, 25);
    public static final BooleanProperty PERSISTENT = BlockStateProperties.PERSISTENT;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private static final int TICK_DELAY = 1;
    public RedwoodLeavesBlock(Properties pProperties) {
        super(pProperties);

        this.maxDistance = 25;

        this.registerDefaultState(this.getStateDefinition().any()
                .trySetValue(DISTANCE, maxDistance)
                .trySetValue(PERSISTENT, false)
                .trySetValue(WATERLOGGED, false));
    }

    public VoxelShape getBlockSupportShape(BlockState pState, BlockGetter pReader, BlockPos pPos) {
        return Shapes.empty();
    }

    public boolean isRandomlyTicking(BlockState pState) {
        return pState.getValue(DISTANCE) == maxDistance && !pState.getValue(PERSISTENT);
    }

    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (this.decaying(pState)) {
            dropResources(pState, pLevel, pPos);
            pLevel.removeBlock(pPos, false);
        }
    }

    protected boolean decaying(BlockState pState) {
        return !pState.getValue(PERSISTENT) && pState.getValue(DISTANCE) > maxDistance;
    }

    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        pLevel.setBlock(pPos, updateDistance(pState, pLevel, pPos, maxDistance), 3);
    }

    public int getLightBlock(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return 1;
    }


    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        if (pState.getValue(WATERLOGGED)) {
            pLevel.scheduleTick(pCurrentPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
        }

        int i = getDistanceAt(pFacingState, maxDistance) + 1;
        if (i != 1 || pState.getValue(DISTANCE) != i) {
            pLevel.scheduleTick(pCurrentPos, this, 1);
        }

        return pState;
    }

    private static BlockState updateDistance(BlockState pState, LevelAccessor pLevel, BlockPos pPos, int maxDistance) {
        int i = maxDistance;
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

        for(Direction direction : Direction.values()) {
            blockpos$mutableblockpos.setWithOffset(pPos, direction);
            i = Math.min(i, getDistanceAt(pLevel.getBlockState(blockpos$mutableblockpos), maxDistance) + 1);
            if (i == 1) {
                break;
            }
        }

        return pState.setValue(DISTANCE, Integer.valueOf(i));
    }

    private static int getDistanceAt(BlockState pNeighbor, int maxDistance) {
        return getOptionalDistanceAt(pNeighbor).orElse(maxDistance);
    }

    public static OptionalInt getOptionalDistanceAt(BlockState pState) {
        if (pState.is(BlockTags.LOGS)) {
            return OptionalInt.of(0);
        } else {
            return pState.hasProperty(DISTANCE) ? OptionalInt.of(pState.getValue(DISTANCE)) : OptionalInt.empty();
        }
    }

    public FluidState getFluidState(BlockState pState) {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }

    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        if (pLevel.isRainingAt(pPos.above())) {
            if (pRandom.nextInt(15) == 1) {
                BlockPos blockpos = pPos.below();
                BlockState blockstate = pLevel.getBlockState(blockpos);
                if (!blockstate.canOcclude() || !blockstate.isFaceSturdy(pLevel, blockpos, Direction.UP)) {
                    ParticleUtils.spawnParticleBelow(pLevel, pPos, pRandom, ParticleTypes.DRIPPING_WATER);
                }
            }
        }
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(DISTANCE, PERSISTENT, WATERLOGGED);
    }

    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        FluidState fluidstate = pContext.getLevel().getFluidState(pContext.getClickedPos());
        BlockState blockstate = this.defaultBlockState().setValue(PERSISTENT, Boolean.valueOf(true)).setValue(WATERLOGGED, Boolean.valueOf(fluidstate.getType() == Fluids.WATER));
        return updateDistance(blockstate, pContext.getLevel(), pContext.getClickedPos(), maxDistance);
    }

}