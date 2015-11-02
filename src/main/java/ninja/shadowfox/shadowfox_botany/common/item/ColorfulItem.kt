package ninja.shadowfox.shadowfox_botany.common.item

import net.minecraft.entity.passive.EntitySheep
import java.awt.Color;
import net.minecraft.item.ItemStack
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

    override fun getUnlocalizedName(par1ItemStack: ItemStack?): String {
        return if(par1ItemStack != null) this.getUnlocalizedNameLazy(par1ItemStack) + par1ItemStack.itemDamage else ""
    }

    internal fun getUnlocalizedNameLazy(par1ItemStack: ItemStack): String {
        return super.getUnlocalizedName(par1ItemStack)
    }
}