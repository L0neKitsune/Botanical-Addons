package ninja.shadowfox.shadowfox_botany.common.blocks.tile

import net.minecraft.nbt.NBTTagCompound
import vazkii.botania.common.Botania
import java.awt.Color

class TileRainbowManaFlame : TileManaFlame() {
    private val TAG_INVISIBLE = "invisible"
    public var invisible = false

    override fun writeCustomNBT(nbttagcompound: NBTTagCompound) {
        nbttagcompound.setBoolean(TAG_INVISIBLE, this.invisible)
    }

    override fun readCustomNBT(nbttagcompound: NBTTagCompound) {
        this.invisible = nbttagcompound.getBoolean(TAG_INVISIBLE)
    }

    override fun getColor(): Int = Color.HSBtoRGB(Botania.proxy.worldElapsedTicks * 2 % 360 / 360F, 1F, 1F)
    override fun shouldRender(): Boolean = !this.invisible || Botania.proxy.isClientPlayerWearingMonocle
}