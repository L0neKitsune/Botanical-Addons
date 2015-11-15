package ninja.shadowfox.shadowfox_botany.common.item.baubles

import ninja.shadowfox.shadowfox_botany.common.core.ShadowFoxCreativeTab
import ninja.shadowfox.shadowfox_botany.common.utils.helper.IconHelper

import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.ItemRenderer
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.client.renderer.texture.TextureMap
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.IIcon
import net.minecraft.util.MathHelper
import net.minecraft.util.StatCollector
import net.minecraftforge.client.event.RenderPlayerEvent

import org.lwjgl.opengl.GL11

import vazkii.botania.api.item.ICosmeticAttachable
import vazkii.botania.api.item.IBaubleRender
import vazkii.botania.api.mana.IManaUsingItem
import vazkii.botania.api.mana.ManaItemHandler
import vazkii.botania.common.Botania
import vazkii.botania.common.core.helper.ItemNBTHelper
import vazkii.botania.common.core.helper.Vector3
import vazkii.botania.common.item.equipment.bauble.ItemBauble

import cpw.mods.fml.common.registry.GameRegistry
import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly

import baubles.api.BaubleType

import kotlin.properties.Delegates

class ItemAesirEmblem() : ItemBauble("aesirEmblem"), IBaubleRender, IManaUsingItem {

    val COST = 2*ItemPriestEmblem.TYPES
    var baubleIcon: IIcon by Delegates.notNull()

    init {
        setHasSubtypes(true)
        setCreativeTab(ShadowFoxCreativeTab)
    }

    override fun addInformation(par1ItemStack: ItemStack?, par2EntityPlayer: EntityPlayer?, par3List: MutableList<Any?>?, par4: Boolean) {
        if(par1ItemStack == null) return
        this.addStringToTooltip("&7"+StatCollector.translateToLocal("misc.shadowfox_botany.creative")+"&r", par3List)
        super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4)
    } 

    fun addStringToTooltip(s : String, tooltip : MutableList<Any?>?) {
        tooltip!!.add(s.replace("&".toRegex(), "\u00a7"))
    }

    override fun getUnlocalizedNameInefficiently(par1ItemStack: ItemStack): String {
        return super.getUnlocalizedNameInefficiently(par1ItemStack).replace("item\\.botania:".toRegex(), "item.shadowfox_botany:")
    }

    override fun getItemStackDisplayName(stack: ItemStack): String {
        return super.getItemStackDisplayName(stack).replace("&".toRegex(), "\u00a7")
    }

    @SideOnly(Side.CLIENT)
    override fun registerIcons(par1IconRegister: IIconRegister) {
        this.itemIcon = IconHelper.forItem(par1IconRegister, this)
        baubleIcon = IconHelper.forItem(par1IconRegister, this, "Render")
    }

    override fun getBaubleType(stack: ItemStack) : BaubleType {
        return BaubleType.AMULET
    }

    fun getHeadOrientation(entity: EntityLivingBase): Vector3 {
        val f1 = MathHelper.cos(-entity.rotationYaw * 0.017453292F - Math.PI.toFloat())
        val f2 = MathHelper.sin(-entity.rotationYaw * 0.017453292F - Math.PI.toFloat())
        val f3 = -MathHelper.cos(-(entity.rotationPitch-90) * 0.017453292F)
        val f4 = MathHelper.sin(-(entity.rotationPitch-90) * 0.017453292F)
        return Vector3((f2 * f3).toDouble(), f4.toDouble(), (f1 * f3).toDouble())
    }

    override fun onWornTick(stack: ItemStack, player: EntityLivingBase) {
        if (player.ticksExisted % 10 == 0) {
            
            if(player is EntityPlayer) {
                if (ManaItemHandler.requestManaExact(stack, player, COST, true)) {
                    ItemNBTHelper.setByte(stack, "active", 1.toByte())
                    if (!this.hasPhantomInk(stack) && (this as ICosmeticAttachable).getCosmeticItem(stack) == null) {
                        val shift = getHeadOrientation(player)
                        val x = player.posX + shift.x * 0.25
                        val y = player.posY + shift.y * 0.25
                        val z = player.posZ + shift.z * 0.25
                        val xmotion = shift.x.toFloat() * 0.025f
                        val ymotion = shift.y.toFloat() * 0.025f
                        val zmotion = shift.z.toFloat() * 0.025f
                        Botania.proxy.wispFX(player.worldObj, x, y, z, 1.0f, 1.0f, 1.0f, Math.random().toFloat() * 0.15f + 0.15f, xmotion, ymotion, zmotion)
                    }
                }
                else
                    ItemNBTHelper.setByte(stack, "active", 0.toByte())
            }
        }
    }

    override fun usesMana(stack: ItemStack): Boolean {
        return true
    }

    @SideOnly(Side.CLIENT)
    override fun onPlayerBaubleRender(stack: ItemStack, event: RenderPlayerEvent, type: IBaubleRender.RenderType) {
        if(type == IBaubleRender.RenderType.BODY) {
            Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationItemsTexture)
            IBaubleRender.Helper.rotateIfSneaking(event.entityPlayer)
            var armor = event.entityPlayer.getCurrentArmor(2) != null
            GL11.glRotatef(180F, 1F, 0F, 0F)
            GL11.glTranslatef(-0.26F, -0.4F, if (armor) 0.2F else 0.15F)
            GL11.glScalef(0.5F, 0.5F, 0.5F)

            ItemRenderer.renderItemIn2D(Tessellator.instance, baubleIcon.maxU, baubleIcon.minV, baubleIcon.minU, baubleIcon.maxV, baubleIcon.iconWidth, baubleIcon.iconHeight, 1F / 32F)
        }
    }
}
