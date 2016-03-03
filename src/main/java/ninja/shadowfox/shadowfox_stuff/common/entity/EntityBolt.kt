package ninja.shadowfox.shadowfox_stuff.common.entity

import io.netty.buffer.ByteBuf
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.IProjectile
import net.minecraft.entity.monster.EntityEnderman
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.network.play.server.S2BPacketChangeGameState
import net.minecraft.util.*
import net.minecraft.world.World
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import ninja.shadowfox.shadowfox_stuff.api.CollisionEffect

/**
 * 1.8 mod
 * created on 2/20/16
 */
class EntityBolt : Entity, IProjectile, IEntityAdditionalSpawnData {
    var xTile = -1
    var yTile = -1
    var zTile = -1

    var inTile: Block? = null
    var inData: Int = 0
    var inGround: Boolean = false

    var canBePickedUp: Int = 0
    var arrowShake: Int = 0
    var shootingEntity: Entity? = null

    var ticksInGround: Int = 0
    var ticksInAir: Int = 0
    var damage = 2.0
    var knockbackStrength: Int = 0
    var effect: CollisionEffect? = null
    var item: ItemStack? = null

    constructor(worldIn: World): super(worldIn) {
        this.renderDistanceWeight = 10.0
        this.setSize(0.5f, 0.5f)
    }

    constructor(worldIn: World, x: Double, y: Double, z: Double): super(worldIn) {
        this.renderDistanceWeight = 10.0
        this.setSize(0.5f, 0.5f)
        this.setPosition(x, y, z)
    }

    constructor(worldIn: World, shooter: EntityLivingBase, p_i1755_3_: EntityLivingBase, p_i1755_4_: Float, p_i1755_5_: Float): super(worldIn) {
        this.renderDistanceWeight = 10.0
        this.shootingEntity = shooter

        if (shooter is EntityPlayer) {
            this.canBePickedUp = 1
        }

        this.posY = shooter.posY + shooter.eyeHeight.toDouble() - 0.10000000149011612
        val d0 = p_i1755_3_.posX - shooter.posX
        val d1 = p_i1755_3_.entityBoundingBox.minY + (p_i1755_3_.height / 3.0f).toDouble() - this.posY
        val d2 = p_i1755_3_.posZ - shooter.posZ
        val d3 = MathHelper.sqrt_double(d0 * d0 + d2 * d2).toDouble()

        if (d3 >= 1.0E-7) {
            val f = (MathHelper.atan2(d2, d0) * 180.0 / Math.PI).toFloat() - 90.0f
            val f1 = (-(MathHelper.atan2(d1, d3) * 180.0 / Math.PI)).toFloat()
            val d4 = d0 / d3
            val d5 = d2 / d3
            this.setLocationAndAngles(shooter.posX + d4, this.posY, shooter.posZ + d5, f, f1)
            val f2 = (d3 * 0.20000000298023224).toFloat()
            this.setThrowableHeading(d0, d1 + f2.toDouble(), d2, p_i1755_4_, p_i1755_5_)
        }
    }

