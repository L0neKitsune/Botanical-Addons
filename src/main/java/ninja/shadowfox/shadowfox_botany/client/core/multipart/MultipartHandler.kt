package ninja.shadowfox.shadowfox_botany.client.core.multipart

import java.util.ArrayList

import cpw.mods.fml.relauncher.FMLLaunchHandler
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import ninja.shadowfox.shadowfox_botany.common.blocks.ShadowFoxBlocks
import codechicken.microblock.BlockMicroMaterial
import codechicken.microblock.TopMicroMaterial
import codechicken.microblock.MicroMaterialRegistry

public object MultipartHandler {

    init {
        registerAllColoredMultiparts(ShadowFoxBlocks.coloredDirtBlock)
        registerAllMultiparts(ShadowFoxBlocks.rainbowDirtBlock)

        registerAllColoredMultiparts(ShadowFoxBlocks.irisWood0)
        registerAllColoredMultiparts(ShadowFoxBlocks.irisWood1)
        registerAllColoredMultiparts(ShadowFoxBlocks.irisWood2)
        registerAllColoredMultiparts(ShadowFoxBlocks.irisWood3)
        registerAllMultiparts(ShadowFoxBlocks.rainbowWood)

        registerAllColoredMultiparts(ShadowFoxBlocks.coloredPlanks)
        registerAllColoredMultiparts(ShadowFoxBlocks.rainbowPlanks)

        registerAllMultiparts(ShadowFoxBlocks.kindling)

        registerAllMultiparts(ShadowFoxBlocks.lightningWood)
        registerAllMultiparts(ShadowFoxBlocks.lightningPlanks)

        registerAllColoredMultiparts(ShadowFoxBlocks.irisLeaves0)
        registerAllColoredMultiparts(ShadowFoxBlocks.irisLeaves1)
        registerAllMultiparts(ShadowFoxBlocks.rainbowLeaves)
        registerAllMultiparts(ShadowFoxBlocks.lightningLeaves)
    }

    fun registerAllMultiparts(block: Block) {
        var stacks = ArrayList<ItemStack>()
        var item = Item.getItemFromBlock(block)
        block.getSubBlocks(item, block.creativeTabToDisplayOn, stacks)

        for(stack in stacks)
            if(stack.item == item)
                registerMultipart(block, stack.itemDamage)
    }

    fun registerAllColoredMultiparts(block: Block) {
        var stacks = ArrayList<ItemStack>()
        var item = Item.getItemFromBlock(block)
        block.getSubBlocks(item, block.creativeTabToDisplayOn, stacks)

        for(stack in stacks)
            if(stack.item == item)
                registerOverlaidMultipart(block, stack.itemDamage)
    }

    fun registerMultipart(block: Block, meta: Int) {
        MicroMaterialRegistry.registerMaterial(BlockMicroMaterial(block, meta), block.unlocalizedName + if (meta == 0) "" else "_" + meta)
    }

    fun registerOverlaidMultipart(block: Block, meta: Int) {
        MicroMaterialRegistry.registerMaterial(ColoredMicroMaterial(block, meta), block.unlocalizedName + if (meta == 0) "" else "_" + meta)
    }
}
