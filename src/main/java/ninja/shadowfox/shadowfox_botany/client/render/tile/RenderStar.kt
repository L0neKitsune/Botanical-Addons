package ninja.shadowfox.shadowfox_botany.client.render.tile

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer
import net.minecraft.tileentity.TileEntity
import ninja.shadowfox.shadowfox_botany.common.blocks.tile.TileEntityStar
import ninja.shadowfox.shadowfox_botany.common.item.ItemIridescent
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL12
import vazkii.botania.client.core.helper.RenderHelper

/**
 * @author WireSegal
 * Created at 9:43 PM on 2/6/16.
 */
class RenderStar: TileEntitySpecialRenderer() {

    val SIZE = 0.1f

    override fun renderTileEntityAt(tile: TileEntity, x: Double, y: Double, z: Double, partticks: Float) {
        val SIZE = 0.05f
        if (tile is TileEntityStar) {
            GL11.glPushMatrix()
            GL11.glEnable(GL11.GL_BLEND)
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
            GL11.glEnable(GL12.GL_RESCALE_NORMAL)
            GL11.glColor3f(1f, 1f, 1f)
            GL11.glTranslated(x+0.5, y+0.5, z+0.5)

            val seed = (tile.xCoord xor tile.yCoord xor tile.zCoord).toLong()
            var color = tile.getColor()
            if (color == -1) color = ItemIridescent.rainbowColor()
            RenderHelper.renderStar(color, SIZE, SIZE, SIZE, seed)

            GL11.glColor3f(1f, 1f, 1f)
            GL11.glDisable(GL11.GL_BLEND)
            GL11.glDisable(GL12.GL_RESCALE_NORMAL)
            GL11.glPopMatrix()
        }
    }
}
