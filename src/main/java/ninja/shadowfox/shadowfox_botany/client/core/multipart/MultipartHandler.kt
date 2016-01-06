package ninja.shadowfox.shadowfox_botany.client.core.multipart

import codechicken.microblock.BlockMicroMaterial
import codechicken.microblock.MicroMaterialRegistry
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import ninja.shadowfox.shadowfox_botany.common.blocks.ShadowFoxBlocks
import java.util.*

public object MultipartHandler {

    init {
        registerAllColoredMultiparts(ShadowFoxBlocks.coloredDirtBlock)
        registerAllMultiparts(ShadowFoxBlocks.rainbowDirtBlock)

        registerAllColoredMultiparts(ShadowFoxBlocks.irisWood0)
        registerAllColoredMultiparts(ShadowFoxBlocks.irisWood1)
        registerAllColoredMultiparts(ShadowFoxBlocks.irisWood2)
        registerAllColoredMultiparts(ShadowFoxBlocks.irisWood3)
        registerAllMultiparts(ShadowFoxBlocks.rainbowWood)
        registerAllMultiparts(ShadowFoxBlocks.altWood0)
        registerAllMultiparts(ShadowFoxBlocks.altWood1)

        registerAllColoredMultiparts(ShadowFoxBlocks.coloredPlanks)
        registerAllMultiparts(ShadowFoxBlocks.rainbowPlanks)
        registerAllMultiparts(ShadowFoxBlocks.altPlanks)

        registerAllMultiparts(ShadowFoxBlocks.kindling)

        registerAllMultiparts(ShadowFoxBlocks.lightningWood)
        registerAllMultiparts(ShadowFoxBlocks.lightningPlanks)

        registerAllMultiparts(ShadowFoxBlocks.netherWood)
        registerAllMultiparts(ShadowFoxBlocks.netherPlanks)

        registerAllColoredMultiparts(ShadowFoxBlocks.irisLeaves0)
        registerAllColoredMultiparts(ShadowFoxBlocks.irisLeaves1)
        registerAllMultiparts(ShadowFoxBlocks.rainbowLeaves)
        registerAllMultiparts(ShadowFoxBlocks.altLeaves)
        registerAllMultiparts(ShadowFoxBlocks.lightningLeaves)
        registerAllMultiparts(ShadowFoxBlocks.netherLeaves)
    }

    fun registerAllMultiparts(block: Block) {
        var stacks = ArrayList<ItemStack>()
        var item = Item.getItemFromBlock(block)
        block.getSubBlocks(item, block.creativeTabToDisplayOn, stacks)

        for (stack in stacks)
            if (stack.item == item)
                registerMultipart(block, stack.itemDamage)
    }

    fun registerAllColoredMultiparts(block: Block) {
        var stacks = ArrayList<ItemStack>()
        var item = Item.getItemFromBlock(block)
        block.getSubBlocks(item, block.creativeTabToDisplayOn, stacks)

        for (stack in stacks)
            if (stack.item == item)
                registerOverlaidMultipart(block, stack.itemDamage)
    }

    fun registerMultipart(block: Block, meta: Int) {
        MicroMaterialRegistry.registerMaterial(BlockMicroMaterial(block, meta), block.unlocalizedName + if (meta == 0) "" else "_" + meta)
    }

    fun registerOverlaidMultipart(block: Block, meta: Int) {
        MicroMaterialRegistry.registerMaterial(ColoredMicroMaterial(block, meta), block.unlocalizedName + if (meta == 0) "" else "_" + meta)
    }
}
