package ninja.shadowfox.shadowfox_botany.client.core.nei

import codechicken.nei.NEIServerUtils
import net.minecraft.item.ItemStack
import net.minecraft.util.StatCollector

import vazkii.botania.client.core.handler.HUDHandler
import vazkii.botania.common.block.tile.mana.TilePool

import ninja.shadowfox.shadowfox_botany.api.ShadowFoxAPI
import ninja.shadowfox.shadowfox_botany.api.recipe.RecipeTreeCrafting
import ninja.shadowfox.shadowfox_botany.common.blocks.ShadowFoxBlocks

import codechicken.nei.PositionedStack
import codechicken.nei.recipe.TemplateRecipeHandler
import net.minecraft.init.Items
import net.minecraftforge.oredict.OreDictionary
import java.awt.Rectangle
import java.util.*


open class RecipeHandlerTreeCrafting : TemplateRecipeHandler() {

    override fun getRecipeName(): String = StatCollector.translateToLocal("shadowfox_botany.nei.treeCrafter")

    override fun drawBackground(recipe: Int) {
        super.drawBackground(recipe)
        HUDHandler.renderManaBar(32, 113, 0x0000FF, 0.75F, (arecipes.get(recipe) as RecipeHandlerTreeCrafting.CachedTreeRecipe).manaUsage, TilePool.MAX_MANA / 10)
    }

    open val recipeID: String
        get() = "shadowfox_botany.treeCrafter"

    override fun getGuiTexture(): String {
        return "botania:textures/gui/neiBlank.png"
    }

    override fun loadTransferRects() {
        this.transferRects.add(RecipeTransferRect(Rectangle(72, 54, 18, 18), this.recipeID, *arrayOfNulls<Any>(0)))
    }

    override fun recipiesPerPage(): Int {
        return 1
    }

    open val recipes: List<RecipeTreeCrafting>
        get() = ShadowFoxAPI.treeRecipes

    open fun getCachedRecipe(recipe: RecipeTreeCrafting): CachedTreeRecipe {
        return this.CachedTreeRecipe(recipe)
    }

    override fun loadCraftingRecipes(outputId: String, vararg results: Any) {
        if (outputId == this.recipeID) {
            val var3 = this.recipes.iterator()

            while (var3.hasNext()) {
                if (var3.next().output.item !== Items.skull) {
                    this.arecipes.add(this.getCachedRecipe(var3.next()))
                }
            }
        } else {
            super.loadCraftingRecipes(outputId, *results)
        }

    }

    override fun loadCraftingRecipes(result: ItemStack?) {
        val var2 = this.recipes.iterator()

        while (true) {
            var recipe: RecipeTreeCrafting?
            do {
                do {
                    if (!var2.hasNext()) {
                        return
                    }

                    recipe = var2.next()
                } while (recipe == null)
            } while ((recipe!!.output.stackTagCompound == null || !NEIServerUtils.areStacksSameType(recipe.output, result)) && (recipe.output.stackTagCompound != null || !NEIServerUtils.areStacksSameTypeCrafting(recipe.output, result) || recipe.output.item === Items.skull))

            this.arecipes.add(this.getCachedRecipe(recipe))
        }
    }

    override fun loadUsageRecipes(ingredient: ItemStack?) {
        val var2 = this.recipes.iterator()

        while (var2.hasNext()) {
            if (var2.next() != null) {
                val crecipe = this.getCachedRecipe(var2.next())
                if (crecipe.contains(crecipe.inputs, ingredient) && var2.next().output.item !== Items.skull) {
                    this.arecipes.add(crecipe)
                }
            }
        }

    }

    open inner class CachedTreeRecipe : CachedRecipe {
        var inputs: MutableList<PositionedStack>
        var output: PositionedStack
        var manaUsage = 0

        constructor(recipe: RecipeTreeCrafting) : super() {
            this.inputs = ArrayList()
            this.setIngredients(recipe.inputs)
            this.output = PositionedStack(recipe.output, 111, 21)

            manaUsage = recipe.mana

            inputs.add(PositionedStack(ItemStack(ShadowFoxBlocks.irisSapling), 73, 55))

        }

        fun setIngredients(inputs: MutableList<Any?>) {
            val degreePerInput = 360.0f / inputs.size.toFloat()
            var currentDegree = -90.0f

            val var4 = inputs.iterator()
            while (var4.hasNext()) {
                val o = var4.next()
                val posX = Math.round(73.0 + Math.cos(currentDegree.toDouble() * 3.141592653589793 / 180.0) * 32.0).toInt()
                val posY = Math.round(55.0 + Math.sin(currentDegree.toDouble() * 3.141592653589793 / 180.0) * 32.0).toInt()
                if (o is String) {
                    this.inputs.add(PositionedStack(OreDictionary.getOres(o), posX, posY))
                } else {
                    this.inputs.add(PositionedStack(o, posX, posY))
                }
                currentDegree += degreePerInput
            }

        }

        override fun getIngredients(): List<PositionedStack> {
            return this.getCycledIngredients(this@RecipeHandlerTreeCrafting.cycleticks / 20, this.inputs)
        }

        override fun getResult(): PositionedStack {
            return this.output
        }
    }
}
