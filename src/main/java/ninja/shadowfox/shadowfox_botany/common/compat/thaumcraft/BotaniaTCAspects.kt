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
import vazkii.botania.common.item.ModItems
import vazkii.botania.common.lib.LibEntityNames

/**
 * @author WireSegal
 * Created at 4:07 PM on 1/19/16.
 */
object BotaniaTCAspects {

    fun WildStack(i: Block): ItemStack = ItemStack(i, 1, OreDictionary.WILDCARD_VALUE)
    fun WildStack(i: Item): ItemStack = ItemStack(i, 1, OreDictionary.WILDCARD_VALUE)


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
        val forbidden = Loader.isModLoaded("ForbiddenMagic")

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
        ThaumcraftApi.registerObjectTag(ItemStack(ModBlocks.spreader, 1, 0), list) // Spreader

        list = AspectList().a(MAGIC, 2).a(MOTION, 2).a(MECHANISM)
        ThaumcraftApi.registerObjectTag(ItemStack(ModBlocks.spreader, 1, 1), list) // Pulse

        list = AspectList().a(MAGIC, 2).a(MOTION, 2).a(ELDRITCH)
        ThaumcraftApi.registerObjectTag(ItemStack(ModBlocks.spreader, 1, 2), list) // Dreamwood

        list = AspectList().a(MAGIC, 2).a(MOTION, 2).a(ELDRITCH).a(COLOR).a(PRIDE)
        ThaumcraftApi.registerObjectTag(ItemStack(ModBlocks.spreader, 1, 3), list) // Gaia

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

        list = AspectList().a(EARTH, 2).a(LIFE, 16).a(MAGIC, 8)
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
        ThaumcraftApi.registerObjectTag(ItemStack(ModBlocks.bifrostPerm), list)

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
        if (ModFluffBlocks.darkQuartz != null)
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






        /////// ITEMS!

        list = AspectList(ItemStack(Items.book)).a(PLANT)
        ThaumcraftApi.registerObjectTag(WildStack(ModItems.lexicon), list)

        list = AspectList().a(PLANT).a(COLOR)
        ThaumcraftApi.registerObjectTag(WildStack(ModItems.petal), list)
        ThaumcraftApi.registerObjectTag(WildStack(ModItems.dye), list)

        list = AspectList(ItemStack(ModItems.twigWand)).a(TOOL, 3)
        ThaumcraftApi.registerObjectTag(WildStack(ModItems.twigWand), list)

        list = AspectList().a(METAL, 4).a(MAGIC)
        ThaumcraftApi.registerObjectTag(ItemStack(ModItems.manaResource, 1, 0), list) // Manasteel

        list = AspectList().a(ELDRITCH, 4).a(MAGIC, 6).a(TRAVEL, 2)
        ThaumcraftApi.registerObjectTag(ItemStack(ModItems.manaResource, 1, 1), list) // Manapearl

        list = AspectList().a(CRYSTAL, 4).a(GREED, 4).a(MAGIC, 4)
        ThaumcraftApi.registerObjectTag(ItemStack(ModItems.manaResource, 1, 2), list) // Manadiamond

        list = AspectList().a(METAL, 4).a(MAGIC, 2).a(EARTH, 2).a(ELDRITCH, 2).a(GREED, 2)
        ThaumcraftApi.registerObjectTag(ItemStack(ModItems.manaResource, 1, 4), list) // Terrasteel

        list = AspectList().a(ELDRITCH, 16).a(PRIDE, 8).a(LIFE, 4).a(COLOR, 4)
        ThaumcraftApi.registerObjectTag(ItemStack(ModItems.manaResource, 1, 5), list) // Gaia Spirit

        list = AspectList().a(METAL, 4).a(ELDRITCH, 1).a(MAGIC, 1)
        ThaumcraftApi.registerObjectTag(ItemStack(ModItems.manaResource, 1, 7), list) // Elementium

        list = AspectList().a(ELDRITCH, 6).a(MAGIC, 6)
        ThaumcraftApi.registerObjectTag(ItemStack(ModItems.manaResource, 1, 8), list) // Pixie Dust

        list = AspectList().a(CRYSTAL, 4).a(GREED, 4).a(MAGIC, 4).a(ELDRITCH, 2)
        ThaumcraftApi.registerObjectTag(ItemStack(ModItems.manaResource, 1, 9), list) // Dragonstone

