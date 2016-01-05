package ninja.shadowfox.shadowfox_botany.common.item.blocks

import net.minecraft.block.Block
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemBlockWithMetadata
import net.minecraft.item.ItemStack
import net.minecraft.util.StatCollector
import ninja.shadowfox.shadowfox_botany.common.blocks.base.ShadowFoxLeaves
import kotlin.text.replace
import kotlin.text.toRegex

open class ItemSubtypedBlockMod(par2Block: Block) : ItemBlockWithMetadata(par2Block, par2Block) {

    override fun getMetadata(meta: Int): Int {
        if (field_150939_a is ShadowFoxLeaves) return meta or field_150939_a.decayBit()
        return meta
    }

    override fun getUnlocalizedNameInefficiently(par1ItemStack: ItemStack): String {
        return (super.getUnlocalizedNameInefficiently(par1ItemStack)).replace("tile.", "tile.shadowfox_botany:").replace("\\d+$".toRegex(), "")
    }

    fun addStringToTooltip(s: String, tooltip: MutableList<Any?>?) {
        tooltip!!.add(s.replace("&".toRegex(), "\u00a7"))
    }

    override fun addInformation(par1ItemStack: ItemStack?, par2EntityPlayer: EntityPlayer?, par3List: MutableList<Any?>?, par4: Boolean) {
        if (par1ItemStack == null) return
        addStringToTooltip("&7" + StatCollector.translateToLocal("misc.shadowfox_botany.color." + par1ItemStack.itemDamage) + "&r", par3List)
    }

    override fun getUnlocalizedName(par1ItemStack: ItemStack?): String {
        return super.getUnlocalizedName(par1ItemStack)
    }
}

class ItemUniqueSubtypedBlockMod(par2Block: Block) : ItemBlockWithMetadata(par2Block, par2Block) {
    override fun getMetadata(meta: Int): Int {
        if (field_150939_a is ShadowFoxLeaves) return meta or field_150939_a.decayBit()
        return meta
    }

    override fun getUnlocalizedNameInefficiently(par1ItemStack: ItemStack): String {
        return (super.getUnlocalizedNameInefficiently(par1ItemStack)).replace("tile.", "tile.shadowfox_botany:") + par1ItemStack.itemDamage
    }
}
