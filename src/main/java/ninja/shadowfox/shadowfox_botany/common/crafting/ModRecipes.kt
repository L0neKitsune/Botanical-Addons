package ninja.shadowfox.shadowfox_botany.common.crafting

import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.block.Block
import net.minecraft.init.Blocks
import net.minecraft.init.Items
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.CraftingManager
import net.minecraft.item.crafting.IRecipe
import net.minecraftforge.oredict.OreDictionary
import net.minecraftforge.oredict.RecipeSorter
import net.minecraftforge.oredict.RecipeSorter.Category
import net.minecraftforge.oredict.ShapedOreRecipe
import net.minecraftforge.oredict.ShapelessOreRecipe
import ninja.shadowfox.shadowfox_botany.ShadowfoxBotany
import ninja.shadowfox.shadowfox_botany.api.ShadowFoxAPI
import ninja.shadowfox.shadowfox_botany.api.recipe.RecipeTreeCrafting
import ninja.shadowfox.shadowfox_botany.common.blocks.ShadowFoxBlocks
import ninja.shadowfox.shadowfox_botany.common.compat.thaumcraft.ThaumcraftSuffusionRecipes
import ninja.shadowfox.shadowfox_botany.common.core.handler.ConfigHandler
import ninja.shadowfox.shadowfox_botany.common.item.ShadowFoxItems
import ninja.shadowfox.shadowfox_botany.common.item.blocks.ItemStarPlacer
import vazkii.botania.api.BotaniaAPI
import vazkii.botania.api.recipe.RecipeManaInfusion
import vazkii.botania.api.recipe.RecipePetals
import vazkii.botania.api.recipe.RecipePureDaisy
import vazkii.botania.common.Botania
import vazkii.botania.common.core.helper.ItemNBTHelper
import vazkii.botania.common.crafting.ModCraftingRecipes
import vazkii.botania.common.crafting.ModManaInfusionRecipes
import java.util.*
import ninja.shadowfox.shadowfox_botany.lib.LibOreDict as ShadowFoxOreDict
import vazkii.botania.common.block.ModBlocks as BotaniaBlocks
import vazkii.botania.common.block.ModFluffBlocks as BotaniaDecorBlocks
import vazkii.botania.common.item.ModItems as BotaniaItems
import vazkii.botania.common.lib.LibOreDict as BotaniaOreDict

public object ModRecipes {
    val recipesColoredDirt: List<IRecipe>
    val recipesWoodPanel: List<IRecipe>
    val recipesSlabs: List<IRecipe>
    val recipesStairsL: List<IRecipe>
    val recipesStairsR: List<IRecipe>
    val recipesSlabsFull: List<IRecipe>
    val recipesColoredSkyDirtRod: List<IRecipe>
    val recipesPriestOfSif: IRecipe
    val recipesCoatOfArms: List<IRecipe>
    val recipesLightningRod: IRecipe
    val recipesPriestOfThor: IRecipe
    val recipesLeafDyes: List<IRecipe>
    val recipesInterdictionRod: IRecipe
    val recipesPriestOfNjord: IRecipe
    val recipesRedstoneRoot: List<IRecipe>
    val recipesColorOverride: IRecipe
    val recipesinvisibleLens: IRecipe
    val recipesinvisibleLensUndo: IRecipe
    val recipesAttribution: IRecipe
    val recipesKindling: IRecipe
    val recipesRainbowRod: IRecipe
    val recipesItemDisplay: List<IRecipe>
    val recipesItemDisplayElven: IRecipe
    val recipesThunderousPlanks: IRecipe
    val recipesThunderousStairsL: IRecipe
    val recipesThunderousStairsR: IRecipe
    val recipesThunderousSlabs: IRecipe
    val recipesThunderousTwig: IRecipe
    val recipesLivingwoodFunnel: IRecipe
    val recipesInfernalPlanks: IRecipe
    val recipesInfernalStairsL: IRecipe
    val recipesInfernalStairsR: IRecipe
    val recipesInfernalSlabs: IRecipe
    val recipesInfernalTwig: IRecipe
    val recipesSixTorches: IRecipe
    val recipesAltPlanks: List<IRecipe>
    val recipesAltSlabs: List<IRecipe>
    val recipesAltStairsR: List<IRecipe>
    val recipesAltStairsL: List<IRecipe>
    val recipesToolbelt: IRecipe
    val recipesLamp: IRecipe
    val recipesSealingPlanks: IRecipe
    val recipesSealingStairsL: IRecipe
    val recipesSealingStairsR: IRecipe
    val recipesSealingSlabs: IRecipe
    val recipesAmplifier: IRecipe
    val recipesStar: List<IRecipe>

    val recipeShimmerQuartz: IRecipe

    val recipeRainbowPetalGrinding: IRecipe
    val recipeRainbowPetal: IRecipe
    val recipeRainbowDoublePetal: IRecipe

    val recipesPastoralSeeds: List<RecipeManaInfusion>

