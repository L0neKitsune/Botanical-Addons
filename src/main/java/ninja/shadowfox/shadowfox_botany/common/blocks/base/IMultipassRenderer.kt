package ninja.shadowfox.shadowfox_botany.common.blocks.base

import net.minecraft.block.Block

interface IMultipassRenderer {
	fun innerBlock(): Block
}
