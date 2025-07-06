package com.haru.natura.block.entity.custom;

import com.haru.natura.block.entity.NaturaBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.HangingSignBlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class NaturaHangingSignBlockEntity extends HangingSignBlockEntity {
    public NaturaHangingSignBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(pPos, pBlockState);
    }

    @Override
    public BlockEntityType<?> getType() {
        return NaturaBlockEntities.HANGING_SIGN.get();
    }
}