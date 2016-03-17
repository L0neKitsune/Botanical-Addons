package ninja.shadowfox.shadowfox_botany.common.blocks.base

import net.minecraft.block.properties.PropertyEnum
import net.minecraft.util.IStringSerializable
import ninja.shadowfox.shadowfox_botany.common.blocks.base.Property

/**
 * kitsune
 * created on 3/6/16
 */
interface IVariantArrayHolder {

    val varientArray: Array<Property>

    companion object {

        val HEADER = "variant"
    }

}
