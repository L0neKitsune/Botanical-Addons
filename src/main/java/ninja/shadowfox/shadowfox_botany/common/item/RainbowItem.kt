package ninja.shadowfox.shadowfox_botany.common.item

import cpw.mods.fml.common.registry.GameRegistry
import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import java.awt.Color
import net.minecraft.entity.passive.EntitySheep
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.item.Item
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.util.IIcon
import net.minecraft.util.StatCollector
import ninja.shadowfox.shadowfox_botany.common.core.ShadowFoxCreativeTab
import ninja.shadowfox.shadowfox_botany.common.utils.helper.IconHelper
import vazkii.botania.common.Botania
import kotlin.properties.Delegates

open class RainbowItem(name: String) : Item() {

    companion object {
        fun getColor(): Int {
            return Color.HSBtoRGB(Botania.proxy.getWorldElapsedTicks() * 2 % 360 / 360F, 0.8F, 1F)
        }
    }

    init {
        setHasSubtypes(true)
        setCreativeTab(ShadowFoxCreativeTab)
        setUnlocalizedName(name)
    }

    @SideOnly(Side.CLIENT)
    var overlayIcon: IIcon by Delegates.notNull()

    override fun requiresMultipleRenderPasses() : Boolean {
        return true
    }

    override fun setUnlocalizedName(par1Str: String): Item {
        GameRegistry.registerItem(this, par1Str)
        return super.setUnlocalizedName(par1Str)
    }

    override fun getUnlocalizedNameInefficiently(par1ItemStack: ItemStack): String {
        return super.getUnlocalizedNameInefficiently(par1ItemStack).replace("item\\.".toRegex(), "item.shadowfox_botany:")
    }

    @SideOnly(Side.CLIENT)
    override fun getIconFromDamageForRenderPass(meta: Int, pass: Int) : IIcon {
        return if (pass === 1) this.overlayIcon else super.getIconFromDamageForRenderPass(meta, pass)
    }

    @SideOnly(Side.CLIENT)
    override fun registerIcons(iconRegister : IIconRegister) {
        this.itemIcon = IconHelper.forItem(iconRegister, this)
        this.overlayIcon = IconHelper.forItem(iconRegister, this, "Overlay")
    }

    override fun getColorFromItemStack(par1ItemStack : ItemStack, pass : Int) : Int {
        if (pass > 0)
            return 0xFFFFFF
        return RainbowItem.getColor()
    }

    fun addStringToTooltip(s : String, tooltip : MutableList<Any?>?) {
        tooltip!!.add(s.replace("&".toRegex(), "\u00a7"))
    }

    override fun addInformation(par1ItemStack: ItemStack?, par2EntityPlayer: EntityPlayer?, par3List: MutableList<Any?>?, par4: Boolean) {
        if(par1ItemStack == null) return
        addStringToTooltip("&7"+StatCollector.translateToLocal("misc.shadowfox_botany.color.rainbow")+"&r", par3List)
    } 

    override fun getUnlocalizedName(par1ItemStack: ItemStack?): String {
        return if(par1ItemStack != null) this.getUnlocalizedNameLazy(par1ItemStack) else ""
    }

    internal fun getUnlocalizedNameLazy(par1ItemStack: ItemStack): String {
        return super.getUnlocalizedName(par1ItemStack)
    }
}
