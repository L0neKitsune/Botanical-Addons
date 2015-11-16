package ninja.shadowfox.shadowfox_botany.common.item

import net.minecraft.item.ItemStack
import net.minecraft.entity.player.EntityPlayer
import vazkii.botania.api.item.ICosmeticAttachable
import baubles.common.lib.PlayerHandler

interface IPriestColorOverride {
    companion object {
        public fun getColor(player: EntityPlayer?): Int? {
            if (player != null) {
                var baubles = PlayerHandler.getPlayerBaubles(player)
                for (i in 0..3) {
                    var stack = baubles.getStackInSlot(i)
                    if (stack != null && stack.item is IPriestColorOverride) 
                        return (stack.item as IPriestColorOverride).colorOverride(stack)
                    else if (stack != null && stack.item is ICosmeticAttachable && (stack.item as ICosmeticAttachable).getCosmeticItem(stack) != null) {
                        var cosmeticStack = (stack.item as ICosmeticAttachable).getCosmeticItem(stack)
                        if (cosmeticStack != null && cosmeticStack.item is IPriestColorOverride) 
                            return (cosmeticStack.item as IPriestColorOverride).colorOverride(cosmeticStack)
                    }
                }
            }
            return null
        }
        public fun getColor(player: EntityPlayer?, default: Int): Int {
            var color = getColor(player)
            if (color != null) 
                return color
            return default
        }
    }
    fun colorOverride(stack: ItemStack?): Int?
}