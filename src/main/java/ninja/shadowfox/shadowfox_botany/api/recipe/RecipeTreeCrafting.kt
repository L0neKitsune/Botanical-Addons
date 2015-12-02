package ninja.shadowfox.shadowfox_botany.api.recipe

import akka.event.Logging
import cpw.mods.fml.common.FMLLog
import net.minecraft.block.Block
import net.minecraft.item.ItemStack
import net.minecraftforge.oredict.OreDictionary
import org.apache.logging.log4j.Level
import java.util.*
import kotlin.properties.Delegates


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
        FMLLog.log(Level.ERROR, "Inputs $inputs")
        var inputsMissing = ArrayList(inputs)

        for (i in items) {
            for (j in inputsMissing.indices) {
                if (inputsMissing[j] is String) {

                    for (stack in OreDictionary.getOres(inputsMissing[j] as String)) {
                        val cstack = stack.copy()
                        if (cstack.itemDamage == 32767) {
                            cstack.itemDamage = i.itemDamage
                        }

                        if (i.isItemEqual(cstack)) {
                            inputsMissing.removeAt(j)
                            break
                        }
                    }

                } else if (inputsMissing[j] is ItemStack && this.simpleAreStacksEqual(inputsMissing[j] as ItemStack, i)) {
                    inputsMissing.removeAt(j)
                    break
                } else {
                    FMLLog.log(Level.ERROR, "WTF $j : ${j.javaClass}")
                    break
                }
            }
        }

        FMLLog.log(Level.ERROR, "WTF $inputsMissing")

        return inputsMissing.isEmpty()
    }

    fun getManaUsage(): Int = mana

    internal fun simpleAreStacksEqual(stack: ItemStack, stack2: ItemStack): Boolean {
        return stack.item === stack2.item && stack.itemDamage == stack2.itemDamage
    }
}
