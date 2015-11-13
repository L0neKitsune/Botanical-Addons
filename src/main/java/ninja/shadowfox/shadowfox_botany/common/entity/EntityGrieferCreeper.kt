package ninja.shadowfox.shadowfox_botany.common.entity

import net.minecraft.entity.monster.EntityCreeper
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.world.World
import vazkii.botania.common.entity.EntityManaStorm


class EntityGrieferCreeper(world: World): EntityCreeper(world) {
    private var lastActiveTime: Int = 0
    /** The amount of time since the creeper was close enough to the player to ignite  */
    private var timeSinceIgnited: Int = 0
    private var explosionRadius = 6
    private var fuseTime = 30

    override fun writeEntityToNBT(tag: NBTTagCompound) {
        super.writeEntityToNBT(tag)

        if (this.dataWatcher.getWatchableObjectByte(17).toInt() == 1) {
            tag.setBoolean("powered", true)
        }

        tag.setShort("Fuse", this.fuseTime.toShort())
        tag.setBoolean("ignited", this.func_146078_ca())
    }


    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    override fun readEntityFromNBT(tag: NBTTagCompound) {
        super.readEntityFromNBT(tag)
        this.dataWatcher.updateObject(17, java.lang.Byte.valueOf((if (tag.getBoolean("powered")) 1 else 0).toByte()))

        if (tag.hasKey("Fuse", 99)) {
            this.fuseTime = tag.getShort("Fuse").toInt()
        }

        if (tag.getBoolean("ignited")) {
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
