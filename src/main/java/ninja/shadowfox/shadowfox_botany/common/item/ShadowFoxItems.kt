package ninja.shadowfox.shadowfox_botany.common.item

import net.minecraft.item.Item
import ninja.shadowfox.shadowfox_botany.common.item.rods.ColorfulSkyDirtRod
import ninja.shadowfox.shadowfox_botany.common.item.rods.LightningRod

/**
 * Created by l0nekitsune on 10/19/15.
 */
object ShadowFoxItems {
    val irisSeeds: Item
    val colorfulSkyDirtRod: Item
    val lightningRod: Item

    init {
        irisSeeds = ItemColorSeeds()
        colorfulSkyDirtRod = ColorfulSkyDirtRod()
        lightningRod = LightningRod()
    }
}
