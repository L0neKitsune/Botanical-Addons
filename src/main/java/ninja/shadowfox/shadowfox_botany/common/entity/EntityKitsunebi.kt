package ninja.shadowfox.shadowfox_botany.common.entity

import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.ai.attributes.RangedAttribute
import net.minecraft.entity.monster.EntityBlaze
import net.minecraft.entity.projectile.EntityFireball
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.*
import net.minecraft.world.World


class EntityKitsunebi : EntityFireball {

    companion object {
        val KITSUNEBI_OFFSETS_X = arrayOf(2.00, 1.53, 0.35, -1.00, -1.88, -1.88, -1.00, 0.35, 1.53)
        val KITSUNEBI_OFFSETS_Y = arrayOf(0.00, 1.29, 1.97, 1.73, 0.68, -0.68, -1.73, -1.97, -1.29)

        class DSKitsuneBolt(bolt: EntityKitsunebi, player: Entity) : EntityDamageSourceIndirect("kitsunebi", bolt, player) {
            init {
                setDamageBypassesArmor()
                setProjectile()
                setMagicDamage()
            }
        }

        val kitsuneAttackDamage = RangedAttribute("shadowfox_botany.kitsunebiAttackDamage", 0.0, 0.0, Double.MAX_VALUE)
    }

    var damage: Double = 0.0
    var following_shooter: Boolean = false

    var offsetIndex: Int = 0


    override fun entityInit() {
    }

    @SideOnly(Side.CLIENT)
    override fun isInRangeToRenderDist(p_isInRangeToRenderDist_1_: Double): Boolean {
        var var3 = boundingBox.averageEdgeLength * 4.0
        var3 *= 64.0
        return p_isInRangeToRenderDist_1_ < var3 * var3
    }

    constructor(p_i1760_1_: World) : super(p_i1760_1_) {
        this.setSize(0.3125f, 0.3125f)
    }

    constructor(p_i1760_1_: World, p_i1760_2_: Double, p_i1760_4_: Double, p_i1760_6_: Double) : this(p_i1760_1_) {
        setLocationAndAngles(p_i1760_2_, p_i1760_4_, p_i1760_6_, rotationYaw, rotationPitch)
        setPosition(p_i1760_2_, p_i1760_4_, p_i1760_6_)
    }

    constructor(p_i1771_1_: World, p_i1771_2_: EntityLivingBase, p_i1771_3_: Double, p_i1771_5_: Double, p_i1771_7_: Double): super(p_i1771_1_, p_i1771_2_, p_i1771_3_, p_i1771_5_, p_i1771_7_){
        this.setSize(0.3125f, 0.3125f)
    }

    constructor(p_i1760_1_: World, p_i1760_2_: Double, p_i1760_4_: Double, p_i1760_6_: Double, p_i1760_8_: Double, p_i1760_10_: Double, p_i1760_12_: Double): this(p_i1760_1_)
    {
        this.setLocationAndAngles(p_i1760_2_, p_i1760_4_, p_i1760_6_, this.rotationYaw, this.rotationPitch)
        this.setPosition(p_i1760_2_, p_i1760_4_, p_i1760_6_)
        val var14 = MathHelper.sqrt_double(p_i1760_8_ * p_i1760_8_ + p_i1760_10_ * p_i1760_10_ + p_i1760_12_ * p_i1760_12_).toDouble()
        this.accelerationX = p_i1760_8_ / var14 * 0.1
        this.accelerationY = p_i1760_10_ / var14 * 0.1
        this.accelerationZ = p_i1760_12_ / var14 * 0.1
    }

    constructor(p_i1761_1_: World, p_i1761_2_: EntityLivingBase) : this(p_i1761_1_) {
        shootingEntity = p_i1761_2_
        setLocationAndAngles(p_i1761_2_.posX, p_i1761_2_.posY, p_i1761_2_.posZ, p_i1761_2_.rotationYaw, p_i1761_2_.rotationPitch)
        setPosition(posX, posY, posZ)
        yOffset = 0.0f
        motionX = 0.0 ; motionY = 0.0 ; motionZ = 0.0
    }

    override fun onUpdate() {
        if (worldObj.isRemote || (shootingEntity == null || !shootingEntity!!.isDead)) {
            super.onUpdate()

            setFire(1)
            if (ticksExisted > 1200) setDead()

            if (following_shooter) {

                if (shootingEntity != null) {
                    val var5 = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.addCoord(motionX, motionY, motionZ).expand(1.0, 1.0, 1.0))

                    for (var8 in var5) {
                        val var9 = var8 as Entity

                        if (var9.canBeCollidedWith() && !var9.isEntityEqual(shootingEntity)) {
                            if (var9.boundingBox.expand(.3, .3, .3).intersectsWith(this.boundingBox.expand(.3, .3, .3)))
                                onImpact(MovingObjectPosition(var9))
                        }
                    }

                    val X = shootingEntity!!.posX.toDouble() + KITSUNEBI_OFFSETS_X[offsetIndex]
                    val Z = shootingEntity!!.posZ.toDouble() + KITSUNEBI_OFFSETS_Y[offsetIndex]
                    val Y = shootingEntity!!.posY + .5

                    setPosition(X, Y, Z)

                } else {
                    //                    setDead()
                }

            } else {
                var var1 = Vec3.createVectorHelper(this.posX, this.posY, this.posZ)
                var var2 = Vec3.createVectorHelper(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ)
                var var3: MovingObjectPosition? = this.worldObj.rayTraceBlocks(var1, var2)
                var1 = Vec3.createVectorHelper(this.posX, this.posY, this.posZ)
                var2 = Vec3.createVectorHelper(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ)
                if (var3 != null) {
                    var2 = Vec3.createVectorHelper(var3.hitVec.xCoord, var3.hitVec.yCoord, var3.hitVec.zCoord)
                }

                var var4: Entity? = null
                val var5 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0, 1.0, 1.0))
                var var6 = 0.0

