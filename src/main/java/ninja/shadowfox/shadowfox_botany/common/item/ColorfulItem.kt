package ninja.shadowfox.shadowfox_botany.common.item

import cpw.mods.fml.common.registry.GameRegistry
import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.passive.EntitySheep
import net.minecraft.item.Item
import java.awt.Color;
import net.minecraft.item.ItemStack
import ninja.shadowfox.shadowfox_botany.common.core.ShadowFoxCreativeTab
import ninja.shadowfox.shadowfox_botany.common.utils.helper.IconHelper

/**
 * Created by l0nekitsune on 10/19/15.
 */
open class ColorfulItem(name: String) : Item() {
    init {
        setCreativeTab(ShadowFoxCreativeTab);
        setHasSubtypes(true);
        setUnlocalizedName(name);
    }

    override fun getColorFromItemStack(par1ItemStack : ItemStack, par2 : Int) : Int {
        if(par1ItemStack.itemDamage >= EntitySheep.fleeceColorTable.size())
            return 0xFFFFFF;

        var color = EntitySheep.fleeceColorTable[par1ItemStack.itemDamage];
        return Color(color[0], color[1], color[2]).rgb;
    }


//    override fun getSubItems(item : Item?, par2CreativeTabs: CreativeTabs?, par3List : MutableList<Any?>?) {
//        if (par3List != null && item != null)
//            for (i in 0..15) {
//                par3List.add(ItemStack(item, 1, i));
//            }
//    }

    override fun getUnlocalizedName(par1ItemStack: ItemStack) : String {
        return getUnlocalizedNameLazy(par1ItemStack) + par1ItemStack.itemDamage;
    }

    fun getUnlocalizedNameLazy(par1ItemStack : ItemStack) : String {
        return super.getUnlocalizedName(par1ItemStack);
    }

    override fun setUnlocalizedName(par1Str : String) : Item {
        GameRegistry.registerItem(this, par1Str);
        return super.setUnlocalizedName(par1Str);
    }

    override fun getUnlocalizedNameInefficiently(par1ItemStack : ItemStack) : String {
        return super.getUnlocalizedNameInefficiently(par1ItemStack).replaceAll("item\\.", "item.shadowfox_botany:");
    }

    @SideOnly(Side.CLIENT)
    override fun registerIcons(par1IconRegister: IIconRegister) {
        itemIcon = IconHelper.forItem(par1IconRegister, this);
    }
}