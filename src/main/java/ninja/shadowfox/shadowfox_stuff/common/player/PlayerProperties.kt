package ninja.shadowfox.shadowfox_stuff.common.player

import net.minecraft.entity.Entity
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.world.World
import net.minecraftforge.common.IExtendedEntityProperties

/**
 * 1.8 mod
 * created on 2/28/16
 */
class PlayerProperties() : IExtendedEntityProperties {
    override fun loadNBTData(p0: NBTTagCompound?) {
        throw UnsupportedOperationException()
    }

    override fun saveNBTData(p0: NBTTagCompound?) {
        throw UnsupportedOperationException()
    }

    override fun init(p0: Entity?, p1: World?) {
        throw UnsupportedOperationException()
    }
}