                for (var8 in var5.indices) {
                    val var9 = var5[var8] as Entity
                    if (var9.canBeCollidedWith() && !var9.isEntityEqual(this.shootingEntity)) {
                        val var10 = 0.3f
                        val var11 = var9.boundingBox.expand(var10.toDouble(), var10.toDouble(), var10.toDouble())
                        val var12 = var11.calculateIntercept(var1, var2)
                        if (var12 != null) {
                            val var13 = var1.distanceTo(var12.hitVec)
                            if (var13 < var6 || var6 == 0.0) {
                                var4 = var9
                                var6 = var13
                            }
                        }
                    }
                }

                if (var4 != null) {
                    var3 = MovingObjectPosition(var4)
                }

                if (var3 != null) {
                    this.onImpact(var3)
                }

                this.posX += this.motionX
                this.posY += this.motionY
                this.posZ += this.motionZ
                val var15 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ)
                this.rotationYaw = (Math.atan2(this.motionZ, this.motionX) * 180.0 / 3.1415927410125732).toFloat() + 90.0f

                this.rotationPitch = (Math.atan2(var15.toDouble(), this.motionY) * 180.0 / 3.1415927410125732).toFloat() - 90.0f
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
                var var16 = this.getMotionFactor()
                if (this.isInWater) {
                    for (var17 in 0..3) {
                        val var18 = 0.25f
                        this.worldObj.spawnParticle("bubble", this.posX - this.motionX * var18.toDouble(), this.posY - this.motionY * var18.toDouble(), this.posZ - this.motionZ * var18.toDouble(), this.motionX, this.motionY, this.motionZ)
                    }

                    var16 = 0.8f
                }

                this.motionX += this.accelerationX
                this.motionY += this.accelerationY
                this.motionZ += this.accelerationZ
                this.motionX *= var16.toDouble()
                this.motionY *= var16.toDouble()
                this.motionZ *= var16.toDouble()
                this.worldObj.spawnParticle("smoke", this.posX, this.posY + 0.5, this.posZ, 0.0, 0.0, 0.0)
                this.setPosition(this.posX, this.posY, this.posZ)
            }
        } else {
            setDead()
        }
    }

    override fun getMotionFactor(): Float {
        return 0.95f
    }


    override fun onImpact(var1: MovingObjectPosition) {
        if (!worldObj.isRemote) {
            if (var1.entityHit != null && var1.entityHit != shootingEntity) {
                var1.entityHit.attackEntityFrom(DSKitsuneBolt(this, shootingEntity ?: this), 6.0f)
                setDead()
            }

        }
    }

    public override fun writeEntityToNBT(p_writeEntityToNBT_1_: NBTTagCompound) {
        super.writeEntityToNBT(p_writeEntityToNBT_1_)
        p_writeEntityToNBT_1_.setDouble("damage", damage)
        p_writeEntityToNBT_1_.setBoolean("follow", following_shooter)

    }

    public override fun readEntityFromNBT(p_readEntityFromNBT_1_: NBTTagCompound) {
        damage = p_readEntityFromNBT_1_.getDouble("damage")
        following_shooter = p_readEntityFromNBT_1_.getBoolean("follow")
        super.readEntityFromNBT(p_readEntityFromNBT_1_)

    }

    override fun canBeCollidedWith(): Boolean {
        return true
    }

    override fun getCollisionBorderSize(): Float {
        return .35f
    }

    override fun attackEntityFrom(p_attackEntityFrom_1_: DamageSource?, p_attackEntityFrom_2_: Float): Boolean {
//        if (isEntityInvulnerable) {
//            return false
//        } else {
//            setBeenAttacked()
//            if (p_attackEntityFrom_1_!!.entity != null) {
//                val var3 = p_attackEntityFrom_1_.entity.lookVec
//                if (var3 != null) {
//                    motionX = var3.xCoord
//                    motionY = var3.yCoord
//                    motionZ = var3.zCoord
//                    accelerationX = motionX * 0.1
//                    accelerationY = motionY * 0.1
//                    accelerationZ = motionZ * 0.1
//                }
//
//                if (p_attackEntityFrom_1_.entity is EntityLivingBase) {
//                    shootingEntity = p_attackEntityFrom_1_.entity as EntityLivingBase
//                }
//
//                return true
//            } else {
//                return false
//            }
//        }
        return false
    }

    @SideOnly(Side.CLIENT)
    override fun getShadowSize(): Float {
        return 0.0f
    }

    override fun getBrightness(p_getBrightness_1_: Float): Float {
        return 3.0f
    }

    @SideOnly(Side.CLIENT)
    override fun getBrightnessForRender(p_getBrightnessForRender_1_: Float): Int {
        return 15728880
    }
}
