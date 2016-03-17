package ninja.shadowfox.shadowfox_botany.common.blocks.alt_grass


@Deprecated("1.7.10")
class BlockAltWood(val set: Int)
//: ShadowFoxRotatedPillar(Material.wood), ILexiconable {
//
//    lateinit protected var iconsTop: Array<IIcon?>
//    lateinit protected var iconsSide: Array<IIcon?>
//
//    init {
//        setBlockName("altWood$set")
//        isBlockContainer = true
//        blockHardness = 2F
//        if (FMLLaunchHandler.side().isClient)
//            MinecraftForge.EVENT_BUS.register(this)
//    }
//
//    override fun canSustainLeaves(world: IBlockAccess, x: Int, y: Int, z: Int): Boolean = true
//    override fun isWood(world: IBlockAccess?, x: Int, y: Int, z: Int): Boolean = true
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
//        super.breakBlock(world, x, y, z, block, fortune)
//    }
//
//    override fun damageDropped(meta: Int): Int = meta and 3
//    override fun quantityDropped(random: Random): Int = 1
//
//    override fun register(par1Str: String) {
//        GameRegistry.registerBlock(this, ItemUniqueSubtypedBlockMod::class.java, par1Str)
//    }
//
//    override fun getTopIcon(meta: Int): IIcon? {
//        return iconsTop[meta % (if (set == 1) 2 else 4)]
//    }
//
//    override fun getSideIcon(meta: Int): IIcon? {
//        return iconsSide[meta % (if (set == 1) 2 else 4)]
//    }
//
//    override fun getSubBlocks(item: Item?, tab: CreativeTabs?, list: MutableList<Any?>?) {
//        if (list != null && item != null) {
//            if (set == 0) {
//                list.add(ItemStack(this, 1, 0))
//                list.add(ItemStack(this, 1, 1))
//                list.add(ItemStack(this, 1, 2))
//                list.add(ItemStack(this, 1, 3))
//            } else {
//                list.add(ItemStack(this, 1, 0))
//                list.add(ItemStack(this, 1, 1))
//            }
//        }
//    }
//
//    @SideOnly(Side.CLIENT)
//    override fun registerBlockIcons(par1IconRegister: IIconRegister) {
//        iconsTop = Array(if (set == 0 ) 4 else 2, {
//            i ->
//            if (set == 0 && i == 3) null else IconHelper.forName(par1IconRegister, "altOak${ALT_TYPES[(set * 4) + i]}Top")
//        })
//        iconsSide = Array(if (set == 0 ) 4 else 2, {
//            i ->
//            if (set == 0 && i == 3) null else IconHelper.forName(par1IconRegister, "altOak${ALT_TYPES[(set * 4) + i]}Side")
//        })
//    }
//
//    @SubscribeEvent
//    @SideOnly(Side.CLIENT)
//    override fun loadTextures(event: TextureStitchEvent.Pre) {
//        if (event.map.textureType == 0 && set == 0) {
//            iconsTop[3] = InterpolatedIconHelper.forName(event.map, "altOak${ALT_TYPES[3]}Top")
//            iconsSide[3] = InterpolatedIconHelper.forName(event.map, "altOak${ALT_TYPES[3]}Side")
//        }
//    }
//
//    override fun getEntry(p0: World?, p1: Int, p2: Int, p3: Int, p4: EntityPlayer?, p5: ItemStack?): LexiconEntry? {
//        return LexiconRegistry.irisSapling
//    }
//}
