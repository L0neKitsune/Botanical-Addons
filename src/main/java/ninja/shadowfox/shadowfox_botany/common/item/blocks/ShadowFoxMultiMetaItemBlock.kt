package ninja.shadowfox.shadowfox_botany.common.item.blocks

import net.minecraft.block.Block
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemBlockWithMetadata
import net.minecraft.item.ItemStack
import net.minecraft.util.StatCollector


open class ShadowFoxLogItemBlock0(par2Block: Block) : ItemBlockWithMetadata(par2Block, par2Block) {

    open val colorSet = 0

    override fun getUnlocalizedNameInefficiently(par1ItemStack: ItemStack): String {
        return (super.getUnlocalizedNameInefficiently(par1ItemStack)).replace("tile.", "tile.shadowfox_botany:").replace("\\d+$".toRegex(), "")
    }

    fun addStringToTooltip(s : String, tooltip : MutableList<Any?>?) {
        tooltip!!.add(s.replace("&".toRegex(), "\u00a7"))
    }

    override fun addInformation(par1ItemStack: ItemStack?, par2EntityPlayer: EntityPlayer?, par3List: MutableList<Any?>?, par4: Boolean) {
        if(par1ItemStack == null) return
        addStringToTooltip("&7"+StatCollector.translateToLocal("misc.shadowfox_botany.color." + (par1ItemStack.itemDamage+(4*colorSet)))+"&r", par3List)
    } 

    override fun getUnlocalizedName(par1ItemStack: ItemStack?): String {
        return super.getUnlocalizedName(par1ItemStack)
    }
}

open class ShadowFoxLogItemBlock1(par2Block: Block) : ShadowFoxLogItemBlock0(par2Block) {
    override val colorSet = 1
}

open class ShadowFoxLogItemBlock2(par2Block: Block) : ShadowFoxLogItemBlock0(par2Block) {
    override val colorSet = 2
}

open class ShadowFoxLogItemBlock3(par2Block: Block) : ShadowFoxLogItemBlock0(par2Block) {
    override val colorSet = 3
}
