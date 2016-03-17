package ninja.shadowfox.shadowfox_botany.common.blocks.base

import net.minecraft.block.ITileEntityProvider
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.BlockPos
import net.minecraft.world.World
import ninja.shadowfox.shadowfox_botany.common.blocks.base.BlockMod

/**
 * 1.8 mod
 * created on 2/24/16
 */
open class BlockModContainer(name: String, materialIn: Material, vararg variants: String) : BlockMod(name, materialIn, *variants), ITileEntityProvider {

    init {
        isBlockContainer = true
    }

    override fun breakBlock(worldIn: World, pos: BlockPos, state: IBlockState) {
        super.breakBlock(worldIn, pos, state)
        worldIn.removeTileEntity(pos)
    }

    override fun onBlockEventReceived(worldIn: World, pos: BlockPos, state: IBlockState, eventID: Int, eventParam: Int): Boolean {
        super.onBlockEventReceived(worldIn, pos, state, eventID, eventParam)
        val tileentity = worldIn.getTileEntity(pos)
        return if (tileentity == null) false else tileentity.receiveClientEvent(eventID, eventParam)
    }

    override fun createNewTileEntity(worldIn: World, meta: Int): TileEntity? {
        return null
    }
}
