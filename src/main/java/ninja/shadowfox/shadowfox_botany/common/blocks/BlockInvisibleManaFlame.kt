package ninja.shadowfox.shadowfox_botany.common.blocks

import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.AxisAlignedBB
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import ninja.shadowfox.shadowfox_botany.common.blocks.base.ShadowFoxBlockMod
import ninja.shadowfox.shadowfox_botany.common.blocks.tile.TileInvisibleManaFlame
import ninja.shadowfox.shadowfox_botany.common.utils.helper.IconHelper
import java.util.*


class BlockManaInvisibleFlame : ShadowFoxBlockMod(Material.cloth) {

    init {
        this.setBlockName("invisibleFlame")
        val f = 0.25f
        this.setStepSound(Block.soundTypeCloth)
        this.setBlockBounds(f, f, f, 1.0f - f, 1.0f - f, 1.0f - f)
        this.setLightLevel(1.0f)
    }

    override fun registerBlockIcons(par1IconRegister: IIconRegister) {
        blockIcon = IconHelper.forName(par1IconRegister, "transparent")
    }
    override fun isOpaqueCube(): Boolean = false
    override fun renderAsNormalBlock(): Boolean = false
    override fun hasTileEntity(metadata: Int): Boolean = true


    override fun getBlocksMovement(p_149655_1_: IBlockAccess?, p_149655_2_: Int, p_149655_3_: Int, p_149655_4_: Int): Boolean = true
    override fun getCollisionBoundingBoxFromPool(p_149668_1_: World?, p_149668_2_: Int, p_149668_3_: Int, p_149668_4_: Int): AxisAlignedBB? = null

    override fun getDrops(world: World, x: Int, y: Int, z: Int, metadata: Int, fortune: Int): ArrayList<ItemStack> {
        return ArrayList()
    }

    override fun createTileEntity(p_149915_1_: World?, p_149915_2_: Int): TileEntity? {
        return TileInvisibleManaFlame()
    }
}