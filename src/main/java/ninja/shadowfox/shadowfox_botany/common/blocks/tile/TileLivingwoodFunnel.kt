package ninja.shadowfox.shadowfox_botany.common.blocks.tile

import net.minecraft.block.BlockChest
import net.minecraft.block.BlockHopper
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.ScaledResolution
import net.minecraft.client.renderer.entity.RenderItem
import net.minecraft.command.IEntitySelector
import net.minecraft.entity.item.EntityItem
import net.minecraft.entity.item.EntityItemFrame
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.IInventory
import net.minecraft.inventory.ISidedInventory
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.nbt.NBTTagList
import net.minecraft.tileentity.IHopper
import net.minecraft.tileentity.TileEntityChest
import net.minecraft.util.AxisAlignedBB
import net.minecraft.util.Facing
import net.minecraft.util.MathHelper
import net.minecraft.world.World
import ninja.shadowfox.shadowfox_botany.common.blocks.BlockFunnel
import org.lwjgl.opengl.GL11
import vazkii.botania.common.lib.LibMisc

class TileLivingwoodFunnel() : TileMod(), IHopper {
    private var inventory = arrayOfNulls<ItemStack>(1)
    private var transferCooldown = -1

    override fun getSizeInventory(): Int = 1
    override fun getStackInSlot(par1: Int): ItemStack? = inventory[par1]

    override fun updateEntity() {
        if (worldObj != null && !worldObj.isRemote) {
            --transferCooldown

            if (transferCooldown <= 0) {
                transferCooldown = 0
                if (worldObj != null && !worldObj.isRemote) {
                    if (transferCooldown <= 0 && BlockFunnel.getActiveStateFromMetadata(getBlockMetadata())) {
                        var flag = false

                        if (!isEmpty()) {
                            flag = pushToAttachedInventory()
                        }

                        if (!isFull()) {
                            flag = canHopperPull(this) || flag
                        }

                        if (flag) {
                            transferCooldown = 8
                            markDirty()
                        }
                    }
                }
            }
        }
    }


    fun getInventoryAt(world: World, x: Double, y: Double, z: Double): IInventory? {
        var iinventory: IInventory? = null
        val i = MathHelper.floor_double(x)
        val j = MathHelper.floor_double(y)
        val k = MathHelper.floor_double(z)
        val tileentity = world.getTileEntity(i, j, k)

        if (tileentity != null && tileentity is IInventory) {
            iinventory = tileentity

            if (iinventory is TileEntityChest) {
                val block = world.getBlock(i, j, k)

                if (block is BlockChest) {
                    iinventory = block.func_149951_m(world, i, j, k)
                }
            }
        }

        if (iinventory == null) {
            val list = world.getEntitiesWithinAABBExcludingEntity(null, AxisAlignedBB.getBoundingBox(x, y, z, x + 1.0, y + 1.0, z + 1.0), IEntitySelector.selectInventories)

            if (list != null && list.size > 0) {
                iinventory = list[world.rand.nextInt(list.size)] as IInventory
            }
        }

        return iinventory
    }

    fun canHopperPull(funnel: IHopper): Boolean {
        val iinventory = getInventoryAbove(funnel)
        if (iinventory != null) {
            if (inventoryEmpty(iinventory, 0)) return false

            if (iinventory is ISidedInventory) {
                for (slot in iinventory.getAccessibleSlotsFromSide(0)) {
                    if (pullItemIn(funnel, iinventory, slot, 0)) return true
                }
            } else {
                for (slot in 0..iinventory.sizeInventory - 1) {
                    if (pullItemIn(funnel, iinventory, slot, 0)) return true
                }
            }
        } else {
            val entityitem = entitiesOnFunnel(funnel.worldObj, funnel.xPos, funnel.yPos + 1.0, funnel.zPos)

            if (entityitem != null) {
                return pullEntityFromWorld(funnel, entityitem)
            }
        }

        return false
    }


    fun getInventoryAbove(hopper: IHopper): IInventory? {
        return getInventoryAt(hopper.worldObj, hopper.xPos, hopper.yPos + 1.0, hopper.zPos)
    }


    private fun isFull(): Boolean {
        val aitemstack = inventory
        val i = aitemstack.size

        for (j in 0..i - 1) {
            val itemstack = aitemstack[j]

            if (itemstack == null || itemstack.stackSize != itemstack.maxStackSize) {
                return false
            }
        }

        return true
    }

    private fun isEmpty(): Boolean {
        val aitemstack = inventory
        val i = aitemstack.size

        for (j in 0..i - 1) {
            val itemstack = aitemstack[j]

            if (itemstack != null) {
                return false
            }
        }

        return true
    }

