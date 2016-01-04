package ninja.shadowfox.shadowfox_botany.common.entity

import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.projectile.EntityThrowable
import net.minecraft.potion.Potion
import net.minecraft.potion.PotionEffect
import net.minecraft.util.MovingObjectPosition


class EntityThrownPotion(player: EntityPlayer, val effects: List<PotionEffect>) : EntityThrowable(player.worldObj, player) {

    init {
        this.dataWatcher.addObject(30, java.lang.Float.valueOf(.03f))
        this.dataWatcher.setObjectWatched(30)
    }

    override fun onImpact(movingObject: MovingObjectPosition?) {
        if (!this.worldObj.isRemote && movingObject != null && effects.isNotEmpty()) {
            val axisalignedbb = this.boundingBox.expand(4.0, 2.0, 4.0)
            val list1 = this.worldObj.getEntitiesWithinAABB(EntityLivingBase::class.java, axisalignedbb)

            if (list1 != null && !list1.isEmpty()) {
                val iterator = list1.iterator()

                while (iterator.hasNext()) {
                    val entitylivingbase = iterator.next() as EntityLivingBase
                    val d0 = this.getDistanceSqToEntity(entitylivingbase)

                    if (d0 < 16.0) {
                        var d1 = 1.0 - Math.sqrt(d0) / 4.0

                        if (entitylivingbase === movingObject.entityHit) {
                            d1 = 1.0
                        }

                        for (e: PotionEffect in effects)

                            if (Potion.potionTypes[e.potionID].isInstant) {
                                Potion.potionTypes[e.potionID].affectEntity(this.thrower, entitylivingbase, e.amplifier, d1)
                            } else {
                                val j = (d1 * e.duration.toDouble() + 0.5).toInt()

                                if (j > 20) {
                                    entitylivingbase.addPotionEffect(PotionEffect(e.potionID, j, e.amplifier))
                                }
                            }
                    }
                }
            }

        }

        worldObj.playAuxSFX(2002, Math.round(posX).toInt(), Math.round(posY).toInt(), Math.round(posZ).toInt(), 0)
        setDead()
    }

    override fun getGravityVelocity(): Float {
        return this.dataWatcher.getWatchableObjectFloat(30)
    }
}
