package ninja.shadowfox.shadowfox_botany.common.item.blocks

import net.minecraft.block.Block
import net.minecraft.item.ItemBlockWithMetadata
import net.minecraft.item.ItemStack


open class ShadowFoxMetaItemBlock(par2Block: Block) : ItemBlockWithMetadata(par2Block, par2Block) {

    override fun getUnlocalizedNameInefficiently(par1ItemStack: ItemStack): String {
        return (super.getUnlocalizedNameInefficiently(par1ItemStack)).replace("tile.", "tile.shadowfox_botany:")
    }

    override fun getUnlocalizedName(par1ItemStack: ItemStack?): String {
        return super.getUnlocalizedName(par1ItemStack) + par1ItemStack!!.itemDamage
    }
}