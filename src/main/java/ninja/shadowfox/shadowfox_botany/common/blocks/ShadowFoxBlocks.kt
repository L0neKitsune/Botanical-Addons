package ninja.shadowfox.shadowfox_botany.common.blocks

import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.block.Block
import net.minecraft.init.Blocks
import net.minecraft.item.ItemStack
import net.minecraftforge.oredict.OreDictionary
import ninja.shadowfox.shadowfox_botany.api.ShadowFoxAPI
import ninja.shadowfox.shadowfox_botany.api.trees.IIridescentSaplingVariant
import ninja.shadowfox.shadowfox_botany.common.blocks.alt_grass.*
import ninja.shadowfox.shadowfox_botany.common.blocks.base.BlockManaFlame
import ninja.shadowfox.shadowfox_botany.common.blocks.base.BlockSlabMod
import ninja.shadowfox.shadowfox_botany.common.blocks.colored.*
import ninja.shadowfox.shadowfox_botany.common.blocks.magic_trees.lightning_oak.*
import ninja.shadowfox.shadowfox_botany.common.blocks.magic_trees.nether_oak.*
import ninja.shadowfox.shadowfox_botany.common.blocks.rainbow.*
import ninja.shadowfox.shadowfox_botany.common.blocks.schema.*
import ninja.shadowfox.shadowfox_botany.common.blocks.tile.*
import vazkii.botania.api.BotaniaAPI
import vazkii.botania.api.lexicon.multiblock.MultiblockSet
import vazkii.botania.common.block.ModBlocks as BotaniaBlocks

public object ShadowFoxBlocks {

    fun setBurnable(block: Block, encouragement: Int, flammablility: Int) {
        Blocks.fire.setFireInfo(block, encouragement, flammablility)
    }

