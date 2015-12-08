package ninja.shadowfox.shadowfox_botany.client.core.nei

import net.minecraft.item.ItemStack
import net.minecraft.util.StatCollector

import vazkii.botania.api.recipe.RecipePetals
import vazkii.botania.client.core.handler.HUDHandler
import vazkii.botania.common.block.tile.mana.TilePool

import ninja.shadowfox.shadowfox_botany.api.ShadowFoxAPI
import ninja.shadowfox.shadowfox_botany.api.recipe.RecipeTreeCrafting
import ninja.shadowfox.shadowfox_botany.common.blocks.ShadowFoxBlocks

import codechicken.nei.PositionedStack
import vazkii.botania.client.integration.nei.RecipeHandlerPetalApothecary


class RecipeHandlerTreeCrafting : RecipeHandlerPetalApothecary() {
    inner class CachedTreeCrafterRecipe internal constructor(recipe: RecipeTreeCrafting?) : RecipeHandlerPetalApothecary.CachedPetalApothecaryRecipe(recipe, false) {
        internal var manaUsage = 0

        init {

            if (recipe != null) {
                manaUsage = recipe.getManaUsage()

                inputs.add(PositionedStack(ItemStack(ShadowFoxBlocks.irisSapling), 73, 55))
            }
        }
    }

    override fun getRecipeName(): String = StatCollector.translateToLocal("shadowfox_botany.nei.treeCrafter")
    override fun getRecipeID(): String = "shadowfox_botany.treeCrafter"

    override fun drawBackground(recipe: Int) {
        super.drawBackground(recipe)
        HUDHandler.renderManaBar(32, 113, 0x0000FF, 0.75F, (arecipes.get(recipe) as CachedTreeCrafterRecipe).manaUsage, TilePool.MAX_MANA / 10)
    }

    override fun getRecipes(): MutableList<out RecipePetals> {
        return ShadowFoxAPI.treeRecipes
    }

    override fun getCachedRecipe(recipe: RecipePetals): CachedPetalApothecaryRecipe {
        return CachedTreeCrafterRecipe(recipe as RecipeTreeCrafting)
    }
}
