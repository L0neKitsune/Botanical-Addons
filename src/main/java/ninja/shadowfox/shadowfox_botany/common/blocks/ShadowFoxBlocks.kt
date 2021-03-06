package ninja.shadowfox.shadowfox_botany.common.blocks

import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.block.Block
import net.minecraft.init.Blocks
import net.minecraft.item.ItemStack
import net.minecraftforge.oredict.OreDictionary
import ninja.shadowfox.shadowfox_botany.ShadowfoxBotany
import ninja.shadowfox.shadowfox_botany.api.ShadowFoxAPI
import ninja.shadowfox.shadowfox_botany.api.trees.IIridescentSaplingVariant
import ninja.shadowfox.shadowfox_botany.common.blocks.alt_grass.*
import ninja.shadowfox.shadowfox_botany.common.blocks.base.BlockManaFlame
import ninja.shadowfox.shadowfox_botany.common.blocks.base.BlockSlabMod
import ninja.shadowfox.shadowfox_botany.common.blocks.colored.*
import ninja.shadowfox.shadowfox_botany.common.blocks.magic_trees.lightning_oak.*
import ninja.shadowfox.shadowfox_botany.common.blocks.magic_trees.nether_oak.*
import ninja.shadowfox.shadowfox_botany.common.blocks.magic_trees.sealing_oak.*
import ninja.shadowfox.shadowfox_botany.common.blocks.rainbow.*
import ninja.shadowfox.shadowfox_botany.common.blocks.schema.*
import ninja.shadowfox.shadowfox_botany.common.blocks.subtile.ShadowFoxSignature
import ninja.shadowfox.shadowfox_botany.common.blocks.subtile.SubTileCrysanthermum
import ninja.shadowfox.shadowfox_botany.common.blocks.tile.*
import ninja.shadowfox.shadowfox_botany.lib.LibOreDict
import vazkii.botania.api.BotaniaAPI
import vazkii.botania.api.lexicon.multiblock.MultiblockSet
import vazkii.botania.common.block.ModBlocks as BotaniaBlocks

object ShadowFoxBlocks {

    fun setBurnable(block: Block, encouragement: Int, flammablility: Int) {
        Blocks.fire.setFireInfo(block, encouragement, flammablility)
    }

    fun setBurnable(blocks: Array<Block>, encouragement: Int, flammablility: Int) {
        for (i in blocks)
            if (Block.getIdFromBlock(i) != -1)
                Blocks.fire.setFireInfo(i, encouragement, flammablility)
    }

    var coloredDirtBlock: Block
    var rainbowDirtBlock: Block
    var irisSapling: Block
    var irisLeaves0: Block
    var irisLeaves1: Block
    var rainbowLeaves: Block
    var irisGrass: Block
    var rainbowGrass: BlockRainbowGrass

    var irisWood0: Block
    var irisWood1: Block
    var irisWood2: Block
    var irisWood3: Block
    var rainbowWood: Block

    var coloredPlanks: Block
    var rainbowPlanks: Block

    var irisTallGrass0: Block
    var irisTallGrass1: Block
    var rainbowTallGrass: Block

    var coloredSlabs: Array<Block>
    var rainbowSlabs: Block

    var coloredSlabsFull: Array<Block>
    var rainbowSlabsFull: Block

    var coloredStairs: Array<Block>
    var rainbowStairs: Block

    var itemDisplay: Block
    var treeCrafter: MultiblockSet
    var treeCrafterBlock: Block
    var treeCrafterBlockRB: Block

    var lightningSapling: Block
    var lightningWood: Block
    var lightningLeaves: Block
    var lightningPlanks: Block

    var lightningStairs: Block
    var lightningSlabs: Block
    var lightningSlabsFull: Block

    var invisibleFlame: Block
    var rainbowFlame: Block
    var livingwoodFunnel: Block

    var netherSapling: Block
    var netherWood: Block
    var netherLeaves: Block
    var netherPlanks: Block
    var netherSlabs: Block
    var netherSlabsFull: Block
    var netherStairs: Block