    fun setBurnable(blocks: Array<Block>, encouragement: Int, flammablility: Int) {
        for (i in blocks)
            if (Block.getIdFromBlock(i) != -1)
                Blocks.fire.setFireInfo(i, encouragement, flammablility)
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

    public var netherSapling: Block
    public var netherWood: Block
    public var netherLeaves: Block
    public var netherPlanks: Block
    public var netherSlabs: Block
    public var netherSlabsFull: Block
    public var netherStairs: Block

    public var altWood0: Block
    public var altWood1: Block
    public var altLeaves: Block
    public var altPlanks: Block
    public var altSlabs: Array<Block>
    public var altSlabsFull: Array<Block>
    public var altStairs: Array<Block>

    public var barrier: Block

    public var kindling: Block

    public var markerBlock: Block
    public var schemaBlock: Block
    public var schemaGenBlock: Block
    public var fillerBlock: Block
    public var annihilatorBlock: Block

    public var irisLamp: Block

    public var iridescentTree0: IIridescentSaplingVariant
    public var iridescentTree1: IIridescentSaplingVariant
    public var iridescentTree2: IIridescentSaplingVariant
    public var iridescentTree3: IIridescentSaplingVariant
    public var bifrostTree: IIridescentSaplingVariant
    public var altTree0: IIridescentSaplingVariant
    public var altTree1: IIridescentSaplingVariant

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

        coloredSlabs = Array<Block>(16, { i -> BlockColoredWoodSlab(false, i) })
        coloredSlabsFull = Array<Block>(16, { i -> BlockColoredWoodSlab(true, i) })
        coloredStairs = Array<Block>(16, { i -> BlockColoredWoodStairs(i) })

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

        altWood0 = BlockAltWood(0)
        altWood1 = BlockAltWood(1)
        altLeaves = BlockAltLeaves()
        altPlanks = BlockAltPlanks()

        altSlabs = Array<Block>(6, { i -> BlockAltWoodSlab(false, i) })
        altSlabsFull = Array<Block>(6, { i -> BlockAltWoodSlab(true, i) })
        altStairs = Array<Block>(6, { i -> BlockAltWoodStairs(i) })

        barrier = BlockBarrier()

        kindling = BlockKindling()
        schemaBlock = BlockSchema()
        markerBlock = BlockMarker()
        schemaGenBlock = BlockSchematicOak()
        fillerBlock = BlockFiller()
        annihilatorBlock = BlockSchematicAnnihilator()

        irisLamp = BlockColoredLamp()

        register()
        initOreDict()

        GameRegistry.registerTileEntity(TileInvisibleManaFlame::class.java, "shadowfox_botany:manaInvisibleFlame")
        GameRegistry.registerTileEntity(TileRainbowManaFlame::class.java, "shadowfox_botany:manaRainbowFlame")
        GameRegistry.registerTileEntity(TileItemDisplay::class.java, "shadowfox_botany:itemDisplay")
        GameRegistry.registerTileEntity(TileTreeCrafter::class.java, "shadowfox_botany:treeCrafter")
        GameRegistry.registerTileEntity(TileLivingwoodFunnel::class.java, "shadowfox_botany:livingwoodFunnel")
        GameRegistry.registerTileEntity(TileLightningRod::class.java, "shadowfox_botany:lightningRod")
        GameRegistry.registerTileEntity(TileSchema::class.java, "shadowfox_botany:schema")
        GameRegistry.registerTileEntity(TileSchematicAnnihilator::class.java, "shadowfox_botany:schematicAnnihilator")

        BotaniaAPI.registerPaintableBlock(coloredDirtBlock)

        iridescentTree0 = ShadowFoxAPI.addTreeVariant(coloredDirtBlock, irisWood0, irisLeaves0, 0, 3)
        iridescentTree1 = ShadowFoxAPI.addTreeVariant(coloredDirtBlock, irisWood1, irisLeaves0, 4, 7)
        iridescentTree2 = ShadowFoxAPI.addTreeVariant(coloredDirtBlock, irisWood2, irisLeaves1, 8, 11, 8)
        iridescentTree3 = ShadowFoxAPI.addTreeVariant(coloredDirtBlock, irisWood3, irisLeaves1, 12, 15, 8)
        bifrostTree = ShadowFoxAPI.addTreeVariant(rainbowDirtBlock, rainbowWood, rainbowLeaves)
        altTree0 = ShadowFoxAPI.addTreeVariant(BotaniaBlocks.altGrass, altWood0, altLeaves, 0, 3)
        altTree1 = ShadowFoxAPI.addTreeVariant(BotaniaBlocks.altGrass, altWood1, altLeaves, 4, 5)
    }

