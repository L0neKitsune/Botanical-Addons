package ninja.shadowfox.shadowfox_botany.common.item.baubles

import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import cpw.mods.fml.common.registry.GameRegistry

import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.ItemRenderer
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.client.renderer.texture.TextureMap
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.IIcon
import net.minecraft.util.StatCollector
import net.minecraftforge.client.event.RenderPlayerEvent
import net.minecraftforge.oredict.RecipeSorter
import net.minecraftforge.oredict.RecipeSorter.Category

import org.lwjgl.opengl.GL11

import ninja.shadowfox.shadowfox_botany.common.item.ItemIridescent
import ninja.shadowfox.shadowfox_botany.common.item.IPriestColorOverride
import ninja.shadowfox.shadowfox_botany.common.utils.helper.IconHelper
import ninja.shadowfox.shadowfox_botany.common.core.ShadowFoxCreativeTab

import vazkii.botania.api.item.ICosmeticBauble
import vazkii.botania.api.item.IBaubleRender
import vazkii.botania.common.core.helper.ItemNBTHelper
import vazkii.botania.common.item.equipment.bauble.ItemBauble
import vazkii.botania.common.lib.LibItemNames
import baubles.api.BaubleType

import kotlin.properties.Delegates

class ItemColorOverride(): ItemBauble("colorOverride"), ICosmeticBauble, IPriestColorOverride {

    var overlayIcon: IIcon by Delegates.notNull()

    init {
        setHasSubtypes(true)
        setCreativeTab(ShadowFoxCreativeTab)
    }

    override fun registerIcons(par1IconRegister: IIconRegister) {
        this.itemIcon = IconHelper.forItem(par1IconRegister, this)
        this.overlayIcon = IconHelper.forItem(par1IconRegister, this, "Overlay")
    }

    override fun colorOverride(stack: ItemStack?): Int? {
        if (ItemNBTHelper.detectNBT(stack)) {
            var comp = ItemNBTHelper.getCompound(stack, "display", false)
            if (comp.hasKey("color", 3))
                return comp.getInteger("color")
        }
        return null
    }

    @SideOnly(Side.CLIENT)
    override fun requiresMultipleRenderPasses(): Boolean = true

    @SideOnly(Side.CLIENT)
    override fun getColorFromItemStack(stack: ItemStack, pass: Int): Int {
        if (pass > 0)
            return 0xFFFFFF
        return this.getColor(stack)
    }

    @SideOnly(Side.CLIENT)
    override fun getIconFromDamageForRenderPass(meta: Int, pass: Int): IIcon {
        return if (pass > 0) this.overlayIcon else super.getIconFromDamageForRenderPass(meta, pass)
    }

    fun setColor(stack: ItemStack, color: Int) {
        if (!ItemNBTHelper.verifyExistance(stack, "display"))
            ItemNBTHelper.setCompound(stack, "display", NBTTagCompound())
        ItemNBTHelper.getCompound(stack, "display", true).setInteger("color", color)
    }

    fun removeColor(stack: ItemStack) {
        if (ItemNBTHelper.detectNBT(stack)) {
            var comp = ItemNBTHelper.getCompound(stack, "display", false)
            if (comp.hasKey("color", 3))
                comp.removeTag("color")
        }
    }

    fun getColor(stack: ItemStack): Int {
        if (ItemNBTHelper.detectNBT(stack)) {
            var comp = ItemNBTHelper.getCompound(stack, "display", false)
            if (comp.hasKey("color", 3))
                return comp.getInteger("color")
        }
        return 0xB0B0B0
    }

    fun hasColor(stack: ItemStack): Boolean {
        if (ItemNBTHelper.detectNBT(stack)) {
            var comp = ItemNBTHelper.getCompound(stack, "display", false)
            if (comp.hasKey("color", 3))
                return true
        }
        return false
    }

    override fun getUnlocalizedNameInefficiently(par1ItemStack: ItemStack): String {
        return super.getUnlocalizedNameInefficiently(par1ItemStack).replace("item\\.botania:".toRegex(), "item.shadowfox_botany:")
    }

    override fun addInformation(par1ItemStack: ItemStack?, par2EntityPlayer: EntityPlayer?, par3List: MutableList<Any?>, par4: Boolean) {
        if (par1ItemStack == null) return
        if (this.hasColor(par1ItemStack))
            addStringToTooltip("&7"+StatCollector.translateToLocal("item.dyed")+"&r", par3List)
        super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4)
    }

    override fun addHiddenTooltip(par1ItemStack: ItemStack, par2EntityPlayer: EntityPlayer, par3List: MutableList<Any?>, par4: Boolean) {
        addStringToTooltip(StatCollector.translateToLocal("botaniamisc.cosmeticBauble"), par3List)
        super.addHiddenTooltip(par1ItemStack, par2EntityPlayer, par3List, par4)
    }

    fun addStringToTooltip(s : String, tooltip : MutableList<Any?>?) {
        tooltip!!.add(s.replace("&".toRegex(), "\u00a7"))
    }

    override fun getBaubleType(arg0: ItemStack): BaubleType {
        return BaubleType.RING
    }

    override fun onPlayerBaubleRender(stack: ItemStack, event: RenderPlayerEvent, type: IBaubleRender.RenderType) {}
}
