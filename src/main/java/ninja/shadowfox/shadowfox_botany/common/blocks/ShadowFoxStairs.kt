package ninja.shadowfox.shadowfox_botany.common.blocks


import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.block.Block
import net.minecraft.block.BlockStairs
import ninja.shadowfox.shadowfox_botany.common.core.ShadowFoxCreativeTab
import ninja.shadowfox.shadowfox_botany.common.item.blocks.ShadowFoxStairsItemBlock
import vazkii.botania.api.lexicon.ILexiconable
import vazkii.botania.api.lexicon.LexiconEntry


abstract  class ShadowFoxStairs(val source: Block, val meta: Int, val name: String) : BlockStairs(source, meta), ILexiconable
{
    var lexicaPage: LexiconEntry? = null

    init {
        setCreativeTab(ShadowFoxCreativeTab)
        useNeighborBrightness = true
        setStepSound(source.stepSound)
        setBlockName(name)
    }

    override fun setBlockName(par1Str: String): Block {
        GameRegistry.registerBlock(this, ShadowFoxStairsItemBlock::class.java, par1Str)
        return super.setBlockName(name)
    }
}
