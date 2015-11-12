package ninja.shadowfox.shadowfox_botany.common.lexicon

import ninja.shadowfox.shadowfox_botany.common.blocks.ShadowFoxBlocks
import ninja.shadowfox.shadowfox_botany.common.item.ShadowFoxItems
import ninja.shadowfox.shadowfox_botany.common.crafting.ModRecipes
import net.minecraft.item.ItemStack
import vazkii.botania.api.BotaniaAPI
import vazkii.botania.api.lexicon.LexiconEntry
import vazkii.botania.api.lexicon.LexiconRecipeMappings
import vazkii.botania.common.lexicon.page.PageCraftingRecipe
import vazkii.botania.common.lexicon.page.PageManaInfusionRecipe
import vazkii.botania.common.lexicon.page.PageText

public object LexiconRegistry {

    val coloredDirt : LexiconEntry
    val irisSapling : LexiconEntry
    val techicolor : LexiconEntry
    val lightningRod : LexiconEntry
    val pastoralSeeds : LexiconEntry

    init {
        coloredDirt = ShadowfoxLexiconEntry("coloredDirt", BotaniaAPI.categoryMisc)
        coloredDirt.setLexiconPages(PageText("0"), PageCraftingRecipe("1", ModRecipes.recipesColoredDirt))

        techicolor = ShadowfoxLexiconEntry("techicolorRod", BotaniaAPI.categoryTools).setKnowledgeType(BotaniaAPI.elvenKnowledge)
        techicolor.setLexiconPages(PageText("0"), PageCraftingRecipe("1", ModRecipes.recipesColoredSkyDirtRod))

        lightningRod = ShadowfoxLexiconEntry("lightningRod", BotaniaAPI.categoryTools).setKnowledgeType(BotaniaAPI.elvenKnowledge)
        lightningRod.setLexiconPages(PageText("0"), PageCraftingRecipe("1", ModRecipes.recipesLightningRod))

        irisSapling = ShadowfoxLexiconEntry("irisSapling", BotaniaAPI.categoryMisc, block = ShadowFoxBlocks.irisSapling)
        irisSapling.setLexiconPages(PageText("0"),
                PageCraftingRecipe("1", ModRecipes.recipesWoodPanel),
                PageCraftingRecipe("2", ModRecipes.recipesSlabs),
                PageCraftingRecipe("3", ModRecipes.recipesStairsR + ModRecipes.recipesStairsL),
                PageCraftingRecipe("4", ModRecipes.recipesSlabsFull),
                PageCraftingRecipe("5", ModRecipes.recipesLeafDyes))

        pastoralSeeds = ShadowfoxLexiconEntry("irisSeeds", BotaniaAPI.categoryTools, block = ShadowFoxBlocks.irisGrass)
        pastoralSeeds.setLexiconPages(PageText("0"),
                PageCraftingRecipe("1", ModRecipes.recipesRedstoneRoot),
                PageManaInfusionRecipe("2", ModRecipes.recipesPastoralSeeds))

        LexiconRecipeMappings.map(ItemStack(ShadowFoxBlocks.irisSapling), irisSapling, 0)
        LexiconRecipeMappings.map(ItemStack(ShadowFoxItems.lightningRod), lightningRod, 1)
        for (i in 0..3){
            LexiconRecipeMappings.map(ItemStack(ShadowFoxBlocks.irisWood0, 1, i), irisSapling, 1)
            LexiconRecipeMappings.map(ItemStack(ShadowFoxBlocks.irisWood1, 1, i), irisSapling, 1)
            LexiconRecipeMappings.map(ItemStack(ShadowFoxBlocks.irisWood2, 1, i), irisSapling, 1)
            LexiconRecipeMappings.map(ItemStack(ShadowFoxBlocks.irisWood3, 1, i), irisSapling, 1)
        }
        for (i in 0..15) {
            LexiconRecipeMappings.map(ItemStack(ShadowFoxItems.colorfulSkyDirtRod, 1, i), techicolor, 1)
            LexiconRecipeMappings.map(ItemStack(ShadowFoxBlocks.irisLeaves, 1, i), irisSapling, 5)
            LexiconRecipeMappings.map(ItemStack(ShadowFoxBlocks.coloredPlanks, 1, i), irisSapling, 1)
            LexiconRecipeMappings.map(ItemStack(ShadowFoxBlocks.coloredSlabs[i], 1), irisSapling, 2)
            LexiconRecipeMappings.map(ItemStack(ShadowFoxBlocks.coloredStairs[i], 1), irisSapling, 3)
            LexiconRecipeMappings.map(ItemStack(ShadowFoxBlocks.coloredDirtBlock, 1, i), coloredDirt, 1)
            LexiconRecipeMappings.map(ItemStack(ShadowFoxBlocks.irisGrass, 1, i), pastoralSeeds, 0)
            LexiconRecipeMappings.map(ItemStack(ShadowFoxItems.irisSeeds, 1, i), pastoralSeeds, 2)
        }
        for (i in 0..7) {
            LexiconRecipeMappings.map(ItemStack(ShadowFoxBlocks.irisTallGrass0, 1, i), pastoralSeeds, 0)
            LexiconRecipeMappings.map(ItemStack(ShadowFoxBlocks.irisTallGrass1, 1, i), pastoralSeeds, 0)
        }

    }
}
