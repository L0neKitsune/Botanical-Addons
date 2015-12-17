package ninja.shadowfox.shadowfox_botany.common.blocks

import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.ScaledResolution
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.item.EntityItem
import net.minecraft.inventory.Container
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.AxisAlignedBB
import net.minecraft.util.Facing
import net.minecraft.util.IIcon
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import ninja.shadowfox.shadowfox_botany.lib.Constants
import ninja.shadowfox.shadowfox_botany.common.blocks.base.ShadowFoxTileContainer
import ninja.shadowfox.shadowfox_botany.common.blocks.tile.TileLivingwoodFunnel
import ninja.shadowfox.shadowfox_botany.common.core.ShadowFoxCreativeTab
import ninja.shadowfox.shadowfox_botany.common.utils.helper.IconHelper
import vazkii.botania.api.wand.IWandHUD
import vazkii.botania.common.block.ModBlocks as BotaniaBlocks
import java.util.*

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler
import net.minecraft.block.BlockHopper
import net.minecraft.client.renderer.RenderBlocks
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.EntityRenderer

/**
 * Created by l0nekitsune on 12/11/15.
 */
class BlockFunnel() : ShadowFoxTileContainer<TileLivingwoodFunnel>(Material.wood), IWandHUD {
    private val field_149922_a = Random()
    public lateinit var top_icon: IIcon

    companion object {
        @SideOnly(Side.CLIENT)
        fun getHopperIcon(string: String): IIcon? {
            if (string == "hopper_top") return (ShadowFoxBlocks.livingwoodFunnel as BlockFunnel).top_icon

            return BotaniaBlocks.livingwood.getIcon(0, 0)
        }

        fun getDirectionFromMetadata(p_149918_0_: Int): Int = p_149918_0_ and 7
        fun getActiveStateFromMetadata(p_149917_0_: Int): Boolean = (p_149917_0_ and 8) != 8
    }

