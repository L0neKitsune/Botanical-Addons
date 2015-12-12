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
import ninja.shadowfox.shadowfox_botany.common.blocks.base.ShadowFoxTileContainer
import ninja.shadowfox.shadowfox_botany.common.blocks.tile.TileLivingwoodFunnel
import ninja.shadowfox.shadowfox_botany.common.core.ShadowFoxCreativeTab
import ninja.shadowfox.shadowfox_botany.common.utils.helper.IconHelper
import vazkii.botania.api.wand.IWandHUD
import java.util.*

/**
 * Created by l0nekitsune on 12/11/15.
 */
class BlockFunnel() : ShadowFoxTileContainer<TileLivingwoodFunnel>(Material.wood), IWandHUD
{
    private val field_149922_a = Random()
    @SideOnly(Side.CLIENT) lateinit var outsideIcon: IIcon
    @SideOnly(Side.CLIENT) lateinit var topIcon: IIcon
    @SideOnly(Side.CLIENT) lateinit var insideIcon: IIcon

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
        if(p_149689_1_ != null) this.updateBlockData(p_149689_1_, p_149695_2_, p_149695_3_, p_149695_4_)
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
        val tile = p_149749_1_.getTileEntity(p_149749_2_, p_149749_3_, p_149749_4_) as TileLivingwoodFunnel

        if (tile != null) {
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
        return 0
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
        return if (p_149691_1_ == 1) this.topIcon else this.outsideIcon
    }

    companion object {
        fun getDirectionFromMetadata(p_149918_0_: Int): Int = p_149918_0_ and 7
        fun getActiveStateFromMetadata(p_149917_0_: Int): Boolean = (p_149917_0_ and 8) != 8
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
        if(p_149736_1_ == null) return 0
        return Container.calcRedstoneFromInventory(getTile(p_149736_1_, p_149736_2_, p_149736_3_, p_149736_4_))
    }

    @SideOnly(Side.CLIENT)
    override fun registerBlockIcons(register: IIconRegister) {
        this.outsideIcon = IconHelper.forName(register, "hopper_outside")
        this.topIcon =  IconHelper.forName(register, "hopper_top")
        this.insideIcon = IconHelper.forName(register, "hopper_inside")
    }

    @SideOnly(Side.CLIENT)
    fun getHopperIcon(p_149916_0_: String): IIcon? {
        return if (p_149916_0_ == "hopper_outside") (ShadowFoxBlocks.livingwoodFunnel as BlockFunnel).outsideIcon
        else (if (p_149916_0_ == "hopper_inside") (ShadowFoxBlocks.livingwoodFunnel as BlockFunnel).insideIcon else null)
    }

    fun getTile(p_149920_0_: IBlockAccess, p_149920_1_: Int, p_149920_2_: Int, p_149920_3_: Int): TileLivingwoodFunnel? {
        return p_149920_0_.getTileEntity(p_149920_1_, p_149920_2_, p_149920_3_) as TileLivingwoodFunnel
    }

    /**
     * Gets the icon name of the ItemBlock corresponding to this block. Used by hoppers.
     */
    @SideOnly(Side.CLIENT)
    override fun getItemIconName(): String = "hopper"
}