    var altWood0: Block
    var altWood1: Block
    var altLeaves: Block
    var altPlanks: Block
    var altSlabs: Array<Block>
    var altSlabsFull: Array<Block>
    var altStairs: Array<Block>

    var barrier: Block

    var kindling: Block

    lateinit var markerBlock: Block
    lateinit var schemaBlock: Block
    lateinit var schemaGenBlock: Block
    lateinit var fillerBlock: Block
    lateinit var annihilatorBlock: Block

    var irisLamp: Block

    var sealingSapling: Block
    var sealingWood: Block
    var sealingLeaves: Block
    var sealingPlanks: Block
    var sealingSlabs: Block
    var sealingSlabsFull: Block
    var sealingStairs: Block

    var amp: Block

    var star: Block

    var shimmerQuartz: BlockShimmerQuartz
    var shimmerQuartzSlab: BlockShimmerQuartzSlab
    var shimmerQuartzSlabFull: BlockShimmerQuartzSlab
    var shimmerQuartzStairs: Block

    var iridescentTree0: IIridescentSaplingVariant
    var iridescentTree1: IIridescentSaplingVariant
    var iridescentTree2: IIridescentSaplingVariant
    var iridescentTree3: IIridescentSaplingVariant
    var bifrostTree: IIridescentSaplingVariant
    var altTree0: IIridescentSaplingVariant
    var altTree1: IIridescentSaplingVariant

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
        treeCrafterBlock = BlockTreeCrafter("treeCrafter", coloredPlanks)
        treeCrafterBlockRB = BlockTreeCrafter("treeCrafterRB", rainbowPlanks)

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

        if (ShadowfoxBotany.isDevEnv) {
            schemaBlock = BlockSchema()
            markerBlock = BlockMarker()
            schemaGenBlock = BlockSchematicOak()
            fillerBlock = BlockFiller()
            annihilatorBlock = BlockSchematicAnnihilator()
        }

        irisLamp = BlockColoredLamp()

        sealingSapling = BlockSealingSapling()
        sealingWood = BlockSealingWood()
        sealingLeaves = BlockSealingLeaves()
        sealingPlanks = BlockSealingPlanks()
        sealingSlabs = BlockSealingWoodSlab(false)
        sealingSlabsFull = BlockSealingWoodSlab(true)
        sealingStairs = BlockSealingWoodStairs()

        amp = BlockAmp()

        star = BlockStar()

        shimmerQuartz = BlockShimmerQuartz()
        shimmerQuartzSlab = BlockShimmerQuartzSlab(shimmerQuartz, false)
        shimmerQuartzSlabFull = BlockShimmerQuartzSlab(shimmerQuartz, true)
        shimmerQuartzStairs = BlockShimmerQuartzStairs(shimmerQuartz)

        register()
        initOreDict()

        GameRegistry.registerTileEntity(TileInvisibleManaFlame::class.java, "shadowfox_botany:manaInvisibleFlame")
        GameRegistry.registerTileEntity(TileRainbowManaFlame::class.java, "shadowfox_botany:manaRainbowFlame")
        GameRegistry.registerTileEntity(TileItemDisplay::class.java, "shadowfox_botany:itemDisplay")
        GameRegistry.registerTileEntity(TileTreeCrafter::class.java, "shadowfox_botany:treeCrafter")
        GameRegistry.registerTileEntity(TileLivingwoodFunnel::class.java, "shadowfox_botany:livingwoodFunnel")
        GameRegistry.registerTileEntity(TileLightningRod::class.java, "shadowfox_botany:lightningRod")
        GameRegistry.registerTileEntity(TileEntityStar::class.java, "shadowfox_botany:star")
        if (ShadowfoxBotany.isDevEnv) {
            GameRegistry.registerTileEntity(TileSchema::class.java, "shadowfox_botany:schema")
            GameRegistry.registerTileEntity(TileSchematicAnnihilator::class.java, "shadowfox_botany:schematicAnnihilator")
        }

