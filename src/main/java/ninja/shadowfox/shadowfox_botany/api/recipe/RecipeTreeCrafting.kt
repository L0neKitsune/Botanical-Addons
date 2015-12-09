package ninja.shadowfox.shadowfox_botany.api.recipe

import cpw.mods.fml.common.FMLLog
import net.minecraft.block.Block
import net.minecraft.item.ItemStack
import net.minecraftforge.oredict.OreDictionary
import org.apache.logging.log4j.Level
import java.util.*

import vazkii.botania.api.recipe.RecipePetals

internal fun ItemStack.itemEquals(rItem: Any): Boolean {
    if (rItem is String) {

        for (stack in OreDictionary.getOres(rItem)) {
            val cstack = stack.copy()

            if (cstack.itemDamage == 32767) cstack.itemDamage = this.itemDamage
            if (this.isItemEqual(cstack)) return true
        }

    } else if (rItem is ItemStack && simpleAreStacksEqual(rItem, this)) return true
    else return false
    return false
}

internal fun simpleAreStacksEqual(stack: ItemStack, stack2: ItemStack): Boolean {
    return stack.item === stack2.item && stack.itemDamage == stack2.itemDamage
}

public class RecipeTreeCrafting(val mana: Int, val outputBlock: Block, val meta: Int, vararg inputs: Any): RecipePetals(ItemStack(outputBlock, 1, meta), *inputs) {

    fun matches(items: List<ItemStack>): Boolean {
        var inputsMissing = ArrayList(inputs)

        for (i in items) {
            for (j in inputsMissing.indices) {
                var inp = inputsMissing[j]
                if (inp is ItemStack)
                    if(inp.itemDamage.toShort() == Short.MAX_VALUE)
                        inp.setItemDamage(i.itemDamage)
                if (i.itemEquals(inp)) {
                    inputsMissing.removeAt(j)
                    break
                }
            }
        }

        // FMLLog.log(Level.INFO, "Missing: [$inputsMissing]")
        return inputsMissing.isEmpty()
    }

    fun getManaUsage(): Int {
        return this.mana
    }

}
