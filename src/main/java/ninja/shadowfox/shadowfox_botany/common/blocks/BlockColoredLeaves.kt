package ninja.shadowfox.shadowfox_botany.common.blocks

import cpw.mods.fml.common.registry.GameRegistry
import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.IIcon
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import net.minecraftforge.common.IShearable
import ninja.shadowfox.shadowfox_botany.common.item.blocks.ShadowFoxMetaItemBlock
import ninja.shadowfox.shadowfox_botany.common.utils.helper.IconHelper
import java.util.*

/**
 * Created by l0nekitsune on 10/23/15.
 */
public class BlockColoredLeaves(): ShadowFoxBlockMod(Material.leaves) , IShearable {

    protected var field_150121_P: Boolean = false
    internal var field_150128_a: IntArray? = null
    val TYPES : Int = 16
    protected var icons : Array<Array<IIcon>> = Array(2, {i -> emptyArray<IIcon>()})

    @SideOnly(Side.CLIENT) var field_150127_b: Int = 0

    init {
        setHardness(0.2f)
        setLightOpacity(1)
        setStepSound(Block.soundTypeGrass)
        setTickRandomly(true)
        setBlockName("irisLeaves")

    }

    override fun shouldRegisterInNameSet(): Boolean {
        return false
    }

    override fun setBlockName(par1Str: String): Block {
        register(par1Str)
        return super.setBlockName(par1Str)
    }

    override fun quantityDropped(p_149745_1_: Random): Int
    {
        return if (p_149745_1_.nextInt(20) == 0) 1 else 0
    }

    internal fun register(name: String) {
        GameRegistry.registerBlock(this, ShadowFoxMetaItemBlock::class.java, name)
    }

    @SideOnly(Side.CLIENT)
    override fun getIcon(side : Int, meta : Int) : IIcon {
        return icons[field_150127_b][Math.min(TYPES - 1, meta)]
    }

    override fun getSubBlocks(item : Item?, tab : CreativeTabs?, list : MutableList<Any?>?) {
        if (list != null && item != null)
            for (i in 0..(TYPES - 1)) {
                list.add(ItemStack(item, 1, i));
            }
    }


    @SideOnly(Side.CLIENT)
    fun setGraphicsLevel(p_150122_1_: Boolean)
    {
        this.field_150121_P = p_150122_1_
        this.field_150127_b = if (p_150122_1_) 0 else 1
    }

    override fun registerBlockIcons(par1IconRegister: IIconRegister) {
        for (i in 0..1)
        {
            icons[i] = Array(TYPES, {j -> IconHelper.forBlock(par1IconRegister, this, "$j${if(i == 0)"_opaque" else ""}")})
        }
    }

    override fun getItemDropped(p_149650_1_: Int, p_149650_2_: Random, p_149650_3_: Int): Item
    {
        return Item.getItemFromBlock(ShadowFoxBlocks.irisSapling);
    }

    fun removeLeaves(p_150126_1_: World, p_150126_2_: Int, p_150126_3_: Int, p_150126_4_: Int)
    {
        dropBlockAsItem(p_150126_1_, p_150126_2_, p_150126_3_, p_150126_4_, p_150126_1_.getBlockMetadata(p_150126_2_, p_150126_3_, p_150126_4_), 0);
        p_150126_1_.setBlockToAir(p_150126_2_, p_150126_3_, p_150126_4_);
    }

