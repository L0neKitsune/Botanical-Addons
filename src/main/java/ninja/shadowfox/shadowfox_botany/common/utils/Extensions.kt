package ninja.shadowfox.shadowfox_botany.common.utils

import net.minecraft.entity.Entity
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.AxisAlignedBB
import vazkii.botania.common.core.helper.Vector3

fun Entity.boundingBox(range: Int = 1): AxisAlignedBB {
    return AxisAlignedBB.getBoundingBox(this.posX - range, this.posY - range, this.posZ - range, this.posX + range, this.posY + range, this.posZ + range)
}

fun TileEntity.boundingBox(range: Int = 1): AxisAlignedBB {
    return AxisAlignedBB.getBoundingBox((this.xCoord - range).toDouble(),
            (this.yCoord - range).toDouble(), (this.zCoord - range).toDouble(),
            (this.xCoord + range).toDouble(), (this.yCoord + range).toDouble(), (this.zCoord + range).toDouble())
}

fun Entity.playSoundAtEntity(sound: String, volume: Float, duration: Float){
    this.worldObj.playSoundEffect(this.posX.toDouble(), this.posY.toDouble(), this.posZ.toDouble(), sound, volume, duration)
}

fun Entity.centerVector(): Vector3 {
    return Vector3.fromEntityCenter(this)
}
