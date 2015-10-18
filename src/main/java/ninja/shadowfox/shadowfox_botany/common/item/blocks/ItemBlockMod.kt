package ninja.shadowfox.shadowfox_botany.common.item.blocks

import net.minecraft.block.Block
import net.minecraft.item.ItemBlock
import net.minecraft.item.ItemStack

public class ItemBlockMod(block: Block) : ItemBlock(block){

    override fun getUnlocalizedNameInefficiently(par1ItemStack: ItemStack): String {
        return getUnlocalizedNameInefficiently_(par1ItemStack).replaceAll("tile.", "tile.shadowfox_botany:")
    }

    fun getUnlocalizedNameInefficiently_(stack: ItemStack): String {
        return super.getUnlocalizedNameInefficiently(stack)
    }
}
