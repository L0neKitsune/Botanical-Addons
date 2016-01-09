package ninja.shadowfox.shadowfox_botany.common.crafting

import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.init.Blocks
import net.minecraft.init.Items
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.IRecipe
import net.minecraftforge.oredict.RecipeSorter
import net.minecraftforge.oredict.RecipeSorter.Category
import net.minecraftforge.oredict.ShapedOreRecipe
import net.minecraftforge.oredict.ShapelessOreRecipe
import ninja.shadowfox.shadowfox_botany.api.ShadowFoxAPI
import ninja.shadowfox.shadowfox_botany.api.recipe.RecipeTreeCrafting
import ninja.shadowfox.shadowfox_botany.common.blocks.ShadowFoxBlocks
import ninja.shadowfox.shadowfox_botany.common.item.ShadowFoxItems
import vazkii.botania.api.BotaniaAPI
import vazkii.botania.api.recipe.RecipeManaInfusion
import vazkii.botania.api.recipe.RecipePetals
import vazkii.botania.api.recipe.RecipePureDaisy
import vazkii.botania.common.core.helper.ItemNBTHelper
import vazkii.botania.common.lib.LibOreDict
import java.util.*
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

    val recipesPastoralSeeds: List<RecipeManaInfusion>

    val recipesAttributionHeads: List<RecipePetals>

    val recipesPlainDirt: RecipePureDaisy
    val recipesIrisSapling: RecipePureDaisyExclusion

    val recipesLightningTree: RecipeTreeCrafting
    val recipesInfernalTree: RecipeTreeCrafting


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
                " EW",
                " SD",
                "S  ",
                'E', "woodSplintersLightning",
                'D', LibOreDict.DRAGONSTONE,
                'S', "twigThunderwood",
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

        recipesLightningTree = ShadowFoxAPI.addTreeRecipe(50000,
                ShadowFoxBlocks.lightningSapling, 0,
                350,
                LibOreDict.MANA_STEEL, LibOreDict.MANA_STEEL, LibOreDict.MANA_STEEL,
                LibOreDict.RUNE[13], // Wrath
                ShadowFoxBlocks.LEAVES[10], ShadowFoxBlocks.LEAVES[10], ShadowFoxBlocks.LEAVES[10], // Purple
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
                'L', LibOreDict.LIVING_WOOD, 'C', ItemStack(Blocks.chest))

        recipesLivingwoodFunnel = BotaniaAPI.getLatestAddedRecipe()

        recipesInfernalTree = ShadowFoxAPI.addTreeRecipe(10000,
                ShadowFoxBlocks.netherSapling, 0,
                70,
                "ingotBrickNether", ItemStack(Items.ghast_tear), "ingotBrickNether",
                LibOreDict.RUNE[1], // Fire
                ShadowFoxBlocks.LEAVES[14], ShadowFoxBlocks.LEAVES[14], ShadowFoxBlocks.LEAVES[14], // Red
                LibOreDict.BLAZE_BLOCK)

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

        recipesAttributionHeads = ArrayList<RecipePetals>()
        recipesAttributionHeads.add(attributionSkull("yrsegal", ShadowFoxItems.irisSeeds, 16)) // Bifrost Seeds
        recipesAttributionHeads.add(attributionSkull("l0nekitsune", ShadowFoxItems.resource, 3)) // Infernal Splinters
        recipesAttributionHeads.add(attributionSkull("Tristaric", ShadowFoxItems.resource, 1)) // Thunderous Splinters


        GameRegistry.addSmelting(ShadowFoxBlocks.irisWood0, ItemStack(Items.coal, 1, 1), 0.15F)
        GameRegistry.addSmelting(ShadowFoxBlocks.irisWood1, ItemStack(Items.coal, 1, 1), 0.15F)
        GameRegistry.addSmelting(ShadowFoxBlocks.irisWood2, ItemStack(Items.coal, 1, 1), 0.15F)
        GameRegistry.addSmelting(ShadowFoxBlocks.irisWood3, ItemStack(Items.coal, 1, 1), 0.15F)
        GameRegistry.addSmelting(ShadowFoxBlocks.rainbowWood, ItemStack(Items.coal, 1, 1), 0.15F)
        GameRegistry.addSmelting(ShadowFoxBlocks.lightningWood, ItemStack(Items.coal, 1, 1), 0.15F)
        GameRegistry.addSmelting(ShadowFoxBlocks.altWood0, ItemStack(Items.coal, 1, 1), 0.15F)
        GameRegistry.addSmelting(ShadowFoxBlocks.altWood1, ItemStack(Items.coal, 1, 1), 0.15F)
        GameRegistry.addSmelting(ShadowFoxBlocks.lightningPlanks, ItemStack(ShadowFoxItems.resource, 2, 1), 0.1F) // Thunderous Splinters
        GameRegistry.addSmelting(ShadowFoxBlocks.netherPlanks, ItemStack(ShadowFoxItems.resource, 2, 3), 0.1F) // Infernal Splinters
        GameRegistry.addSmelting(ShadowFoxBlocks.netherWood, ItemStack(ShadowFoxItems.resource, 1, 4), 0.15F) // Flame-Laced Coal

    }

    private fun skullStack(name: String): ItemStack {
        val stack = ItemStack(Items.skull, 1, 3)
        ItemNBTHelper.setString(stack, "SkullOwner", name)
        return stack
    }

    private fun attributionSkull(name: String, item: Item, meta: Int): RecipePetals {
        return BotaniaAPI.registerPetalRecipe(skullStack(name), *Array(16, {ItemStack(item, 1, meta)}))
    }

    private fun addOreDictRecipe(output: ItemStack, vararg recipe: Any) {
        GameRegistry.addRecipe(ShapedOreRecipe(output, *recipe))
    }

    private fun addShapelessOreDictRecipe(output: ItemStack, vararg recipe: Any) {
        GameRegistry.addRecipe(ShapelessOreRecipe(output, *recipe))
    }
}