    val recipesAttributionHeads: List<RecipePetals>

    val recipesPlainDirt: RecipePureDaisy
    val recipesIrisSapling: RecipePureDaisyExclusion

    val recipesLightningTree: RecipeTreeCrafting
    val recipesInfernalTree: RecipeTreeCrafting
    val recipesSealingTree: RecipeTreeCrafting

    val recipeCrysanthermum: RecipePetals


    init {

        GameRegistry.addRecipe(RecipeRingDyes())
        RecipeSorter.register("shadowfox_botany:ringdye", RecipeRingDyes::class.java, Category.SHAPELESS, "")
        GameRegistry.addRecipe(RecipeRainbowLensDye())
        RecipeSorter.register("shadowfox_botany:lensdye", RecipeRainbowLensDye::class.java, Category.SHAPELESS, "")

        for (i in 0..15)
            addOreDictRecipe(ItemStack(ShadowFoxBlocks.coloredDirtBlock, 8, i),
                    "DDD",
                    "DPD",
                    "DDD", 'P', ShadowFoxOreDict.DYES[i], 'D', ItemStack(Blocks.dirt, 1))
        addOreDictRecipe(ItemStack(ShadowFoxBlocks.rainbowDirtBlock, 8),
                "DDD",
                "DPD",
                "DDD", 'P', ShadowFoxOreDict.DYES[16], 'D', ItemStack(Blocks.dirt, 1))

        recipesColoredDirt = BotaniaAPI.getLatestAddedRecipes(17)

        for (i in 0..15)
            addShapelessOreDictRecipe(ItemStack(BotaniaItems.dye, 1, i), ShadowFoxOreDict.LEAVES[i], BotaniaOreDict.PESTLE_AND_MORTAR)
        addShapelessOreDictRecipe(ItemStack(ShadowFoxItems.resource, 1, 6), ShadowFoxOreDict.LEAVES[16], BotaniaOreDict.PESTLE_AND_MORTAR)

        recipesLeafDyes = BotaniaAPI.getLatestAddedRecipes(17)
        for (i in 0..15)
            addShapelessOreDictRecipe(ItemStack(ShadowFoxBlocks.coloredPlanks, 4, i), ShadowFoxOreDict.WOOD[i])
        addShapelessOreDictRecipe(ItemStack(ShadowFoxBlocks.rainbowPlanks, 4), ShadowFoxBlocks.rainbowWood)

        recipesWoodPanel = BotaniaAPI.getLatestAddedRecipes(17)

        for (i in 0..15)
            GameRegistry.addRecipe(ItemStack(ShadowFoxBlocks.coloredSlabs[i], 6),
                    "QQQ",
                    'Q', ItemStack(ShadowFoxBlocks.coloredPlanks, 1, i))
        GameRegistry.addRecipe(ItemStack(ShadowFoxBlocks.rainbowSlabs, 6),
                "QQQ",
                'Q', ItemStack(ShadowFoxBlocks.rainbowPlanks))

        recipesSlabs = BotaniaAPI.getLatestAddedRecipes(17)

        for (i in 0..15)
            GameRegistry.addRecipe(ItemStack(ShadowFoxBlocks.coloredStairs[i], 4),
                    "  Q", " QQ", "QQQ",
                    'Q', ItemStack(ShadowFoxBlocks.coloredPlanks, 1, i))
        GameRegistry.addRecipe(ItemStack(ShadowFoxBlocks.rainbowStairs, 4),
                "  Q", " QQ", "QQQ",
                'Q', ItemStack(ShadowFoxBlocks.rainbowPlanks))

        recipesStairsR = BotaniaAPI.getLatestAddedRecipes(17)

        for (i in 0..15)
            GameRegistry.addRecipe(ItemStack(ShadowFoxBlocks.coloredStairs[i], 4),
                    "Q  ", "QQ ", "QQQ",
                    'Q', ItemStack(ShadowFoxBlocks.coloredPlanks, 1, i))
        GameRegistry.addRecipe(ItemStack(ShadowFoxBlocks.rainbowStairs, 4),
                "Q  ", "QQ ", "QQQ",
                'Q', ItemStack(ShadowFoxBlocks.rainbowPlanks))

        recipesStairsL = BotaniaAPI.getLatestAddedRecipes(17)

        for (i in 0..15)
            GameRegistry.addShapelessRecipe(ItemStack(ShadowFoxBlocks.coloredPlanks, 1, i),
                    ItemStack(ShadowFoxBlocks.coloredSlabs[i], 1), ItemStack(ShadowFoxBlocks.coloredSlabs[i], 1))
        GameRegistry.addShapelessRecipe(ItemStack(ShadowFoxBlocks.rainbowPlanks),
                ItemStack(ShadowFoxBlocks.rainbowSlabs), ItemStack(ShadowFoxBlocks.rainbowSlabs))

        recipesSlabsFull = BotaniaAPI.getLatestAddedRecipes(17)

        for (i in 0..15)
            addOreDictRecipe(ItemStack(ShadowFoxItems.colorfulSkyDirtRod, 1, i),
                    " PD",
                    " RP",
                    "S  ",
                    'D', ItemStack(ShadowFoxBlocks.coloredDirtBlock, 1, i),
                    'R', ItemStack(BotaniaItems.skyDirtRod, 1),
                    'P', BotaniaOreDict.PIXIE_DUST,
                    'S', BotaniaOreDict.DRAGONSTONE)
        addOreDictRecipe(ItemStack(ShadowFoxItems.colorfulSkyDirtRod, 1, 16),
                " PD",
                " RP",
                "S  ",
                'D', ItemStack(ShadowFoxBlocks.rainbowDirtBlock),
                'R', ItemStack(BotaniaItems.skyDirtRod, 1),
                'P', BotaniaOreDict.PIXIE_DUST,
                'S', BotaniaOreDict.DRAGONSTONE)

        recipesColoredSkyDirtRod = BotaniaAPI.getLatestAddedRecipes(16)

        addOreDictRecipe(ItemStack(ShadowFoxItems.lightningRod, 1),
                " EW",
                " SD",
                "S  ",
                'E', ShadowFoxOreDict.SPLINTERS_THUNDERWOOD,
                'D', BotaniaOreDict.DRAGONSTONE,
                'S', ShadowFoxOreDict.TWIG_THUNDERWOOD,
                'W', BotaniaOreDict.RUNE[13]) // Wrath

        recipesLightningRod = BotaniaAPI.getLatestAddedRecipe()

        addOreDictRecipe(ItemStack(ShadowFoxItems.emblem, 1, 0),
                "EGE",
                "TAT",
                " W ",
                'E', BotaniaOreDict.ENDER_AIR_BOTTLE,
                'T', BotaniaOreDict.TERRASTEEL_NUGGET,
                'G', BotaniaOreDict.LIFE_ESSENCE,
                'W', BotaniaOreDict.RUNE[13], // Wrath
                'A', ShadowFoxOreDict.HOLY_PENDANT)

        recipesPriestOfThor = BotaniaAPI.getLatestAddedRecipe()

        addOreDictRecipe(ItemStack(ShadowFoxItems.emblem, 1, 1),
                "DGD",
                "NAN",
                " P ",
                'D', BotaniaOreDict.DRAGONSTONE,
                'N', "nuggetGold",
                'G', BotaniaOreDict.LIFE_ESSENCE,
                'P', BotaniaOreDict.RUNE[2], // Earth
                'A', ShadowFoxOreDict.HOLY_PENDANT)

        recipesPriestOfSif = BotaniaAPI.getLatestAddedRecipe()

        for (i in 0..15)
            addOreDictRecipe(ItemStack(ShadowFoxItems.coatOfArms, 1, i),
                    "LLL",
                    "LSL",
                    "LLL",
                    'L', ShadowFoxOreDict.LEAVES[i],
                    'S', BotaniaOreDict.MANA_STRING)
        addOreDictRecipe(ItemStack(ShadowFoxItems.coatOfArms, 1, 16),
                "LLL",
                "LSL",
                "LLL",
                'L', ItemStack(ShadowFoxBlocks.rainbowLeaves),
                'S', BotaniaOreDict.MANA_STRING)

        recipesCoatOfArms = BotaniaAPI.getLatestAddedRecipes(17)

        addOreDictRecipe(ItemStack(ShadowFoxItems.colorOverride),
                "PE ",
                "E E",
                " E ",
                'P', ItemStack(BotaniaItems.lens, 1, 14), // Paintslinger's Lens
                'E', BotaniaOreDict.ELEMENTIUM)

        recipesColorOverride = BotaniaAPI.getLatestAddedRecipe()

        addOreDictRecipe(ItemStack(ShadowFoxItems.interdictionRod),
                " AS",
                " DA",
                "P  ",
                'P', BotaniaOreDict.RUNE[15], // Pride
                'A', BotaniaOreDict.RUNE[3], // Air
                'S', ItemStack(BotaniaItems.tornadoRod),
                'D', BotaniaOreDict.DREAMWOOD_TWIG)

        recipesInterdictionRod = BotaniaAPI.getLatestAddedRecipe()

        addOreDictRecipe(ItemStack(ShadowFoxItems.emblem, 1, 2),
                "RGR",
                "NAN",
                " P ",
                'P', BotaniaOreDict.RUNE[15], // Pride
                'N', BotaniaOreDict.MANASTEEL_NUGGET,
                'G', BotaniaOreDict.LIFE_ESSENCE,
                'R', BotaniaOreDict.RUNE[3], // Air
                'A', ShadowFoxOreDict.HOLY_PENDANT)

        recipesPriestOfNjord = BotaniaAPI.getLatestAddedRecipe()

        addOreDictRecipe(ItemStack(ShadowFoxItems.attributionBauble),
                "S S",
                "Q Q",
                " G ",
                'G', "ingotGold",
                'Q', "gemQuartz",
                'S', BotaniaOreDict.MANA_STRING)

        recipesAttribution = BotaniaAPI.getLatestAddedRecipe()

        addOreDictRecipe(ItemStack(ShadowFoxBlocks.kindling),
                " S ",
                "SBS",
                " S ",
                'B', "powderBlaze",
                'S', BotaniaOreDict.MANA_STRING)

        recipesKindling = BotaniaAPI.getLatestAddedRecipe()

        addShapelessOreDictRecipe(ItemStack(ShadowFoxItems.invisibleFlameLens),
                ItemStack(BotaniaItems.lens, 1, 17), BotaniaItems.phantomInk)

        recipesinvisibleLens = BotaniaAPI.getLatestAddedRecipe()

        addShapelessOreDictRecipe(ItemStack(BotaniaItems.lens, 1, 17),
                ItemStack(ShadowFoxItems.invisibleFlameLens), BotaniaItems.phantomInk)

        recipesinvisibleLensUndo = BotaniaAPI.getLatestAddedRecipe()

        for (i in 0..15)
            addShapelessOreDictRecipe(ItemStack(BotaniaItems.manaResource, 1, 6), "dustRedstone", ItemStack(ShadowFoxBlocks.irisGrass, 1, i))
        addShapelessOreDictRecipe(ItemStack(BotaniaItems.manaResource, 1, 6), "dustRedstone", ItemStack(ShadowFoxBlocks.rainbowGrass))
        recipesRedstoneRoot = BotaniaAPI.getLatestAddedRecipes(17)

        recipesPastoralSeeds = ArrayList<RecipeManaInfusion>()
        for (i in 0..15)
            recipesPastoralSeeds.add(BotaniaAPI.registerManaInfusionRecipe(ItemStack(ShadowFoxItems.irisSeeds, 1, i), ItemStack(ShadowFoxBlocks.irisGrass, 1, i), 2500))
        recipesPastoralSeeds.add(BotaniaAPI.registerManaInfusionRecipe(ItemStack(ShadowFoxItems.irisSeeds, 1, 16), ItemStack(ShadowFoxBlocks.rainbowGrass), 2500))

        recipesPlainDirt = BotaniaAPI.registerPureDaisyRecipe(ShadowFoxOreDict.IRIS_DIRT, Blocks.dirt, 0)

        recipesIrisSapling = RecipePureDaisyExclusion("treeSapling", ShadowFoxBlocks.irisSapling, 0)
        BotaniaAPI.pureDaisyRecipes.add(recipesIrisSapling as RecipePureDaisy)

        addOreDictRecipe(ItemStack(ShadowFoxItems.rainbowRod),
                " GB",
                " DG",
                "D  ",
                'G', "glowstone",
                'B', ShadowFoxOreDict.DYES[16],
                'D', BotaniaOreDict.DREAMWOOD_TWIG)

        recipesRainbowRod = BotaniaAPI.getLatestAddedRecipe()

        addOreDictRecipe(ItemStack(ShadowFoxBlocks.itemDisplay),
                "N",
                "W",
                'N', BotaniaOreDict.MANASTEEL_NUGGET,
                'W', ItemStack(BotaniaDecorBlocks.livingwoodSlab))
        addOreDictRecipe(ItemStack(ShadowFoxBlocks.itemDisplay, 1, 1),
                "N",
                "W",
                'N', BotaniaOreDict.TERRASTEEL_NUGGET,
                'W', ItemStack(BotaniaDecorBlocks.livingwoodSlab))

        recipesItemDisplay = BotaniaAPI.getLatestAddedRecipes(2)

        addOreDictRecipe(ItemStack(ShadowFoxBlocks.itemDisplay, 1, 2),
                "N",
                "W",
                'N', BotaniaOreDict.ELEMENTIUM_NUGGET,
                'W', ItemStack(BotaniaDecorBlocks.dreamwoodSlab))

        recipesItemDisplayElven = BotaniaAPI.getLatestAddedRecipe()

        recipesLightningTree = ShadowFoxAPI.addTreeRecipe(50000,
                ShadowFoxBlocks.lightningSapling, 0,
                350,
                BotaniaOreDict.MANA_STEEL, BotaniaOreDict.MANA_STEEL, BotaniaOreDict.MANA_STEEL,
                BotaniaOreDict.RUNE[13], // Wrath
                ShadowFoxOreDict.LEAVES[10], ShadowFoxOreDict.LEAVES[10], ShadowFoxOreDict.LEAVES[10], // Purple
                ItemStack(BotaniaBlocks.teruTeruBozu))

        addShapelessOreDictRecipe(ItemStack(ShadowFoxBlocks.lightningPlanks, 4), ShadowFoxBlocks.lightningWood)

        recipesThunderousPlanks = BotaniaAPI.getLatestAddedRecipe()

        GameRegistry.addRecipe(ItemStack(ShadowFoxBlocks.lightningSlabs, 6),
                "QQQ",
                'Q', ItemStack(ShadowFoxBlocks.lightningPlanks))

        recipesThunderousSlabs = BotaniaAPI.getLatestAddedRecipe()

        GameRegistry.addRecipe(ItemStack(ShadowFoxBlocks.lightningStairs, 4),
                "Q  ", "QQ ", "QQQ",
                'Q', ItemStack(ShadowFoxBlocks.lightningPlanks))

        recipesThunderousStairsL = BotaniaAPI.getLatestAddedRecipe()

        GameRegistry.addRecipe(ItemStack(ShadowFoxBlocks.lightningStairs, 4),
                "  Q", " QQ", "QQQ",
                'Q', ItemStack(ShadowFoxBlocks.lightningPlanks))

        recipesThunderousStairsR = BotaniaAPI.getLatestAddedRecipe()

        GameRegistry.addRecipe(ItemStack(ShadowFoxItems.resource, 1, 0), // Thunderous Twig
                "Q",
                "Q",
                'Q', ItemStack(ShadowFoxBlocks.lightningWood))

        recipesThunderousTwig = BotaniaAPI.getLatestAddedRecipe()

        addOreDictRecipe(ItemStack(ShadowFoxBlocks.livingwoodFunnel, 1),
                "L L",
                "LCL",
                " L ",
                'L', BotaniaOreDict.LIVING_WOOD, 'C', ItemStack(Blocks.chest))

        recipesLivingwoodFunnel = BotaniaAPI.getLatestAddedRecipe()

        recipesInfernalTree = ShadowFoxAPI.addTreeRecipe(10000,
                ShadowFoxBlocks.netherSapling, 0,
                70,
                "ingotBrickNether", "ingotBrickNether", "ingotBrickNether",
                BotaniaOreDict.RUNE[1], // Fire
                ShadowFoxOreDict.LEAVES[14], ShadowFoxOreDict.LEAVES[14], ShadowFoxOreDict.LEAVES[14], // Red
                BotaniaOreDict.BLAZE_BLOCK)

        addShapelessOreDictRecipe(ItemStack(ShadowFoxBlocks.netherPlanks, 4), ShadowFoxBlocks.netherWood)

        recipesInfernalPlanks = BotaniaAPI.getLatestAddedRecipe()

        GameRegistry.addRecipe(ItemStack(ShadowFoxBlocks.netherSlabs, 6),
                "QQQ",
                'Q', ItemStack(ShadowFoxBlocks.netherPlanks))

        recipesInfernalSlabs = BotaniaAPI.getLatestAddedRecipe()

        GameRegistry.addRecipe(ItemStack(ShadowFoxBlocks.netherStairs, 4),
                "Q  ", "QQ ", "QQQ",
                'Q', ItemStack(ShadowFoxBlocks.netherPlanks))

        recipesInfernalStairsL = BotaniaAPI.getLatestAddedRecipe()

        GameRegistry.addRecipe(ItemStack(ShadowFoxBlocks.netherStairs, 4),
                "  Q", " QQ", "QQQ",
                'Q', ItemStack(ShadowFoxBlocks.netherPlanks))

        recipesInfernalStairsR = BotaniaAPI.getLatestAddedRecipe()

        GameRegistry.addRecipe(ItemStack(ShadowFoxItems.resource, 1, 2), // Infernal Twig
                "Q",
                "Q",
                'Q', ItemStack(ShadowFoxBlocks.netherWood))

        recipesInfernalTwig = BotaniaAPI.getLatestAddedRecipe()

        GameRegistry.addRecipe(ItemStack(Blocks.torch, 6),
                "C",
                "S",
                'C', ItemStack(ShadowFoxItems.resource, 1, 4), // Flame-Laced Coal
                'S', ItemStack(Items.stick))

        recipesSixTorches = BotaniaAPI.getLatestAddedRecipe()

        for (i in 0..3)
            addShapelessOreDictRecipe(ItemStack(ShadowFoxBlocks.altPlanks, 4, i), ItemStack(ShadowFoxBlocks.altWood0, 1, i))
        addShapelessOreDictRecipe(ItemStack(ShadowFoxBlocks.altPlanks, 4, 4), ItemStack(ShadowFoxBlocks.altWood1, 1, 0))
        addShapelessOreDictRecipe(ItemStack(ShadowFoxBlocks.altPlanks, 4, 5), ItemStack(ShadowFoxBlocks.altWood1, 1, 1))

        recipesAltPlanks = BotaniaAPI.getLatestAddedRecipes(6)

        for (i in 0..5)
            GameRegistry.addRecipe(ItemStack(ShadowFoxBlocks.altSlabs[i], 6),
                    "QQQ",
                    'Q', ItemStack(ShadowFoxBlocks.altPlanks, 1, i))

        recipesAltSlabs = BotaniaAPI.getLatestAddedRecipes(6)

        for (i in 0..5)
            GameRegistry.addRecipe(ItemStack(ShadowFoxBlocks.altStairs[i], 4),
                    "  Q", " QQ", "QQQ",
                    'Q', ItemStack(ShadowFoxBlocks.altPlanks, 1, i))

        recipesAltStairsR = BotaniaAPI.getLatestAddedRecipes(6)

        for (i in 0..5)
            GameRegistry.addRecipe(ItemStack(ShadowFoxBlocks.altStairs[i], 4),
                    "Q  ", "QQ ", "QQQ",
                    'Q', ItemStack(ShadowFoxBlocks.altPlanks, 1, i))

        recipesAltStairsL = BotaniaAPI.getLatestAddedRecipes(6)

        addOreDictRecipe(ItemStack(ShadowFoxItems.toolbelt),
                "CL ",
                "L L",
                "PLS",
                'P', BotaniaOreDict.PIXIE_DUST,
                'L', ItemStack(Items.leather),
                'C', ItemStack(Blocks.chest),
                'S', BotaniaOreDict.RUNE[12]) // Sloth

        recipesToolbelt = BotaniaAPI.getLatestAddedRecipe()

        addOreDictRecipe(ItemStack(ShadowFoxBlocks.irisLamp),
                " B ",
                "BLB",
                " B ",
                'L', ItemStack(Blocks.redstone_lamp),
                'B', ShadowFoxOreDict.DYES[16])

        recipesLamp = BotaniaAPI.getLatestAddedRecipe()

        if (ShadowfoxBotany.thaumcraftLoaded && (Botania.gardenOfGlassLoaded || ShadowfoxBotany.isDevEnv) && ConfigHandler.addThaumcraftTreeSuffusion) {
            ThaumcraftSuffusionRecipes.initRecipes()
        }

        recipesSealingTree = ShadowFoxAPI.addTreeRecipe(50000,
                ShadowFoxBlocks.sealingSapling, 0,
                350,
                BotaniaOreDict.ELEMENTIUM, BotaniaOreDict.ELEMENTIUM, BotaniaOreDict.ELEMENTIUM,
                BotaniaOreDict.RUNE[7], // Winter
                ShadowFoxOreDict.LEAVES[0], ShadowFoxOreDict.LEAVES[0], ShadowFoxOreDict.LEAVES[0], // White
                ItemStack(Blocks.wool, 1, OreDictionary.WILDCARD_VALUE))

        addShapelessOreDictRecipe(ItemStack(ShadowFoxBlocks.sealingPlanks, 4), ShadowFoxBlocks.sealingWood)

        recipesSealingPlanks = BotaniaAPI.getLatestAddedRecipe()

        GameRegistry.addRecipe(ItemStack(ShadowFoxBlocks.sealingSlabs, 6),
                "QQQ",
                'Q', ItemStack(ShadowFoxBlocks.sealingPlanks))

        recipesSealingSlabs = BotaniaAPI.getLatestAddedRecipe()

        GameRegistry.addRecipe(ItemStack(ShadowFoxBlocks.sealingStairs, 4),
                "Q  ", "QQ ", "QQQ",
                'Q', ItemStack(ShadowFoxBlocks.sealingPlanks))

        recipesSealingStairsL = BotaniaAPI.getLatestAddedRecipe()

        GameRegistry.addRecipe(ItemStack(ShadowFoxBlocks.sealingStairs, 4),
                "  Q", " QQ", "QQQ",
                'Q', ItemStack(ShadowFoxBlocks.sealingPlanks))

        recipesSealingStairsR = BotaniaAPI.getLatestAddedRecipe()

        GameRegistry.addRecipe(ItemStack(ShadowFoxBlocks.amp),
                " N ",
                "NRN",
                " N ",
                'N', ItemStack(Blocks.noteblock),
                'R', ItemStack(ShadowFoxBlocks.sealingPlanks))

        recipesAmplifier = BotaniaAPI.getLatestAddedRecipe()

        for (i in 0..16) {
            val stack = ItemStarPlacer.forColor(i)
            stack.stackSize = 3
            addOreDictRecipe(stack,
                    " E ",
                    "GDG",
                    " G ",
                    'E', BotaniaOreDict.ENDER_AIR_BOTTLE,
                    'G', "dustGlowstone",
                    'D', ShadowFoxOreDict.DYES[i])
        }

        recipesStar = BotaniaAPI.getLatestAddedRecipes(17)



        addShapelessOreDictRecipe(ItemStack(BotaniaItems.fertilizer, if (Botania.gardenOfGlassLoaded) 3 else 1), ItemStack(Items.dye, 1, 15), ShadowFoxOreDict.FLORAL_POWDER, ShadowFoxOreDict.FLORAL_POWDER, ShadowFoxOreDict.FLORAL_POWDER, ShadowFoxOreDict.FLORAL_POWDER)
        CraftingManager.getInstance().recipeList.remove(ModCraftingRecipes.recipeFertilizerPowder)
        ModCraftingRecipes.recipeFertilizerPowder = BotaniaAPI.getLatestAddedRecipe()

        ModManaInfusionRecipes.manaPowderRecipes.add(BotaniaAPI.registerManaInfusionRecipe(ItemStack(BotaniaItems.manaResource, 1, 23), ItemStack(ShadowFoxItems.resource, 1, 6), 400))

        addShapelessOreDictRecipe(ItemStack(BotaniaBlocks.shimmerrock), "livingrock", ShadowFoxOreDict.DYES[16])
        CraftingManager.getInstance().recipeList.remove(ModCraftingRecipes.recipeShimmerrock)
        ModCraftingRecipes.recipeShimmerrock = BotaniaAPI.getLatestAddedRecipe()

        addShapelessOreDictRecipe(ItemStack(BotaniaBlocks.shimmerwoodPlanks), ItemStack(BotaniaBlocks.dreamwood, 1, 1), ShadowFoxOreDict.DYES[16])
        CraftingManager.getInstance().recipeList.remove(ModCraftingRecipes.recipeShimmerwoodPlanks)
        ModCraftingRecipes.recipeShimmerwoodPlanks = BotaniaAPI.getLatestAddedRecipe()

        addShapelessOreDictRecipe(ItemStack(Items.mushroom_stew), ShadowFoxOreDict.MUSHROOM, ShadowFoxOreDict.MUSHROOM, ItemStack(Items.bowl))

        addOreDictRecipe(ItemStack(BotaniaBlocks.altar), "SPS", " C ", "CCC", 'S', "slabCobblestone", 'P', ShadowFoxOreDict.RAINBOW_PETAL, 'C', "cobblestone")
        ModCraftingRecipes.recipesApothecary.add(BotaniaAPI.getLatestAddedRecipe())

        addOreDictRecipe(ItemStack(BotaniaBlocks.spreader), "WWW", "GP ", "WWW", 'W', BotaniaOreDict.LIVING_WOOD, 'P', ShadowFoxOreDict.RAINBOW_PETAL, 'G', if (Botania.gardenOfGlassLoaded) BotaniaOreDict.LIVING_WOOD else "ingotGold")
        ModCraftingRecipes.recipesSpreader.add(BotaniaAPI.getLatestAddedRecipe())

        addOreDictRecipe(ItemStack(BotaniaBlocks.spreader, 1, 2), "WWW", "EP ", "WWW", 'W', BotaniaOreDict.DREAM_WOOD, 'P', ShadowFoxOreDict.RAINBOW_PETAL, 'E', BotaniaOreDict.ELEMENTIUM)
        ModCraftingRecipes.recipesDreamwoodSpreader.add(BotaniaAPI.getLatestAddedRecipe())

        addOreDictRecipe(ItemStack(BotaniaItems.flowerBag), "WPW", "W W", " W ", 'P', ShadowFoxOreDict.PETAL, 'W', ItemStack(Blocks.wool, 1, 32767))
        CraftingManager.getInstance().recipeList.remove(ModCraftingRecipes.recipeFlowerBag)
        ModCraftingRecipes.recipeFlowerBag = BotaniaAPI.getLatestAddedRecipe()

        addShapelessOreDictRecipe(ItemStack(ShadowFoxItems.resource, 1, 6), ShadowFoxOreDict.RAINBOW_PETAL, BotaniaOreDict.PESTLE_AND_MORTAR)
        recipeRainbowPetalGrinding = BotaniaAPI.getLatestAddedRecipe()

        addShapelessOreDictRecipe(ItemStack(ShadowFoxItems.resource, 2, 7), ShadowFoxOreDict.RAINBOW_FLOWER)
        recipeRainbowDoublePetal = BotaniaAPI.getLatestAddedRecipe()

        addShapelessOreDictRecipe(ItemStack(ShadowFoxItems.resource, 4, 7), ShadowFoxOreDict.RAINBOW_DOUBLE_FLOWER)
        recipeRainbowPetal = BotaniaAPI.getLatestAddedRecipe()

        recipeShimmerQuartz = addQuartzRecipes(ShadowFoxBlocks.shimmerQuartz, ShadowFoxBlocks.shimmerQuartzStairs, ShadowFoxBlocks.shimmerQuartzSlab)

        recipesAttributionHeads = ArrayList<RecipePetals>()
        recipesAttributionHeads.add(attributionSkull("yrsegal", ShadowFoxItems.irisSeeds, 16)) // Bifrost Seeds
        // Wire - I just love rainbows, what can I say?
        recipesAttributionHeads.add(attributionSkull("l0nekitsune", ShadowFoxItems.resource, 4)) // Flame-Laced coal
        // L0ne - "hot stuff" (because I'm classy like that)
        recipesAttributionHeads.add(attributionSkull("Tristaric", ShadowFoxItems.coatOfArms, 6)) // Irish Shield
        // Tris - The only item that remotely fits me.

        recipeCrysanthermum = BotaniaAPI.registerPetalRecipe(BotaniaAPI.internalHandler.getSubTileAsStack("crysanthermum"),
                BotaniaOreDict.PETAL[1], BotaniaOreDict.PETAL[1], // Orange
                BotaniaOreDict.PETAL[15], // Black
                BotaniaOreDict.PETAL[3], BotaniaOreDict.PETAL[3], // Light Blue
                BotaniaOreDict.RUNE[7], // Winter
                BotaniaOreDict.RUNE[5]) // Summer


        GameRegistry.addSmelting(ShadowFoxBlocks.irisWood0, ItemStack(Items.coal, 1, 1), 0.15F)
        GameRegistry.addSmelting(ShadowFoxBlocks.irisWood1, ItemStack(Items.coal, 1, 1), 0.15F)
        GameRegistry.addSmelting(ShadowFoxBlocks.irisWood2, ItemStack(Items.coal, 1, 1), 0.15F)
        GameRegistry.addSmelting(ShadowFoxBlocks.irisWood3, ItemStack(Items.coal, 1, 1), 0.15F)
        GameRegistry.addSmelting(ShadowFoxBlocks.rainbowWood, ItemStack(Items.coal, 1, 1), 0.15F)
        GameRegistry.addSmelting(ShadowFoxBlocks.lightningWood, ItemStack(Items.coal, 1, 1), 0.15F)
        GameRegistry.addSmelting(ShadowFoxBlocks.altWood0, ItemStack(Items.coal, 1, 1), 0.15F)
        GameRegistry.addSmelting(ShadowFoxBlocks.altWood1, ItemStack(Items.coal, 1, 1), 0.15F)
        GameRegistry.addSmelting(ShadowFoxBlocks.sealingWood, ItemStack(Items.coal, 1, 1), 0.15F)
        GameRegistry.addSmelting(ShadowFoxBlocks.lightningPlanks, ItemStack(ShadowFoxItems.resource, 2, 1), 0.1F) // Thunderous Splinters
        GameRegistry.addSmelting(ShadowFoxBlocks.netherPlanks, ItemStack(ShadowFoxItems.resource, 2, 3), 0.1F) // Infernal Splinters
        GameRegistry.addSmelting(ShadowFoxBlocks.netherWood, ItemStack(ShadowFoxItems.resource, 1, 4), 0.15F) // Flame-Laced Coal

    }

