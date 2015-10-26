package ninja.shadowfox.shadowfox_botany.common.blocks

import cpw.mods.fml.common.registry.GameRegistry
import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.passive.EntitySheep
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.IIcon
import net.minecraft.util.MovingObjectPosition
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import ninja.shadowfox.shadowfox_botany.common.item.blocks.ShadowFoxMetaItemBlock
import ninja.shadowfox.shadowfox_botany.common.utils.helper.IconHelper
import java.awt.Color
import java.util.*


class BlockColoredWood() : ShadowFoxBlockMod(Material.wood)  {

    private val name = "irisWood"
    private val TYPES = 16
    protected var icons : Array<IIcon> = emptyArray()

    init {
        blockHardness = 2F
        setLightLevel(0f)
        stepSound = Block.soundTypeWood

        setBlockName(this.name)
    }

    override fun shouldRegisterInNameSet(): Boolean {
        return false
    }

    override fun setBlockName(par1Str: String): Block {
        register(par1Str)
        return super.setBlockName(par1Str)
    }

    override fun quantityDropped(p_149745_1_: Random): Int { return 1 }

    override fun getItemDropped(p_149650_1_: Int, p_149650_2_: Random, p_149650_3_: Int): Item
    {
        return Item.getItemFromBlock(this);
    }

    override fun breakBlock(p_149749_1_: World, p_149749_2_: Int, p_149749_3_: Int, p_149749_4_: Int, p_149749_5_: Block, p_149749_6_: Int)
    {
        var b0: Byte = 4;
        var i1: Int = b0 + 1;

        if (p_149749_1_.checkChunksExist(p_149749_2_ - i1, p_149749_3_ - i1, p_149749_4_ - i1, p_149749_2_ + i1, p_149749_3_ + i1, p_149749_4_ + i1))
        {
            for (j1 in -b0..b0) for (k1 in -b0..b0)
                for (l1 in -b0..b0)
                {
                    var block: Block = p_149749_1_.getBlock(p_149749_2_ + j1, p_149749_3_ + k1, p_149749_4_ + l1);
                    if (block.isLeaves(p_149749_1_, p_149749_2_ + j1, p_149749_3_ + k1, p_149749_4_ + l1))
                    {
                        block.beginLeavesDecay(p_149749_1_, p_149749_2_ + j1, p_149749_3_ + k1, p_149749_4_ + l1);
                    }
                }
        }
    }

    @SideOnly(Side.CLIENT)
    override fun getIcon(side: Int, meta: Int): IIcon
    {
        val k = meta and 0x0000f0
        val l = meta and 0x00000f
        return if (k == 0 && (side == 1 || side == 0)) this.getTopIcon(l) else (if (k == 32 && (side == 5 || side == 4)) this.getTopIcon(l) else (if (k == 16 && (side == 2 || side == 3)) this.getTopIcon(l) else this.getSideIcon(l)))

    }

    override fun damageDropped(p_149692_1_: Int): Int {
        return p_149692_1_ and 0x00000F
    }

    override fun getRenderType(): Int {
        return 39
    }

    override fun createStackedBlock(p_149644_1_: Int): ItemStack {
        return ItemStack(Item.getItemFromBlock(this), 1, p_149644_1_ and 0x00000F)
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

    override fun onBlockPlaced(world: World, x: Int, y: Int, z: Int, side: Int, hitX: Float, hitY: Float, hitZ: Float, meta: Int): Int
    {
        val j1 = meta and 0x00000F
        var b0: Int = 0x0000f0

        when (side) {
            0, 1 -> b0 = 0x000000
            2, 3 -> b0 = 0x000020
            4, 5 -> b0 = 0x000010
        }

        return j1 or b0
    }

    @SideOnly(Side.CLIENT)
    fun getSideIcon(color: Int): IIcon
    {
        return icons[1]
    }

    @SideOnly(Side.CLIENT)
    fun getTopIcon(color: Int): IIcon
    {
        return icons[0]
    }

    override fun canSustainLeaves(world: IBlockAccess, x: Int, y: Int, z: Int): Boolean { return true }
    override fun isWood(world: IBlockAccess, x: Int, y: Int, z: Int): Boolean { return true }

    internal fun register(name: String) {
        GameRegistry.registerBlock(this, ShadowFoxMetaItemBlock::class.java, name)
    }

    override fun getPickBlock(target: MovingObjectPosition?, world: World, x: Int, y: Int, z: Int): ItemStack {
        val meta = world.getBlockMetadata(x, y, z)
        return ItemStack(this, 1, meta)
    }

    override fun registerBlockIcons(par1IconRegister: IIconRegister) {
        icons = arrayOf(IconHelper.forBlock(par1IconRegister, this), IconHelper.forBlock(par1IconRegister, this, "_top"))

    }

    override fun getSubBlocks(item : Item?, tab : CreativeTabs?, list : MutableList<Any?>?) {
        if (list != null && item != null)
            for (i in 0..(TYPES - 1)) {
                list.add(ItemStack(item, 1, i));
            }
    }
}