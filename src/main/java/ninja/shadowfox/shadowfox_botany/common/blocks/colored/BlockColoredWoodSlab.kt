package ninja.shadowfox.shadowfox_botany.common.blocks.colored


@Deprecated("1.7.10")
class BlockColoredWoodSlab()
//full: Boolean, meta: Int, source: Block = ShadowFoxBlocks.coloredPlanks) :
//        BlockSlabMod(full, meta, source, source.unlocalizedName.replace("tile.".toRegex(), "") + "Slab" + (if (full) "Full" else "") + meta), ILexiconable, IFuelHandler {
//    init {
//        this.setResistance(10.0f)
//        GameRegistry.registerFuelHandler(this)
//    }
//
//    @SideOnly(Side.CLIENT)
//    override fun getRenderColor(m: Int): Int {
//        if (meta >= EntitySheep.fleeceColorTable.size)
//            return 0xFFFFFF
//
//        var color = EntitySheep.fleeceColorTable[meta]
//        return Color(color[0], color[1], color[2]).rgb
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
//    @SideOnly(Side.CLIENT)
//    override fun colorMultiplier(world: IBlockAccess?, x: Int, y: Int, z: Int): Int {
//        return getRenderColor(meta)
//    }
//
//    override fun getFullBlock(): BlockSlab {
//        return ShadowFoxBlocks.coloredSlabsFull[meta] as BlockSlab
//    }
//
//    override fun getSingleBlock(): BlockSlab {
//        return ShadowFoxBlocks.coloredSlabs[meta] as BlockSlab
//    }
//
//    override fun getEntry(world: World?, x: Int, y: Int, z: Int, player: EntityPlayer?, lexicon: ItemStack?): LexiconEntry {
//        return LexiconRegistry.irisSapling
//    }
//
//    override fun getBurnTime(fuel: ItemStack): Int {
//        return if (fuel.item == Item.getItemFromBlock(this)) 150 else 0
//    }
//}
