package com.voxelutopia.ultramarine.datagen;

import com.voxelutopia.ultramarine.Ultramarine;
import com.voxelutopia.ultramarine.mixin.RegistriesDatapackGeneratorAccessor;
import com.voxelutopia.ultramarine.world.feature.ModConfiguredFeatures;
import com.voxelutopia.ultramarine.world.feature.ModPlacedFeatures;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = DataGenerators.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    public static final String MOD_ID = Ultramarine.MOD_ID;
    public static final RegistrySetBuilder DATA_BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, ModConfiguredFeatures::createConfiguredFeatures)
            .add(Registries.PLACED_FEATURE, ModPlacedFeatures::createPlacedFeatures)
            ;
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper fh = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        DatapackBuiltinEntriesProvider provider = new DatapackBuiltinEntriesProvider(output, lookupProvider, DATA_BUILDER, Set.of(Ultramarine.MOD_ID));
        lookupProvider = ((RegistriesDatapackGeneratorAccessor) provider).getRegistries();
        generator.addProvider(event.includeServer(), provider);

        BlockTagsProvider blockTags = new ModBlockTagProvider(output, lookupProvider, fh);
            generator.addProvider(event.includeServer(), blockTags);
            generator.addProvider(event.includeServer(), new ModLootTableProvider(generator));
            generator.addProvider(event.includeServer(),new ModItemTagProvider(output,lookupProvider, blockTags, fh));
            generator.addProvider(event.includeServer(),new ModRecipeProvider(generator));

            //generator.addProvider(event.includeClient(),new MergedTextureProvider(generator, fh));
            generator.addProvider(event.includeClient(),new ModBlockModelProvider(output, fh));
            generator.addProvider(event.includeClient(),new ModItemModelProvider(output, fh));

    }



}
