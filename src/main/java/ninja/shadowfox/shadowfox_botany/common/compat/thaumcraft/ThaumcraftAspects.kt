package ninja.shadowfox.shadowfox_botany.common.compat.thaumcraft

import cpw.mods.fml.common.Loader

import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.item.ItemStack

import net.minecraftforge.oredict.OreDictionary

import thaumcraft.api.ThaumcraftApi
import thaumcraft.api.ThaumcraftApiHelper
import thaumcraft.api.aspects.Aspect
import thaumcraft.api.aspects.AspectList

import ninja.shadowfox.shadowfox_botany.common.blocks.ShadowFoxBlocks
import ninja.shadowfox.shadowfox_botany.common.entity.ShadowFoxEntity
import ninja.shadowfox.shadowfox_botany.common.item.ShadowFoxItems

import kotlin.properties.Delegates

object ThaumcraftAspects {

    fun WildStack(i: Block): ItemStack = ItemStack(i, 1, OreDictionary.WILDCARD_VALUE)
    fun WildStack(i: Item): ItemStack = ItemStack(i, 1, OreDictionary.WILDCARD_VALUE)

    fun forMeta(cap: Int, lambda: (Int) -> Unit) {
        for (i in 0..cap) {
            lambda.invoke(i)
        }
    }

