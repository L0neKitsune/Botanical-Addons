package ninja.shadowfox.shadowfox_botany.common.blocks

import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.init.Blocks
import net.minecraft.block.Block
import net.minecraft.item.ItemStack
import net.minecraftforge.oredict.OreDictionary
import ninja.shadowfox.shadowfox_botany.common.blocks.alt_grass.*
import ninja.shadowfox.shadowfox_botany.common.blocks.base.*
import ninja.shadowfox.shadowfox_botany.common.blocks.colored.*
import ninja.shadowfox.shadowfox_botany.common.blocks.magic_trees.*
import ninja.shadowfox.shadowfox_botany.common.blocks.magic_trees.lightning_oak.*
import ninja.shadowfox.shadowfox_botany.common.blocks.magic_trees.nether_oak.*
import ninja.shadowfox.shadowfox_botany.common.blocks.rainbow.*

import ninja.shadowfox.shadowfox_botany.common.blocks.tile.*
import vazkii.botania.api.BotaniaAPI
import vazkii.botania.api.lexicon.multiblock.MultiblockSet

public object ShadowFoxBlocks {

    fun setBurnable(block: Block, encouragement: Int, flammablility: Int) {
        Blocks.fire.setFireInfo(block, encouragement, flammablility)
    }

    public var coloredDirtBlock: Block
    public var rainbowDirtBlock: Block
    public var irisSapling: Block
    public var irisLeaves0: Block
    public var irisLeaves1: Block
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

    public var itemDisplay: Block
    public var treeCrafter: MultiblockSet
    public var treeCrafterBlock: Block
    public var treeCrafterBlockRB: Block

    public var lightningSapling: Block
    public var lightningWood: Block
    public var lightningLeaves: Block
    public var lightningPlanks: Block

    public var lightningStairs: Block
    public var lightningSlabs: Block
    public var lightningSlabsFull: Block

    public var invisibleFlame: Block
    public var rainbowFlame: Block
    public var livingwoodFunnel: Block
    public var schemaBlock: Block

    public var netherSapling: Block
    public var netherWood: Block
    public var netherLeaves: Block
    public var netherPlanks: Block
    public var netherSlabs: Block
    public var netherSlabsFull: Block
    public var netherStairs: Block

    public var altWood1: Block
    public var altWood2: Block
    public var altLeaves: Block
    public var altPlanks: Block
    public var altSlabs: Array<Block>
    public var altSlabsFull: Array<Block>
    public var altStairs: Array<Block>

    public var barrier: Block

    public var kindling: Block

    val WOOD: Array<String> = arrayOf("irisWoodWhite", "irisWoodOrange", "irisWoodMagenta", "irisWoodLightBlue", "irisWoodYellow", "irisWoodLime", "irisWoodPink", "irisWoodGray", "irisWoodLightGray", "irisWoodCyan", "irisWoodPurple", "irisWoodBlue", "irisWoodBrown", "irisWoodGreen", "irisWoodRed", "irisWoodBlack")
    val LEAVES: Array<String> = arrayOf("irisLeavesWhite", "irisLeavesOrange", "irisLeavesMagenta", "irisLeavesLightBlue", "irisLeavesYellow", "irisLeavesLime", "irisLeavesPink", "irisLeavesGray", "irisLeavesLightGray", "irisLeavesCyan", "irisLeavesPurple", "irisLeavesBlue", "irisLeavesBrown", "irisLeavesGreen", "irisLeavesRed", "irisLeavesBlack")


