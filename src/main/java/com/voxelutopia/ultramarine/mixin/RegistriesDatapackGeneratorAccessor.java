package com.voxelutopia.ultramarine.mixin;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.registries.RegistriesDatapackGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.concurrent.CompletableFuture;

/**
 * RegistriesDatapackGeneratorAccessor
 *
 * @author cnlimiter
 * @version 1.0
 * @description
 * @date 2024/3/27 20:13
 */
@Mixin(RegistriesDatapackGenerator.class)
public interface RegistriesDatapackGeneratorAccessor {

    @Accessor("registries")
    CompletableFuture<HolderLookup.Provider> getRegistries();
}
