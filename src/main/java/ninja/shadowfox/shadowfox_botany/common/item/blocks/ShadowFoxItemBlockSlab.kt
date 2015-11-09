package ninja.shadowfox.shadowfox_botany.common.item.blocks

import net.minecraft.block.Block
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemSlab
import net.minecraft.item.ItemStack
import net.minecraft.util.StatCollector
import ninja.shadowfox.shadowfox_botany.common.blocks.ShadowFoxSlabs

/**
 * Created by l0nekitsune on 10/24/15.
 */

class ShadowFoxItemBlockSlab(par1: Block) : ItemSlab(par1, (par1 as ShadowFoxSlabs).singleBlock, par1.fullBlock, false) {

    override fun getUnlocalizedName(par1ItemStack: ItemStack): String {
        return field_150939_a.unlocalizedName.replace("tile.".toRegex(), "tile.shadowfox_botany:").replace("\\d+$".toRegex(), "")
    }

    fun addStringToTooltip(s : String, tooltip : MutableList<Any?>?) {
        tooltip!!.add(s.replace("&".toRegex(), "\u00a7"));
    }

    override fun addInformation(par1ItemStack: ItemStack, par2EntityPlayer: EntityPlayer, par3List: MutableList<Any?>?, par4: Boolean) {
        if(par1ItemStack != null)
            addStringToTooltip("&7"+StatCollector.translateToLocal("misc.shadowfox_botany.color." + par1ItemStack.itemDamage)+"&r", par3List);
    } 

}