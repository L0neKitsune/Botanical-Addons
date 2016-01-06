package ninja.shadowfox.shadowfox_botany.common.blocks.rainbow

import net.minecraft.block.Block
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.world.World
import ninja.shadowfox.shadowfox_botany.common.blocks.ShadowFoxBlocks
import ninja.shadowfox.shadowfox_botany.common.blocks.base.BlockStairsMod
import vazkii.botania.api.lexicon.ILexiconable
import vazkii.botania.api.lexicon.LexiconEntry
import kotlin.text.replace
import kotlin.text.toRegex


open class BlockRainbowWoodStairs(source: Block = ShadowFoxBlocks.rainbowPlanks) : BlockStairsMod(source, 0, source.unlocalizedName.replace("tile.".toRegex(), "") + "Stairs"), ILexiconable {
    init {
    }

    override fun getEntry(p0: World?, p1: Int, p2: Int, p3: Int, p4: EntityPlayer?, p5: ItemStack?): LexiconEntry? {
        return (source as ILexiconable).getEntry(p0, p1, p2, p3, p4, p5)
    }

    override fun isToolEffective(type: String?, metadata: Int): Boolean {
        return (type != null && type.equals("axe"))
    }

    override fun getHarvestTool(metadata: Int): String {
        return "axe"
    }
}
