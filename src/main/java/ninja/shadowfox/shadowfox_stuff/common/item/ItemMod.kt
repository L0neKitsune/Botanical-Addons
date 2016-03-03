package ninja.shadowfox.shadowfox_stuff.common.item

import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraftforge.fml.common.registry.GameRegistry
import ninja.shadowfox.shadowfox_stuff.common.core.CreativeTab

/**
 * 1.8 mod
 * created on 2/20/16
 */
open class ItemMod(name: String) : Item() {
    init {
        creativeTab = CreativeTab
        unlocalizedName = name

    }

    override fun setUnlocalizedName(par1Str: String): Item {
        GameRegistry.registerItem(this, par1Str)
        return super.setUnlocalizedName(par1Str)
    }

    override fun getItemStackDisplayName(stack: ItemStack): String {
        return super.getItemStackDisplayName(stack).replace("&".toRegex(), "\u00a7")
    }

    override fun getUnlocalizedNameInefficiently(par1ItemStack: ItemStack): String {
        return super.getUnlocalizedNameInefficiently(par1ItemStack).replace("item.", "item.shadowfox_stuff:");
    }
}
