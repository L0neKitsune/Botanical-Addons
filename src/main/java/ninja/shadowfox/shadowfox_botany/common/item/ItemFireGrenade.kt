package ninja.shadowfox.shadowfox_botany.common.item

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Items
import net.minecraft.item.ItemStack
import net.minecraft.util.IIcon
import net.minecraft.world.World
import ninja.shadowfox.shadowfox_botany.common.entity.EntityThrowableItem

class ItemFireGrenade() : ItemMod("fireGrenade") {
    init {
    }

    override fun onItemRightClick(stack: ItemStack?, world: World?, player: EntityPlayer?): ItemStack? {
        if (stack != null && world != null && player != null) {
            if (!world.isRemote) {
                val potion = EntityThrowableItem(player)
                //                potion.event = ShadowFoxAPI.getThrowableFromKey("shadowfox_fire_grenade")
                potion.motionX *= 1.6
                potion.motionY *= 1.6
                potion.motionZ *= 1.6
                world.spawnEntityInWorld(potion)

                stack.stackSize--
            }
        }

        return stack
    }


    override fun getIcon(stack: ItemStack, pass: Int): IIcon {
        return Items.fire_charge.getIconFromDamage(0)
    }

}
