package ninja.shadowfox.shadowfox_botany.api

import net.minecraft.init.Blocks
import net.minecraft.init.Items
import net.minecraft.block.Block
import net.minecraft.item.ItemStack
import ninja.shadowfox.shadowfox_botany.api.recipe.RecipeTreeCrafting
import java.util.*

public object ShadowFoxAPI {
    var treeRecipes: MutableList<RecipeTreeCrafting> = ArrayList()

    public fun addTreeRecipe(recipe: RecipeTreeCrafting): RecipeTreeCrafting {
        treeRecipes.add(recipe)
        return recipe
    }

    public fun addTreeRecipe(mana: Int, outputBlock: Block, meta: Int, vararg inputs: Any): RecipeTreeCrafting {
        return addTreeRecipe(RecipeTreeCrafting(mana, outputBlock, meta, *inputs))
    }

    init {
    }
}
