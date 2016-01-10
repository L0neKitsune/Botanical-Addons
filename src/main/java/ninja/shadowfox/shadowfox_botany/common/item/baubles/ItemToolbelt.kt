package ninja.shadowfox.shadowfox_botany.common.item.baubles

import baubles.api.BaubleType
import baubles.common.lib.PlayerHandler
import cpw.mods.fml.common.eventhandler.SubscribeEvent
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.MathHelper
import net.minecraftforge.event.entity.player.PlayerInteractEvent
import vazkii.botania.common.core.helper.ItemNBTHelper
import vazkii.botania.common.item.equipment.bauble.ItemBauble
import kotlin.text.replace
import kotlin.text.toRegex

class ItemToolbelt() : ItemBauble("toolbelt") {
    companion object {
        val SEGMENTS = 12

        val TAG_ITEM_PREFIX = "item";
        val TAG_EQUIPPED = "equipped";
        val TAG_ROTATION_BASE = "rotationBase";

        fun isEquipped(stack: ItemStack): Boolean = ItemNBTHelper.getBoolean(stack, TAG_EQUIPPED, false)
        fun setEquipped(stack: ItemStack, equipped: Boolean) = ItemNBTHelper.setBoolean(stack, TAG_EQUIPPED, equipped)
        fun getRotationBase(stack: ItemStack): Float = ItemNBTHelper.getFloat(stack, TAG_ROTATION_BASE, 0F)
        fun setRotationBase(stack: ItemStack, rotation: Float) = ItemNBTHelper.setFloat(stack, TAG_ROTATION_BASE, rotation)

        fun getSegmentLookedAt(stack: ItemStack, player: EntityLivingBase): Int {
            val yaw = getCheckingAngle(player, getRotationBase(stack))

            val angles = 360
            val segAngles = angles / SEGMENTS
            for (seg in 0..SEGMENTS - 1) {
                val calcAngle = seg.toFloat() * segAngles
                if (yaw >= calcAngle && yaw < calcAngle + segAngles)
                    return seg
            }
            return -1
        }

        fun getCheckingAngle(player: EntityLivingBase): Float = getCheckingAngle(player, 0F)

        // Agreed, V, minecraft's rotation is shit. And no roll? Seriously?
        fun getCheckingAngle(player: EntityLivingBase, base: Float): Float {
            var yaw = MathHelper.wrapAngleTo180_float(player.rotationYaw) + 90F
            val angles = 360
            val segAngles = angles / SEGMENTS
            val shift = segAngles / 2

            if (yaw < 0)
                yaw = 180F + (180F + yaw)
            yaw -= 360F - base
            var angle = 360F - yaw + shift

            if (angle < 0)
                angle += 360F

            return angle
        }

        fun getItemForSlot(stack: ItemStack, slot: Int): ItemStack? {
            if (slot >= SEGMENTS) return null
            else {
                val cmp = getStoredCompound(stack, slot)
                return ItemStack.loadItemStackFromNBT(cmp)
            }
        }

        fun getStoredCompound(stack: ItemStack, slot: Int): NBTTagCompound? = ItemNBTHelper.getCompound(stack, TAG_ITEM_PREFIX + slot, true)
        fun setItem(beltStack: ItemStack, stack: ItemStack?, pos: Int) {
            if (stack == null) ItemNBTHelper.setCompound(beltStack, TAG_ITEM_PREFIX + pos, NBTTagCompound())
            else {
                var tag = NBTTagCompound()
                stack.writeToNBT(tag)
                ItemNBTHelper.setCompound(beltStack, TAG_ITEM_PREFIX + pos, tag)
            }
        }
    }

    @SubscribeEvent
    fun onPlayerInteract(event: PlayerInteractEvent) {
        val player = event.entityPlayer
        val inv = PlayerHandler.getPlayerBaubles(player)
        var beltStack: ItemStack? = null
        for (i in 0..inv.sizeInventory) {
            var stack = inv.getStackInSlot(i)
            if (stack.item == this) {
                beltStack = stack
            }
        }
        var heldItem = player.currentEquippedItem
        if (beltStack != null && isEquipped(beltStack)) {
            if (event.action === PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK || event.action === PlayerInteractEvent.Action.RIGHT_CLICK_AIR) {
                val segment = getSegmentLookedAt(beltStack, player)
                val toolStack = getItemForSlot(beltStack, segment)
                if (toolStack == null && heldItem != null) {
                    setItem(beltStack, heldItem, segment)
                    player.inventory.setInventorySlotContents(player.inventory.currentItem, null)
                } else if (toolStack != null) {
                    if (player.inventory.addItemStackToInventory(toolStack)) {
                        setItem(beltStack, null, segment)
                    }
                }
            }
        }
    }

    override fun getBaubleType(stack: ItemStack): BaubleType {
        return BaubleType.BELT
    }

    override fun onWornTick(stack: ItemStack, player: EntityLivingBase) {
        if (player is EntityPlayer) {
            val eqLastTick = isEquipped(stack)
            val sneak = player.isSneaking
            if (eqLastTick != sneak)
                setEquipped(stack, sneak)

            if (!sneak) {
                val angles = 360
                val segAngles = angles / SEGMENTS
                val shift = segAngles / 2
                setRotationBase(stack, getCheckingAngle(player) - shift);
            }
        }
    }

    override fun getUnlocalizedNameInefficiently(par1ItemStack: ItemStack): String {
        return super.getUnlocalizedNameInefficiently(par1ItemStack).replace("item\\.botania:".toRegex(), "item.shadowfox_botany:")
    }
}
