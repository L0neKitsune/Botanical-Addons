package ninja.shadowfox.botania_addon.common.core

import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import ninja.shadowfox.botania_addon.common.blocks.ShadowFoxBlocks

object ShadowFoxCreativeTab : CreativeTabs("Botanical Add-ons") {

    override fun getIconItemStack(): ItemStack {
        return ItemStack(ShadowFoxBlocks.coloredDirtBlock)
    }

    override fun getTabIconItem(): Item {
        return iconItemStack.item
    }
}
