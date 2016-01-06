package ninja.shadowfox.shadowfox_botany.common.entity

import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.monster.EntityCreeper
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Items
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.potion.PotionEffect
import net.minecraft.util.AxisAlignedBB
import net.minecraft.world.World
import ninja.shadowfox.shadowfox_botany.common.brew.ShadowFoxPotions
import ninja.shadowfox.shadowfox_botany.common.core.handler.ConfigHandler
import vazkii.botania.common.item.ModItems

/**
 * All the mana is mine mahhhaahahha
 */
class EntityVoidCreeper(world: World) : EntityCreeper(world) {
    private var lastActiveTime: Int = 0
    private var timeSinceIgnited: Int = 0
    private var range: Int = 3
    private var fuseTime = 30

    override fun writeEntityToNBT(tag: NBTTagCompound) {
        super.writeEntityToNBT(tag)

        if (this.dataWatcher.getWatchableObjectByte(17).toInt() == 1) {
            tag.setBoolean("powered", true)
        }

        tag.setShort("Fuse", this.fuseTime.toShort())
        tag.setBoolean("ignited", this.func_146078_ca())
    }

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

    override fun getDropItem(): Item {
        return Items.gunpowder
    }

    override fun dropFewItems(par1: Boolean, par2: Int) {
        if (par1) {
            if (Math.random() < ConfigHandler.blackLotusDropRate) {
                this.entityDropItem(ItemStack(ModItems.blackLotus), 1F)
            }
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

    override fun fall(distance: Float) {
        super.fall(distance)
        this.timeSinceIgnited = (this.timeSinceIgnited.toFloat() + distance * 1.5f).toInt()

        if (this.timeSinceIgnited > this.fuseTime - 5) {
            this.timeSinceIgnited = this.fuseTime - 5
        }
    }

    private fun creeperGoBoom() {
        if (!this.worldObj.isRemote) {
            val r = range * if (powered) 2 else 1

            var potential = this.worldObj.getEntitiesWithinAABB(EntityLivingBase::class.java, AxisAlignedBB.getBoundingBox(this.posX - r, this.posY - r, this.posZ - r, this.posX + r, this.posY + r, this.posZ + r))

            for (p in potential) {
                if (p is EntityPlayer) {
                    p.addPotionEffect(PotionEffect(ShadowFoxPotions.manaVoid.id, if (powered) 1200 else 120, 0))
                }
            }

            this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 1f, false)
            this.setDead()
        }
    }
}
