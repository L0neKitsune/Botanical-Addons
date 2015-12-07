package ninja.shadowfox.shadowfox_botany.common.blocks

import cpw.mods.fml.common.registry.GameRegistry
import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.Entity
import net.minecraft.entity.item.EntityItem
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraft.inventory.IInventory
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.AxisAlignedBB
import net.minecraft.util.IIcon
import net.minecraft.world.World
import ninja.shadowfox.shadowfox_botany.common.blocks.base.ShadowFoxBlockMod
import ninja.shadowfox.shadowfox_botany.common.item.blocks.ShadowFoxSubtypeItemBlock
import ninja.shadowfox.shadowfox_botany.common.blocks.tile.TileItemDisplay
import ninja.shadowfox.shadowfox_botany.common.lexicon.LexiconRegistry
import ninja.shadowfox.shadowfox_botany.common.utils.helper.IconHelper
import vazkii.botania.api.lexicon.ILexiconable
import vazkii.botania.api.lexicon.LexiconEntry
import java.util.*


class BlockItemDisplay() : ShadowFoxBlockMod(Material.wood) {

    val TYPES = 3
    var icons: Array<IIcon?> = arrayOfNulls(TYPES)
    var sideIcons: Array<IIcon?> = arrayOfNulls(TYPES)

    init {
        setBlockName("itemDisplay")
        setStepSound(Block.soundTypeWood)
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F)
    }

    override fun getSubBlocks(item : Item?, tab : CreativeTabs?, list : MutableList<Any?>?) {
        if (list != null && item != null)
            for (i in 0..(TYPES - 1)) {
                list.add(ItemStack(item, 1, i))
            }
    }

    override fun shouldRegisterInNameSet(): Boolean = false

    override fun setBlockName(par1Str: String): Block {
        register(par1Str)
        return super.setBlockName(par1Str)
    }

    internal fun register(name: String) {
        GameRegistry.registerBlock(this, ShadowFoxSubtypeItemBlock::class.java, name)
    }

    override fun addCollisionBoxesToList(world:World, x:Int, y:Int, z:Int, axis:AxisAlignedBB, bounds:MutableList<Any?>, entity:Entity?) {
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F)
        super.addCollisionBoxesToList(world, x, y, z, axis, bounds, entity)
    }

    override fun registerBlockIcons(par1IconRegister: IIconRegister) {
        for (i in 0..(TYPES-1)) {
            icons[i] = IconHelper.forBlock(par1IconRegister, this, i)
            sideIcons[i] = IconHelper.forBlock(par1IconRegister, this, "Side"+i)
        }

    }

    @SideOnly(Side.CLIENT)
    override fun getIcon(side: Int, meta: Int): IIcon {
        return if (side == 1 || side == 0) icons[Math.min(meta, TYPES-1)]!! else sideIcons[Math.min(meta, TYPES-1)]!!
    }

    override fun onBlockActivated(world: World?, x: Int, y: Int, z: Int, player: EntityPlayer?, p_149727_6_: Int, p_149727_7_: Float, p_149727_8_: Float, p_149727_9_: Float): Boolean {
        if (world!!.isRemote || player == null) return true
        else {
            var tileEntity = world.getTileEntity(x, y, z)

            if (tileEntity is TileItemDisplay) {
                val fs = tileEntity

                if (fs.getStackInSlot(0) != null) {
                    dropItemsAtEntity(world, x, y, z, player)
                    return true
                }

                if (player.currentEquippedItem != null) {
                    val item = player.currentEquippedItem.copy()
                    item.stackSize = 1
                    fs.setInventorySlotContents(0, item)

                    --player.currentEquippedItem.stackSize
                    if (player.currentEquippedItem.stackSize == 0) {
                        player.setCurrentItemOrArmor(0, null)
                    }

                    player.inventory.markDirty()
                }
            }
        }

        return true
    }

    fun dropItemsAtEntity(world: World, x: Int, y: Int, z: Int, entity: Entity) {
        Random()
        val tileEntity = world.getTileEntity(x, y, z)
        if (tileEntity is IInventory) {
            for (i in 0..tileEntity.sizeInventory - 1) {
                val item = tileEntity.getStackInSlot(i)
                if (item != null && item.stackSize > 0) {
                    val entityItem = EntityItem(world, entity.posX, entity.posY + (entity.eyeHeight / 2.0f).toDouble(), entity.posZ, item.copy())
                    world.spawnEntityInWorld(entityItem)
                    tileEntity.setInventorySlotContents(i, null)
                }

            }

        }
    }

    override fun isOpaqueCube(): Boolean = false
    override fun renderAsNormalBlock(): Boolean = false
    override fun hasTileEntity(metadata: Int): Boolean = true

    override fun getDrops(world: World, x: Int, y: Int, z: Int, metadata: Int, fortune: Int): ArrayList<ItemStack> {
        return ArrayList()
    }

    override fun createTileEntity(world: World?, meta: Int): TileEntity? {
        return TileItemDisplay()
    }
}
