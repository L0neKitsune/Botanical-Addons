package ninja.shadowfox.shadowfox_botany.common.blocks

import net.minecraft.block.Block
import net.minecraft.item.ItemStack
import net.minecraftforge.oredict.OreDictionary
import vazkii.botania.api.BotaniaAPI

public object ShadowFoxBlocks {

    public var coloredDirtBlock: Block
    public var rainbowDirtBlock: Block
    public var irisSapling: Block
    public var irisLeaves: Block
    public var irisGrass: Block
    public var rainbowGrass: Block
    public var rainbowPlanks: Block

    public var irisWood0: Block
    public var irisWood1: Block
    public var irisWood2: Block
    public var irisWood3: Block

    public var coloredPlanks: Block

    public var irisTallGrass0: Block
    public var irisTallGrass1: Block
    public var rainbowTallGrass: Block
    public var rainbowLeaves: Block
    public var rainbowWood: Block

    public var coloredSlabs: Array<Block>
    public var rainbowSlabs: Block

    public var coloredSlabsFull: Array<Block>
    public var rainbowSlabsFull: Block

    public var coloredStairs: Array<Block>
    public var rainbowStairs: Block

    val WOOD: Array<String> = arrayOf("irisWoodWhite", "irisWoodOrange", "irisWoodMagenta", "irisWoodLightBlue", "irisWoodYellow", "irisWoodLime", "irisWoodPink", "irisWoodGray", "irisWoodLightGray", "irisWoodCyan", "irisWoodPurple", "irisWoodBlue", "irisWoodBrown", "irisWoodGreen", "irisWoodRed", "irisWoodBlack")
    val LEAVES: Array<String> = arrayOf("irisLeavesWhite", "irisLeavesOrange", "irisLeavesMagenta", "irisLeavesLightBlue", "irisLeavesYellow", "irisLeavesLime", "irisLeavesPink", "irisLeavesGray", "irisLeavesLightGray", "irisLeavesCyan", "irisLeavesPurple", "irisLeavesBlue", "irisLeavesBrown", "irisLeavesGreen", "irisLeavesRed", "irisLeavesBlack")


    init {
//        orangeBlock = OrangeBlock()
        coloredDirtBlock = BlockColoredDirt()
        rainbowDirtBlock = BlockRainbowDirt()
        irisSapling = BlockColoredSapling()
        irisLeaves = BlockColoredLeaves()
        irisGrass = BlockColoredGrass()
        rainbowGrass = BlockRainbowGrass()
        rainbowLeaves = BlockRainbowLeaves()
        rainbowWood = BlockRainbowWood()
        rainbowPlanks = BlockRainbowPlanks()

        irisWood0 = BlockColoredWood(0)
        irisWood1 = BlockColoredWood(1)
        irisWood2 = BlockColoredWood(2)
        irisWood3 = BlockColoredWood(3)

        coloredPlanks = BlockColoredPlanks()

        coloredSlabs = Array<Block>(16 , {i -> SlabColoredWood(false, i)})
        rainbowSlabs = SlabRainbowWood(false)

        coloredSlabsFull = Array<Block>(16 , {i -> SlabColoredWood(true, i)})
        rainbowSlabsFull = SlabRainbowWood(true)

        coloredStairs = Array<Block>(16 , {i -> StairsColoredWood(i)})
        rainbowStairs = StairsRainbowWood()

        (rainbowSlabs as ShadowFoxSlabs).register()
        (rainbowSlabsFull as ShadowFoxSlabs).register()

        for (i in coloredSlabs) {
            (i as ShadowFoxSlabs).register()
        }

        for (i in coloredSlabsFull) {
            (i as ShadowFoxSlabs).register()
        }

        irisTallGrass0 = BlockColoredDoubleGrass(0)
        irisTallGrass1 = BlockColoredDoubleGrass(1)
        rainbowTallGrass = BlockRainbowDoubleGrass()

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
        }


        for (i in 0..15) {
            OreDictionary.registerOre("irisDirt", ItemStack(coloredDirtBlock, 1, i))
            
            var t = ItemStack(irisLeaves, 1, i)
            OreDictionary.registerOre("treeLeaves", t)
            OreDictionary.registerOre("irisLeaves", t)
            OreDictionary.registerOre(LEAVES[i], t)

            t = ItemStack(coloredPlanks, 1, i)
            OreDictionary.registerOre("plankWood", t)
            OreDictionary.registerOre("irisPlanks", t)

            t = ItemStack(coloredStairs[i], 1)
            OreDictionary.registerOre("stairWood", t)
            OreDictionary.registerOre("irisStairs", t)

            t = ItemStack(coloredSlabs[i], 1)
            OreDictionary.registerOre("slabWood", t)
            OreDictionary.registerOre("irisSlabs", t)
        }

        BotaniaAPI.registerPaintableBlock(coloredDirtBlock)
    }
}
