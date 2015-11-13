package ninja.shadowfox.shadowfox_botany.common.item.blocks

import net.minecraft.block.Block
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemBlockWithMetadata
import net.minecraft.item.ItemStack
import net.minecraft.util.StatCollector

open class ShadowFoxStairsItemBlock(par2Block: Block) : ItemBlockWithMetadata(par2Block, par2Block) {
    fun addStringToTooltip(s : String, tooltip : MutableList<Any?>?) {
        tooltip!!.add(s.replace("&".toRegex(), "\u00a7"))
    }

    override fun addInformation(par1ItemStack: ItemStack?, par2EntityPlayer: EntityPlayer?, par3List: MutableList<Any?>?, par4: Boolean) {
        if(par1ItemStack == null) return
        addStringToTooltip("&7"+StatCollector.translateToLocal("misc.shadowfox_botany.color." + "\\d+$".toRegex().find(field_150939_a.unlocalizedName)?.value) + "&r", par3List)

    } 

    override fun getUnlocalizedName(par1ItemStack: ItemStack?): String {
        return field_150939_a.unlocalizedName.replace("tile.".toRegex(), "tile.shadowfox_botany:").replace("\\d+$".toRegex(), "")
    }
}
