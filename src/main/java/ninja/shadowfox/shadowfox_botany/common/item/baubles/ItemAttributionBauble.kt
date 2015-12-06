package ninja.shadowfox.shadowfox_botany.common.item.baubles

import ninja.shadowfox.shadowfox_botany.common.core.ShadowFoxCreativeTab
import ninja.shadowfox.shadowfox_botany.common.utils.helper.IconHelper

import net.minecraft.client.Minecraft
import net.minecraft.client.model.ModelBiped
import net.minecraft.client.renderer.ItemRenderer
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.client.renderer.texture.TextureMap
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.IIcon
import net.minecraft.util.ResourceLocation
import net.minecraft.util.StatCollector

import net.minecraftforge.client.event.RenderPlayerEvent
import net.minecraftforge.client.event.TextureStitchEvent
import net.minecraftforge.common.MinecraftForge

import org.lwjgl.opengl.GL11

import vazkii.botania.client.core.proxy.ClientProxy
import vazkii.botania.client.model.ModelTinyPotato
import vazkii.botania.client.lib.LibResources
import vazkii.botania.api.item.IBaubleRender
import vazkii.botania.api.item.ICosmeticBauble
import vazkii.botania.common.item.equipment.bauble.ItemBauble
import vazkii.botania.client.render.block.InterpolatedIcon

import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import cpw.mods.fml.relauncher.FMLLaunchHandler
import cpw.mods.fml.common.eventhandler.SubscribeEvent

import baubles.api.BaubleType
import net.minecraft.client.renderer.OpenGlHelper
import vazkii.botania.client.core.helper.ShaderHelper

import kotlin.properties.Delegates

class ItemAttributionBauble() : ItemBauble("attributionBauble"), ICosmeticBauble {
    private val kitsuneTexture = ResourceLocation("shadowfox_botany:textures/items/kitsunesTail.png")

    var defaultIcon: IIcon by Delegates.notNull()
    var wireIcon: IIcon by Delegates.notNull()
    var kitsuneIcon: IIcon by Delegates.notNull()

