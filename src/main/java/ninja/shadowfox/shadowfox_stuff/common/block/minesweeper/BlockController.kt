package ninja.shadowfox.shadowfox_stuff.common.block.minesweeper

import net.minecraft.block.material.Material
import net.minecraft.world.World
import ninja.shadowfox.shadowfox_stuff.common.block.BlockModContainer
import ninja.shadowfox.shadowfox_stuff.common.block.tile.TileController

/**
 * 1.8 mod
 * created on 2/24/16
 */
class BlockController(): BlockModContainer<TileController>(Material.rock) {
    init {
        unlocalizedName = "minesweeperController"

        setBlockUnbreakable()
    }

    override fun createNewTileEntity(var1: World, var2: Int): TileController = TileController()
}
