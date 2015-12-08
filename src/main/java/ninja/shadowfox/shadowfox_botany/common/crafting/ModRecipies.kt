package ninja.shadowfox.shadowfox_botany.common.crafting

import java.util.ArrayList
import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.init.Blocks
import net.minecraft.init.Items
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.IRecipe
import net.minecraftforge.oredict.ShapedOreRecipe
import net.minecraftforge.oredict.ShapelessOreRecipe
import ninja.shadowfox.shadowfox_botany.api.ShadowFoxAPI
import ninja.shadowfox.shadowfox_botany.api.recipe.RecipeTreeCrafting
import ninja.shadowfox.shadowfox_botany.common.blocks.ShadowFoxBlocks
import ninja.shadowfox.shadowfox_botany.common.item.ShadowFoxItems
import vazkii.botania.api.BotaniaAPI
import vazkii.botania.api.recipe.RecipePureDaisy
import vazkii.botania.api.recipe.RecipeManaInfusion
import vazkii.botania.common.lib.LibOreDict
import net.minecraftforge.oredict.RecipeSorter
import net.minecraftforge.oredict.RecipeSorter.Category
import vazkii.botania.common.block.ModBlocks as BotaniaBlocks
import vazkii.botania.common.block.ModFluffBlocks as BotaniaDecorBlocks
import vazkii.botania.common.item.ModItems as BotaniaItems

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
    val recipesPastoralSeeds: List<RecipeManaInfusion>
    val recipesColorOverride: IRecipe
    val recipesinvisibleLens: IRecipe
    val recipesinvisibleLensUndo: IRecipe
    val recipesAttribution: IRecipe
    val recipesKindling: IRecipe
    val recipesRainbowRod: IRecipe
    val recipesItemDisplay: List<IRecipe>
    val recipesItemDisplayElven: IRecipe
    val recipesPlainDirt: RecipePureDaisy
    val recipesIrisSapling: RecipePureDaisyExclusion

    val recipesTreeTest: RecipeTreeCrafting

    init {

        GameRegistry.addRecipe(RecipeRingDyes())
        RecipeSorter.register("shadowfox_botany:ringdye", RecipeRingDyes::class.java, Category.SHAPELESS, "")

        for (i in 0..15)
            GameRegistry.addShapedRecipe(ItemStack(ShadowFoxBlocks.coloredDirtBlock, 8, i),
                    "DDD",
                    "DPD",
                    "DDD", 'P', ItemStack(BotaniaBlocks.petalBlock, 1, i), 'D', ItemStack(Blocks.dirt, 1))
        GameRegistry.addShapedRecipe(ItemStack(ShadowFoxBlocks.rainbowDirtBlock, 8),
                "DDD",
                "DPD",
                "DDD", 'P', ItemStack(BotaniaBlocks.bifrostPerm), 'D', ItemStack(Blocks.dirt, 1))

        recipesColoredDirt = BotaniaAPI.getLatestAddedRecipes(17)

        for (i in 0..15)
            addShapelessOreDictRecipe(ItemStack(BotaniaItems.dye, 1, i), ShadowFoxBlocks.LEAVES[i], "pestleAndMortar")

        recipesLeafDyes = BotaniaAPI.getLatestAddedRecipes(16)
        for (i in 0..15)
            addShapelessOreDictRecipe(ItemStack(ShadowFoxBlocks.coloredPlanks, 4, i), ShadowFoxBlocks.WOOD[i])
        addShapelessOreDictRecipe(ItemStack(ShadowFoxBlocks.rainbowPlanks, 4), ShadowFoxBlocks.rainbowWood)

        recipesWoodPanel = BotaniaAPI.getLatestAddedRecipes(17)

        for (i in 0..15)
            GameRegistry.addRecipe(ItemStack(ShadowFoxBlocks.coloredSlabs[i], 6, i),
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
                    'P', LibOreDict.PIXIE_DUST, 'S', LibOreDict.DRAGONSTONE)
        addOreDictRecipe(ItemStack(ShadowFoxItems.colorfulSkyDirtRod, 1, 16),
                " PD",
                " RP",
                "S  ",
                'D', ItemStack(ShadowFoxBlocks.rainbowDirtBlock),
                'R', ItemStack(BotaniaItems.skyDirtRod, 1),
                'P', LibOreDict.PIXIE_DUST, 'S', LibOreDict.DRAGONSTONE)

        recipesColoredSkyDirtRod = BotaniaAPI.getLatestAddedRecipes(16)

        addOreDictRecipe(ItemStack(ShadowFoxItems.lightningRod, 1),
                " TW",
                " ST",
                "E  ",
                'E', LibOreDict.ENDER_AIR_BOTTLE,
                'T', LibOreDict.TERRA_STEEL,
                'S', LibOreDict.DREAMWOOD_TWIG,
                'W', LibOreDict.RUNE[13]) // Wrath

        recipesLightningRod = BotaniaAPI.getLatestAddedRecipe()

        addOreDictRecipe(ItemStack(ShadowFoxItems.emblem, 1, 0),
                "EGE",
                "TAT",
                " W ",
                'E', LibOreDict.ENDER_AIR_BOTTLE,
                'T', LibOreDict.TERRASTEEL_NUGGET,
                'G', LibOreDict.LIFE_ESSENCE,
                'W', LibOreDict.RUNE[13], // Wrath
                'A', "holyPendant")

        recipesPriestOfThor = BotaniaAPI.getLatestAddedRecipe()

        addOreDictRecipe(ItemStack(ShadowFoxItems.emblem, 1, 1),
                "DGD",
                "NAN",
                " P ",
                'D', LibOreDict.DRAGONSTONE,
                'N', "nuggetGold",
                'G', LibOreDict.LIFE_ESSENCE,
                'P', LibOreDict.RUNE[2], // Earth
                'A', "holyPendant")

        recipesPriestOfSif = BotaniaAPI.getLatestAddedRecipe()

        for (i in 0..15)
            addOreDictRecipe(ItemStack(ShadowFoxItems.coatOfArms, 1, i),
                    "LLL",
                    "LSL",
                    "LLL",
                    'L', ShadowFoxBlocks.LEAVES[i],
                    'S', LibOreDict.MANA_STRING)
        addOreDictRecipe(ItemStack(ShadowFoxItems.coatOfArms, 1, 16),
                "LLL",
                "LSL",
                "LLL",
                'L', ItemStack(ShadowFoxBlocks.rainbowLeaves),
                'S', LibOreDict.MANA_STRING)

        recipesCoatOfArms = BotaniaAPI.getLatestAddedRecipes(17)

        addOreDictRecipe(ItemStack(ShadowFoxItems.colorOverride),
                "PE ",
                "E E",
                " E ",
                'P', ItemStack(BotaniaItems.lens, 1, 14), // Paintslinger's Lens
                'E', LibOreDict.ELEMENTIUM)

        recipesColorOverride = BotaniaAPI.getLatestAddedRecipe()

        addOreDictRecipe(ItemStack(ShadowFoxItems.interdictionRod),
                " AS",
                " DA",
                "P  ",
                'P', LibOreDict.RUNE[15], // Pride
                'A', LibOreDict.RUNE[3], // Air
                'S', ItemStack(BotaniaItems.tornadoRod),
                'D', LibOreDict.DREAMWOOD_TWIG)

        recipesInterdictionRod = BotaniaAPI.getLatestAddedRecipe()

        addOreDictRecipe(ItemStack(ShadowFoxItems.emblem, 1, 2),
                "RGR",
                "NAN",
                " P ",
                'P', LibOreDict.RUNE[15], // Pride
                'N', LibOreDict.MANASTEEL_NUGGET,
                'G', LibOreDict.LIFE_ESSENCE,
                'R', LibOreDict.RUNE[3], // Air
                'A', "holyPendant")

        recipesPriestOfNjord = BotaniaAPI.getLatestAddedRecipe()

        addOreDictRecipe(ItemStack(ShadowFoxItems.attributionBauble),
                "S S",
                "Q Q",
                " G ",
                'G', "ingotGold",
                'Q', "gemQuartz",
                'S', LibOreDict.MANA_STRING)

        recipesAttribution = BotaniaAPI.getLatestAddedRecipe()

        addOreDictRecipe(ItemStack(ShadowFoxBlocks.kindling),
                " S ",
                "SBS",
                " S ",
                'B', "powderBlaze",
                'S', LibOreDict.MANA_STRING)

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

        recipesPlainDirt = BotaniaAPI.registerPureDaisyRecipe("irisDirt", Blocks.dirt, 0)

        recipesIrisSapling = RecipePureDaisyExclusion("treeSapling", ShadowFoxBlocks.irisSapling, 0)
        BotaniaAPI.pureDaisyRecipes.add(recipesIrisSapling as RecipePureDaisy)

        addOreDictRecipe(ItemStack(ShadowFoxItems.rainbowRod),
                " GB",
                " DG",
                "D  ",
                'G', "glowstone",
                'B', ItemStack(BotaniaBlocks.bifrostPerm),
                'D', LibOreDict.DREAMWOOD_TWIG)

        recipesRainbowRod = BotaniaAPI.getLatestAddedRecipe()

        addOreDictRecipe(ItemStack(ShadowFoxBlocks.itemDisplay),
                "N",
                "W",
                'N', LibOreDict.MANASTEEL_NUGGET,
                'W', ItemStack(BotaniaDecorBlocks.livingwoodSlab))
        addOreDictRecipe(ItemStack(ShadowFoxBlocks.itemDisplay, 1, 1),
                "N",
                "W",
                'N', LibOreDict.TERRASTEEL_NUGGET,
                'W', ItemStack(BotaniaDecorBlocks.livingwoodSlab))

        recipesItemDisplay = BotaniaAPI.getLatestAddedRecipes(2)

        addOreDictRecipe(ItemStack(ShadowFoxBlocks.itemDisplay, 1, 2),
                "N",
                "W",
                'N', LibOreDict.ELEMENTIUM_NUGGET,
                'W', ItemStack(BotaniaDecorBlocks.dreamwoodSlab))

        recipesItemDisplayElven = BotaniaAPI.getLatestAddedRecipe()

        recipesTreeTest = ShadowFoxAPI.addTreeRecipe(500000,
                Blocks.diamond_block, 0,
                ItemStack(Items.apple, 1), ItemStack(Items.apple, 1), ItemStack(Items.apple, 1), ItemStack(Items.apple, 1))

        GameRegistry.addSmelting(ShadowFoxBlocks.irisWood0, ItemStack(Items.coal, 1, 1), 0.15F)
        GameRegistry.addSmelting(ShadowFoxBlocks.irisWood1, ItemStack(Items.coal, 1, 1), 0.15F)
        GameRegistry.addSmelting(ShadowFoxBlocks.irisWood2, ItemStack(Items.coal, 1, 1), 0.15F)
        GameRegistry.addSmelting(ShadowFoxBlocks.irisWood3, ItemStack(Items.coal, 1, 1), 0.15F)
        GameRegistry.addSmelting(ShadowFoxBlocks.rainbowWood, ItemStack(Items.coal, 1, 1), 0.15F)

    }

    private fun addOreDictRecipe(output: ItemStack, vararg recipe: Any) {
        GameRegistry.addRecipe(ShapedOreRecipe(output, *recipe))
    }

    private fun addShapelessOreDictRecipe(output: ItemStack, vararg recipe: Any) {
        GameRegistry.addRecipe(ShapelessOreRecipe(output, *recipe))
    }
}