        BotaniaAPI.registerPaintableBlock(coloredDirtBlock)

        iridescentTree0 = ShadowFoxAPI.addTreeVariant(coloredDirtBlock, irisWood0, irisLeaves0, 0, 3)
        iridescentTree1 = ShadowFoxAPI.addTreeVariant(coloredDirtBlock, irisWood1, irisLeaves0, 4, 7)
        iridescentTree2 = ShadowFoxAPI.addTreeVariant(coloredDirtBlock, irisWood2, irisLeaves1, 8, 11, 8)
        iridescentTree3 = ShadowFoxAPI.addTreeVariant(coloredDirtBlock, irisWood3, irisLeaves1, 12, 15, 8)
        bifrostTree = ShadowFoxAPI.addTreeVariant(rainbowDirtBlock, rainbowWood, rainbowLeaves)
        altTree0 = ShadowFoxAPI.addTreeVariant(BotaniaBlocks.altGrass, altWood0, altLeaves, 0, 3)
        altTree1 = ShadowFoxAPI.addTreeVariant(BotaniaBlocks.altGrass, altWood1, altLeaves, 4, 5)

        BotaniaAPI.registerSubTile("crysanthermum", SubTileCrysanthermum::class.java)
        BotaniaAPI.registerSubTileSignature(SubTileCrysanthermum::class.java, ShadowFoxSignature("crysanthermum"))
        BotaniaAPI.subTileMods.put("crysanthermum", "Botanical Addons")
        BotaniaAPI.addSubTileToCreativeMenu("crysanthermum")
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

        setBurnable(sealingLeaves, 30, 60)
        setBurnable(sealingWood, 5, 5)
        setBurnable(sealingPlanks, 5, 20)

        setBurnable(sealingSlabs, 5, 20)
        setBurnable(sealingSlabsFull, 5, 20)
        setBurnable(sealingStairs, 5, 20)

        setBurnable(amp, 5, 20)

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

        (sealingSlabs as BlockSlabMod).register()
        (sealingSlabsFull as BlockSlabMod).register()


