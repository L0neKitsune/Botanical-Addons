package ninja.shadowfox.shadowfox_botany.common.item

import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraftforge.oredict.OreDictionary
import ninja.shadowfox.shadowfox_botany.ShadowfoxBotany
import ninja.shadowfox.shadowfox_botany.common.item.baubles.*
import ninja.shadowfox.shadowfox_botany.common.item.blocks.ItemStarPlacer
import ninja.shadowfox.shadowfox_botany.common.item.creator.ItemTrisDagger
import ninja.shadowfox.shadowfox_botany.common.item.creator.ItemWireAxe
import ninja.shadowfox.shadowfox_botany.common.item.rods.*
import ninja.shadowfox.shadowfox_botany.lib.LibOreDict
import vazkii.botania.common.block.ModBlocks
import vazkii.botania.common.item.ModItems
import vazkii.botania.common.item.block.ItemBlockFloatingSpecialFlower
import vazkii.botania.common.item.block.ItemBlockSpecialFlower

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
    val flameRod: Item
    val wireAxe: Item
    val star: Item
    val trisDagger: Item
//    val fireGrenade: Item
//    val splashPotion: Item

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
        flameRod = ItemFlameRod()
        wireAxe = ItemWireAxe()
        star = ItemStarPlacer()
        trisDagger = ItemTrisDagger()
//        splashPotion = ItemSplashPotion()
//        fireGrenade = ItemFireGrenade()
        initOreDict()



        if (!ShadowfoxBotany.isDevEnv) {
            EventHandlerTooltip.register()
            EventHandlerTooltip.registerStack(ItemStack(flameRod, 1, OreDictionary.WILDCARD_VALUE))
            EventHandlerTooltip.registerStack(ItemBlockSpecialFlower.ofType("crysanthermum"))
            EventHandlerTooltip.registerStack(ItemBlockFloatingSpecialFlower.ofType("crysanthermum"))
            EventHandlerTooltip.registerStack(ItemStack(emblem, 1, 3))
        }
    }

    fun initOreDict() {

        OreDictionary.registerOre(LibOreDict.TWIG_THUNDERWOOD, ItemStack(resource, 1, 0))
        OreDictionary.registerOre(LibOreDict.SPLINTERS_THUNDERWOOD, ItemStack(resource, 1, 1))
        OreDictionary.registerOre(LibOreDict.TWIG_NETHERWOOD, ItemStack(resource, 1, 2))
        OreDictionary.registerOre(LibOreDict.SPLINTERS_NETHERWOOD, ItemStack(resource, 1, 3))
        OreDictionary.registerOre(LibOreDict.COAL_NETHERWOOD, ItemStack(resource, 1, 4))
        OreDictionary.registerOre(LibOreDict.DYES[16], ItemStack(resource, 1, 6))
        OreDictionary.registerOre(LibOreDict.FLORAL_POWDER, ItemStack(resource, 1, 6))

        OreDictionary.registerOre(LibOreDict.HOLY_PENDANT, ItemStack(attributionBauble, 1, OreDictionary.WILDCARD_VALUE))

        OreDictionary.registerOre(LibOreDict.DYES[16], ItemStack(ModBlocks.bifrostPerm))
        OreDictionary.registerOre(LibOreDict.FLORAL_POWDER, ItemStack(ModItems.dye, 1, OreDictionary.WILDCARD_VALUE))
    }
}
