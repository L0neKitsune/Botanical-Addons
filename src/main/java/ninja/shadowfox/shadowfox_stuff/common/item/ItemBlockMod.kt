package ninja.shadowfox.shadowfox_stuff.common.item

import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.item.ItemBlock
import net.minecraft.item.ItemStack

/**
 * 1.8 mod
 * created on 2/21/16
 */
open class ItemBlockMod(block: Block) : ItemBlock(block) {
    override fun setUnlocalizedName(p_77655_1_: String?): ItemBlock? {
        (this as Item).unlocalizedName = p_77655_1_
        return this
    }

    override fun getUnlocalizedNameInefficiently(par1ItemStack: ItemStack): String {
        return getUnlocalizedNameInefficiently_(par1ItemStack).replace("tile.", "tile.shadowfox_stuff:")
    }

    fun getUnlocalizedNameInefficiently_(stack: ItemStack): String {
        return super.getUnlocalizedNameInefficiently(stack)
    }
}
