package ninja.shadowfox.shadowfox_botany.common.item

import cpw.mods.fml.common.IFuelHandler
import cpw.mods.fml.common.eventhandler.SubscribeEvent
import cpw.mods.fml.common.registry.GameRegistry
import cpw.mods.fml.relauncher.FMLLaunchHandler
import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.inventory.IInventory
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.IIcon
import net.minecraftforge.client.event.TextureStitchEvent
import net.minecraftforge.common.MinecraftForge
import ninja.shadowfox.shadowfox_botany.common.utils.helper.IconHelper
import ninja.shadowfox.shadowfox_botany.common.utils.helper.InterpolatedIconHelper
import vazkii.botania.api.recipe.IFlowerComponent

class ItemResource() : ItemMod("resource"), IFlowerComponent, IFuelHandler {

    init {
        setHasSubtypes(true)
        if (FMLLaunchHandler.side().isClient)
            MinecraftForge.EVENT_BUS.register(this)
        GameRegistry.registerFuelHandler(this)
    }

    val TYPES = 6

    var icons: Array<IIcon?> = arrayOfNulls(TYPES)

    private fun isInterpolated(meta: Int): Boolean = meta == 0 || meta == 4 || meta == 5
    private fun isFlowerComponent(meta: Int): Boolean = meta == 4

    @SideOnly(Side.CLIENT)
    override fun registerIcons(par1IconRegister: IIconRegister) {
        for (i in 0..(TYPES - 1)) {
            if (!isInterpolated(i)) {
                icons[i] = IconHelper.forItem(par1IconRegister, this, i)
            }
        }
    }

    override fun canFit(stack: ItemStack, inventory: IInventory): Boolean {
        return isFlowerComponent(stack.itemDamage)
    }

    override fun getParticleColor(stack: ItemStack): Int {
        return when (stack.itemDamage) {
            4 -> 0x6B2406
            else -> 0xFFFFFF
        }
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    fun loadTextures(event: TextureStitchEvent.Pre) {
        if (event.map.textureType == 1) {
            for (i in 0..(TYPES - 1)) {
                if (isInterpolated(i)) {
                    icons[i] = InterpolatedIconHelper.forItem(event.map, this, i)
                }
            }
        }
    }

    override fun getSubItems(item: Item, tab: CreativeTabs?, list: MutableList<Any?>) {
        for (i in 0..(TYPES - 1))
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
                1, 3 -> return 100 // Splinters smelt half an item.
                4 -> return 2400 // Flame-Laced Coal smelts 12 items.
            }
        }
        return 0
    }
}
