package ninja.shadowfox.shadowfox_botany.client.core.multipart

import net.minecraft.block.Block

import codechicken.lib.render.CCRenderState
import codechicken.microblock.BlockMicroMaterial

class ColoredMicroMaterial(val block: Block, val meta: Int) : BlockMicroMaterial(block, meta) {
    override fun getColour(pass: Int): Int {
        return block.getRenderColor(meta) shl 8 or 0xFF
    }
}
