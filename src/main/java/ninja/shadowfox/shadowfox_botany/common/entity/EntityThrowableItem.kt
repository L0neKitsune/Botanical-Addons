package ninja.shadowfox.shadowfox_botany.common.entity

import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.projectile.EntityThrowable
import net.minecraft.init.Blocks
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.MathHelper
import net.minecraft.util.MovingObjectPosition
import net.minecraft.world.World
import ninja.shadowfox.shadowfox_botany.api.ShadowFoxAPI
import ninja.shadowfox.shadowfox_botany.api.item.ImpactEventAction
import ninja.shadowfox.shadowfox_botany.api.item.ThrowableCollidingItem


class EntityThrowableItem : EntityThrowable
{
    constructor(world: World) : super(world) {}

    constructor(player: EntityPlayer, effect_key: String) : super(player.worldObj, player) {
        event = ShadowFoxAPI.getThrowableFromKey(effect_key)
    }

    var event: ThrowableCollidingItem? = null

    override fun onImpact(movingObject: MovingObjectPosition?) {
        if (!this.worldObj.isRemote && movingObject != null) {
            event?.onImpact(this, movingObject)
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
        }
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    override fun readEntityFromNBT(p_70037_1_: NBTTagCompound) {
        event = ShadowFoxAPI.getThrowableFromKey(p_70037_1_.getString("effect_key"))
    }
}
