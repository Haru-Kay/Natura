package com.haru.natura.block.custom;

import com.haru.natura.block.custom.wood.WoodBuilder;
import com.haru.natura.block.entity.custom.NaturaSignBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

public class NaturaStandingSignBlock extends StandingSignBlock {

    public NaturaStandingSignBlock(Properties pProperties, WoodType woodType) {
        super(pProperties, woodType);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new NaturaSignBlockEntity(pPos, pState);
    }
}
