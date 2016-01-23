package ninja.shadowfox.shadowfox_botany.common.item.baubles

import cpw.mods.fml.common.eventhandler.SubscribeEvent
import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.block.Block
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.ItemRenderer
import net.minecraft.client.renderer.RenderBlocks
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.entity.RenderManager
import net.minecraft.client.renderer.texture.TextureMap
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemBlock
import net.minecraft.item.ItemStack
import net.minecraftforge.client.event.RenderWorldLastEvent
import net.minecraftforge.event.entity.player.PlayerInteractEvent
import ninja.shadowfox.shadowfox_botany.ShadowfoxBotany
import ninja.shadowfox.shadowfox_botany.api.item.IToolbeltBlacklisted
import ninja.shadowfox.shadowfox_botany.common.network.PlayerItemMessage
import org.lwjgl.opengl.GL11
import vazkii.botania.client.core.handler.ClientTickHandler
import java.awt.Color

/**
 * @author WireSegal
 * Created at 2:59 PM on 1/23/16.
 */
class ToolbeltEventHandler {
    @SubscribeEvent
    fun onPlayerInteract(event: PlayerInteractEvent) {
        val player = event.entityPlayer
        val beltStack = ItemToolbelt.getEquippedBelt(player)

        var heldItem = player.currentEquippedItem
        if (beltStack != null && ItemToolbelt.isEquipped(beltStack)) {
            if (event.action === PlayerInteractEvent.Action.RIGHT_CLICK_AIR) {
                val segment = ItemToolbelt.getSegmentLookedAt(beltStack, player)
                val toolStack = ItemToolbelt.getItemForSlot(beltStack, segment)
                if (toolStack == null && heldItem != null) {
                    val heldItemObject = heldItem.item
                    if (!(heldItemObject is IToolbeltBlacklisted && !heldItemObject.allowedInToolbelt(heldItem))) {
                        if (!event.world.isRemote) {
                            val item = heldItem.copy()

                            ItemToolbelt.setItem(beltStack, item, segment)

                            player.inventory.decrStackSize(player.inventory.currentItem, 64)
                            player.inventory.markDirty()
                            event.isCanceled = true
                        }
                    }
                } else if (toolStack != null) {
                    ShadowfoxBotany.network.sendToServer(PlayerItemMessage(toolStack))

                    ItemToolbelt.setItem(beltStack, null, segment)
                    event.isCanceled = true
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    fun onRenderWorldLast(event: RenderWorldLastEvent) {
        val player = Minecraft.getMinecraft().thePlayer
        var beltStack = ItemToolbelt.getEquippedBelt(player)
        if (beltStack != null && ItemToolbelt.isEquipped(beltStack))
            render(beltStack, player, event.partialTicks)
    }

    @SideOnly(Side.CLIENT)
    fun render(stack: ItemStack, player: EntityPlayer, partialTicks: Float) {
        val mc = Minecraft.getMinecraft()
        val tess = Tessellator.instance
        Tessellator.renderingWorldRenderer = false

        GL11.glPushMatrix()
        GL11.glEnable(GL11.GL_BLEND)
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
        val alpha = (Math.sin((ClientTickHandler.ticksInGame + partialTicks).toDouble() * 0.2) * 0.5F + 0.5F) * 0.4F + 0.3F

        val posX = player.prevPosX + (player.posX - player.prevPosX) * partialTicks
        val posY = player.prevPosY + (player.posY - player.prevPosY) * partialTicks
        val posZ = player.prevPosZ + (player.posZ - player.prevPosZ) * partialTicks

        GL11.glTranslated(posX - RenderManager.renderPosX, posY - RenderManager.renderPosY, posZ - RenderManager.renderPosZ)


        val base = ItemToolbelt.getRotationBase(stack)
        val angles = 360
        val segAngles = angles / ItemToolbelt.SEGMENTS
        val shift = base - segAngles / 2

        val u = 1F
        val v = 0.25F

        val s = 3F
        val m = 0.8F
        val y = v * s * 2
        var y0 = 0.0

        val segmentLookedAt = ItemToolbelt.getSegmentLookedAt(stack, player)

        for (seg in 0..ItemToolbelt.SEGMENTS - 1) {
            var inside = false
            var rotationAngle = (seg + 0.5F) * segAngles + shift
            GL11.glPushMatrix()
            GL11.glRotatef(rotationAngle, 0F, 1F, 0F)
            GL11.glTranslatef(s * m, -0.75F, 0F)

            if (segmentLookedAt == seg)
                inside = true

            val slotStack = ItemToolbelt.getItemForSlot(stack, seg)
            if (slotStack != null) {
                mc.renderEngine.bindTexture(if (slotStack.item is ItemBlock) TextureMap.locationBlocksTexture else TextureMap.locationItemsTexture)

                if (slotStack.item is ItemBlock && RenderBlocks.renderItemIn3d(Block.getBlockFromItem(slotStack.item).renderType)) {
                    GL11.glScalef(0.6F, 0.6F, 0.6F)
                    GL11.glRotatef(180F, 0F, 1F, 0F)
                    GL11.glTranslatef(0F, 0.6F, 0F)

                    RenderBlocks.getInstance().renderBlockAsItem(Block.getBlockFromItem(slotStack.item), slotStack.itemDamage, 1F)
                } else {
                    GL11.glScalef(0.75F, 0.75F, 0.75F)
                    GL11.glTranslatef(0F, 0F, 0.5F)
                    GL11.glRotatef(90F, 0F, 1F, 0F)
                    var renderPass = 0
                    while (renderPass < slotStack.item.getRenderPasses(slotStack.itemDamage)) {
                        val icon = slotStack.item.getIcon(slotStack, renderPass)
                        if (icon != null) {
                            val color = Color(slotStack.item.getColorFromItemStack(slotStack, renderPass))
                            GL11.glColor3ub(color.red.toByte(), color.green.toByte(), color.blue.toByte())
                            val f = icon.minU
                            val f1 = icon.maxU
                            val f2 = icon.minV
                            val f3 = icon.maxV
                            ItemRenderer.renderItemIn2D(Tessellator.instance, f1, f2, f, f3, icon.iconWidth, icon.iconHeight, 1F / 16F)
                            GL11.glColor3f(1F, 1F, 1F)
                        }
                        renderPass++
                    }
                }
            }
            GL11.glPopMatrix()

            GL11.glPushMatrix()
            GL11.glRotatef(180F, 1F, 0F, 0F)
            var a = alpha.toFloat()
            if (inside) {
                a += 0.3F
                y0 = -y.toDouble()
            }

            if (seg % 2 == 0)
                GL11.glColor4f(0.6F, 0.6F, 0.6F, a)
            else GL11.glColor4f(1F, 1F, 1F, a)

            GL11.glDisable(GL11.GL_CULL_FACE)
            val item = stack.item as ItemToolbelt
            mc.renderEngine.bindTexture(item.getGlowResource())
            tess.startDrawingQuads()
            for (i in 0..segAngles - 1) {
                val ang = i + seg * segAngles + shift
                var xp = Math.cos(ang * Math.PI / 180F) * s
                var zp = Math.sin(ang * Math.PI / 180F) * s

                tess.addVertexWithUV(xp * m, y.toDouble(), zp * m, u.toDouble(), v.toDouble())
                tess.addVertexWithUV(xp, y0, zp, u.toDouble(), 0.0)

                xp = Math.cos((ang + 1) * Math.PI / 180F) * s
                zp = Math.sin((ang + 1) * Math.PI / 180F) * s

                tess.addVertexWithUV(xp, y0, zp, 0.0, 0.0)
                tess.addVertexWithUV(xp * m, y.toDouble(), zp * m, 0.0, v.toDouble())
            }
            y0 = 0.0
            tess.draw()
            GL11.glEnable(GL11.GL_CULL_FACE)
            GL11.glPopMatrix()
        }
        GL11.glPopMatrix()
    }
}
