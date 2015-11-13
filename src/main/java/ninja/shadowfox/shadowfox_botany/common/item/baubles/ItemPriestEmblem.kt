package ninja.shadowfox.shadowfox_botany.common.item.baubles


import ninja.shadowfox.shadowfox_botany.common.item.ColorfulItem
import ninja.shadowfox.shadowfox_botany.common.item.ShadowFoxItems
import ninja.shadowfox.shadowfox_botany.common.core.ShadowFoxCreativeTab
import ninja.shadowfox.shadowfox_botany.common.utils.helper.IconHelper

import java.awt.Color

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
import net.minecraftforge.client.event.RenderPlayerEvent

import org.lwjgl.opengl.GL11

import vazkii.botania.api.item.IBaubleRender
import vazkii.botania.api.mana.IManaUsingItem
import vazkii.botania.api.mana.ManaItemHandler
import vazkii.botania.common.Botania
import vazkii.botania.common.core.helper.ItemNBTHelper
import vazkii.botania.common.core.helper.Vector3
import vazkii.botania.common.item.equipment.bauble.ItemBauble

import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly

import baubles.api.BaubleType
import baubles.common.lib.PlayerHandler

class ItemPriestEmblem() : ItemBauble("priestEmblem"), IBaubleRender, IManaUsingItem {

    companion object {
        val TYPES = 2
        public fun getEmblem(meta: Int, player: EntityPlayer): ItemStack? {
            var baubles = PlayerHandler.getPlayerBaubles(player)
            var stack = baubles.getStackInSlot(0)
            return if (stack != null && ((stack.item == ShadowFoxItems.emblem && stack.getItemDamage() == meta) || stack.item == ShadowFoxItems.aesirEmblem) && ItemNBTHelper.getByte(stack, "active", 0) == 1.toByte()) stack else null
        }
    }

    val COST = 2
    var icons: Array<IIcon?> = arrayOfNulls(TYPES)
    var baubleIcons: Array<IIcon?> = arrayOfNulls(TYPES)

    init {
        setHasSubtypes(true)
        setCreativeTab(ShadowFoxCreativeTab)
    }

    override fun getUnlocalizedNameInefficiently(par1ItemStack: ItemStack): String {
        return super.getUnlocalizedNameInefficiently(par1ItemStack).replace("item\\.botania:".toRegex(), "item.shadowfox_botany:")
    }

    override fun getItemStackDisplayName(stack: ItemStack): String {
        return super.getItemStackDisplayName(stack).replace("&".toRegex(), "\u00a7")
    }

    override fun registerIcons(par1IconRegister: IIconRegister) {
        for(i in 0..(TYPES - 1))
            icons[i] = IconHelper.forItem(par1IconRegister, this, i)
        for(i in 0..(TYPES - 1))
            baubleIcons[i] = IconHelper.forItem(par1IconRegister, this, "Render"+i.toString())
    }

    override fun getSubItems(item: Item, tab: CreativeTabs?, list: MutableList<Any?>) {
        for(i in 0..(TYPES - 1))
            list.add(ItemStack(item, 1, i))
    }

    override fun getIconFromDamage(dmg: Int): IIcon? {
        return icons[Math.min(TYPES - 1, dmg)]
    }

    fun getBaubleIconFromDamage(dmg: Int): IIcon? {
        return baubleIcons[Math.min(TYPES - 1, dmg)]
    }

    override fun getBaubleType(stack: ItemStack) : BaubleType {
        return BaubleType.AMULET
    }

    override fun getUnlocalizedName(par1ItemStack: ItemStack): String {
        return super.getUnlocalizedName(par1ItemStack) + par1ItemStack.itemDamage
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
                    if (!this.hasPhantomInk(stack)) {
                        when (stack.itemDamage) {
                            0 -> {
                                var playerHead = Vector3.fromEntityCenter(player).add(0.0, 0.75, 0.0).add(Vector3(player.lookVec).multiply(-0.25))
                                val playerShift = playerHead.copy().add(getHeadOrientation(player))
                                Botania.proxy.lightningFX(player.worldObj, playerHead, playerShift, 2.0f, 96708, 11198463)
                            }
                            1 -> {
                                for (i in 0..6) {
                                    var xmotion = (Math.random()-0.5).toFloat() * 0.15f
                                    var zmotion = (Math.random()-0.5).toFloat() * 0.15f
                                    // Brown
                                    var r = 0.6F
                                    var g = 0.3F
                                    var b = 0.0F
                                    val held = player.inventory.getCurrentItem()
                                    if (held != null && held.item == ShadowFoxItems.colorfulSkyDirtRod) {
                                        val color = Color(ColorfulItem.colorFromItemStack(held))
                                        r = color.red.toFloat() / 255F
                                        g = color.green.toFloat() / 255F
                                        b = color.blue.toFloat() / 255F
                                    }
                                    Botania.proxy.wispFX(player.worldObj, player.posX, player.posY - player.yOffset, player.posZ, r, g, b, Math.random().toFloat() * 0.15f + 0.15f, xmotion, 0.0075f, zmotion)
                                }
                            }
                        }
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

            var icon = getBaubleIconFromDamage(stack.itemDamage)
            if(icon != null)
            ItemRenderer.renderItemIn2D(Tessellator.instance, icon.maxU, icon.minV, icon.minU, icon.maxV, icon.iconWidth, icon.iconHeight, 1F / 32F)
        }
    }
}
