package ninja.shadowfox.shadowfox_botany.common.item.blocks

import net.minecraft.block.Block
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemBlock
import net.minecraft.item.ItemStack
import net.minecraft.util.StatCollector

public class ShadowFoxItemBlockMod(block: Block) : ItemBlock(block){

    override fun getUnlocalizedNameInefficiently(par1ItemStack: ItemStack): String {
        return getUnlocalizedNameInefficiently_(par1ItemStack).replace("tile.", "tile.shadowfox_botany:")
    }

    fun getUnlocalizedNameInefficiently_(stack: ItemStack): String {
        return super.getUnlocalizedNameInefficiently(stack)
    }
}
