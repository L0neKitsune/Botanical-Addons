package ninja.shadowfox.shadowfox_botany.client.core.nei;

import ninja.shadowfox.shadowfox_botany.api.recipe.RecipeTreeCrafting;
import vazkii.botania.client.integration.nei.RecipeHandlerPetalApothecary;

/**
 * Created by l0nekitsune on 12/7/15.
 */
public class CacheRecipeIntermediate extends RecipeHandlerPetalApothecary.CachedPetalApothecaryRecipe {
    CacheRecipeIntermediate(RecipeHandlerPetalApothecary outer, RecipeTreeCrafting recipe){
        outer.super(recipe, false);
    }
}
