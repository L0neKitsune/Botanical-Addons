package ninja.shadowfox.shadowfox_botany.common.blocks.tile

import net.minecraft.entity.Entity
import net.minecraft.entity.effect.EntityLightningBolt
import net.minecraft.init.Blocks
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.AxisAlignedBB
import net.minecraft.world.World
import net.minecraftforge.event.ForgeEventFactory
import java.util.*


class TileLightningRod() : TileEntity() {

    override fun updateEntity() {
        if (worldObj != null) {
            for (e in getBoltsWithinAABB(worldObj, AxisAlignedBB.getBoundingBox((xCoord - 48).toDouble(), (yCoord - 48).toDouble(), (zCoord - 48).toDouble(),
                    (xCoord + 48).toDouble(), (yCoord + 48).toDouble(), (zCoord + 48).toDouble()))) {
                worldObj.removeEntity(e)
                worldObj.addWeatherEffect(FakeLightning(worldObj, xCoord.toDouble(), (yCoord + 1).toDouble(), zCoord.toDouble()))
            }

            for (x in xCoord - 2..xCoord + 2)
                for (y in yCoord - 2..yCoord + 2)
                    for (z in zCoord - 2..zCoord + 2){
                        if (worldObj.getBlock(x, y, z) === Blocks.fire){
                            worldObj.setBlockToAir(x, y, z)
                        }
                    }
        }
    }

    fun getBoltsWithinAABB(world: World, box: AxisAlignedBB): ArrayList<EntityLightningBolt> {
        var bolts = ArrayList<EntityLightningBolt>()

        for (effect in world.weatherEffects) {
            if (effect is EntityLightningBolt && effect !is FakeLightning) {
                if (effect.posX.inRange(box.minX, box.maxX) &&
                        effect.posY.inRange(box.minY, box.maxY) &&
                        effect.posZ.inRange(box.minZ, box.maxZ)) {

                    bolts.add(effect)
                }
            }
        }

        return bolts
    }

    fun Double.inRange(min: Double, max: Double): Boolean {
        return (this > min && this < max)
    }

    /**
     * Like real lightning but less fire
     */
    class FakeLightning(world: World, x: Double, y: Double, z: Double) : EntityLightningBolt(world, x, y, z) {
        private var lightningState: Int = 0
        private var boltLivingTime: Int = 0

        init {
            this.setLocationAndAngles(x, y, z, 0.0f, 0.0f)
            this.lightningState = 2
            this.boltVertex = this.rand.nextLong()
            this.boltLivingTime = this.rand.nextInt(3) + 1
        }

        override fun onUpdate() {
            super.onUpdate()

            if (this.lightningState == 2) {
                this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "ambient.weather.thunder", 10000.0f, 0.8f + this.rand.nextFloat() * 0.2f)
                this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "random.explode", 2.0f, 0.5f + this.rand.nextFloat() * 0.2f)
            }

            --this.lightningState

            if (this.lightningState < 0) {
                if (this.boltLivingTime == 0) {
                    this.setDead()
                } else if (this.lightningState < -this.rand.nextInt(10)) {
                    --this.boltLivingTime
                    this.lightningState = 1
                    this.boltVertex = this.rand.nextLong()
                }
            }

            if (this.lightningState >= 0) {
                if (this.worldObj.isRemote) {
                    this.worldObj.lastLightningBolt = 2
                } else {
                    val d0 = 3.0
                    val list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, AxisAlignedBB.getBoundingBox(this.posX - d0, this.posY - d0, this.posZ - d0, this.posX + d0, this.posY + 6.0 + d0, this.posZ + d0))

                    for (l in list.indices) {
                        val entity = list[l] as Entity
                        if (!ForgeEventFactory.onEntityStruckByLightning(entity, this))
                            entity.onStruckByLightning(this)
                    }
                }
            }
        }

        override fun entityInit() {
        }

        override fun readEntityFromNBT(p_70037_1_: NBTTagCompound) {
        }

        override fun writeEntityToNBT(p_70014_1_: NBTTagCompound) {
        }
    }
}
