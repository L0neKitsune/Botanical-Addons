package ninja.shadowfox.shadowfox_botany.common.throwables

import net.minecraft.block.material.Material
import net.minecraft.entity.EntityLivingBase
import net.minecraft.init.Blocks
import net.minecraft.item.ItemStack
import net.minecraft.util.BlockPos
import net.minecraft.util.MathHelper
import ninja.shadowfox.shadowfox_botany.api.ShadowFoxAPI
import ninja.shadowfox.shadowfox_botany.api.ShadowFoxAPI.*
import ninja.shadowfox.shadowfox_botany.api.item.ThrowableCollidingItem
import ninja.shadowfox.shadowfox_botany.common.item.ShadowFoxItems


object ShadowFoxThrowables {

    init {
//        registerThrowable(ThrowableCollidingItem(
//                "shadowfox_fire_grenade",
//                ItemStack(ShadowFoxItems.fireGrenade),
//                ThrowableCollidingItem.OnImpactEvent { throwable, pos ->
//                    if (!throwable.worldObj.isRemote) {
//                        val axisalignedbb = throwable.collisionBoundingBox.expand(8.0, 2.0, 8.0)
//                        val axisalignedbb = throwable.collisionBoundingBox.expand(8.0, 2.0, 8.0)
//                        val list1 = throwable.worldObj.getEntitiesWithinAABB(EntityLivingBase::class.java, axisalignedbb)
//
//                        if (list1 != null && !list1.isEmpty()) {
//                            val iterator = list1.iterator()
//
//                            while (iterator.hasNext()) {
//                                val entitylivingbase = iterator.next() as EntityLivingBase
//                                val d0 = throwable.getDistanceSqToEntity(entitylivingbase)
//
//                                if (d0 < 16.0) {
//                                    entitylivingbase.setFire(10)
//                                }
//                            }
//                        }
//
//                        var i = MathHelper.floor_double(throwable.posX)
//                        var j = MathHelper.floor_double(throwable.posY)
//                        var k = MathHelper.floor_double(throwable.posZ)
//
//                        if (throwable.worldObj.isAirBlock(BlockPos(i, j, k)) &&
//                                Blocks.fire.canPlaceBlockAt(throwable.worldObj, BlockPos(i, j, k))) {
//                            throwable.worldObj.setBlockState(BlockPos(i, j, k), Blocks.fire.defaultState)
//                        }
//
//                        for (n in 0..36) {
//                            j = MathHelper.floor_double(throwable.posX) + throwable.worldObj.rand.nextInt(6) - 1
//                            k = MathHelper.floor_double(throwable.posY) + throwable.worldObj.rand.nextInt(6) - 1
//                            val l = MathHelper.floor_double(throwable.posZ) + throwable.worldObj.rand.nextInt(6) - 1
//
//                            if (throwable.worldObj.isAirBlock(BlockPos(i, j, k)) &&
//                                    Blocks.fire.canPlaceBlockAt(throwable.worldObj, BlockPos(i, j, k))) {
//                                throwable.worldObj.setBlockState(BlockPos(i, j, k), Blocks.fire.defaultState)
//                            }
//                        }
//                    }
//
//
//                    throwable.worldObj.playAuxSFX(2002, BlockPos(Math.round(throwable.posX).toInt(),
//                            Math.round(throwable.posY).toInt(), Math.round(throwable.posZ).toInt()), 0)
//
//                }
//        ))
    }
}
