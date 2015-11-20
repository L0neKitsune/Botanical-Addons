package ninja.shadowfox.shadowfox_botany.common.blocks

import net.minecraft.block.Block
import net.minecraft.item.ItemStack
import net.minecraftforge.oredict.OreDictionary
import ninja.shadowfox.shadowfox_botany.common.blocks.base.ShadowFoxSlabs
import ninja.shadowfox.shadowfox_botany.common.blocks.colored.*
import ninja.shadowfox.shadowfox_botany.common.blocks.rainbow.*
import vazkii.botania.api.BotaniaAPI

public object ShadowFoxBlocks {

    public var coloredDirtBlock: Block
    public var rainbowDirtBlock: Block
    public var irisSapling: Block
    public var irisLeaves: Block
    public var rainbowLeaves: Block
    public var irisGrass: Block
    public var rainbowGrass: Block

    public var irisWood0: Block
    public var irisWood1: Block
    public var irisWood2: Block
    public var irisWood3: Block
    public var rainbowWood: Block

    public var coloredPlanks: Block
    public var rainbowPlanks: Block

    public var irisTallGrass0: Block
    public var irisTallGrass1: Block
    public var rainbowTallGrass: Block

    public var coloredSlabs: Array<Block>
    public var rainbowSlabs: Block

    public var coloredSlabsFull: Array<Block>
    public var rainbowSlabsFull: Block

    public var coloredStairs: Array<Block>
    public var rainbowStairs: Block
    public var invisibleFlame: Block
    public var lightningRod: Block

    val WOOD: Array<String> = arrayOf("irisWoodWhite", "irisWoodOrange", "irisWoodMagenta", "irisWoodLightBlue", "irisWoodYellow", "irisWoodLime", "irisWoodPink", "irisWoodGray", "irisWoodLightGray", "irisWoodCyan", "irisWoodPurple", "irisWoodBlue", "irisWoodBrown", "irisWoodGreen", "irisWoodRed", "irisWoodBlack")
    val LEAVES: Array<String> = arrayOf("irisLeavesWhite", "irisLeavesOrange", "irisLeavesMagenta", "irisLeavesLightBlue", "irisLeavesYellow", "irisLeavesLime", "irisLeavesPink", "irisLeavesGray", "irisLeavesLightGray", "irisLeavesCyan", "irisLeavesPurple", "irisLeavesBlue", "irisLeavesBrown", "irisLeavesGreen", "irisLeavesRed", "irisLeavesBlack")


    init {
        coloredDirtBlock = BlockColoredDirt()
        rainbowDirtBlock = BlockRainbowDirt()
        irisSapling = BlockColoredSapling()
        irisLeaves = BlockColoredLeaves()
        rainbowLeaves = BlockRainbowLeaves()
        irisGrass = BlockColoredGrass()
        rainbowGrass = BlockRainbowGrass()
        invisibleFlame = BlockManaInvisibleFlame()

        irisWood0 = BlockColoredWood(0)
        irisWood1 = BlockColoredWood(1)
        irisWood2 = BlockColoredWood(2)
        irisWood3 = BlockColoredWood(3)
        rainbowWood = BlockRainbowWood()

        coloredPlanks = BlockColoredPlanks()
        rainbowPlanks = BlockRainbowPlanks()

        coloredSlabs = Array<Block>(16 , {i -> BlockColoredWoodSlab(false, i) })
        rainbowSlabs = BlockRainbowWoodSlab(false)

        coloredSlabsFull = Array<Block>(16 , {i -> BlockColoredWoodSlab(true, i) })
        rainbowSlabsFull = BlockRainbowWoodSlab(true)

        coloredStairs = Array<Block>(16 , {i -> BlockColoredWoodStairs(i) })
        rainbowStairs = BlockRainbowWoodStairs()

        irisTallGrass0 = BlockColoredDoubleGrass(0)
        irisTallGrass1 = BlockColoredDoubleGrass(1)
        rainbowTallGrass = BlockRainbowDoubleGrass()
        lightningRod = BlockLightningRod()

        register()
        initOreDict()

        BotaniaAPI.registerPaintableBlock(coloredDirtBlock)
    }

    /**
     * Mainly for slabs since they can't be registred till both full and half slabs are created
     */
    private fun register(){
        for (i in coloredSlabs) {
            (i as ShadowFoxSlabs).register()
        }

        for (i in coloredSlabsFull) {
            (i as ShadowFoxSlabs).register()
        }

        (rainbowSlabs as ShadowFoxSlabs).register()
        (rainbowSlabsFull as ShadowFoxSlabs).register()
    }

    private fun initOreDict() {
        OreDictionary.registerOre("treeSapling", irisSapling)

        var t: ItemStack
        for (i in 0..3){
            t = ItemStack(irisWood0, 1, i)
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
        t = ItemStack(rainbowWood)
        OreDictionary.registerOre("logWood", t)
        OreDictionary.registerOre("irisWood", t)
        OreDictionary.registerOre("irisWoodRainbow", t)


        OreDictionary.registerOre("irisDirt", ItemStack(rainbowDirtBlock))
        for (i in 0..15) {
            OreDictionary.registerOre("irisDirt", ItemStack(coloredDirtBlock, 1, i))

            t = ItemStack(irisLeaves, 1, i)
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

        t = ItemStack(rainbowLeaves)
        OreDictionary.registerOre("treeLeaves", t)
        OreDictionary.registerOre("irisLeaves", t)
        OreDictionary.registerOre("irisLeavesRainbow", t)

        t = ItemStack(rainbowPlanks)
        OreDictionary.registerOre("plankWood", t)
        OreDictionary.registerOre("irisPlanks", t)

        t = ItemStack(rainbowStairs)
        OreDictionary.registerOre("stairWood", t)
        OreDictionary.registerOre("irisStairs", t)

        t = ItemStack(rainbowSlabs)
        OreDictionary.registerOre("slabWood", t)
        OreDictionary.registerOre("irisSlabs", t)
    }
}
