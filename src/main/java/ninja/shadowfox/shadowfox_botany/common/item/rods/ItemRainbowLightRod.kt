package ninja.shadowfox.shadowfox_botany.common.item.rods

import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraft.item.ItemStack
import net.minecraft.util.IIcon
import net.minecraft.util.StatCollector
import net.minecraft.world.World
import net.minecraftforge.common.util.ForgeDirection
import ninja.shadowfox.shadowfox_botany.common.blocks.ShadowFoxBlocks
import ninja.shadowfox.shadowfox_botany.common.blocks.tile.TileRainbowManaFlame
import ninja.shadowfox.shadowfox_botany.common.item.ItemIridescent
import ninja.shadowfox.shadowfox_botany.common.item.ItemMod
import ninja.shadowfox.shadowfox_botany.common.utils.helper.IconHelper
import vazkii.botania.api.item.IPhantomInkable
import vazkii.botania.api.mana.IManaUsingItem
import vazkii.botania.api.mana.ManaItemHandler
import vazkii.botania.common.core.helper.ItemNBTHelper
import kotlin.properties.Delegates

class ItemRainbowLightRod : ItemMod("rainbowLightRod"), IManaUsingItem, IPhantomInkable {

    val COST = 100

    init {
        setMaxStackSize(1)
    }

    var overlayIcon: IIcon by Delegates.notNull()

    override fun requiresMultipleRenderPasses(): Boolean {
        return true
    }

    @SideOnly(Side.CLIENT)
    override fun registerIcons(par1IconRegister: IIconRegister) {
        this.itemIcon = IconHelper.forItem(par1IconRegister, this)
        this.overlayIcon = IconHelper.forItem(par1IconRegister, this, "Overlay")
    }

    @SideOnly(Side.CLIENT)
    override fun getIconFromDamageForRenderPass(meta: Int, pass: Int): IIcon {
        return if (pass === 1) this.overlayIcon else super.getIconFromDamageForRenderPass(meta, pass)
    }

    override fun getColorFromItemStack(par1ItemStack: ItemStack, pass: Int): Int {
        if (pass > 0)
            return 0xFFFFFF
        return ItemIridescent.rainbowColor()
    }

    override fun onItemUse(par1ItemStack: ItemStack, par2EntityPlayer: EntityPlayer, par3World: World,
                           x: Int, y: Int, z: Int, direction: Int, par8: Float, par9: Float, par10: Float): Boolean {
        if (par3World.getBlock(x, y, z) == ShadowFoxBlocks.rainbowFlame) {
            par3World.setBlock(x, y, z, Blocks.air)
            par3World.playSoundEffect(x.toDouble() + 0.5, y.toDouble() + 0.5, z.toDouble() + 0.5, "random.fizz", 0.3F, Math.random().toFloat() * 0.4F + 0.8F)
            return true
        }
        var toPlace = ItemStack(ShadowFoxBlocks.rainbowFlame)
        if (ManaItemHandler.requestManaExactForTool(par1ItemStack, par2EntityPlayer, COST, false)) {
            val dir = ForgeDirection.getOrientation(direction)
            if (par3World.getBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ).isAir(par3World, x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ)) {
                toPlace.tryPlaceItemIntoWorld(par2EntityPlayer, par3World, x, y, z, direction, par8, par9, par10)
                if (toPlace.stackSize == 0) {
                    par3World.playSoundEffect(x.toDouble() + 0.5, y.toDouble() + 0.5, z.toDouble() + 0.5, "fire.ignite", 0.3F, Math.random().toFloat() * 0.4F + 0.8F)
                    ManaItemHandler.requestManaExactForTool(par1ItemStack, par2EntityPlayer, COST, true)
                    val tile = par3World.getTileEntity(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ)
                    if (tile is TileRainbowManaFlame) {
                        tile.invisible = hasPhantomInk(par1ItemStack)
                    }
                }
                return true
            }
        }
        return false
    }

    fun addStringToTooltip(s: String, tooltip: MutableList<Any?>) {
        tooltip.add(s.replace("&".toRegex(), "\u00a7"))
    }

    override fun addInformation(par1ItemStack: ItemStack?, par2EntityPlayer: EntityPlayer?, par3List: MutableList<Any?>, fpar4: Boolean) {
        if (par1ItemStack == null) return
        if (hasPhantomInk(par1ItemStack))
            addStringToTooltip(StatCollector.translateToLocal("botaniamisc.hasPhantomInk"), par3List)
    }

    override fun usesMana(stack: ItemStack): Boolean {
        return true
    }

    override fun hasPhantomInk(stack: ItemStack): Boolean {
        return ItemNBTHelper.getBoolean(stack, "invisible", false)
    }

    override fun setPhantomInk(stack: ItemStack, ink: Boolean) {
        ItemNBTHelper.setBoolean(stack, "invisible", ink)
    }

    override fun isFull3D(): Boolean {
        return true
    }
}
