package ninja.shadowfox.shadowfox_botany.common.blocks.tile

import net.minecraft.nbt.NBTTagCompound
import vazkii.botania.client.core.handler.ClientTickHandler
import vazkii.botania.common.Botania
import java.awt.Color
import java.util.Random

class TileRainbowManaFlame : TileManaFlame() {
    private val TAG_INVISIBLE = "invisible"
    public var invisible = true

    override fun writeCustomNBT(nbttagcompound: NBTTagCompound) {
        nbttagcompound.setBoolean(TAG_INVISIBLE, this.invisible)
    }

    override fun readCustomNBT(nbttagcompound: NBTTagCompound) {
        this.invisible = nbttagcompound.getBoolean(TAG_INVISIBLE)
    }

    override fun getColor(): Int {
        var time = ClientTickHandler.ticksInGame + ClientTickHandler.partialTicks
        time += Random((this.xCoord xor this.yCoord xor this.zCoord).toLong()).nextInt(100000)
        return Color.HSBtoRGB(time * 0.005F, 1F, 1F)
    }
    override fun shouldRender(): Boolean = Botania.proxy.isClientPlayerWearingMonocle || !this.invisible
}
