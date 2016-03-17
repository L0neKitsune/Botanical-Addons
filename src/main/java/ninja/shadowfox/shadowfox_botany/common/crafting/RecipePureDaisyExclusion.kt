package ninja.shadowfox.shadowfox_botany.common.crafting

import net.minecraft.block.state.IBlockState
import net.minecraft.item.ItemStack
import net.minecraft.util.BlockPos
import net.minecraft.world.World
import vazkii.botania.api.recipe.RecipePureDaisy
import vazkii.botania.api.subtile.SubTileEntity

class RecipePureDaisyExclusion(input: Any, val output: IBlockState, outputMeta: Int) : RecipePureDaisy(input, output, outputMeta) {

    override fun matches(world: World, pos: BlockPos, pureDaisy: SubTileEntity, state: IBlockState): Boolean {
        if (input is IBlockState)
            return state.block == input

        val stack = ItemStack(state.block, 1, state.block.getMetaFromState(state))
        var oredict = input as String
        return isOreDict(stack, oredict) && state != output
    }
}
