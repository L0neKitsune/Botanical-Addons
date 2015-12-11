package ninja.shadowfox.shadowfox_botany.common.blocks.base

import cpw.mods.fml.common.Optional
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.item.ItemStack
import net.minecraft.init.Blocks
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.AxisAlignedBB
import net.minecraft.util.IIcon
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import ninja.shadowfox.shadowfox_botany.common.blocks.base.ShadowFoxBlockMod
import ninja.shadowfox.shadowfox_botany.common.blocks.tile.TileManaFlame
import ninja.shadowfox.shadowfox_botany.common.utils.helper.IconHelper
import java.util.*


class BlockManaFlame(name: String, val Tile: Class<out TileManaFlame>) : ShadowFoxBlockMod(Material.cloth) {

    override val registerInCreative = false

    init {
        this.setBlockName(name)
        val f = 0.25f
        this.setStepSound(Block.soundTypeCloth)
        this.setBlockBounds(f, f, f, 1.0f - f, 1.0f - f, 1.0f - f)
        this.setLightLevel(1.0f)
    }

    @Optional.Method(modid = "easycoloredlights")
    override fun getLightValue(world: IBlockAccess, x: Int, y: Int, z: Int): Int {
        return this.Tile.cast(world.getTileEntity(x, y, z)).getLightColor()
    }

    override fun registerBlockIcons(par1IconRegister: IIconRegister) {}

    override fun getRenderType(): Int = -1

    override fun getIcon(side: Int, meta: Int): IIcon {
        return Blocks.fire.getIcon(side, meta)
    }

    override fun isOpaqueCube(): Boolean = false
    override fun renderAsNormalBlock(): Boolean = false
    override fun hasTileEntity(metadata: Int): Boolean = true


    override fun getBlocksMovement(world: IBlockAccess?, x: Int, y: Int, z: Int): Boolean = true
    override fun getCollisionBoundingBoxFromPool(world: World?, x: Int, y: Int, z: Int): AxisAlignedBB? = null

    override fun getDrops(world: World, x: Int, y: Int, z: Int, metadata: Int, fortune: Int): ArrayList<ItemStack> {
        return ArrayList()
    }

    override fun createTileEntity(world: World?, meta: Int): TileEntity? {
        return this.Tile.newInstance()
    }
}
