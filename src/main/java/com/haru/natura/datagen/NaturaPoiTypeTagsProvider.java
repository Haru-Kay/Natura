package com.haru.natura.datagen;

import com.haru.natura.Natura;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.PoiTypeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.PoiTypeTags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class NaturaPoiTypeTagsProvider extends PoiTypeTagsProvider {
    public NaturaPoiTypeTagsProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pProvider, Natura.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        //tag(PoiTypeTags.ACQUIRABLE_JOB_SITE)
        //        .addOptional(ResourceLocation.fromNamespaceAndPath(Natura.MODID, "sound_poi"));
    }
}
