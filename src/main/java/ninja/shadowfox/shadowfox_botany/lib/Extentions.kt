package ninja.shadowfox.shadowfox_botany.lib

import net.minecraft.entity.Entity
import net.minecraft.item.EnumDyeColor
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.AxisAlignedBB
import net.minecraftforge.oredict.OreDictionary
import ninja.shadowfox.shadowfox_botany.common.blocks.base.IVariantArrayHolder
import ninja.shadowfox.shadowfox_botany.common.blocks.base.Property
import ninja.shadowfox.shadowfox_botany.common.blocks.base.PropertyArray
import ninja.shadowfox.shadowfox_botany.common.blocks.base.temporaryVariantProp
import vazkii.botania.common.core.helper.Vector3

/**
 * BotanicalAddons
 * created on 3/16/16
 */
fun Array<Property>.asVariantArray(): Array<String> {
    temporaryVariantProp = PropertyArray.create(IVariantArrayHolder.HEADER, this)
    return Array(this.size, {i -> this[i]._name})
}

fun getIridescentProperties(base: String): Array<Property> {
    return Array(EnumDyeColor.values().size, { i -> Property("${base}_${EnumDyeColor.values()[i].name.toLowerCase()}", i) })
}

fun getMetaNames(base: String, values: Int): Array<String> {
    return Array(values, { i -> "${base}_${EnumDyeColor.values()[i].name.toLowerCase()}" })
}

fun Entity.boundingBox(range: Int = 1): AxisAlignedBB {
    return AxisAlignedBB.fromBounds(this.posX - range, this.posY - range, this.posZ - range, this.posX + range, this.posY + range, this.posZ + range)
}

fun TileEntity.boundingBox(range: Int = 1): AxisAlignedBB {
    return AxisAlignedBB.fromBounds((this.pos.x - range).toDouble(),
            (this.pos.y - range).toDouble(), (this.pos.z - range).toDouble(),
            (this.pos.x + range).toDouble(), (this.pos.y + range).toDouble(), (this.pos.z + range).toDouble())
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
