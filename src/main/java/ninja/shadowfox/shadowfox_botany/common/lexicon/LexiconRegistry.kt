package ninja.shadowfox.shadowfox_botany.common.lexicon

import ninja.shadowfox.shadowfox_botany.common.blocks.ShadowFoxBlocks
import ninja.shadowfox.shadowfox_botany.common.crafting.ModRecipes
import vazkii.botania.api.BotaniaAPI
import vazkii.botania.api.lexicon.KnowledgeType
import vazkii.botania.api.lexicon.LexiconEntry
import vazkii.botania.common.lexicon.page.PageCraftingRecipe
import vazkii.botania.common.lexicon.page.PageText

public object LexiconRegistry {

    val coloredDirt : LexiconEntry
    val irisSapling : LexiconEntry
    val techicolor : LexiconEntry
    val lightningRod : LexiconEntry

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

    }
}