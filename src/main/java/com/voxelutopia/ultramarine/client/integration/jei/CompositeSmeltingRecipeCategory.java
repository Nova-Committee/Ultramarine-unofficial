package com.voxelutopia.ultramarine.client.integration.jei;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.voxelutopia.ultramarine.Ultramarine;
import com.voxelutopia.ultramarine.data.recipe.CompositeSmeltingRecipe;
import com.voxelutopia.ultramarine.data.registry.BlockRegistry;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import static mezz.jei.api.recipe.RecipeIngredientRole.INPUT;
import static mezz.jei.api.recipe.RecipeIngredientRole.OUTPUT;

public class CompositeSmeltingRecipeCategory implements IRecipeCategory<CompositeSmeltingRecipe> {

    public static final ResourceLocation UID = new ResourceLocation(Ultramarine.MOD_ID, "composite_smelting");

    public static final RecipeType<CompositeSmeltingRecipe> COMPOSITE_SMELTING_RECIPE_TYPE =
            new RecipeType<>(UID, CompositeSmeltingRecipe.class);

    public static final ResourceLocation TEXTURE_GUI = new ResourceLocation(Ultramarine.MOD_ID, "textures/gui/brick_kiln.png");

    private final IDrawable background;
    private final int regularCookTime;
    private final IDrawable icon;
    private final Component localizedName;
    private final LoadingCache<Integer, IDrawableAnimated> cachedArrows;
    protected final IDrawableStatic staticFlame;
    protected final IDrawableAnimated animatedFlame;

    public CompositeSmeltingRecipeCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createDrawable(TEXTURE_GUI, 45, 16, 92, 54);
        this.regularCookTime = 200;
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(BlockRegistry.BRICK_KILN.get()));
        this.localizedName = Component.translatable("gui.jei.category.composite_smelting");
        this.cachedArrows = CacheBuilder.newBuilder()
                .maximumSize(25)
                .build(new CacheLoader<>() {
                    @Override
                    public IDrawableAnimated load(Integer cookTime) {
                        return guiHelper.drawableBuilder(UltramarinePlugin.JEI_GUI_VANILLA, 82, 128, 24, 17)
                                .buildAnimated(cookTime, IDrawableAnimated.StartDirection.LEFT, false);
                    }
                });
        this.staticFlame = guiHelper.createDrawable(UltramarinePlugin.JEI_GUI_VANILLA, 82, 114, 14, 14);
        this.animatedFlame = guiHelper.createAnimatedDrawable(staticFlame, 300, IDrawableAnimated.StartDirection.TOP, true);
    }

    protected IDrawableAnimated getArrow(CompositeSmeltingRecipe recipe) {
        int cookTime = recipe.getCookingTime();
        if (cookTime <= 0) {
            cookTime = regularCookTime;
        }
        return this.cachedArrows.getUnchecked(cookTime);
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }


    protected void drawExperience(CompositeSmeltingRecipe recipe, GuiGraphics poseStack, int y) {
        float experience = recipe.getExp();
        if (experience > 0) {
            MutableComponent experienceString = Component.translatable("gui.jei.category.smelting.experience", experience);
            Minecraft minecraft = Minecraft.getInstance();
            Font fontRenderer = minecraft.font;
            int stringWidth = fontRenderer.width(experienceString);
            poseStack.drawString(fontRenderer, experienceString, background.getWidth() - stringWidth, y, 0xFF808080);
        }
    }

    protected void drawCookTime(CompositeSmeltingRecipe recipe, GuiGraphics poseStack, int y) {
        int cookTime = recipe.getCookingTime();
        if (cookTime > 0) {
            int cookTimeSeconds = cookTime / 20;
            MutableComponent timeString = Component.translatable("gui.jei.category.smelting.time.seconds", cookTimeSeconds);
            Minecraft minecraft = Minecraft.getInstance();
            Font fontRenderer = minecraft.font;
            int stringWidth = fontRenderer.width(timeString);
            poseStack.drawString(fontRenderer, timeString, background.getWidth() - stringWidth, y, 0xFF808080);
        }
    }

    @Override
    public RecipeType<CompositeSmeltingRecipe> getRecipeType() {
        return COMPOSITE_SMELTING_RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return localizedName;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, CompositeSmeltingRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(INPUT, 1, 1)
                .addIngredients(recipe.getPrimaryIngredient());

        builder.addSlot(INPUT, 21, 1)
                .addIngredients(recipe.getSecondaryIngredient());

        if (Minecraft.getInstance().level != null) {
            builder.addSlot(OUTPUT, 71, 19)
                    .addItemStack(recipe.getResultItem(Minecraft.getInstance().level.registryAccess()));
        }
    }

    @Override
    public void draw(CompositeSmeltingRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        animatedFlame.draw(guiGraphics, 11, 20);

        IDrawableAnimated arrow = getArrow(recipe);
        arrow.draw(guiGraphics, 34, 18);

        drawExperience(recipe, guiGraphics, 0);
        drawCookTime(recipe, guiGraphics, 45);
    }

}
