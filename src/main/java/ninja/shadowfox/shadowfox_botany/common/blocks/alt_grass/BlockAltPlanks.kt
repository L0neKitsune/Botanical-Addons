package ninja.shadowfox.shadowfox_botany.common.blocks.alt_grass


@Deprecated("1.7.10")
class BlockAltPlanks()
//: BlockMod(MaterialCustomSmeltingWood.instance), ILexiconable, IFuelHandler {
//
//    private val name = "altPlanks"
//    protected var icons: Array<IIcon?> = emptyArray()
//
//    init {
//        blockHardness = 2F
//        setLightLevel(0f)
//        stepSound = soundTypeWood
//
//        setBlockName(this.name)
//        GameRegistry.registerFuelHandler(this)
//        if (FMLLaunchHandler.side().isClient)
//            MinecraftForge.EVENT_BUS.register(this)
//    }
//
//    override fun registerBlockIcons(par1IconRegister: IIconRegister) {
//        icons = Array(6, {
//            i ->
//            if (i == 3) null else IconHelper.forBlock(par1IconRegister, this, "${ALT_TYPES[i]}")
//        })
//    }
//
//    @SubscribeEvent
//    @SideOnly(Side.CLIENT)
//    override fun loadTextures(event: TextureStitchEvent.Pre) {
//        if (event.map.textureType == 0) {
//            icons[3] = InterpolatedIconHelper.forBlock(event.map, this, "${ALT_TYPES[3]}")
//        }
//    }
//
//    override fun getIcon(side: Int, meta: Int): IIcon? {
//        return icons[meta % 6]
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
//    override fun shouldRegisterInNameSet(): Boolean {
//        return false
//    }
//
//    override fun damageDropped(par1: Int): Int {
//        return par1
//    }
//
//    override fun setBlockName(par1Str: String): Block {
//        register(par1Str)
//        return super.setBlockName(par1Str)
//    }
//
//    override fun quantityDropped(random: Random): Int {
//        return 1
//    }
//
//    override fun getItemDropped(meta: Int, random: Random, fortune: Int): Item {
//        return Item.getItemFromBlock(this)
//    }
//
//    override fun isFlammable(world: IBlockAccess?, x: Int, y: Int, z: Int, face: ForgeDirection?): Boolean = false
//    override fun getFireSpreadSpeed(world: IBlockAccess?, x: Int, y: Int, z: Int, face: ForgeDirection?): Int = 0
//
//    internal fun register(name: String) {
//        GameRegistry.registerBlock(this, ItemUniqueSubtypedBlockMod::class.java, name)
//    }
//
//    override fun getSubBlocks(item: Item?, tab: CreativeTabs?, list: MutableList<Any?>?) {
//        if (list != null && item != null)
//            for (i in 0..5) {
//                list.add(ItemStack(item, 1, i))
//            }
//    }
//
//    override fun getPickBlock(target: MovingObjectPosition?, world: World, x: Int, y: Int, z: Int): ItemStack {
//        val meta = world.getBlockMetadata(x, y, z)
//        return ItemStack(this, 1, meta)
//    }
//
//    override fun getEntry(p0: World?, p1: Int, p2: Int, p3: Int, p4: EntityPlayer?, p5: ItemStack?): LexiconEntry? {
//        return LexiconRegistry.irisSapling
//    }
//
//    override fun getBurnTime(fuel: ItemStack): Int {
//        return if (fuel.item == Item.getItemFromBlock(this)) 300 else 0
//    }
//}
