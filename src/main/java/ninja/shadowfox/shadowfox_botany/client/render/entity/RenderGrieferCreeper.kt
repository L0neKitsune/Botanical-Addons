package ninja.shadowfox.shadowfox_botany.client.render.entity

import net.minecraft.client.model.ModelCreeper
import net.minecraft.client.renderer.entity.RenderCreeper
import net.minecraft.entity.monster.EntityCreeper
import net.minecraft.util.ResourceLocation
import org.lwjgl.opengl.GL11

/**
 * Created by l0nekitsune on 10/31/15.
 */

public class RenderGrieferCreeper : RenderCreeper() {
    private val armoredCreeperTextures = ResourceLocation("shadowfox_botany:textures/model/griefer_creeper/creeper_armor.png")
    private val creeperTextures = ResourceLocation("shadowfox_botany:textures/model/griefer_creeper/griefer_creeper.png")
    /** The creeper model.  */
    private val creeperModel = ModelCreeper(2.0f)

    override fun shouldRenderPass(p_77032_1_: EntityCreeper, p_77032_2_: Int, p_77032_3_: Float): Int {
        if (p_77032_1_.powered) {
            if (p_77032_1_.isInvisible) {
                GL11.glDepthMask(false)
            } else {
                GL11.glDepthMask(true)
            }

            if (p_77032_2_ == 1) {
                val f1 = p_77032_1_.ticksExisted.toFloat() + p_77032_3_
                this.bindTexture(armoredCreeperTextures)
                GL11.glMatrixMode(GL11.GL_TEXTURE)
                GL11.glLoadIdentity()
                val f2 = f1 * 0.01f
                val f3 = f1 * 0.01f
                GL11.glTranslatef(f2, f3, 0.0f)
                this.setRenderPassModel(this.creeperModel)
                GL11.glMatrixMode(GL11.GL_MODELVIEW)
                GL11.glEnable(GL11.GL_BLEND)
                val f4 = 0.5f
                GL11.glColor4f(f4, f4, f4, 1.0f)
                GL11.glDisable(GL11.GL_LIGHTING)
                GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE)
                return 1
            }

            if (p_77032_2_ == 2) {
                GL11.glMatrixMode(GL11.GL_TEXTURE)
                GL11.glLoadIdentity()
                GL11.glMatrixMode(GL11.GL_MODELVIEW)
                GL11.glEnable(GL11.GL_LIGHTING)
                GL11.glDisable(GL11.GL_BLEND)
            }
        }

        return -1
    }

    override fun getEntityTexture(p_110775_1_: EntityCreeper): ResourceLocation {
        return creeperTextures
    }
}
