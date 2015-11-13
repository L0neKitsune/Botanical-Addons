package ninja.shadowfox.shadowfox_botany.common.item

import net.minecraft.item.Item
import ninja.shadowfox.shadowfox_botany.common.item.baubles.ItemPriestEmblem
import ninja.shadowfox.shadowfox_botany.common.item.rods.ColorfulSkyDirtRod
import ninja.shadowfox.shadowfox_botany.common.item.rods.LightningRod


object ShadowFoxItems {
    val irisSeeds: Item
    val colorfulSkyDirtRod: Item
    val lightningRod: Item
    val emblem: Item

    init {
        irisSeeds = ItemColorSeeds()
        colorfulSkyDirtRod = ColorfulSkyDirtRod()
        lightningRod = LightningRod()
        emblem = ItemPriestEmblem()
    }
}
