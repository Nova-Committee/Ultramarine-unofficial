package com.voxelutopia.ultramarine.data.registry;

import com.google.common.collect.ImmutableSet;
import com.voxelutopia.ultramarine.Ultramarine;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Set;

public class PoiTypeRegistry {

    public static final DeferredRegister<PoiType> POI_TYPES = DeferredRegister.create(ForgeRegistries.POI_TYPES, Ultramarine.MOD_ID);

    public static final RegistryObject<PoiType> COOKING_POI = POI_TYPES.register("cooking_poi",
            () -> new PoiType(getAllStates(BlockRegistry.FOOD_HAMPER.get()), 1, 1));

    private static Set<BlockState> getAllStates(Block @NotNull ... blocks) {
        return Arrays.stream(blocks).flatMap(block -> block.getStateDefinition().getPossibleStates().stream()).collect(ImmutableSet.toImmutableSet());
    }


}
