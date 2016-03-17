package ninja.shadowfox.shadowfox_botany.common.blocks


@Deprecated("1.7.10")
class BlockFunnel()
//: BlockContainerMod<TileLivingwoodFunnel>(Material.wood), IWandHUD, ILexiconable {
//    private val random = Random()
//    lateinit var top_icon: IIcon
//    lateinit var inside_icon: IIcon
//    lateinit var outside_icon: IIcon
//
//    companion object {
//        @SideOnly(Side.CLIENT)
//        fun getHopperIcon(string: String): IIcon? {
//            when (string) {
//                "funnel_top" -> return (ShadowFoxBlocks.livingwoodFunnel as BlockFunnel).top_icon
//                "funnel_inside" -> return (ShadowFoxBlocks.livingwoodFunnel as BlockFunnel).inside_icon
//                else -> return (ShadowFoxBlocks.livingwoodFunnel as BlockFunnel).outside_icon
//            }
//        }
//
//        fun getDirectionFromMetadata(meta: Int): Int = meta and 7
//        fun getActiveStateFromMetadata(meta: Int): Boolean = (meta and 8) != 8
//    }
//
//    init {
//        this.setBlockName("livingwoodFunnel")
//        blockHardness = 2f
//
//        this.setCreativeTab(ShadowFoxCreativeTab)
//        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f)
//    }
//
//    override fun isSideSolid(world: IBlockAccess?, x: Int, y: Int, z: Int, side: ForgeDirection?): Boolean {
//        return side == ForgeDirection.UP
//    }
//
//    /**
//     * Updates the blocks bounds based on its current state. Args: world, x, y, z
//     */
//    override fun setBlockBoundsBasedOnState(world: IBlockAccess?, x: Int, y: Int, z: Int) {
//        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f)
//    }
//
//    override fun addCollisionBoxesToList(world: World?, x: Int, y: Int, z: Int, axis: AxisAlignedBB?, bounds: MutableList<Any?>?, entity: Entity?) {
//        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.625f, 1.0f)
//        super.addCollisionBoxesToList(world, x, y, z, axis, bounds, entity)
//        val f = 0.125f
//        this.setBlockBounds(0.0f, 0.0f, 0.0f, f, 1.0f, 1.0f)
//        super.addCollisionBoxesToList(world, x, y, z, axis, bounds, entity)
//        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, f)
//        super.addCollisionBoxesToList(world, x, y, z, axis, bounds, entity)
//        this.setBlockBounds(1.0f - f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f)
//        super.addCollisionBoxesToList(world, x, y, z, axis, bounds, entity)
//        this.setBlockBounds(0.0f, 0.0f, 1.0f - f, 1.0f, 1.0f, 1.0f)
//        super.addCollisionBoxesToList(world, x, y, z, axis, bounds, entity)
//        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f)
//    }
//
//    /**
//     * Called when a block is placed using its ItemBlock. Args: World, X, Y, Z, side, hitX, hitY, hitZ, block metadata
//     */
//    override fun onBlockPlaced(world: World?, x: Int, y: Int, z: Int, side: Int, hitX: Float, hitY: Float, hitZ: Float, meta: Int): Int {
//        var j1 = Facing.oppositeSide[side]
//
//        if (j1 == 1) j1 = 0
//        return j1
//    }
//
//    override fun createNewTileEntity(var1: World, var2: Int): TileLivingwoodFunnel = TileLivingwoodFunnel()
//
//    /**
//     * Called whenever the block is added into the world. Args: world, x, y, z
//     */
//    override fun onBlockAdded(world: World?, x: Int, y: Int, z: Int) {
//        super.onBlockAdded(world, x, y, z)
//        if (world != null) this.updateBlockData(world, x, y, z)
//    }
//
//    /**
//     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
//     * their own) Args: x, y, z, neighbor Block
//     */
//    override fun onNeighborBlockChange(world: World?, x: Int, y: Int, z: Int, neighbor: Block?) {
//        if (world != null) this.updateBlockData(world, x, y, z)
//    }
//
//    private fun updateBlockData(world: World, x: Int, y: Int, z: Int) {
//        val l = world.getBlockMetadata(x, y, z)
//        val i1 = getDirectionFromMetadata(l)
//        val flag = !world.isBlockIndirectlyGettingPowered(x, y, z)
//        val flag1 = getActiveStateFromMetadata(l)
//
//        if (flag != flag1) {
//            world.setBlockMetadataWithNotify(x, y, z, i1 or (if (flag) 0 else 8), 4)
//        }
//    }
//
//    override fun renderHUD(mc: Minecraft, res: ScaledResolution, world: World, x: Int, y: Int, z: Int) {
//        (world.getTileEntity(x, y, z) as TileLivingwoodFunnel).renderHUD(mc, res)
//    }
//
//
//    override fun breakBlock(world: World, x: Int, y: Int, z: Int, block: Block?, meta: Int) {
//        val tile = world.getTileEntity(x, y, z)
//
//        if (tile != null && tile is TileLivingwoodFunnel) {
//            for (i1 in 0..tile.sizeInventory - 1) {
//                val itemstack = tile.getStackInSlot(i1)
//
//                if (itemstack != null) {
//                    val f = this.random.nextFloat() * 0.8f + 0.1f
//                    val f1 = this.random.nextFloat() * 0.8f + 0.1f
//                    val f2 = this.random.nextFloat() * 0.8f + 0.1f
//
//                    while (itemstack.stackSize > 0) {
//                        var j1 = this.random.nextInt(21) + 10
//
//                        if (j1 > itemstack.stackSize) {
//                            j1 = itemstack.stackSize
//                        }
//
//                        itemstack.stackSize -= j1
//                        val entityitem = EntityItem(world, (x.toFloat() + f).toDouble(), (y.toFloat() + f1).toDouble(), (z.toFloat() + f2).toDouble(), ItemStack(itemstack.item, j1, itemstack.itemDamage))
//
//                        if (itemstack.hasTagCompound()) {
//                            entityitem.entityItem.tagCompound = itemstack.tagCompound.copy() as NBTTagCompound
//                        }
//
//                        val f3 = 0.05f
//                        entityitem.motionX = (this.random.nextGaussian().toFloat() * f3).toDouble()
//                        entityitem.motionY = (this.random.nextGaussian().toFloat() * f3 + 0.2f).toDouble()
//                        entityitem.motionZ = (this.random.nextGaussian().toFloat() * f3).toDouble()
//                        world.spawnEntityInWorld(entityitem)
//                    }
//                }
//            }
//
//            world.func_147453_f(x, y, z, block)
//        }
//
//        super.breakBlock(world, x, y, z, block, meta)
//    }
//
//    /**
//     * The type of render function that is called for this block
//     */
//    override fun getRenderType(): Int {
//        return Constants.hopperRenderingID
//    }
//
//    /**
//     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
//     */
//    override fun renderAsNormalBlock(): Boolean {
//        return false
//    }
//
//    /**
//     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
//     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
//     */
//    override fun isOpaqueCube(): Boolean {
//        return false
//    }
//
//    /**
//     * Returns true if the given side of this block type should be rendered, if the adjacent block is at the given
//     * coordinates.  Args: blockAccess, x, y, z, side
//     */
//    @SideOnly(Side.CLIENT)
//    override fun shouldSideBeRendered(world: IBlockAccess, x: Int, y: Int, z: Int, side: Int): Boolean {
//        return true
//    }
//
//    /**
//     * Gets the block's texture. Args: side, meta
//     */
//    @SideOnly(Side.CLIENT)
//    override fun getIcon(side: Int, meta: Int): IIcon {
//        if (side == 1) return top_icon
//        return outside_icon
//    }
//
//    /**
//     * If this returns true, then comparators facing away from this block will use the value from
//     * getComparatorInputOverride instead of the actual redstone signal strength.
//     */
//    override fun hasComparatorInputOverride(): Boolean = true
//
//    /**
//     * If hasComparatorInputOverride returns true, the return value from this is used instead of the redstone signal
//     * strength when this block inputs to a comparator.
//     */
//    override fun getComparatorInputOverride(world: World?, x: Int, y: Int, z: Int, side: Int): Int {
//        if (world == null) return 0
//        return Container.calcRedstoneFromInventory(getTile(world, x, y, z))
//    }
//
//    @SideOnly(Side.CLIENT)
//    override fun registerBlockIcons(par1IconRegister: IIconRegister) {
//        top_icon = IconHelper.forName(par1IconRegister, "funnel_top")
//        inside_icon = IconHelper.forName(par1IconRegister, "funnel_inside")
//        outside_icon = IconHelper.forName(par1IconRegister, "funnel_outside")
//    }
//
//    fun getTile(world: IBlockAccess, x: Int, y: Int, z: Int): TileLivingwoodFunnel? {
//        return world.getTileEntity(x, y, z) as TileLivingwoodFunnel
//    }
//
//    /**
//     * Gets the icon name of the ItemBlock corresponding to this block. Used by hoppers.
//     */
//    @SideOnly(Side.CLIENT)
//    override fun getItemIconName(): String = "shadowfox_botany:livingwood_funnel"
//
//    class HopperRenderer : ISimpleBlockRenderingHandler {
//        override fun getRenderId(): Int {
//            return Constants.hopperRenderingID
//        }
//
//        override fun shouldRender3DInInventory(modelId: Int): Boolean {
//            return false
//        }
//
//        override fun renderInventoryBlock(block: Block, metadata: Int, modelID: Int, renderer: RenderBlocks) {
//        }
//
//        override fun renderWorldBlock(world: IBlockAccess, x: Int, y: Int, z: Int, block: Block, modelId: Int, renderer: RenderBlocks): Boolean {
//            val tessellator = Tessellator.instance
//            tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z))
//            val l = block.colorMultiplier(world, x, y, z)
//            var f = (l shr 16 and 255).toFloat() / 255.0f
//            var f1 = (l shr 8 and 255).toFloat() / 255.0f
//            var f2 = (l and 255).toFloat() / 255.0f
//
//            if (EntityRenderer.anaglyphEnable) {
//                val f3 = (f * 30.0f + f1 * 59.0f + f2 * 11.0f) / 100.0f
//                val f4 = (f * 30.0f + f1 * 70.0f) / 100.0f
//                val f5 = (f * 30.0f + f2 * 70.0f) / 100.0f
//                f = f3
//                f1 = f4
//                f2 = f5
//            }
//
//            tessellator.setColorOpaque_F(f, f1, f2)
//            val meta = world.getBlockMetadata(x, y, z)
//
//            val i1 = BlockFunnel.getDirectionFromMetadata(meta)
//            val d0 = 0.625
//            renderer.setRenderBounds(0.0, d0, 0.0, 1.0, 1.0, 1.0)
//
//            val flag = false
//
//            if (flag) {
//                tessellator.startDrawingQuads()
//                tessellator.setNormal(0.0f, -1.0f, 0.0f)
//                renderer.renderFaceYNeg(block, 0.0, 0.0, 0.0, renderer.getBlockIconFromSideAndMetadata(block, 0, meta))
//                tessellator.draw()
//                tessellator.startDrawingQuads()
//                tessellator.setNormal(0.0f, 1.0f, 0.0f)
//                renderer.renderFaceYPos(block, 0.0, 0.0, 0.0, renderer.getBlockIconFromSideAndMetadata(block, 1, meta))
//                tessellator.draw()
//                tessellator.startDrawingQuads()
//                tessellator.setNormal(0.0f, 0.0f, -1.0f)
//                renderer.renderFaceZNeg(block, 0.0, 0.0, 0.0, renderer.getBlockIconFromSideAndMetadata(block, 2, meta))
//                tessellator.draw()
//                tessellator.startDrawingQuads()
//                tessellator.setNormal(0.0f, 0.0f, 1.0f)
//                renderer.renderFaceZPos(block, 0.0, 0.0, 0.0, renderer.getBlockIconFromSideAndMetadata(block, 3, meta))
//                tessellator.draw()
//                tessellator.startDrawingQuads()
//                tessellator.setNormal(-1.0f, 0.0f, 0.0f)
//                renderer.renderFaceXNeg(block, 0.0, 0.0, 0.0, renderer.getBlockIconFromSideAndMetadata(block, 4, meta))
//                tessellator.draw()
//                tessellator.startDrawingQuads()
//                tessellator.setNormal(1.0f, 0.0f, 0.0f)
//                renderer.renderFaceXPos(block, 0.0, 0.0, 0.0, renderer.getBlockIconFromSideAndMetadata(block, 5, meta))
//                tessellator.draw()
//            } else {
//                renderer.renderStandardBlock(block, x, y, z)
//            }
//
//
//            if (!flag) {
//                tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z))
//                val j1 = block.colorMultiplier(renderer.blockAccess, x, y, z)
//                var g = (j1 shr 16 and 255).toFloat() / 255.0f
//                f1 = (j1 shr 8 and 255).toFloat() / 255.0f
//                var g1 = (j1 and 255).toFloat() / 255.0f
//
//                if (EntityRenderer.anaglyphEnable) {
//                    val f3 = (g * 30.0f + f1 * 59.0f + g1 * 11.0f) / 100.0f
//                    val f4 = (g * 30.0f + f1 * 70.0f) / 100.0f
//                    val f5 = (g * 30.0f + g1 * 70.0f) / 100.0f
//                    g = f3
//                    f1 = f4
//                    g1 = f5
//                }
//
//                tessellator.setColorOpaque_F(g, f1, g1)
//            }
//
//            val iicon = BlockFunnel.getHopperIcon("funnel_outside")
//            val iicon1 = BlockFunnel.getHopperIcon("funnel_inside")
//            f1 = 0.125f
//
//            if (flag) {
//                tessellator.startDrawingQuads()
//                tessellator.setNormal(1.0f, 0.0f, 0.0f)
//                renderer.renderFaceXPos(block, (-1.0f + f1).toDouble(), 0.0, 0.0, iicon)
//                tessellator.draw()
//                tessellator.startDrawingQuads()
//                tessellator.setNormal(-1.0f, 0.0f, 0.0f)
//                renderer.renderFaceXNeg(block, (1.0f - f1).toDouble(), 0.0, 0.0, iicon)
//                tessellator.draw()
//                tessellator.startDrawingQuads()
//                tessellator.setNormal(0.0f, 0.0f, 1.0f)
//                renderer.renderFaceZPos(block, 0.0, 0.0, (-1.0f + f1).toDouble(), iicon)
//                tessellator.draw()
//                tessellator.startDrawingQuads()
//                tessellator.setNormal(0.0f, 0.0f, -1.0f)
//                renderer.renderFaceZNeg(block, 0.0, 0.0, (1.0f - f1).toDouble(), iicon)
//                tessellator.draw()
//                tessellator.startDrawingQuads()
//                tessellator.setNormal(0.0f, 1.0f, 0.0f)
//                renderer.renderFaceYPos(block, 0.0, -1.0 + d0, 0.0, iicon1)
//                tessellator.draw()
//            } else {
//                renderer.renderFaceXPos(block, (x.toFloat() - 1.0f + f1).toDouble(), y.toDouble(), z.toDouble(), iicon)
//                renderer.renderFaceXNeg(block, (x.toFloat() + 1.0f - f1).toDouble(), y.toDouble(), z.toDouble(), iicon)
//                renderer.renderFaceZPos(block, x.toDouble(), y.toDouble(), (z.toFloat() - 1.0f + f1).toDouble(), iicon)
//                renderer.renderFaceZNeg(block, x.toDouble(), y.toDouble(), (z.toFloat() + 1.0f - f1).toDouble(), iicon)
//                renderer.renderFaceYPos(block, x.toDouble(), (y.toFloat() - 1.0f).toDouble() + d0, z.toDouble(), iicon1)
//            }
//
//            renderer.setOverrideBlockTexture(iicon)
//            val d3 = 0.25
//            val d4 = 0.25
//            renderer.setRenderBounds(d3, d4, d3, 1.0 - d3, d0 - 0.002, 1.0 - d3)
//
//            if (flag) {
//                tessellator.startDrawingQuads()
//                tessellator.setNormal(1.0f, 0.0f, 0.0f)
//                renderer.renderFaceXPos(block, 0.0, 0.0, 0.0, iicon)
//                tessellator.draw()
//                tessellator.startDrawingQuads()
//                tessellator.setNormal(-1.0f, 0.0f, 0.0f)
//                renderer.renderFaceXNeg(block, 0.0, 0.0, 0.0, iicon)
//                tessellator.draw()
//                tessellator.startDrawingQuads()
//                tessellator.setNormal(0.0f, 0.0f, 1.0f)
//                renderer.renderFaceZPos(block, 0.0, 0.0, 0.0, iicon)
//                tessellator.draw()
//                tessellator.startDrawingQuads()
//                tessellator.setNormal(0.0f, 0.0f, -1.0f)
//                renderer.renderFaceZNeg(block, 0.0, 0.0, 0.0, iicon)
//                tessellator.draw()
//                tessellator.startDrawingQuads()
//                tessellator.setNormal(0.0f, 1.0f, 0.0f)
//                renderer.renderFaceYPos(block, 0.0, 0.0, 0.0, iicon)
//                tessellator.draw()
//                tessellator.startDrawingQuads()
//                tessellator.setNormal(0.0f, -1.0f, 0.0f)
//                renderer.renderFaceYNeg(block, 0.0, 0.0, 0.0, iicon)
//                tessellator.draw()
//            } else {
//                renderer.renderStandardBlock(block, x, y, z)
//            }
//
//            if (!flag) {
//                val d1 = 0.375
//                val d2 = 0.25
//                renderer.setOverrideBlockTexture(iicon)
//
//                if (i1 == 0) {
//                    renderer.setRenderBounds(d1, 0.0, d1, 1.0 - d1, 0.25, 1.0 - d1)
//                    renderer.renderStandardBlock(block, x, y, z)
//                }
//
//                if (i1 == 2) {
//                    renderer.setRenderBounds(d1, d4, 0.0, 1.0 - d1, d4 + d2, d3)
//                    renderer.renderStandardBlock(block, x, y, z)
//                }
//
//                if (i1 == 3) {
//                    renderer.setRenderBounds(d1, d4, 1.0 - d3, 1.0 - d1, d4 + d2, 1.0)
//                    renderer.renderStandardBlock(block, x, y, z)
//                }
//
//                if (i1 == 4) {
//                    renderer.setRenderBounds(0.0, d4, d1, d3, d4 + d2, 1.0 - d1)
//                    renderer.renderStandardBlock(block, x, y, z)
//                }
//
//                if (i1 == 5) {
//                    renderer.setRenderBounds(1.0 - d3, d4, d1, 1.0, d4 + d2, 1.0 - d1)
//                    renderer.renderStandardBlock(block, x, y, z)
//                }
//            }
//
//            renderer.clearOverrideBlockTexture()
//
//            return true
//        }
//
//    }
//
//    override fun getEntry(p0: World?, p1: Int, p2: Int, p3: Int, p4: EntityPlayer?, p5: ItemStack?): LexiconEntry? {
//        return LexiconRegistry.livingwoodFunnel
//    }
//}
