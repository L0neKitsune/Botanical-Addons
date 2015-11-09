package ninja.shadowfox.shadowfox_botany.common.item

import java.awt.Color
import net.minecraft.entity.passive.EntitySheep
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.StatCollector
import ninja.shadowfox.shadowfox_botany.common.core.ShadowFoxCreativeTab

open class ColorfulItem(name: String) : StandardItem(name) {

    init {
        setHasSubtypes(true)
    }

    override fun getColorFromItemStack(par1ItemStack : ItemStack, par2 : Int) : Int {
        if(par1ItemStack.itemDamage >= EntitySheep.fleeceColorTable.size)
            return 0xFFFFFF;

        var color = EntitySheep.fleeceColorTable[par1ItemStack.itemDamage];
        return Color(color[0], color[1], color[2]).rgb;
    }

    fun addStringToTooltip(s : String, tooltip : MutableList<Any?>?) {
        tooltip!!.add(s.replace("&".toRegex(), "\u00a7"));
    }

    override fun addInformation(par1ItemStack: ItemStack, par2EntityPlayer: EntityPlayer, par3List: MutableList<Any?>?, par4: Boolean) {
        addStringToTooltip("&7"+StatCollector.translateToLocal("misc.shadowfox_botany.color." + par1ItemStack.itemDamage)+"&r", par3List);
    } 

    override fun getUnlocalizedName(par1ItemStack: ItemStack?): String {
        return if(par1ItemStack != null) this.getUnlocalizedNameLazy(par1ItemStack) else ""
    }

    internal fun getUnlocalizedNameLazy(par1ItemStack: ItemStack): String {
        return super.getUnlocalizedName(par1ItemStack)
    }
}