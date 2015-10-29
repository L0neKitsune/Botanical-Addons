package ninja.shadowfox.shadowfox_botany.common.blocks

import net.minecraft.block.Block
import net.minecraft.item.ItemStack
import net.minecraftforge.oredict.OreDictionary
import vazkii.botania.api.BotaniaAPI

public object ShadowFoxBlocks {
//    public var orangeBlock: Block

    public var coloredDirtBlock: Block
    public var irisSapling: Block
    public var irisLeaves: Block

    public var irisWood0: Block
    public var irisWood1: Block
    public var irisWood2: Block
    public var irisWood3: Block

    public var coloredStairs0: Block
    public var coloredStairs1: Block
    public var coloredStairs2: Block
    public var coloredStairs3: Block


    public var coloredPlanks: Block
    public var coloredSlabs: Block
    public var coloredSlabsFull: Block

    val WOOD: Array<String> = arrayOf("irisWoodWhite", "irisWoodOrange", "irisWoodMagenta", "irisWoodLightBlue", "irisWoodYellow", "irisWoodLime", "irisWoodPink", "irisWoodGray", "irisWoodLightGray", "irisWoodCyan", "irisWoodPurple", "irisWoodBlue", "irisWoodBrown", "irisWoodGreen", "irisWoodRed", "irisWoodBlack")
    val LEAVES: Array<String> = arrayOf("irisLeavesWhite", "irisLeavesOrange", "irisLeavesMagenta", "irisLeavesLightBlue", "irisLeavesYellow", "irisLeavesLime", "irisLeavesPink", "irisLeavesGray", "irisLeavesLightGray", "irisLeavesCyan", "irisLeavesPurple", "irisLeavesBlue", "irisLeavesBrown", "irisLeavesGreen", "irisLeavesRed", "irisLeavesBlack")


    init {
//        orangeBlock = OrangeBlock()
        coloredDirtBlock = BlockColoredDirt()
        irisSapling = BlockColoredSapling()
        irisLeaves = BlockColoredLeaves()

        irisWood0 = BlockColoredWood(0)
        irisWood1 = BlockColoredWood(1)
        irisWood2 = BlockColoredWood(2)
        irisWood3 = BlockColoredWood(3)


        coloredPlanks = BlockColoredPlanks()
        coloredSlabs = SlabColoredWood(false, coloredPlanks)
        coloredSlabsFull = SlabColoredWood(true, coloredPlanks)

        coloredStairs0 = StairsColoredWood(coloredPlanks, 0)
        coloredStairs1 = StairsColoredWood(coloredPlanks, 1)
        coloredStairs2 = StairsColoredWood(coloredPlanks, 2)
        coloredStairs3 = StairsColoredWood(coloredPlanks, 3)

        OreDictionary.registerOre("treeSapling", irisSapling)

        for (i in 0..3){
            var t = ItemStack(irisWood0, 1, i)
            OreDictionary.registerOre("logWood", t)
            OreDictionary.registerOre("irisWood", t)
            OreDictionary.registerOre(WOOD[i], t)

            t = ItemStack(irisWood1, 1, i)
            OreDictionary.registerOre("logWood", t)
            OreDictionary.registerOre("irisWood", t)
            OreDictionary.registerOre(WOOD[i+4], t)

            t = ItemStack(irisWood2, 1, i)
            OreDictionary.registerOre("logWood", t)
            OreDictionary.registerOre("irisWood", t)
            OreDictionary.registerOre(WOOD[i+8], t)

            t = ItemStack(irisWood3, 1, i)
            OreDictionary.registerOre("logWood", t)
            OreDictionary.registerOre("irisWood", t)
            OreDictionary.registerOre(WOOD[i+12], t)

            t = ItemStack(coloredStairs0, 1, i)
            OreDictionary.registerOre("stairWood", t)
            OreDictionary.registerOre("irisStairs", t)

            t = ItemStack(coloredStairs1, 1, i)
            OreDictionary.registerOre("stairWood", t)
            OreDictionary.registerOre("irisStairs", t)

            t = ItemStack(coloredStairs2, 1, i)
            OreDictionary.registerOre("stairWood", t)
            OreDictionary.registerOre("irisStairs", t)

            t = ItemStack(coloredStairs3, 1, i)
            OreDictionary.registerOre("stairWood", t)
            OreDictionary.registerOre("irisStairs", t)
        }


        for (i in 0..15) {
            var t = ItemStack(irisLeaves, 1, i)
            OreDictionary.registerOre("treeLeaves", t)
            OreDictionary.registerOre("irisLeaves", t)
            OreDictionary.registerOre(LEAVES[i], t)

            t = ItemStack(coloredPlanks, 1, i)
            OreDictionary.registerOre("plankWood", t)
            OreDictionary.registerOre("irisPlanks", t)

            t = ItemStack(coloredSlabs, 1, i)
            OreDictionary.registerOre("slabWood", t)
            OreDictionary.registerOre("irisSlabs", t)
        }


        BotaniaAPI.registerPaintableBlock(coloredDirtBlock)
    }
}
