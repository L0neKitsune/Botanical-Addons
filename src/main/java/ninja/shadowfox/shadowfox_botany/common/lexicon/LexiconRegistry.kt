package ninja.shadowfox.shadowfox_botany.common.lexicon

import ninja.shadowfox.shadowfox_botany.ShadowfoxBotany
import ninja.shadowfox.shadowfox_botany.common.core.handler.ConfigHandler
import ninja.shadowfox.shadowfox_botany.common.blocks.ShadowFoxBlocks
import ninja.shadowfox.shadowfox_botany.common.item.ShadowFoxItems
import ninja.shadowfox.shadowfox_botany.common.crafting.ModRecipes
import vazkii.botania.common.lexicon.page.PageTreeCrafting
import net.minecraft.item.ItemStack
import vazkii.botania.api.BotaniaAPI
import vazkii.botania.common.lexicon.LexiconData
import vazkii.botania.api.lexicon.LexiconEntry
import vazkii.botania.api.lexicon.LexiconRecipeMappings
import vazkii.botania.common.lexicon.page.PageCraftingRecipe
import vazkii.botania.common.lexicon.page.PageManaInfusionRecipe
import vazkii.botania.common.lexicon.page.PageMultiblock
import vazkii.botania.common.lexicon.page.PageText

public object LexiconRegistry {

    val coloredDirt : LexiconEntry
    val irisSapling : LexiconEntry
    val technicolor : LexiconEntry
    val lightningRod : LexiconEntry
    val interdictionRod : LexiconEntry
    val pastoralSeeds : LexiconEntry
    val coatOfArms : LexiconEntry
    val colorOverride : LexiconEntry
    val treeCrafting : LexiconEntry
    val dendrology: ShadowFoxLexiconCategory
    val attribution : LexiconEntry
    val sealCreepers: LexiconEntry
    val kindling: LexiconEntry
    val waveRod: LexiconEntry
    val itemDisplay: LexiconEntry
    val lightningSapling: LexiconEntry

