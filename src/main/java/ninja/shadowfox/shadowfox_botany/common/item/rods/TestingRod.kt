package ninja.shadowfox.shadowfox_botany.common.item.rods

import net.minecraft.entity.effect.EntityLightningBolt
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.world.World
import ninja.shadowfox.shadowfox_botany.common.item.StandardItem

class TestingRod(name: String = "testingRod") : StandardItem(name) {

    init {
        setMaxStackSize(1)
    }

    override fun onItemRightClick(par1ItemStack: ItemStack, par2World: World?, par3EntityPlayer: EntityPlayer?): ItemStack {
        if(par2World != null && par3EntityPlayer != null)
            spawnLightning(par2World, par3EntityPlayer.posX,par3EntityPlayer.posY, par3EntityPlayer.posZ)

        return par1ItemStack
    }

    fun spawnLightning(world: World, posX: Double, posY: Double, posZ: Double): Boolean {
        return world.addWeatherEffect(EntityLightningBolt(world, posX, posY, posZ))
    }

    override fun isFull3D(): Boolean {
        return true
    }

}