    init {
        this.setBlockName("livingwoodFunnel")
        this.setCreativeTab(ShadowFoxCreativeTab)
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f)
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    override fun setBlockBoundsBasedOnState(p_149719_1_: IBlockAccess?, p_149719_2_: Int, p_149719_3_: Int, p_149719_4_: Int) {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f)
    }

    override fun addCollisionBoxesToList(p_149743_1_: World?, p_149743_2_: Int, p_149743_3_: Int, p_149743_4_: Int, p_149743_5_: AxisAlignedBB?, p_149743_6_: MutableList<Any?>?, p_149743_7_: Entity?) {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.625f, 1.0f)
        super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_)
        val f = 0.125f
        this.setBlockBounds(0.0f, 0.0f, 0.0f, f, 1.0f, 1.0f)
        super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_)
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, f)
        super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_)
        this.setBlockBounds(1.0f - f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f)
        super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_)
        this.setBlockBounds(0.0f, 0.0f, 1.0f - f, 1.0f, 1.0f, 1.0f)
        super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_)
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f)
    }

    /**
     * Called when a block is placed using its ItemBlock. Args: World, X, Y, Z, side, hitX, hitY, hitZ, block metadata
     */
    override fun onBlockPlaced(p_149660_1_: World?, p_149660_2_: Int, p_149660_3_: Int, p_149660_4_: Int, p_149660_5_: Int, p_149660_6_: Float, p_149660_7_: Float, p_149660_8_: Float, p_149660_9_: Int): Int {
        var j1 = Facing.oppositeSide[p_149660_5_]

        if (j1 == 1) j1 = 0
        return j1
    }

    override fun createNewTileEntity(var1: World, var2: Int): TileLivingwoodFunnel = TileLivingwoodFunnel()

    /**
     * Called when the block is placed in the world.
     */
    override fun onBlockPlacedBy(p_149689_1_: World?, p_149689_2_: Int, p_149689_3_: Int, p_149689_4_: Int, p_149689_5_: EntityLivingBase?, p_149689_6_: ItemStack?) {
        super.onBlockPlacedBy(p_149689_1_, p_149689_2_, p_149689_3_, p_149689_4_, p_149689_5_, p_149689_6_)
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    override fun onBlockAdded(p_149726_1_: World?, p_149726_2_: Int, p_149726_3_: Int, p_149726_4_: Int) {
        super.onBlockAdded(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_)
        if (p_149726_1_ != null) this.updateBlockData(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_)
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor Block
     */
    override fun onNeighborBlockChange(p_149689_1_: World?, p_149695_2_: Int, p_149695_3_: Int, p_149695_4_: Int, p_149695_5_: Block?) {
        if (p_149689_1_ != null) this.updateBlockData(p_149689_1_, p_149695_2_, p_149695_3_, p_149695_4_)
    }

    private fun updateBlockData(p_149919_1_: World, p_149919_2_: Int, p_149919_3_: Int, p_149919_4_: Int) {
        val l = p_149919_1_.getBlockMetadata(p_149919_2_, p_149919_3_, p_149919_4_)
        val i1 = getDirectionFromMetadata(l)
        val flag = !p_149919_1_.isBlockIndirectlyGettingPowered(p_149919_2_, p_149919_3_, p_149919_4_)
        val flag1 = getActiveStateFromMetadata(l)

        if (flag != flag1) {
            p_149919_1_.setBlockMetadataWithNotify(p_149919_2_, p_149919_3_, p_149919_4_, i1 or (if (flag) 0 else 8), 4)
        }
    }

    override fun renderHUD(mc: Minecraft, res: ScaledResolution, world: World, x: Int, y: Int, z: Int) {
        (world.getTileEntity(x, y, z) as TileLivingwoodFunnel).renderHUD(mc, res)
    }


    override fun breakBlock(p_149749_1_: World, p_149749_2_: Int, p_149749_3_: Int, p_149749_4_: Int, p_149749_5_: Block?, p_149749_6_: Int) {
        val tile = p_149749_1_.getTileEntity(p_149749_2_, p_149749_3_, p_149749_4_)

        if (tile != null && tile is TileLivingwoodFunnel) {
            for (i1 in 0..tile.sizeInventory - 1) {
                val itemstack = tile.getStackInSlot(i1)

                if (itemstack != null) {
                    val f = this.field_149922_a.nextFloat() * 0.8f + 0.1f
                    val f1 = this.field_149922_a.nextFloat() * 0.8f + 0.1f
                    val f2 = this.field_149922_a.nextFloat() * 0.8f + 0.1f

                    while (itemstack.stackSize > 0) {
                        var j1 = this.field_149922_a.nextInt(21) + 10

                        if (j1 > itemstack.stackSize) {
                            j1 = itemstack.stackSize
                        }

                        itemstack.stackSize -= j1
                        val entityitem = EntityItem(p_149749_1_, (p_149749_2_.toFloat() + f).toDouble(), (p_149749_3_.toFloat() + f1).toDouble(), (p_149749_4_.toFloat() + f2).toDouble(), ItemStack(itemstack.item, j1, itemstack.itemDamage))

                        if (itemstack.hasTagCompound()) {
                            entityitem.entityItem.tagCompound = itemstack.tagCompound.copy() as NBTTagCompound
                        }

                        val f3 = 0.05f
                        entityitem.motionX = (this.field_149922_a.nextGaussian().toFloat() * f3).toDouble()
                        entityitem.motionY = (this.field_149922_a.nextGaussian().toFloat() * f3 + 0.2f).toDouble()
                        entityitem.motionZ = (this.field_149922_a.nextGaussian().toFloat() * f3).toDouble()
                        p_149749_1_.spawnEntityInWorld(entityitem)
                    }
                }
            }

            p_149749_1_.func_147453_f(p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_)
        }

        super.breakBlock(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_, p_149749_6_)
    }

    /**
     * The type of render function that is called for this block
     */
    override fun getRenderType(): Int {
        return Constants.hopperRenderingID
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    override fun renderAsNormalBlock(): Boolean {
        return false
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    override fun isOpaqueCube(): Boolean {
        return false
    }

    /**
     * Returns true if the given side of this block type should be rendered, if the adjacent block is at the given
     * coordinates.  Args: blockAccess, x, y, z, side
     */
    @SideOnly(Side.CLIENT)
    override fun shouldSideBeRendered(p_149646_1_: IBlockAccess, p_149646_2_: Int, p_149646_3_: Int, p_149646_4_: Int, p_149646_5_: Int): Boolean {
        return true
    }

    /**
     * Gets the block's texture. Args: side, meta
     */
    @SideOnly(Side.CLIENT)
    override fun getIcon(p_149691_1_: Int, p_149691_2_: Int): IIcon {
        if (p_149691_1_ == 1) return top_icon
        return BotaniaBlocks.livingwood.getIcon(0, 0)
    }

    /**
     * If this returns true, then comparators facing away from this block will use the value from
     * getComparatorInputOverride instead of the actual redstone signal strength.
     */
    override fun hasComparatorInputOverride(): Boolean = true

    /**
     * If hasComparatorInputOverride returns true, the return value from this is used instead of the redstone signal
     * strength when this block inputs to a comparator.
     */
    override fun getComparatorInputOverride(p_149736_1_: World?, p_149736_2_: Int, p_149736_3_: Int, p_149736_4_: Int, p_149736_5_: Int): Int {
        if (p_149736_1_ == null) return 0
        return Container.calcRedstoneFromInventory(getTile(p_149736_1_, p_149736_2_, p_149736_3_, p_149736_4_))
    }

    @SideOnly(Side.CLIENT)
    override fun registerBlockIcons(par1IconRegister: IIconRegister) {
        top_icon = IconHelper.forName(par1IconRegister, "hopper_top")
    }

    fun getTile(p_149920_0_: IBlockAccess, p_149920_1_: Int, p_149920_2_: Int, p_149920_3_: Int): TileLivingwoodFunnel? {
        return p_149920_0_.getTileEntity(p_149920_1_, p_149920_2_, p_149920_3_) as TileLivingwoodFunnel
    }

    /**
     * Gets the icon name of the ItemBlock corresponding to this block. Used by hoppers.
     */
    @SideOnly(Side.CLIENT)
    override fun getItemIconName(): String = "shadowfox_botany:livingwood_funnel"

    class HopperRenderer : ISimpleBlockRenderingHandler {
        override fun getRenderId(): Int {
            return Constants.hopperRenderingID
        }

        public override fun shouldRender3DInInventory(modelId: Int): Boolean {
            return false
        }

        public override fun renderInventoryBlock(block: Block, metadata: Int, modelID: Int, renderer: RenderBlocks) {
        }

        public override fun renderWorldBlock(world: IBlockAccess, x: Int, y: Int, z: Int, block: Block, modelId: Int, renderer: RenderBlocks): Boolean {
            val tessellator = Tessellator.instance
            tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z))
            val l = block.colorMultiplier(world, x, y, z)
            var f = (l shr 16 and 255).toFloat() / 255.0f
            var f1 = (l shr 8 and 255).toFloat() / 255.0f
            var f2 = (l and 255).toFloat() / 255.0f

            if (EntityRenderer.anaglyphEnable) {
                val f3 = (f * 30.0f + f1 * 59.0f + f2 * 11.0f) / 100.0f
                val f4 = (f * 30.0f + f1 * 70.0f) / 100.0f
                val f5 = (f * 30.0f + f2 * 70.0f) / 100.0f
                f = f3
                f1 = f4
                f2 = f5
            }

            tessellator.setColorOpaque_F(f, f1, f2)
            val meta = world.getBlockMetadata(x, y, z)

            val i1 = BlockFunnel.getDirectionFromMetadata(meta)
            val d0 = 0.625
            renderer.setRenderBounds(0.0, d0, 0.0, 1.0, 1.0, 1.0)

            val p_147799_6_ = false

            if (p_147799_6_) {
                tessellator.startDrawingQuads()
                tessellator.setNormal(0.0f, -1.0f, 0.0f)
                renderer.renderFaceYNeg(block, 0.0, 0.0, 0.0, renderer.getBlockIconFromSideAndMetadata(block, 0, meta))
                tessellator.draw()
                tessellator.startDrawingQuads()
                tessellator.setNormal(0.0f, 1.0f, 0.0f)
                renderer.renderFaceYPos(block, 0.0, 0.0, 0.0, renderer.getBlockIconFromSideAndMetadata(block, 1, meta))
                tessellator.draw()
                tessellator.startDrawingQuads()
                tessellator.setNormal(0.0f, 0.0f, -1.0f)
                renderer.renderFaceZNeg(block, 0.0, 0.0, 0.0, renderer.getBlockIconFromSideAndMetadata(block, 2, meta))
                tessellator.draw()
                tessellator.startDrawingQuads()
                tessellator.setNormal(0.0f, 0.0f, 1.0f)
                renderer.renderFaceZPos(block, 0.0, 0.0, 0.0, renderer.getBlockIconFromSideAndMetadata(block, 3, meta))
                tessellator.draw()
                tessellator.startDrawingQuads()
                tessellator.setNormal(-1.0f, 0.0f, 0.0f)
                renderer.renderFaceXNeg(block, 0.0, 0.0, 0.0, renderer.getBlockIconFromSideAndMetadata(block, 4, meta))
                tessellator.draw()
                tessellator.startDrawingQuads()
                tessellator.setNormal(1.0f, 0.0f, 0.0f)
                renderer.renderFaceXPos(block, 0.0, 0.0, 0.0, renderer.getBlockIconFromSideAndMetadata(block, 5, meta))
                tessellator.draw()
            } else {
                renderer.renderStandardBlock(block, x, y, z)
            }


            if (!p_147799_6_) {
                tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z))
                val j1 = block.colorMultiplier(renderer.blockAccess, x, y, z)
                var f = (j1 shr 16 and 255).toFloat() / 255.0f
                f1 = (j1 shr 8 and 255).toFloat() / 255.0f
                var f2 = (j1 and 255).toFloat() / 255.0f

                if (EntityRenderer.anaglyphEnable) {
                    val f3 = (f * 30.0f + f1 * 59.0f + f2 * 11.0f) / 100.0f
                    val f4 = (f * 30.0f + f1 * 70.0f) / 100.0f
                    val f5 = (f * 30.0f + f2 * 70.0f) / 100.0f
                    f = f3
                    f1 = f4
                    f2 = f5
                }

                tessellator.setColorOpaque_F(f, f1, f2)
            }

            val iicon = BlockFunnel.getHopperIcon("hopper_outside")
            val iicon1 = BlockFunnel.getHopperIcon("hopper_inside")
            f1 = 0.125f

            if (p_147799_6_) {
                tessellator.startDrawingQuads()
                tessellator.setNormal(1.0f, 0.0f, 0.0f)
                renderer.renderFaceXPos(block, (-1.0f + f1).toDouble(), 0.0, 0.0, iicon)
                tessellator.draw()
                tessellator.startDrawingQuads()
                tessellator.setNormal(-1.0f, 0.0f, 0.0f)
                renderer.renderFaceXNeg(block, (1.0f - f1).toDouble(), 0.0, 0.0, iicon)
                tessellator.draw()
                tessellator.startDrawingQuads()
                tessellator.setNormal(0.0f, 0.0f, 1.0f)
                renderer.renderFaceZPos(block, 0.0, 0.0, (-1.0f + f1).toDouble(), iicon)
                tessellator.draw()
                tessellator.startDrawingQuads()
                tessellator.setNormal(0.0f, 0.0f, -1.0f)
                renderer.renderFaceZNeg(block, 0.0, 0.0, (1.0f - f1).toDouble(), iicon)
                tessellator.draw()
                tessellator.startDrawingQuads()
                tessellator.setNormal(0.0f, 1.0f, 0.0f)
                renderer.renderFaceYPos(block, 0.0, -1.0 + d0, 0.0, iicon1)
                tessellator.draw()
            } else {
                renderer.renderFaceXPos(block, (x.toFloat() - 1.0f + f1).toDouble(), y.toDouble(), z.toDouble(), iicon)
                renderer.renderFaceXNeg(block, (x.toFloat() + 1.0f - f1).toDouble(), y.toDouble(), z.toDouble(), iicon)
                renderer.renderFaceZPos(block, x.toDouble(), y.toDouble(), (z.toFloat() - 1.0f + f1).toDouble(), iicon)
                renderer.renderFaceZNeg(block, x.toDouble(), y.toDouble(), (z.toFloat() + 1.0f - f1).toDouble(), iicon)
                renderer.renderFaceYPos(block, x.toDouble(), (y.toFloat() - 1.0f).toDouble() + d0, z.toDouble(), iicon1)
            }

            renderer.setOverrideBlockTexture(iicon)
            val d3 = 0.25
            val d4 = 0.25
            renderer.setRenderBounds(d3, d4, d3, 1.0 - d3, d0 - 0.002, 1.0 - d3)

            if (p_147799_6_) {
                tessellator.startDrawingQuads()
                tessellator.setNormal(1.0f, 0.0f, 0.0f)
                renderer.renderFaceXPos(block, 0.0, 0.0, 0.0, iicon)
                tessellator.draw()
                tessellator.startDrawingQuads()
                tessellator.setNormal(-1.0f, 0.0f, 0.0f)
                renderer.renderFaceXNeg(block, 0.0, 0.0, 0.0, iicon)
                tessellator.draw()
                tessellator.startDrawingQuads()
                tessellator.setNormal(0.0f, 0.0f, 1.0f)
                renderer.renderFaceZPos(block, 0.0, 0.0, 0.0, iicon)
                tessellator.draw()
                tessellator.startDrawingQuads()
                tessellator.setNormal(0.0f, 0.0f, -1.0f)
                renderer.renderFaceZNeg(block, 0.0, 0.0, 0.0, iicon)
                tessellator.draw()
                tessellator.startDrawingQuads()
                tessellator.setNormal(0.0f, 1.0f, 0.0f)
                renderer.renderFaceYPos(block, 0.0, 0.0, 0.0, iicon)
                tessellator.draw()
                tessellator.startDrawingQuads()
                tessellator.setNormal(0.0f, -1.0f, 0.0f)
                renderer.renderFaceYNeg(block, 0.0, 0.0, 0.0, iicon)
                tessellator.draw()
            } else {
                renderer.renderStandardBlock(block, x, y, z)
            }

            if (!p_147799_6_) {
                val d1 = 0.375
                val d2 = 0.25
                renderer.setOverrideBlockTexture(iicon)

                if (i1 == 0) {
                    renderer.setRenderBounds(d1, 0.0, d1, 1.0 - d1, 0.25, 1.0 - d1)
                    renderer.renderStandardBlock(block, x, y, z)
                }

                if (i1 == 2) {
                    renderer.setRenderBounds(d1, d4, 0.0, 1.0 - d1, d4 + d2, d3)
                    renderer.renderStandardBlock(block, x, y, z)
                }

                if (i1 == 3) {
                    renderer.setRenderBounds(d1, d4, 1.0 - d3, 1.0 - d1, d4 + d2, 1.0)
                    renderer.renderStandardBlock(block, x, y, z)
                }

                if (i1 == 4) {
                    renderer.setRenderBounds(0.0, d4, d1, d3, d4 + d2, 1.0 - d1)
                    renderer.renderStandardBlock(block, x, y, z)
                }

                if (i1 == 5) {
                    renderer.setRenderBounds(1.0 - d3, d4, d1, 1.0, d4 + d2, 1.0 - d1)
                    renderer.renderStandardBlock(block, x, y, z)
                }
            }

            renderer.clearOverrideBlockTexture()

            return true;
        }
    }
}
