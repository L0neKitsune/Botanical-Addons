package ninja.shadowfox.shadowfox_botany.client.render.tile

import net.minecraft.block.Block
import net.minecraft.world.IBlockAccess

import ninja.shadowfox.shadowfox_botany.lib.Constants
import ninja.shadowfox.shadowfox_botany.common.blocks.base.IMultipassRenderer

import net.minecraft.client.renderer.RenderBlocks
import net.minecraft.client.renderer.EntityRenderer

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler

class MultipassRenderer: ISimpleBlockRenderingHandler {

    companion object {
        var pass = 0
    }

    public override fun getRenderId(): Int {
        return Constants.multipassRenderingID
    }

    public override fun shouldRender3DInInventory(modelId: Int): Boolean {return true}

    public override fun renderInventoryBlock(block: Block, metadata: Int, modelID: Int, renderer: RenderBlocks){}

    public override fun renderWorldBlock(world: IBlockAccess, x: Int, y: Int, z: Int, block: Block, modelId: Int, renderer: RenderBlocks): Boolean {
        if (block is IMultipassRenderer)
            if (pass == 1) renderer.renderStandardBlock(block, x, y, z) else renderer.renderStandardBlock(block.innerBlock(), x, y, z)
        return true
    }
}
