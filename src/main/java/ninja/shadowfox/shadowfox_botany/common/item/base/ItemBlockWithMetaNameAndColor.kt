package ninja.shadowfox.shadowfox_botany.common.item.base

import net.minecraft.block.Block
import net.minecraft.item.ItemStack
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

/**
 * BotanicalAddons
 * created on 3/16/16
 */
open class ItemBlockWithMetaNameAndColor(block: Block) : ItemBlockWithMetadataAndName(block) {

    @SideOnly(Side.CLIENT)
    override fun getColorFromItemStack(stack: ItemStack?, renderPass: Int): Int {
        return this.block.getRenderColor(this.block.getStateFromMeta(stack!!.metadata))
    }
}
