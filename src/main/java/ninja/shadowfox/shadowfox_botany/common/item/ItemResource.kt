package ninja.shadowfox.shadowfox_botany.common.item

import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.client.renderer.texture.TextureMap
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.IIcon

import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import cpw.mods.fml.relauncher.FMLLaunchHandler
import cpw.mods.fml.common.eventhandler.SubscribeEvent

import net.minecraftforge.client.event.TextureStitchEvent
import net.minecraftforge.common.MinecraftForge

import ninja.shadowfox.shadowfox_botany.common.utils.helper.IconHelper

import vazkii.botania.client.render.block.InterpolatedIcon

class ItemResource(): ItemMod("resource") {

    init {
        setHasSubtypes(true)
        if (FMLLaunchHandler.side().isClient())
            MinecraftForge.EVENT_BUS.register(this)
    }

    val TYPES = 1
    val INTERP = 0x1

    var icons: Array<IIcon?> = arrayOfNulls(TYPES)

    @SideOnly(Side.CLIENT)
    override fun registerIcons(par1IconRegister: IIconRegister) {
        for(i in 0..(TYPES - 1)) {
            if ((1 shr i) and INTERP == 0) {
                icons[i] = IconHelper.forItem(par1IconRegister, this, i)
            }
        }
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    fun loadTextures(event: TextureStitchEvent.Pre) {
        if(event.map.textureType == 1) {
            for(i in 0..(TYPES - 1)) {
                if ((1 shr i) and INTERP != 0) {
                    var localIcon = InterpolatedIcon("shadowfox_botany:resource"+i)
                    if(event.map.setTextureEntry("shadowfox_botany:resource"+i, localIcon)) {
                        icons[i] = localIcon
                    }
                }
            }
        }
    }

    override fun getSubItems(item: Item, tab: CreativeTabs?, list: MutableList<Any?>) {
        for(i in 0..(TYPES - 1))
            list.add(ItemStack(item, 1, i))
    }

    override fun getUnlocalizedName(par1ItemStack: ItemStack): String {
      return super.getUnlocalizedName(par1ItemStack) + par1ItemStack.itemDamage
    }

    override fun getIconFromDamage(dmg: Int): IIcon? {
        return icons[Math.min(TYPES - 1, dmg)]
    }
}
