package ninja.shadowfox.shadowfox_botany.common.blocks

import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.block.Block
import net.minecraft.block.IGrowable
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraft.item.ItemStack
import net.minecraft.util.AxisAlignedBB
import net.minecraft.util.IIcon
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import net.minecraft.world.gen.feature.WorldGenerator
import net.minecraftforge.common.EnumPlantType
import net.minecraftforge.common.IPlantable
import net.minecraftforge.common.util.ForgeDirection
import ninja.shadowfox.shadowfox_botany.common.core.ShadowFoxCreativeTab
import ninja.shadowfox.shadowfox_botany.common.utils.helper.IconHelper
import ninja.shadowfox.shadowfox_botany.common.world.ColoredTreeGen
import ninja.shadowfox.shadowfox_botany.common.lexicon.LexiconRegistry
import vazkii.botania.api.lexicon.ILexiconable
import vazkii.botania.api.lexicon.LexiconEntry
import java.util.*
import kotlin.properties.Delegates

public class BlockColoredSapling() : ShadowFoxBlockMod(Material.plants), IGrowable, IPlantable, ILexiconable {

    internal var icon: IIcon by Delegates.notNull()

    init {
        this.setTickRandomly(true)
        this.setBlockBounds(0.5F - 0.4F, 0.0F, 0.5F - 0.4F, 0.5F + 0.4F, 0.4F * 2.0F, 0.5F + 0.4F)
        stepSound = Block.soundTypeGrass
        this.setBlockName("irisSapling")
    }

    @SideOnly(Side.CLIENT)
    override fun getIcon(pass: Int, meta: Int): IIcon {
        return icon
    }

    override fun getCollisionBoundingBoxFromPool(world: World?, x: Int, y: Int, z: Int): AxisAlignedBB? {
        return null
    }

    override fun registerBlockIcons(par1IconRegister: IIconRegister) {
        icon = IconHelper.forBlock(par1IconRegister, this)
    }

    override fun isOpaqueCube(): Boolean { return false }
    override fun renderAsNormalBlock() : Boolean { return false }
    override fun getRenderType() : Int { return 1 }

    override fun getPlant(world: IBlockAccess?, x: Int, y: Int, z: Int): Block? {
        return this
    }

    override fun getPlantType(world: IBlockAccess?, x: Int, y: Int, z: Int): EnumPlantType? {
        return EnumPlantType.Plains
    }

    override fun getPlantMetadata(world: IBlockAccess?, x: Int, y: Int, z: Int): Int {
        return if (world != null) world.getBlockMetadata(x, y, z) else 0
    }

    override fun canPlaceBlockAt(world: World, x: Int, y: Int, z: Int): Boolean {
        return super.canPlaceBlockAt(world, x, y, z) && this.canBlockStay(world, x, y, z)
    }

    /**
     * Ticks the block if it's been scheduled
     */
    override fun updateTick(world: World, x: Int, y: Int, z: Int, random: Random) {
        if (!world.isRemote) {
            checkAndDropBlock(world, x, y, z)

            if (world.getBlockLightValue(x, y + 1, z) >= 9 && random.nextInt(7) == 0) {
                this.markOrGrowMarked(world, x, y, z, random)
            }
        }
    }

    override fun onNeighborBlockChange(world: World?, x: Int, y: Int, z: Int, block: Block) {
        super.onNeighborBlockChange(world, x, y, z, block)
        checkAndDropBlock(world, x, y, z)
    }

    fun checkAndDropBlock(world: World?, x: Int, y: Int, z: Int){
        if (world != null && !this.canBlockStay(world, x, y, z)) {
            this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0)
            world.setBlock(x, y, z, Block.getBlockById(0), 0, 2)
        }
    }

    override fun canBlockStay(world: World, x: Int, y: Int, z: Int): Boolean {
        return world.getBlock(x, y - 1, z).canSustainPlant(world, x, y - 1, z, ForgeDirection.UP, this)
    }

    public fun markOrGrowMarked(world: World?, x: Int, y: Int, z: Int, random: Random?) {
        if (world != null) {
            val l = world.getBlockMetadata(x, y, z)

            if ((l and 8) == 0) {
                world.setBlockMetadataWithNotify(x, y, z, l or 8, 4)
            } else {
                growTree(world, x, y, z, random)
            }
        }
    }

    public fun growTree(world: World?, x: Int, y: Int, z: Int, random: Random?) {
        if(world != null) {
            if (!net.minecraftforge.event.terraingen.TerrainGen.saplingGrowTree(world, random, x, y, z)) return

            val plantedOn: Block = world.getBlock(x, y - 1, z)

            if(plantedOn == ShadowFoxBlocks.coloredDirtBlock) {
                val l = world.getBlockMetadata(x, y, z)

                val obj: WorldGenerator = ColoredTreeGen(5)

                world.setBlock(x, y, z, Blocks.air, 0, 4)

                if (!obj.generate(world, random, x, y, z)) {
                    world.setBlock(x, y, z, this, l, 4)
                }
            }
        }
    }


    /**
     *  shouldFertilize
     */
    override fun func_149852_a(world: World?, random: Random?, x: Int, y: Int, z: Int): Boolean {
        if (world != null) return world.rand.nextFloat().toDouble() < 0.45
        return false
    }

    /**
     * fertilize
     */
    override fun func_149853_b(world: World?, random: Random?, x: Int, y: Int, z: Int) {
        this.markOrGrowMarked(world, x, y, z, random)
    }

    /**
     * canFertilize
     */
    override fun func_149851_a(world: World?, x: Int, y: Int, z: Int, isRemote: Boolean): Boolean {
        return true
    }

    override fun getEntry(p0: World?, p1: Int, p2: Int, p3: Int, p4: EntityPlayer?, p5: ItemStack?): LexiconEntry? {
        return LexiconRegistry.irisSapling
    }
}
