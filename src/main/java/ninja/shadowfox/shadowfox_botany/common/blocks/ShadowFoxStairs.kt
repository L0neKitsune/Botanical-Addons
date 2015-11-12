package ninja.shadowfox.shadowfox_botany.common.blocks

import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.block.Block
import net.minecraft.block.BlockStairs
import net.minecraft.util.IIcon
import ninja.shadowfox.shadowfox_botany.common.core.ShadowFoxCreativeTab

open class ShadowFoxStairs(val mainBlock: Block) : BlockStairs(mainBlock, 0) {

    init {
        setCreativeTab(ShadowFoxCreativeTab)
        useNeighborBrightness = true
    }

    @SideOnly(Side.CLIENT)
    override fun getIcon(side: Int, meta: Int): IIcon {
        return mainBlock.getIcon(side, meta)
    }
}
