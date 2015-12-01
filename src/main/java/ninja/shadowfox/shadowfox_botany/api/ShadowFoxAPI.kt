package ninja.shadowfox.shadowfox_botany.api

import net.minecraft.init.Blocks
import net.minecraft.init.Items
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import ninja.shadowfox.shadowfox_botany.api.recipe.RecipeTreeCrafting
import java.util.*

public object ShadowFoxAPI {
    var treeRecipes: MutableList<RecipeTreeCrafting> = ArrayList()


    public fun addTreeRecipe(recipe: RecipeTreeCrafting) {
        treeRecipes.add(recipe)
    }

    init {
        addTreeRecipe(
                RecipeTreeCrafting(
                        0,
                        ItemStack(Items.diamond, 64),
                        *arrayOf(
                            ItemStack(Items.apple, 1),
                            ItemStack(Items.apple, 1),
                            ItemStack(Items.apple, 1),
                            ItemStack(Items.apple, 1)
                        )
                )
        )
    }
}
