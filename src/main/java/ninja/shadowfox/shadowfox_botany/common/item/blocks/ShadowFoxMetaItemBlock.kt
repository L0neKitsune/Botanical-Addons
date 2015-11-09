package ninja.shadowfox.shadowfox_botany.common.item.blocks

import net.minecraft.block.Block
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemBlockWithMetadata
import net.minecraft.item.ItemStack
import net.minecraft.util.StatCollector


open class ShadowFoxMetaItemBlock(par2Block: Block) : ItemBlockWithMetadata(par2Block, par2Block) {

    override fun getUnlocalizedNameInefficiently(par1ItemStack: ItemStack): String {
        return (super.getUnlocalizedNameInefficiently(par1ItemStack)).replace("tile.", "tile.shadowfox_botany:").replace("\\d+$".toRegex(), "")
    }

    fun addStringToTooltip(s : String, tooltip : MutableList<Any?>?) {
        tooltip!!.add(s.replace("&".toRegex(), "\u00a7"));
    }

    override fun addInformation(par1ItemStack: ItemStack, par2EntityPlayer: EntityPlayer, par3List: MutableList<Any?>?, par4: Boolean) {
        addStringToTooltip("&7"+StatCollector.translateToLocal("misc.shadowfox_botany.color." + par1ItemStack.itemDamage)+"&r", par3List);
    } 

    override fun getUnlocalizedName(par1ItemStack: ItemStack?): String {
        return super.getUnlocalizedName(par1ItemStack)
    }
}