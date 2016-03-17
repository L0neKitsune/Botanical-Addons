package ninja.shadowfox.shadowfox_botany.common.item.base

import net.minecraft.client.renderer.ItemMeshDefinition

/**
 * kitsune
 * created on 3/6/16
 */
interface IVariantHolder {

    fun getVariants(): Array<String>
    fun customMeshDefinition(): ItemMeshDefinition?

}
