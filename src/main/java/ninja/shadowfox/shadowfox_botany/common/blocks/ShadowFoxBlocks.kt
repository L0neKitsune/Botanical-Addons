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
    public var coloredSlabs: Array<Block> = emptyArray()
    public var coloredSlabsFull: Array<Block> = emptyArray()
    public var coloredStairs: Array<Block> = emptyArray()

    init {
//        orangeBlock = OrangeBlock()
        coloredDirtBlock = BlockColoredDirt()
        irisSapling = BlockColoredSapling()
        irisLeaves = BlockColoredLeaves()
        irisWood = BlockColoredWood()
        coloredPlanks = BlockColoredPlanks()

        OreDictionary.registerOre("treeSapling", irisSapling)

        for (i in 0..15) {
            var t = ItemStack(irisWood, 1, i)
            OreDictionary.registerOre("logWood", t)
            OreDictionary.registerOre("irisWood", t)

            t = ItemStack(irisLeaves, 1, i)
            OreDictionary.registerOre("treeLeaves", t)
            OreDictionary.registerOre("irisLeaves", t)

        }

        coloredSlabs = Array(16, {i -> SlabColoredWood(false, coloredPlanks, i)})
        coloredSlabsFull = Array(16, {i -> SlabColoredWood(true, coloredPlanks, i)})
        coloredStairs = Array(16, {i -> ShadowFoxStairs(coloredPlanks, i,
                coloredPlanks.getUnlocalizedName().replace("tile.".toRegex(), "") + i + "Stairs")})


        for (i in 0..15){
            (coloredSlabs[i] as ShadowFoxSlabs).register()
            (coloredSlabsFull[i] as ShadowFoxSlabs).register()
        }

        BotaniaAPI.registerPaintableBlock(coloredDirtBlock)
    }
}
