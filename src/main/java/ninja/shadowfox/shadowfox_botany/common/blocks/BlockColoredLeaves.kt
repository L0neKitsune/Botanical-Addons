package ninja.shadowfox.shadowfox_botany.common.blocks

import cpw.mods.fml.common.registry.GameRegistry
import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.block.Block
import net.minecraft.block.BlockLeaves
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.passive.EntitySheep
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.IIcon
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import net.minecraftforge.common.IShearable
import ninja.shadowfox.shadowfox_botany.common.core.ShadowFoxCreativeTab
import ninja.shadowfox.shadowfox_botany.common.item.blocks.ShadowFoxMetaItemBlock
import ninja.shadowfox.shadowfox_botany.common.utils.helper.IconHelper
import java.awt.Color
import java.util.*

public class BlockColoredLeaves() : BlockLeaves(), IShearable {

    val TYPES: Int = 16
    internal var field_150128_a: IntArray? = null
    protected var icons: Array<IIcon> = emptyArray()

    init {
        setHardness(0.2f)
        setCreativeTab(ShadowFoxCreativeTab)
        setLightOpacity(1)
        setStepSound(Block.soundTypeGrass)
        setBlockName("irisLeaves")

    }

    override fun setBlockName(par1Str: String): Block {
        register(par1Str)
        return super.setBlockName(par1Str)
    }

    override fun quantityDropped(p_149745_1_: Random): Int {
        return if (p_149745_1_.nextInt(20) == 0) 1 else 0
    }

    internal fun register(name: String) {
        GameRegistry.registerBlock(this, ShadowFoxMetaItemBlock::class.java, name)
    }

    @SideOnly(Side.CLIENT)
    override fun getBlockColor(): Int {
        return 0xFFFFFF
    }

    /**
     * Returns the color this block should be rendered. Used by leaves.
     */
    @SideOnly(Side.CLIENT)
    override fun getRenderColor(p_149741_1_: Int): Int {
        if (p_149741_1_ >= EntitySheep.fleeceColorTable.size)
            return 0xFFFFFF;

        var color = EntitySheep.fleeceColorTable[p_149741_1_];
        return Color(color[0], color[1], color[2]).rgb;
    }

    @SideOnly(Side.CLIENT)
    override fun colorMultiplier(p_149720_1_: IBlockAccess?, p_149720_2_: Int, p_149720_3_: Int, p_149720_4_: Int): Int {
        val meta = p_149720_1_!!.getBlockMetadata(p_149720_2_, p_149720_3_, p_149720_4_)

        if (meta >= EntitySheep.fleeceColorTable.size)
            return 0xFFFFFF;

        var color = EntitySheep.fleeceColorTable[meta];
        return Color(color[0], color[1], color[2]).rgb;
    }

    @SideOnly(Side.CLIENT)
    override fun getIcon(side: Int, meta: Int): IIcon {
        this.setGraphicsLevel(Minecraft.getMinecraft().gameSettings.fancyGraphics);
        return icons[if (this.field_150121_P) 0 else 1]
    }

    override fun getSubBlocks(item: Item?, tab: CreativeTabs?, list: MutableList<Any?>?) {
        if (list != null && item != null)
            for (i in 0..(TYPES - 1)) {
                list.add(ItemStack(item, 1, i));
            }
    }

    private fun removeLeaves(p_150126_1_: World, p_150126_2_: Int, p_150126_3_: Int, p_150126_4_: Int) {
        this.dropBlockAsItem(p_150126_1_, p_150126_2_, p_150126_3_, p_150126_4_, p_150126_1_.getBlockMetadata(p_150126_2_, p_150126_3_, p_150126_4_), 0)
        p_150126_1_.setBlockToAir(p_150126_2_, p_150126_3_, p_150126_4_)
    }

    override fun registerBlockIcons(par1IconRegister: IIconRegister) {
        icons = Array(2, { i -> IconHelper.forBlock(par1IconRegister, this, if (i == 0) "" else "_opaque") })
    }

    override fun getItemDropped(p_149650_1_: Int, p_149650_2_: Random, p_149650_3_: Int): Item {
        return Item.getItemFromBlock(ShadowFoxBlocks.irisSapling);
    }

    override fun isLeaves(world: IBlockAccess, x: Int, y: Int, z: Int): Boolean {
        return true
    }

    override fun isOpaqueCube(): Boolean {
        return false
    }

    override fun func_150125_e(): Array<String> {
        return arrayOf("ColoredLeaves")
    }

    override fun isShearable(item: ItemStack?, world: IBlockAccess?, x: Int, y: Int, z: Int): Boolean {
        return true
    }

    override fun onSheared(item: ItemStack?, world: IBlockAccess?, x: Int, y: Int, z: Int, fortune: Int): ArrayList<ItemStack>? {
        val ret = ArrayList<ItemStack>()
        ret.add(ItemStack(this, 1, world!!.getBlockMetadata(x, y, z)))
        return ret
    }

    override fun beginLeavesDecay(world: World?, x: Int, y: Int, z: Int) {

    }