    private fun isFull(inventory: IInventory, side: Int): Boolean {
        if (inventory is ISidedInventory && side > -1) {
            val aint = inventory.getAccessibleSlotsFromSide(side)

            for (l in aint.indices) {
                val itemstack1 = inventory.getStackInSlot(aint[l])

                if (itemstack1 == null || itemstack1.stackSize != itemstack1.maxStackSize) {
                    return false
                }
            }
        } else {
            val j = inventory.sizeInventory

            for (k in 0..j - 1) {
                val itemstack = inventory.getStackInSlot(k)

                if (itemstack == null || itemstack.stackSize != itemstack.maxStackSize) {
                    return false
                }
            }
        }

        return true
    }

    private fun pushToAttachedInventory(): Boolean {
        val iinventory = this.getFacingInventory()

        if (iinventory == null) {
            return false
        } else {
            val i = Facing.oppositeSide[BlockHopper.getDirectionFromMetadata(this.getBlockMetadata())]

            if (this.isFull(iinventory, i)) {
                return false
            } else {
                for (j in 0..this.sizeInventory - 1) {
                    if (this.getStackInSlot(j) != null) {
                        val itemstack = this.getStackInSlot(j)?.copy()
                        val itemstack1 = iinventory.addItemToSide(this.decrStackSize(j, 1), i)

                        if (itemstack1 == null || itemstack1.stackSize == 0) {
                            iinventory.markDirty()
                            return true
                        }

                        this.setInventorySlotContents(j, itemstack)
                    }
                }

                return false
            }
        }
    }

    private fun getFacingInventory(): IInventory? {
        val i = BlockFunnel.getDirectionFromMetadata(getBlockMetadata())
        return getInventoryAt(getWorldObj(), (xCoord + Facing.offsetsXForSide[i]).toDouble(), (yCoord + Facing.offsetsYForSide[i]).toDouble(), (zCoord + Facing.offsetsZForSide[i]).toDouble())
    }

    fun IInventory.addItemToSide(item: ItemStack?, side: Int): ItemStack? {
        var stack = item
        if (inventory is ISidedInventory && side > -1) {
            val aint = (this as ISidedInventory).getAccessibleSlotsFromSide(side)

            var l = 0
            while (l < aint.size && stack != null && stack.stackSize > 0) {
                stack = pushToInventory(this, stack, aint[l], side)
                ++l
            }
        } else {
            val j = this.sizeInventory

            var k = 0
            while (k < j && stack != null && stack.stackSize > 0) {
                stack = pushToInventory(this, stack, k, side)
                ++k
            }
        }

        if (stack != null && stack.stackSize == 0) {
            stack = null
        }

        return stack
    }

    private fun canInsertItem(inventory: IInventory, stack: ItemStack, slot: Int, side: Int): Boolean {
        return if (!inventory.isItemValidForSlot(slot, stack)) false else inventory !is ISidedInventory || inventory.canInsertItem(slot, stack, side)
    }


    private fun pushToInventory(inventory: IInventory, item: ItemStack?, slot: Int, side: Int): ItemStack? {
        var stack = item
        val itemstack1 = inventory.getStackInSlot(slot)

        if (stack != null && canInsertItem(inventory, stack, slot, side)) {
            var flag = false

            if (itemstack1 == null) {
                val max = Math.min(stack.maxStackSize, inventory.inventoryStackLimit)
                if (max >= stack.stackSize) {
                    inventory.setInventorySlotContents(slot, stack)
                    stack = null
                } else {
                    inventory.setInventorySlotContents(slot, stack.splitStack(max))
                }
                flag = true
            } else if (canAddToStack(itemstack1, stack)) {
                val max = Math.min(stack.maxStackSize, inventory.inventoryStackLimit)
                if (max > itemstack1.stackSize) {
                    val l = Math.min(stack.stackSize, max - itemstack1.stackSize)
                    stack.stackSize -= l
                    itemstack1.stackSize += l
                    flag = l > 0
                }
            }

            if (flag) {
                if (inventory is TileLivingwoodFunnel) {
                    inventory.transferCooldown = 8
                    inventory.markDirty()
                }

                inventory.markDirty()
            }
        }

        return stack
    }

    private fun canAddToStack(stack: ItemStack, mainStack: ItemStack): Boolean {
        return if (stack.item !== mainStack.item) false else (if (stack.itemDamage != mainStack.itemDamage) false else (if (stack.stackSize > stack.maxStackSize) false else ItemStack.areItemStackTagsEqual(stack, mainStack)))
    }

    fun pullEntityFromWorld(inventory: IInventory, item: EntityItem?): Boolean {
        var flag = false

        if (item == null) {
            return false
        } else {
            val itemstack = item.entityItem
            if (itemstack.itemInFrames()) {
                val itemstack1 = inventory.addItemToSide(itemstack, -1)

                if (itemstack1 != null && itemstack1.stackSize != 0) {
                    item.setEntityItemStack(itemstack1)
                } else {
                    flag = true
                    item.setDead()
                }
            }

            return flag
        }
    }

