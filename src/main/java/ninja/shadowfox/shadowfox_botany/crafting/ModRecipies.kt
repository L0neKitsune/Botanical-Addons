package ninja.shadowfox.shadowfox_botany.crafting

import net.minecraft.init.Blocks
import net.minecraft.init.Items
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.CraftingManager
import net.minecraft.item.crafting.IRecipe
import net.minecraftforge.oredict.ShapedOreRecipe
import ninja.shadowfox.shadowfox_botany.common.blocks.ShadowFoxBlocks
import ninja.shadowfox.shadowfox_botany.common.item.ShadowFoxItems
import vazkii.botania.api.BotaniaAPI
import vazkii.botania.common.lib.LibOreDict
import vazkii.botania.common.block.ModBlocks as BotaniaBlocks
import vazkii.botania.common.item.ModItems as BotaniaItems

public object ModRecipes {
    var recipesColoredDirt: List<IRecipe>
    var recipesColoredSkyDirtRod: IRecipe

    init {

        for (i in 0..15)
            addOreDictRecipe(ItemStack(ShadowFoxBlocks.coloredDirtBlock, 8, i),
                    "DDD",
                    "DPD",
                    "DDD", 'P', ItemStack(BotaniaBlocks.petalBlock, 1, i), 'D', ItemStack(Blocks.dirt, 1))

        recipesColoredDirt = BotaniaAPI.getLatestAddedRecipes(16)

        addOreDictRecipe(ItemStack(ShadowFoxItems.colorfulSkyDirtRod, 1),
                " PN",
                " RP",
                "A  ",

                'P', ItemStack(ShadowFoxBlocks.coloredDirtBlock, 1),
                'R', ItemStack(BotaniaItems.skyDirtRod, 1),
                'A', LibOreDict.RUNE[3], 'N', LibOreDict.RUNE[14])

        recipesColoredSkyDirtRod = BotaniaAPI.getLatestAddedRecipe()


    }

    private fun addOreDictRecipe(output: ItemStack, vararg recipe: Any) {
        CraftingManager.getInstance().recipeList.add(ShapedOreRecipe(output, *recipe))
    }
}