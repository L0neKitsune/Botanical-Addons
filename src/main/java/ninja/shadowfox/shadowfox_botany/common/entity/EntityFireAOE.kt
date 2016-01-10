package ninja.shadowfox.shadowfox_botany.common.entity

import net.minecraft.block.material.Material
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.projectile.EntityThrowable
import net.minecraft.init.Blocks
import net.minecraft.util.MathHelper
import net.minecraft.util.MovingObjectPosition

/**
 * Created by l0nekitsune on 1/7/16.
 */
class EntityFireAOE(player: EntityPlayer) : EntityThrowable(player.worldObj, player) {


    override fun onImpact(movingObject: MovingObjectPosition?) {
        if (!this.worldObj.isRemote && movingObject != null) {
            val axisalignedbb = this.boundingBox.expand(8.0, 2.0, 8.0)
            val list1 = this.worldObj.getEntitiesWithinAABB(EntityLivingBase::class.java, axisalignedbb)

            if (list1 != null && !list1.isEmpty()) {
                val iterator = list1.iterator()

                while (iterator.hasNext()) {
                    val entitylivingbase = iterator.next() as EntityLivingBase
                    val d0 = this.getDistanceSqToEntity(entitylivingbase)

                    if (d0 < 16.0) {
                        entitylivingbase.setFire(10)
                    }
                }
            }

            var i = MathHelper.floor_double(posX)
            var j = MathHelper.floor_double(posY)
            var k = MathHelper.floor_double(posZ)

            if (worldObj.getBlock(i, j, k).material === Material.air &&
                    Blocks.fire.canPlaceBlockAt(worldObj, i, j, k)) {
                worldObj.setBlock(i, j, k, Blocks.fire)
            }

            for (n in 0..36) {
                j = MathHelper.floor_double(posX) + this.rand.nextInt(6) - 1
                k = MathHelper.floor_double(posY) + this.rand.nextInt(6) - 1
                val l = MathHelper.floor_double(posZ) + this.rand.nextInt(6) - 1

                if (worldObj.getBlock(j, k, l).material === Material.air && Blocks.fire.canPlaceBlockAt(worldObj, j, k, l)) {
                    worldObj.setBlock(j, k, l, Blocks.fire)
                }
            }


        }

        worldObj.playAuxSFX(2002, Math.round(posX).toInt(), Math.round(posY).toInt(), Math.round(posZ).toInt(), 0)
        setDead()
    }
}
