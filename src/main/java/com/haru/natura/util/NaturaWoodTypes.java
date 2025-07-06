package com.haru.natura.util;

import com.haru.natura.block.custom.wood.RedwoodBuilder;
import com.haru.natura.block.custom.wood.WoodBuilder;
import com.haru.natura.worldgen.tree.custom.grower.*;

public class NaturaWoodTypes {
    public static WoodBuilder MAPLE;
    public static WoodBuilder SILVERBELL;
    public static WoodBuilder AMARANTH;
    public static WoodBuilder TIGER;
    public static WoodBuilder WILLOW;
    //public static WoodBuilder WILLOW_3;
    public static WoodBuilder EUCALYPTUS;
    //public static WoodBuilder EUCALYPTUS_PLAINS;
    public static WoodBuilder SAKURA;
    //public static WoodBuilder SAKURA_FOREST;
    public static WoodBuilder HOPSEED;
    public static RedwoodBuilder REDWOOD;

    public static void init() {
        MAPLE = new WoodBuilder("maple", new MapleTreeGrower(), 7).build();
        SILVERBELL = new WoodBuilder("silverbell", new SilverbellTreeGrower(), 7).build();
        AMARANTH = new WoodBuilder("amaranth", new AmaranthTreeGrower(), 7).build();
        TIGER = new WoodBuilder("tiger", new TigerTreeGrower(), 7).build();
        WILLOW = new WoodBuilder("willow", new WillowTreeGrower(), 10).build();
        //WILLOW_3 = new WoodBuilder("willow_3", new MapleTreeGrower()).build();
        EUCALYPTUS = new WoodBuilder("eucalyptus", new EucalyptusTreeGrower(), 7).build();
        //EUCALYPTUS_PLAINS = new WoodBuilder("eucalyptus_plains", new MapleTreeGrower()).build();
        SAKURA = new WoodBuilder("sakura", new SakuraTreeGrower(), 7).build();
        //SAKURA_FOREST = new WoodBuilder("sakura_forest", new MapleTreeGrower()).build();
        HOPSEED = new WoodBuilder("hopseed", new HopseedTreeGrower(), 7).build();
        REDWOOD = new RedwoodBuilder("redwood", new RedwoodTreeGrower()).build();

    }
}
