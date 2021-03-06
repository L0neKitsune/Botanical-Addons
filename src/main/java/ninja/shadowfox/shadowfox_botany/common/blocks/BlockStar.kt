package ninja.shadowfox.shadowfox_botany.common.blocks

import cpw.mods.fml.common.Optional
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.entity.item.EntityItem
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.AxisAlignedBB
import net.minecraft.util.IIcon
import net.minecraft.util.MovingObjectPosition
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import ninja.shadowfox.shadowfox_botany.common.blocks.base.BlockMod
import ninja.shadowfox.shadowfox_botany.common.blocks.tile.TileEntityStar
import ninja.shadowfox.shadowfox_botany.common.item.blocks.ItemStarPlacer
import ninja.shadowfox.shadowfox_botany.common.lexicon.LexiconRegistry
import vazkii.botania.api.lexicon.ILexiconable
import vazkii.botania.api.lexicon.LexiconEntry
import java.util.*

/**
 * @author WireSegal
 * Created at 9:23 PM on 2/6/16.
 */
class BlockStar(name: String = "star") : BlockMod(Material.cloth), ILexiconable {

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
        return (world.getTileEntity(x, y, z) as TileEntityStar).getLightColor()
    }

    override fun registerBlockIcons(par1IconRegister: IIconRegister) {
    }

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

    override fun onBlockHarvested(world: World, x: Int, y: Int, z: Int, meta: Int, player: EntityPlayer?) {
        if (!(player?.capabilities?.isCreativeMode ?: false)) {
            val te = world.getTileEntity(x, y, z)
            if (te is TileEntityStar) {
                val f = 0.5

                val color = te.getColor()
                val stack = ItemStarPlacer.colorStack(color)

                val entityitem = EntityItem(world, (x.toFloat() + f).toDouble(), (y.toFloat() + f).toDouble(), (z.toFloat() + f).toDouble(), stack)

                val f3 = 0.05f
                entityitem.motionX = (world.rand.nextGaussian().toFloat() * f3).toDouble()
                entityitem.motionY = (world.rand.nextGaussian().toFloat() * f3 + 0.2f).toDouble()
                entityitem.motionZ = (world.rand.nextGaussian().toFloat() * f3).toDouble()
                world.spawnEntityInWorld(entityitem)
            }
        }

        super.onBlockHarvested(world, x, y, z, meta, player)
    }

    override fun getPickBlock(target: MovingObjectPosition, world: World, x: Int, y: Int, z: Int, player: EntityPlayer): ItemStack? {
        val te = world.getTileEntity(x, y, z)
        if (te is TileEntityStar) {
            return ItemStarPlacer.colorStack(te.getColor())
        }
        return super.getPickBlock(target, world, x, y, z, player)
    }

    override fun createTileEntity(world: World?, meta: Int): TileEntity? {
        return TileEntityStar()
    }

    override fun getEntry(p0: World?, p1: Int, p2: Int, p3: Int, p4: EntityPlayer?, p5: ItemStack?): LexiconEntry? {
        return LexiconRegistry.frozenStar
    }
}
