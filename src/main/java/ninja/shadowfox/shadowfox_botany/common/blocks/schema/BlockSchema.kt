package ninja.shadowfox.shadowfox_botany.common.blocks.schema

import net.minecraft.block.Block
import net.minecraft.block.ITileEntityProvider
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.IIcon
import net.minecraft.world.World
import ninja.shadowfox.shadowfox_botany.common.blocks.base.BlockMod
import ninja.shadowfox.shadowfox_botany.common.blocks.tile.TileSchema
// import ninja.shadowfox.shadowfox_botany.common.blocks.tile.TileSchema
import ninja.shadowfox.shadowfox_botany.common.utils.helper.IconHelper
import vazkii.botania.api.wand.IWandable


class BlockSchema() : BlockMod(Material.wood), IWandable, ITileEntityProvider {
    lateinit var icon1: IIcon
    lateinit var icon2: IIcon
    lateinit var icon3: IIcon

    init {
        setBlockName("schemaBlock")
        isBlockContainer = true
    }

    override fun getIcon(side: Int, meta: Int): IIcon? {
        when (side) {
            0, 1 -> return icon1
            2, 3 -> return icon2
            else -> return icon3
        }
    }

    override fun hasTileEntity(metadata: Int): Boolean {
        return true
    }

    override fun createNewTileEntity(world: World?, meta: Int): TileEntity? {
        return TileSchema()
    }


    override fun breakBlock(world: World, x: Int, y: Int, z: Int, block: Block?, meta: Int) {
        super.breakBlock(world, x, y, z, block, meta)
        world.removeTileEntity(x, y, z)
    }

    override fun onBlockEventReceived(world: World?, x: Int, y: Int, z: Int, eventID: Int, eventArgs: Int): Boolean {
        super.onBlockEventReceived(world, x, y, z, eventID, eventArgs)
        val tileentity = world!!.getTileEntity(x, y, z)
        return if (tileentity != null) tileentity.receiveClientEvent(eventID, eventArgs) else false
    }

    override fun registerBlockIcons(par1IconRegister: IIconRegister) {
        icon1 = IconHelper.forName(par1IconRegister, "schema1")
        icon2 = IconHelper.forName(par1IconRegister, "schema2")
        icon3 = IconHelper.forName(par1IconRegister, "schema3")
    }

    override fun onUsedByWand(p0: EntityPlayer?, p1: ItemStack?, p2: World?, p3: Int, p4: Int, p5: Int, p6: Int): Boolean {

        if (p2 != null) {

            val tile: TileEntity? = p2.getTileEntity(p3, p4, p5)

            if (tile is TileSchema) {
                tile.blockActivated(p0)
            }
        }

        return true
    }
}
