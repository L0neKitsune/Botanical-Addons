package ninja.shadowfox.shadowfox_stuff.common.block.minesweeper

import net.minecraft.block.Block
import net.minecraft.block.BlockTNT
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.item.EntityMinecartTNT
import net.minecraft.entity.item.EntityTNTPrimed
import net.minecraft.util.BlockPos
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import ninja.shadowfox.shadowfox_stuff.common.block.BlockMod

/**
 * 1.8 mod
 * created on 2/24/16
 */
class BlockMine(): BlockMod(Material.rock) {
    init {
        unlocalizedName = "minesweeperMine"
        setBlockUnbreakable()
    }

    override fun onNeighborBlockChange(worldIn: World?, pos: BlockPos?, state: IBlockState?, neighborBlock: Block?) {
        super.onNeighborBlockChange(worldIn, pos, state, neighborBlock)

        if(worldIn != null && pos != null) {
            if (!worldIn.isRemote) {
                if (worldIn.isAirBlock(pos.up())) {
                    worldIn.setBlockToAir(pos)

                    val entitytntprimed = EntityTNTPrimed(worldIn, (pos.x.toFloat() + 0.5f).toDouble(), pos.y.toDouble(), (pos.z.toFloat() + 0.5f).toDouble(), null)
                    worldIn.spawnEntityInWorld(entitytntprimed)
                    worldIn.playSoundAtEntity(entitytntprimed, "game.tnt.primed", 1.0f, 1.0f)
                }
            }
        }
    }
}
