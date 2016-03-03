package ninja.shadowfox.shadowfox_stuff.common.block.minesweeper

import net.minecraft.block.material.Material
import ninja.shadowfox.shadowfox_stuff.common.block.BlockMod

/**
 * 1.8 mod
 * created on 2/24/16
 */
class BlockBoard(): BlockMod(Material.rock) {
    init {
        unlocalizedName = "blockBoard"
        blockHardness = 2f
    }
}
