package ninja.shadowfox.shadowfox_botany.client.render.entity

import net.minecraft.client.renderer.ItemRenderer
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.entity.Render
import net.minecraft.client.renderer.texture.TextureMap
import net.minecraft.entity.Entity
import net.minecraft.util.IIcon
import net.minecraft.util.ResourceLocation
import ninja.shadowfox.shadowfox_botany.common.entity.EntityThrowableItem
import ninja.shadowfox.shadowfox_botany.common.item.ShadowFoxItems
import org.lwjgl.opengl.GL11
import java.awt.Color

/**
 * Created by l0nekitsune on 1/20/16.
 */
class RenderThrownItem : Render() {

    override fun doRender(p_76986_1_: Entity, p_76986_2_: Double, p_76986_4_: Double, p_76986_6_: Double, p_76986_8_: Float, p_76986_9_: Float) {
        val c = p_76986_1_ as EntityThrowableItem
        var itemStack = c.item ?: null

        if(itemStack != null) {
            GL11.glScalef(0.75F, 0.75F, 0.75F)
            GL11.glTranslatef(0F, 0F, 0.5F)
            GL11.glRotatef(90F, 0F, 1F, 0F)
            var renderPass = 0

            do {
                val icon = itemStack.item.getIcon(itemStack, renderPass)
                if (icon != null) {
                    val color = Color(itemStack.item.getColorFromItemStack(itemStack, renderPass))
                    GL11.glColor3ub(color.red.toByte(), color.green.toByte(), color.blue.toByte())
                    val f = icon.minU
                    val f1 = icon.maxU
                    val f2 = icon.minV
                    val f3 = icon.maxV

                    ItemRenderer.renderItemIn2D(Tessellator.instance, f1, f2, f, f3, icon.iconWidth, icon.iconHeight, 0.0625f)
                    GL11.glColor3f(1.0f, 1.0f, 1.0f)
                }

                ++renderPass
            } while (renderPass < itemStack.item.getRenderPasses(itemStack.itemDamage))
        }

    }

    override fun getEntityTexture(p_110775_1_: Entity): ResourceLocation {
        return TextureMap.locationItemsTexture
    }

    private fun func_77026_a(p_77026_1_: Tessellator, p_77026_2_: IIcon, light: Int) {
        val f = p_77026_2_.minU
        val f1 = p_77026_2_.maxU
        val f2 = p_77026_2_.minV
        val f3 = p_77026_2_.maxV
        val f4 = 1.0f
        val f5 = 0.5f
        val f6 = 0.25f
        GL11.glRotatef(180.0f - this.renderManager.playerViewY, 0.0f, 1.0f, 0.0f)
        GL11.glRotatef(-this.renderManager.playerViewX, 1.0f, 0.0f, 0.0f)
        p_77026_1_.startDrawingQuads()
        p_77026_1_.setNormal(0.0f, 1.0f, 0.0f)
        if (light != -1) {
            p_77026_1_.setBrightness(light)
        }

        p_77026_1_.addVertexWithUV((0.0f - f5).toDouble(), (0.0f - f6).toDouble(), 0.0, f.toDouble(), f3.toDouble())
        p_77026_1_.addVertexWithUV((f4 - f5).toDouble(), (0.0f - f6).toDouble(), 0.0, f1.toDouble(), f3.toDouble())
        p_77026_1_.addVertexWithUV((f4 - f5).toDouble(), (f4 - f6).toDouble(), 0.0, f1.toDouble(), f2.toDouble())
        p_77026_1_.addVertexWithUV((0.0f - f5).toDouble(), (f4 - f6).toDouble(), 0.0, f.toDouble(), f2.toDouble())
        p_77026_1_.draw()
    }
}
