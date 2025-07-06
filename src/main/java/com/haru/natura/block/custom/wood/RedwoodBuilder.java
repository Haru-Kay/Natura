package com.haru.natura.block.custom.wood;

import com.haru.natura.block.NaturaBlocks;
import com.haru.natura.block.custom.RedwoodLeavesBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.RegistryObject;

public class RedwoodBuilder extends WoodBuilder {

    public RegistryObject<Block> bark;
    public RegistryObject<Block> root;
    public RegistryObject<Block> heart;
    public RedwoodBuilder(String name, AbstractTreeGrower treeGrower) {
        super(name, treeGrower);
    }

    public RedwoodBuilder build() {
        this.bark = NaturaBlocks.registerBlock(name + "_bark",
                () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)));
        this.root = NaturaBlocks.registerBlock(name + "_root",
                () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)));
        this.heart = NaturaBlocks.registerBlock(name + "_heart",
                () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)));
        leaves = NaturaBlocks.registerBlock(name + "_leaves",
                () -> new RedwoodLeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES)));
        super.buildShared();
        return this;
    }
}
