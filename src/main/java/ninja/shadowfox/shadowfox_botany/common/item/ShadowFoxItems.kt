package ninja.shadowfox.shadowfox_botany.common.item

import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraftforge.oredict.OreDictionary
import ninja.shadowfox.shadowfox_botany.common.item.baubles.*
import ninja.shadowfox.shadowfox_botany.common.item.rods.ItemInterdictionRod
import ninja.shadowfox.shadowfox_botany.common.item.rods.ItemIridescentRod
import ninja.shadowfox.shadowfox_botany.common.item.rods.ItemLightningRod
import ninja.shadowfox.shadowfox_botany.common.item.rods.ItemRainbowLightRod
import ninja.shadowfox.shadowfox_botany.lib.LibOreDict

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
    val colorOverride: Item
    val attributionBauble: Item
    val wiltedLotus: Item
    val resource: Item
    val toolbelt: Item

    val fireGrenade: Item
    // val splashPotion: Item

    init {
        irisSeeds = ItemColorSeeds()
        colorfulSkyDirtRod = ItemIridescentRod()
        rainbowRod = ItemRainbowLightRod()
        lightningRod = ItemLightningRod()
        interdictionRod = ItemInterdictionRod()
        emblem = ItemPriestEmblem()
        aesirEmblem = ItemAesirEmblem()
        coatOfArms = ItemCoatOfArms()
        invisibleFlameLens = ItemLensFlashInvisible()
        colorOverride = ItemColorOverride()
        attributionBauble = ItemAttributionBauble()
        wiltedLotus = ItemWiltedLotus()
        resource = ItemResource()
        toolbelt = ItemToolbelt()

        // splashPotion = ItemSplashPotion()
        fireGrenade = ItemFireGrenade()
        initOreDict()
    }

    fun initOreDict() {

        OreDictionary.registerOre(LibOreDict.TWIG_THUNDERWOOD, ItemStack(resource, 1, 0))
        OreDictionary.registerOre(LibOreDict.SPLINTERS_THUNDERWOOD, ItemStack(resource, 1, 1))
        OreDictionary.registerOre(LibOreDict.TWIG_NETHERWOOD, ItemStack(resource, 1, 2))
        OreDictionary.registerOre(LibOreDict.SPLINTERS_NETHERWOOD, ItemStack(resource, 1, 3))
        OreDictionary.registerOre(LibOreDict.COAL_NETHERWOOD, ItemStack(resource, 1, 4))

        OreDictionary.registerOre(LibOreDict.HOLY_PENDANT, ItemStack(attributionBauble, 1, 0))
        OreDictionary.registerOre(LibOreDict.HOLY_PENDANT, ItemStack(attributionBauble, 1, 1))
    }
}
