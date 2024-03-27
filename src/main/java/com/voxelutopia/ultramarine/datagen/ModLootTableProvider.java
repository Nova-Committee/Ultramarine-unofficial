package com.voxelutopia.ultramarine.datagen;

import com.google.common.collect.ImmutableList;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.packs.VanillaLootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootDataId;
import net.minecraft.world.level.storage.loot.LootDataType;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Map;
/**
 * ModLootTableProvider
 *
 * @author cnlimiter
 * @version 1.0
 * @description
 * @date 2024/3/27 19:21
 */
public class ModLootTableProvider extends LootTableProvider{

    public ModLootTableProvider(DataGenerator output) {
        super(output.getPackOutput(), Collections.emptySet(), VanillaLootTableProvider.create(output.getPackOutput()).getTables());
    }

    @Override
    public @NotNull List<SubProviderEntry> getTables() {
        return ImmutableList.of(
                new SubProviderEntry(ModBlockLootProvider::new, LootContextParamSets.BLOCK)
//                new SubProviderEntry(ModChestLootTables::new, LootContextParamSets.CHEST),
//                new SubProviderEntry(ModEntityLootTables::new, LootContextParamSets.ENTITY),
//                new SubProviderEntry(ModGiftLootTables::new, LootContextParamSets.GIFT)
        );
    }
    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, @NotNull ValidationContext validationContext) {
        map.forEach((name, loo) -> {
            loo.validate(validationContext.setParams(loo.getParamSet()).enterElement("{" + name + "}", new LootDataId<>(LootDataType.TABLE, name)));
        });
    }

}