        list = AspectList().a(CRYSTAL).a(WATER)
        ThaumcraftApi.registerObjectTag(ItemStack(ModItems.manaResource, 1, 10), list) // Prismarine

        list = AspectList().a(CRAFT)
        ThaumcraftApi.registerObjectTag(ItemStack(ModItems.manaResource, 1, 11), list) // Crafting Placeholder

        list = AspectList().a(ELDRITCH, 2).a(CLOTH, 2)
        ThaumcraftApi.registerObjectTag(ItemStack(ModItems.manaResource, 1, 12), list) // Red String

        list = AspectList().a(CRYSTAL).a(ELDRITCH)
        ThaumcraftApi.registerObjectTag(ItemStack(ModItems.manaResource, 1, 15), list) // Ender Air

        list = AspectList().a(CLOTH).a(MAGIC)
        ThaumcraftApi.registerObjectTag(ItemStack(ModItems.manaResource, 1, 16), list) // Mana String

        list = AspectList().a(METAL)
        ThaumcraftApi.registerObjectTag(ItemStack(ModItems.manaResource, 1, 17), list) // Manasteel Nugget
        ThaumcraftApi.registerObjectTag(ItemStack(ModItems.manaResource, 1, 18), list) // Terrasteel Nugget
        ThaumcraftApi.registerObjectTag(ItemStack(ModItems.manaResource, 1, 19), list) // Elementium Nugget

        list = AspectList().a(TREE)
        ThaumcraftApi.registerObjectTag(ItemStack(ModItems.manaResource, 1, 20), list) // Livingroot

        list = AspectList().a(EARTH)
        ThaumcraftApi.registerObjectTag(ItemStack(ModItems.manaResource, 1, 21), list) // Pebble

        list = AspectList().a(MAGIC, 2)
        ThaumcraftApi.registerObjectTag(ItemStack(ModItems.manaResource, 1, 23), list) // Mana Powder

        list = AspectList().a(WATER, 16)
        ThaumcraftApi.registerObjectTag(ItemStack(ModItems.rune, 1, 0), list) // Rune of Water

        list = AspectList().a(FIRE, 16)
        ThaumcraftApi.registerObjectTag(ItemStack(ModItems.rune, 1, 1), list) // Rune of Fire

        list = AspectList().a(EARTH, 16)
        ThaumcraftApi.registerObjectTag(ItemStack(ModItems.rune, 1, 2), list) // Rune of Earth

        list = AspectList().a(AIR, 16)
        ThaumcraftApi.registerObjectTag(ItemStack(ModItems.rune, 1, 3), list) // Rune of Air

        list = AspectList().a(LIFE, 16)
        ThaumcraftApi.registerObjectTag(ItemStack(ModItems.rune, 1, 4), list) // Rune of Spring

        list = AspectList().a(CROP, 16)
        ThaumcraftApi.registerObjectTag(ItemStack(ModItems.rune, 1, 5), list) // Rune of Summer

        list = AspectList().a(HARVEST, 16)
        ThaumcraftApi.registerObjectTag(ItemStack(ModItems.rune, 1, 6), list) // Rune of Autumn

        list = AspectList().a(COLD, 16)
        ThaumcraftApi.registerObjectTag(ItemStack(ModItems.rune, 1, 7), list) // Rune of Winter

        list = AspectList().a(AURA, 16)
        ThaumcraftApi.registerObjectTag(ItemStack(ModItems.rune, 1, 8), list) // Rune of Mana

        if (forbidden) list = AspectList().a(LUST, 16)
        else list = AspectList().a(FLESH, 16)
        ThaumcraftApi.registerObjectTag(ItemStack(ModItems.rune, 1, 9), list) // Rune of Lust

        if (forbidden) list = AspectList().a(GLUTTONY, 16)
        else list = AspectList().a(HUNGER, 16)
        ThaumcraftApi.registerObjectTag(ItemStack(ModItems.rune, 1, 10), list) // Rune of Gluttony

        list = AspectList().a(GREED, 16)
        ThaumcraftApi.registerObjectTag(ItemStack(ModItems.rune, 1, 11), list) // Rune of Greed

