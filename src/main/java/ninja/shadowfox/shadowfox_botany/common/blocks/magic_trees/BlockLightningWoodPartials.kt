package ninja.shadowfox.shadowfox_botany.common.blocks.magic_trees

import cpw.mods.fml.common.registry.GameRegistry

import net.minecraft.block.Block
import net.minecraft.block.BlockSlab

import ninja.shadowfox.shadowfox_botany.common.blocks.rainbow.BlockRainbowWoodSlab
import ninja.shadowfox.shadowfox_botany.common.blocks.rainbow.BlockRainbowWoodStairs
import ninja.shadowfox.shadowfox_botany.common.item.blocks.ShadowFoxItemBlockMod
import ninja.shadowfox.shadowfox_botany.common.item.blocks.ShadowFoxSlabItemBlock
import ninja.shadowfox.shadowfox_botany.common.blocks.ShadowFoxBlocks

class BlockLightningWoodSlab(full: Boolean, source: Block = ShadowFoxBlocks.lightningPlanks): BlockRainbowWoodSlab(full, source) {
    override fun getFullBlock(): BlockSlab {
        return ShadowFoxBlocks.lightningSlabsFull as BlockSlab
    }

    override fun register() {
        GameRegistry.registerBlock(this, ShadowFoxSlabItemBlock::class.java, name)
    }

    override fun getSingleBlock(): BlockSlab {
        return ShadowFoxBlocks.lightningSlabs as BlockSlab
    }
}

class BlockLightningWoodStairs(source: Block = ShadowFoxBlocks.lightningPlanks): BlockRainbowWoodStairs(source) {
    override fun register() {
        GameRegistry.registerBlock(this, ShadowFoxItemBlockMod::class.java, name)
    }
}
