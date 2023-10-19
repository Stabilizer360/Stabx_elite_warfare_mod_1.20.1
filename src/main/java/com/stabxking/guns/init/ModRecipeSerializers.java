package com.stabxking.guns.init;

import com.stabxking.guns.Reference;
import com.stabxking.guns.crafting.DyeItemRecipe;
import com.stabxking.guns.crafting.WorkbenchRecipeSerializer;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * Author: MrCrayfish
 */
public class ModRecipeSerializers
{
    public static final DeferredRegister<RecipeSerializer<?>> REGISTER = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Reference.MOD_ID);

    public static final RegistryObject<SimpleCraftingRecipeSerializer<DyeItemRecipe>> DYE_ITEM = REGISTER.register("dye_item", () -> new SimpleCraftingRecipeSerializer<>(DyeItemRecipe::new));
    public static final RegistryObject<WorkbenchRecipeSerializer> WORKBENCH = REGISTER.register("workbench", WorkbenchRecipeSerializer::new);
}
