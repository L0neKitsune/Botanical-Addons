package ninja.shadowfox.shadowfox_botany.common.blocks

import cpw.mods.fml.common.Optional
import net.minecraft.block.Block
import net.minecraft.block.BlockContainer
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.AxisAlignedBB
import net.minecraft.util.IIcon
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import ninja.shadowfox.shadowfox_botany.common.blocks.tile.TileInvisibleManaFlame
import java.util.*

/**
 * Created by l0nekitsune on 11/17/15.
 */
class BlockManaInvisibleFlame : BlockContainer(Material.cloth) {

    init {
        this.setBlockName("invisibleFlame")
        val f = 0.25f
        this.setStepSound(Block.soundTypeCloth)
        this.setBlockBounds(f, f, f, 1.0f - f, 1.0f - f, 1.0f - f)
        this.setLightLevel(1.0f)
    }

    override fun registerBlockIcons(par1IconRegister: IIconRegister) {}
    override fun isOpaqueCube(): Boolean = false
    override fun renderAsNormalBlock(): Boolean = false
    override fun getBlocksMovement(p_149655_1_: IBlockAccess?, p_149655_2_: Int, p_149655_3_: Int, p_149655_4_: Int): Boolean = true
    override fun getCollisionBoundingBoxFromPool(p_149668_1_: World?, p_149668_2_: Int, p_149668_3_: Int, p_149668_4_: Int): AxisAlignedBB? = null

    override fun getIcon(side: Int, meta: Int): IIcon {
        return Blocks.fire.getIcon(side, meta)
    }

    override fun getDrops(world: World, x: Int, y: Int, z: Int, metadata: Int, fortune: Int): ArrayList<ItemStack> {
        return ArrayList()
    }

    override fun createNewTileEntity(world: World, meta: Int): TileEntity {
        return TileInvisibleManaFlame()
    }
}