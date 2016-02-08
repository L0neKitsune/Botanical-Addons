package ninja.shadowfox.shadowfox_botany.common.entity

import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.IProjectile
import net.minecraft.entity.ai.attributes.RangedAttribute
import net.minecraft.entity.monster.EntityEnderman
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.init.Items
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.network.play.server.S2BPacketChangeGameState
import net.minecraft.util.*
import net.minecraft.world.World
import ninja.shadowfox.shadowfox_botany.common.blocks.ShadowFoxBlocks


class EntityKitsuneArrow: Entity,  IProjectile{
    private var xTile = -1
    private var yTile = -1
    private var zTile = -1
    private var block: Block? = null
    private var inData: Int = 0
    private var inGround: Boolean = false

    var canBePickedUp: Int = 2
    var arrowShake: Int = 0
    var shootingEntity: Entity? = null

    private var ticksInGround: Int = 0
    private var ticksInAir: Int = 0
    var damage = 2.0
    var knockbackStrength: Int = 0

    var isCritical: Boolean
        set(value) {
            val var2 = dataWatcher.getWatchableObjectByte(16).toInt()
            if (value) {
                dataWatcher.updateObject(16, java.lang.Byte.valueOf((var2 or 1).toByte()))
            } else {
                dataWatcher.updateObject(16, java.lang.Byte.valueOf((var2 and -2).toByte()))
            }

            isCritical = value
        }
        get() {
            val var1: Int = dataWatcher.getWatchableObjectByte(16).toInt()
            return (var1 and 1) != 0
        }

    constructor(world: World): super(world) {
        renderDistanceWeight = 10.0
        setSize(0.5f, 0.5f)
    }

    constructor(world: World, x: Double, y: Double, z: Double): super(world) {
        renderDistanceWeight = 10.0
        setSize(0.5f, 0.5f)
        setPosition(x, y, z)
        yOffset = 0.0f
    }

    constructor(world: World, shooter: EntityLivingBase, p_i1755_3_: EntityLivingBase, p_i1755_4_: Float, p_i1755_5_: Float): super(world) {
        renderDistanceWeight = 10.0
        shootingEntity = shooter

        posY = shooter.posY + shooter.eyeHeight.toDouble() - 0.10000000149011612

        val var6 = p_i1755_3_.posX - shooter.posX
        val var8 = p_i1755_3_.boundingBox.minY + (p_i1755_3_.height / 3.0f).toDouble() - posY
        val var10 = p_i1755_3_.posZ - shooter.posZ
        val var12 = MathHelper.sqrt_double(var6 * var6 + var10 * var10).toDouble()
        if (var12 >= 1.0E-7) {
            val var14 = (Math.atan2(var10, var6) * 180.0 / 3.1415927410125732).toFloat() - 90.0f
            val var15 = (-(Math.atan2(var8, var12) * 180.0 / 3.1415927410125732)).toFloat()
            val var16 = var6 / var12
            val var18 = var10 / var12
            setLocationAndAngles(shooter.posX + var16, posY, shooter.posZ + var18, var14, var15)
            yOffset = 0.0f
            val var20 = var12.toFloat() * 0.2f
            setThrowableHeading(var6, var8 + var20.toDouble(), var10, p_i1755_4_, p_i1755_5_)
        }
    }

    constructor(world: World, shooter: EntityLivingBase, p_i1756_3_: Float): super(world) {
        renderDistanceWeight = 10.0
        shootingEntity = shooter

        setSize(0.5f, 0.5f)
        setLocationAndAngles(shooter.posX, shooter.posY + shooter.eyeHeight.toDouble(), shooter.posZ, shooter.rotationYaw, shooter.rotationPitch)
        posX -= (MathHelper.cos(rotationYaw / 180.0f * 3.1415927f) * 0.16f).toDouble()
        posY -= 0.10000000149011612
        posZ -= (MathHelper.sin(rotationYaw / 180.0f * 3.1415927f) * 0.16f).toDouble()
        setPosition(posX, posY, posZ)
        yOffset = 0.0f
        motionX = (-MathHelper.sin(rotationYaw / 180.0f * 3.1415927f) * MathHelper.cos(rotationPitch / 180.0f * 3.1415927f)).toDouble()
        motionZ = (MathHelper.cos(rotationYaw / 180.0f * 3.1415927f) * MathHelper.cos(rotationPitch / 180.0f * 3.1415927f)).toDouble()
        motionY = (-MathHelper.sin(rotationPitch / 180.0f * 3.1415927f)).toDouble()
        setThrowableHeading(motionX, motionY, motionZ, p_i1756_3_ * 1.5f, 1.0f)
    }

