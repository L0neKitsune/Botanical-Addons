package ninja.shadowfox.shadowfox_botany.common.core.handler

import java.util.ArrayList

import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import ninja.shadowfox.shadowfox_botany.common.blocks.ShadowFoxBlocks
import codechicken.microblock.BlockMicroMaterial
import codechicken.microblock.MicroMaterialRegistry

public object MultipartHandler {

	init {
		// For some reason, we can't use colorskin rendering for these blocks. Maybe we should try to figure out a way to do so?
		// registerAllMultiparts(ShadowFoxBlocks.coloredDirtBlock)
    registerAllMultiparts(ShadowFoxBlocks.rainbowDirtBlock)
    // registerAllMultiparts(ShadowFoxBlocks.irisWood0)
    // registerAllMultiparts(ShadowFoxBlocks.irisWood1)
    // registerAllMultiparts(ShadowFoxBlocks.irisWood2)
    // registerAllMultiparts(ShadowFoxBlocks.irisWood3)
    registerAllMultiparts(ShadowFoxBlocks.rainbowWood)
    // registerAllMultiparts(ShadowFoxBlocks.coloredPlanks)
    registerAllMultiparts(ShadowFoxBlocks.rainbowPlanks)
	}

	fun registerAllMultiparts(block: Block) {
		var stacks = ArrayList<ItemStack>()
		var item = Item.getItemFromBlock(block)
		block.getSubBlocks(item, block.creativeTabToDisplayOn, stacks)

		for(stack in stacks)
			if(stack.item == item)
				registerMultipart(block, stack.itemDamage)
	}

	fun registerMultipart(block: Block, meta: Int) {
		MicroMaterialRegistry.registerMaterial(BlockMicroMaterial(block, meta), block.unlocalizedName + if (meta == 0) "" else "_" + meta)
	}
}
