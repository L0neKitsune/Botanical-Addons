package ninja.shadowfox.shadowfox_botany.common.blocks.tile

import net.minecraft.nbt.NBTTagCompound
import net.minecraft.network.NetworkManager
import net.minecraft.network.Packet
import net.minecraft.network.play.server.S35PacketUpdateTileEntity
import vazkii.botania.common.integration.coloredlights.ColoredLightHelper

/**
 * @author WireSegal
 * Created at 9:32 PM on 2/6/16.
 */
class TileEntityStar : TileMod() {

    private val TAG_COLOR = "color"
    private val TAG_SIZE = "size"
    public var starColor = -1
    public var size = 0.05f

    override fun writeCustomNBT(nbttagcompound: NBTTagCompound) {
        nbttagcompound.setInteger(TAG_COLOR, this.starColor)
        nbttagcompound.setFloat(TAG_SIZE, this.size)
    }

    override fun readCustomNBT(nbttagcompound: NBTTagCompound) {
        this.starColor = nbttagcompound.getInteger(TAG_COLOR)
        this.size = nbttagcompound.getFloat(TAG_SIZE)
    }

    fun getColor(): Int {
        return this.starColor
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
