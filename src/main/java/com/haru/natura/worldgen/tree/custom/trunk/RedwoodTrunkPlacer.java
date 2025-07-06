package com.haru.natura.worldgen.tree.custom.trunk;

import com.google.common.collect.ImmutableList;
import com.haru.natura.util.NaturaWoodTypes;
import com.haru.natura.worldgen.tree.NaturaTrunkPlacerTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import static com.haru.natura.util.NaturaUtil.posDoubleToInt;

public class RedwoodTrunkPlacer extends TrunkPlacer {
    private static final int MAX_BASE_HEIGHT = 80;
    private static final int MAX_RAND = 60;
    public static final int MAX_HEIGHT = 140;
    public static final Codec<RedwoodTrunkPlacer> CODEC = RecordCodecBuilder.create(redwoodTrunkPlacerInstance ->
            trunkPlacerParts(redwoodTrunkPlacerInstance).apply(redwoodTrunkPlacerInstance, RedwoodTrunkPlacer::new));
    private int treeHeight;
    private int foliageHeight;
    private int foliageLayers;
    private double heightAttenuation = 0.618D;
    private double branchSlope = 0.381D;
    private double scaleWidth = 1.0D;
    private double leafDensity = 1.0D;
    private int foliageExtraLayerLimit = 12;
    private int leafDistanceLimit = 5;
    private List<FoliageCoordinates> foliageCoords;
    public final Block bark;
    public final Block heart;
    public final Block root;
    public final Block leaves;
    private RandomSource rand;
    private LevelSimulatedReader level;
    private BlockPos basePos;
    private BlockPos foliagePos;
    private BiConsumer<BlockPos, BlockState> blockSetter;
    private TreeConfiguration config;
    private boolean onTopLayer = false;

    public RedwoodTrunkPlacer(int pBaseHeight, int pHeightRandA, int pHeightRandB) {
        this(pBaseHeight, pHeightRandA, pHeightRandB,
                NaturaWoodTypes.REDWOOD.bark.get(), NaturaWoodTypes.REDWOOD.heart.get(), NaturaWoodTypes.REDWOOD.root.get(),
                NaturaWoodTypes.REDWOOD.leaves.get());
    }
    public RedwoodTrunkPlacer(int pBaseHeight, int pHeightRandA, int pHeightRandB,
                              Block bark, Block heart, Block root, Block leaves) {
        super(pBaseHeight, pHeightRandA, pHeightRandB);
        this.bark = bark;
        this.heart = heart;
        this.root = root;

        this.leaves = leaves;
    }

