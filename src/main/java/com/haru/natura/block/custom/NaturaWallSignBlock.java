package com.haru.natura.block.custom;

import com.haru.natura.block.custom.wood.WoodBuilder;
import com.haru.natura.block.entity.custom.NaturaHangingSignBlockEntity;
import com.haru.natura.block.entity.custom.NaturaSignBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

public class NaturaWallSignBlock extends WallSignBlock {
    public NaturaWallSignBlock(Properties pProperties, WoodType type) {
        super(pProperties, type);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new NaturaSignBlockEntity(pPos, pState);
    }
}