        if (forbidden) list = AspectList().a(SLOTH, 16)
        else list = AspectList().a(CLOTH, 16)
        ThaumcraftApi.registerObjectTag(ItemStack(ModItems.rune, 1, 12), list) // Rune of Sloth

        if (forbidden) list = AspectList().a(WRATH, 16)
        else list = AspectList().a(WEAPON, 16)
        ThaumcraftApi.registerObjectTag(ItemStack(ModItems.rune, 1, 13), list) // Rune of Wrath

        if (forbidden) list = AspectList().a(ENVY, 16)
        else list = AspectList().a(TRAP, 16)
        ThaumcraftApi.registerObjectTag(ItemStack(ModItems.rune, 1, 14), list) // Rune of Envy

        if (forbidden) list = AspectList().a(PRIDE, 16)
        else list = AspectList().a(COLOR, 16)
        ThaumcraftApi.registerObjectTag(ItemStack(ModItems.rune, 1, 15), list) // Rune of Pride

        list = AspectList().a(VOID, 2).a(MAGIC, 8)
        ThaumcraftApi.registerObjectTag(WildStack(ModItems.manaTablet), list)

        list = AspectList().a(HUNGER, 64).a(CROP, 64) // why did i do this
        ThaumcraftApi.registerObjectTag(WildStack(ModItems.manaCookie), list)

        list = AspectList().a(PLANT).a(EXCHANGE)
        ThaumcraftApi.registerObjectTag(ItemStack(ModItems.grassSeeds, 1, 0), list) // grass

        list = AspectList().a(EARTH).a(EXCHANGE)
        ThaumcraftApi.registerObjectTag(ItemStack(ModItems.grassSeeds, 1, 1), list) // podzol

        list = AspectList().a(DARKNESS).a(EXCHANGE)
        ThaumcraftApi.registerObjectTag(ItemStack(ModItems.grassSeeds, 1, 2), list) // mycelium

        list = AspectList().a(PLANT).a(EXCHANGE).a(COLOR)
        ThaumcraftApi.registerObjectTag(ItemStack(ModItems.grassSeeds, 1, 3), list) // dry
        ThaumcraftApi.registerObjectTag(ItemStack(ModItems.grassSeeds, 1, 4), list) // golden
        ThaumcraftApi.registerObjectTag(ItemStack(ModItems.grassSeeds, 1, 5), list) // vivid
        ThaumcraftApi.registerObjectTag(ItemStack(ModItems.grassSeeds, 1, 6), list) // scorched
        ThaumcraftApi.registerObjectTag(ItemStack(ModItems.grassSeeds, 1, 7), list) // infused
        ThaumcraftApi.registerObjectTag(ItemStack(ModItems.grassSeeds, 1, 8), list) // mutated

        list = AspectList().a(VOID, 2).a(MAGIC, 8).a(ELDRITCH, 8)
        ThaumcraftApi.registerObjectTag(WildStack(ModItems.manaMirror), list)

        list = AspectList(ItemStack(ModItems.manasteelHelm)).a(SENSES, 4)
        ThaumcraftApi.registerObjectTag(WildStack(ModItems.manasteelHelmRevealing), list)

        list = AspectList(ItemStack(ModItems.terrasteelHelm)).a(SENSES, 4)
        ThaumcraftApi.registerObjectTag(WildStack(ModItems.terrasteelHelmRevealing), list)

        list = AspectList().a(EARTH, 12).a(HUNGER, 12).a(MAGIC, 2)
        ThaumcraftApi.registerObjectTag(WildStack(ModItems.tinyPlanet), list)

        list = AspectList().a(VOID, 2).a(MAGIC, 8).a(METAL, 8)
        ThaumcraftApi.registerObjectTag(WildStack(ModItems.manaRing), list)

        list = AspectList().a(VOID, 2).a(MAGIC, 8).a(METAL, 8)
        ThaumcraftApi.registerObjectTag(WildStack(ModItems.manaRingGreater), list)

        list = AspectList().a(CLOTH, 4).a(TRAVEL, 8)
        ThaumcraftApi.registerObjectTag(WildStack(ModItems.travelBelt), list)

        list = AspectList(ItemStack(Items.quartz))
        ThaumcraftApi.registerObjectTag(WildStack(ModItems.quartz), list)