    companion object {
        class DSKitsuneArrow(arrow: EntityKitsuneArrow, player: Entity): EntityDamageSourceIndirect("kitsuneArrow", arrow, player) {
            init {
                setDamageBypassesArmor()
                setProjectile()
                setMagicDamage()
            }
        }
        val kitsuneAttackDamage = RangedAttribute("shadowfox_botany.kitsuneAttackDamage", 0.0, 0.0, Double.MAX_VALUE)
    }


    override fun canTriggerWalking(): Boolean = false
    override fun canAttackWithItem(): Boolean = false

    @SideOnly(Side.CLIENT)
    override fun getShadowSize(): Float = 0f

    override fun entityInit() {
        dataWatcher.addObject(16, java.lang.Byte.valueOf(0.toByte()))
    }

    override fun setThrowableHeading(th1: Double, th3: Double, th5: Double, th7: Float, th9: Float) {
        var _th1 = th1
        var _th3 = th3
        var _th5 = th5

        MathHelper.sqrt_double(_th1 * _th1 + _th3 * _th3 + _th5 * _th5).toDouble().let {
            _th1 /= it ; _th3 /= it ; _th5 /= it
        }

        _th1 += rand.nextGaussian() * (if (rand.nextBoolean()) -1 else 1).toDouble() * 0.007499999832361937 * th9.toDouble()
        _th3 += rand.nextGaussian() * (if (rand.nextBoolean()) -1 else 1).toDouble() * 0.007499999832361937 * th9.toDouble()
        _th5 += rand.nextGaussian() * (if (rand.nextBoolean()) -1 else 1).toDouble() * 0.007499999832361937 * th9.toDouble()

        th7.toDouble().let { _th1 *= it ; _th3 *= it ; _th5 *= it }

        motionX = _th1
        motionY = _th3
        motionZ = _th5

        val var10 = MathHelper.sqrt_double(_th1 * _th1 + _th5 * _th5)

        rotationYaw = (Math.atan2(_th1, _th5) * 180.0 / 3.1415927410125732).toFloat()
        prevRotationYaw = rotationYaw
        rotationPitch = (Math.atan2(_th3, var10.toDouble()) * 180.0 / 3.1415927410125732).toFloat()
        prevRotationPitch = rotationPitch
        ticksInGround = 0
    }

    @SideOnly(Side.CLIENT)
    override fun setPositionAndRotation2(p_setPositionAndRotation2_1_: Double, p_setPositionAndRotation2_3_: Double, p_setPositionAndRotation2_5_: Double, p_setPositionAndRotation2_7_: Float, p_setPositionAndRotation2_8_: Float, p_setPositionAndRotation2_9_: Int) {
        setPosition(p_setPositionAndRotation2_1_, p_setPositionAndRotation2_3_, p_setPositionAndRotation2_5_)
        setRotation(p_setPositionAndRotation2_7_, p_setPositionAndRotation2_8_)
    }

    @SideOnly(Side.CLIENT)
    override fun setVelocity(p_setVelocity_1_: Double, p_setVelocity_3_: Double, p_setVelocity_5_: Double) {
        motionX = p_setVelocity_1_
        motionY = p_setVelocity_3_
        motionZ = p_setVelocity_5_
        if (prevRotationPitch == 0.0f && prevRotationYaw == 0.0f) {
            val var7 = MathHelper.sqrt_double(p_setVelocity_1_ * p_setVelocity_1_ + p_setVelocity_5_ * p_setVelocity_5_)
            rotationYaw = (Math.atan2(p_setVelocity_1_, p_setVelocity_5_) * 180.0 / 3.1415927410125732).toFloat()
            prevRotationYaw = rotationYaw
            rotationPitch = (Math.atan2(p_setVelocity_3_, var7.toDouble()) * 180.0 / 3.1415927410125732).toFloat()
            prevRotationPitch = rotationPitch
            prevRotationYaw = rotationYaw
            setLocationAndAngles(posX, posY, posZ, rotationYaw, rotationPitch)
            ticksInGround = 0
        }

    }

    override fun onUpdate() {
        super.onUpdate()
        if (prevRotationPitch == 0.0f && prevRotationYaw == 0.0f) {
            val var1 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ)
            rotationYaw = (Math.atan2(motionX, motionZ) * 180.0 / 3.1415927410125732).toFloat()
            prevRotationYaw = rotationYaw
            rotationPitch = (Math.atan2(motionY, var1.toDouble()) * 180.0 / 3.1415927410125732).toFloat()
            prevRotationPitch = rotationPitch
        }

