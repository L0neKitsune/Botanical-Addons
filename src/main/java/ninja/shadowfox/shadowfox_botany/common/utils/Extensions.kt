package ninja.shadowfox.shadowfox_botany.common.utils

import net.minecraft.entity.Entity
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.AxisAlignedBB
import net.minecraftforge.oredict.OreDictionary
import vazkii.botania.common.core.helper.Vector3

fun Entity.boundingBox(range: Int = 1): AxisAlignedBB {
    return AxisAlignedBB.getBoundingBox(this.posX - range, this.posY - range, this.posZ - range, this.posX + range, this.posY + range, this.posZ + range)
}

fun TileEntity.boundingBox(range: Int = 1): AxisAlignedBB {
    return AxisAlignedBB.getBoundingBox((this.xCoord - range).toDouble(),
            (this.yCoord - range).toDouble(), (this.zCoord - range).toDouble(),
            (this.xCoord + range).toDouble(), (this.yCoord + range).toDouble(), (this.zCoord + range).toDouble())
}

fun Entity.playSoundAtEntity(sound: String, volume: Float, duration: Float) {
    this.worldObj.playSoundEffect(this.posX.toDouble(), this.posY.toDouble(), this.posZ.toDouble(), sound, volume, duration)
}

fun Entity.centerVector(): Vector3 {
    return Vector3.fromEntityCenter(this)
}

fun TileEntity.centerVector(): Vector3 {
    return Vector3.fromTileEntityCenter(this)
}


fun ItemStack.itemEquals(rItem: Any): Boolean {
    if (rItem is String) {

        for (stack in OreDictionary.getOres(rItem)) {
            val cstack = stack.copy()

            if (cstack.itemDamage == 32767) cstack.itemDamage = this.itemDamage
            if (this.isItemEqual(cstack)) return true
        }

    } else if (rItem is ItemStack && simpleAreStacksEqual(rItem, this)) return true
    else return false
    return false
}

internal fun simpleAreStacksEqual(stack: ItemStack, stack2: ItemStack): Boolean {
    return stack.item === stack2.item && stack.itemDamage == stack2.itemDamage
}
