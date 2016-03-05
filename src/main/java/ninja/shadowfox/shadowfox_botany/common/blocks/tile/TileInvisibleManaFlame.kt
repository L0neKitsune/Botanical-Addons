package ninja.shadowfox.shadowfox_botany.common.blocks.tile

import net.minecraft.nbt.NBTTagCompound
import vazkii.botania.common.Botania

class TileInvisibleManaFlame : TileManaFlame() {
    private val TAG_COLOR = "color"
    var flameColor = 0x20FF20

    override fun writeCustomNBT(nbttagcompound: NBTTagCompound) {
        nbttagcompound.setInteger(TAG_COLOR, this.flameColor)
    }

    override fun readCustomNBT(nbttagcompound: NBTTagCompound) {
        this.flameColor = nbttagcompound.getInteger(TAG_COLOR)
    }

    override fun getColor(): Int {
        return this.flameColor
    }

    override fun shouldRender(): Boolean = Botania.proxy.isClientPlayerWearingMonocle
}
