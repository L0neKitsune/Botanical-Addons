package ninja.shadowfox.shadowfox_botany.common.item.throwables

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.EnumAction
import net.minecraft.item.ItemStack
import net.minecraft.world.World
import ninja.shadowfox.shadowfox_botany.common.entity.EntityThrowableItem
import ninja.shadowfox.shadowfox_botany.common.item.ItemMod
import ninja.shadowfox.shadowfox_botany.common.item.ShadowFoxItems
import ninja.shadowfox.shadowfox_botany.lib.ThrowableKeys
import vazkii.botania.common.item.ModItems


class ItemGrassCannon() : ItemMod("grassCannon") {

    init {
        maxStackSize = 1
    }

    override fun onPlayerStoppedUsing(par1ItemStack: ItemStack?, par2World: World?, par3EntityPlayer: EntityPlayer?, par4: Int) {
        val j = this.getMaxItemUseDuration(par1ItemStack) - par4
        if (par3EntityPlayer!!.capabilities.isCreativeMode || par3EntityPlayer.inventory.hasItem(ModItems.vineBall)) {
            var f = j.toFloat() / 20.0f
            f = (f * f + f * 2.0f) / 3.0f
            if (f < 1.0f) {
                return
            }

            if (!par3EntityPlayer.capabilities.isCreativeMode) {
                par3EntityPlayer.inventory.consumeInventoryItem(ModItems.vineBall)
            }

            if (!par2World!!.isRemote) {

                val item = ItemStack(ShadowFoxItems.irisSeeds, 0)
                item.stackSize = 1
//                stack.stackSize--

                val entity = EntityThrowableItem(par3EntityPlayer, item, ThrowableKeys.GRASS_GRENADE)
                entity.motionX *= 1.6
                entity.motionY *= 1.6
                entity.motionZ *= 1.6
                par2World.spawnEntityInWorld(entity)

            }
        }

    }

    override fun onItemRightClick(stack: ItemStack?, world: World?, player: EntityPlayer?): ItemStack? {

        if (player!!.capabilities.isCreativeMode || player!!.inventory.hasItem(ModItems.vineBall)) {
            player.setItemInUse(stack, this.getMaxItemUseDuration(stack))
        }

        return stack
    }

    override fun onEaten(par1ItemStack: ItemStack, par2World: World?, par3EntityPlayer: EntityPlayer?): ItemStack {
        return par1ItemStack
    }

    override fun getMaxItemUseDuration(par1ItemStack: ItemStack?): Int {
        return 72000
    }

    override fun getItemUseAction(par1ItemStack: ItemStack?): EnumAction {
        return EnumAction.bow
    }
}
