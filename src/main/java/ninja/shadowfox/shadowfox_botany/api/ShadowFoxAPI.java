package ninja.shadowfox.shadowfox_botany.api;

import net.minecraft.block.Block;
import ninja.shadowfox.shadowfox_botany.api.recipe.RecipeTreeCrafting;
import java.util.*;

public class ShadowFoxAPI {
    public static List<RecipeTreeCrafting> treeRecipes = new ArrayList();

    public static RecipeTreeCrafting addTreeRecipe(RecipeTreeCrafting recipe) {
        treeRecipes.add(recipe);
        return recipe;
    }

    public static RecipeTreeCrafting addTreeRecipe(int mana, Block outputBlock, int meta, Object... inputs) {
        return addTreeRecipe(new RecipeTreeCrafting(mana, outputBlock, meta, inputs));
    }
}
