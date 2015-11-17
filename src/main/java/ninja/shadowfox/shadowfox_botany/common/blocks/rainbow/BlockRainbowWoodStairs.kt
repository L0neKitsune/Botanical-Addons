package ninja.shadowfox.shadowfox_botany.common.blocks.rainbow

import net.minecraft.block.Block
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.world.World
import ninja.shadowfox.shadowfox_botany.common.blocks.ShadowFoxBlocks
import ninja.shadowfox.shadowfox_botany.common.blocks.base.ShadowFoxStairs
import ninja.shadowfox.shadowfox_botany.common.lexicon.LexiconRegistry
import vazkii.botania.api.lexicon.ILexiconable
import vazkii.botania.api.lexicon.LexiconEntry


class BlockRainbowWoodStairs(source: Block = ShadowFoxBlocks.rainbowPlanks) : ShadowFoxStairs(source, 0, ShadowFoxBlocks.rainbowPlanks.unlocalizedName.replace("tile.".toRegex(), "") + "Stairs"), ILexiconable
{
    init { }

    override fun getEntry(p0: World?, p1: Int, p2: Int, p3: Int, p4: EntityPlayer?, p5: ItemStack?): LexiconEntry? {
        return LexiconRegistry.irisSapling
    }
}
