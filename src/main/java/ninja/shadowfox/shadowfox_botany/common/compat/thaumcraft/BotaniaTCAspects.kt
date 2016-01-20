package ninja.shadowfox.shadowfox_botany.common.compat.thaumcraft

import cpw.mods.fml.common.Loader
import net.minecraft.block.Block
import net.minecraft.init.Blocks
import net.minecraft.init.Items
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraftforge.oredict.OreDictionary
import thaumcraft.api.ThaumcraftApi
import thaumcraft.api.aspects.Aspect
import thaumcraft.api.aspects.Aspect.*
import thaumcraft.api.aspects.AspectList
import vazkii.botania.common.block.ModBlocks
import vazkii.botania.common.block.ModFluffBlocks

/**
 * @author WireSegal
 * Created at 4:07 PM on 1/19/16.
 */
object BotaniaTCAspects {

    fun WildStack(i: Block): ItemStack = ItemStack(i, 1, OreDictionary.WILDCARD_VALUE)
    fun WildStack(i: Item): ItemStack = ItemStack(i, 1, OreDictionary.WILDCARD_VALUE)

    fun forMeta(cap: Int, lambda: (Int) -> Unit) {
        for (i in 0..cap) {
            lambda.invoke(i)
        }
    }

    fun forList(list: Array<Block>, lambda: (Block) -> Unit) {
        for (i in list) {
            lambda.invoke(i)
        }
    }

    fun initAspects() {

    }

    fun getAspect(mod: String, tag: String): Aspect? {
        if (Loader.isModLoaded(mod)) {
            try {
                return Aspect.getAspect(tag)
            } catch (e: Exception) {
            }
        }
        return null
    }

    fun AspectList.a(asp: Aspect?, n: Int = 1): AspectList {
        if (asp != null)
            this.add(asp, n)
        return this
    }

