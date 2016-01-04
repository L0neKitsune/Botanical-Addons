package ninja.shadowfox.shadowfox_botany.common.lexicon

import cpw.mods.fml.relauncher.FMLLaunchHandler
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.ChunkCoordinates
import net.minecraft.world.World
import vazkii.botania.api.BotaniaAPI
import vazkii.botania.api.lexicon.multiblock.component.MultiblockComponent
import java.util.*

class MultiblockComponentRainbow(relPos: ChunkCoordinates, default: Block, vararg blocks: Block):
      MultiblockComponent(relPos, default, -1) {

    private val blockpairs: MutableList<BlockPair> = ArrayList()
    val secondaryBlocks: Array<out Block>

    init {
        secondaryBlocks = blocks
        populatePairs(default, blockpairs)
        for (block in blocks) populatePairs(block, blockpairs)
    }

    private fun populatePairs(block: Block, pairs: MutableList<BlockPair>) {
        if (FMLLaunchHandler.side().isServer) return
        var stacks = ArrayList<ItemStack>()
        var item = Item.getItemFromBlock(block)
        block.getSubBlocks(item, block.creativeTabToDisplayOn, stacks)

        for(stack in stacks)
            if(stack.item == item)
                pairs.add(BlockPair(block, stack.itemDamage))
    }

    override fun getMeta(): Int {
        return blockpairs[(BotaniaAPI.internalHandler.worldElapsedTicks / 20 % blockpairs.size).toInt()].meta
    }

    override fun getBlock(): Block {
        return blockpairs[(BotaniaAPI.internalHandler.worldElapsedTicks / 20 % blockpairs.size).toInt()].block
    }

    override fun matches(world: World, x: Int, y: Int, z: Int): Boolean {
        for (pair in blockpairs)
            if (world.getBlock(x, y, z) == pair.block) 
                return true
        return false
    }

    override fun copy(): MultiblockComponent {
        return MultiblockComponentRainbow(this.relPos, this.block, *this.secondaryBlocks)
    }

    private class BlockPair(val block: Block, val meta: Int) {}
}
