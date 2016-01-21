package ninja.shadowfox.shadowfox_botany.common.entity


import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.projectile.EntityThrowable
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.MovingObjectPosition
import ninja.shadowfox.shadowfox_botany.api.ShadowFoxAPI
import ninja.shadowfox.shadowfox_botany.api.item.ThrowableCollidingItem


class EntityThrownItem(player: EntityPlayer, var event: ThrowableCollidingItem) :
        EntityThrowable(player.worldObj, player) {


    override fun onImpact(movingObject: MovingObjectPosition?) {
        if (movingObject != null) {
            event.onImpact(this, movingObject)
        }

        worldObj.playAuxSFX(2002, Math.round(posX).toInt(), Math.round(posY).toInt(), Math.round(posZ).toInt(), 0)
        setDead()
    }

    override fun writeEntityToNBT(p_70014_1_: NBTTagCompound) {
        p_70014_1_.setString("eventKey", event.key)
    }

    override fun readEntityFromNBT(p_70037_1_: NBTTagCompound) {
        this.event = ShadowFoxAPI.getThrowableFromKey(p_70037_1_.getString("eventKey"))
    }
}
