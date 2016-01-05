package ninja.shadowfox.shadowfox_botany.common.item

import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.StatCollector

class ItemWiltedLotus : ItemMod("wiltedLotus") {

    init {
        setHasSubtypes(true)
    }

    override fun getSubItems(item: Item, tab: CreativeTabs?, list: MutableList<Any?>) {
        for (i in 0..1)
            list.add(ItemStack(item, 1, i))
    }

    override fun hasEffect(par1ItemStack: ItemStack, pass: Int): Boolean {
        return par1ItemStack.itemDamage > 0
    }

    override fun addInformation(stack: ItemStack?, player: EntityPlayer?, list: MutableList<Any?>, adv: Boolean) {
        list.add(StatCollector.translateToLocal("misc.shadowfox_botany:lotusDesc"))
    }

    override fun getUnlocalizedNameInefficiently(par1ItemStack: ItemStack): String {
        return super.getUnlocalizedNameInefficiently(par1ItemStack) + par1ItemStack.itemDamage
    }
}
