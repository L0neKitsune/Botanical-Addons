package ninja.shadowfox.shadowfox_botany.common.blocks.tile

import net.minecraft.nbt.NBTTagCompound
import net.minecraftforge.fml.relauncher.FMLLaunchHandler
import vazkii.botania.client.core.handler.ClientTickHandler
import vazkii.botania.common.Botania
import java.awt.Color
import java.util.*

class TileRainbowManaFlame : TileManaFlame() {
    private val TAG_INVISIBLE = "invisible"
    var invisible = false

    override fun writeCustomNBT(nbttagcompound: NBTTagCompound) {
        nbttagcompound.setBoolean(TAG_INVISIBLE, this.invisible)
    }

    override fun readCustomNBT(nbttagcompound: NBTTagCompound) {
        this.invisible = nbttagcompound.getBoolean(TAG_INVISIBLE)
    }

    override fun getColor(): Int {
        if (FMLLaunchHandler.side().isServer) return 0xFFFFFF
        var time = ClientTickHandler.ticksInGame + ClientTickHandler.partialTicks
        time += Random((this.pos.x xor this.pos.y xor this.pos.z).toLong()).nextInt(100000)
        return Color.HSBtoRGB(time * 0.005F, 1F, 1F)
    }

    override fun shouldRender(): Boolean = Botania.proxy.isClientPlayerWearingMonocle || !this.invisible
}
