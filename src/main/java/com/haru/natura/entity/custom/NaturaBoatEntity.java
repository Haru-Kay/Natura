package com.haru.natura.entity.custom;

import com.haru.natura.entity.NaturaEntities;
import com.haru.natura.util.NaturaWoodTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.util.function.IntFunction;

public class NaturaBoatEntity extends Boat {
    private static final EntityDataAccessor<Integer> DATA_ID_TYPE = SynchedEntityData.defineId(Boat.class, EntityDataSerializers.INT);

    public NaturaBoatEntity(EntityType<? extends Boat> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    public Item getDropItem() {
        return getModVariant().planks.asItem();
    }

    public void setVariant(Type pVariant) {
        this.entityData.set(DATA_ID_TYPE, pVariant.ordinal());
    }

    public Type getModVariant() {
        return Type.byId(this.entityData.get(DATA_ID_TYPE));
    }
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_ID_TYPE, Type.MAPLE.ordinal());
    }

    protected void addAdditionalSaveData(CompoundTag pCompound) {
        pCompound.putString("Type", this.getModVariant().getSerializedName());
    }

    protected void readAdditionalSaveData(CompoundTag pCompound) {
        if (pCompound.contains("Type", 8)) {
            this.setVariant(Type.byName(pCompound.getString("Type")));
        }
    }

    public NaturaBoatEntity(Level level, double pX, double pY, double pZ) {
        this(NaturaEntities.NATURA_BOAT.get(), level);
        this.setPos(pX, pY, pZ);
        this.xo = pX;
        this.yo = pY;
        this.zo = pZ;
    }

    public static enum Type implements StringRepresentable {
        MAPLE(NaturaWoodTypes.MAPLE.planks.get(), NaturaWoodTypes.MAPLE.name),
        SILVERBELL(NaturaWoodTypes.SILVERBELL.planks.get(), NaturaWoodTypes.SILVERBELL.name),
        AMARANTH(NaturaWoodTypes.AMARANTH.planks.get(), NaturaWoodTypes.AMARANTH.name),
        TIGER(NaturaWoodTypes.TIGER.planks.get(), NaturaWoodTypes.TIGER.name),
        WILLOW(NaturaWoodTypes.WILLOW.planks.get(), NaturaWoodTypes.WILLOW.name),
        EUCALYPTUS(NaturaWoodTypes.EUCALYPTUS.planks.get(), NaturaWoodTypes.EUCALYPTUS.name),
        SAKURA(NaturaWoodTypes.SAKURA.planks.get(), NaturaWoodTypes.SAKURA.name),
        HOPSEED(NaturaWoodTypes.HOPSEED.planks.get(), NaturaWoodTypes.HOPSEED.name),
        REDWOOD(NaturaWoodTypes.REDWOOD.planks.get(), NaturaWoodTypes.REDWOOD.name);

        private final String name;
        private final Block planks;
        public static final StringRepresentable.EnumCodec<NaturaBoatEntity.Type> CODEC = StringRepresentable.fromEnum(NaturaBoatEntity.Type::values);
        private static final IntFunction<Type> BY_ID = ByIdMap.continuous(Enum::ordinal, values(), ByIdMap.OutOfBoundsStrategy.ZERO);

        private Type(Block pPlanks, String pName) {
            this.name = pName;
            this.planks = pPlanks;
        }

        public String getSerializedName() {
            return this.name;
        }

        public String getName() {
            return this.name;
        }

        public Block getPlanks() {
            return this.planks;
        }

        public String toString() {
            return this.name;
        }

        /**
         * Get a boat type by its enum ordinal
         */
        public static NaturaBoatEntity.Type byId(int pId) {
            return BY_ID.apply(pId);
        }

        public static NaturaBoatEntity.Type byName(String pName) {
            return CODEC.byName(pName);
        }
    }
}
