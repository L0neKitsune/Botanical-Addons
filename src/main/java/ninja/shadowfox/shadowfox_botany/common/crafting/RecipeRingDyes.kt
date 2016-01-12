package ninja.shadowfox.shadowfox_botany.common.crafting

import net.minecraft.entity.passive.EntitySheep
import net.minecraft.inventory.InventoryCrafting
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.IRecipe
import net.minecraft.world.World
import ninja.shadowfox.shadowfox_botany.common.item.baubles.ItemColorOverride
import java.awt.Color
import vazkii.botania.common.item.ModItems as BotaniaItems

class RecipeRingDyes : IRecipe {
    /**
     * Used to check if a recipe matches current crafting inventory
     */
    override fun matches(inventory: InventoryCrafting, world: World?): Boolean {
        var itemstack: ItemStack? = null

        var colors = 0

        for (i in 0..(inventory.sizeInventory - 1)) {
            var tempstack = inventory.getStackInSlot(i)

            if (tempstack != null) {
                if (tempstack.item is ItemColorOverride) {
                    if (itemstack != null)
                        return false
                    itemstack = tempstack
                    if ((itemstack.item as ItemColorOverride).hasColor(tempstack)) colors++
                } else if (tempstack.item == BotaniaItems.dye)
                    colors++
                else
                    return false
            }
        }
        return colors > 0 && itemstack != null
    }

    /**
     * Returns an Item that is the result of this recipe
     */
    override fun getCraftingResult(inventory: InventoryCrafting): ItemStack? {
        var itemstack: ItemStack? = null
        var colorOverride: ItemColorOverride? = null

        var resetcolor = true

        var colors = 0
        var r = 0
        var g = 0
        var b = 0

        for (k in 0..(inventory.sizeInventory - 1)) {
            var tempstack = inventory.getStackInSlot(k)

            if (tempstack != null) {
                if (tempstack.item is ItemColorOverride) {
                    colorOverride = tempstack.item as ItemColorOverride

                    if (itemstack != null)
                        return null

                    itemstack = tempstack.copy()
                    itemstack.stackSize = 1

                    if (colorOverride.hasColor(tempstack)) {
                        var color = Color(colorOverride.getColor(itemstack))
                        colors++
                        r += color.red
                        g += color.green
                        b += color.blue
                    }
                } else {
                    if (tempstack.item != BotaniaItems.dye)
                        return null

                    var dyecolortable = EntitySheep.fleeceColorTable[tempstack.itemDamage]
                    var dyecolor = Color(dyecolortable[0], dyecolortable[1], dyecolortable[2])
                    colors++
                    r += dyecolor.red
                    g += dyecolor.green
                    b += dyecolor.blue
                    resetcolor = false
                }
            }
        }
        if (colorOverride != null && itemstack != null && colors > 0) {
            if (resetcolor)
                colorOverride.removeColor(itemstack)
            else {
                var color = Color(r.toFloat() / colors.toFloat() / 255f, g.toFloat() / colors.toFloat() / 255f, b.toFloat() / colors.toFloat() / 255f).rgb and 0xFFFFFF
                colorOverride.setColor(itemstack, color)
            }
            return itemstack
        }
        return null
    }

    /**
     * Returns the size of the recipe area
     */
    override fun getRecipeSize(): Int = 10

    override fun getRecipeOutput(): ItemStack? = null
}
