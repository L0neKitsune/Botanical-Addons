package ninja.shadowfox.shadowfox_botany.common.blocks

import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.block.Block
import net.minecraft.block.BlockSlab
import net.minecraft.util.IIcon

class SlabColoredWood(val full: Boolean, val source: Block, val meta: Int) :
        ShadowFoxSlabs(full, source.material, source.unlocalizedName.replace("tile.".toRegex(), "") + meta + "Slab" + (if (full) "Full" else ""))
{
    init {
        setStepSound(source.stepSound)
    }

    override val singleBlock: BlockSlab get() = ShadowFoxBlocks.coloredSlabs[meta] as BlockSlab
    override val fullBlock: BlockSlab get() = ShadowFoxBlocks.coloredSlabsFull[meta] as BlockSlab

    @SideOnly(Side.CLIENT)
    override fun getIcon(par1: Int, par2: Int): IIcon {
        return source.getIcon(par1, meta)
    }
}