        shimmerQuartzSlab.register()
        shimmerQuartzSlabFull.register()
    }

    private fun initOreDict() {

        OreDictionary.registerOre(LibOreDict.RAINBOW_FLOWER, ItemStack(rainbowGrass, 1, 1))
        OreDictionary.registerOre(LibOreDict.RAINBOW_DOUBLE_FLOWER, ItemStack(rainbowTallGrass, 1, 1))

        OreDictionary.registerOre(LibOreDict.MUSHROOM, ItemStack(BotaniaBlocks.mushroom, 1, OreDictionary.WILDCARD_VALUE))
        OreDictionary.registerOre(LibOreDict.MUSHROOM, ItemStack(rainbowGrass, 1, rainbowGrass.SHROOM))

        OreDictionary.registerOre("treeSapling", irisSapling)

        var t: ItemStack

        t = ItemStack(rainbowWood)
        OreDictionary.registerOre(LibOreDict.WOOD[16], t)

        OreDictionary.registerOre(LibOreDict.IRIS_DIRT, ItemStack(rainbowDirtBlock))
        OreDictionary.registerOre(LibOreDict.DIRT[16], ItemStack(rainbowDirtBlock))

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

        OreDictionary.registerOre("treeLeaves", ItemStack(sealingLeaves))
        OreDictionary.registerOre("plankWood", ItemStack(sealingPlanks))
        OreDictionary.registerOre("treeSapling", ItemStack(sealingSapling))

        OreDictionary.registerOre("slabWood", ItemStack(sealingSlabs))
        OreDictionary.registerOre("stairWood", ItemStack(sealingStairs))

        for (i in 0..3) {
            t = ItemStack(irisWood0, 1, i)
            OreDictionary.registerOre(LibOreDict.WOOD[i], t)

            t = ItemStack(irisWood1, 1, i)
            OreDictionary.registerOre(LibOreDict.WOOD[i + 4], t)

            t = ItemStack(irisWood2, 1, i)
            OreDictionary.registerOre(LibOreDict.WOOD[i + 8], t)

            t = ItemStack(irisWood3, 1, i)
            OreDictionary.registerOre(LibOreDict.WOOD[i + 12], t)
        }

        for (i in 0..7) {
            t = ItemStack(irisLeaves0, 1, i)
            OreDictionary.registerOre(LibOreDict.LEAVES[i], t)

            t = ItemStack(irisLeaves1, 1, i)
            OreDictionary.registerOre(LibOreDict.LEAVES[i + 8], t)
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
            OreDictionary.registerOre(LibOreDict.IRIS_DIRT, ItemStack(coloredDirtBlock, 1, i))
            OreDictionary.registerOre(LibOreDict.DIRT[i], ItemStack(coloredDirtBlock, 1, i))

            OreDictionary.registerOre("logWood", ItemStack(lightningWood, 1, i))
            OreDictionary.registerOre("logWood", ItemStack(netherWood, 1, i))
            OreDictionary.registerOre("logWood", ItemStack(sealingWood, 1, i))

            t = ItemStack(rainbowWood, 1, i)
            OreDictionary.registerOre("logWood", t)
            OreDictionary.registerOre(LibOreDict.IRIS_WOOD, t)

            t = ItemStack(irisWood0, 1, i)
            OreDictionary.registerOre("logWood", t)
            OreDictionary.registerOre(LibOreDict.IRIS_WOOD, t)

            t = ItemStack(irisWood1, 1, i)
            OreDictionary.registerOre("logWood", t)
            OreDictionary.registerOre(LibOreDict.IRIS_WOOD, t)

            t = ItemStack(irisWood2, 1, i)
            OreDictionary.registerOre("logWood", t)
            OreDictionary.registerOre(LibOreDict.IRIS_WOOD, t)

            t = ItemStack(irisWood3, 1, i)
            OreDictionary.registerOre("logWood", t)
            OreDictionary.registerOre(LibOreDict.IRIS_WOOD, t)

            t = ItemStack(altWood0, 1, i)
            OreDictionary.registerOre("logWood", t)

            t = ItemStack(altWood1, 1, i)
            OreDictionary.registerOre("logWood", t)

            t = ItemStack(irisLeaves0, 1, i)
            OreDictionary.registerOre("treeLeaves", t)
            OreDictionary.registerOre(LibOreDict.IRIS_LEAVES, t)

            t = ItemStack(irisLeaves1, 1, i)
            OreDictionary.registerOre("treeLeaves", t)
            OreDictionary.registerOre(LibOreDict.IRIS_LEAVES, t)

            t = ItemStack(coloredPlanks, 1, i)
            OreDictionary.registerOre("plankWood", t)

            t = ItemStack(altPlanks, 1, i)
            OreDictionary.registerOre("plankWood", t)

            t = ItemStack(coloredStairs[i], 1)
            OreDictionary.registerOre("stairWood", t)

            t = ItemStack(coloredSlabs[i], 1)
            OreDictionary.registerOre("slabWood", t)

            t = ItemStack(coloredSlabsFull[i], 1)
            OreDictionary.registerOre("slabWood", t)
        }

        t = ItemStack(rainbowLeaves)
        OreDictionary.registerOre("treeLeaves", t)
        OreDictionary.registerOre(LibOreDict.IRIS_LEAVES, t)
        OreDictionary.registerOre(LibOreDict.LEAVES[16], t)

        t = ItemStack(rainbowPlanks)
        OreDictionary.registerOre("plankWood", t)

        t = ItemStack(rainbowStairs)
        OreDictionary.registerOre("stairWood", t)

        t = ItemStack(rainbowSlabs)
        OreDictionary.registerOre("slabWood", t)
    }
}
