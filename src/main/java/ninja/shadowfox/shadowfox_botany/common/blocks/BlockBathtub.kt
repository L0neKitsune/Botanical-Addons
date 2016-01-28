package ninja.shadowfox.shadowfox_botany.common.blocks

import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.block.Block
import net.minecraft.block.ITileEntityProvider
import net.minecraft.block.material.Material
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.ScaledResolution
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.Entity
import net.minecraft.entity.item.EntityItem
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.AxisAlignedBB
import net.minecraft.util.IIcon
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import net.minecraftforge.common.util.ForgeDirection
import ninja.shadowfox.shadowfox_botany.common.blocks.base.BlockMod
import ninja.shadowfox.shadowfox_botany.common.blocks.tile.TileBathtub
import vazkii.botania.api.BotaniaAPI
import vazkii.botania.api.internal.VanillaPacketDispatcher
import vazkii.botania.api.lexicon.ILexiconable
import vazkii.botania.api.lexicon.LexiconEntry
import vazkii.botania.api.wand.IWandHUD
import vazkii.botania.api.wand.IWandable
import vazkii.botania.client.core.helper.IconHelper
import vazkii.botania.client.lib.LibRenderIDs
import vazkii.botania.common.block.ModBlocks
import vazkii.botania.common.block.tile.mana.TilePool
import vazkii.botania.common.item.block.ItemBlockPool
import java.util.*


class BlockBathtub(): BlockMod(Material.rock), ITileEntityProvider, IWandHUD, IWandable, ILexiconable {
    internal var lastFragile = false
    lateinit var manaIcon: IIcon

    init {
        this.setHardness(2.0f)
        this.setResistance(10.0f)
        this.setStepSound(Block.soundTypeStone)
        this.setBlockName("pool")
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 1.0f)
        BotaniaAPI.blacklistBlockFromMagnet(this, 32767)
    }

    override fun shouldRegisterInNameSet(): Boolean {
        return false
    }

    override fun setBlockName(par1Str: String): Block {
        GameRegistry.registerBlock(this, ItemBlockPool::class.java, par1Str)
        return super.setBlockName(par1Str)
    }

    override fun registerBlockIcons(par1IconRegister: IIconRegister) {
        manaIcon = IconHelper.forName(par1IconRegister, "manaWater")
    }

    override fun damageDropped(meta: Int): Int {
        return meta
    }

    override fun getDrops(world: World, x: Int, y: Int, z: Int, metadata: Int, fortune: Int): ArrayList<ItemStack> {
        val drops = ArrayList<ItemStack>()
        if (!this.lastFragile) {
            drops.add(ItemStack(this, 1, metadata))
        }

        return drops
    }

    override fun getSubBlocks(par1: Item?, par2: CreativeTabs?, par3: MutableList<Any?>?) {
        par3?.add(ItemStack(par1, 1, 0))
        par3?.add(ItemStack(par1, 1, 1))
    }

    override fun onEntityCollidedWithBlock(par1World: World?, par2: Int, par3: Int, par4: Int, par5Entity: Entity?) {
        if (par5Entity is EntityItem) {
            val tile = par1World!!.getTileEntity(par2, par3, par4) as TilePool
            if (tile.collideEntityItem(par5Entity as EntityItem?)) {
                VanillaPacketDispatcher.dispatchTEToNearbyPlayers(par1World, par2, par3, par4)
            }
        }
    }


    override fun addCollisionBoxesToList(p_149743_1_: World, p_149743_2_: Int, p_149743_3_: Int, p_149743_4_: Int, p_149743_5_: AxisAlignedBB?, p_149743_6_: MutableList<Any?>?, p_149743_7_: Entity?) {
        val f = 0.0625f
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, f, 1.0f)
        super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_)
        this.setBlockBounds(0.0f, 0.0f, 0.0f, f, 0.5f, 1.0f)
        super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_)
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.5f, f)
        super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_)
        this.setBlockBounds(1.0f - f, 0.0f, 0.0f, 1.0f, 0.5f, 1.0f)
        super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_)
        this.setBlockBounds(0.0f, 0.0f, 1.0f - f, 1.0f, 0.5f, 1.0f)
        super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_)
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 1.0f)
    }

    override fun isSideSolid(world: IBlockAccess, x: Int, y: Int, z: Int, side: ForgeDirection): Boolean {
        return side == ForgeDirection.DOWN
    }

    override fun isOpaqueCube(): Boolean {
        return false
    }

    override fun renderAsNormalBlock(): Boolean {
        return false
    }

    override fun getIcon(par1: Int, par2: Int): IIcon {
        return ModBlocks.livingrock.getIcon(par1, 0)
    }

    override fun getRenderType(): Int {
        return LibRenderIDs.idPool
    }

    override fun hasComparatorInputOverride(): Boolean {
        return true
    }

    override fun getComparatorInputOverride(par1World: World?, par2: Int, par3: Int, par4: Int, par5: Int): Int {
        val pool = par1World!!.getTileEntity(par2, par3, par4) as TilePool
        var `val` = (pool.currentMana.toDouble() / pool.manaCap.toDouble() * 15.0).toInt()
        if (pool.currentMana > 0) {
            `val` = Math.max(`val`, 1)
        }

        return `val`
    }

    override fun renderHUD(mc: Minecraft, res: ScaledResolution, world: World, x: Int, y: Int, z: Int) {
        (world.getTileEntity(x, y, z) as TilePool).renderHUD(mc, res)
    }

    override fun onUsedByWand(player: EntityPlayer, stack: ItemStack, world: World, x: Int, y: Int, z: Int, side: Int): Boolean {
        (world.getTileEntity(x, y, z) as TilePool).onWanded(player, stack)
        return true
    }

    override fun getEntry(p0: World?, p1: Int, p2: Int, p3: Int, p4: EntityPlayer?, p5: ItemStack?): LexiconEntry? {
        return null
    }

    override fun onBlockAdded(p_149726_1_: World?, p_149726_2_: Int, p_149726_3_: Int, p_149726_4_: Int) {
        super.onBlockAdded(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_)
    }

    override fun breakBlock(par1World: World, par2: Int, par3: Int, par4: Int, par5: Block?, par6: Int) {
        val pool = par1World.getTileEntity(par2, par3, par4) as TileBathtub?
        this.lastFragile = pool?.fragile ?: false
        super.breakBlock(par1World, par2, par3, par4, par5, par6)

        par1World.removeTileEntity(par2, par3, par4)
    }

    override fun onBlockEventReceived(p_149696_1_: World?, p_149696_2_: Int, p_149696_3_: Int, p_149696_4_: Int, p_149696_5_: Int, p_149696_6_: Int): Boolean {
        super.onBlockEventReceived(p_149696_1_, p_149696_2_, p_149696_3_, p_149696_4_, p_149696_5_, p_149696_6_)
        val tileentity = p_149696_1_!!.getTileEntity(p_149696_2_, p_149696_3_, p_149696_4_)
        return if (tileentity != null) tileentity.receiveClientEvent(p_149696_5_, p_149696_6_) else false
    }


    override fun createNewTileEntity(var1: World, var2: Int): TileBathtub = TileBathtub()

    override fun createTileEntity(world: World?, metadata: Int): TileEntity? {
        return if(isDrain(metadata)) TileBathtub() else null
    }
    override fun hasTileEntity(metadata: Int): Boolean = isDrain(metadata)
    public fun isDrain(meta: Int): Boolean = (meta == 0)
}
