package ninja.shadowfox.shadowfox_stuff.common.crafting

import net.minecraft.block.Block
import net.minecraft.init.Items
import net.minecraft.inventory.InventoryCrafting
import net.minecraft.item.Item
import net.minecraft.item.ItemPotion
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.CraftingManager
import net.minecraft.item.crafting.IRecipe
import net.minecraft.world.World
import net.minecraftforge.fml.common.registry.GameRegistry
import net.minecraftforge.oredict.OreDictionary
import net.minecraftforge.oredict.ShapelessOreRecipe
import ninja.shadowfox.shadowfox_stuff.common.item.IPotionContainer
import ninja.shadowfox.shadowfox_stuff.common.item.ItemPotionPaste
import ninja.shadowfox.shadowfox_stuff.common.item.ModItems
import java.util.*

/**
 * 1.8 mod
 * created on 2/21/16
 */
object ModCrafting {
//    val potionPaste: IRecipe

    init {
//        GameRegistry.addRecipe(PotionBaseRecipe(ItemStack(ModItems.potionPaste, 1),
//                ItemStack(Items.potionitem, 1, OreDictionary.WILDCARD_VALUE), "slimeball"))
//        potionPaste = getLatestAddedRecipe()
    }
}

class PotionBaseRecipe : ShapelessOreRecipe {

    constructor(result: Block, vararg recipe: Any): this(ItemStack(result), *recipe)
    constructor(result: Item, vararg recipe: Any): this(ItemStack(result), *recipe)
    constructor(result: ItemStack, vararg recipe: Any): super(result, *recipe) {
        if (result.item !is IPotionContainer)
            throw RuntimeException("PotionBaseRecipe output must extend IPotionContainer")
    }

    override fun matches(var1: InventoryCrafting?, world: World?): Boolean {
        return super.matches(var1, world)
    }

    override fun getCraftingResult(inv: InventoryCrafting?): ItemStack? {
        if (inv == null) return null

        for (j in 0..inv.sizeInventory - 1) {
            val itemstack1 = inv.getStackInSlot(j)

            if (itemstack1 != null) {
                if (itemstack1.item === Items.potionitem) {
                    return super.getCraftingResult(inv).apply {
                        for (effect in (itemstack1.item as ItemPotion).getEffects(itemstack1))
                            (item as IPotionContainer).addEffect(this, effect)
                    }
                }
            }
        }

        return null
    }
}

fun getLatestAddedRecipe(): IRecipe {
    val list = CraftingManager.getInstance().recipeList
    return list[list.size - 1]
}

fun getLatestAddedRecipes(x: Int): List<IRecipe> {
    val list = CraftingManager.getInstance().recipeList
    val newList = ArrayList<IRecipe>()

    for (i in x - 1 downTo 0) {
        newList.add(list[list.size - 1 - i])
    }

    return newList
}
