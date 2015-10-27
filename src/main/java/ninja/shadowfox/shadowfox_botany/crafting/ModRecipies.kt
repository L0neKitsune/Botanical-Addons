package ninja.shadowfox.shadowfox_botany.crafting

import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.block.Block
import net.minecraft.init.Blocks
import net.minecraft.init.Items
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.CraftingManager
import net.minecraft.item.crafting.IRecipe
import net.minecraftforge.oredict.ShapedOreRecipe
import net.minecraftforge.oredict.ShapelessOreRecipe
import ninja.shadowfox.shadowfox_botany.common.blocks.ShadowFoxBlocks
import ninja.shadowfox.shadowfox_botany.common.item.ShadowFoxItems
import vazkii.botania.api.BotaniaAPI
import vazkii.botania.common.lib.LibOreDict
import vazkii.botania.common.block.ModBlocks as BotaniaBlocks
import vazkii.botania.common.item.ModItems as BotaniaItems

public object ModRecipes {
    var recipesColoredDirt: List<IRecipe>
    var recipesWoodPanel: List<IRecipe>
    var recipesSlabs: List<IRecipe>
    var recipesStairsL: List<IRecipe>
    var recipesStairsR: List<IRecipe>
    var recipesSlabsFull: List<IRecipe>
    var recipesColoredSkyDirtRod: IRecipe

    init {

        for (i in 0..15)
            GameRegistry.addShapedRecipe(ItemStack(ShadowFoxBlocks.coloredDirtBlock, 8, i),
                    "DDD",
                    "DPD",
                    "DDD", 'P', ItemStack(BotaniaBlocks.petalBlock, 1, i), 'D', ItemStack(Blocks.dirt, 1))

        recipesColoredDirt = BotaniaAPI.getLatestAddedRecipes(16)

        for (i in 0..15)
            GameRegistry.addShapelessRecipe(ItemStack(ShadowFoxBlocks.coloredPlanks, 4, i), ItemStack(ShadowFoxBlocks.irisWood, 1, i))

        recipesWoodPanel = BotaniaAPI.getLatestAddedRecipes(16)
        
        for (i in 0..15)
            GameRegistry.addRecipe(ItemStack(ShadowFoxBlocks.coloredSlabs, 6, i),
                "QQQ",
                'Q', ItemStack(ShadowFoxBlocks.coloredPlanks, 1, i))

        recipesSlabs = BotaniaAPI.getLatestAddedRecipes(16)

        for (i in 0..15)
            GameRegistry.addRecipe(ItemStack(ShadowFoxBlocks.coloredStairs, 4, i),
                "  Q", " QQ", "QQQ",
                'Q', ItemStack(ShadowFoxBlocks.coloredPlanks, 1, i))
        
        recipesStairsR = BotaniaAPI.getLatestAddedRecipes(16)

        for (i in 0..15)
        GameRegistry.addRecipe(ItemStack(ShadowFoxBlocks.coloredStairs, 4, i),
                "Q  ", "QQ ", "QQQ",
                'Q', ItemStack(ShadowFoxBlocks.coloredPlanks, 1, i))
        
        recipesStairsL = BotaniaAPI.getLatestAddedRecipes(16)

        for (i in 0..15)
        GameRegistry.addRecipe(ItemStack(ShadowFoxBlocks.coloredPlanks, 1, i),
                "Q", "Q",
                'Q', ItemStack(ShadowFoxBlocks.coloredSlabs, 1, i))

        recipesSlabsFull = BotaniaAPI.getLatestAddedRecipes(16)
        
    
        addOreDictRecipe(ItemStack(ShadowFoxItems.colorfulSkyDirtRod, 1),
                " PN",
                " RP",
                "A  ",
                'P', ItemStack(ShadowFoxBlocks.coloredDirtBlock, 1),
                'R', ItemStack(BotaniaItems.skyDirtRod, 1),
                'A', LibOreDict.RUNE[3], 'N', LibOreDict.RUNE[14])
        
        recipesColoredSkyDirtRod = BotaniaAPI.getLatestAddedRecipe()

        
        for (i in 0..15)
            GameRegistry.addShapelessRecipe(ItemStack(ShadowFoxBlocks.coloredPlanks, 4, i),
                ItemStack(ShadowFoxBlocks.irisWood, 1, i))
        recipesWoodPanel = BotaniaAPI.getLatestAddedRecipes(16)

        BotaniaAPI.registerPureDaisyRecipe(Blocks.sapling, ShadowFoxBlocks.irisSapling, 0)
    
    }

    private fun addStairsAndSlabs(block: Block, meta: Int, stairs: Block, slab: Block) {
        GameRegistry.addRecipe(ItemStack(slab, 6, meta),
                "QQQ",
                'Q', ItemStack(block, 1, meta))
        GameRegistry.addRecipe(ItemStack(stairs, 4, meta),
                "  Q", " QQ", "QQQ",
                'Q', ItemStack(block, 1, meta))
        GameRegistry.addRecipe(ItemStack(stairs, 4, meta),
                "Q  ", "QQ ", "QQQ",
                'Q', ItemStack(block, 1, meta))
        GameRegistry.addRecipe(ItemStack(block, 1, meta),
                "Q", "Q",
                'Q', ItemStack(slab, meta))
    }

    private fun addOreDictRecipe(output: ItemStack, vararg recipe: Any) {
        CraftingManager.getInstance().recipeList.add(ShapedOreRecipe(output, *recipe))
    }

    private fun addShapelessOreDictRecipe(output: ItemStack, vararg recipe: Any) {
        CraftingManager.getInstance().recipeList.add(ShapelessOreRecipe(output, *recipe))
    }
}