    init {
        coloredDirtBlock = BlockColoredDirt()
        rainbowDirtBlock = BlockRainbowDirt()
        irisSapling = BlockColoredSapling()
        irisLeaves0 = BlockColoredLeaves(0)
        irisLeaves1 = BlockColoredLeaves(1)
        rainbowLeaves = BlockRainbowLeaves()
        irisGrass = BlockColoredGrass()
        rainbowGrass = BlockRainbowGrass()
        invisibleFlame = BlockManaFlame("invisibleFlame", TileInvisibleManaFlame::class.java)
        rainbowFlame = BlockManaFlame("rainbowFlame", TileRainbowManaFlame::class.java)

        irisWood0 = BlockColoredWood(0)
        irisWood1 = BlockColoredWood(1)
        irisWood2 = BlockColoredWood(2)
        irisWood3 = BlockColoredWood(3)
        rainbowWood = BlockRainbowWood()

        coloredPlanks = BlockColoredPlanks()
        rainbowPlanks = BlockRainbowPlanks()

        coloredSlabs = Array<Block>(16 , {i -> BlockColoredWoodSlab(false, i) })
        coloredSlabsFull = Array<Block>(16 , {i -> BlockColoredWoodSlab(true, i) })
        coloredStairs = Array<Block>(16 , {i -> BlockColoredWoodStairs(i) })

        rainbowSlabsFull = BlockRainbowWoodSlab(true)
        rainbowSlabs = BlockRainbowWoodSlab(false)
        rainbowStairs = BlockRainbowWoodStairs()

        irisTallGrass0 = BlockColoredDoubleGrass(0)
        irisTallGrass1 = BlockColoredDoubleGrass(1)
        rainbowTallGrass = BlockRainbowDoubleGrass()
        itemDisplay = BlockItemDisplay()
        treeCrafter = TileTreeCrafter.makeMultiblockSet()
        treeCrafterBlock = BlockTreeCrafter()
        treeCrafterBlockRB = BlockTreeCrafterRainbow()

        lightningSapling = BlockLightningSapling()
        lightningWood = BlockLightningWood()
        lightningLeaves = BlockLightningLeaves()
        lightningPlanks = BlockLightningPlanks()

        lightningSlabs = BlockLightningWoodSlab(false)
        lightningSlabsFull = BlockLightningWoodSlab(true)
        lightningStairs = BlockLightningWoodStairs()
        livingwoodFunnel = BlockFunnel()

        netherSapling = BlockNetherSapling()
        netherWood = BlockNetherWood()
        netherLeaves = BlockNetherLeaves()
        netherPlanks = BlockNetherPlanks()
        netherSlabs = BlockNetherWoodSlab(false)
        netherSlabsFull = BlockNetherWoodSlab(true)
        netherStairs = BlockNetherWoodStairs()

        altWood1 = BlockAltWood(0)
        altWood2 = BlockAltWood(1)
        altLeaves = BlockAltLeaves()
        altPlanks = BlockAltPlanks()

        altSlabs = Array<Block>(6 , {i -> BlockAltWoodSlab(false, i) })
        altSlabsFull = Array<Block>(6 , {i -> BlockAltWoodSlab(true, i) })
        altStairs = Array<Block>(6 , {i -> BlockAltWoodStairs(i) })

        barrier = BlockBarrier()

        kindling = BlockKindling()
        schemaBlock = BlockSchema()

        register()
        initOreDict()

        GameRegistry.registerTileEntity(TileInvisibleManaFlame::class.java, "shadowfox_botany:manaInvisibleFlame")
        GameRegistry.registerTileEntity(TileRainbowManaFlame::class.java, "shadowfox_botany:manaRainbowFlame")
        GameRegistry.registerTileEntity(TileItemDisplay::class.java, "shadowfox_botany:itemDisplay")
        GameRegistry.registerTileEntity(TileTreeCrafter::class.java, "shadowfox_botany:treeCrafter")
        GameRegistry.registerTileEntity(TileLivingwoodFunnel::class.java, "shadowfox_botany:livingwoodFunnel")

        BotaniaAPI.registerPaintableBlock(coloredDirtBlock)
    }

