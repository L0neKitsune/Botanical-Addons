package ninja.shadowfox.shadowfox_botany.common.blocks.base


import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.block.Block
import net.minecraft.block.BlockStairs
import net.minecraft.world.World
import ninja.shadowfox.shadowfox_botany.common.core.ShadowFoxCreativeTab
import ninja.shadowfox.shadowfox_botany.common.item.blocks.ItemIridescentBlockMod
import vazkii.botania.api.lexicon.ILexiconable


abstract class BlockStairsMod(val source: Block, val meta: Int, val name: String) : BlockStairs(source, meta), ILexiconable {
    init {
        setCreativeTab(ShadowFoxCreativeTab)
        useNeighborBrightness = true
        setStepSound(source.stepSound)
        setBlockName(name)
    }

    override fun getBlockHardness(world: World?, x: Int, y: Int, z: Int): Float {
        return source.getBlockHardness(world, x, y, z)
    }

    override fun setBlockName(par1Str: String): Block {
        register()
        return super.setBlockName(name)
    }

    open fun register() {
        GameRegistry.registerBlock(this, ItemIridescentBlockMod::class.java, name)
    }
}
