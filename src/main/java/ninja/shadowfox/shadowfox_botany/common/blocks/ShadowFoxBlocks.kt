package ninja.shadowfox.shadowfox_botany.common.blocks

import net.minecraft.block.Block
import net.minecraft.item.ItemStack
import net.minecraftforge.oredict.OreDictionary
import vazkii.botania.api.BotaniaAPI
import vazkii.botania.common.block.ModBlocks

public object ShadowFoxBlocks {
//    public var orangeBlock: Block

    public var coloredDirtBlock: Block
    public var irisSapling: Block
    public var irisLeaves: Block
    public var irisWood: Block
    public var coloredPlanks: Block
    public var coloredSlabs: Block
    public var coloredSlabsFull: Block
    public var coloredStairs: Block

    init {
//        orangeBlock = OrangeBlock()
        coloredDirtBlock = BlockColoredDirt()
        irisSapling = BlockColoredSapling()
        irisLeaves = BlockColoredLeaves()
        irisWood = BlockColoredWood()
        coloredPlanks = BlockColoredPlanks()
        coloredSlabs = SlabColoredWood(false, coloredPlanks)
        coloredSlabsFull = SlabColoredWood(true, coloredPlanks)
        coloredStairs = StairsColoredWood(coloredPlanks)

        OreDictionary.registerOre("treeSapling", irisSapling)

        for (i in 0..15) {
            var t = ItemStack(irisWood, 1, i)
            OreDictionary.registerOre("logWood", t)
            OreDictionary.registerOre("irisWood", t)

            t = ItemStack(irisLeaves, 1, i)
            OreDictionary.registerOre("treeLeaves", t)
            OreDictionary.registerOre("irisLeaves", t)

            t = ItemStack(coloredPlanks, 1, i)
            OreDictionary.registerOre("plankWood", t)
            OreDictionary.registerOre("irisPlanks", t)

            t = ItemStack(coloredSlabs, 1, i)
            OreDictionary.registerOre("slabWood", t)
            OreDictionary.registerOre("irisSlabs", t)

            t = ItemStack(coloredStairs, 1, i)
            OreDictionary.registerOre("stairWood", t)
            OreDictionary.registerOre("irisStairs", t)
        }


        BotaniaAPI.registerPaintableBlock(coloredDirtBlock)
    }
}
