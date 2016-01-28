package ninja.shadowfox.shadowfox_botany.common.entity


import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.projectile.EntityThrowable
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.nbt.NBTTagList
import net.minecraft.util.MovingObjectPosition
import net.minecraft.world.World
import ninja.shadowfox.shadowfox_botany.api.ShadowFoxAPI
import ninja.shadowfox.shadowfox_botany.api.item.ThrowableCollidingItem


class EntityThrowableItem : EntityThrowable
{
    constructor(world: World) : super(world) {}

    constructor(player: EntityPlayer, item: ItemStack, effect_key: String) : super(player.worldObj, player) {
        event = ShadowFoxAPI.getThrowableFromKey(effect_key)
        this.item = item
    }
    var item: ItemStack? = null
    var event: ThrowableCollidingItem? = null

    override fun onImpact(movingObject: MovingObjectPosition?) {
        if (!this.worldObj.isRemote && movingObject != null) {
            event?.onImpact(this, item, movingObject)
        }

        worldObj.playAuxSFX(2002, Math.round(posX).toInt(), Math.round(posY).toInt(), Math.round(posZ).toInt(), 0)
        setDead()
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    override fun writeEntityToNBT(p_70014_1_: NBTTagCompound) {
        event?.let {
            p_70014_1_.setString("effect_key", it.key)

            val nbttaglist = NBTTagList()

            if (item != null) {
                val nbttagcompound1 = NBTTagCompound()
                item!!.writeToNBT(nbttagcompound1)
                p_70014_1_.setTag("item", nbttaglist)
            }
        }
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    override fun readEntityFromNBT(p_70037_1_: NBTTagCompound) {
        event = ShadowFoxAPI.getThrowableFromKey(p_70037_1_.getString("effect_key"))

        if (p_70037_1_.hasKey("item")) {
            item = ItemStack.loadItemStackFromNBT(p_70037_1_.getCompoundTag("item"))
        }
    }
}