        list = AspectList(ItemStack(ModItems.elementiumHelm)).a(SENSES, 4)
        ThaumcraftApi.registerObjectTag(WildStack(ModItems.elementiumHelmRevealing), list)

        list = AspectList().a(METAL, 4).a(VOID, 8)
        ThaumcraftApi.registerObjectTag(WildStack(ModItems.openBucket), list)

        list = AspectList().a(LIFE, 16).a(MAGIC, 8).a(TRAVEL, 4)
        ThaumcraftApi.registerObjectTag(WildStack(ModItems.spawnerMover), list)

        list = AspectList().a(CRYSTAL).a(ENTROPY, 8).a(MAGIC, 4)
        ThaumcraftApi.registerObjectTag(WildStack(ModItems.manaBottle), list)

        list = AspectList().a(METAL, 15).a(GREED, 3).a(SENSES, 3)
        ThaumcraftApi.registerObjectTag(WildStack(ModItems.itemFinder), list)

        list = AspectList().a(FLIGHT).a(MAGIC)
        ThaumcraftApi.registerObjectTag(WildStack(ModItems.manaInkwell), list)

        list = AspectList().a(CRYSTAL)
        ThaumcraftApi.registerObjectTag(WildStack(ModItems.vial), list)
        ThaumcraftApi.registerObjectTag(WildStack(ModItems.brewVial), list)

        list = AspectList().a(CRYSTAL).a(MAGIC, 8)
        ThaumcraftApi.registerObjectTag(WildStack(ModItems.brewFlask), list)

        list = AspectList().a(CRAFT, 4).a(ELDRITCH, 4)
        ThaumcraftApi.registerObjectTag(WildStack(ModItems.craftingHalo), list)

        list = AspectList().a(MAGIC, 16).a(ELDRITCH, 4).a(ORDER, 2)
        ThaumcraftApi.registerObjectTag(ItemStack(ModItems.blackLotus, 1, 0), list)

        list = AspectList().a(MAGIC, 20).a(ELDRITCH, 8).a(ORDER, 6)
        ThaumcraftApi.registerObjectTag(ItemStack(ModItems.blackLotus, 1, 1), list)

        list = AspectList().a(SENSES, 8).a(CRYSTAL, 4)
        ThaumcraftApi.registerObjectTag(WildStack(ModItems.monocle), list)

        list = AspectList().a(TRAVEL, 16)
        ThaumcraftApi.registerObjectTag(WildStack(ModItems.worldSeed), list)

        list = AspectList().a(PLANT, 8).a(METAL, 4).a(WEAPON, 4).a(WRATH, 4)
        ThaumcraftApi.registerObjectTag(ItemStack(ModItems.thornChakram, 1, 0), list)

        list = AspectList().a(PLANT, 8).a(METAL, 4).a(WEAPON, 4).a(WRATH, 4).a(FIRE, 2)
        ThaumcraftApi.registerObjectTag(ItemStack(ModItems.thornChakram, 1, 1), list)

        list = AspectList().a(LIFE, 32).a(MAGIC, 16)
        ThaumcraftApi.registerObjectTag(WildStack(ModItems.overgrowthSeed), list)

        list = AspectList().a(ELDRITCH, 64).a(MIND, 32).a(SOUL, 32)
        ThaumcraftApi.registerObjectTag(WildStack(ModItems.ancientWill), list)

        list = AspectList().a(VOID)
        ThaumcraftApi.registerObjectTag(WildStack(ModItems.phantomInk), list)

        list = AspectList().a(COLOR, 32).a(LIFE, 16).a(HEAL, 16)
        ThaumcraftApi.registerObjectTag(WildStack(ModItems.pinkinator), list)

        list = AspectList().a(VOID, 64)
        ThaumcraftApi.registerObjectTag(WildStack(ModItems.blackHoleTalisman), list)

        list = AspectList().a(SENSES, 4).a(AIR, 4).a(GREED, 4).a(COLOR, 2).a(PRIDE, 4)
        ThaumcraftApi.registerObjectTag(WildStack(ModItems.recordGaia1), list)
        ThaumcraftApi.registerObjectTag(WildStack(ModItems.recordGaia2), list)

        list = AspectList(ItemStack(ModItems.terraAxe)).a(TOOL, 3)
        ThaumcraftApi.registerObjectTag(WildStack(ModItems.terraAxe), list)

