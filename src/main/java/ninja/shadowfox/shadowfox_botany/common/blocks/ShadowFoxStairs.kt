package ninja.shadowfox.shadowfox_botany.common.blocks

import cpw.mods.fml.common.registry.GameRegistry
import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.block.Block
import net.minecraft.block.BlockStairs
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.passive.EntitySheep
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.IIcon
import net.minecraft.world.IBlockAccess
import ninja.shadowfox.shadowfox_botany.common.core.ShadowFoxCreativeTab
import ninja.shadowfox.shadowfox_botany.common.item.blocks.ShadowFoxMetaItemBlock
import java.awt.Color

open class ShadowFoxStairs(val mainBlock: Block) : BlockStairs(mainBlock, 0) {

    init {
        setCreativeTab(ShadowFoxCreativeTab)
        useNeighborBrightness = true
    }

    @SideOnly(Side.CLIENT)
    override fun getIcon(side: Int, meta: Int): IIcon
    {
        return mainBlock.getIcon(side, meta)
    }
}
