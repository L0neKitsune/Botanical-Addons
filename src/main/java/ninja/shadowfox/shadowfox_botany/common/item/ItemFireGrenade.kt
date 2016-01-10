package ninja.shadowfox.shadowfox_botany.common.item

import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.IIcon
import net.minecraft.world.World
import ninja.shadowfox.shadowfox_botany.common.entity.EntityFireAOE
import vazkii.botania.client.core.handler.ClientTickHandler
import vazkii.botania.client.core.helper.IconHelper
import java.awt.Color


class ItemFireGrenade() : ItemMod("fireGrenade") {
    lateinit var itemIconFluid: IIcon

    init {

    }

    override fun onItemRightClick(stack: ItemStack?, world: World?, player: EntityPlayer?): ItemStack? {
        if (stack != null && world != null && player != null) {
            if (!world.isRemote) {
                val potion = EntityFireAOE(player)
                potion.motionX *= 1.6
                potion.motionY *= 1.6
                potion.motionZ *= 1.6
                world.spawnEntityInWorld(potion)

                stack.stackSize--
            }
        }

        return stack
    }

    override fun getColorFromItemStack(stack: ItemStack?, pass: Int): Int {
        if (stack != null) {
            if (pass == 0) {
                return 0xFFFFFF
            } else {
                val color = Color(192)
                val add = (Math.sin(ClientTickHandler.ticksInGame.toDouble() * 0.1) * 16.0).toInt()
                val r = Math.max(0, Math.min(255, color.red + add))
                val g = Math.max(0, Math.min(255, color.green + add))
                val b = Math.max(0, Math.min(255, color.blue + add))
                return r shl 16 or g shl 8 or b
            }
        }

        return 0xFFFFFF
    }

    override fun registerIcons(par1IconRegister: IIconRegister) {
        itemIcon = IconHelper.forName(par1IconRegister, "vial" + "0")
        itemIconFluid = IconHelper.forName(par1IconRegister, "vial" + "1_0")
    }


    override fun getIcon(stack: ItemStack, pass: Int): IIcon {
        return if (pass == 0) itemIcon else itemIconFluid
    }

}