    init {

        dendrology = ShadowFoxLexiconCategory("dendrology", 1)

        coloredDirt = ShadowfoxLexiconEntry("coloredDirt", BotaniaAPI.categoryMisc, ShadowFoxBlocks.rainbowDirtBlock)
        coloredDirt.setLexiconPages(PageText("0"), PageCraftingRecipe("1", ModRecipes.recipesColoredDirt))

        technicolor = ShadowfoxLexiconEntry("technicolorRod", BotaniaAPI.categoryTools, ItemStack(ShadowFoxItems.colorfulSkyDirtRod, 1, 16)).setKnowledgeType(BotaniaAPI.elvenKnowledge)
        technicolor.setLexiconPages(PageText("0"),
                PageCraftingRecipe("1", ModRecipes.recipesColoredSkyDirtRod),
                PageText("2"),
                PageCraftingRecipe("3", ModRecipes.recipesPriestOfSif))

        lightningRod = ShadowfoxLexiconEntry("lightningRod", BotaniaAPI.categoryTools, ShadowFoxItems.lightningRod).setKnowledgeType(BotaniaAPI.elvenKnowledge)
        lightningRod.setLexiconPages(PageText("0"),
                PageCraftingRecipe("1", ModRecipes.recipesLightningRod),
                PageText("2"),
                PageCraftingRecipe("3", ModRecipes.recipesPriestOfThor))

        irisSapling = ShadowfoxLexiconEntry("irisSapling", dendrology, ShadowFoxBlocks.irisSapling)
        irisSapling.setPriority().setLexiconPages(PageText("0"),
                PageCraftingRecipe("1", ModRecipes.recipesWoodPanel),
                PageCraftingRecipe("2", ModRecipes.recipesSlabs),
                PageCraftingRecipe("3", ModRecipes.recipesStairsR + ModRecipes.recipesStairsL),
                PageCraftingRecipe("4", ModRecipes.recipesSlabsFull),
                PageCraftingRecipe("5", ModRecipes.recipesLeafDyes))

        pastoralSeeds = ShadowfoxLexiconEntry("irisSeeds", BotaniaAPI.categoryTools, ShadowFoxBlocks.rainbowGrass)
        pastoralSeeds.setLexiconPages(PageText("0"),
                PageCraftingRecipe("1", ModRecipes.recipesRedstoneRoot),
                PageManaInfusionRecipe("2", ModRecipes.recipesPastoralSeeds))

        coatOfArms = ShadowfoxLexiconEntry("coatOfArms", BotaniaAPI.categoryMisc, ItemStack(ShadowFoxItems.coatOfArms, 1, 16))
        coatOfArms.setLexiconPages(PageText("0"),
                PageCraftingRecipe("1", ModRecipes.recipesCoatOfArms))

        colorOverride = ShadowfoxLexiconEntry("colorOverride", BotaniaAPI.categoryMisc, ShadowFoxItems.colorOverride).setKnowledgeType(BotaniaAPI.elvenKnowledge)
        colorOverride.setLexiconPages(PageText("0"),
                PageCraftingRecipe("1", ModRecipes.recipesColorOverride))

        interdictionRod = ShadowfoxLexiconEntry("interdictionRod", BotaniaAPI.categoryTools, ShadowFoxItems.interdictionRod).setKnowledgeType(BotaniaAPI.elvenKnowledge)
        interdictionRod.setLexiconPages(PageText("0"),
                PageCraftingRecipe("1", ModRecipes.recipesInterdictionRod),
                PageText("2"),
                PageCraftingRecipe("3", ModRecipes.recipesPriestOfNjord))

        treeCrafting = ShadowfoxLexiconEntry("treeCrafting", dendrology, ShadowFoxBlocks.treeCrafterBlockRB)
        treeCrafting.setPriority().setLexiconPages(PageText("0"),
                PageMultiblock("1", ShadowFoxBlocks.treeCrafter))

        attribution = ShadowfoxLexiconEntry("attribution", BotaniaAPI.categoryBaubles, ShadowFoxItems.attributionBauble)
        attribution.setLexiconPages(PageText("0"),
                PageCraftingRecipe("1", ModRecipes.recipesAttribution))

        sealCreepers = ShadowfoxLexiconEntry("sealCreepers", BotaniaAPI.categoryBasics, ShadowFoxItems.wiltedLotus)
        if (ConfigHandler.blackLotusDropRate > 0.0)
            sealCreepers.setLexiconPages(PageText("0"),
                    PageText("1Drop"))
        else
            sealCreepers.setLexiconPages(PageText("0"),
                    PageText("1NoDrop"))

        kindling = ShadowfoxLexiconEntry("kindling", BotaniaAPI.categoryMisc, ShadowFoxBlocks.kindling)
        kindling.setLexiconPages(PageText("0"),
                PageCraftingRecipe("1", ModRecipes.recipesKindling))

        waveRod = ShadowfoxLexiconEntry("waveRod", BotaniaAPI.categoryTools, ShadowFoxItems.rainbowRod).setKnowledgeType(BotaniaAPI.elvenKnowledge)
        waveRod.setLexiconPages(PageText("0"),
                PageCraftingRecipe("1", ModRecipes.recipesRainbowRod))

        itemDisplay = ShadowfoxLexiconEntry("itemDisplay", BotaniaAPI.categoryMisc, ItemStack(ShadowFoxBlocks.itemDisplay, 1, 1))
        itemDisplay.setLexiconPages(PageText("0"),
                PageCraftingRecipe("1", ModRecipes.recipesItemDisplay))

        lightningSapling = ShadowfoxLexiconEntry("lightningSapling", dendrology, ShadowFoxBlocks.lightningSapling)
        lightningSapling.setLexiconPages(PageText("0"),
                PageTreeCrafting("1", ModRecipes.recipesLightningTree))

        LexiconRecipeMappings.map(ItemStack(ShadowFoxBlocks.irisSapling), irisSapling, 0)
        LexiconRecipeMappings.map(ItemStack(ShadowFoxItems.lightningRod), lightningRod, 1)
        LexiconRecipeMappings.map(ItemStack(ShadowFoxItems.interdictionRod), interdictionRod, 1)
        LexiconRecipeMappings.map(ItemStack(ShadowFoxItems.rainbowRod), waveRod, 1)
        LexiconRecipeMappings.map(ItemStack(ShadowFoxItems.emblem, 1, 0), lightningRod, 3)
        LexiconRecipeMappings.map(ItemStack(ShadowFoxItems.emblem, 1, 1), technicolor, 3)
        LexiconRecipeMappings.map(ItemStack(ShadowFoxItems.emblem, 1, 2), interdictionRod, 3)
        LexiconRecipeMappings.map(ItemStack(ShadowFoxItems.colorOverride), colorOverride, 1)

        LexiconRecipeMappings.map(ItemStack(ShadowFoxItems.attributionBauble, 1, 0), attribution, 1)
        LexiconRecipeMappings.map(ItemStack(ShadowFoxItems.attributionBauble, 1, 1), LexiconData.tinyPotato, 1)

        LexiconRecipeMappings.map(ItemStack(ShadowFoxItems.wiltedLotus, 1, 0), sealCreepers, 1)
        LexiconRecipeMappings.map(ItemStack(ShadowFoxItems.wiltedLotus, 1, 1), sealCreepers, 1)

        LexiconRecipeMappings.map(ItemStack(ShadowFoxBlocks.treeCrafterBlock), treeCrafting, 1)
        LexiconRecipeMappings.map(ItemStack(ShadowFoxBlocks.treeCrafterBlockRB), treeCrafting, 1)

        LexiconRecipeMappings.map(ItemStack(ShadowFoxBlocks.lightningSapling), lightningSapling, 1)
        LexiconRecipeMappings.map(ItemStack(ShadowFoxBlocks.lightningWood), lightningSapling, 1)
        LexiconRecipeMappings.map(ItemStack(ShadowFoxBlocks.lightningLeaves), lightningSapling, 1)

        for (i in 0..2)
            LexiconRecipeMappings.map(ItemStack(ShadowFoxBlocks.itemDisplay, 1, i), itemDisplay, 1)

        for (i in 0..3){
            LexiconRecipeMappings.map(ItemStack(ShadowFoxBlocks.irisWood0, 1, i), irisSapling, 1)
            LexiconRecipeMappings.map(ItemStack(ShadowFoxBlocks.irisWood1, 1, i), irisSapling, 1)
            LexiconRecipeMappings.map(ItemStack(ShadowFoxBlocks.irisWood2, 1, i), irisSapling, 1)
            LexiconRecipeMappings.map(ItemStack(ShadowFoxBlocks.irisWood3, 1, i), irisSapling, 1)
        }
        LexiconRecipeMappings.map(ItemStack(ShadowFoxBlocks.rainbowWood), irisSapling, 1)
        for (i in 0..15) {
            LexiconRecipeMappings.map(ItemStack(ShadowFoxBlocks.coloredPlanks, 1, i), irisSapling, 1)
            LexiconRecipeMappings.map(ItemStack(ShadowFoxBlocks.coloredSlabs[i], 1), irisSapling, 2)
            LexiconRecipeMappings.map(ItemStack(ShadowFoxBlocks.coloredStairs[i], 1), irisSapling, 3)
            LexiconRecipeMappings.map(ItemStack(ShadowFoxBlocks.coloredDirtBlock, 1, i), coloredDirt, 1)
            LexiconRecipeMappings.map(ItemStack(ShadowFoxBlocks.irisGrass, 1, i), pastoralSeeds, 0)
        }
        LexiconRecipeMappings.map(ItemStack(ShadowFoxBlocks.rainbowLeaves), irisSapling, 5)
        LexiconRecipeMappings.map(ItemStack(ShadowFoxBlocks.rainbowPlanks), irisSapling, 1)
        LexiconRecipeMappings.map(ItemStack(ShadowFoxBlocks.rainbowSlabs), irisSapling, 2)
        LexiconRecipeMappings.map(ItemStack(ShadowFoxBlocks.rainbowStairs), irisSapling, 3)
        LexiconRecipeMappings.map(ItemStack(ShadowFoxBlocks.rainbowDirtBlock), coloredDirt, 1)
        LexiconRecipeMappings.map(ItemStack(ShadowFoxBlocks.rainbowGrass), pastoralSeeds, 0)
        for (i in 0..16) {
            LexiconRecipeMappings.map(ItemStack(ShadowFoxItems.colorfulSkyDirtRod, 1, i), technicolor, 1)
            LexiconRecipeMappings.map(ItemStack(ShadowFoxItems.irisSeeds, 1, i), pastoralSeeds, 2)
            LexiconRecipeMappings.map(ItemStack(ShadowFoxItems.coatOfArms, 1, i), coatOfArms, 1)
        }
        for (i in 0..7) {
            LexiconRecipeMappings.map(ItemStack(ShadowFoxBlocks.irisLeaves0, 1, i), irisSapling, 0)
            LexiconRecipeMappings.map(ItemStack(ShadowFoxBlocks.irisLeaves1, 1, i), irisSapling, 0)
            LexiconRecipeMappings.map(ItemStack(ShadowFoxBlocks.irisTallGrass0, 1, i), pastoralSeeds, 0)
            LexiconRecipeMappings.map(ItemStack(ShadowFoxBlocks.irisTallGrass1, 1, i), pastoralSeeds, 0)
        }
        LexiconRecipeMappings.map(ItemStack(ShadowFoxBlocks.rainbowTallGrass), pastoralSeeds, 0)

    }
}