    private fun addQuartzRecipes(block: Block, stairs: Block, slab: Block): IRecipe {
        GameRegistry.addRecipe(ItemStack(block),
                "QQ",
                "QQ",
                'Q', ItemStack(ShadowFoxItems.resource, 1, 5))
        BotaniaAPI.registerManaAlchemyRecipe(ItemStack(ShadowFoxItems.resource, 4, 5), ItemStack(block, 1, 32767), 25)
        GameRegistry.addRecipe(ItemStack(block, 2, 2),
                "Q",
                "Q",
                'Q', block)
        GameRegistry.addRecipe(ItemStack(block, 1, 1),
                "Q",
                "Q",
                'Q', slab)
        addStairsAndSlabs(block, 0, stairs, slab)

        GameRegistry.addRecipe(ShapedOreRecipe(ItemStack(ShadowFoxItems.resource, 8, 5),
                "QQQ",
                "QCQ",
                "QQQ",
                'Q', "gemQuartz",
                'C', ShadowFoxOreDict.DYES[16]))
        return BotaniaAPI.getLatestAddedRecipe()
    }

    private fun addStairsAndSlabs(block: Block, meta: Int, stairs: Block, slab: Block) {
        GameRegistry.addRecipe(ItemStack(slab, 6),
                "QQQ",
                'Q', ItemStack(block, 1, meta))
        GameRegistry.addRecipe(ItemStack(stairs, 4),
                "  Q",
                " QQ",
                "QQQ",
                'Q', ItemStack(block, 1, meta))
        GameRegistry.addRecipe(ItemStack(stairs, 4),
                "Q  ",
                "QQ ",
                "QQQ",
                'Q', ItemStack(block, 1, meta))
    }

    fun skullStack(name: String): ItemStack {
        val stack = ItemStack(Items.skull, 1, 3)
        ItemNBTHelper.setString(stack, "SkullOwner", name)
        return stack
    }

    private fun attributionSkull(name: String, item: Item, meta: Int): RecipePetals {
        return BotaniaAPI.registerPetalRecipe(skullStack(name), *Array(16, { ItemStack(item, 1, meta) }))
    }

    private fun addOreDictRecipe(output: ItemStack, vararg recipe: Any) {
        GameRegistry.addRecipe(ShapedOreRecipe(output, *recipe))
    }

    private fun addShapelessOreDictRecipe(output: ItemStack, vararg recipe: Any) {
        GameRegistry.addRecipe(ShapelessOreRecipe(output, *recipe))
    }
}
