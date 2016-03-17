package ninja.shadowfox.shadowfox_botany.common.blocks


@Deprecated("1.7.10")
open class BlockTreeCrafter()
//name: String, val block: Block) : BlockContainerMod<TileTreeCrafter>(Material.wood), IWandHUD, ILexiconable, IMultipassRenderer {
//    internal var random: Random
//    override val registerInCreative: Boolean = false
//
//    init {
//        setHardness(3.0f)
//        setResistance(5.0f)
//        setLightLevel(1.0f)
//        setStepSound(Block.soundTypeWood)
//        setBlockName(name)
//        random = Random()
//    }
//
//    override fun isOpaqueCube(): Boolean {
//        return false
//    }
//
//    override fun createNewTileEntity(var1: World, var2: Int): TileTreeCrafter {
//        return TileTreeCrafter()
//    }
//
//    override fun hasComparatorInputOverride(): Boolean {
//        return true
//    }
//
//    override fun getComparatorInputOverride(par1World: World?, par2: Int, par3: Int, par4: Int, par5: Int): Int {
//        val crafter = par1World!!.getTileEntity(par2, par3, par4) as TileTreeCrafter
//        return crafter.signal
//    }
//
//    override fun getItemDropped(meta: Int, random: Random?, fortune: Int): Item {
//        return Item.getItemFromBlock(innerBlock(0))
//    }
//
//    override fun renderHUD(mc: Minecraft, res: ScaledResolution, world: World, x: Int, y: Int, z: Int) {
//        (world.getTileEntity(x, y, z) as TileTreeCrafter).renderHUD(mc, res)
//    }
//
//    override fun getEntry(p0: World?, p1: Int, p2: Int, p3: Int, p4: EntityPlayer?, p5: ItemStack?): LexiconEntry? {
//        return LexiconRegistry.treeCrafting
//    }
//
//    override fun renderAsNormalBlock(): Boolean = false
//
//    override fun canRenderInPass(pass: Int): Boolean {
//        MultipassRenderer.pass = pass
//        return true
//    }
//
//    override fun getRenderBlockPass(): Int = 1
//    override fun getRenderType(): Int = Constants.multipassRenderingID
//
//    override fun innerBlock(meta: Int): Block = block
//}
