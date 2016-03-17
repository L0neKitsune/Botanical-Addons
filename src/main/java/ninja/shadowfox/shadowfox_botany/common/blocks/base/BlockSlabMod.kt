package ninja.shadowfox.shadowfox_botany.common.blocks.base


@Deprecated("1.7.10")
abstract class BlockSlabMod()//val full: Boolean, val meta: Int, val source: Block, val name: String) :
//        BlockSlab(full, source.material), ILexiconable {
//
//    init {
//        this.setStepSound(source.stepSound)
//        this.setBlockName(name)
//        if (!full) {
//            setCreativeTab(ShadowFoxCreativeTab)
//            this.useNeighborBrightness = true
//        }
//
//    }
//
//    override fun getBlockHardness(world: World?, x: Int, y: Int, z: Int): Float {
//        return source.getBlockHardness(world, x, y, z)
//    }
//
//    override fun setBlockName(name: String?): Block? {
//        return super.setBlockName(name)
//    }
//
//    @SideOnly(Side.CLIENT)
//    override fun getIcon(par1: Int, par2: Int): IIcon {
//        return source.getIcon(par1, par2)
//    }
//
//    override fun getPickBlock(target: MovingObjectPosition?, world: World, x: Int, y: Int, z: Int): ItemStack {
//        return ItemStack(getSingleBlock())
//    }
//
//    override fun getItemDropped(meta: Int, random: Random?, fortune: Int): Item {
//        return Item.getItemFromBlock(this.getSingleBlock())
//    }
//
//    override fun quantityDropped(meta: Int, fortune: Int, random: Random): Int {
//        return super.quantityDropped(meta, fortune, random)
//    }
//
//    public override fun createStackedBlock(par1: Int): ItemStack {
//        return ItemStack(getSingleBlock())
//    }
//
//    @SideOnly(Side.CLIENT)
//    override fun registerBlockIcons(par1IconRegister: IIconRegister) {
//    }
//
//    open fun register() {
//        GameRegistry.registerBlock(this, ItemColoredSlabMod::class.java, name)
//    }
//
//    override fun func_150002_b(i: Int): String {
//        return this.name
//    }
//
//    abstract fun getFullBlock(): BlockSlab
//    abstract fun getSingleBlock(): BlockSlab
//
//}
