package ninja.shadowfox.shadowfox_botany.common.blocks.tile

import net.minecraft.block.BlockHopper
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.ContainerHopper
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.nbt.NBTTagList
import net.minecraft.tileentity.IHopper
import net.minecraft.tileentity.TileEntityHopper

class TileLivingwoodFunnel() : ShadowFoxTile(), IHopper {
    private val slots = intArrayOf(0)
    private var inventory = arrayOfNulls<ItemStack>(1)

    override fun getSizeInventory(): Int = 1
    override fun getStackInSlot(par1: Int): ItemStack? {
        return this.inventory[par1]
    }

    override fun decrStackSize(slot: Int, par2: Int): ItemStack? {
        if (inventory[slot] != null) {

            if (!worldObj.isRemote) {
                worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord)
            }

            val itemstack: ItemStack

            if (inventory[slot]!!.stackSize <= par2) {
                itemstack = inventory[slot]!!
                inventory[slot] = null
                markDirty()
                return itemstack
            } else {
                itemstack = inventory[slot]!!.splitStack(par2)
                if (inventory[slot]!!.stackSize == 0) {
                    inventory[slot] = null
                }

                this.markDirty()
                return itemstack
            }
        } else {
            return null
        }
    }

    override fun getStackInSlotOnClosing(par1: Int): ItemStack? {
        if (inventory[par1] != null) {
            val itemstack = inventory[par1]
            inventory[par1] = null
            return itemstack
        } else {
            return null
        }
    }

    override fun setInventorySlotContents(par1: Int, par2ItemStack: ItemStack?) {
        this.inventory[par1] = par2ItemStack
        if (par2ItemStack != null && par2ItemStack.stackSize > this.inventoryStackLimit) {
            par2ItemStack.stackSize = this.inventoryStackLimit
        }

        this.markDirty()
        if (!this.worldObj.isRemote) {
            this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord)
        }

    }

    override fun getInventoryName(): String = "container.livingwoodHopper"
    override fun isUseableByPlayer(p_70300_1_: EntityPlayer?): Boolean = true
    override fun hasCustomInventoryName(): Boolean = false

    override fun readCustomNBT(nbttagcompound: NBTTagCompound) {
        val nbttaglist = nbttagcompound.getTagList("Items", 10)
        this.inventory = arrayOfNulls<ItemStack>(this.sizeInventory)

        for (i in 0..nbttaglist.tagCount() - 1) {
            val nbttagcompound1 = nbttaglist.getCompoundTagAt(i)

            val b0: Int = (nbttagcompound1.getByte("Slot")).toInt()

            if (b0 >= 0 && b0 < inventory.size) {
                this.inventory[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1)
            }
        }

    }

    override fun writeCustomNBT(nbttagcompound: NBTTagCompound) {
        val nbttaglist = NBTTagList()

        for (i in this.inventory.indices) {
            if (this.inventory[i] != null) {
                val nbttagcompound1 = NBTTagCompound()
                nbttagcompound1.setByte("Slot", i.toByte())
                inventory[i]!!.writeToNBT(nbttagcompound1)
                nbttaglist.appendTag(nbttagcompound1)
            }
        }

        nbttagcompound.setTag("Items", nbttaglist)
    }

    override fun getXPos(): Double = xCoord.toDouble()
    override fun getYPos(): Double = yCoord.toDouble()
    override fun getZPos(): Double = zCoord.toDouble()

    override fun openInventory() {}
    override fun closeInventory() {}

    override fun getInventoryStackLimit(): Int = 1

    override fun canUpdate(): Boolean = true

    override fun isItemValidForSlot(par1: Int, par2ItemStack: ItemStack): Boolean = true
}
