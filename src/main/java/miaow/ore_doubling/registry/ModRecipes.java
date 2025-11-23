package miaow.ore_doubling.registry;

import miaow.ore_doubling.OreDoubling;
import miaow.ore_doubling.recipe.CrushingRecipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModRecipes {
    public static final RecipeType<CrushingRecipe> CRUSHING_TYPE = Registry.register(Registries.RECIPE_TYPE, Identifier.of(OreDoubling.MOD_ID, "crushing"), new RecipeType<CrushingRecipe>() {
        @Override
        public String toString() { return "crushing"; }
    });

    public static final RecipeSerializer<CrushingRecipe> CRUSHING_SERIALIZER = Registry.register(Registries.RECIPE_SERIALIZER, Identifier.of(OreDoubling.MOD_ID, "crushing"), new CrushingRecipe.Serializer());

    public static void registerRecipes() {
        // 调用以初始化
    }
}
