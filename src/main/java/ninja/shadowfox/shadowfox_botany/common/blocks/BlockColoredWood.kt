package ninja.shadowfox.shadowfox_botany.common.blocks

import cpw.mods.fml.common.registry.GameRegistry
import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.block.Block
import net.minecraft.block.BlockRotatedPillar
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.passive.EntitySheep
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.IIcon
import net.minecraft.util.MovingObjectPosition
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import ninja.shadowfox.shadowfox_botany.common.item.blocks.ShadowFoxLogItemBlock0
import ninja.shadowfox.shadowfox_botany.common.item.blocks.ShadowFoxLogItemBlock1
import ninja.shadowfox.shadowfox_botany.common.item.blocks.ShadowFoxLogItemBlock2
import ninja.shadowfox.shadowfox_botany.common.item.blocks.ShadowFoxLogItemBlock3
import ninja.shadowfox.shadowfox_botany.common.utils.helper.IconHelper
import ninja.shadowfox.shadowfox_botany.common.lexicon.LexiconRegistry
import vazkii.botania.api.lexicon.ILexiconable
import vazkii.botania.api.lexicon.LexiconEntry
import java.awt.Color
import java.util.*


class BlockColoredWood(val colorSet: Int) : ShadowFoxBlockMod(Material.wood), ILexiconable  {

    private val name = "irisWood${colorSet}"
    private val TYPES = 4
    protected var icons : Array<IIcon> = emptyArray()

    init {
        blockHardness = 2F
        setLightLevel(0f)
        stepSound = Block.soundTypeWood

        setBlockName(name)
    }

    override fun shouldRegisterInNameSet(): Boolean {
        return false
    }

    override fun setBlockName(par1Str: String): Block {
        if (colorSet == 0) {
            GameRegistry.registerBlock(this, ShadowFoxLogItemBlock0::class.java, par1Str)
        } else if (colorSet == 1) {
            GameRegistry.registerBlock(this, ShadowFoxLogItemBlock1::class.java, par1Str)
        } else if (colorSet == 2) {
            GameRegistry.registerBlock(this, ShadowFoxLogItemBlock2::class.java, par1Str)
        } else if (colorSet == 3) {
            GameRegistry.registerBlock(this, ShadowFoxLogItemBlock3::class.java, par1Str)
        }
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
        val k = meta and 12
        return if (k == 0 && (side == 1 || side == 0)) this.getTopIcon() else (if (k == 4 && (side == 5 || side == 4)) this.getTopIcon() else (if (k == 8 && (side == 2 || side == 3)) this.getTopIcon() else this.getSideIcon()))
    }

    override fun damageDropped(p_149692_1_: Int): Int {
        return p_149692_1_ and 3
    }

    override fun getRenderType(): Int {
        return 31
    }

    override fun createStackedBlock(p_149644_1_: Int): ItemStack {
        return ItemStack(Item.getItemFromBlock(this), 1, p_149644_1_ and 3)
    }

    @SideOnly(Side.CLIENT)
    override fun getBlockColor(): Int {
        return 0xFFFFFF
    }

    @SideOnly(Side.CLIENT)
    fun colorMeta(meta: Int): Int {
        return meta + (colorSet * 4)
    }

    /**
     * Returns the color this block should be rendered. Used by leaves.
     */
    @SideOnly(Side.CLIENT)
    override fun getRenderColor(meta: Int): Int {
        if (colorMeta(meta and 3) >= EntitySheep.fleeceColorTable.size)
            return 0xFFFFFF;

        var color = EntitySheep.fleeceColorTable[colorMeta(meta and 3)];
        return Color(color[0], color[1], color[2]).rgb;
    }

    @SideOnly(Side.CLIENT)
    override fun colorMultiplier(p_149720_1_: IBlockAccess?, p_149720_2_: Int, p_149720_3_: Int, p_149720_4_: Int): Int {
        val meta = p_149720_1_!!.getBlockMetadata(p_149720_2_, p_149720_3_, p_149720_4_)

        if (colorMeta(meta and 3) >= EntitySheep.fleeceColorTable.size)
            return 0xFFFFFF;

        var color = EntitySheep.fleeceColorTable[colorMeta(meta and 3)];
        return Color(color[0], color[1], color[2]).rgb;
    }

    override fun onBlockPlaced(world: World, x: Int, y: Int, z: Int, side: Int, hitX: Float, hitY: Float, hitZ: Float, meta: Int): Int
    {
        val j1 = meta and 3
        var b0: Int = 0

        when (side) {
            0, 1 -> b0 = 0
            2, 3 -> b0 = 8
            4, 5 -> b0 = 4
        }

        return j1 or b0
    }

    @SideOnly(Side.CLIENT)
    fun getSideIcon(): IIcon
    {
        return icons[0]
    }

    @SideOnly(Side.CLIENT)
    fun getTopIcon(): IIcon
    {
        return icons[1]
    }

    override fun canSustainLeaves(world: IBlockAccess, x: Int, y: Int, z: Int): Boolean { return true }
    override fun isWood(world: IBlockAccess, x: Int, y: Int, z: Int): Boolean { return true }

    override fun getPickBlock(target: MovingObjectPosition?, world: World, x: Int, y: Int, z: Int): ItemStack {
        val meta = world.getBlockMetadata(x, y, z)
        return ItemStack(this, 1, meta and 3)
    }

    override fun registerBlockIcons(par1IconRegister: IIconRegister) {
        icons = arrayOf(IconHelper.forName(par1IconRegister, "irisWood"), IconHelper.forName(par1IconRegister, "irisWood_top"))

    }

    override fun getSubBlocks(item : Item?, tab : CreativeTabs?, list : MutableList<Any?>?) {
        if(list != null && item != null) {
            list.add(ItemStack(this, 1, 0))
            list.add(ItemStack(this, 1, 1))
            list.add(ItemStack(this, 1, 2))
            list.add(ItemStack(this, 1, 3))
        }
    }

    override fun getEntry(p0: World?, p1: Int, p2: Int, p3: Int, p4: EntityPlayer?, p5: ItemStack?): LexiconEntry? {
        return LexiconRegistry.irisSapling
    }
}
