package ninja.shadowfox.shadowfox_botany.common.blocks.magic_trees.nether_oak

import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.block.Block
import net.minecraft.block.BlockSlab
import ninja.shadowfox.shadowfox_botany.common.blocks.ShadowFoxBlocks
import ninja.shadowfox.shadowfox_botany.common.blocks.rainbow.BlockRainbowWoodSlab
import ninja.shadowfox.shadowfox_botany.common.blocks.rainbow.BlockRainbowWoodStairs
import ninja.shadowfox.shadowfox_botany.common.item.blocks.ItemBlockMod
import ninja.shadowfox.shadowfox_botany.common.item.blocks.ItemSlabMod

class BlockNetherWoodSlab(full: Boolean, source: Block = ShadowFoxBlocks.netherPlanks): BlockRainbowWoodSlab(full, source) {
    override fun getFullBlock(): BlockSlab {
        return ShadowFoxBlocks.netherSlabsFull as BlockSlab
    }

    override fun register() {
        GameRegistry.registerBlock(this, ItemSlabMod::class.java, name)
    }

    override fun getSingleBlock(): BlockSlab {
        return ShadowFoxBlocks.netherSlabs as BlockSlab
    }
}

class BlockNetherWoodStairs(source: Block = ShadowFoxBlocks.netherPlanks): BlockRainbowWoodStairs(source) {
    override fun register() {
        GameRegistry.registerBlock(this, ItemBlockMod::class.java, name)
    }
}
