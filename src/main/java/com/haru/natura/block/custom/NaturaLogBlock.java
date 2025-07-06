package com.haru.natura.block.custom;

import com.haru.natura.Natura;
import com.haru.natura.block.NaturaBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import org.jetbrains.annotations.Nullable;

public class NaturaLogBlock extends RotatedPillarBlock {
    private Block stripped;
    public NaturaLogBlock(Properties properties) {
        super(properties);
        this.stripped = null;
    }
    public NaturaLogBlock(Properties properties, Block block) {
        this(properties);
        this.stripped = block;
    }

    @Override
    public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return true;
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 5;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 5;
    }

    @Override
    public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction, boolean simulate) {
        if(stripped != null && context.getItemInHand().getItem() instanceof AxeItem) {
            return this.stripped.defaultBlockState();
        }
        return super.getToolModifiedState(state, context, toolAction, simulate);
    }
}
