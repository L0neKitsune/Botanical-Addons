package ninja.shadowfox.shadowfox_botany.common.item

import cpw.mods.fml.common.IFuelHandler
import cpw.mods.fml.common.eventhandler.SubscribeEvent
import cpw.mods.fml.common.registry.GameRegistry
import cpw.mods.fml.relauncher.FMLLaunchHandler
import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.IIcon
import net.minecraftforge.client.event.TextureStitchEvent
import net.minecraftforge.common.MinecraftForge
import ninja.shadowfox.shadowfox_botany.common.utils.helper.IconHelper
import vazkii.botania.client.render.block.InterpolatedIcon

class ItemResource(): ItemMod("resource"), IFuelHandler {

    init {
        setHasSubtypes(true)
        if (FMLLaunchHandler.side().isClient())
            MinecraftForge.EVENT_BUS.register(this)
        GameRegistry.registerFuelHandler(this)
    }

    val TYPES = 5
    val INTERP = 0x11

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
    override fun getBurnTime(fuel: ItemStack): Int {
        if (fuel.item == ShadowFoxItems.resource) {
            when (fuel.itemDamage) {
                1,3 -> return 100 // Splinters smelt half an item.
                4 -> return 2400 // Flame-Laced Coal smelts 12 items
            }
        }
        return 0
    }
}
