package com.voxelutopia.ultramarine.world.feature;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import static com.voxelutopia.ultramarine.world.feature.ModConfiguredFeatures.*;

public class ModPlacedFeatures {

    public static final ResourceKey<PlacedFeature> JADE_ORE_PLACED_FEATURE = PlacementUtils.createKey("jade_ore");

    public static final ResourceKey<PlacedFeature> MAGNESITE_ORE_PLACED_FEATURE = PlacementUtils.createKey("magnesite_ore");

    public static final ResourceKey<PlacedFeature> HEMATITE_ORE_PLACED_FEATURE = PlacementUtils.createKey("hematite_ore");

    public static final ResourceKey<PlacedFeature> COBALT_ORE_PLACED_FEATURE = PlacementUtils.createKey("cobalt_ore");

    public static void createPlacedFeatures(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> holdergetter = context.lookup(Registries.CONFIGURED_FEATURE);
        Holder<ConfiguredFeature<?, ?>> holder1 = holdergetter.getOrThrow(JADE_ORE);
        Holder<ConfiguredFeature<?, ?>> holder2 = holdergetter.getOrThrow(MAGNESITE_ORE);
        Holder<ConfiguredFeature<?, ?>> holder3 = holdergetter.getOrThrow(HEMATITE_ORE);
        Holder<ConfiguredFeature<?, ?>> holder4 = holdergetter.getOrThrow(COBALT_ORE);
        PlacementUtils.register(context, JADE_ORE_PLACED_FEATURE, holder1,
                ModOrePlacement.commonOrePlacement(7,
                    HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80))
                )
        );
        PlacementUtils.register(context, MAGNESITE_ORE_PLACED_FEATURE, holder2,
                ModOrePlacement.commonOrePlacement(10,
                        HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-24), VerticalAnchor.aboveBottom(56))
                )
        );
        PlacementUtils.register(context, HEMATITE_ORE_PLACED_FEATURE, holder3,
                ModOrePlacement.commonOrePlacement(7,
                        HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80))
                )
        );
        PlacementUtils.register(context, COBALT_ORE_PLACED_FEATURE, holder4,
                ModOrePlacement.commonOrePlacement(8,
                        HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(0), VerticalAnchor.aboveBottom(32))
                )
        );
    }
}
