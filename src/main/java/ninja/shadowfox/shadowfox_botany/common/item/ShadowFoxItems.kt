package ninja.shadowfox.shadowfox_botany.common.item

import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraftforge.oredict.OreDictionary
import ninja.shadowfox.shadowfox_botany.common.item.baubles.*
import ninja.shadowfox.shadowfox_botany.common.item.rods.*


object ShadowFoxItems {
    val irisSeeds: Item
    val colorfulSkyDirtRod: Item
    val rainbowRod: Item
    val lightningRod: Item
    val interdictionRod: Item
    val emblem: Item
    val aesirEmblem: Item
    val coatOfArms: Item
    val invisibleFlameLens: Item
//    val testingRod: Item
//    val splashPotion: Item
    val colorOverride: Item
    val attributionBauble: Item
    val wiltedLotus: Item

    init {
        irisSeeds = ItemColorSeeds()
        colorfulSkyDirtRod = ColorfulSkyDirtRod()
        rainbowRod = RainbowLightRod()
        lightningRod = LightningRod()
        interdictionRod = InterdictionRod()
        emblem = ItemPriestEmblem()
        aesirEmblem = ItemAesirEmblem()
        coatOfArms = ItemCoatOfArms()
        invisibleFlameLens = ItemLensFlashInvisible()
//        testingRod = TestingRod()
//        splashPotion = ItemSplashPotion()
        colorOverride = ItemColorOverride()
        attributionBauble = ItemAttributionBauble()
        wiltedLotus = ItemWiltedLotus()

        initOreDict()
    }

    fun initOreDict() {
        OreDictionary.registerOre("holyPendant", ItemStack(attributionBauble, 1, 0))
        OreDictionary.registerOre("holyPendant", ItemStack(attributionBauble, 1, 1))
    }
}
