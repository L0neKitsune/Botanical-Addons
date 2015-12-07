package ninja.shadowfox.shadowfox_botany.api.recipe

import cpw.mods.fml.common.FMLLog
import net.minecraft.block.Block
import net.minecraft.item.ItemStack
import ninja.shadowfox.shadowfox_botany.common.utils.itemEquals
import org.apache.logging.log4j.Level
import java.util.*


public class RecipeTreeCrafting(val mana: Int, val output: Block, val meta: Int, vararg inputs: Any) {
    internal val inputs: ArrayList<Any>

    init {
        val inputsToSet = ArrayList<Any>()
        val var4 = inputs
        val var5 = inputs.size

        for (var6 in 0..var5 - 1) {
            val obj = var4[var6]
            if (obj !is String && obj !is ItemStack) {
                throw IllegalArgumentException("Invalid input")
            }

            inputsToSet.add(obj)
        }

        this.inputs = inputsToSet
    }


    fun matches(items: List<ItemStack>): Boolean {
        var inputsMissing = ArrayList(inputs)

        for (i in items) {
            for (j in inputsMissing.indices) {
                if (i.itemEquals(inputsMissing[j])) inputsMissing.removeAt(j) ; break
            }
        }

        FMLLog.log(Level.INFO, "Missing: [$inputsMissing]")
        return inputsMissing.isEmpty()
    }

}
