package ninja.shadowfox.shadowfox_botany.common.item

import net.minecraft.item.ItemStack
import net.minecraft.entity.player.EntityPlayer
import baubles.common.lib.PlayerHandler

interface IPriestColorOverride {
    companion object {
        public fun getColor(player: EntityPlayer?): Int? {
            if (player != null) {
                var held = player.inventory.getCurrentItem()
                if (held != null && held.item is IPriestColorOverride)
                    return (held.item as IPriestColorOverride).colorOverride(held)
                else {
                    var baubles = PlayerHandler.getPlayerBaubles(player)
                    for (i in 0..3) {
                        var stack = baubles.getStackInSlot(i)
                        if (stack != null && stack.item is IPriestColorOverride) 
                            return (stack.item as IPriestColorOverride).colorOverride(stack)
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