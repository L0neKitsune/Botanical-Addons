package ninja.shadowfox.shadowfox_botany.common.item

import net.minecraft.item.Item
import ninja.shadowfox.shadowfox_botany.common.item.baubles.ItemAesirEmblem
import ninja.shadowfox.shadowfox_botany.common.item.baubles.ItemPriestEmblem
import ninja.shadowfox.shadowfox_botany.common.item.baubles.ItemCoatOfArms
import ninja.shadowfox.shadowfox_botany.common.item.rods.ColorfulSkyDirtRod
import ninja.shadowfox.shadowfox_botany.common.item.rods.LightningRod
import ninja.shadowfox.shadowfox_botany.common.item.rods.TestingRod


object ShadowFoxItems {
    val irisSeeds: Item
    val colorfulSkyDirtRod: Item
    val lightningRod: Item
    val emblem: Item
    val aesirEmblem: Item
    val coatOfArms: Item
    val testingRod: Item
    val splashPotion: Item

    init {
        irisSeeds = ItemColorSeeds()
        colorfulSkyDirtRod = ColorfulSkyDirtRod()
        lightningRod = LightningRod()
        emblem = ItemPriestEmblem()
        aesirEmblem = ItemAesirEmblem()
        coatOfArms = ItemCoatOfArms()
        testingRod = TestingRod()
        splashPotion = ItemSplashPotion()
    }

}
