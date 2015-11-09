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
        this.setBlockBounds(0.5F - 0.4F, 0.0F, 0.5F - 0.4F, 0.5F + 0.4F, 0.4F * 2.0F, 0.5F + 0.4F);
        this.setBlockName("irisSapling")
    }

    @SideOnly(Side.CLIENT)
    override fun getIcon(p_149691_1_: Int, meta: Int): IIcon
    {
        return icon
    }

    override fun getCollisionBoundingBoxFromPool(p_149668_1_: World?, p_149668_2_: Int, p_149668_3_: Int, p_149668_4_: Int): AxisAlignedBB?
    {
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

    override fun canPlaceBlockAt(p_149742_1_: World, p_149742_2_: Int, p_149742_3_: Int, p_149742_4_: Int): Boolean
    {
        return super.canPlaceBlockAt(p_149742_1_, p_149742_2_, p_149742_3_, p_149742_4_) && this.canBlockStay(p_149742_1_, p_149742_2_, p_149742_3_, p_149742_4_);
    }

    /**
     * Ticks the block if it's been scheduled
     */
    override fun updateTick(p_149674_1_: World, p_149674_2_: Int, p_149674_3_: Int, p_149674_4_: Int, p_149674_5_: Random)
    {
        if (!p_149674_1_.isRemote)
        {
            checkAndDropBlock(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_);

            if (p_149674_1_.getBlockLightValue(p_149674_2_, p_149674_3_ + 1, p_149674_4_) >= 9 && p_149674_5_.nextInt(7) == 0)
            {
                this.markOrGrowMarked(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_, p_149674_5_);
            }
        }
    }

    override fun onNeighborBlockChange(p_149695_1_: World?, p_149695_2_: Int, p_149695_3_: Int, p_149695_4_: Int, p_149695_5_: Block)
    {
        super.onNeighborBlockChange(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, p_149695_5_);
        checkAndDropBlock(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_);
    }

    fun checkAndDropBlock(p_149695_1_: World?, p_149695_2_: Int, p_149695_3_: Int, p_149695_4_: Int){
        if (p_149695_1_ != null && !this.canBlockStay(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_)) {
            this.dropBlockAsItem(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, p_149695_1_.getBlockMetadata(p_149695_2_, p_149695_3_, p_149695_4_), 0)
            p_149695_1_.setBlock(p_149695_2_, p_149695_3_, p_149695_4_, Block.getBlockById(0), 0, 2)
        }
    }

    override fun canBlockStay(p_149718_1_: World, p_149718_2_: Int, p_149718_3_: Int, p_149718_4_: Int): Boolean
    {
        return p_149718_1_.getBlock(p_149718_2_, p_149718_3_ - 1, p_149718_4_).canSustainPlant(p_149718_1_, p_149718_2_, p_149718_3_ - 1, p_149718_4_, ForgeDirection.UP, this);
    }

    public fun markOrGrowMarked(p_149879_1_: World?, p_149879_2_: Int, p_149879_3_: Int, p_149879_4_: Int, p_149879_5_: Random?)
    {
        if (p_149879_1_ != null) {
            val l = p_149879_1_.getBlockMetadata(p_149879_2_, p_149879_3_, p_149879_4_);

            if ((l and 8) == 0) {
                p_149879_1_.setBlockMetadataWithNotify(p_149879_2_, p_149879_3_, p_149879_4_, l or 8, 4);
            } else {
                growTree(p_149879_1_, p_149879_2_, p_149879_3_, p_149879_4_, p_149879_5_);
            }
        }
    }

    public fun growTree(world: World?, x: Int, y: Int, z: Int, random: Random?)
    {
        if(world != null) {
            if (!net.minecraftforge.event.terraingen.TerrainGen.saplingGrowTree(world, random, x, y, z)) return

            val plantedOn: Block = world.getBlock(x, y - 1, z)

            if(plantedOn == ShadowFoxBlocks.coloredDirtBlock) {
                val l = world.getBlockMetadata(x, y, z)

                val obj: WorldGenerator = ColoredTreeGen(5)

                world.setBlock(x, y, z, Blocks.air, 0, 4);

                if (!obj.generate(world, random, x, y, z)) {
                    world.setBlock(x, y, z, this, l, 4);
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
    override fun func_149853_b(world: World?, p_149853_2_: Random?, p_149853_3_: Int, p_149853_4_: Int, p_149853_5_: Int) {
        this.markOrGrowMarked(world, p_149853_3_, p_149853_4_, p_149853_5_, p_149853_2_)
    }

    /**
     * canFertilize
     */
    override fun func_149851_a(p_149851_1_: World?, p_149851_2_: Int, p_149851_3_: Int, p_149851_4_: Int, p_149851_5_: Boolean): Boolean {
        return true
    }

    override fun getEntry(p0: World?, p1: Int, p2: Int, p3: Int, p4: EntityPlayer?, p5: ItemStack?): LexiconEntry? {
        return LexiconRegistry.irisSapling
    }
}