        val var16 = worldObj.getBlock(xTile, yTile, zTile)
        if (var16.material !== Material.air) {
            var16.setBlockBoundsBasedOnState(worldObj, xTile, yTile, zTile)
            val var2 = var16.getCollisionBoundingBoxFromPool(worldObj, xTile, yTile, zTile)
            if (var2 != null && var2.isVecInside(Vec3.createVectorHelper(posX, posY, posZ))) {
                inGround = true
            }
        }

        if (arrowShake > 0) --arrowShake

        if (inGround) {
            worldObj.setBlock(xTile, yTile + 1, zTile, ShadowFoxBlocks.star)
            setDead()
        } else {
            ++ticksInAir
            var var17 = Vec3.createVectorHelper(posX, posY, posZ)
            var var3 = Vec3.createVectorHelper(posX + motionX, posY + motionY, posZ + motionZ)
            var var4: MovingObjectPosition? = worldObj.func_147447_a(var17, var3, false, true, false)
            var17 = Vec3.createVectorHelper(posX, posY, posZ)
            var3 = Vec3.createVectorHelper(posX + motionX, posY + motionY, posZ + motionZ)
            if (var4 != null) {
                var3 = Vec3.createVectorHelper(var4.hitVec.xCoord, var4.hitVec.yCoord, var4.hitVec.zCoord)
            }

            var var5: Entity? = null
            val var6 = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.addCoord(motionX, motionY, motionZ).expand(6.0, 6.0, 6.0))
            var var7 = 0.0

            var var11: Float
            for (e in var6) {
                val var10 = e as Entity
                if (var10.canBeCollidedWith() && (var10 !== shootingEntity || ticksInAir >= 5)) {
                    var11 = 4f
                    val var12 = var10.boundingBox.expand(var11.toDouble(), var11.toDouble(), var11.toDouble())
                    val var13 = var12.calculateIntercept(var17, var3)
                    if (var13 != null) {
                        val var14 = var17.distanceTo(var13.hitVec)
                        if (var14 < var7 || var7 == 0.0) {
                            var5 = var10
                            var7 = var14
                        }
                    }
                }
            }

            if (var5 != null) {
                var4 = MovingObjectPosition(var5)
            }

            if (var4 != null && var4.entityHit != null && var4.entityHit is EntityPlayer) {
                val var19 = var4.entityHit as EntityPlayer
                if (var19.capabilities.disableDamage || shootingEntity is EntityPlayer && !(shootingEntity as EntityPlayer).canAttackPlayer(var19)) {
                    var4 = null
                }
            }

