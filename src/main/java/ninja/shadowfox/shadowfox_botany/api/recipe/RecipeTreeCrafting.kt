package ninja.shadowfox.shadowfox_botany.api.recipe

import cpw.mods.fml.common.FMLLog
import net.minecraft.block.Block
import net.minecraft.item.ItemStack
import ninja.shadowfox.shadowfox_botany.common.utils.itemEquals
import org.apache.logging.log4j.Level
import java.util.*

import vazkii.botania.api.recipe.RecipePetals


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
