package ninja.shadowfox.shadowfox_botany.common.blocks

import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.block.Block
import net.minecraft.block.BlockSlab
import net.minecraft.entity.passive.EntitySheep
import net.minecraft.util.IIcon
import net.minecraft.world.IBlockAccess
import java.awt.Color

class SlabColoredWood(val full: Boolean, val source: Block, val meta: Int) :
        ShadowFoxSlabs(full, source.material, source.unlocalizedName.replace("tile.".toRegex(), "") + meta + "Slab" + (if (full) "Full" else ""))
{
    init {
        setStepSound(source.stepSound)
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

    override val singleBlock: BlockSlab get() = ShadowFoxBlocks.coloredSlabs[meta] as BlockSlab
    override val fullBlock: BlockSlab get() = ShadowFoxBlocks.coloredSlabsFull[meta] as BlockSlab

    @SideOnly(Side.CLIENT)
    override fun getIcon(par1: Int, par2: Int): IIcon {
        return source.getIcon(par1, meta)
    }
}
