package ninja.shadowfox.shadowfox_botany.common.entity

import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.block.Block
import net.minecraft.block.BlockTallGrass
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.ai.attributes.RangedAttribute
import net.minecraft.entity.projectile.EntityArrow
import net.minecraft.entity.projectile.EntityFireball
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.*
import net.minecraft.world.World


class EntityKitsunebi : Entity {

    companion object {
        class DSKitsuneBolt(bolt: EntityKitsunebi, player: Entity) : EntityDamageSourceIndirect("kitsuneBolt", bolt, player) {
            init {
                setDamageBypassesArmor()
                setProjectile()
                setMagicDamage()
            }
        }

        val kitsuneAttackDamage = RangedAttribute("shadowfox_botany.kitsuneAttackDamage", 0.0, 0.0, Double.MAX_VALUE)
    }

    var shootingEntity: EntityLivingBase? = null

    var accelerationX: Double = 0.toDouble()
    var accelerationY: Double = 0.toDouble()
    var accelerationZ: Double = 0.toDouble()


    override fun entityInit() {}

    @SideOnly(Side.CLIENT)
    override fun isInRangeToRenderDist(p_isInRangeToRenderDist_1_: Double): Boolean {
        var var3 = boundingBox.averageEdgeLength * 4.0
        var3 *= 64.0
        return p_isInRangeToRenderDist_1_ < var3 * var3
    }

    constructor(p_i1760_1_: World): super(p_i1760_1_){
        this.setSize(0.3125f, 0.3125f)
    }

    constructor(p_i1760_1_: World, p_i1760_2_: Double, p_i1760_4_: Double, p_i1760_6_: Double): this(p_i1760_1_) {
        setLocationAndAngles(p_i1760_2_, p_i1760_4_, p_i1760_6_, rotationYaw, rotationPitch)
        setPosition(p_i1760_2_, p_i1760_4_, p_i1760_6_)
    }

    constructor(p_i1761_1_: World, p_i1761_2_: EntityLivingBase): this(p_i1761_1_) {
        shootingEntity = p_i1761_2_
        setLocationAndAngles(p_i1761_2_.posX, p_i1761_2_.posY, p_i1761_2_.posZ, p_i1761_2_.rotationYaw, p_i1761_2_.rotationPitch)
        setPosition(posX, posY, posZ)
        yOffset = 0.0f
        motionX = 0.0 ; motionY = 0.0 ; motionZ = 0.0
    }

