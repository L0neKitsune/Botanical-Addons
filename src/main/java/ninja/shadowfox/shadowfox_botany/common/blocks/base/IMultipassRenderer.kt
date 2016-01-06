package ninja.shadowfox.shadowfox_botany.common.blocks.base

import net.minecraft.block.Block
import net.minecraft.world.IBlockAccess

interface IMultipassRenderer {
    fun innerBlock(world: IBlockAccess?, x: Int, y: Int, z: Int): Block
    fun innerBlock(meta: Int): Block
}
