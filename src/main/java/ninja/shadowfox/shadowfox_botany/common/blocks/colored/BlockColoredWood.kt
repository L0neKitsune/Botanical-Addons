package ninja.shadowfox.shadowfox_botany.common.blocks.colored


@Deprecated("1.7.10")
class BlockColoredWood(val colorSet: Int)
//: ShadowFoxRotatedPillar(Material.wood) {
//
//    private val name = "irisWood$colorSet"
//
//    init {
//        blockHardness = 2F
//        setLightLevel(0f)
//        stepSound = Block.soundTypeWood
//
//        setBlockName(name)
//    }
//
//    override fun breakBlock(world: World, x: Int, y: Int, z: Int, block: Block, fortune: Int) {
//        var b0: Byte = 4
//        var i1: Int = b0 + 1
//
//        if (world.checkChunksExist(x - i1, y - i1, z - i1, x + i1, y + i1, z + i1)) {
//            for (j1 in -b0..b0) for (k1 in -b0..b0)
//                for (l1 in -b0..b0) {
//                    var blockInWorld: Block = world.getBlock(x + j1, y + k1, z + l1)
//                    if (blockInWorld.isLeaves(world, x + j1, y + k1, z + l1)) {
//                        blockInWorld.beginLeavesDecay(world, x + j1, y + k1, z + l1)
//                    }
//                }
//        }
//    }
//
//    override fun register(par1Str: String) {
//        GameRegistry.registerBlock(this, ItemIridescentWoodMod::class.java, par1Str)
//    }
//
//    @SideOnly(Side.CLIENT)
//    override fun getBlockColor(): Int {
//        return 0xFFFFFF
//    }
//
//    @SideOnly(Side.CLIENT)
//    fun colorMeta(meta: Int): Int {
//        return meta + (colorSet * 4)
//    }
//
//    /**
//     * Returns the color this block should be rendered. Used by leaves.
//     */
//    @SideOnly(Side.CLIENT)
//    override fun getRenderColor(meta: Int): Int {
//        if (colorMeta(meta and 3) >= EntitySheep.fleeceColorTable.size)
//            return 0xFFFFFF
//
//        var color = EntitySheep.fleeceColorTable[colorMeta(meta and 3)]
//        return Color(color[0], color[1], color[2]).rgb
//    }
//
//    @SideOnly(Side.CLIENT)
//    override fun colorMultiplier(world: IBlockAccess?, x: Int, y: Int, z: Int): Int {
//        val meta = world!!.getBlockMetadata(x, y, z)
//        return getRenderColor(meta)
//    }
//
//    override fun canSustainLeaves(world: IBlockAccess, x: Int, y: Int, z: Int): Boolean = true
//    override fun isWood(world: IBlockAccess, x: Int, y: Int, z: Int): Boolean = true
//
//    override fun getSubBlocks(item: Item?, tab: CreativeTabs?, list: MutableList<Any?>?) {
//        if (list != null && item != null) {
//            list.add(ItemStack(this, 1, 0))
//            list.add(ItemStack(this, 1, 1))
//            list.add(ItemStack(this, 1, 2))
//            list.add(ItemStack(this, 1, 3))
//        }
//    }
//
//    override fun getEntry(p0: World?, p1: Int, p2: Int, p3: Int, p4: EntityPlayer?, p5: ItemStack?): LexiconEntry? {
//        return LexiconRegistry.irisSapling
//    }
//}