    @Override
    protected TrunkPlacerType<?> type() {
        return NaturaTrunkPlacerTypes.REDWOOD_TRUNK_PLACER.get();
    }

    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader pLevel, BiConsumer<BlockPos, BlockState> pBlockSetter, RandomSource pRandom, int pFreeTreeHeight, BlockPos pPos, TreeConfiguration pConfig) {
        this.treeHeight = pFreeTreeHeight;
        this.basePos = pPos;
        this.rand = pRandom;
        this.level = pLevel;
        this.blockSetter = pBlockSetter;
        this.config = pConfig;
        this.foliageLayers = 5 + this.rand.nextInt(this.foliageExtraLayerLimit);
        int highestRing = this.treeHeight > 120 ? 10 : this.treeHeight > 100 ? 8 : 6;
        int currentRing = 1;
        BlockPos pos = this.basePos.above(0);

        for (int currentHeight = 0; currentHeight < this.treeHeight; currentHeight++) {
            if (currentHeight > this.treeHeight * currentRing / highestRing) {
                currentRing++;
            }

            int ring = highestRing - currentRing;
            // ring goes 9 -> 0, lower number = smaller/higher ringer
            pos = this.basePos.above(currentHeight);
            generateRing(ring, pos, false);

            if (ring <= 5) {
                generateBranch(ring, highestRing, pos);
            }
        }
        generateRoots(highestRing);

        this.foliagePos = pos; //.below(3);
        generateTopFoliage();
        //ImmutableList.of(new FoliagePlacer.FoliageAttachment(pPos.above(height), 0, false));
        return ImmutableList.of();
    }

    public boolean generateRing(int ring, BlockPos currentPosition, boolean rootRing) {
        int[] slice;
        int[] bark;
        switch (ring) {
            case 9:
                slice = new int[]{5, 9, 11, 11, 13, 13, 13};
                bark = new int[]{-1, 3, 2, 1, 2, 1, 1};
                break;
            case 8:
                slice = new int[]{3, 7, 9, 11, 13, 13, 13};
                bark = new int[]{-1, 2, 1, 1, 1, 1, 1};
                break;
            case 7:
                slice = new int[]{3, 7, 9, 9, 11, 11};
                bark = new int[]{-1, 2, 2, 1, 1, 1};
                break;
            case 6:
                slice = new int[]{5, 7, 9, 9, 9};
                bark = new int[]{-1, 2, 2, 1, 1};
                break;
            case 5:
                slice = new int[]{3, 7, 7, 9, 9};
                bark = new int[]{-1, 2, 1, 1, 1};
                break;
            case 4:
                slice = new int[]{5, 7, 7, 7};
                bark = new int[]{-1, 2, 1, 1};
                break;
            case 3:
                slice = new int[]{3, 5, 7, 7};
                bark = new int[]{-1, 1, 1, 1};
                break;
            case 2:
                slice = new int[]{5, 5, 5};
                bark = new int[]{-1, 1, 1};
                break;
            case 1:
                slice = new int[]{3, 5, 5};
                bark = new int[]{-1, 1, 1};
                break;
            default:
                slice = new int[]{3, 3};
                bark = new int[]{-1, 1};
                break;
        }
        int radius = slice.length - 1;

        int x = 0;
        int flipX = 1;
        while (x >= 0) {
            int z = 0;
            int flipZ = 1;
            int xCoord = flipX * (x - radius);
            int barkCount = 0;
            int barkStart = (slice[x] - 1) / 2;

            while (z >= 0) {
                int zCoord = flipZ * (z - radius);

                if (Math.abs(zCoord) <= barkStart) {
                    barkCount += flipZ;
                    BlockState block = (barkCount <= bark[x] || bark[x] == -1) ? this.bark.defaultBlockState() : this.heart.defaultBlockState();
                    BlockPos blockPos = currentPosition.offset(xCoord, 0, zCoord);
                    if(this.validTreePos(this.level, blockPos) ||
                            (rootRing && level.isStateAtPosition(blockPos, state -> state.is(BlockTags.DIRT)))) {
                        placeBlock(blockPos, block);
                    }
                } else {
                    // do not place
                }

                if (z >= radius && flipZ == 1) {
                    flipZ = -1;
                }
                z += flipZ;
            }

            if (x >= radius && flipX == 1) {
                flipX = -1;
            }
            x += flipX;
        }

        return false;
    }

    public void generateBranch(int ring, int highestRing, BlockPos currentPosition) {
        /*
            highestRing == 10 : low(5, 4), mid(3, 2), high(1, 0)
                        ==  8 : mid(3, 2), high(1, 0)
                        ==  6 : mid(2), high(1, 0)

            low  offset: ( -8,  8)
            mid  offset: (-15, 15)
            high offset: (-10, 10)
         */

        if (ring == 1 || ring == 0) {
            generateHighBranch(currentPosition);
        }
        if (highestRing < 8 && ring != 3) {
            return;
        }
        if (ring == 2 || ring == 3) {
            generateMidBranch(currentPosition);
        }
        if (highestRing < 10) {
            return;
        }

        generateLowBranch(currentPosition);
    }

    public void generateLowBranch(BlockPos currentPosition) {
        int upper = 28;
        int lower = upper / 2;
        for (int i = 0; i < 2; i++) {
            int xOffset = this.rand.nextInt(upper + 1) - lower;
            int zOffset = this.rand.nextInt(upper + 1) - lower;

            this.foliagePos = currentPosition.offset(xOffset, 0, zOffset);
            generateLeafNodes();
            generateLeafNodeBases();
            generateLeaves();
        }

        if (this.rand.nextInt(2) == 0) {
            int xOffset = this.rand.nextInt(upper + 1) - lower;
            int zOffset = this.rand.nextInt(upper + 1) - lower;

            this.foliagePos = currentPosition.offset(xOffset, 0, zOffset);
            generateLeafNodes();
            generateLeafNodeBases();
            generateLeaves();
        }
    }

    public void generateMidBranch(BlockPos currentPosition) {
        int upper = 20;
        int lower = upper / 2;
        for (int i = 0; i < 6; i++) {
            int xOffset = this.rand.nextInt(upper + 1) - lower;
            int zOffset = this.rand.nextInt(upper + 1) - lower;

            this.foliagePos = currentPosition.offset(xOffset, 0, zOffset);
            generateLeafNodes();
            generateLeafNodeBases();
            generateLeaves();
        }
    }

    public void generateHighBranch(BlockPos currentPosition) {
        int upper = 16;
        int lower = upper / 2;
        for (int i = 0; i < 3; i++) {
            int xOffset = this.rand.nextInt(upper + 1) - lower;
            int zOffset = this.rand.nextInt(upper + 1) - lower;

            this.foliagePos = currentPosition.offset(xOffset, 0, zOffset);
            generateLeafNodes();
            generateLeafNodeBases();
            generateLeaves();
        }

    }

    public boolean generateRoots(int highestRing) {
        BlockPos rootPos = this.basePos.below();
        generateRing(highestRing - 1, rootPos, true);

        int maxRoot = highestRing == 10 ? 18 : highestRing == 8 ? 14 : 11;

        for (int height = 0; height < maxRoot; height++) {
            rootPos = rootPos.below();
            int root;
            if (height >= 14) {
                root = 5;
            } else if (height >= 11) {
                root = 4;
            } else if (height >= 9) {
                root = 3;
            } else if (height >= 5) {
                root = 2;
            } else if (height >= 3) {
                root = 1;
            } else {
                root = 0;
            }
            generateRoot(rootPos, root, maxRoot);
        }

        return false;
    }

    public void generateRoot(BlockPos rootPos, int root, int maxRoot) {
        // * = skip 1, ^ = skip 2, % = skip 3
        /*
        4 =>  1 /  3 /  3 / 4
        4 =>  1 /  3 / 3* / 4*
        2 => 2* / 3*
        1 => 2* / 2*
         */
        /*
        5 =>  1 /  3 /  4 /  4 /  5
        4 =>  1 /  2 /  3 /  4
        3 => 2* / 3* / 4*
        2 => 2* / 3^
        1 => 3^
         */
        /*
        6  =>  2 /  4 /  5 /  5 /  6
        5  =>  4 /  5 /  5 /  6 /  6
        4  => 3* / 4* / 4* / 5*
        4  => 3^ / 4^ / 4^ / 5%
        3  => 3^ / 4^ / 4%
        2^ => 4^ / 3^
         */
        int[] xBoundArr;
        int[][][] zBoundArr;
        if (maxRoot == 11) {
            xBoundArr = new int[]{4, 4, 2, 1};
            zBoundArr = new int[][][]{{{1, 3, 3, 4}, {0, 0, 0, 0}},
                                        {{1, 3, 3, 4}, {0, 0, 1, 1}},
                                        {{2, 3}, {1, 1}},
                                        {{2, 2}, {1, 1}}};
        } else if (maxRoot == 14) {
            xBoundArr = new int[]{5, 4, 3, 2, 1};
            zBoundArr = new int[][][]{{{1, 3, 4, 4, 5}, {0, 0, 0, 0, 0}},
                                        {{1, 2, 3, 4}, {0, 0, 0, 0}},
                                        {{2, 3, 4}, {1, 1, 1}},
                                        {{2, 3}, {1, 2}},
                                        {{3}, {2}}};
        } else {
            xBoundArr = new int[]{6, 5, 4, 3, 3, 2};
            zBoundArr = new int[][][]{{{2, 4, 5, 5, 6}, {0, 0, 0, 0, 0}},
                                        {{4, 5, 5, 6, 6}, {1, 1, 1, 1, 1}},
                                        {{3, 4, 4, 5}, {1, 1, 1, 1}},
                                        {{3, 4, 4, 5}, {2, 2, 2, 3}},
                                        {{3, 4, 4}, {2, 2, 3}},
                                        {{4, 3}, {2, 2}}};
        }
        int xBound = xBoundArr[root];
        int[] zLayer = zBoundArr[root][0];
        int[] zSkipLayer = zBoundArr[root][1];
        for (int x = -xBound; x <= xBound; x++) {
            if(x != 0) {
                int j = xBound + ((x > 0 ? -1 : 1) * x);
                int zBound = zLayer[j];
                int zSkip = zSkipLayer[j];
                for (int z = -zBound; z <= zBound; z++) {
                    if(Math.abs(z) > zSkip) {
                        this.placeBlock(rootPos.offset(x, 0, z), NaturaWoodTypes.REDWOOD.root.get().defaultBlockState());
                    }
                }
            }
        }

    }

    public boolean generateTopFoliage() {
        this.onTopLayer = true;
        for (int i = 0; i < 3; i++) {
            this.foliageCoords = new ArrayList<>();
            BlockPos pos = this.foliagePos.offset(this.rand.nextInt(5) - 2, this.rand.nextInt(3), this.rand.nextInt(5) - 2);
            this.foliageCoords.add(new FoliageCoordinates(pos, new BlockPos(this.basePos.getX(), pos.getY(), this.basePos.getZ())));
            generateLeafNodeBases();
            generateLeaves();
        }

        this.foliageCoords = new ArrayList<>();
        BlockPos pos = this.foliagePos.above(4);
        this.foliageCoords.add(new FoliageCoordinates(pos, new BlockPos(this.basePos.getX(), this.foliagePos.getY(), this.basePos.getZ())));
        generateLeafNodes();
        generateLeafNodeBases();
        generateLeaves();
        return false;
    }

    public void placeBlock(BlockPos blockPos, BlockState block) {
        this.blockSetter.accept(blockPos, block);
    }

    @Override
    public int getTreeHeight(RandomSource pRandom) {
        //return this.baseHeight + this.rand.nextInt(this.heightRandA);
        this.rand = pRandom;
        return MAX_BASE_HEIGHT + this.rand.nextInt(MAX_RAND);
    }

    private void generateLeafNodes() { // foliage
        this.foliageHeight = (int) (this.foliageLayers * this.heightAttenuation);
        int leafClusters = (int) (1.382D + Math.pow(this.leafDensity * this.foliageLayers / 13.0D, 2.0D));

        int nodeBase = this.foliagePos.getY() + this.foliageHeight;
        int leafPlacementStart = this.foliageLayers - this.leafDistanceLimit;
        this.foliageCoords = new ArrayList<>();
        this.foliageCoords.add(new FoliageCoordinates(this.foliagePos.above(leafPlacementStart),
                new BlockPos(this.basePos.getX(), this.foliagePos.above(leafPlacementStart).getY(), this.basePos.getZ())));

        for (; leafPlacementStart >= 0; --leafPlacementStart) {
            float layerRadius = this.layerSize(leafPlacementStart);

            if (layerRadius >= 0.0F) {
                for (int cluster = 0; cluster < leafClusters; ++cluster) {
                    double radius = this.scaleWidth * layerRadius * (this.rand.nextFloat() + 0.328D);
                    double angle = this.rand.nextFloat() * 2.0F * Math.PI;
                    double xOffset = radius * Math.sin(angle) + 0.5D;
                    double zOffset = radius * Math.cos(angle) + 0.5D;
                    BlockPos foliageCenterPos = this.foliagePos.offset(posDoubleToInt(xOffset), 0, posDoubleToInt(zOffset));
                    BlockPos foliageTopPos = foliageCenterPos.above(this.leafDistanceLimit);

                    if (this.checkBlockLine(foliageCenterPos, foliageTopPos) == -1) {
                        int xDiff = this.foliagePos.getX() - foliageCenterPos.getX();
                        int zDiff = this.foliagePos.getZ() - foliageCenterPos.getZ();
                        double testY = foliageCenterPos.getY() - Math.sqrt(xDiff * xDiff + zDiff * zDiff) * this.branchSlope;
                        int branchBaseY = testY > nodeBase ? nodeBase : (int) testY;
                        BlockPos foliageStartPos = new BlockPos(this.foliagePos.getX(), branchBaseY, this.foliagePos.getZ());

                        if (this.checkBlockLine(foliageStartPos, foliageCenterPos) == -1) {
                            this.foliageCoords.add(new FoliageCoordinates(foliageCenterPos,
                                    new BlockPos(this.basePos.getX(), foliageCenterPos.getY(), this.basePos.getZ())));
                        }
                    }
                }
            }
        }
    }

    void crossSection(BlockPos pos, float size) { // foliage
        int realSize = (int) (size + this.heightAttenuation);

        for (int x = -realSize; x <= realSize; ++x) {
            for (int z = -realSize; z <= realSize; ++z) {
                if (Math.pow(Math.abs(x) + 0.5D, 2.0D) + Math.pow(Math.abs(z) + 0.5D, 2.0D) <= size * size) {
                    BlockPos currentPos = pos.offset(x, 0, z);

                    boolean canPlace = level.isStateAtPosition(currentPos, state -> state.isAir()) ||
                            level.isStateAtPosition(currentPos, state -> state.canBeReplaced());
                    if (canPlace) {
                        //this.setBlockAndMetadata(this.world, currentPos, leaves);
                        //tryPlaceLeaf(level, setter, random, config, currentPos);
                        placeBlock(currentPos, this.leaves.defaultBlockState());
                    }
                }
            }
        }
    }

    /**
     * Gets the rough size of a layer of the tree.
     */
    float layerSize(int y)
    {
        if (y < this.foliageLayers * 0.3F)
        {
            return -1.0F;
        }
        else
        {
            float foliageRadius = this.foliageLayers / 2.0F;
            float foliageDistance = foliageRadius - y;
            float foliageSize = (float) Math.sqrt(foliageRadius * foliageRadius - foliageDistance * foliageDistance);

            if (foliageDistance == 0.0F)
            {
                foliageSize = foliageRadius;
            }
            else if (Math.abs(foliageDistance) >= foliageRadius)
            {
                return 0.0F;
            }

            return foliageSize * 0.5F;
        }
    }

    float leafSize(int y)
    {
        return y >= 0 && y < this.leafDistanceLimit ? (y != 0 && y != this.leafDistanceLimit - 1 ? 3.0F : 2.0F) : -1.0F;
    }

    /**
     * Generates the leaves surrounding an individual entry in the leafNodes list.
     */
    void generateLeafNode(BlockPos pos)
    {
        for (int i = 0; i < this.leafDistanceLimit; ++i)
        {
            this.crossSection(pos.above(i), this.leafSize(i));
        }
    }

    void limb(BlockPos nodeBase, BlockPos foliage)
    {
        BlockPos blockpos = foliage.offset(-nodeBase.getX(), -nodeBase.getY(), -nodeBase.getZ());
        int i = this.getGreatestDistance(blockpos);
        float xDistOffset = (float) blockpos.getX() / (float) i;
        float yDistOffset = (float) blockpos.getY() / (float) i;
        float zDistOffset = (float) blockpos.getZ() / (float) i;

        for (int j = 0; j <= i; ++j)
        {
            BlockPos currentPos = nodeBase.offset(posDoubleToInt(0.5F + j * xDistOffset),
                    posDoubleToInt(0.5F + j * yDistOffset), posDoubleToInt(0.5F + j * zDistOffset));
            //this.setBlockAndMetadata(this.world, blockpos1, block);
            //placeLog(pLevel, pBlockSetter, pRandom, pPos.above(i), pConfig);
            placeBlock(currentPos, this.bark.defaultBlockState());
        }
    }

    /**
     * Returns the absolute greatest distance in the BlockPos object.
     */
    private int getGreatestDistance(BlockPos posIn)
    {
        int i = Math.abs(posIn.getX());
        int j = Math.abs(posIn.getY());
        int k = Math.abs(posIn.getZ());
        return k > i && k > j ? k : (j > i ? j : i);
    }

    /**
     * Generates the leaf portion of the tree as specified by the leafNodes list.
     */
    void generateLeaves()
    {
        for (FoliageCoordinates foliagecoordinates : this.foliageCoords)
        {
            this.generateLeafNode(foliagecoordinates);
        }
    }

    /**
     * Indicates whether or not a leaf node requires additional wood to be added to preserve integrity.
     */
    boolean leafNodeNeedsBase(FoliageCoordinates foliageCoordinate) {
        if (this.onTopLayer) {
            return true;
        }
        int limit = leafDistanceLimit / 2;
        for (int x = -limit; x <= limit; x++) {
            for (int y = -limit; y <= limit; y++) {
                for (int z = -limit; z <= limit; z++) {
                    BlockPos pos = foliageCoordinate.offset(x, y, z);
                    if (level.isStateAtPosition(pos, state -> state.is(BlockTags.LOGS))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Generates additional wood blocks to fill out the bases of different leaf nodes that would otherwise degrade.
     */
    void generateLeafNodeBases() {
        for (FoliageCoordinates foliagecoordinates : this.foliageCoords)
        {
            BlockPos nodeBase = foliagecoordinates.getBranchBase();

            if (!nodeBase.equals(foliagecoordinates) && this.leafNodeNeedsBase(foliagecoordinates))
            {
                this.limb(nodeBase, foliagecoordinates);
            }
        }
    }

    /**
     * Checks a line of blocks in the world from the first coordinate to triplet to the second, returning the distance
     * (in blocks) before a non-air, non-leaf block is encountered and/or the end is encountered.
     */
    int checkBlockLine(BlockPos posOne, BlockPos posTwo)
    {
        BlockPos blockpos = posTwo.offset(-posOne.getX(), -posOne.getY(), -posOne.getZ());
        int i = this.getGreatestDistance(blockpos);
        float f = (float) blockpos.getX() / (float) i;
        float f1 = (float) blockpos.getY() / (float) i;
        float f2 = (float) blockpos.getZ() / (float) i;

        if (i == 0)
        {
            return -1;
        }
        else
        {
            for (int j = 0; j <= i; ++j)
            {
                BlockPos blockpos1 = posOne.offset(posDoubleToInt(0.5F + j * f), posDoubleToInt(0.5F + j * f1), posDoubleToInt(0.5F + j * f2));

                if (!this.isReplaceable(this.level, blockpos1))
                {
                    return j;
                }
            }

            return -1;
        }
    }

    public boolean isReplaceable(LevelSimulatedReader world, BlockPos pos) {
        return world.isStateAtPosition(pos, state -> state.isAir()) ||
            world.isStateAtPosition(pos, state -> state.is(BlockTags.REPLACEABLE_BY_TREES));
    }

    BlockPos findGround(LevelSimulatedReader world, BlockPos pos)
    {
        boolean foundGround = false;

        int height = (63) + 64;

        while (!foundGround) {
            height--;

            BlockPos position = new BlockPos(pos.getX(), height, pos.getZ());

            boolean isGround = world.isStateAtPosition(position, state -> state.is(BlockTags.DIRT));

            if (isGround || height < (63))
            {
                foundGround = true;
            }
        }

        return new BlockPos(pos.getX(), height, pos.getZ());
    }

    public boolean isValidSpawn(LevelSimulatedReader world, BlockPos pos)
    {
        return this.validTreePos(world, pos);
    }

    static class FoliageCoordinates extends BlockPos
    {
        private final BlockPos branchBase;

        public FoliageCoordinates(BlockPos pos, BlockPos branchBase)
        {
            super(pos.getX(), pos.getY(), pos.getZ());
            this.branchBase = branchBase;

        }

        public BlockPos getBranchBase()
        {
            return this.branchBase;
        }
    }
}
