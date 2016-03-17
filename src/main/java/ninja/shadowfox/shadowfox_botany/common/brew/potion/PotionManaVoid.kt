package ninja.shadowfox.shadowfox_botany.common.brew.potion

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.IInventory
import net.minecraft.item.ItemStack
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.entity.living.LivingEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import ninja.shadowfox.shadowfox_botany.common.core.handler.ConfigHandler
import ninja.shadowfox.shadowfox_botany.common.item.ShadowFoxItems
import vazkii.botania.api.BotaniaAPI
import vazkii.botania.api.mana.IManaItem
import vazkii.botania.common.item.ModItems


class PotionManaVoid : PotionMod("manaVoid", true, 192, 0) {

    init {
        MinecraftForge.EVENT_BUS.register(this)
    }

    @SubscribeEvent
    fun onEntityUpdate(event: LivingEvent.LivingUpdateEvent) {
        val e = event.entityLiving
        if (this.hasEffect(e)) {
            if (e is EntityPlayer) {
                val mainInv = e.inventory
                val baublesInv = BotaniaAPI.internalHandler.getBaublesInventory(e)
                val invSize = mainInv.sizeInventory
                var size = invSize

                if (baublesInv != null) {
                    size = invSize + baublesInv.sizeInventory
                }
                val mana = 1040 // Will drain about 1/4 of a tablet in 5 seconds

                for (i in 0..size) {
                    val useBaubles = i >= invSize
                    val inv = if (useBaubles) baublesInv else mainInv
                    val slot = i - (if (useBaubles) invSize else 0)
                    val stackInSlot = (inv as IInventory).getStackInSlot(slot)
                    if (stackInSlot != null && stackInSlot.item is IManaItem) {
                        val manaItemSlot = stackInSlot.item as IManaItem
                        if (manaItemSlot.getMana(stackInSlot) > mana) {
                            manaItemSlot.addMana(stackInSlot, -mana)

                            if (useBaubles) {
                                BotaniaAPI.internalHandler.sendBaubleUpdatePacket(e, slot)
                            }

                            break
                        } else {
                            manaItemSlot.addMana(stackInSlot, -manaItemSlot.getMana(stackInSlot))
                            if (useBaubles) {
                                BotaniaAPI.internalHandler.sendBaubleUpdatePacket(e, slot)
                            }
                            break
                        }
                    }
                }
                //TODO
//                for (slot in 0..invSize - 1) {
//                    val stackInSlot = mainInv.getStackInSlot(slot)
//                    if (stackInSlot != null && stackInSlot.item == ModItems.blackLotus) {
//                        var wiltStack = ItemStack(ShadowFoxItems.wiltedLotus, stackInSlot.stackSize, stackInSlot.itemDamage)
//                        wiltStack.stackTagCompound = stackInSlot.tagCompound
//                        mainInv.setInventorySlotContents(slot, wiltStack)
//                    }
//                }
            }
        }
    }
}
