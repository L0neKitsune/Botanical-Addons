package ninja.shadowfox.shadowfox_botany.api;

import net.minecraft.block.Block;
import ninja.shadowfox.shadowfox_botany.api.recipe.RecipeTreeCrafting;
import ninja.shadowfox.shadowfox_botany.api.trees.IIridescentSaplingVariant;
import ninja.shadowfox.shadowfox_botany.api.trees.IridescentSaplingBaseVariant;
import java.util.*;

public class ShadowFoxAPI {
    public static List<RecipeTreeCrafting> treeRecipes = new ArrayList();
    public static List<IIridescentSaplingVariant> treeVariants = new ArrayList();

    public static RecipeTreeCrafting addTreeRecipe(RecipeTreeCrafting recipe) {
        treeRecipes.add(recipe);
        return recipe;
    }

    public static RecipeTreeCrafting addTreeRecipe(int mana, Block outputBlock, int meta, Object... inputs) {
        return addTreeRecipe(new RecipeTreeCrafting(mana, outputBlock, meta, inputs));
    }

    public static IIridescentSaplingVariant addTreeVariant(IIridescentSaplingVariant variant) {
        treeVariants.add(variant);
        return variant;
    }

    public static IIridescentSaplingVariant addTreeVariant(Block soil, Block wood, Block leaves) {
        return addTreeVariant(new IridescentSaplingBaseVariant(soil, wood, leaves));
    }

    public static IIridescentSaplingVariant addTreeVariant(Block soil, Block wood, Block leaves, int meta) {
        return addTreeVariant(new IridescentSaplingBaseVariant(soil, wood, leaves, meta));
    }

    public static IIridescentSaplingVariant addTreeVariant(Block soil, Block wood, Block leaves, int metaMin, int metaMax) {
        return addTreeVariant(new IridescentSaplingBaseVariant(soil, wood, leaves, metaMin, metaMax));
    }

    public static IIridescentSaplingVariant addTreeVariant(Block soil, Block wood, Block leaves, int metaMin, int metaMax, int metaShift) {
        return addTreeVariant(new IridescentSaplingBaseVariant(soil, wood, leaves, metaMin, metaMax, metaShift));
    }
}
