package ninja.shadowfox.shadowfox_botany.common.blocks.tile

import net.minecraft.nbt.NBTTagCompound
import vazkii.botania.common.Botania

class TileInvisibleManaFlame : TileManaFlame() {
    private val TAG_COLOR = "color"
    public var flameColor = 0x20FF20

    override fun writeCustomNBT(nbttagcompound: NBTTagCompound) {
        nbttagcompound.setInteger("color", this.flameColor)
    }

    override fun readCustomNBT(nbttagcompound: NBTTagCompound) {
        this.flameColor = nbttagcompound.getInteger("color")
    }

    override fun getColor(): Int {
        return this.flameColor
    }
    override fun shouldRender(): Boolean = Botania.proxy.isClientPlayerWearingMonocle
}