    init {
        setHasSubtypes(true)
        setCreativeTab(ShadowFoxCreativeTab)
        if (FMLLaunchHandler.side().isClient())
            MinecraftForge.EVENT_BUS.register(this)
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    fun loadTextures(event: TextureStitchEvent.Pre) {
        if(event.map.textureType == 1) {
            var icon = InterpolatedIcon("shadowfox_botany:attributionBauble-WireSegal")
            if(event.map.setTextureEntry("shadowfox_botany:attributionBauble-WireSegal", icon))
                this.wireIcon = icon
        }
    }

    override fun getUnlocalizedNameInefficiently(par1ItemStack: ItemStack): String {
        return super.getUnlocalizedNameInefficiently(par1ItemStack).replace("item\\.botania:".toRegex(), "item.shadowfox_botany:")
    }

    override fun getItemStackDisplayName(stack: ItemStack): String {
        return super.getItemStackDisplayName(stack).replace("&".toRegex(), "\u00a7")
    }

    override fun registerIcons(par1IconRegister: IIconRegister) {
        this.itemIcon = IconHelper.forItem(par1IconRegister, this)
        this.defaultIcon = IconHelper.forItem(par1IconRegister, this, "Render")
        this.kitsuneIcon = IconHelper.forItem(par1IconRegister, this, "-L0neKitsune")
    }

    override fun addHiddenTooltip(par1ItemStack: ItemStack, par2EntityPlayer: EntityPlayer, par3List: MutableList<Any?>, par4: Boolean) {
        addStringToTooltip(StatCollector.translateToLocal("botaniamisc.cosmeticBauble"), par3List)
        super.addHiddenTooltip(par1ItemStack, par2EntityPlayer, par3List, par4)
    }

    fun addStringToTooltip(s : String, tooltip : MutableList<Any?>?) {
        tooltip!!.add(s.replace("&".toRegex(), "\u00a7"))
    }

    override fun getIconFromDamage(dmg: Int): IIcon {
        return itemIcon
    }

    override fun getBaubleType(stack: ItemStack) : BaubleType {
        return BaubleType.AMULET
    }

    override fun getUnlocalizedName(par1ItemStack: ItemStack): String {
        return super.getUnlocalizedName(par1ItemStack) + par1ItemStack.itemDamage
    }

    fun faceTranslate() {
        GL11.glRotatef(90F, 0F, 1F, 0F)
        GL11.glRotatef(180F, 1F, 0F, 0F)
        GL11.glTranslatef(-0.4F, 0.1F, -0.25F)
    }

    fun chestTranslate() {
        GL11.glRotatef(180F, 1F, 0F, 0F)
        GL11.glTranslatef(-0.5F, -0.7F, 0.15F)
    }

    fun scale(f: Float) {
        GL11.glScalef(f, f, f)
    }

    override fun onEquipped(stack: ItemStack, player: EntityLivingBase) {
        super.onEquipped(stack, player)
        if (stack.itemDamage !== 1 && stack.displayName.equals("vazkii is bae")) {
            stack.setItemDamage(1)
            stack.getTagCompound().removeTag("display")
        }
    }

//    @SideOnly(Side.CLIENT)
//    fun renderTail(event: RenderPlayerEvent) {
//        GL11.glEnable(GL11.GL_BLEND)
//        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
//        GL11.glShadeModel(GL11.GL_SMOOTH)
//        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240f, 240f)
//        GL11.glDisable(GL11.GL_LIGHTING)
//        GL11.glDisable(GL11.GL_CULL_FACE)
//
//        Minecraft.getMinecraft().renderEngine.bindTexture(kitsuneTexture)
//
//        IBaubleRender.Helper.rotateIfSneaking(event.entityPlayer)
//        chestTranslate()
//
//        GL11.glRotatef(-90F, 1F, 0F, 0F)
//
//        //        GL11.glTranslatef(-1F, -0.2F, -.50F)
////        GL11.glRotatef(180F, 0F, 1F, 1F)
////        GL11.glRotatef(-90F, 0F, 1F, 0F)
//        //        GL11.glScalef(1F, 1F, 1F)
//
//        val tes = Tessellator.instance
//        ShaderHelper.useShader(ShaderHelper.halo)
//        tes.startDrawingQuads()
//        tes.addVertexWithUV(-0.75, 0.0, -0.75, 0.0, 0.0)
//        tes.addVertexWithUV(-0.75, 0.0, 0.75, 0.0, 1.0)
//        tes.addVertexWithUV(0.75, 0.0, 0.75, 1.0, 1.0)
//        tes.addVertexWithUV(0.75, 0.0, -0.75, 1.0, 0.0)
//        tes.draw()
//        ShaderHelper.releaseShader()
//
//        GL11.glEnable(GL11.GL_LIGHTING)
//        GL11.glShadeModel(GL11.GL_FLAT)
//        GL11.glEnable(GL11.GL_CULL_FACE)
//    }

    @SideOnly(Side.CLIENT)
    override fun onPlayerBaubleRender(stack: ItemStack, event: RenderPlayerEvent, type: IBaubleRender.RenderType) {
        val potatoTexture = ResourceLocation(if (ClientProxy.dootDoot) LibResources.MODEL_TINY_POTATO_HALLOWEEN else LibResources.MODEL_TINY_POTATO)
        val cloakTexture = ResourceLocation("shadowfox_botany:textures/model/attributionBauble-Tristaric.png")
        var name = event.entityPlayer.commandSenderName
        if(type == IBaubleRender.RenderType.HEAD) {
            if (stack.itemDamage != 0) {
                // Render the Tiny Potato on your head... a tiny headtato, if you will.
                Minecraft.getMinecraft().renderEngine.bindTexture(potatoTexture)
                val model = ModelTinyPotato()
                GL11.glTranslatef(0.0F, -2.0F, 0.0F)
                GL11.glRotatef(-90F, 0F, 1F, 0F)
                model.render()
            } else {
                if (name == "yrsegal" || name == "theLorist") {
                    // Render the Blueflare
                    Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationItemsTexture)
                    IBaubleRender.Helper.translateToHeadLevel(event.entityPlayer)
                    faceTranslate()
                    var armor = event.entityPlayer.getCurrentArmor(3) != null
                    scale(0.35F)
                    GL11.glTranslatef(0.3F, -0.6F, if (armor) -0.15F else 0F)
                    ItemRenderer.renderItemIn2D(Tessellator.instance, wireIcon.maxU, wireIcon.minV, wireIcon.minU, wireIcon.maxV, wireIcon.iconWidth, wireIcon.iconHeight, 1F / 32F)
                }
            }
        }
        else
        if (type == IBaubleRender.RenderType.BODY && stack.itemDamage == 0) {
            Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationItemsTexture)
            IBaubleRender.Helper.rotateIfSneaking(event.entityPlayer)
            if (name == "l0nekitsune") {
                // Render a fox tail
                chestTranslate()
                GL11.glRotatef(-90F, 0F, 1F, 0F)
                GL11.glScalef(1F, 1F, 1F)
                GL11.glTranslatef(-1F, -0.2F, -.50F)
                ItemRenderer.renderItemIn2D(Tessellator.instance, kitsuneIcon.maxU, kitsuneIcon.minV, kitsuneIcon.minU, kitsuneIcon.maxV, kitsuneIcon.iconWidth, kitsuneIcon.iconHeight, 1F / 32F)
                GL11.glTranslatef(0F, 0F, 0.025F)
                ItemRenderer.renderItemIn2D(Tessellator.instance, kitsuneIcon.maxU, kitsuneIcon.minV, kitsuneIcon.minU, kitsuneIcon.maxV, kitsuneIcon.iconWidth, kitsuneIcon.iconHeight, 1F / 32F)
            } else if (name == "Tristaric") {
                // Render a cloak
                Minecraft.getMinecraft().renderEngine.bindTexture(cloakTexture)
                var armor = event.entityPlayer.getCurrentArmor(2) != null
                GL11.glTranslatef(0F, if (armor) -0.07F else -0.01F, 0F)
                if (armor) scale(1.005F)

                scale(0.1F)

                ModelBiped().bipedBody.render(1F)
            } else if (name == "yrsegal" && name != "theLorist") {
                // Render the Holy Symbol
                var armor = event.entityPlayer.getCurrentArmor(2) != null
                GL11.glRotatef(180F, 1F, 0F, 0F)
                GL11.glTranslatef(-0.26F, -0.4F, if (armor) 0.21F else 0.15F)
                scale(0.5F)
                ItemRenderer.renderItemIn2D(Tessellator.instance, defaultIcon.maxU, defaultIcon.minV, defaultIcon.minU, defaultIcon.maxV, defaultIcon.iconWidth, defaultIcon.iconHeight, 1F / 32F)
            }
        }
    }
}
