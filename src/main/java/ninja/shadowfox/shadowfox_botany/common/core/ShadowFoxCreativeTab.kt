package ninja.shadowfox.shadowfox_botany.common.core

import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import ninja.shadowfox.shadowfox_botany.common.item.ShadowFoxItems
import vazkii.botania.common.item.ModItems

object ShadowFoxCreativeTab : CreativeTabs("shadowfox_botany") {

    override fun getIconItemStack(): ItemStack {
        //TODO: change this back to something that we made.
        return ItemStack(ModItems.manaBottle, 1, 16)
    }

    override fun getTabIconItem(): Item {
        return iconItemStack.item
    }
}
