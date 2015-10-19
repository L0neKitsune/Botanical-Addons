package ninja.shadowfox.shadowfox_botany.common.lexicon

import ninja.shadowfox.shadowfox_botany.crafting.ModRecipes
import vazkii.botania.api.BotaniaAPI
import vazkii.botania.api.lexicon.LexiconEntry
import vazkii.botania.common.lexicon.page.PageCraftingRecipe
import vazkii.botania.common.lexicon.page.PageText

/**
 * Created by l0nekitsune on 10/18/15.
 */

public object LexiconRegistry {

    val coloredDirt : LexiconEntry

    init {
        coloredDirt = ShadowfoxLexiconEntry("coloredDirt", BotaniaAPI.categoryMisc)
        coloredDirt.setLexiconPages(PageText("0"), PageCraftingRecipe("1", ModRecipes.recipesColoredDirt))
    }
}