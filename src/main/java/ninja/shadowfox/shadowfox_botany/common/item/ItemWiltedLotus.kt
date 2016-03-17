package ninja.shadowfox.shadowfox_botany.common.item

import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.StatCollector
import ninja.shadowfox.shadowfox_botany.common.item.base.ItemMod

class ItemWiltedLotus : ItemMod("wiltedLotus") {

    init {
        setHasSubtypes(true)
    }

    override fun getSubItems(itemIn: Item, tab: CreativeTabs, subItems: MutableList<ItemStack>) {
        for (i in 0..1)
            subItems.add(ItemStack(itemIn, 1, i))
    }

    override fun hasEffect(stack: ItemStack): Boolean {
        return stack.itemDamage > 0
    }

    override fun addInformation(stack: ItemStack?, playerIn: EntityPlayer?, tooltip: MutableList<String>?, advanced: Boolean) {
        tooltip?.add(StatCollector.translateToLocal("misc.shadowfox_botany:lotusDesc"))
    }

    override fun getUnlocalizedNameInefficiently(par1ItemStack: ItemStack): String {
        return super.getUnlocalizedNameInefficiently(par1ItemStack) + par1ItemStack.itemDamage
    }
}
