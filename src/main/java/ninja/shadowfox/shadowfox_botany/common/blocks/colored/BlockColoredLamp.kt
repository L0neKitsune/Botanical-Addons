package ninja.shadowfox.shadowfox_botany.common.blocks.colored

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.EntityRenderer
import net.minecraft.client.renderer.RenderBlocks
import net.minecraft.client.renderer.Tessellator
import net.minecraft.world.IBlockAccess
import ninja.shadowfox.shadowfox_botany.common.blocks.base.BlockMod
import ninja.shadowfox.shadowfox_botany.lib.Constants
import org.lwjgl.opengl.GL11
import java.awt.Color

/**
 * @author WireSegal
 * Created at 4:44 PM on 1/12/16.
 */
class BlockColoredLamp : BlockMod(Material.redstoneLight) {

    init {
        this.setBlockName("irisLamp")
        this.setStepSound(Block.soundTypeGlass)
        this.blockHardness = 1F
    }

    companion object {
        fun powerLevel(world: IBlockAccess, x: Int, y: Int, z: Int): Int {
            val block = world.getBlock(x, y, z)
            return if (block.shouldCheckWeakPower(world, x, y, z, 0)) blockProvidingPower(world, x, y, z) else block.isProvidingWeakPower(world, x, y, z, 0)
        }
        fun blockProvidingPower(world: IBlockAccess, x: Int, y: Int, z: Int): Int {
            val b0 = 0
            var l = Math.max(b0.toInt(), world.isBlockProvidingPowerTo(x, y - 1, z, 0))

            if (l >= 15) {
                return l
            } else {
                l = Math.max(l, world.isBlockProvidingPowerTo(x, y + 1, z, 1))

                if (l >= 15) {
                    return l
                } else {
                    l = Math.max(l, world.isBlockProvidingPowerTo(x, y, z - 1, 2))

                    if (l >= 15) {
                        return l
                    } else {
                        l = Math.max(l, world.isBlockProvidingPowerTo(x, y, z + 1, 3))

                        if (l >= 15) {
                            return l
                        } else {
                            l = Math.max(l, world.isBlockProvidingPowerTo(x - 1, y, z, 4))

                            if (l >= 15) {
                                return l
                            } else {
                                l = Math.max(l, world.isBlockProvidingPowerTo(x + 1, y, z, 5))
                                return if (l >= 15) l else l
                            }
                        }
                    }
                }
            }
        }
        fun powerColor(power: Int): Int = when (power) { // These are sheep colors
                0      -> 0x191616 // Black
                1,  2  -> 0x963430 // Red
                3,  4  -> 0xDB7D3E // Orange
                5,  6  -> 0xB1A627 // Yellow
                7,  8  -> 0x41AE38 // Lime
                9,  10 -> 0x2E388D // Blue
                11, 12 -> 0xB350BC // Magenta
                13, 14 -> 0x7E3DB5 // Purple
                else ->   0xDDDDDD // White
            }
    }

    override fun getLightValue() : Int = 15
    override fun getLightValue(world: IBlockAccess, x: Int, y: Int, z: Int): Int {
        return if (powerLevel(world, x, y, z) > 0) 15 else 0
    }
    override fun canConnectRedstone(world: IBlockAccess?, x: Int, y: Int, z: Int, side: Int): Boolean = true

    override fun getRenderType(): Int {
        return Constants.lampRenderingID
    }

    class LampRenderer : ISimpleBlockRenderingHandler {

        public override fun getRenderId(): Int {
            return Constants.lampRenderingID
        }

        public override fun shouldRender3DInInventory(modelId: Int): Boolean {
            return true
        }

        public override fun renderInventoryBlock(block: Block, meta: Int, modelID: Int, renderer: RenderBlocks) {
            val tessellator = Tessellator.instance
            GL11.glPushMatrix()
            if (renderer.useInventoryTint) {
                var j = powerColor(0)

                var f1 = (j shr 16 and 255).toFloat() / 255.0F
                var f2 = (j shr 8 and 255).toFloat() / 255.0F
                var f3 = (j and 255).toFloat() / 255.0F
                GL11.glColor4f(f1, f2, f3, 1.0F);
            }

            block.setBlockBoundsForItemRender()
            renderer.setRenderBoundsFromBlock(block)
            GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f)
            GL11.glTranslatef(-0.5f, -0.5f, -0.5f)

            tessellator.startDrawingQuads()
            tessellator.setNormal(0.0f, -1.0f, 0.0f)
            renderer.renderFaceYNeg(block, 0.0, 0.0, 0.0, renderer.getBlockIconFromSideAndMetadata(block, 0, meta))
            tessellator.draw()
            tessellator.startDrawingQuads()
            tessellator.setNormal(0.0f, 1.0f, 0.0f)
            renderer.renderFaceYPos(block, 0.0, 0.0, 0.0, renderer.getBlockIconFromSideAndMetadata(block, 1, meta))
            tessellator.draw()
            tessellator.startDrawingQuads()
            tessellator.setNormal(0.0f, 0.0f, -1.0f)
            renderer.renderFaceZNeg(block, 0.0, 0.0, 0.0, renderer.getBlockIconFromSideAndMetadata(block, 2, meta))
            tessellator.draw()
            tessellator.startDrawingQuads()
            tessellator.setNormal(0.0f, 0.0f, 1.0f)
            renderer.renderFaceZPos(block, 0.0, 0.0, 0.0, renderer.getBlockIconFromSideAndMetadata(block, 3, meta))
            tessellator.draw()
            tessellator.startDrawingQuads()
            tessellator.setNormal(-1.0f, 0.0f, 0.0f)
            renderer.renderFaceXNeg(block, 0.0, 0.0, 0.0, renderer.getBlockIconFromSideAndMetadata(block, 4, meta))
            tessellator.draw()
            tessellator.startDrawingQuads()
            tessellator.setNormal(1.0f, 0.0f, 0.0f)
            renderer.renderFaceXPos(block, 0.0, 0.0, 0.0, renderer.getBlockIconFromSideAndMetadata(block, 5, meta))
            tessellator.draw()
            GL11.glPopMatrix()
        }

        public override fun renderWorldBlock(world: IBlockAccess, x: Int, y: Int, z: Int, block: Block, modelId: Int, renderer: RenderBlocks): Boolean {
            val renderColor = Color(powerColor(powerLevel(world, x, y, z)))
            var f = renderColor.red.toFloat()/255F
            var f1 = renderColor.green.toFloat()/255F
            var f2 = renderColor.blue.toFloat()/255F
            if (EntityRenderer.anaglyphEnable) {
                val f3 = (f * 30.0f + f1 * 59.0f + f2 * 11.0f) / 100.0f
                val f4 = (f * 30.0f + f1 * 70.0f) / 100.0f
                val f5 = (f * 30.0f + f2 * 70.0f) / 100.0f
                f = f3
                f1 = f4
                f2 = f5
            }

            GL11.glColor3f(f, f1, f2)

            val ret = if (Minecraft.isAmbientOcclusionEnabled() && block.getLightValue() == 0) (if (renderer.partialRenderBounds) renderer.renderStandardBlockWithAmbientOcclusionPartial(block, x, y, z, f, f1, f2) else renderer.renderStandardBlockWithAmbientOcclusion(block, x, y, z, f, f1, f2)) else renderer.renderStandardBlockWithColorMultiplier(block, x, y, z, f, f1, f2)

            GL11.glColor3f(1F, 1F, 1F)
            return ret
        }
    }

}