    override fun isLeaves(world: IBlockAccess, x: Int, y: Int, z: Int): Boolean { return true }
    override fun isOpaqueCube() : Boolean { return false }

//    override fun updateTick(p_149674_1_: World?, p_149674_2_: Int, p_149674_3_: Int, p_149674_4_: Int, p_149674_5_: Random?) {
//        if (!p_149674_1_!!.isRemote) {
//            val l = p_149674_1_.getBlockMetadata(p_149674_2_, p_149674_3_, p_149674_4_)
//
//            if ((l and 8) != 0 && (l and 4) == 0) {
//                val b0 = 4
//                val i1 = b0 + 1
//                val b1 = 32
//                val j1 = b1 * b1
//                val k1 = b1 / 2
//
//                if (this.field_150128_a == null) {
//                    this.field_150128_a = IntArray(b1.toInt() * b1.toInt() * b1.toInt())
//                }
//
//                var l1: Int
//
//                if (p_149674_1_.checkChunksExist(p_149674_2_ - i1, p_149674_3_ - i1, p_149674_4_ - i1, p_149674_2_ + i1, p_149674_3_ + i1, p_149674_4_ + i1)) {
//                    var i2: Int
//                    var j2: Int
//
//                    l1 = -b0
//                    while (l1 <= b0) {
//                        i2 = -b0
//                        while (i2 <= b0) {
//                            j2 = -b0
//                            while (j2 <= b0) {
//                                val block = p_149674_1_.getBlock(p_149674_2_ + l1, p_149674_3_ + i2, p_149674_4_ + j2)
//
//                                if (!block.canSustainLeaves(p_149674_1_, p_149674_2_ + l1, p_149674_3_ + i2, p_149674_4_ + j2)) {
//                                    if (block.isLeaves(p_149674_1_, p_149674_2_ + l1, p_149674_3_ + i2, p_149674_4_ + j2)) {
//                                        this.field_150128_a!![(l1 + k1) * j1 + (i2 + k1) * b1 + j2 + k1] = -2
//                                    } else {
//                                        this.field_150128_a!![(l1 + k1) * j1 + (i2 + k1) * b1 + j2 + k1] = -1
//                                    }
//                                } else {
//                                    this.field_150128_a!![(l1 + k1) * j1 + (i2 + k1) * b1 + j2 + k1] = 0
//                                }
//                                ++j2
//                            }
//                            ++i2
//                        }
//                        ++l1
//                    }
//
//                    l1 = 1
//                    while (l1 <= 4) {
//                        i2 = -b0
//                        while (i2 <= b0) {
//                            j2 = -b0
//                            while (j2 <= b0) {
//                                for (k2 in -b0..b0) {
//                                    if (this.field_150128_a!![(i2 + k1) * j1 + (j2 + k1) * b1 + k2 + k1] == l1 - 1) {
//                                        if (this.field_150128_a!![(i2 + k1 - 1) * j1 + (j2 + k1) * b1 + k2 + k1] == -2) {
//                                            this.field_150128_a!![(i2 + k1 - 1) * j1 + (j2 + k1) * b1 + k2 + k1] = l1
//                                        }
//
//                                        if (this.field_150128_a!![(i2 + k1 + 1) * j1 + (j2 + k1) * b1 + k2 + k1] == -2) {
//                                            this.field_150128_a!![(i2 + k1 + 1) * j1 + (j2 + k1) * b1 + k2 + k1] = l1
//                                        }
//
//                                        if (this.field_150128_a!![(i2 + k1) * j1 + (j2 + k1 - 1) * b1 + k2 + k1] == -2) {
//                                            this.field_150128_a!![(i2 + k1) * j1 + (j2 + k1 - 1) * b1 + k2 + k1] = l1
//                                        }
//
//                                        if (this.field_150128_a!![(i2 + k1) * j1 + (j2 + k1 + 1) * b1 + k2 + k1] == -2) {
//                                            this.field_150128_a!![(i2 + k1) * j1 + (j2 + k1 + 1) * b1 + k2 + k1] = l1
//                                        }
//
//                                        if (this.field_150128_a!![(i2 + k1) * j1 + (j2 + k1) * b1 + (k2 + k1 - 1)] == -2) {
//                                            this.field_150128_a!![(i2 + k1) * j1 + (j2 + k1) * b1 + (k2 + k1 - 1)] = l1
//                                        }
//
//                                        if (this.field_150128_a!![(i2 + k1) * j1 + (j2 + k1) * b1 + k2 + k1 + 1] == -2) {
//                                            this.field_150128_a!![(i2 + k1) * j1 + (j2 + k1) * b1 + k2 + k1 + 1] = l1
//                                        }
//                                    }
//                                }
//                                ++j2
//                            }
//                            ++i2
//                        }
//                        ++l1
//                    }
//                }
//
//                this.removeLeaves(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_)
//            }
//        }
//    }

    /**
     * A randomly called display update to be able to add particles or other items for display
     */
    @SideOnly(Side.CLIENT)
    override fun randomDisplayTick(p_149734_1_: World?, p_149734_2_: Int, p_149734_3_: Int, p_149734_4_: Int, p_149734_5_: Random?) {
        if (p_149734_1_!!.canLightningStrikeAt(p_149734_2_, p_149734_3_ + 1, p_149734_4_) && !World.doesBlockHaveSolidTopSurface(p_149734_1_, p_149734_2_, p_149734_3_ - 1, p_149734_4_) && p_149734_5_!!.nextInt(15) == 1) {
            val d0 = (p_149734_2_.toFloat() + p_149734_5_!!.nextFloat()).toDouble()
            val d1 = p_149734_3_.toDouble() - 0.05
            val d2 = (p_149734_4_.toFloat() + p_149734_5_.nextFloat()).toDouble()
            p_149734_1_.spawnParticle("dripWater", d0, d1, d2, 0.0, 0.0, 0.0)
        }
    }

