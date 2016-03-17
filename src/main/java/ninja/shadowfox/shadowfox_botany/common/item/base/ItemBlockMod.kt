package ninja.shadowfox.shadowfox_botany.common.item.base

import net.minecraft.block.Block
import net.minecraft.client.renderer.ItemMeshDefinition
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.EnumRarity
import net.minecraft.item.Item
import net.minecraft.item.ItemBlock
import net.minecraft.item.ItemStack
import net.minecraftforge.fml.common.registry.GameRegistry
import ninja.shadowfox.shadowfox_botany.common.blocks.base.IKitsuneBlock
import ninja.shadowfox.shadowfox_botany.lib.Constants

/**
 * 1.8 mod
 * created on 2/21/16
 */
class ItemBlockMod(block: Block) : ItemBlock(block), IVariantHolder {

    private val baseBlock: IKitsuneBlock

    init {
        baseBlock = block as IKitsuneBlock

        ItemMod.variantHolders.add(this)
        if(getVariants().size > 1)
            setHasSubtypes(true)
    }

    override fun getMetadata(damage: Int): Int {
        return damage
    }

    override fun setUnlocalizedName(par1Str: String): ItemBlock {
        GameRegistry.registerItem(this, par1Str)
        return super.setUnlocalizedName(par1Str)
    }

    override fun getUnlocalizedName(par1ItemStack: ItemStack): String {
        val dmg = par1ItemStack.itemDamage
        val variants = getVariants()

        val name: String
        if (dmg >= variants.size)
            name = baseBlock.bareName
        else
            name = variants[dmg]

        return "tile.${Constants.PREFIX_MOD}$name"
    }

    override fun getSubItems(itemIn: Item, tab: CreativeTabs?, subItems: MutableList<ItemStack>) {
        val variants = getVariants()
        variants.indices.forEach {
            subItems.add(ItemStack(itemIn, 1, it))
        }
    }

    override fun getVariants(): Array<String> = baseBlock.getVariants()

    override fun customMeshDefinition(): ItemMeshDefinition?
    {
        return baseBlock.customMeshDefinition()
    }

    override fun getRarity(stack: ItemStack): EnumRarity {
        return baseBlock.getBlockRarity(stack)
    }

}
