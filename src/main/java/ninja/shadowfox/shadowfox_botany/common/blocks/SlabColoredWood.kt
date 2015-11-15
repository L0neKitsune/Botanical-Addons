package ninja.shadowfox.shadowfox_botany.common.blocks

import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.block.Block
import net.minecraft.block.BlockSlab
import net.minecraft.entity.passive.EntitySheep
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import vazkii.botania.api.lexicon.ILexiconable
import vazkii.botania.api.lexicon.LexiconEntry
import ninja.shadowfox.shadowfox_botany.common.lexicon.LexiconRegistry
import java.awt.Color

class SlabColoredWood(full: Boolean, meta: Int, source: Block = ShadowFoxBlocks.coloredPlanks) :
        ShadowFoxSlabs(full, meta, source, source.unlocalizedName.replace("tile.".toRegex(), "") + "Slab" + (if (full) "Full" else "") + meta), ILexiconable
{
    init {
        this.setHardness(1.5f)
        this.setResistance(10.0f)
    }

    @SideOnly(Side.CLIENT)
    override fun getRenderColor(m: Int): Int {
        if (meta >= EntitySheep.fleeceColorTable.size)
            return 0xFFFFFF;

        var color = EntitySheep.fleeceColorTable[meta];
        return Color(color[0], color[1], color[2]).rgb;
    }

    @SideOnly(Side.CLIENT)
    override fun colorMultiplier(p_149720_1_: IBlockAccess?, p_149720_2_: Int, p_149720_3_: Int, p_149720_4_: Int): Int {

        if (meta >= EntitySheep.fleeceColorTable.size)
            return 0xFFFFFF

        var color = EntitySheep.fleeceColorTable[meta]
        return Color(color[0], color[1], color[2]).rgb
    }

    override fun getFullBlock(): BlockSlab {
        return ShadowFoxBlocks.coloredSlabsFull[meta] as BlockSlab
    }

    override fun getSingleBlock(): BlockSlab {
        return ShadowFoxBlocks.coloredSlabs[meta] as BlockSlab
    }

    override fun getEntry(world: World?, x: Int, y: Int, z: Int, player: EntityPlayer?, lexicon: ItemStack?): LexiconEntry {
        return LexiconRegistry.irisSapling
    }
}
