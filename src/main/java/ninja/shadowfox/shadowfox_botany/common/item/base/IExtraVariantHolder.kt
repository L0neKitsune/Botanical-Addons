package ninja.shadowfox.shadowfox_botany.common.item.base

import ninja.shadowfox.shadowfox_botany.common.item.base.IVariantHolder

/**
 * kitsune
 * created on 3/6/16
 */
interface IExtraVariantHolder : IVariantHolder {
    fun getExtraVariants(): Array<String>
}
