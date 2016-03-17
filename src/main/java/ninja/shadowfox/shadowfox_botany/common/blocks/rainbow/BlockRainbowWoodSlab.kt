package ninja.shadowfox.shadowfox_botany.common.blocks.rainbow


@Deprecated("1.7.10")
open class BlockRainbowWoodSlab()
//full: Boolean, source: Block = ShadowFoxBlocks.rainbowPlanks) : BlockSlabMod(full, 0, source, source.unlocalizedName.replace("tile.".toRegex(), "") + "Slab" + (if (full) "Full" else "")), IFuelHandler {
//    init {
//        this.setResistance(10.0f)
//        GameRegistry.registerFuelHandler(this)
//    }
//
//    override fun isToolEffective(type: String?, metadata: Int): Boolean {
//        return (type != null && type.equals("axe"))
//    }
//
//    override fun getHarvestTool(metadata: Int): String {
//        return "axe"
//    }
//
//    override fun getFullBlock(): BlockSlab {
//        return ShadowFoxBlocks.rainbowSlabsFull as BlockSlab
//    }
//
//    override fun getSingleBlock(): BlockSlab {
//        return ShadowFoxBlocks.rainbowSlabs as BlockSlab
//    }
//
//    override fun getEntry(world: World?, x: Int, y: Int, z: Int, player: EntityPlayer?, lexicon: ItemStack?): LexiconEntry {
//        return (source as ILexiconable).getEntry(world, x, y, z, player, lexicon)
//    }
//
//    override fun getBurnTime(fuel: ItemStack): Int {
//        return if (fuel.item == Item.getItemFromBlock(this)) 150 else 0
//    }
//}
