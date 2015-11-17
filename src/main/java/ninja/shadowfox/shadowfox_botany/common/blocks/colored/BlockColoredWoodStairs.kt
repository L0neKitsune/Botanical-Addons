package ninja.shadowfox.shadowfox_botany.common.blocks.colored


import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.block.Block
import net.minecraft.entity.passive.EntitySheep
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.world.World
import net.minecraft.world.IBlockAccess
import ninja.shadowfox.shadowfox_botany.common.blocks.ShadowFoxBlocks
import ninja.shadowfox.shadowfox_botany.common.blocks.base.ShadowFoxStairs
import ninja.shadowfox.shadowfox_botany.common.lexicon.LexiconRegistry
import vazkii.botania.api.lexicon.ILexiconable
import vazkii.botania.api.lexicon.LexiconEntry
import java.awt.Color


class BlockColoredWoodStairs(meta: Int, source: Block = ShadowFoxBlocks.coloredPlanks) : ShadowFoxStairs(source, meta, source.unlocalizedName.replace("tile.".toRegex(), "") + "Stairs" + meta), ILexiconable
{
    init { }

    @SideOnly(Side.CLIENT)
    override fun getRenderColor(m: Int): Int {
        if (meta >= EntitySheep.fleeceColorTable.size)
            return 0xFFFFFF

        var color = EntitySheep.fleeceColorTable[meta]
        return Color(color[0], color[1], color[2]).rgb
    }

    @SideOnly(Side.CLIENT)
    override fun colorMultiplier(p_149720_1_: IBlockAccess?, p_149720_2_: Int, p_149720_3_: Int, p_149720_4_: Int): Int {
        if (meta >= EntitySheep.fleeceColorTable.size)
            return 0xFFFFFF

        var color = EntitySheep.fleeceColorTable[meta]
        return Color(color[0], color[1], color[2]).rgb

    }

    override fun getEntry(p0: World?, p1: Int, p2: Int, p3: Int, p4: EntityPlayer?, p5: ItemStack?): LexiconEntry? {
        return LexiconRegistry.irisSapling
    }
}
