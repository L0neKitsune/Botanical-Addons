package ninja.shadowfox.shadowfox_botany.common.compat.thaumcraft

import net.minecraft.block.Block
import ninja.shadowfox.shadowfox_botany.ShadowfoxBotany
import ninja.shadowfox.shadowfox_botany.api.ShadowFoxAPI
import ninja.shadowfox.shadowfox_botany.api.recipe.RecipeTreeCrafting
import ninja.shadowfox.shadowfox_botany.common.core.handler.ConfigHandler
import thaumcraft.api.ItemApi
import vazkii.botania.common.Botania
import ninja.shadowfox.shadowfox_botany.lib.LibOreDict as ShadowFoxOreDict
import vazkii.botania.common.lib.LibOreDict as BotaniaOreDict

/**
 * @author WireSegal
 * Created at 9:18 PM on 1/19/16.
 */
object ThaumcraftSuffusionRecipes {

    lateinit var greatwoodRecipe: RecipeTreeCrafting
    lateinit var silverwoodRecipe: RecipeTreeCrafting

    val saplingBlock = Block.getBlockFromItem(ItemApi.getBlock("blockCustomPlant", 0).item)
    val balanceShard = ItemApi.getItem("itemShard", 6)

    fun initRecipes() {
        if (ShadowfoxBotany.thaumcraftLoaded && Botania.gardenOfGlassLoaded && ConfigHandler.addThaumcraftTreeSuffusion) {
            greatwoodRecipe = ShadowFoxAPI.addTreeRecipe(10000,
                    saplingBlock, 0,
                    70,
                    BotaniaOreDict.ELEMENTIUM, BotaniaOreDict.ELEMENTIUM, BotaniaOreDict.ELEMENTIUM,
                    BotaniaOreDict.RUNE[4], // Spring
                    ShadowFoxOreDict.LEAVES[13], ShadowFoxOreDict.LEAVES[13], ShadowFoxOreDict.LEAVES[13], // Green
                    "shardEarth")
            silverwoodRecipe = ShadowFoxAPI.addTreeRecipe(30000,
                    saplingBlock, 1,
                    210,
                    BotaniaOreDict.ELEMENTIUM, BotaniaOreDict.ELEMENTIUM, BotaniaOreDict.ELEMENTIUM,
                    BotaniaOreDict.RUNE[7], // Winter
                    ShadowFoxOreDict.LEAVES[3], ShadowFoxOreDict.LEAVES[3], ShadowFoxOreDict.LEAVES[3], // Light Blue
                    balanceShard)
        }
    }
}