        list = AspectList().a(TREE).a(WATER)
        ThaumcraftApi.registerObjectTag(WildStack(ModItems.waterBowl), list)

        list = AspectList().a(ELDRITCH, 16).a(WEAPON, 4)
        ThaumcraftApi.registerObjectTag(WildStack(ModItems.starSword), list)

        ThaumcraftApi.registerObjectTag(WildStack(ModItems.exchangeRod), list)

        list = AspectList().a(WEATHER, 16).a(WEAPON, 4)
        ThaumcraftApi.registerObjectTag(WildStack(ModItems.thunderSword), list)

        list = AspectList(ItemStack(ModBlocks.gaiaHead))
        ThaumcraftApi.registerObjectTag(WildStack(ModItems.gaiaHead), list)




        /// ENTITIES!

        list = AspectList().a(MAGIC, 3).a(AURA, 3)
        ThaumcraftApi.registerEntityTag(LibEntityNames.MANA_BURST, list)

        list = AspectList().a(MAGIC, 3).a(LIGHT, 3).a(COLOR, 3)
        ThaumcraftApi.registerEntityTag(LibEntityNames.SIGNAL_FLARE, list)

        list = AspectList().a(LIGHT, 2).a(FLIGHT, 2).a(ELDRITCH)
        ThaumcraftApi.registerEntityTag(LibEntityNames.PIXIE, list)

        list = AspectList().a(FIRE, 8)
        ThaumcraftApi.registerEntityTag(LibEntityNames.FLAME_RING, list)

        list = AspectList().a(PLANT, 7)
        ThaumcraftApi.registerEntityTag(LibEntityNames.VINE_BALL, list)

        list = AspectList().a(MAN, 16).a(EARTH, 8).a(ELDRITCH, 8).a(DARKNESS, 4)
        ThaumcraftApi.registerEntityTag(LibEntityNames.DOPPLEGANGER, list)

        list = AspectList().a(MAGIC, 2).a(TRAP, 2).a(POISON, 2)
        ThaumcraftApi.registerEntityTag(LibEntityNames.MAGIC_LANDMINE, list)

        list = AspectList(ItemStack(ModItems.spark))
        ThaumcraftApi.registerEntityTag(LibEntityNames.SPARK, list)

        list = AspectList().a(MOTION, 4)
        ThaumcraftApi.registerEntityTag(LibEntityNames.THROWN_ITEM, list)

        list = AspectList().a(WEAPON, 4).a(ELDRITCH, 2)
        ThaumcraftApi.registerEntityTag(LibEntityNames.MAGIC_MISSILE, list)

        list = AspectList(ItemStack(ModItems.thornChakram))
        ThaumcraftApi.registerEntityTag(LibEntityNames.THORN_CHAKRAM, list)

        list = AspectList(ItemStack(ModItems.corporeaSpark))
        ThaumcraftApi.registerEntityTag(LibEntityNames.CORPOREA_SPARK, list)

        list = AspectList(ItemStack(ModItems.manaResource, 1, 15))
        ThaumcraftApi.registerEntityTag(LibEntityNames.ENDER_AIR_BOTTLE, list)

        list = AspectList(ItemStack(ModItems.poolMinecart))
        ThaumcraftApi.registerEntityTag(LibEntityNames.POOL_MINECART, list)

        list = AspectList().a(UNDEAD, 20).a(ORDER, 20).a(FIRE, 15).a(HEAL, 15)
        ThaumcraftApi.registerEntityTag(LibEntityNames.PINK_WITHER, list)

        list = AspectList().a(MOTION, 4).a(TRAVEL, 4)
        ThaumcraftApi.registerEntityTag(LibEntityNames.PLAYER_MOVER, list)

        list = AspectList().a(ENTROPY, 32).a(WRATH, 16)
        ThaumcraftApi.registerEntityTag(LibEntityNames.MANA_STORM, list)

        list = AspectList().a(ELDRITCH, 16).a(WEAPON, 16)
        ThaumcraftApi.registerEntityTag(LibEntityNames.BABYLON_WEAPON, list)

        list = AspectList().a(ELDRITCH, 8)
        ThaumcraftApi.registerEntityTag(LibEntityNames.FALLING_STAR, list)
    }
}
