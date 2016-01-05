package ninja.shadowfox.shadowfox_botany.common.blocks.tile

import net.minecraft.nbt.NBTTagCompound
import net.minecraft.network.NetworkManager
import net.minecraft.network.Packet
import net.minecraft.network.play.server.S35PacketUpdateTileEntity
import vazkii.botania.common.Botania
import vazkii.botania.common.integration.coloredlights.ColoredLightHelper

abstract class TileManaFlame : ShadowFoxTile() {

    abstract fun getColor(): Int

    abstract fun shouldRender(): Boolean

    override fun updateEntity() {
        try {
            if (shouldRender()) {
                val c = 0.3f
                if (Math.random() < c.toDouble()) {
                    val v = 0.1f
                    val r = (getColor() shr 16 and 255).toFloat() / 255.0f + (Math.random() - 0.5).toFloat() * v
                    val g = (getColor() shr 8 and 255).toFloat() / 255.0f + (Math.random() - 0.5).toFloat() * v
                    val b = (getColor() and 255).toFloat() / 255.0f + (Math.random() - 0.5).toFloat() * v
                    val w = 0.15f
                    val h = 0.05f
                    val x = this.xCoord.toDouble() + 0.5 + (Math.random() - 0.5) * w.toDouble()
                    val y = this.yCoord.toDouble() + 0.25 + (Math.random() - 0.5) * h.toDouble()
                    val z = this.zCoord.toDouble() + 0.5 + (Math.random() - 0.5) * w.toDouble()
                    val s = 0.2f + Math.random().toFloat() * 0.1f
                    val m = 0.03f + Math.random().toFloat() * 0.015f
                    Botania.proxy.wispFX(this.worldObj, x, y, z, r, g, b, s, -m)
                }
            }
        } catch (e: NullPointerException) {
        }
    }

    fun getLightColor(): Int {
        val r = (getColor() shr 16 and 255).toFloat() / 255.0f
        val g = (getColor() shr 8 and 255).toFloat() / 255.0f
        val b = (getColor() and 255).toFloat() / 255.0f
        return ColoredLightHelper.makeRGBLightValue(r, g, b, 1.0f)
    }

    override fun getDescriptionPacket(): Packet {
        val nbttagcompound = NBTTagCompound()
        this.writeCustomNBT(nbttagcompound)
        return S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, -999, nbttagcompound)
    }

    override fun onDataPacket(net: NetworkManager?, pkt: S35PacketUpdateTileEntity?) {
        super.onDataPacket(net, pkt)
        this.readCustomNBT(pkt!!.func_148857_g())
    }
}
