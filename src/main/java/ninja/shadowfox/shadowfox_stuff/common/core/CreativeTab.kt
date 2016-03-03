package ninja.shadowfox.shadowfox_stuff.common.core

import net.minecraft.creativetab.CreativeTabs
import net.minecraft.init.Items
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import ninja.shadowfox.shadowfox_stuff.ShadowFox


object CreativeTab : CreativeTabs(ShadowFox.MODID) {

    override fun getIconItemStack(): ItemStack {
        return ItemStack(Items.arrow, 1)
    }

    override fun getTabIconItem(): Item {
        return iconItemStack.item
    }
}