    fun registerBurnables() {
        setBurnable(irisWood0, 5, 5)
        setBurnable(irisWood1, 5, 5)
        setBurnable(irisWood2, 5, 5)
        setBurnable(irisWood3, 5, 5)
        setBurnable(rainbowWood, 5, 5)
        setBurnable(altWood0, 5, 5)
        setBurnable(altWood1, 5, 5)

        setBurnable(coloredPlanks, 5, 20)
        setBurnable(rainbowPlanks, 5, 20)
        setBurnable(altPlanks, 5, 20)

        setBurnable(coloredSlabs, 5, 20)
        setBurnable(coloredSlabsFull, 5, 20)
        setBurnable(rainbowSlabs, 5, 20)
        setBurnable(rainbowSlabsFull, 5, 20)
        setBurnable(altSlabs, 5, 20)
        setBurnable(altSlabsFull, 5, 20)

        setBurnable(coloredStairs, 5, 20)
        setBurnable(rainbowStairs, 5, 20)
        setBurnable(altStairs, 5, 20)

        setBurnable(irisLeaves0, 30, 60)
        setBurnable(irisLeaves1, 30, 60)
        setBurnable(rainbowLeaves, 30, 60)
        setBurnable(altLeaves, 30, 60)

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
    private fun register() {
        for (i in coloredSlabs) {
            (i as BlockSlabMod).register()
        }

        for (i in coloredSlabsFull) {
            (i as BlockSlabMod).register()
        }

        (rainbowSlabs as BlockSlabMod).register()
        (rainbowSlabsFull as BlockSlabMod).register()

        (lightningSlabs as BlockSlabMod).register()
        (lightningSlabsFull as BlockSlabMod).register()

        (netherSlabs as BlockSlabMod).register()
        (netherSlabsFull as BlockSlabMod).register()

        for (i in altSlabs) {
            (i as BlockSlabMod).register()
        }

        for (i in altSlabsFull) {
            (i as BlockSlabMod).register()
        }
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

        OreDictionary.registerOre("treeLeaves", ItemStack(netherLeaves))
        OreDictionary.registerOre("plankWood", ItemStack(netherPlanks))
        OreDictionary.registerOre("treeSapling", ItemStack(netherSapling))

        OreDictionary.registerOre("slabWood", ItemStack(netherSlabs))
        OreDictionary.registerOre("stairWood", ItemStack(netherStairs))

        for (i in 0..3) {
            t = ItemStack(irisWood0, 1, i)
            OreDictionary.registerOre(WOOD[i], t)

            t = ItemStack(irisWood1, 1, i)
            OreDictionary.registerOre(WOOD[i + 4], t)

            t = ItemStack(irisWood2, 1, i)
            OreDictionary.registerOre(WOOD[i + 8], t)

            t = ItemStack(irisWood3, 1, i)
            OreDictionary.registerOre(WOOD[i + 12], t)
        }

        for (i in 0..7) {
            t = ItemStack(irisLeaves0, 1, i)
            OreDictionary.registerOre(LEAVES[i], t)

            t = ItemStack(irisLeaves1, 1, i)
            OreDictionary.registerOre(LEAVES[i + 8], t)
        }

        for (i in 0..5) {
            t = ItemStack(altStairs[i], 1)
            OreDictionary.registerOre("stairWood", t)

            t = ItemStack(altSlabs[i], 1)
            OreDictionary.registerOre("slabWood", t)

            t = ItemStack(altSlabsFull[i], 1)
            OreDictionary.registerOre("slabWood", t)

            t = ItemStack(altLeaves, 1, i)
            OreDictionary.registerOre("treeLeaves", t)
        }

        for (i in 0..15) {
            OreDictionary.registerOre("irisDirt", ItemStack(coloredDirtBlock, 1, i))

            OreDictionary.registerOre("logWood", ItemStack(lightningWood, 1, i))
            OreDictionary.registerOre("logWood", ItemStack(netherWood, 1, i))

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

            t = ItemStack(altWood0, 1, i)
            OreDictionary.registerOre("logWood", t)

            t = ItemStack(altWood1, 1, i)
            OreDictionary.registerOre("logWood", t)

            t = ItemStack(irisLeaves0, 1, i)
            OreDictionary.registerOre("treeLeaves", t)
            OreDictionary.registerOre("irisLeaves", t)

            t = ItemStack(irisLeaves1, 1, i)
            OreDictionary.registerOre("treeLeaves", t)
            OreDictionary.registerOre("irisLeaves", t)

            t = ItemStack(coloredPlanks, 1, i)
            OreDictionary.registerOre("plankWood", t)
            OreDictionary.registerOre("irisPlanks", t)

            t = ItemStack(altPlanks, 1, i)
            OreDictionary.registerOre("plankWood", t)

            t = ItemStack(coloredStairs[i], 1)
            OreDictionary.registerOre("stairWood", t)
            OreDictionary.registerOre("irisStairs", t)

            t = ItemStack(coloredSlabs[i], 1)
            OreDictionary.registerOre("slabWood", t)
            OreDictionary.registerOre("irisSlabs", t)

            t = ItemStack(coloredSlabsFull[i], 1)
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
