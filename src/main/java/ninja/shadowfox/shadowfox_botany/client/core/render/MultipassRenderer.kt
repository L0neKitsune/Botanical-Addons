package ninja.shadowfox.shadowfox_botany.client.core.render

import net.minecraft.block.Block
import net.minecraft.client.renderer.Tessellator
import net.minecraft.world.IBlockAccess
import ninja.shadowfox.shadowfox_botany.common.blocks.base.IMultipassRenderer
import ninja.shadowfox.shadowfox_botany.lib.Constants
import org.lwjgl.opengl.GL11

@Deprecated("1.7.10")
class MultipassRenderer
//: ISimpleBlockRenderingHandler {
//
//    companion object {
//        var pass = 0
//    }
//
//    override fun getRenderId(): Int {
//        return Constants.multipassRenderingID
//    }
//
//    override fun shouldRender3DInInventory(modelId: Int): Boolean {
//        return true
//    }
//
//    override fun renderInventoryBlock(block: Block, metadata: Int, modelID: Int, renderer: RenderBlocks) {
//        if (block is IMultipassRenderer) {
//            renderInventoryBlock(block.innerBlock(metadata), metadata, 1.0F, renderer, 0)
//            renderInventoryBlock(block, metadata, 1.0F, renderer, 1)
//        }
//    }
//
//    override fun renderWorldBlock(world: IBlockAccess, x: Int, y: Int, z: Int, block: Block, modelId: Int, renderer: RenderBlocks): Boolean {
//        if (block is IMultipassRenderer) {
//            if (pass == 1) renderer.renderStandardBlock(block, x, y, z)
//            else renderer.renderStandardBlock(block.innerBlock(world, x, y, z), x, y, z)
//        }
//        return true
//    }
//
//    fun renderInventoryBlock(block: Block, meta: Int, brightness: Float, renderer: RenderBlocks, stage: Int) {
//        val tessellator = Tessellator.instance
//        GL11.glPushMatrix()
//        if (renderer.useInventoryTint) {
//            var j = block.getRenderColor(meta)
//
//            var f1 = (j shr 16 and 255).toFloat() / 255.0F
//            var f2 = (j shr 8 and 255).toFloat() / 255.0F
//            var f3 = (j and 255).toFloat() / 255.0F
//            GL11.glColor4f(f1 * brightness, f2 * brightness, f3 * brightness, 1.0F);
//        }
//
//        block.setBlockBoundsForItemRender()
//        renderer.setRenderBoundsFromBlock(block)
//        GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f)
//        GL11.glTranslatef(-0.5f, -0.5f, -0.5f)
//
//        val zf = stage.toFloat() / 200f
//        GL11.glTranslatef(-zf, -zf, -zf)
//        GL11.glScalef(1f + zf * 2f, 1f + zf * 2f, 1f + zf * 2f)
//        tessellator.startDrawingQuads()
//        tessellator.setNormal(0.0f, -1.0f, 0.0f)
//        renderer.renderFaceYNeg(block, 0.0, 0.0, 0.0, renderer.getBlockIconFromSideAndMetadata(block, 0, meta))
//        tessellator.draw()
//        tessellator.startDrawingQuads()
//        tessellator.setNormal(0.0f, 1.0f, 0.0f)
//        renderer.renderFaceYPos(block, 0.0, 0.0, 0.0, renderer.getBlockIconFromSideAndMetadata(block, 1, meta))
//        tessellator.draw()
//        tessellator.startDrawingQuads()
//        tessellator.setNormal(0.0f, 0.0f, -1.0f)
//        renderer.renderFaceZNeg(block, 0.0, 0.0, 0.0, renderer.getBlockIconFromSideAndMetadata(block, 2, meta))
//        tessellator.draw()
//        tessellator.startDrawingQuads()
//        tessellator.setNormal(0.0f, 0.0f, 1.0f)
//        renderer.renderFaceZPos(block, 0.0, 0.0, 0.0, renderer.getBlockIconFromSideAndMetadata(block, 3, meta))
//        tessellator.draw()
//        tessellator.startDrawingQuads()
//        tessellator.setNormal(-1.0f, 0.0f, 0.0f)
//        renderer.renderFaceXNeg(block, 0.0, 0.0, 0.0, renderer.getBlockIconFromSideAndMetadata(block, 4, meta))
//        tessellator.draw()
//        tessellator.startDrawingQuads()
//        tessellator.setNormal(1.0f, 0.0f, 0.0f)
//        renderer.renderFaceXPos(block, 0.0, 0.0, 0.0, renderer.getBlockIconFromSideAndMetadata(block, 5, meta))
//        tessellator.draw()
//        GL11.glTranslatef(0.5f + zf, 0.5f + zf, 0.5f + zf)
//        GL11.glPopMatrix()
//    }
//}
