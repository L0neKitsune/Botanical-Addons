package ninja.shadowfox.shadowfox_botany.common.item

import net.minecraft.item.Item
import ninja.shadowfox.shadowfox_botany.common.item.baubles.*
import ninja.shadowfox.shadowfox_botany.common.item.rods.*


object ShadowFoxItems {
    val irisSeeds: Item
    val colorfulSkyDirtRod: Item
    val lightningRod: Item
    val emblem: Item
    val aesirEmblem: Item
    val coatOfArms: Item
    val invisibleFlameLens: Item
//    val testingRod: Item
//    val splashPotion: Item
    val colorOverride: Item

    init {
        irisSeeds = ItemColorSeeds()
        colorfulSkyDirtRod = ColorfulSkyDirtRod()
        lightningRod = LightningRod()
        emblem = ItemPriestEmblem()
        aesirEmblem = ItemAesirEmblem()
        coatOfArms = ItemCoatOfArms()
        invisibleFlameLens = ItemLensFlashInvisible()
//        testingRod = TestingRod()
//        splashPotion = ItemSplashPotion()
        colorOverride = ItemColorOverride()
    }
}
