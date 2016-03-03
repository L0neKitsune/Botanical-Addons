package ninja.shadowfox.shadowfox_stuff.common.item

import net.minecraft.item.ItemStack
import net.minecraft.potion.PotionEffect

/**
 * 1.8 mod
 * created on 2/21/16
 */
interface IPotionContainer {
    fun getEffects(stack: ItemStack): List<PotionEffect>
    fun addEffect(stack: ItemStack, effect: PotionEffect)
}