    override fun updateTick(p_149674_1_: World?, p_149674_2_: Int, p_149674_3_: Int, p_149674_4_: Int, p_149674_5_: Random?) {
        if (!p_149674_1_!!.isRemote) {
            val b0 = 4
            val i1 = b0 + 1
            val b1 = 32
            val j1 = b1 * b1
            val k1 = b1 / 2

            if (this.field_150128_a == null) {
                this.field_150128_a = IntArray(b1.toInt() * b1.toInt() * b1.toInt())
            }

            var l1: Int

            if (p_149674_1_.checkChunksExist(p_149674_2_ - i1, p_149674_3_ - i1, p_149674_4_ - i1, p_149674_2_ + i1, p_149674_3_ + i1, p_149674_4_ + i1)) {
                var i2: Int
                var j2: Int

                l1 = -b0
                while (l1 <= b0) {
                    i2 = -b0
                    while (i2 <= b0) {
                        j2 = -b0
                        while (j2 <= b0) {
                            val block = p_149674_1_.getBlock(p_149674_2_ + l1, p_149674_3_ + i2, p_149674_4_ + j2)

                            if (!block.canSustainLeaves(p_149674_1_, p_149674_2_ + l1, p_149674_3_ + i2, p_149674_4_ + j2)) {
                                if (block.isLeaves(p_149674_1_, p_149674_2_ + l1, p_149674_3_ + i2, p_149674_4_ + j2)) {
                                    field_150128_a!![(l1 + k1) * j1 + (i2 + k1) * b1 + j2 + k1] = -2
                                } else {
                                    field_150128_a!![(l1 + k1) * j1 + (i2 + k1) * b1 + j2 + k1] = -1
                                }
                            } else {
                                field_150128_a!![(l1 + k1) * j1 + (i2 + k1) * b1 + j2 + k1] = 0
                            }
                            ++j2
                        }
                        ++i2
                    }
                    ++l1
                }

                l1 = 1
                while (l1 <= 4) {
                    i2 = -b0
                    while (i2 <= b0) {
                        j2 = -b0
                        while (j2 <= b0) {
                            for (k2 in -b0..b0) {
                                if (field_150128_a!![(i2 + k1) * j1 + (j2 + k1) * b1 + k2 + k1] == l1 - 1) {
                                    if (field_150128_a!![(i2 + k1 - 1) * j1 + (j2 + k1) * b1 + k2 + k1] == -2) {
                                        field_150128_a!![(i2 + k1 - 1) * j1 + (j2 + k1) * b1 + k2 + k1] = l1
                                    }

                                    if (field_150128_a!![(i2 + k1 + 1) * j1 + (j2 + k1) * b1 + k2 + k1] == -2) {
                                        field_150128_a!![(i2 + k1 + 1) * j1 + (j2 + k1) * b1 + k2 + k1] = l1
                                    }

                                    if (field_150128_a!![(i2 + k1) * j1 + (j2 + k1 - 1) * b1 + k2 + k1] == -2) {
                                        field_150128_a!![(i2 + k1) * j1 + (j2 + k1 - 1) * b1 + k2 + k1] = l1
                                    }

                                    if (field_150128_a!![(i2 + k1) * j1 + (j2 + k1 + 1) * b1 + k2 + k1] == -2) {
                                        field_150128_a!![(i2 + k1) * j1 + (j2 + k1 + 1) * b1 + k2 + k1] = l1
                                    }

                                    if (field_150128_a!![(i2 + k1) * j1 + (j2 + k1) * b1 + (k2 + k1 - 1)] == -2) {
                                        field_150128_a!![(i2 + k1) * j1 + (j2 + k1) * b1 + (k2 + k1 - 1)] = l1
                                    }

                                    if (field_150128_a!![(i2 + k1) * j1 + (j2 + k1) * b1 + k2 + k1 + 1] == -2) {
                                        field_150128_a!![(i2 + k1) * j1 + (j2 + k1) * b1 + k2 + k1 + 1] = l1
                                    }
                                }
                            }
                            ++j2
                        }
                        ++i2
                    }
                    ++l1
                }
            }

            l1 = field_150128_a!![k1 * j1 + k1 * b1 + k1]

            if (l1 >= 0) { }
            else removeLeaves(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_)

        }
    }


    override fun dropBlockAsItemWithChance(p_149690_1_: World, p_149690_2_: Int, p_149690_3_: Int, p_149690_4_: Int, p_149690_5_: Int,
                                           p_149690_6_: Float, p_149690_7_: Int) {
        super.dropBlockAsItemWithChance(p_149690_1_, p_149690_2_, p_149690_3_, p_149690_4_, p_149690_5_, 1.0f, p_149690_7_);
    }

    override fun getDrops(world: World, x: Int, y: Int, z: Int, metadata: Int, fortune: Int): ArrayList<ItemStack> {
        var ret: ArrayList<ItemStack> = ArrayList()

        var chance: Int = 20

        if (fortune > 0) {
            chance -= 2 shl fortune;
            if (chance < 10) chance = 10;
        }

        if (world.rand.nextInt(chance) == 0)
            ret.add(ItemStack(this.getItemDropped(metadata, world.rand, fortune), 1, this.damageDropped(metadata)));

        chance = 200;
        if (fortune > 0) {
            chance -= 10 shl fortune;
            if (chance < 40) chance = 40;
        }

        this.captureDrops(true);
        this.func_150124_c(world, x, y, z, metadata, chance); // Dammet mojang
        ret.addAll(this.captureDrops(false));
        return ret;
    }
}