            var var20: Float
            var var26: Float
            if (var4 != null) {
                if (var4.entityHit != null) {
                    var20 = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ)
                    var var21 = MathHelper.ceiling_double_int(var20.toDouble() * damage)
                    if (isCritical) {
                        var21 += rand.nextInt(var21 / 2 + 2)
                    }

                    var var22: DamageSource? = DSKitsuneArrow(this, if(shootingEntity == null) this else shootingEntity!!)

                    if (isBurning && var4.entityHit !is EntityEnderman) {
                        var4.entityHit.setFire(5)
                    }

                    if (var4.entityHit.attackEntityFrom(var22, var21.toFloat())) {
                        if (var4.entityHit is EntityLivingBase) {
                            val var24 = var4.entityHit as EntityLivingBase
                            if (!worldObj.isRemote) {
                                var24.arrowCountInEntity = var24.arrowCountInEntity + 1
                            }

                            if (knockbackStrength > 0) {
                                var26 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ)
                                if (var26 > 0.0f) {
                                    var4.entityHit.addVelocity(motionX * knockbackStrength.toDouble() * 0.6000000238418579 / var26.toDouble(), 0.1, motionZ * knockbackStrength.toDouble() * 0.6000000238418579 / var26.toDouble())
                                }
                            }

                            if (shootingEntity != null && shootingEntity is EntityLivingBase) {
                                EnchantmentHelper.func_151384_a(var24, shootingEntity)
                                EnchantmentHelper.func_151385_b(shootingEntity as EntityLivingBase?, var24)
                            }

                            if (shootingEntity != null && var4.entityHit !== shootingEntity && var4.entityHit is EntityPlayer && shootingEntity is EntityPlayerMP) {
                                (shootingEntity as EntityPlayerMP).playerNetServerHandler.sendPacket(S2BPacketChangeGameState(6, 0.0f))
                            }
                        }

                        playSound("random.bowhit", 1.0f, 1.2f / (rand.nextFloat() * 0.2f + 0.9f))
                        if (var4.entityHit !is EntityEnderman) {
                            setDead()
                        }
                    } else {
                        motionX *= -0.10000000149011612
                        motionY *= -0.10000000149011612
                        motionZ *= -0.10000000149011612
                        rotationYaw += 180.0f
                        prevRotationYaw += 180.0f
                        ticksInAir = 0
                    }
                } else {
                    xTile = var4.blockX
                    yTile = var4.blockY
                    zTile = var4.blockZ
                    block = worldObj.getBlock(xTile, yTile, zTile)
                    inData = worldObj.getBlockMetadata(xTile, yTile, zTile)
                    motionX = (var4.hitVec.xCoord - posX).toFloat().toDouble()
                    motionY = (var4.hitVec.yCoord - posY).toFloat().toDouble()
                    motionZ = (var4.hitVec.zCoord - posZ).toFloat().toDouble()
                    var20 = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ)
                    posX -= motionX / var20.toDouble() * 0.05000000074505806
                    posY -= motionY / var20.toDouble() * 0.05000000074505806
                    posZ -= motionZ / var20.toDouble() * 0.05000000074505806
                    playSound("random.bowhit", 1.0f, 1.2f / (rand.nextFloat() * 0.2f + 0.9f))
                    inGround = true
                    arrowShake = 7
                    isCritical = false
                    if (block!!.material !== Material.air) {
                        block!!.onEntityCollidedWithBlock(worldObj, xTile, yTile, zTile, this)
                    }
                }
            }

            if (isCritical) {
                for (i in 0..4) {
                    worldObj.spawnParticle("crit", posX + motionX * i.toDouble() / 4.0, posY + motionY * i.toDouble() / 4.0, posZ + motionZ * i.toDouble() / 4.0, -motionX, -motionY + 0.2, -motionZ)
                }
            }

            posX += motionX
            posY += motionY
            posZ += motionZ
            var20 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ)
            rotationYaw = (Math.atan2(motionX, motionZ) * 180.0 / 3.1415927410125732).toFloat()

            rotationPitch = (Math.atan2(motionY, var20.toDouble()) * 180.0 / 3.1415927410125732).toFloat()
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
            var var23 = 0.99f
            var11 = 0.05f
            if (isInWater) {
                for (var25 in 0..3) {
                    var26 = 0.25f
                    worldObj.spawnParticle("bubble", posX - motionX * var26.toDouble(), posY - motionY * var26.toDouble(), posZ - motionZ * var26.toDouble(), motionX, motionY, motionZ)
                }

                var23 = 0.8f
            }

            if (isWet) extinguish()


            motionX *= var23.toDouble()
            motionY *= var23.toDouble()
            motionZ *= var23.toDouble()
            motionY -= var11.toDouble()
            setPosition(posX, posY, posZ)
            func_145775_I()
        }
    }

    override fun writeEntityToNBT(tagCompound: NBTTagCompound) {
        tagCompound.setShort("xTile", xTile.toShort())
        tagCompound.setShort("yTile", yTile.toShort())
        tagCompound.setShort("zTile", zTile.toShort())
        tagCompound.setShort("life", ticksInGround.toShort())
        tagCompound.setByte("inTile", Block.getIdFromBlock(block).toByte())
        tagCompound.setByte("inData", inData.toByte())
        tagCompound.setByte("shake", arrowShake.toByte())
        tagCompound.setByte("inGround", (if (inGround) 1 else 0).toByte())
        tagCompound.setByte("pickup", canBePickedUp.toByte())
        tagCompound.setDouble("damage", damage)
    }

    override fun readEntityFromNBT(tagCompound: NBTTagCompound) {
        xTile = tagCompound.getShort("xTile").toInt()
        yTile = tagCompound.getShort("yTile").toInt()
        zTile = tagCompound.getShort("zTile").toInt()
        ticksInGround = tagCompound.getShort("life").toInt()
        block = Block.getBlockById(tagCompound.getByte("inTile").toInt() and 255)
        inData = tagCompound.getByte("inData").toInt() and 255
        arrowShake = tagCompound.getByte("shake").toInt() and 255
        inGround = tagCompound.getByte("inGround").toInt() == 1
        if (tagCompound.hasKey("damage", 99)) {
            damage = tagCompound.getDouble("damage")
        }

        if (tagCompound.hasKey("pickup", 99)) {
            canBePickedUp = tagCompound.getByte("pickup").toInt()
        } else if (tagCompound.hasKey("player", 99)) {
            canBePickedUp = if (tagCompound.getBoolean("player")) 1 else 0
        }
    }
}
