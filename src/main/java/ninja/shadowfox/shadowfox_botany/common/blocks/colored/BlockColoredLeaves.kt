package ninja.shadowfox.shadowfox_botany.common.blocks.colored

@Deprecated("1.7.10")
class BlockColoredLeaves(val colorSet: Int)
//: BlockLeavesMod() {
//
//    val TYPES: Int = 8
//
//    init {
//        setBlockName("irisLeaves$colorSet")
//    }
//
//    override fun quantityDropped(random: Random): Int {
//        return if (random.nextInt(20) == 0) 1 else 0
//    }
//
//    override fun register(name: String) {
//        GameRegistry.registerBlock(this, ItemIridescentLeavesMod::class.java, name)
//    }
//
//    @SideOnly(Side.CLIENT)
//    override fun getBlockColor(): Int = 0xFFFFFF
//
//    @SideOnly(Side.CLIENT)
//    override fun getRenderColor(meta: Int): Int {
//        var shiftedMeta = meta % TYPES + colorSet * TYPES
//        if (shiftedMeta >= EntitySheep.fleeceColorTable.size)
//            return 0xFFFFFF
//
//        var color = EntitySheep.fleeceColorTable[shiftedMeta]
//        return Color(color[0], color[1], color[2]).rgb
//    }
//
//    @SideOnly(Side.CLIENT)
//    override fun colorMultiplier(world: IBlockAccess?, x: Int, y: Int, z: Int): Int {
//        val meta = world!!.getBlockMetadata(x, y, z)
//        return getRenderColor(meta)
//    }
//
//    override fun getSubBlocks(item: Item?, tab: CreativeTabs?, list: MutableList<Any?>?) {
//        if (list != null && item != null)
//            for (i in 0..(TYPES - 1)) {
//                list.add(ItemStack(item, 1, i))
//            }
//    }
//
//    override fun getItemDropped(meta: Int, random: Random, fortune: Int): Item {
//        return Item.getItemFromBlock(ShadowFoxBlocks.irisSapling)
//    }
//
//    override fun func_150125_e(): Array<String> {
//        return arrayOf("ColoredLeaves")
//    }
//
//    override fun decayBit(): Int = 0x8
//
//    override fun getEntry(p0: World?, p1: Int, p2: Int, p3: Int, p4: EntityPlayer?, p5: ItemStack?): LexiconEntry? {
//        return LexiconRegistry.irisSapling
//    }
//}
