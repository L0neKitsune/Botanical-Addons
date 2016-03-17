package ninja.shadowfox.shadowfox_botany.common.item.base

import net.minecraft.client.renderer.ItemMeshDefinition
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraftforge.fml.common.registry.GameRegistry
import ninja.shadowfox.shadowfox_botany.common.core.ShadowFoxCreativeTab
import vazkii.botania.client.lib.LibResources
import java.util.*

/**
 * 1.8 mod
 * created on 2/20/16
 */
open class ItemMod(val bareName: String, vararg srt_variants: String) : Item(), IVariantHolder {
    var variant: Array<String> = arrayOf()

    init {
        creativeTab = ShadowFoxCreativeTab
        if(srt_variants.size > 1)
            setHasSubtypes(true)

        if(srt_variants.size == 0)
            variant = arrayOf(bareName)

        this.variant = arrayOf(*srt_variants)
        variantHolders.add(this)
    }

    override fun setUnlocalizedName(name: String): Item {
        super.setUnlocalizedName(name)
        registryName = name
        GameRegistry.registerItem(this, name)

        return this
    }

    override fun getUnlocalizedName(par1ItemStack: ItemStack): String {
        val dmg = par1ItemStack.itemDamage
        val variants = variant

        val name: String
        if (dmg >= variants.size)
            name = bareName
        else
            name = variants[dmg]

        return "item." + LibResources.PREFIX_MOD + name
    }

    override fun getSubItems(itemIn: Item, tab: CreativeTabs, subItems: MutableList<ItemStack>) {
        for (i in 0..variant.size - 1)
            subItems.add(ItemStack(itemIn, 1, i))
    }

    override fun customMeshDefinition(): ItemMeshDefinition?{
        return null
    }

    override fun getVariants(): Array<String> = variant

    companion object {

        var variantHolders: MutableList<IVariantHolder> = ArrayList()

//        fun tooltipIfShift(tooltip: MutableList<String>, r: Runnable) {
//            TooltipHelper.tooltipIfShift(tooltip, r)
//        }
//
//        fun addToTooltip(tooltip: MutableList<String>, s: String, vararg format: Any) {
//            TooltipHelper.addToTooltip(tooltip, s, format)
//        }
//
//        fun local(s: String): String {
//            return TooltipHelper.local(s)
//        }
    }

}

