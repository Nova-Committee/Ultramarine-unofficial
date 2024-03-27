package com.voxelutopia.ultramarine.world.feature;

import com.voxelutopia.ultramarine.data.registry.BlockRegistry;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

public class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> JADE_ORE = FeatureUtils.createKey("jade_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MAGNESITE_ORE = FeatureUtils.createKey("magnesite_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> HEMATITE_ORE = FeatureUtils.createKey("hematite_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> COBALT_ORE = FeatureUtils.createKey("cobalt_ore");


    public static void createConfiguredFeatures(BootstapContext<ConfiguredFeature<?, ?>> context) {

        RuleTest ruletest1 = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest ruletest2 = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        RuleTest ruletest3 = new BlockMatchTest(Blocks.NETHERRACK);
        List<OreConfiguration.TargetBlockState> OVERWORLD_JADE_ORES = List.of(
                OreConfiguration.target(ruletest1,BlockRegistry.JADE_ORE.get().defaultBlockState()),
                OreConfiguration.target(ruletest2, BlockRegistry.DEEPSLATE_JADE_ORE.get().defaultBlockState()));
        List<OreConfiguration.TargetBlockState> OVERWORLD_MAGNESITE_ORES = List.of(
                OreConfiguration.target(ruletest1,BlockRegistry.MAGNESITE_ORE.get().defaultBlockState()),
                OreConfiguration.target(ruletest2, BlockRegistry.DEEPSLATE_MAGNESITE_ORE.get().defaultBlockState()));
        List<OreConfiguration.TargetBlockState> OVERWORLD_HEMATITE_ORES = List.of(
                OreConfiguration.target(ruletest1,BlockRegistry.HEMATITE_ORE.get().defaultBlockState()),
                OreConfiguration.target(ruletest2, BlockRegistry.DEEPSLATE_HEMATITE_ORE.get().defaultBlockState()));
        List<OreConfiguration.TargetBlockState> NETHER_COBALT_ORES = List.of(
                OreConfiguration.target(ruletest3,BlockRegistry.NETHER_COBALT_ORE.get().defaultBlockState()));
        FeatureUtils.register(context, JADE_ORE, Feature.ORE, new OreConfiguration(OVERWORLD_JADE_ORES, 9));
        FeatureUtils.register(context, MAGNESITE_ORE, Feature.ORE, new OreConfiguration(OVERWORLD_MAGNESITE_ORES, 7));
        FeatureUtils.register(context, HEMATITE_ORE, Feature.ORE, new OreConfiguration(OVERWORLD_HEMATITE_ORES, 9));
        FeatureUtils.register(context, COBALT_ORE, Feature.ORE, new OreConfiguration(NETHER_COBALT_ORES, 6));
    }
}