    override fun onUpdate() {
        if (worldObj.isRemote || (shootingEntity == null || !shootingEntity!!.isDead) && worldObj.blockExists(posX.toInt(), posY.toInt(), posZ.toInt())) {
            super.onUpdate()
            setFire(1)

            if(ticksExisted > 1200) setDead()

            var var1 = Vec3.createVectorHelper(posX, posY, posZ)
            var var2 = Vec3.createVectorHelper(posX + motionX, posY + motionY, posZ + motionZ)
            var var3: MovingObjectPosition? = worldObj.rayTraceBlocks(var1, var2)
            var1 = Vec3.createVectorHelper(posX, posY, posZ)
            var2 = Vec3.createVectorHelper(posX + motionX, posY + motionY, posZ + motionZ)
            if (var3 != null) {
                var2 = Vec3.createVectorHelper(var3.hitVec.xCoord, var3.hitVec.yCoord, var3.hitVec.zCoord)
            }

            var var4: Entity? = null
            val var5 = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.addCoord(motionX, motionY, motionZ).expand(1.0, 1.0, 1.0))
            var var6 = 0.0

            for (var8 in var5.indices) {
                val var9 = var5[var8] as Entity

                if (var9.canBeCollidedWith() && !var9.isEntityEqual(shootingEntity)) {
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
                onImpact(var3)
            }

            posX += motionX
            posY += motionY
            posZ += motionZ
            val var15 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ)
            rotationYaw = (Math.atan2(motionZ, motionX) * 180.0 / 3.1415927410125732).toFloat() + 90.0f

            rotationPitch = (Math.atan2(var15.toDouble(), motionY) * 180.0 / 3.1415927410125732).toFloat() - 90.0f
            while (rotationPitch - prevRotationPitch < -180.0f) {
                prevRotationPitch -= 360.0f
            }

            while (rotationPitch - prevRotationPitch >= 180.0f) {
                prevRotationPitch += 360.0f
            }

            while (rotationYaw - prevRotationYaw < -180.0f) {
                prevRotationYaw -= 360.0f
            }

            while (rotationYaw - prevRotationYaw >= 180.0f) {
                prevRotationYaw += 360.0f
            }

            rotationPitch = prevRotationPitch + (rotationPitch - prevRotationPitch) * 0.2f
            rotationYaw = prevRotationYaw + (rotationYaw - prevRotationYaw) * 0.2f
            var var16 = getMotionFactor()
            if (isInWater) {
                for (var17 in 0..3) {
                    val var18 = 0.25f
                    worldObj.spawnParticle("bubble", posX - motionX * var18.toDouble(), posY - motionY * var18.toDouble(), posZ - motionZ * var18.toDouble(), motionX, motionY, motionZ)
                }

                var16 = 0.8f
            }

            motionX += accelerationX
            motionY += accelerationY
            motionZ += accelerationZ
            motionX *= var16.toDouble()
            motionY *= var16.toDouble()
            motionZ *= var16.toDouble()
            worldObj.spawnParticle("smoke", posX, posY + 0.5, posZ, 0.0, 0.0, 0.0)
            setPosition(posX, posY, posZ)
        } else {
            setDead()
        }
    }

    protected fun getMotionFactor(): Float {
        return 0.75f
    }

    protected fun onImpact(var1: MovingObjectPosition) {
        if (!worldObj.isRemote) {
            if (var1.entityHit != null && var1.entityHit != shootingEntity) {
                var1.entityHit.attackEntityFrom(DSKitsuneBolt(this, shootingEntity ?: this), 6.0f)
                setDead()
            }

        }
    }

    public override fun writeEntityToNBT(p_writeEntityToNBT_1_: NBTTagCompound) {
        p_writeEntityToNBT_1_.setTag("direction", newDoubleNBTList(*doubleArrayOf(motionX, motionY, motionZ)))
    }

    public override fun readEntityFromNBT(p_readEntityFromNBT_1_: NBTTagCompound) {
        if (p_readEntityFromNBT_1_.hasKey("direction", 9)) {
            val var2 = p_readEntityFromNBT_1_.getTagList("direction", 6)
            motionX = var2.func_150309_d(0)
            motionY = var2.func_150309_d(1)
            motionZ = var2.func_150309_d(2)
        } else {
            setDead()
        }

    }

    override fun canBeCollidedWith(): Boolean {
        return true
    }

    override fun getCollisionBorderSize(): Float {
        return 1.0f
    }

    override fun attackEntityFrom(p_attackEntityFrom_1_: DamageSource?, p_attackEntityFrom_2_: Float): Boolean {
        if (isEntityInvulnerable) {
            return false
        } else {
            setBeenAttacked()
            if (p_attackEntityFrom_1_!!.entity != null) {
                val var3 = p_attackEntityFrom_1_.entity.lookVec
                if (var3 != null) {
                    motionX = var3.xCoord
                    motionY = var3.yCoord
                    motionZ = var3.zCoord
                    accelerationX = motionX * 0.1
                    accelerationY = motionY * 0.1
                    accelerationZ = motionZ * 0.1
                }

                if (p_attackEntityFrom_1_.entity is EntityLivingBase) {
                    shootingEntity = p_attackEntityFrom_1_.entity as EntityLivingBase
                }

                return true
            } else {
                return false
            }
        }
    }

    @SideOnly(Side.CLIENT)
    override fun getShadowSize(): Float {
        return 0.0f
    }

    override fun getBrightness(p_getBrightness_1_: Float): Float {
        return 1.0f
    }

    @SideOnly(Side.CLIENT)
    override fun getBrightnessForRender(p_getBrightnessForRender_1_: Float): Int {
        return 15728880
    }
}