    fun registerBurnables() {
        setBurnable(irisWood0, 5, 5)
        setBurnable(irisWood1, 5, 5)
        setBurnable(irisWood2, 5, 5)
        setBurnable(irisWood3, 5, 5)
        setBurnable(rainbowWood, 5, 5)

        setBurnable(coloredPlanks, 5, 20)
        setBurnable(rainbowPlanks, 5, 20)

        for (i in 0..15) setBurnable(coloredSlabs[i], 5, 20)
        for (i in 0..15) setBurnable(coloredSlabsFull[i], 5, 20)
        setBurnable(rainbowSlabs, 5, 20)
        setBurnable(rainbowSlabsFull, 5, 20)

        for (i in 0..15) setBurnable(coloredStairs[i], 5, 20)
        setBurnable(rainbowStairs, 5, 20)

        setBurnable(irisLeaves0, 30, 60)
        setBurnable(irisLeaves1, 30, 60)
        setBurnable(rainbowLeaves, 30, 60)

        setBurnable(irisGrass, 60, 100)
        setBurnable(irisTallGrass0, 60, 100)
        setBurnable(irisTallGrass1, 60, 100)

        setBurnable(lightningLeaves, 30, 60)
        setBurnable(lightningWood, 5, 5)
        setBurnable(lightningPlanks, 5, 20)

        setBurnable(lightningSlabs, 5, 20)
        setBurnable(lightningSlabsFull, 5, 20)
        setBurnable(lightningStairs, 5, 20)

        setBurnable(rainbowGrass, 60, 100)
        setBurnable(rainbowTallGrass, 60, 100)
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

        (lightningSlabs as ShadowFoxSlabs).register()
        (lightningSlabsFull as ShadowFoxSlabs).register()
    }

    private fun initOreDict() {
        OreDictionary.registerOre("treeSapling", irisSapling)

        var t: ItemStack

        t = ItemStack(rainbowWood)
        OreDictionary.registerOre("irisWoodRainbow", t)

        OreDictionary.registerOre("irisDirt", ItemStack(rainbowDirtBlock))

        OreDictionary.registerOre("treeLeaves", ItemStack(lightningLeaves))
        OreDictionary.registerOre("plankWood", ItemStack(lightningPlanks))
        OreDictionary.registerOre("treeSapling", ItemStack(lightningSapling))

        OreDictionary.registerOre("slabWood", ItemStack(lightningSlabs))
        OreDictionary.registerOre("stairWood", ItemStack(lightningStairs))

        for (i in 0..3){
            t = ItemStack(irisWood0, 1, i)
            OreDictionary.registerOre(WOOD[i], t)

            t = ItemStack(irisWood1, 1, i)
            OreDictionary.registerOre(WOOD[i+4], t)

            t = ItemStack(irisWood2, 1, i)
            OreDictionary.registerOre(WOOD[i+8], t)

            t = ItemStack(irisWood3, 1, i)
            OreDictionary.registerOre(WOOD[i+12], t)
        }

        for (i in 0..7) {
            t = ItemStack(irisLeaves0, 1, i)
            OreDictionary.registerOre(LEAVES[i], t)

            t = ItemStack(irisLeaves1, 1, i)
            OreDictionary.registerOre(LEAVES[i+8], t)
        }

        for (i in 0..15) {
            OreDictionary.registerOre("irisDirt", ItemStack(coloredDirtBlock, 1, i))

            OreDictionary.registerOre("logWood", ItemStack(lightningWood, 1, i))

            t = ItemStack(rainbowWood, 1, i)
            OreDictionary.registerOre("logWood", t)
            OreDictionary.registerOre("irisWood", t)

            t = ItemStack(irisWood0, 1, i)
            OreDictionary.registerOre("logWood", t)
            OreDictionary.registerOre("irisWood", t)

            t = ItemStack(irisWood1, 1, i)
            OreDictionary.registerOre("logWood", t)
            OreDictionary.registerOre("irisWood", t)

            t = ItemStack(irisWood2, 1, i)
            OreDictionary.registerOre("logWood", t)
            OreDictionary.registerOre("irisWood", t)

            t = ItemStack(irisWood3, 1, i)
            OreDictionary.registerOre("logWood", t)
            OreDictionary.registerOre("irisWood", t)

            t = ItemStack(irisLeaves0, 1, i)
            OreDictionary.registerOre("treeLeaves", t)
            OreDictionary.registerOre("irisLeaves", t)

            t = ItemStack(irisLeaves1, 1, i)
            OreDictionary.registerOre("treeLeaves", t)
            OreDictionary.registerOre("irisLeaves", t)

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
