package ninja.shadowfox.shadowfox_botany.common.blocks.base

import net.minecraft.block.properties.IProperty
import net.minecraft.item.EnumRarity
import net.minecraft.item.ItemStack
import ninja.shadowfox.shadowfox_botany.common.item.base.IVariantHolder

/**
 * kitsune
 * created on 3/6/16
 */
interface IKitsuneBlock : IVariantHolder {

    val bareName: String
    val ignoredProperties: List<IProperty<*>>

    fun getBlockRarity(stack: ItemStack): EnumRarity
}
