package ninja.shadowfox.shadowfox_botany.common.entity

import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.monster.EntityCreeper
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Items
import net.minecraft.item.Item
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.potion.PotionEffect
import net.minecraft.util.AxisAlignedBB
import net.minecraft.world.World
import ninja.shadowfox.shadowfox_botany.common.brew.ShadowFoxPotions

/**
 * All the mana is mine mahhhaahahha
 */
class EntityVoidCreeper(p_i1701_1_: World): EntityCreeper(p_i1701_1_) {
    private var lastActiveTime: Int = 0
    private var timeSinceIgnited: Int = 0
    private var range: Int = 3
    private var fuseTime = 30

    override fun writeEntityToNBT(p_70014_1_: NBTTagCompound) {
        super.writeEntityToNBT(p_70014_1_)

        if (this.dataWatcher.getWatchableObjectByte(17).toInt() == 1) {
            p_70014_1_.setBoolean("powered", true)
        }

        p_70014_1_.setShort("Fuse", this.fuseTime.toShort())
        p_70014_1_.setBoolean("ignited", this.func_146078_ca())
    }

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

    override fun getDropItem(): Item {
        return Items.gunpowder
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

    override fun fall(p_70069_1_: Float) {
        super.fall(p_70069_1_)
        this.timeSinceIgnited = (this.timeSinceIgnited.toFloat() + p_70069_1_ * 1.5f).toInt()

        if (this.timeSinceIgnited > this.fuseTime - 5) {
            this.timeSinceIgnited = this.fuseTime - 5
        }
    }

    private fun creeperGoBoom() {
        if (!this.worldObj.isRemote) {
            val r = range * if(powered) 2 else 1

            var potential = this.worldObj.getEntitiesWithinAABB(EntityLivingBase::class.java, AxisAlignedBB.getBoundingBox(this.posX - r, this.posY - r, this.posZ - r, this.posX + r, this.posY + r, this.posZ + r))

            for(p in potential){
                if(p is EntityPlayer){
                    p.addPotionEffect(PotionEffect(ShadowFoxPotions.manaVoid.id, if(powered) 1200 else 120, 0))
                }
            }

            this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 1f, false)
            this.setDead()
        }
    }
}