    init {
        var forbidden = false
        var NETHER: Aspect? = null
        var PRIDE: Aspect? = null
        var WRATH: Aspect? = null
        var hellAspect = Aspect.FIRE
        if (Loader.isModLoaded("ForbiddenMagic")) {
            NETHER = Aspect.aspects.get("infernus")
            PRIDE = Aspect.aspects.get("superbia")
            WRATH = Aspect.aspects.get("ira")
            forbidden = true
            hellAspect = NETHER
        }

        val splinterlist = AspectList().add(Aspect.TREE, 1).add(Aspect.ENTROPY, 1)

        var list = AspectList().add(Aspect.EARTH, 2).add(Aspect.SENSES, 1)
        ThaumcraftApi.registerObjectTag(WildStack(ShadowFoxBlocks.coloredDirtBlock), list)
        ThaumcraftApi.registerObjectTag(WildStack(ShadowFoxBlocks.rainbowDirtBlock), list)

        list = AspectList().add(Aspect.PLANT, 2).add(Aspect.TREE, 1).add(Aspect.SENSES, 1)
        ThaumcraftApi.registerObjectTag(ItemStack(ShadowFoxBlocks.irisSapling), list)
        
        list = AspectList().add(Aspect.PLANT, 1).add(Aspect.AIR, 1).add(Aspect.SENSES, 1)
        ThaumcraftApi.registerObjectTag(WildStack(ShadowFoxBlocks.irisGrass), list)
        ThaumcraftApi.registerObjectTag(WildStack(ShadowFoxBlocks.rainbowGrass), list)
        ThaumcraftApi.registerObjectTag(WildStack(ShadowFoxBlocks.irisTallGrass0), list)
        ThaumcraftApi.registerObjectTag(WildStack(ShadowFoxBlocks.irisTallGrass1), list)
        ThaumcraftApi.registerObjectTag(WildStack(ShadowFoxBlocks.rainbowTallGrass), list)
        
        list = AspectList().add(Aspect.TREE, 4).add(Aspect.SENSES, 1)
        forMeta(4,
            {ThaumcraftApi.registerObjectTag(ItemStack(ShadowFoxBlocks.irisWood0, 1, it), list)})
        forMeta(4,
            {ThaumcraftApi.registerObjectTag(ItemStack(ShadowFoxBlocks.irisWood1, 1, it), list)})
        forMeta(4,
            {ThaumcraftApi.registerObjectTag(ItemStack(ShadowFoxBlocks.irisWood2, 1, it), list)})
        forMeta(4,
            {ThaumcraftApi.registerObjectTag(ItemStack(ShadowFoxBlocks.irisWood3, 1, it), list)})
        ThaumcraftApi.registerObjectTag(ItemStack(ShadowFoxBlocks.rainbowWood), list)

        list = AspectList().add(Aspect.TREE, 1).add(Aspect.METAL, 1)
        ThaumcraftApi.registerObjectTag(WildStack(ShadowFoxBlocks.itemDisplay), list)

        list = AspectList().add(Aspect.TREE, 8).add(Aspect.MAGIC, 8).add(Aspect.CRAFT, 4)
        ThaumcraftApi.registerObjectTag(WildStack(ShadowFoxBlocks.treeCrafterBlock), list)
        ThaumcraftApi.registerObjectTag(WildStack(ShadowFoxBlocks.treeCrafterBlockRB), list)

        list = AspectList().add(Aspect.PLANT, 2).add(Aspect.TREE, 1).add(Aspect.WEATHER, 1)
        ThaumcraftApi.registerObjectTag(ItemStack(ShadowFoxBlocks.lightningSapling), list)

        list = AspectList().add(Aspect.TREE, 4).add(Aspect.WEATHER, 1)
        ThaumcraftApi.registerObjectTag(ItemStack(ShadowFoxBlocks.lightningWood), list)

        list = AspectList().add(Aspect.TREE, 20).add(Aspect.MECHANISM, 1).add(Aspect.EXCHANGE, 1).add(Aspect.VOID, 4)
        ThaumcraftApi.registerObjectTag(WildStack(ShadowFoxBlocks.livingwoodFunnel), list)

        list = AspectList().add(Aspect.PLANT, 2).add(Aspect.TREE, 1).add(hellAspect, 1)
        ThaumcraftApi.registerObjectTag(ItemStack(ShadowFoxBlocks.netherSapling), list)
        list = AspectList().add(Aspect.TREE, 4).add(hellAspect, 1)
        ThaumcraftApi.registerObjectTag(ItemStack(ShadowFoxBlocks.netherWood), list)

        list = AspectList().add(Aspect.CLOTH, 4).add(Aspect.FIRE, 2).add(Aspect.MAGIC, 2)
        ThaumcraftApi.registerObjectTag(WildStack(ShadowFoxBlocks.kindling), list)

        list = AspectList().add(Aspect.PLANT, 1).add(Aspect.EXCHANGE, 1).add(Aspect.SENSES, 1)
        ThaumcraftApi.registerObjectTag(WildStack(ShadowFoxItems.irisSeeds), list)

        list = AspectList().add(Aspect.TOOL, 8).add(Aspect.EARTH, 6).add(Aspect.AIR, 2).add(Aspect.MAGIC, 4)
        ThaumcraftApi.registerObjectTag(WildStack(ShadowFoxItems.colorfulSkyDirtRod), list)

        list = AspectList().add(Aspect.TOOL, 8).add(Aspect.LIGHT, 8).add(Aspect.MAGIC, 4)
        ThaumcraftApi.registerObjectTag(WildStack(ShadowFoxItems.rainbowRod), list)

        list = AspectList().add(Aspect.TOOL, 8).add(Aspect.WEATHER, 8).add(Aspect.MAGIC, 4)
        ThaumcraftApi.registerObjectTag(WildStack(ShadowFoxItems.lightningRod), list)

        if (forbidden) 
            list = AspectList().add(Aspect.TOOL, 8).add(Aspect.AIR, 4).add(Aspect.WEATHER, 2).add(PRIDE!!, 2).add(Aspect.MAGIC, 4)
        else
            list = AspectList().add(Aspect.TOOL, 8).add(Aspect.AIR, 6).add(Aspect.WEATHER, 2).add(Aspect.MAGIC, 4)
        ThaumcraftApi.registerObjectTag(WildStack(ShadowFoxItems.interdictionRod), list)

        list = AspectList().add(Aspect.ELDRITCH, 5).add(Aspect.WEATHER, 16)
        ThaumcraftApi.registerObjectTag(ItemStack(ShadowFoxItems.emblem, 1, 0), list)
        list = AspectList().add(Aspect.ELDRITCH, 5).add(Aspect.EARTH, 16)
        ThaumcraftApi.registerObjectTag(ItemStack(ShadowFoxItems.emblem, 1, 1), list)
        if (forbidden)
            list = AspectList().add(Aspect.ELDRITCH, 5).add(PRIDE!!, 16)
        else
            list = AspectList().add(Aspect.ELDRITCH, 5).add(Aspect.AIR, 16)
        ThaumcraftApi.registerObjectTag(ItemStack(ShadowFoxItems.emblem, 1, 2), list)

        list = AspectList().add(Aspect.CLOTH, 4)
        ThaumcraftApi.registerObjectTag(WildStack(ShadowFoxItems.coatOfArms), list)

        list = AspectList().add(Aspect.SENSES, 8).add(Aspect.METAL, 4)
        ThaumcraftApi.registerObjectTag(WildStack(ShadowFoxItems.colorOverride), list)

        list = AspectList().add(Aspect.CLOTH, 2).add(Aspect.GREED, 2).add(Aspect.ELDRITCH, 2)
        ThaumcraftApi.registerObjectTag(ItemStack(ShadowFoxItems.attributionBauble, 1, 0), list)

        list = AspectList().add(Aspect.CLOTH, 2).add(Aspect.GREED, 2).add(Aspect.ELDRITCH, 2).add(Aspect.CROP, 64).add(Aspect.BEAST, 64) // memes
        ThaumcraftApi.registerObjectTag(ItemStack(ShadowFoxItems.attributionBauble, 1, 1), list)

        list = AspectList().add(Aspect.MAGIC, 16).add(Aspect.TAINT, 4).add(Aspect.ENTROPY, 2)
        ThaumcraftApi.registerObjectTag(ItemStack(ShadowFoxItems.wiltedLotus, 1, 0), list)

        list = AspectList().add(Aspect.MAGIC, 20).add(Aspect.DEATH, 4).add(Aspect.ELDRITCH, 4).add(Aspect.TAINT, 4).add(Aspect.ENTROPY, 2)
        ThaumcraftApi.registerObjectTag(ItemStack(ShadowFoxItems.wiltedLotus, 1, 1), list)

        list = AspectList().add(Aspect.TOOL, 2).add(Aspect.TREE, 2).add(Aspect.WEATHER, 2)
        ThaumcraftApi.registerObjectTag(ItemStack(ShadowFoxItems.resource, 1, 0), list)

        ThaumcraftApi.registerObjectTag(ItemStack(ShadowFoxItems.resource, 1, 1), splinterlist)

        list = AspectList().add(Aspect.TOOL, 2).add(Aspect.TREE, 2).add(hellAspect, 2)
        ThaumcraftApi.registerObjectTag(ItemStack(ShadowFoxItems.resource, 1, 2), list)

        ThaumcraftApi.registerObjectTag(ItemStack(ShadowFoxItems.resource, 1, 3), splinterlist)

        list = AspectList().add(Aspect.FIRE, 4).add(Aspect.ENERGY, 2)
        if (forbidden) list.add(NETHER!!, 2)
        ThaumcraftApi.registerObjectTag(ItemStack(ShadowFoxItems.resource, 1, 4), list)

        list = AspectList().add(Aspect.TAINT, 2).add(Aspect.ENTROPY, 2).add(Aspect.PLANT, 2)
        if (forbidden) list.add(WRATH, 2)
        ThaumcraftApi.registerEntityTag("shadowfox_botany:voidCreeper", list)

        list = AspectList().add(Aspect.ENTROPY, 16).add(Aspect.PLANT, 2) // don't fool around with these guys
        if (forbidden) list.add(WRATH, 8)                                // they will mess. you. up.
        ThaumcraftApi.registerEntityTag("shadowfox_botany:grieferCreeper", list)
    }
}
