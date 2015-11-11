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

    override fun shouldRenderPass(entity: EntityCreeper, pass: Int, par3Float: Float): Int {
        if (entity.powered) {
            if (entity.isInvisible) {
                GL11.glDepthMask(false)
            } else {
                GL11.glDepthMask(true)
            }

            if (pass == 1) {
                val f1 = entity.ticksExisted.toFloat() + par3Float
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

            if (pass == 2) {
                GL11.glMatrixMode(GL11.GL_TEXTURE)
                GL11.glLoadIdentity()
                GL11.glMatrixMode(GL11.GL_MODELVIEW)
                GL11.glEnable(GL11.GL_LIGHTING)
                GL11.glDisable(GL11.GL_BLEND)
            }
        }

        return -1
    }

    override fun getEntityTexture(entity: EntityCreeper): ResourceLocation {
        return creeperTextures
    }
}
