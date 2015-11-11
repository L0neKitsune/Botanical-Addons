package ninja.shadowfox.shadowfox_botany.common.crafting

import net.minecraft.block.Block
import net.minecraft.item.ItemStack
import net.minecraft.world.World
import net.minecraftforge.oredict.OreDictionary
import vazkii.botania.api.recipe.RecipePureDaisy
import vazkii.botania.api.subtile.SubTileEntity

class RecipePureDaisyExclusion(input: Any, output: Block, outputMeta: Int) : RecipePureDaisy(input, output, outputMeta) {

	override fun matches(world: World, x: Int, y: Int, z: Int, pureDaisy: SubTileEntity, block: Block, meta: Int): Boolean {
		if(input is Block)
			return block == input;

		val stack = ItemStack(block, 1, meta);
		var oredict = input as String;
		return isOreDict(stack, oredict) && block != output;
	}

}