    fun entitiesOnFunnel(world: World, x: Double, y: Double, z: Double): EntityItem? {
        val list = world.selectEntitiesWithinAABB(EntityItem::class.java, AxisAlignedBB.getBoundingBox(x, y, z, x + 1.0, y + 1.0, z + 1.0), IEntitySelector.selectAnything)
        return if (list.size > 0) list[0] as EntityItem else null
    }

    private fun pullItemIn(hopper: IHopper, inventory: IInventory, slot: Int, side: Int): Boolean {
        val itemstack = inventory.getStackInSlot(slot)

        if (itemstack != null && canPullItem(inventory, itemstack, slot, side)) {
            if (itemstack.itemInFrames()) {

                val itemstack1 = itemstack.copy()
                val itemstack2 = hopper.addItemToSide(inventory.decrStackSize(slot, 1), -1)

                if (itemstack2 == null || itemstack2.stackSize == 0) {
                    inventory.markDirty()
                    return true
                }

                inventory.setInventorySlotContents(slot, itemstack1)
            }
        }

        return false
    }

    private fun ItemStack.itemInFrames(): Boolean {
        var frameItems: MutableList<ItemStack> = arrayListOf()
        for (i in LibMisc.CARDINAL_DIRECTIONS) {
            val var21 = AxisAlignedBB.getBoundingBox((xCoord + i.offsetX).toDouble(), (yCoord + i.offsetY).toDouble(), (zCoord + i.offsetZ).toDouble(), (xCoord + i.offsetX + 1).toDouble(), (yCoord + i.offsetY + 1).toDouble(), (zCoord + i.offsetZ + 1).toDouble())
            val frames = worldObj.getEntitiesWithinAABB(EntityItemFrame::class.java, var21)
            for (frame in frames) {
                if (frame is EntityItemFrame)
                    if (frame.displayedItem != null)
                        frameItems.add(frame.displayedItem)
            }
        }
        if (frameItems.isEmpty()) return true
        for (i in frameItems) {
            if (this.item === i.item && this.itemDamage == i.itemDamage) return true
        }

        return false
    }

    private fun canPullItem(inventory: IInventory, stack: ItemStack, slot: Int, side: Int): Boolean {
        return inventory !is ISidedInventory || inventory.canExtractItem(slot, stack, side)
    }

    private fun inventoryEmpty(inventory: IInventory, side: Int): Boolean {
        if (inventory is ISidedInventory && side > -1) {
            val aint = inventory.getAccessibleSlotsFromSide(side)

            for (l in inventory.getAccessibleSlotsFromSide(side)) {
                if (inventory.getStackInSlot(aint[l]) != null) {
                    return false
                }
            }
        } else {
            for (k in 0..inventory.sizeInventory - 1) {
                if (inventory.getStackInSlot(k) != null) {
                    return false
                }
            }
        }

        return true
    }

    fun renderHUD(mc: Minecraft, res: ScaledResolution) {
        if (getStackInSlot(0) != null && getStackInSlot(0)!!.stackSize > 0 ) {
            net.minecraft.client.renderer.RenderHelper.enableGUIStandardItemLighting()

            val xc = res.scaledWidth / 2.0
            val yc = res.scaledHeight / 2.0
            GL11.glTranslated(xc, yc, 0.0)
            RenderItem.getInstance().renderItemIntoGUI(mc.fontRenderer, mc.renderEngine, getStackInSlot(0), 0, 0)
            GL11.glTranslated(-xc, -yc, 0.0)

            net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting()
        }
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
        inventory[par1] = par2ItemStack
        if (par2ItemStack != null && par2ItemStack.stackSize > inventoryStackLimit) {
            par2ItemStack.stackSize = inventoryStackLimit
        }

        markDirty()
        if (!worldObj.isRemote) {
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord)
        }

    }

    override fun getInventoryName(): String = "container.livingwoodHopper"
    override fun isUseableByPlayer(player: EntityPlayer?): Boolean = true
    override fun hasCustomInventoryName(): Boolean = false

    override fun readCustomNBT(nbttagcompound: NBTTagCompound) {
        val nbttaglist = nbttagcompound.getTagList("Items", 10)
        inventory = arrayOfNulls<ItemStack>(sizeInventory)

        for (i in 0..nbttaglist.tagCount() - 1) {
            val nbttagcompound1 = nbttaglist.getCompoundTagAt(i)

            val b0: Int = (nbttagcompound1.getByte("Slot")).toInt()

            if (b0 >= 0 && b0 < inventory.size) {
                inventory[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1)
            }
        }

    }

    override fun writeCustomNBT(nbttagcompound: NBTTagCompound) {
        val nbttaglist = NBTTagList()

        for (i in inventory.indices) {
            if (inventory[i] != null) {
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
    override fun openInventory() {
    }

    override fun closeInventory() {
    }

    override fun getInventoryStackLimit(): Int = 1
    override fun canUpdate(): Boolean = true
    override fun isItemValidForSlot(par1: Int, par2ItemStack: ItemStack): Boolean = true
}
