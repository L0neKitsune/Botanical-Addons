package ninja.shadowfox.shadowfox_botany.common.blocks.alt_grass


@Deprecated("1.7.10")
class BlockAltLeaves()
//: BlockLeavesMod()
//{
//
//    protected var icon_norm: Array<IIcon> = emptyArray()
//    protected var icon_opaque: Array<IIcon> = emptyArray()
//
//    init {
//        setBlockName("altLeaves")
//    }
//
//    @SideOnly(Side.CLIENT) override fun getBlockColor(): Int = 0xFFFFFF
//    @SideOnly(Side.CLIENT) override fun getRenderColor(meta: Int): Int = 0xFFFFFF
//    @SideOnly(Side.CLIENT) override fun colorMultiplier(world: IBlockAccess?, x: Int, y: Int, z: Int): Int = 0xFFFFFF
//
//    override fun register(name: String) {
//        GameRegistry.registerBlock(this, ItemUniqueSubtypedBlockMod::class.java, name)
//    }
//
//    override fun registerBlockIcons(iconRegister: IIconRegister) {
//        icon_norm = Array(6, { i -> IconHelper.forBlock(iconRegister, this, "${ALT_TYPES[i]}") })
//        icon_opaque = Array(6, { i -> IconHelper.forBlock(iconRegister, this, "${ALT_TYPES[i]}_opaque") })
//    }
//
//    override fun getIcon(side: Int, meta: Int): IIcon {
//        this.setGraphicsLevel(Minecraft.getMinecraft().gameSettings.fancyGraphics)
//        return if (this.field_150121_P) icon_norm[meta and decayBit().inv()] else icon_opaque[meta and decayBit().inv()]
//    }
//
//    override fun getItemDropped(meta: Int, random: Random, fortune: Int): Item {
//        return Item.getItemFromBlock(ShadowFoxBlocks.irisSapling)
//    }
//
//    override fun quantityDropped(random: Random): Int {
//        return if (random.nextInt(60) == 0) 1 else 0
//    }
//
//    override fun func_150125_e(): Array<out String>? {
//        return arrayOf("altLeaves")
//    }
//
//    override fun getSubBlocks(item: Item?, tab: CreativeTabs?, list: MutableList<Any?>?) {
//        if (list != null && item != null)
//            for (i in 0..5) {
//                list.add(ItemStack(item, 1, i))
//            }
//    }
//
//    override fun decayBit(): Int = 0x8
//
//    override fun getEntry(p0: World?, p1: Int, p2: Int, p3: Int, p4: EntityPlayer?, p5: ItemStack?): LexiconEntry? {
//        return LexiconRegistry.irisSapling
//    }
//}
