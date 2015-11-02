package ninja.shadowfox.shadowfox_botany.common.entity

import net.minecraft.entity.monster.EntityCreeper
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.world.World
import vazkii.botania.common.entity.EntityManaStorm

/**
 * Created by l0nekitsune on 10/29/15.
 */
class EntityGrieferCreeper(p_i1701_1_: World): EntityCreeper(p_i1701_1_) {
    private var lastActiveTime: Int = 0
    /** The amount of time since the creeper was close enough to the player to ignite  */
    private var timeSinceIgnited: Int = 0
    private var explosionRadius = 6
    private var fuseTime = 30

    override fun writeEntityToNBT(p_70014_1_: NBTTagCompound) {
        super.writeEntityToNBT(p_70014_1_)

        if (this.dataWatcher.getWatchableObjectByte(17).toInt() == 1) {
            p_70014_1_.setBoolean("powered", true)
        }

        p_70014_1_.setShort("Fuse", this.fuseTime.toShort())
        p_70014_1_.setBoolean("ignited", this.func_146078_ca())
    }


    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    override fun readEntityFromNBT(p_70037_1_: NBTTagCompound) {
        super.readEntityFromNBT(p_70037_1_)
        this.dataWatcher.updateObject(17, java.lang.Byte.valueOf((if (p_70037_1_.getBoolean("powered")) 1 else 0).toByte()))

        if (p_70037_1_.hasKey("Fuse", 99)) {
            this.fuseTime = p_70037_1_.getShort("Fuse").toInt()
        }

        if (p_70037_1_.getBoolean("ignited")) {
            this.func_146079_cb()
        }
    }

    override fun onUpdate() {
        if (this.isEntityAlive) {
            this.lastActiveTime = this.timeSinceIgnited

            if (this.func_146078_ca()) {
                this.creeperState = 1
            }

            val i = this.creeperState

            if (i > 0 && this.timeSinceIgnited == 0) {
                this.playSound("creeper.primed", 1.0f, 0.5f)
            }

            this.timeSinceIgnited += i

            if (this.timeSinceIgnited < 0) {
                this.timeSinceIgnited = 0
            }

            if (this.timeSinceIgnited >= this.fuseTime) {
                this.timeSinceIgnited = this.fuseTime
                this.creeperGoBoom()
            }
        }
        super.onUpdate()
    }

    private fun creeperGoBoom() {
        if (!this.worldObj.isRemote) {
            val flag = this.worldObj.gameRules.getGameRuleBooleanValue("mobGriefing")

            if (this.powered) {
                val storm = EntityManaStorm(worldObj)
                storm.setPosition(posX.toDouble() + 0.5, posY.toDouble() + 0.5, posZ.toDouble() + 0.5)
                this.worldObj.spawnEntityInWorld(storm)
            } else {
                this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, (this.explosionRadius).toFloat(), flag)
            }

            this.setDead()
        }
    }
}