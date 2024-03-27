package com.voxelutopia.ultramarine.datagen;

import com.voxelutopia.ultramarine.data.registry.BlockRegistry;
import com.voxelutopia.ultramarine.data.registry.ItemRegistry;
import com.voxelutopia.ultramarine.world.block.BaseFence;
import com.voxelutopia.ultramarine.world.block.BaseWall;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ModItemModelProvider extends ItemModelProvider {

    private final static List<RegistryObject<Block>> NON_SIMPLE_BLOCKS = new ArrayList<>();
    private final static List<RegistryObject<Item>> NON_SIMPLE_ITEMS = new ArrayList<>();

    static {
        BlockRegistry.BLOCKS.getEntries().stream()
                .filter(blockRegistryObject -> (
                        blockRegistryObject.get() instanceof BaseWall ||
                        blockRegistryObject.get() instanceof BaseFence
                ))
                .forEach(NON_SIMPLE_BLOCKS::add);
    }

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, DataGenerators.MOD_ID, existingFileHelper);
    }
    @Override
    protected void registerModels() {
        BlockRegistry.BLOCKS.getEntries().stream()
                .filter(blockRegistryObject -> !NON_SIMPLE_BLOCKS.contains(blockRegistryObject))
                .forEach(this::blockItem);
        wallInventory(name(BlockRegistry.BLACK_BRICK_WALL.get()), blockLoc(BlockRegistry.BLACK_BRICKS.get()));
        wallInventory(name(BlockRegistry.CYAN_BRICK_WALL.get()), blockLoc(BlockRegistry.CYAN_BRICKS.get()));
        wallInventory(name(BlockRegistry.CYAN_FLOOR_TILE_WALL.get()), blockLoc(BlockRegistry.CYAN_FLOOR_TILE.get()));
        wallInventory(name(BlockRegistry.LIGHT_CYAN_FLOOR_TILE_WALL.get()), blockLoc(BlockRegistry.LIGHT_CYAN_FLOOR_TILE.get()));
        wallInventory(name(BlockRegistry.BROWNISH_RED_STONE_BRICK_WALL.get()), blockLoc(BlockRegistry.BROWNISH_RED_STONE_BRICKS.get()));
        wallInventory(name(BlockRegistry.POLISHED_WEATHERED_STONE_WALL.get()), blockLoc(BlockRegistry.POLISHED_WEATHERED_STONE.get()));
        fenceInventory(name(BlockRegistry.ROSEWOOD_FENCE.get()), blockLoc(BlockRegistry.ROSEWOOD_PLANKS.get()));
        ItemRegistry.ITEMS.getEntries().stream()
                .filter(blockRegistryObject -> !NON_SIMPLE_ITEMS.contains(blockRegistryObject))
                .filter(blockRegistryObject -> !(blockRegistryObject.get() instanceof BlockItem))
                .forEach(this::generatedItem);

    }

    private ItemModelBuilder generatedItem(RegistryObject<Item> item){
        return singleTexture(Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item.get())).getPath(), mcLoc("item/generated"), "layer0", modLoc("item/" + item.get()));
    }

    private ItemModelBuilder blockItem(RegistryObject<Block> block){
        return withExistingParent(name(block.get()), modLoc("block/" + block.get()));
    }

    private ResourceLocation blockLoc(Block block){
        return modLoc("block/" + name(block));
    }
    
    private String name(Block block){
       return Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block)).getPath();
    }

    @NotNull
    @Override
    public String getName() {
        return DataGenerators.MOD_ID + " Item Models";
    }

}