    constructor(worldIn: World, shooter: EntityLivingBase, velocity: Float): super(worldIn) {
        this.renderDistanceWeight = 10.0
        this.shootingEntity = shooter

        if (shooter is EntityPlayer) {
            this.canBePickedUp = 1
        }

        this.setSize(0.5f, 0.5f)
        this.setLocationAndAngles(shooter.posX, shooter.posY + shooter.eyeHeight.toDouble(), shooter.posZ, shooter.rotationYaw, shooter.rotationPitch)
        this.posX -= (MathHelper.cos(this.rotationYaw / 180.0f * Math.PI.toFloat()) * 0.16f).toDouble()
        this.posY -= 0.10000000149011612
        this.posZ -= (MathHelper.sin(this.rotationYaw / 180.0f * Math.PI.toFloat()) * 0.16f).toDouble()
        this.setPosition(this.posX, this.posY, this.posZ)
        this.motionX = (-MathHelper.sin(this.rotationYaw / 180.0f * Math.PI.toFloat()) * MathHelper.cos(this.rotationPitch / 180.0f * Math.PI.toFloat())).toDouble()
        this.motionZ = (MathHelper.cos(this.rotationYaw / 180.0f * Math.PI.toFloat()) * MathHelper.cos(this.rotationPitch / 180.0f * Math.PI.toFloat())).toDouble()
        this.motionY = (-MathHelper.sin(this.rotationPitch / 180.0f * Math.PI.toFloat())).toDouble()
        this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, velocity * 1.5f, 1.0f)
    }

    override fun entityInit() {
        this.dataWatcher.addObject(16, java.lang.Byte.valueOf(0.toByte()))
    }

    /**
     * Similar to setArrowHeading, it's point the throwable entity to a x, y, z direction.
     */
    override fun setThrowableHeading(_x: Double, _y: Double, _z: Double, velocity: Float, inaccuracy: Float) {
        var x = _x
        var y = _y
        var z = _z
        val f = MathHelper.sqrt_double(x * x + y * y + z * z)
        x /= f.toDouble()
        y /=  f.toDouble()
        z /=  f.toDouble()
        x += this.rand.nextGaussian() * (if (this.rand.nextBoolean()) -1 else 1).toDouble() * 0.007499999832361937 * inaccuracy.toDouble()
        y += this.rand.nextGaussian() * (if (this.rand.nextBoolean()) -1 else 1).toDouble() * 0.007499999832361937 * inaccuracy.toDouble()
        z += this.rand.nextGaussian() * (if (this.rand.nextBoolean()) -1 else 1).toDouble() * 0.007499999832361937 * inaccuracy.toDouble()
        x *= velocity.toDouble()
        y *= velocity.toDouble()
        z *= velocity.toDouble()
        this.motionX = x
        this.motionY = y
        this.motionZ = z
        val f1 = MathHelper.sqrt_double(x * x + z * z)
        this.rotationYaw = (MathHelper.atan2(x, z) * 180.0 / Math.PI).toFloat()
        this.prevRotationYaw = this.rotationYaw
        this.rotationPitch = (MathHelper.atan2(y, f1.toDouble()) * 180.0 / Math.PI).toFloat()
        this.prevRotationPitch = this.rotationPitch
        this.ticksInGround = 0
    }

    @SideOnly(Side.CLIENT)
    override fun setPositionAndRotation2(x: Double, y: Double, z: Double, yaw: Float, pitch: Float, posRotationIncrements: Int, p_180426_10_: Boolean) {
        this.setPosition(x, y, z)
        this.setRotation(yaw, pitch)
    }

    /**
     * Sets the velocity to the args. Args: x, y, z
     */
    @SideOnly(Side.CLIENT)
    override fun setVelocity(x: Double, y: Double, z: Double) {
        this.motionX = x
        this.motionY = y
        this.motionZ = z

        if (this.prevRotationPitch == 0.0f && this.prevRotationYaw == 0.0f) {
            val f = MathHelper.sqrt_double(x * x + z * z)
            this.rotationYaw = (MathHelper.atan2(x, z) * 180.0 / Math.PI).toFloat()
            this.rotationPitch = (MathHelper.atan2(y, f.toDouble()) * 180.0 / Math.PI).toFloat()
            this.prevRotationPitch = this.rotationPitch
            this.prevRotationYaw = this.rotationYaw
            this.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch)
            this.ticksInGround = 0
        }
    }

    /**
     * Called to update the entity's position/logic.
     */
    override fun onUpdate() {
        super.onUpdate()

        if (this.prevRotationPitch == 0.0f && this.prevRotationYaw == 0.0f) {
            val f = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ)
            this.rotationYaw = (MathHelper.atan2(this.motionX, this.motionZ) * 180.0 / Math.PI).toFloat()
            this.prevRotationYaw = this.rotationYaw
            this.rotationPitch = (MathHelper.atan2(this.motionY, f.toDouble()) * 180.0 / Math.PI).toFloat()
            this.prevRotationPitch = this.rotationPitch
        }

        val blockpos = BlockPos(this.xTile, this.yTile, this.zTile)
        val iblockstate = this.worldObj.getBlockState(blockpos)
        val block = iblockstate.block

        if (block.material !== Material.air) {
            block.setBlockBoundsBasedOnState(this.worldObj, blockpos)
            val axisalignedbb = block.getCollisionBoundingBox(this.worldObj, blockpos, iblockstate)

            if (axisalignedbb != null && axisalignedbb.isVecInside(Vec3(this.posX, this.posY, this.posZ))) {
                this.inGround = true
            }
        }

        if (this.arrowShake > 0) {
            --this.arrowShake
        }

        if (this.inGround) {
            val j = block.getMetaFromState(iblockstate)

            if (block === this.inTile && j == this.inData) {
                ++this.ticksInGround

                if (this.ticksInGround >= 1200) {
                    this.setDead()
                }
            } else {
                this.inGround = false
                this.motionX *= (this.rand.nextFloat() * 0.2f).toDouble()
                this.motionY *= (this.rand.nextFloat() * 0.2f).toDouble()
                this.motionZ *= (this.rand.nextFloat() * 0.2f).toDouble()
                this.ticksInGround = 0
                this.ticksInAir = 0
            }
        } else {
            ++this.ticksInAir
            var vec31 = Vec3(this.posX, this.posY, this.posZ)
            var vec3 = Vec3(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ)
            var movingobjectposition: MovingObjectPosition? = this.worldObj.rayTraceBlocks(vec31, vec3, false, true, false)
            vec31 = Vec3(this.posX, this.posY, this.posZ)
            vec3 = Vec3(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ)

            if (movingobjectposition != null) {
                vec3 = Vec3(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord)
            }

            var entity: Entity? = null
            val list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.entityBoundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0, 1.0, 1.0))
            var d0 = 0.0

            for (i in list.indices) {

                if (list[i].canBeCollidedWith() && (list[i] !== this.shootingEntity || this.ticksInAir >= 5)) {
                    val f1 = 0.3f
                    val axisalignedbb1 = list[i].entityBoundingBox.expand(f1.toDouble(), f1.toDouble(), f1.toDouble())
                    val movingobjectposition1 = axisalignedbb1.calculateIntercept(vec31, vec3)

                    if (movingobjectposition1 != null) {
                        val d1 = vec31.squareDistanceTo(movingobjectposition1.hitVec)

                        if (d1 < d0 || d0 == 0.0) {
                            entity = list[i]
                            d0 = d1
                        }
                    }
                }
            }

            if (entity != null) {
                movingobjectposition = MovingObjectPosition(entity)
            }

            if (movingobjectposition != null && movingobjectposition.entityHit != null && movingobjectposition.entityHit is EntityPlayer) {
                val entityplayer = movingobjectposition.entityHit as EntityPlayer

                if (entityplayer.capabilities.disableDamage || this.shootingEntity is EntityPlayer && !(this.shootingEntity as EntityPlayer).canAttackPlayer(entityplayer)) {
                    movingobjectposition = null
                }
            }

            if (movingobjectposition != null) {
                if (movingobjectposition.entityHit != null) {

                    if (effect == null) stdArrowStrike(movingobjectposition)
                    else effect?.onImpact(this, item, movingobjectposition)

                } else {
                    val blockpos1 = movingobjectposition.blockPos
                    this.xTile = blockpos1.x
                    this.yTile = blockpos1.y
                    this.zTile = blockpos1.z
                    val iblockstate1 = this.worldObj.getBlockState(blockpos1)
                    this.inTile = iblockstate1.block
                    this.inData = this.inTile!!.getMetaFromState(iblockstate1)
                    this.motionX = (movingobjectposition.hitVec.xCoord - this.posX).toFloat().toDouble()
                    this.motionY = (movingobjectposition.hitVec.yCoord - this.posY).toFloat().toDouble()
                    this.motionZ = (movingobjectposition.hitVec.zCoord - this.posZ).toFloat().toDouble()
                    val f5 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ)
                    this.posX -= this.motionX / f5.toDouble() * 0.05000000074505806
                    this.posY -= this.motionY / f5.toDouble() * 0.05000000074505806
                    this.posZ -= this.motionZ / f5.toDouble() * 0.05000000074505806
                    this.playSound("random.bowhit", 1.0f, 1.2f / (this.rand.nextFloat() * 0.2f + 0.9f))
                    this.inGround = true
                    this.arrowShake = 7
                    this.setIsCritical(false)

                    if (this.inTile!!.material !== Material.air) {
                        this.inTile!!.onEntityCollidedWithBlock(this.worldObj, blockpos1, iblockstate1, this)
                    }
                }
            }

            if (this.getIsCritical()) {
                for (k in 0..3) {
                    this.worldObj.spawnParticle(EnumParticleTypes.CRIT, this.posX + this.motionX * k.toDouble() / 4.0, this.posY + this.motionY * k.toDouble() / 4.0, this.posZ + this.motionZ * k.toDouble() / 4.0, -this.motionX, -this.motionY + 0.2, -this.motionZ, *IntArray(0))
                }
            }

            this.posX += this.motionX
            this.posY += this.motionY
            this.posZ += this.motionZ
            val f3 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ)
            this.rotationYaw = (MathHelper.atan2(this.motionX, this.motionZ) * 180.0 / Math.PI).toFloat()

            this.rotationPitch = (MathHelper.atan2(this.motionY, f3.toDouble()) * 180.0 / Math.PI).toFloat()
            while (this.rotationPitch - this.prevRotationPitch < -180.0f) {
                this.prevRotationPitch -= 360.0f
            }

            while (this.rotationPitch - this.prevRotationPitch >= 180.0f) {
                this.prevRotationPitch += 360.0f
            }

            while (this.rotationYaw - this.prevRotationYaw < -180.0f) {
                this.prevRotationYaw -= 360.0f
            }

            while (this.rotationYaw - this.prevRotationYaw >= 180.0f) {
                this.prevRotationYaw += 360.0f
            }

            this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2f
            this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2f
            var f4 = 0.99f
            val f6 = 0.05f

            if (this.isInWater) {
                for (i1 in 0..3) {
                    val f8 = 0.25f
                    this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX - this.motionX * f8.toDouble(), this.posY - this.motionY * f8.toDouble(), this.posZ - this.motionZ * f8.toDouble(), this.motionX, this.motionY, this.motionZ, *IntArray(0))
                }

                f4 = 0.6f
            }

            if (this.isWet) {
                this.extinguish()
            }

            this.motionX *= f4.toDouble()
            this.motionY *= f4.toDouble()
            this.motionZ *= f4.toDouble()
            this.motionY -= f6.toDouble()
            this.setPosition(this.posX, this.posY, this.posZ)
            this.doBlockCollisions()
        }
    }

    private fun stdArrowStrike(movingobjectposition: MovingObjectPosition) {
        val f2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ)
        var l = MathHelper.ceiling_double_int(f2.toDouble() * this.damage)

        if (this.getIsCritical()) {
            l += this.rand.nextInt(l / 2 + 2)
        }

        val damagesource: DamageSource

        if (this.shootingEntity == null) {
            damagesource = EntityDamageSourceIndirect("arrow", this, this).setProjectile()
        } else {
            damagesource = EntityDamageSourceIndirect("arrow", this, this.shootingEntity).setProjectile()
        }

        if (this.isBurning && movingobjectposition.entityHit !is EntityEnderman) {
            movingobjectposition.entityHit.setFire(5)
        }

        if (movingobjectposition.entityHit.attackEntityFrom(damagesource, l.toFloat())) {
            if (movingobjectposition.entityHit is EntityLivingBase) {
                val entitylivingbase = movingobjectposition.entityHit as EntityLivingBase

                if (!this.worldObj.isRemote) {
                    entitylivingbase.arrowCountInEntity = entitylivingbase.arrowCountInEntity + 1
                }

                if (this.knockbackStrength > 0) {
                    val f7 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ)

                    if (f7 > 0.0f) {
                        movingobjectposition.entityHit.addVelocity(this.motionX * this.knockbackStrength.toDouble() * 0.6000000238418579 / f7.toDouble(), 0.1, this.motionZ * this.knockbackStrength.toDouble() * 0.6000000238418579 / f7.toDouble())
                    }
                }

                if (this.shootingEntity is EntityLivingBase) {
                    EnchantmentHelper.applyThornEnchantments(entitylivingbase, this.shootingEntity)
                    EnchantmentHelper.applyArthropodEnchantments(this.shootingEntity as EntityLivingBase?, entitylivingbase)
                }

                if (this.shootingEntity != null && movingobjectposition.entityHit !== this.shootingEntity && movingobjectposition.entityHit is EntityPlayer && this.shootingEntity is EntityPlayerMP) {
                    (this.shootingEntity as EntityPlayerMP).playerNetServerHandler.sendPacket(S2BPacketChangeGameState(6, 0.0f))
                }
            }

            this.playSound("random.bowhit", 1.0f, 1.2f / (this.rand.nextFloat() * 0.2f + 0.9f))

            if (movingobjectposition.entityHit !is EntityEnderman) {
                this.setDead()
            }
        } else {
            this.motionX *= -0.10000000149011612
            this.motionY *= -0.10000000149011612
            this.motionZ *= -0.10000000149011612
            this.rotationYaw += 180.0f
            this.prevRotationYaw += 180.0f
            this.ticksInAir = 0
        }
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public override fun writeEntityToNBT(tagCompound: NBTTagCompound) {
        tagCompound.setShort("xTile", this.xTile.toShort())
        tagCompound.setShort("yTile", this.yTile.toShort())
        tagCompound.setShort("zTile", this.zTile.toShort())
        tagCompound.setShort("life", this.ticksInGround.toShort())
        tagCompound.setString("inTile", if (Block.blockRegistry.getNameForObject(this.inTile) == null) "" else Block.blockRegistry.getNameForObject(this.inTile).toString())
        tagCompound.setByte("inData", this.inData.toByte())
        tagCompound.setByte("shake", this.arrowShake.toByte())
        tagCompound.setByte("inGround", (if (this.inGround) 1 else 0).toByte())
        tagCompound.setByte("pickup", this.canBePickedUp.toByte())
        tagCompound.setDouble("damage", this.damage)

        if (item != null) {
            tagCompound.setTag("item", item!!.writeToNBT(NBTTagCompound()))
        }

    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public override fun readEntityFromNBT(tagCompund: NBTTagCompound) {
        this.xTile = tagCompund.getShort("xTile").toInt()
        this.yTile = tagCompund.getShort("yTile").toInt()
        this.zTile = tagCompund.getShort("zTile").toInt()
        this.ticksInGround = tagCompund.getShort("life").toInt()

        if (tagCompund.hasKey("inTile", 8)) {
            this.inTile = Block.getBlockFromName(tagCompund.getString("inTile"))
        } else {
            this.inTile = Block.getBlockById(tagCompund.getByte("inTile").toInt() and 255)
        }

        if (tagCompund.hasKey("item")) {
            this.item = ItemStack.loadItemStackFromNBT(tagCompund.getCompoundTag("item"))
        }

        this.inData = tagCompund.getByte("inData").toInt() and 255
        this.arrowShake = tagCompund.getByte("shake").toInt() and 255
        this.inGround = tagCompund.getByte("inGround").toInt() == 1

        if (tagCompund.hasKey("damage", 99)) {
            this.damage = tagCompund.getDouble("damage")
        }

        if (tagCompund.hasKey("pickup", 99)) {
            this.canBePickedUp = tagCompund.getByte("pickup").toInt()
        } else if (tagCompund.hasKey("player", 99)) {
            this.canBePickedUp = if (tagCompund.getBoolean("player")) 1 else 0
        }
    }

    /**
     * Called by a player entity when they collide with an entity
     */
    override fun onCollideWithPlayer(entityIn: EntityPlayer?) {
        if (!this.worldObj.isRemote && this.inGround && this.arrowShake <= 0 && item != null) {
            var flag = this.canBePickedUp == 1 || this.canBePickedUp == 2 && entityIn!!.capabilities.isCreativeMode

            if (this.canBePickedUp == 1 && !entityIn!!.inventory.addItemStackToInventory(item)) {
                flag = false
            }

            if (flag) {
                this.playSound("random.pop", 0.2f, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7f + 1.0f) * 2.0f)
                entityIn!!.onItemPickup(this, 1)
                this.setDead()
            }
        }
    }

    override fun canTriggerWalking(): Boolean = false
    override fun canAttackWithItem(): Boolean = false
    override fun getEyeHeight(): Float = 0.0f

    override fun readSpawnData(p0: ByteBuf?) {
    }

    override fun writeSpawnData(p0: ByteBuf?) {
    }

    /**
     * Whether the arrow has a stream of critical hit particles flying behind it.
     */
    fun setIsCritical(critical: Boolean) {
        val b0 = this.dataWatcher.getWatchableObjectByte(16).toInt()
        this.dataWatcher.updateObject<Byte>(16, java.lang.Byte.valueOf((if (critical) (b0 or 1) else (b0 and -2)).toByte()))

    }

    /**
     * Whether the arrow has a stream of critical hit particles flying behind it.
     */
    fun getIsCritical(): Boolean {
        val b0 = this.dataWatcher.getWatchableObjectByte(16).toInt()
        return b0 and 1 != 0
    }
}
