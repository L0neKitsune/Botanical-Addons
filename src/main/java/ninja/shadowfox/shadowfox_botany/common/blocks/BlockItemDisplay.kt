package ninja.shadowfox.shadowfox_botany.common.blocks

import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.entity.Entity
import net.minecraft.entity.item.EntityItem
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraft.inventory.IInventory
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.IIcon
import net.minecraft.world.World
import ninja.shadowfox.shadowfox_botany.common.blocks.base.ShadowFoxBlockMod
import ninja.shadowfox.shadowfox_botany.common.blocks.tile.TileItemDisplay
import java.util.*


class BlockItemDisplay() : ShadowFoxBlockMod(Material.wood) {

    override val registerInCreative = true

    init {
        this.setBlockName("itemDisplay")
        this.setStepSound(Block.soundTypeWood)
        this.setLightLevel(1.0f)
    }

    override fun registerBlockIcons(par1IconRegister: IIconRegister) {
    }

    override fun getIcon(side: Int, meta: Int): IIcon {
        return Blocks.stone.getIcon(side, meta)
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

    override fun isOpaqueCube(): Boolean = true
    override fun renderAsNormalBlock(): Boolean = true
    override fun hasTileEntity(metadata: Int): Boolean = true

    override fun getDrops(world: World, x: Int, y: Int, z: Int, metadata: Int, fortune: Int): ArrayList<ItemStack> {
        return ArrayList()
    }

    override fun createTileEntity(world: World?, meta: Int): TileEntity? {
        return TileItemDisplay()
    }
}