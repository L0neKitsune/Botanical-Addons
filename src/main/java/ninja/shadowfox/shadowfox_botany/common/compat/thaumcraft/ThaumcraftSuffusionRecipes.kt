package ninja.shadowfox.shadowfox_botany.common.compat.thaumcraft

import net.minecraft.block.Block
import net.minecraft.init.Blocks
import net.minecraft.item.ItemStack
import ninja.shadowfox.shadowfox_botany.api.ShadowFoxAPI
import ninja.shadowfox.shadowfox_botany.api.recipe.RecipeTreeCrafting
import thaumcraft.api.ItemApi
import ninja.shadowfox.shadowfox_botany.lib.LibOreDict as ShadowFoxOreDict
import vazkii.botania.common.lib.LibOreDict as BotaniaOreDict

/**
 * @author WireSegal
 * Created at 9:18 PM on 1/19/16.
 */
object ThaumcraftSuffusionRecipes {

    var recipesLoaded = false

    lateinit var greatwoodRecipe: RecipeTreeCrafting
    lateinit var silverwoodRecipe: RecipeTreeCrafting
    lateinit var shimmerleafRecipe: RecipeTreeCrafting
    lateinit var cinderpearlRecipe: RecipeTreeCrafting
    lateinit var vishroomRecipe: RecipeTreeCrafting

    lateinit var plantBlock: Block
    lateinit var balanceShard: ItemStack
    lateinit var silverwoodLeaves: ItemStack

    fun initRecipes() {
        plantBlock = Block.getBlockFromItem(ItemApi.getBlock("blockCustomPlant", 0).item)
        balanceShard = ItemApi.getItem("itemShard", 6)
        silverwoodLeaves = ItemApi.getBlock("blockMagicalLeaves", 1)

        recipesLoaded = true

        greatwoodRecipe = ShadowFoxAPI.addTreeRecipe(10000,
                plantBlock, 0,
                70,
                BotaniaOreDict.ELEMENTIUM, BotaniaOreDict.ELEMENTIUM, BotaniaOreDict.ELEMENTIUM,
                BotaniaOreDict.RUNE[4], // Spring
                ShadowFoxOreDict.LEAVES[13], ShadowFoxOreDict.LEAVES[13], ShadowFoxOreDict.LEAVES[13], // Green
                "shardEarth")
        silverwoodRecipe = ShadowFoxAPI.addTreeRecipe(30000,
                plantBlock, 1,
                210,
                BotaniaOreDict.ELEMENTIUM, BotaniaOreDict.ELEMENTIUM, BotaniaOreDict.ELEMENTIUM,
                BotaniaOreDict.RUNE[7], // Winter
                ShadowFoxOreDict.LEAVES[3], ShadowFoxOreDict.LEAVES[3], ShadowFoxOreDict.LEAVES[3], // Light Blue
                balanceShard)
        shimmerleafRecipe = ShadowFoxAPI.addTreeRecipe(5000,
                plantBlock, 2,
                100,
                silverwoodLeaves,
                "shardOrder",
                BotaniaOreDict.PIXIE_DUST)
        cinderpearlRecipe = ShadowFoxAPI.addTreeRecipe(5000,
                plantBlock, 3,
                100,
                "powderBlaze",
                "shardFire",
                BotaniaOreDict.PIXIE_DUST)
        vishroomRecipe = ShadowFoxAPI.addTreeRecipe(5000,
                plantBlock, 5,
                100,
                ItemStack(Blocks.brown_mushroom),
                ItemStack(Blocks.red_mushroom),
                BotaniaOreDict.PIXIE_DUST)
    }
}
