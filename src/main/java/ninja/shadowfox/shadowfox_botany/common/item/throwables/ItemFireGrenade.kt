package ninja.shadowfox.shadowfox_botany.common.item.throwables

import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Items
import net.minecraft.item.ItemStack
import net.minecraft.util.IIcon
import net.minecraft.world.World
import ninja.shadowfox.shadowfox_botany.common.entity.EntityThrowableItem
import ninja.shadowfox.shadowfox_botany.common.item.ItemMod
import ninja.shadowfox.shadowfox_botany.lib.ThrowableKeys

class ItemFireGrenade() : ItemMod("fireGrenade") {
    init {
    }

    override fun onItemRightClick(stack: ItemStack?, world: World?, player: EntityPlayer?): ItemStack? {
        if (stack != null && world != null && player != null) {
            if (!world.isRemote) {

                val item = stack.copy()
                item.stackSize = 1
                stack.stackSize--

                val potion = EntityThrowableItem(player, item, ThrowableKeys.FIRE_GRENADE)
                potion.motionX *= 1.6
                potion.motionY *= 1.6
                potion.motionZ *= 1.6
                world.spawnEntityInWorld(potion)
            }
        }

        return stack
    }

    override fun registerIcons(par1IconRegister: IIconRegister) {}

    override fun getIcon(stack: ItemStack, pass: Int): IIcon {
        return Items.fire_charge.getIcon(stack, pass)
    }

}
