package ninja.shadowfox.shadowfox_botany.common.throwables


object ShadowFoxThrowables {

    init {
        //        ShadowFoxAPI.registerThrowable(ThrowableCollidingItem(
        //                "shadowfox_fire_grenade",
        //                ItemStack(ShadowFoxItems.fireGrenade),
        //                ThrowableCollidingItem.OnImpactEvent { throwable, pos ->
        //                    if (!throwable.worldObj.isRemote) {
        //                        val axisalignedbb = throwable.boundingBox.expand(8.0, 2.0, 8.0)
        //                        val list1 = throwable.worldObj.getEntitiesWithinAABB(net.minecraft.entity.EntityLivingBase::class.java, axisalignedbb)
        //
        //                        if (list1 != null && !list1.isEmpty()) {
        //                            val iterator = list1.iterator()
        //
        //                            while (iterator.hasNext()) {
        //                                val entitylivingbase = iterator.next() as net.minecraft.entity.EntityLivingBase
        //                                val d0 = throwable.getDistanceSqToEntity(entitylivingbase)
        //
        //                                if (d0 < 16.0) {
        //                                    entitylivingbase.setFire(10)
        //                                }
        //                            }
        //                        }
        //
        //                        var i = net.minecraft.util.MathHelper.floor_double(throwable.posX)
        //                        var j = net.minecraft.util.MathHelper.floor_double(throwable.posY)
        //                        var k = net.minecraft.util.MathHelper.floor_double(throwable.posZ)
        //
        //                        if (throwable.worldObj.getBlock(i, j, k).material === net.minecraft.block.material.Material.air &&
        //                                net.minecraft.init.Blocks.fire.canPlaceBlockAt(throwable.worldObj, i, j, k)) {
        //                            throwable.worldObj.setBlock(i, j, k, net.minecraft.init.Blocks.fire)
        //                        }
        //
        //                        for (n in 0..36) {
        //                            j = net.minecraft.util.MathHelper.floor_double(throwable.posX) + throwable.worldObj.rand.nextInt(6) - 1
        //                            k = net.minecraft.util.MathHelper.floor_double(throwable.posY) + throwable.worldObj.rand.nextInt(6) - 1
        //                            val l = net.minecraft.util.MathHelper.floor_double(throwable.posZ) + throwable.worldObj.rand.nextInt(6) - 1
        //
        //                            if (throwable.worldObj.getBlock(j, k, l).material === net.minecraft.block.material.Material.air &&
        //                                    net.minecraft.init.Blocks.fire.canPlaceBlockAt(throwable.worldObj, j, k, l)) {
        //                                throwable.worldObj.setBlock(j, k, l, net.minecraft.init.Blocks.fire)
        //                            }
        //                        }
        //                    }
        //
        //
        //                    throwable.worldObj.playAuxSFX(2002, Math.round(throwable.posX).toInt(),
        //                            Math.round(throwable.posY).toInt(), Math.round(throwable.posZ).toInt(), 0)
        //
        //                }
        //        ))
    }
}
