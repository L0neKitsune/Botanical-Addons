package ninja.shadowfox.shadowfox_botany.common.core

import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import ninja.shadowfox.shadowfox_botany.common.item.ShadowFoxItems

object ShadowFoxCreativeTab : CreativeTabs("shadowfox_botany") {

    override fun getIconItemStack(): ItemStack {
        return ItemStack(ShadowFoxItems.colorfulSkyDirtRod, 1, 10)
    }

    override fun getTabIconItem(): Item {
        return iconItemStack.item
    }
}