    override fun breakBlock(p_149749_1_: World, p_149749_2_: Int, p_149749_3_: Int, p_149749_4_: Int, p_149749_5_: Block?, p_149749_6_: Int) {
        val b0 = 1
        val i1 = b0 + 1

        if (p_149749_1_.checkChunksExist(p_149749_2_ - i1, p_149749_3_ - i1, p_149749_4_ - i1, p_149749_2_ + i1, p_149749_3_ + i1, p_149749_4_ + i1)) {
            for (j1 in -b0..b0) {
                for (k1 in -b0..b0) {
                    for (l1 in -b0..b0) {
                        val block = p_149749_1_.getBlock(p_149749_2_ + j1, p_149749_3_ + k1, p_149749_4_ + l1)
                        if (block.isLeaves(p_149749_1_, p_149749_2_ + j1, p_149749_3_ + k1, p_149749_4_ + l1)) {
                            block.beginLeavesDecay(p_149749_1_, p_149749_2_ + j1, p_149749_3_ + k1, p_149749_4_ + l1)
                        }
                    }
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    override fun shouldSideBeRendered(p_149646_1_: IBlockAccess, p_149646_2_: Int, p_149646_3_: Int, p_149646_4_: Int, p_149646_5_: Int) : Boolean
    {
        val block: Block = p_149646_1_.getBlock(p_149646_2_, p_149646_3_, p_149646_4_);
        return if (!this.field_150121_P && block == this) false else super.shouldSideBeRendered(p_149646_1_, p_149646_2_, p_149646_3_, p_149646_4_, p_149646_5_);
    }

    override fun isShearable(item: ItemStack?, world: IBlockAccess?, x: Int, y: Int, z: Int): Boolean {
        throw UnsupportedOperationException()
    }

    override fun onSheared(item: ItemStack?, world: IBlockAccess?, x: Int, y: Int, z: Int, fortune: Int): ArrayList<ItemStack>? {
        val ret = ArrayList<ItemStack>()
        ret.add(ItemStack(this, 1, world!!.getBlockMetadata(x, y, z)))
        return ret
    }

    override fun dropBlockAsItemWithChance(p_149690_1_: World, p_149690_2_: Int, p_149690_3_: Int, p_149690_4_: Int, p_149690_5_: Int,
                                           p_149690_6_: Float, p_149690_7_: Int)
    {
        super.dropBlockAsItemWithChance(p_149690_1_, p_149690_2_, p_149690_3_, p_149690_4_, p_149690_5_, 1.0f, p_149690_7_);
    }

    protected fun func_150124_c(p_150124_1_: World?, p_150124_2_: Int, p_150124_3_: Int, p_150124_4_: Int, p_150124_5_: Int, p_150124_6_: Int) {}

    override fun harvestBlock(p_149636_1_: World, p_149636_2_: EntityPlayer, p_149636_3_: Int, p_149636_4_: Int, p_149636_5_: Int, p_149636_6_: Int)
    {
        run {
            super.harvestBlock(p_149636_1_, p_149636_2_, p_149636_3_, p_149636_4_, p_149636_5_, p_149636_6_);
        }
    }

    override fun getDrops(world: World, x: Int, y: Int, z: Int, metadata: Int, fortune: Int): ArrayList<ItemStack>
    {
        var ret :ArrayList<ItemStack> = ArrayList()

        var chance : Int = 20

        if (fortune > 0)
        {
            chance -= 2 shl fortune;
            if (chance < 10) chance = 10;
        }

        if (world.rand.nextInt(chance) == 0)
            ret.add(ItemStack(this.getItemDropped(metadata, world.rand, fortune), 1, this.damageDropped(metadata)));

        chance = 200;
        if (fortune > 0)
        {
            chance -= 10 shl fortune;
            if (chance < 40) chance = 40;
        }

        this.captureDrops(true);
        this.func_150124_c(world, x, y, z, metadata, chance); // Dammet mojang
        ret.addAll(this.captureDrops(false));
        return ret;
    }
}