    fun addAspects() {
        val NETHER = if (Aspect.aspects.containsKey("infernus")) getAspect("ForbiddenMagic", "infernus") else FIRE
        val LUST: Aspect? = getAspect("ForbiddenMagic", "luxuria")
        val PRIDE: Aspect? = getAspect("ForbiddenMagic", "superbia")
        val GLUTTONY: Aspect? = getAspect("ForbiddenMagic", "gula")
        val ENVY: Aspect? = getAspect("ForbiddenMagic", "invidia")
        val WRATH: Aspect? = getAspect("ForbiddenMagic", "ira")
        val SLOTH: Aspect? = getAspect("ForbiddenMagic", "desidia")
        val COLOR = if (ThaumcraftAspects.COLOR == null) SENSES else ThaumcraftAspects.COLOR

        var list = AspectList().a(PLANT, 2).a(LIFE).a(COLOR)
        ThaumcraftApi.registerObjectTag(WildStack(ModBlocks.flower), list)

        list = AspectList().a(EARTH, 8).a(CRAFT, 4)
        ThaumcraftApi.registerObjectTag(WildStack(ModBlocks.altar), list)

        list = AspectList().a(EARTH, 2).a(LIFE)
        ThaumcraftApi.registerObjectTag(WildStack(ModBlocks.livingrock), list)

        list = AspectList().a(TREE, 4).a(LIFE)
        ThaumcraftApi.registerObjectTag(WildStack(ModBlocks.livingwood), list)

        list = AspectList().a(PLANT, 2).a(MAGIC, 2).a(LIFE)
        ThaumcraftApi.registerObjectTag(WildStack(ModBlocks.specialFlower), list)

        list = AspectList().a(MAGIC, 2).a(MOTION, 2)
        ThaumcraftApi.registerObjectTag(WildStack(ModBlocks.spreader), list)

        list = AspectList().a(EARTH, 8).a(MAGIC, 2).a(VOID, 2)
        ThaumcraftApi.registerObjectTag(WildStack(ModBlocks.pool), list)

        list = AspectList().a(MAGIC, 8).a(CRAFT, 4).a(EARTH, 4)
        ThaumcraftApi.registerObjectTag(WildStack(ModBlocks.runeAltar), list)

        list = AspectList().a(MAGIC, 2).a(ENTROPY, 2).add(COLOR, 2)
        ThaumcraftApi.registerObjectTag(WildStack(ModBlocks.unstableBlock), list)

        list = AspectList().a(MAGIC, 4).a(GREED, 4).a(METAL, 2).a(CRYSTAL, 2)
        ThaumcraftApi.registerObjectTag(ItemStack(ModBlocks.pylon, 1, 0), list) // Mana Pylon

        list = AspectList().a(MAGIC, 8).a(EARTH, 2).a(METAL, 2).a(ELDRITCH, 2).a(CRYSTAL, 2).a(ENVY, 2)
        ThaumcraftApi.registerObjectTag(ItemStack(ModBlocks.pylon, 1, 1), list) // Natura Pylon

        list = AspectList().a(MAGIC, 8).a(METAL, 2).a(ELDRITCH, 2).a(CRYSTAL, 2)
        ThaumcraftApi.registerObjectTag(ItemStack(ModBlocks.pylon, 1, 2), list) // Gaia Pylon

        list = AspectList().a(MOTION, 2).a(MECHANISM, 4).a(MAGIC)
        ThaumcraftApi.registerObjectTag(WildStack(ModBlocks.pistonRelay), list)

        list = AspectList().a(VOID, 2).a(MOTION, 2).a(MAGIC, 2)
        ThaumcraftApi.registerObjectTag(WildStack(ModBlocks.distributor), list)

        list = AspectList().a(MAGIC, 2).a(ENTROPY, 2).a(COLOR, 2)
        ThaumcraftApi.registerObjectTag(WildStack(ModBlocks.manaBeacon), list)

        list = AspectList().a(VOID, 6).a(MAGIC, 2)
        ThaumcraftApi.registerObjectTag(WildStack(ModBlocks.manaVoid), list)

        list = AspectList().a(MECHANISM, 6).a(MAGIC, 2)
        ThaumcraftApi.registerObjectTag(WildStack(ModBlocks.manaDetector), list)

        list = AspectList().a(MAGIC, 8).a(CRAFT, 4).a(SENSES, 2)
        ThaumcraftApi.registerObjectTag(WildStack(ModBlocks.enchanter), list)

        list = AspectList().a(MOTION, 2).a(MECHANISM, 4)
        ThaumcraftApi.registerObjectTag(WildStack(ModBlocks.turntable), list)

        list = AspectList().a(EARTH, 64).a(HUNGER, 12).a(MAGIC, 2) // It's REALLY heavy.
        ThaumcraftApi.registerObjectTag(WildStack(ModBlocks.tinyPlanet), list)

        list = AspectList().a(MAGIC, 8).a(EXCHANGE, 8)
        ThaumcraftApi.registerObjectTag(WildStack(ModBlocks.alchemyCatalyst), list)

        list = AspectList().a(TREE, 4).a(VOID, 2)
        ThaumcraftApi.registerObjectTag(WildStack(ModBlocks.openCrate), list)

        list = AspectList().a(SENSES, 4).a(MECHANISM, 2)
        ThaumcraftApi.registerObjectTag(WildStack(ModBlocks.forestEye), list)

        list = AspectList().add(HARVEST, 12)
        ThaumcraftApi.registerObjectTag(WildStack(ModBlocks.forestDrum), list)

        list = AspectList().a(PLANT, 2).a(LIFE).a(COLOR).a(LIGHT)
        ThaumcraftApi.registerObjectTag(WildStack(ModBlocks.shinyFlower), list)

        list = AspectList().a(TREE, 2).a(SOUL, 2).a(ELDRITCH)
        ThaumcraftApi.registerObjectTag(ItemStack(ModBlocks.platform, 1, 0), list)
        ThaumcraftApi.registerObjectTag(ItemStack(ModBlocks.platform, 1, 1), list)
        // Nothing for the Infrangible Platform.

        list = AspectList().a(ELDRITCH, 4).a(Aspect.MECHANISM, 4).a(Aspect.TRAVEL, 4)
        ThaumcraftApi.registerObjectTag(WildStack(ModBlocks.alfPortal), list)

        list = AspectList().a(TREE, 4).a(ELDRITCH)
        ThaumcraftApi.registerObjectTag(WildStack(ModBlocks.dreamwood), list)

        list = AspectList().a(MAGIC, 8).a(ORDER, 8).a(ELDRITCH)
        ThaumcraftApi.registerObjectTag(WildStack(ModBlocks.conjurationCatalyst), list)

        list = AspectList().a(COLOR, 8)
        ThaumcraftApi.registerObjectTag(WildStack(ModBlocks.bifrost), list)

        list = AspectList().a(PLANT, 1)
        ThaumcraftApi.registerObjectTag(WildStack(ModBlocks.solidVines), list)

        list = AspectList().a(PLANT, 1).a(COLOR, 1)
        ThaumcraftApi.registerObjectTag(WildStack(ModBlocks.buriedPetals), list)

        list = AspectList().a(WATER, 2).a(CRYSTAL, 2)
        ThaumcraftApi.registerObjectTag(WildStack(ModBlocks.prismarine), list)

        list = AspectList().a(WATER, 2).a(CRYSTAL, 2).a(LIGHT, 2)
        ThaumcraftApi.registerObjectTag(WildStack(ModBlocks.seaLamp), list)

        list = AspectList().a(PLANT, 2).a(LIFE).a(LIGHT).a(AIR).a(EARTH)
        ThaumcraftApi.registerObjectTag(WildStack(ModBlocks.floatingFlower), list)

        list = AspectList().a(LIFE, 2).a(CROP, 2)
        ThaumcraftApi.registerObjectTag(WildStack(ModBlocks.tinyPotato), list)

        list = AspectList().a(LIFE, 16).a(MAGIC, 8)
        ThaumcraftApi.registerObjectTag(WildStack(ModBlocks.spawnerClaw), list)

        list = AspectList().a(EARTH, 2).a(NETHER)
        ThaumcraftApi.registerObjectTag(ItemStack(ModBlocks.customBrick, 1, 0), list) // Hellish Brick

        list = AspectList().a(EARTH, 2).a(SOUL)
        ThaumcraftApi.registerObjectTag(ItemStack(ModBlocks.customBrick, 1, 1), list) // Soul Brick

        list = AspectList().a(EARTH, 2).a(COLD)
        ThaumcraftApi.registerObjectTag(ItemStack(ModBlocks.customBrick, 1, 2), list) // Frosty Brick

        list = AspectList().a(SENSES, 4).a(MECHANISM, 2).a(ENVY, 2)
        ThaumcraftApi.registerObjectTag(WildStack(ModBlocks.enderEye), list)

        list = AspectList().a(LIGHT, 8).a(METAL, 4)
        ThaumcraftApi.registerObjectTag(WildStack(ModBlocks.starfield), list)

        list = AspectList().a(MECHANISM, 16).a(ENERGY, 8).a(SLOTH, 8).a(GLUTTONY, 4) // If you can't tell, I don't like RF.
        ThaumcraftApi.registerObjectTag(WildStack(ModBlocks.rfGenerator), list)

        list = AspectList().a(CRYSTAL).a(ELDRITCH)
        ThaumcraftApi.registerObjectTag(WildStack(ModBlocks.elfGlass), list)

        list = AspectList(ItemStack(Blocks.brewing_stand)).a(MAGIC, 2)
        ThaumcraftApi.registerObjectTag(WildStack(ModBlocks.brewery), list)

        list = AspectList().a(CRYSTAL).a(MAGIC)
        ThaumcraftApi.registerObjectTag(WildStack(ModBlocks.manaGlass), list)

        list = AspectList().a(CRAFT, 4).a(FIRE, 2).a(WATER, 2).a(EARTH, 2).a(AIR, 2).a(MAGIC, 2)
        ThaumcraftApi.registerObjectTag(WildStack(ModBlocks.terraPlate), list)

        list = AspectList().a(MECHANISM, 4).a(ELDRITCH, 2)
        ThaumcraftApi.registerObjectTag(WildStack(ModBlocks.redStringContainer), list)
        ThaumcraftApi.registerObjectTag(WildStack(ModBlocks.redStringDispenser), list)
        ThaumcraftApi.registerObjectTag(WildStack(ModBlocks.redStringFertilizer), list)
        ThaumcraftApi.registerObjectTag(WildStack(ModBlocks.redStringComparator), list)
        ThaumcraftApi.registerObjectTag(WildStack(ModBlocks.redStringRelay), list)

        list = AspectList().a(PLANT, 2).a(MAGIC, 2).a(LIFE).a(COLOR).a(LIGHT).a(AIR).a(EARTH) // So many aspects
        ThaumcraftApi.registerObjectTag(WildStack(ModBlocks.floatingSpecialFlower), list)

        list = AspectList().a(LIGHT, 2).a(COLOR, 2)
        ThaumcraftApi.registerObjectTag(WildStack(ModBlocks.manaFlame), list)

        list = AspectList().a(SOUL, 2).a(CRYSTAL, 2)
        ThaumcraftApi.registerObjectTag(WildStack(ModBlocks.prism), list)

        list = AspectList(ItemStack(Blocks.dirt))
        ThaumcraftApi.registerObjectTag(WildStack(ModBlocks.dirtPath), list)

        list = AspectList().a(EARTH, 2).a(LIFE, 2).a(MAGIC, 2)
        ThaumcraftApi.registerObjectTag(WildStack(ModBlocks.enchantedSoil), list)

        list = AspectList().a(ELDRITCH, 4).a(VOID, 2)
        ThaumcraftApi.registerObjectTag(WildStack(ModBlocks.corporeaIndex), list)
        ThaumcraftApi.registerObjectTag(WildStack(ModBlocks.corporeaFunnel), list)

        list = AspectList(ItemStack(Blocks.brown_mushroom)).a(COLOR, 2)
        ThaumcraftApi.registerObjectTag(WildStack(ModBlocks.mushroom), list)

        list = AspectList().a(MECHANISM, 4).a(MOTION, 2).a(MAGIC, 2).a(WATER, 2).a(VOID, 2)
        ThaumcraftApi.registerObjectTag(WildStack(ModBlocks.pump), list)

        list = AspectList().a(PLANT, 2).a(LIFE).a(COLOR)
        ThaumcraftApi.registerObjectTag(WildStack(ModBlocks.doubleFlower1), list)
        ThaumcraftApi.registerObjectTag(WildStack(ModBlocks.doubleFlower2), list)

        list = AspectList().a(ELDRITCH, 4).a(VOID, 2)
        ThaumcraftApi.registerObjectTag(WildStack(ModBlocks.corporeaInterceptor), list)

        list = AspectList().a(ELDRITCH, 4).a(VOID, 2).a(SENSES)
        ThaumcraftApi.registerObjectTag(WildStack(ModBlocks.corporeaCrystalCube), list)

        list = AspectList().a(TREE, 4).a(FIRE, 2)
        ThaumcraftApi.registerObjectTag(WildStack(ModBlocks.incensePlate), list)

        list = AspectList(ItemStack(Items.clock)).a(CRYSTAL).a(MECHANISM)
        ThaumcraftApi.registerObjectTag(WildStack(ModBlocks.hourglass), list)

        list = AspectList(ItemStack(Blocks.rail)).a(SOUL)
        ThaumcraftApi.registerObjectTag(WildStack(ModBlocks.ghostRail), list)

        list = AspectList().a(MECHANISM, 3).a(MAGIC)
        ThaumcraftApi.registerObjectTag(WildStack(ModBlocks.sparkChanger), list)

        list = AspectList().a(TREE, 4).a(LIFE)
        ThaumcraftApi.registerObjectTag(WildStack(ModBlocks.root), list)

        list = AspectList(ItemStack(Blocks.pumpkin)).a(DARKNESS, 2)
        ThaumcraftApi.registerObjectTag(WildStack(ModBlocks.felPumpkin), list)

        list = AspectList().a(LIFE, 4).a(BEAST, 8)
        ThaumcraftApi.registerObjectTag(WildStack(ModBlocks.cocoon), list)

        list = AspectList().a(TRAVEL, 3).a(MOTION, 2).a(LIGHT, 2)
        ThaumcraftApi.registerObjectTag(WildStack(ModBlocks.lightRelay), list)

        list = AspectList().a(TRAVEL, 3).a(MECHANISM, 2)
        ThaumcraftApi.registerObjectTag(WildStack(ModBlocks.lightLauncher), list)

        list = AspectList().a(ENTROPY, 16).a(WRATH, 8).a(MAGIC, 4)
        ThaumcraftApi.registerObjectTag(WildStack(ModBlocks.manaBomb), list)

        list = AspectList(ItemStack(Blocks.noteblock)).a(GREED, 2).a(AIR, 3)
        ThaumcraftApi.registerObjectTag(WildStack(ModBlocks.cacophonium), list)

        list = AspectList().a(MOTION, 2).a(AIR, 2)
        ThaumcraftApi.registerObjectTag(WildStack(ModBlocks.bellows), list)

        list = AspectList().a(COLOR, 8)
        ThaumcraftApi.registerObjectTag(WildStack(ModBlocks.bifrostPerm), list)

        list = AspectList().a(PLANT, 4).a(LIFE, 4)
        ThaumcraftApi.registerObjectTag(WildStack(ModBlocks.cellBlock), list)

        list = AspectList().a(MECHANISM, 4).a(ELDRITCH, 2)
        ThaumcraftApi.registerObjectTag(WildStack(ModBlocks.redStringInterceptor), list)

        list = AspectList(ItemStack(Items.skull, 1, 3)).a(ELDRITCH, 4).a(EARTH, 4)
        ThaumcraftApi.registerObjectTag(WildStack(ModBlocks.gaiaHead), list)

        list = AspectList().a(ELDRITCH, 4).a(VOID, 2).a(MIND, 2)
        ThaumcraftApi.registerObjectTag(WildStack(ModBlocks.corporeaRetainer), list)

        list = AspectList().a(WEATHER, 2)
        ThaumcraftApi.registerObjectTag(WildStack(ModBlocks.teruTeruBozu), list)

        list = AspectList().a(EARTH, 2).a(COLOR)
        ThaumcraftApi.registerObjectTag(WildStack(ModBlocks.shimmerrock), list)

        list = AspectList().a(TREE).a(COLOR)
        ThaumcraftApi.registerObjectTag(WildStack(ModBlocks.shimmerwoodPlanks), list)

        list = AspectList().a(MAN, 4).a(MAGIC, 2)
        ThaumcraftApi.registerObjectTag(WildStack(ModBlocks.avatar), list)

        list = AspectList().a(EARTH).a(PLANT).a(COLOR)
        ThaumcraftApi.registerObjectTag(WildStack(ModBlocks.altGrass), list)

        list = AspectList(ItemStack(Blocks.quartz_block))
        ThaumcraftApi.registerObjectTag(WildStack(ModFluffBlocks.darkQuartz), list)
        ThaumcraftApi.registerObjectTag(WildStack(ModFluffBlocks.manaQuartz), list)
        ThaumcraftApi.registerObjectTag(WildStack(ModFluffBlocks.blazeQuartz), list)
        ThaumcraftApi.registerObjectTag(WildStack(ModFluffBlocks.lavenderQuartz), list)
        ThaumcraftApi.registerObjectTag(WildStack(ModFluffBlocks.redQuartz), list)
        ThaumcraftApi.registerObjectTag(WildStack(ModFluffBlocks.elfQuartz), list)
        ThaumcraftApi.registerObjectTag(WildStack(ModFluffBlocks.sunnyQuartz), list)

        list = AspectList().a(EARTH, 2)
        ThaumcraftApi.registerObjectTag(WildStack(ModFluffBlocks.biomeStoneA), list)
        ThaumcraftApi.registerObjectTag(WildStack(ModFluffBlocks.biomeStoneB), list)
        ThaumcraftApi.registerObjectTag(WildStack(ModFluffBlocks.stone), list)

        list = AspectList().a(EARTH, 2).a(COLOR)
        ThaumcraftApi.registerObjectTag(WildStack(ModFluffBlocks.pavement), list)
    }
}
